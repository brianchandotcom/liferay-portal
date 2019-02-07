/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.change.tracking.internal;

import com.liferay.change.tracking.CTEngineManager;
import com.liferay.change.tracking.configuration.CTConfiguration;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.internal.configuration.CTConfigurationRegistry;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = CTEngineManager.class)
public class CTEngineManagerImpl implements CTEngineManager {

	@Override
	public void checkoutCTCollection(long userId, long ctCollectionId) {
		long companyId = _getCompanyId(userId);

		if (companyId <= 0) {
			return;
		}

		if (!isChangeTrackingEnabled(companyId)) {
			return;
		}

		Optional<CTCollection> ctCollectionOptional = getCTCollectionOptional(
			ctCollectionId);

		if (!ctCollectionOptional.isPresent()) {
			_log.error(
				"Unable to checkout change tracking collection " +
					ctCollectionId);

			return;
		}

		try {
			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					_updateRecentCTCollectionId(userId, ctCollectionId);

					return null;
				});
		}
		catch (Throwable t) {
			_log.error(
				"Unable to update user's recent change tracking collection", t);
		}
	}

	@Override
	public Optional<CTCollection> createCTCollection(
		long userId, String name, String description) {

		long companyId = _getCompanyId(userId);

		if (companyId <= 0) {
			return Optional.empty();
		}

		if (CTConstants.CT_COLLECTION_NAME_PRODUCTION.equals(name) ||
			!isChangeTrackingEnabled(companyId)) {

			return Optional.empty();
		}

		return _createCTCollection(userId, name, description);
	}

	@Override
	public void deleteCTCollection(long ctCollectionId) {
		Optional<CTCollection> ctCollectionOptional = getCTCollectionOptional(
			ctCollectionId);

		if (!ctCollectionOptional.isPresent()) {
			_log.error(
				"Unable to delete change tracking collection " +
					ctCollectionId);

			return;
		}

		try {
			_ctCollectionLocalService.deleteCTCollection(
				ctCollectionOptional.get());
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to delete change tracking collection " +
					ctCollectionId,
				pe);
		}
	}

	@Override
	public void disableChangeTracking(long companyId) {
		if (!isChangeTrackingEnabled(companyId)) {
			return;
		}

		try {
			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					_ctCollectionLocalService.deleteCompanyCTCollections(
						companyId);

					_productionCTCollection = null;

					return null;
				});
		}
		catch (Throwable t) {
			_log.error("Unable to disable change tracking", t);
		}
	}

	@Override
	public void enableChangeTracking(long companyId, long userId) {
		User user = _userLocalService.fetchUser(userId);

		if (user == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get user " + userId);
			}

			return;
		}

		if (isChangeTrackingEnabled(companyId)) {
			return;
		}

		try {
			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					Optional<CTCollection> ctCollectionOptional =
						_createCTCollection(
							userId, CTConstants.CT_COLLECTION_NAME_PRODUCTION,
							StringPool.BLANK);

					ctCollectionOptional.ifPresent(
						ctCollection -> {
							_productionCTCollection =
								ctCollectionOptional.orElse(null);

							checkoutCTCollection(
								userId, ctCollection.getCtCollectionId());
						});

					return null;
				});
		}
		catch (Throwable t) {
			_log.error("Unable to enable change tracking", t);
		}
	}

	@Override
	public Optional<CTCollection> getActiveCTCollectionOptional(long userId) {
		long companyId = _getCompanyId(userId);

		if (companyId <= 0) {
			return Optional.empty();
		}

		if (!isChangeTrackingEnabled(companyId)) {
			return Optional.empty();
		}

		long recentCTCollectionId = _getRecentCTCollectionId(userId);

		if (recentCTCollectionId == 0L) {
			Optional<CTCollection> productionCTCollectionOptional =
				getProductionCTCollectionOptional(companyId);

			recentCTCollectionId = productionCTCollectionOptional.map(
				CTCollection::getCtCollectionId
			).orElse(
				0L
			);

			checkoutCTCollection(userId, recentCTCollectionId);
		}

		return getCTCollectionOptional(recentCTCollectionId);
	}

	@Override
	public Optional<CTCollection> getCTCollectionOptional(long ctCollectionId) {
		CTCollection ctCollection = _ctCollectionLocalService.fetchCTCollection(
			ctCollectionId);

		return Optional.ofNullable(ctCollection);
	}

	@Override
	public List<CTCollection> getCTCollections(long companyId) {
		if (!isChangeTrackingEnabled(companyId)) {
			return Collections.emptyList();
		}

		return _ctCollectionLocalService.getCTCollections(companyId);
	}

	@Override
	public List<CTEntry> getCTEntries(long ctCollectionId) {
		return _ctEntryLocalService.getCTCollectionCTEntries(ctCollectionId);
	}

	public Optional<CTCollection> getProductionCTCollectionOptional(
		long companyId) {

		if (_productionCTCollection == null) {
			_productionCTCollection =
				_ctCollectionLocalService.fetchCTCollection(
					companyId, CTConstants.CT_COLLECTION_NAME_PRODUCTION);
		}

		return Optional.ofNullable(_productionCTCollection);
	}

	@Override
	public boolean isChangeTrackingEnabled(long companyId) {
		Optional<CTCollection> productionCTCollection =
			getProductionCTCollectionOptional(companyId);

		return productionCTCollection.isPresent();
	}

	@Override
	public boolean isChangeTrackingSupported(
		long companyId, Class<? extends BaseModel> clazz) {

		Optional<CTConfiguration<?, ?>> ctConfigurationOptional =
			_ctConfigurationRegistry.getCTConfigurationOptionalByVersionClass(
				clazz);

		return ctConfigurationOptional.isPresent();
	}

	@Override
	public boolean isChangeTrackingSupported(long companyId, long classNameId) {
		String className = _portal.getClassName(classNameId);

		Optional<CTConfiguration<?, ?>> ctConfigurationOptional =
			_ctConfigurationRegistry.
				getCTConfigurationOptionalByVersionClassName(className);

		return ctConfigurationOptional.isPresent();
	}

	@Override
	public void publishCTCollection(long userId, long ctCollectionId) {
		long companyId = _getCompanyId(userId);

		if (companyId <= 0) {
			return;
		}

		if (!isChangeTrackingEnabled(companyId)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to publish change tracking collection because " +
						"change tracking is not enabled in company " +
							companyId);
			}

			return;
		}

		List<CTEntry> ctEntries = getCTEntries(ctCollectionId);

		if (ListUtil.isEmpty(ctEntries)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find change tracking entries with change " +
						"tracking collection ID " + ctCollectionId);
			}

			return;
		}

		try {
			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					_publishCTEntries(userId, ctCollectionId, ctEntries);

					return null;
				});
		}
		catch (Throwable t) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to publish change tracking entries to change " +
						"tracking collection " + ctCollectionId,
					t);
			}
		}
	}

	private void _copyEntriesFromProduction(CTCollection ctCollection) {
		Optional<CTCollection> productionCTCollectionOptional =
			getProductionCTCollectionOptional(ctCollection.getCompanyId());

		List<CTEntry> productionCTEntries = productionCTCollectionOptional.map(
			CTCollection::getCtCollectionId
		).map(
			this::getCTEntries
		).orElse(
			Collections.emptyList()
		);

		for (CTEntry ctEntry : productionCTEntries) {
			_ctCollectionLocalService.addCTEntryCTCollection(
				ctEntry.getCtEntryId(), ctCollection);
		}
	}

	private Optional<CTCollection> _createCTCollection(
		long userId, String name, String description) {

		CTCollection ctCollection = null;

		try {
			ctCollection = TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					CTCollection addedCTCollection =
						_ctCollectionLocalService.addCTCollection(
							userId, name, description, new ServiceContext());

					_copyEntriesFromProduction(addedCTCollection);

					return addedCTCollection;
				});
		}
		catch (Throwable t) {
			_log.error(
				"Unable to create change tracking collection with name " + name,
				t);
		}

		return Optional.ofNullable(ctCollection);
	}

	private long _getCompanyId(long userId) {
		long companyId = 0;

		User user = _userLocalService.fetchUser(userId);

		if (user == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = user.getCompanyId();
		}

		if (companyId <= 0) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get company ID");
			}
		}

		return companyId;
	}

	private long _getRecentCTCollectionId(long userId) {
		User user = _userLocalService.fetchUser(userId);

		if (user == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get user " + userId);
			}

			return 0L;
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				userId, !user.isDefaultUser());

		return GetterUtil.getLong(
			portalPreferences.getValue(
				CTPortletKeys.CHANGE_LISTS, _RECENT_CT_COLLECTION_ID));
	}

	private void _publishCTEntries(
		long userId, long ctCollectionId, List<CTEntry> ctEntries) {

		User user = _userLocalService.fetchUser(userId);

		if (user == null) {
			_log.error("Unable to get user " + userId);

			return;
		}

		Optional<CTCollection> productionCTCollectionOptional =
			getProductionCTCollectionOptional(user.getCompanyId());

		if (!productionCTCollectionOptional.isPresent()) {
			return;
		}

		long productionCTCollectionId = productionCTCollectionOptional.map(
			CTCollection::getCtCollectionId
		).get();

		_ctEntryLocalService.addCTCollectionCTEntries(
			productionCTCollectionId, ctEntries);

		Optional<CTCollection> ctCollectionOptional = getCTCollectionOptional(
			ctCollectionId);

		if (!ctCollectionOptional.isPresent()) {
			return;
		}

		try {
			_ctCollectionLocalService.updateStatus(
				userId, ctCollectionOptional.get(),
				WorkflowConstants.STATUS_APPROVED, new ServiceContext());
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to update the status of the published change " +
					"tracking collection",
				pe);
		}
	}

	private void _updateRecentCTCollectionId(long userId, long ctCollectionId) {
		User user = _userLocalService.fetchUser(userId);

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				userId, !user.isDefaultUser());

		portalPreferences.setValue(
			CTPortletKeys.CHANGE_LISTS, _RECENT_CT_COLLECTION_ID,
			String.valueOf(ctCollectionId));
	}

	private static final String _RECENT_CT_COLLECTION_ID =
		"recentCTCollectionId";

	private static final Log _log = LogFactoryUtil.getLog(
		CTEngineManagerImpl.class);

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTConfigurationRegistry _ctConfigurationRegistry;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private Portal _portal;

	private CTCollection _productionCTCollection;
	private final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private UserLocalService _userLocalService;

}