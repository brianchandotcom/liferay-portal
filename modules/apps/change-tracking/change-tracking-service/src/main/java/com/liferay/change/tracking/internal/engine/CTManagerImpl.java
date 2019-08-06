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

package com.liferay.change.tracking.internal.engine;

import com.liferay.change.tracking.engine.CTEngineManager;
import com.liferay.change.tracking.engine.CTManager;
import com.liferay.change.tracking.engine.exception.CTEngineException;
import com.liferay.change.tracking.engine.exception.CTEntryCTEngineException;
import com.liferay.change.tracking.exception.DuplicateCTEntryException;
import com.liferay.change.tracking.internal.util.CTEntryCollisionUtil;
import com.liferay.change.tracking.internal.util.ChangeTrackingThreadLocal;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.util.comparator.CTEntryCreateDateComparator;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
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
@Component(immediate = true, service = CTManager.class)
public class CTManagerImpl implements CTManager {

	@Override
	public <T> T executeModelUpdate(
			UnsafeSupplier<T, PortalException> modelUpdateSupplier)
		throws PortalException {

		boolean resetFlag = false;

		try {
			if (!ChangeTrackingThreadLocal.isModelUpdateInProgress()) {
				resetFlag = true;

				ChangeTrackingThreadLocal.setModelUpdateInProgress(true);
			}

			return modelUpdateSupplier.get();
		}
		finally {
			if (resetFlag) {
				ChangeTrackingThreadLocal.setModelUpdateInProgress(false);
			}
		}
	}

	@Override
	public Optional<CTEntry> getActiveCTCollectionCTEntryOptional(
		long companyId, long userId, long modelClassNameId, long modelClassPK) {

		Optional<CTCollection> ctCollectionOptional =
			getActiveCTCollectionOptional(companyId, userId);

		long ctCollectionId = ctCollectionOptional.map(
			CTCollection::getCtCollectionId
		).orElse(
			0L
		);

		CTEntry ctEntry = _getCTEntry(
			companyId, ctCollectionId, modelClassNameId, modelClassPK);

		return Optional.ofNullable(ctEntry);
	}

	@Override
	public Optional<CTCollection> getActiveCTCollectionOptional(
		long companyId, long userId) {

		if (!_ctEngineManager.isChangeTrackingEnabled(companyId)) {
			return Optional.empty();
		}

		long recentCTCollectionId = _ctEngineManager.getRecentCTCollectionId(
			userId);

		_ctEngineManager.checkoutCTCollection(userId, recentCTCollectionId);

		return _ctEngineManager.getCTCollectionOptional(
			companyId, recentCTCollectionId);
	}

	@Override
	public List<CTEntry> getCTCollectionCTEntries(
		long companyId, long ctCollectionId, long classNameId) {

		if (!_ctEngineManager.isChangeTrackingEnabled(companyId) ||
			!_ctEngineManager.isChangeTrackingSupported(
				companyId, classNameId)) {

			return Collections.emptyList();
		}

		return _ctEntryLocalService.fetchCTEntriesByModelClassNameId(
			ctCollectionId, classNameId, new QueryDefinition<>());
	}

	@Override
	public List<CTCollection> getCTCollections(
		long companyId, long userId, boolean includeProduction,
		boolean includeActive, QueryDefinition<CTCollection> queryDefinition) {

		queryDefinition.setAttribute("includeActive", includeActive);

		if (!includeActive) {
			Optional<CTCollection> activeCTCollectionOptional =
				getActiveCTCollectionOptional(companyId, userId);

			CTCollection activeCTCollection = activeCTCollectionOptional.get();

			queryDefinition.setAttribute(
				"activeCTCollectionId", activeCTCollection.getCtCollectionId());
		}

		return _ctCollectionLocalService.getCTCollections(
			companyId, queryDefinition, includeProduction);
	}

	@Override
	public Optional<CTEntry> getLatestModelChangeCTEntryOptional(
		long companyId, long userId, long resourcePrimKey) {

		if (!_ctEngineManager.isChangeTrackingEnabled(companyId)) {
			return Optional.empty();
		}

		QueryDefinition<CTEntry> queryDefinition = new QueryDefinition<>();

		queryDefinition.setEnd(1);
		queryDefinition.setOrderByComparator(new CTEntryCreateDateComparator());
		queryDefinition.setStart(0);

		List<CTEntry> ctEntries = getModelChangeCTEntries(
			companyId, userId, resourcePrimKey, queryDefinition);

		if (ListUtil.isEmpty(ctEntries)) {
			return Optional.empty();
		}

		return Optional.of(ctEntries.get(0));
	}

	@Override
	public List<CTEntry> getModelChangeCTEntries(
		long companyId, long userId, long resourcePrimKey) {

		QueryDefinition<CTEntry> queryDefinition = new QueryDefinition<>();

		queryDefinition.setOrderByComparator(
			new CTEntryCreateDateComparator(true));

		return getModelChangeCTEntries(
			companyId, userId, resourcePrimKey, queryDefinition);
	}

	@Override
	public List<CTEntry> getModelChangeCTEntries(
		long companyId, long userId, long resourcePrimKey,
		QueryDefinition<CTEntry> queryDefinition) {

		Optional<CTCollection> ctCollectionOptional =
			getActiveCTCollectionOptional(companyId, userId);

		long ctCollectionId = ctCollectionOptional.map(
			CTCollection::getCtCollectionId
		).orElse(
			0L
		);

		return _ctEntryLocalService.fetchCTEntries(
			ctCollectionId, resourcePrimKey, queryDefinition);
	}

	@Override
	public Optional<CTEntry> getModelChangeCTEntryOptional(
		long companyId, long userId, long modelClassNameId, long modelClassPK) {

		Optional<CTEntry> ctEntryOptional =
			getActiveCTCollectionCTEntryOptional(
				companyId, userId, modelClassNameId, modelClassPK);

		if (ctEntryOptional.isPresent()) {
			return ctEntryOptional;
		}

		return getProductionCTCollectionCTEntryOptional(
			companyId, modelClassNameId, modelClassPK);
	}

	@Override
	public Optional<CTEntry> getProductionCTCollectionCTEntryOptional(
		long companyId, long modelClassNameId, long modelClassPK) {

		Optional<CTCollection> ctCollectionOptional =
			_ctEngineManager.getProductionCTCollectionOptional(companyId);

		long ctCollectionId = ctCollectionOptional.map(
			CTCollection::getCtCollectionId
		).orElse(
			0L
		);

		CTEntry ctEntry = _getCTEntry(
			companyId, ctCollectionId, modelClassNameId, modelClassPK);

		return Optional.ofNullable(ctEntry);
	}

	@Override
	public boolean isModelUpdateInProgress() {
		return ChangeTrackingThreadLocal.isModelUpdateInProgress();
	}

	@Override
	public boolean isProductionCheckedOut(long companyId, long userId) {
		Optional<CTCollection> activeCTCollectionOptional =
			getActiveCTCollectionOptional(companyId, userId);

		return activeCTCollectionOptional.map(
			CTCollection::isProduction
		).orElse(
			true
		);
	}

	@Override
	public boolean isRetrievableVersion(
		long companyId, long userId, long modelClassNameId, long modelClassPK) {

		if (!_ctEngineManager.isChangeTrackingEnabled(companyId) ||
			!_ctEngineManager.isChangeTrackingSupported(
				companyId, modelClassNameId)) {

			return true;
		}

		Optional<CTEntry> activeCTCollectionCTEntryOptional =
			getActiveCTCollectionCTEntryOptional(
				companyId, userId, modelClassNameId, modelClassPK);

		if (!activeCTCollectionCTEntryOptional.isPresent()) {
			return true;
		}

		CTEntry ctEntry = activeCTCollectionCTEntryOptional.get();

		if (ctEntry.getStatus() != WorkflowConstants.STATUS_DRAFT) {
			return true;
		}

		if (isProductionCheckedOut(companyId, userId)) {
			return false;
		}

		Optional<CTEntry> ctEntryOptional =
			getActiveCTCollectionCTEntryOptional(
				companyId, userId, modelClassNameId, modelClassPK);

		return ctEntryOptional.isPresent();
	}

	@Override
	public Optional<CTEntry> registerModelChange(
			long companyId, long userId, long modelClassNameId,
			long modelClassPK, long modelResourcePrimKey, int changeType)
		throws CTEngineException {

		return registerModelChange(
			companyId, userId, modelClassNameId, modelClassPK,
			modelResourcePrimKey, changeType, false);
	}

	@Override
	public Optional<CTEntry> registerModelChange(
			long companyId, long userId, long modelClassNameId,
			long modelClassPK, long modelResourcePrimKey, int changeType,
			boolean force)
		throws CTEngineException {

		if (!_ctEngineManager.isChangeTrackingEnabled(companyId) ||
			!_ctEngineManager.isChangeTrackingSupported(
				companyId, modelClassNameId)) {

			return Optional.empty();
		}

		Optional<CTCollection> ctCollectionOptional =
			getActiveCTCollectionOptional(companyId, userId);

		if (!ctCollectionOptional.isPresent()) {
			return Optional.empty();
		}

		CTCollection ctCollection = ctCollectionOptional.get();

		if (ctCollection.isProduction()) {
			CTEntryCollisionUtil.checkCollidingCTEntries(
				_ctEntryLocalService, companyId, modelClassPK,
				modelResourcePrimKey);

			return Optional.empty();
		}

		Optional<CTEntry> ctEntryOptional = Optional.empty();

		try {
			ctEntryOptional = TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> _registerModelChange(
					companyId, userId, modelClassNameId, modelClassPK,
					modelResourcePrimKey, changeType, force, ctCollection));
		}
		catch (Throwable t) {
			if (t instanceof CTEngineException) {
				throw (CTEngineException)t;
			}

			_log.error("Unable to register model change", t);
		}

		return ctEntryOptional;
	}

	@Override
	public Optional<CTEntry> unregisterModelChange(
		long companyId, long userId, long modelClassNameId, long modelClassPK) {

		if (!_ctEngineManager.isChangeTrackingEnabled(companyId) ||
			!_ctEngineManager.isChangeTrackingSupported(
				companyId, modelClassNameId)) {

			return Optional.empty();
		}

		Optional<CTEntry> activeCTCollectionCTEntryOptional =
			getActiveCTCollectionCTEntryOptional(
				companyId, userId, modelClassNameId, modelClassPK);

		if (activeCTCollectionCTEntryOptional.isPresent()) {
			return Optional.of(
				_ctEntryLocalService.deleteCTEntry(
					activeCTCollectionCTEntryOptional.get()));
		}

		return Optional.empty();
	}

	private CTEntry _getCTEntry(
		long companyId, long ctCollectionId, long modelClassNameId,
		long modelClassPK) {

		if (!_ctEngineManager.isChangeTrackingEnabled(companyId) ||
			!_ctEngineManager.isChangeTrackingSupported(
				companyId, modelClassNameId)) {

			return null;
		}

		return _ctEntryLocalService.fetchCTEntry(
			ctCollectionId, modelClassNameId, modelClassPK);
	}

	private Optional<CTEntry> _registerModelChange(
			long companyId, long userId, long modelClassNameId,
			long modelClassPK, long modelResourcePrimKey, int changeType,
			boolean force, CTCollection ctCollection)
		throws CTEngineException {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAttribute("force", force);

			// Creating a new change tracking entry

			CTEntry ctEntry = _ctEntryLocalService.addCTEntry(
				userId, modelClassNameId, modelClassPK, modelResourcePrimKey,
				changeType, ctCollection.getCtCollectionId(), serviceContext);

			return Optional.of(ctEntry);
		}
		catch (DuplicateCTEntryException dctee) {
			StringBundler sb = new StringBundler(8);

			sb.append("Duplicate CTEntry with model class name ID ");
			sb.append(modelClassNameId);
			sb.append(", model class PK ");
			sb.append(modelClassPK);
			sb.append(", and model resource primary key ");
			sb.append(modelResourcePrimKey);
			sb.append(" in change tracking collection ");
			sb.append(ctCollection.getCtCollectionId());

			throw new CTEntryCTEngineException(
				0L, companyId, userId, modelClassNameId, modelClassPK,
				modelResourcePrimKey, ctCollection.getCtCollectionId(),
				sb.toString(), dctee);
		}
		catch (PortalException pe) {
			StringBundler sb = new StringBundler(9);

			sb.append("Unable to register model change with model class name ");
			sb.append("ID ");
			sb.append(modelClassNameId);
			sb.append(", model class PK ");
			sb.append(modelClassPK);
			sb.append(", and model resource primary key ");
			sb.append(modelResourcePrimKey);
			sb.append(" in change tracking collection ");
			sb.append(ctCollection.getCtCollectionId());

			throw new CTEngineException(companyId, sb.toString(), pe);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(CTManagerImpl.class);

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTEngineManager _ctEngineManager;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private Portal _portal;

	private final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

}