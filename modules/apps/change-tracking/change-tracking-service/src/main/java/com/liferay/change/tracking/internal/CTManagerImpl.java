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
import com.liferay.change.tracking.CTManager;
import com.liferay.change.tracking.exception.CTEntryException;
import com.liferay.change.tracking.exception.CTException;
import com.liferay.change.tracking.exception.DuplicateCTEntryException;
import com.liferay.change.tracking.internal.util.ChangeTrackingThreadLocal;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.model.CTEntryBag;
import com.liferay.change.tracking.service.CTEntryBagLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.util.comparator.CTEntryCreateDateComparator;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = CTManager.class)
public class CTManagerImpl implements CTManager {

	public Optional<CTEntryBag> addRelatedCTEntry(
		long userId, CTEntry ownerCTEntry, CTEntry relatedCTEntry) {

		Optional<CTCollection> activeCTCollectionOptional =
			_ctEngineManager.getActiveCTCollectionOptional(userId);

		if (!activeCTCollectionOptional.isPresent()) {
			return Optional.empty();
		}

		long activeCTCollectionId = activeCTCollectionOptional.map(
			CTCollection::getCtCollectionId
		).orElse(
			0L
		);

		try {
			CTEntryBag ctEntryBag = TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> _createCTEntryBag(
					userId, ownerCTEntry, relatedCTEntry,
					activeCTCollectionId));

			return Optional.of(ctEntryBag);
		}
		catch (Throwable t) {
			_log.error("Unable to create change tracking entry bag", t);

			return Optional.empty();
		}
	}

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
		long userId, long classNameId, long classPK) {

		long companyId = _getCompanyId(userId);

		if (companyId <= 0) {
			return Optional.empty();
		}

		Optional<CTCollection> ctCollectionOptional =
			_ctEngineManager.getActiveCTCollectionOptional(userId);

		long ctCollectionId = ctCollectionOptional.map(
			CTCollection::getCtCollectionId
		).orElse(
			0L
		);

		CTEntry ctEntry = _getCTentry(
			companyId, ctCollectionId, classNameId, classPK);

		return Optional.ofNullable(ctEntry);
	}

	@Override
	public Optional<CTEntry> getLatestModelChangeCTEntryOptional(
		long userId, long resourcePrimKey) {

		long companyId = _getCompanyId(userId);

		if (companyId <= 0) {
			return Optional.empty();
		}

		if (!_ctEngineManager.isChangeTrackingEnabled(companyId)) {
			return Optional.empty();
		}

		QueryDefinition<CTEntry> queryDefinition = new QueryDefinition<>();

		queryDefinition.setEnd(1);
		queryDefinition.setOrderByComparator(new CTEntryCreateDateComparator());
		queryDefinition.setStart(0);

		List<CTEntry> ctEntries = getModelChangeCTEntries(
			userId, resourcePrimKey, queryDefinition);

		if (ListUtil.isEmpty(ctEntries)) {
			return Optional.empty();
		}

		return Optional.of(ctEntries.get(0));
	}

	@Override
	public List<CTEntry> getModelChangeCTEntries(
		long userId, long resourcePrimKey) {

		QueryDefinition<CTEntry> queryDefinition = new QueryDefinition<>();

		queryDefinition.setOrderByComparator(
			new CTEntryCreateDateComparator(true));

		return getModelChangeCTEntries(
			userId, resourcePrimKey, queryDefinition);
	}

	@Override
	public List<CTEntry> getModelChangeCTEntries(
		long userId, long resourcePrimKey,
		QueryDefinition<CTEntry> queryDefinition) {

		long companyId = _getCompanyId(userId);

		if (companyId <= 0) {
			return Collections.emptyList();
		}

		if (!_ctEngineManager.isChangeTrackingEnabled(companyId)) {
			return Collections.emptyList();
		}

		Optional<CTCollection> ctCollectionOptional =
			_ctEngineManager.getActiveCTCollectionOptional(userId);

		long ctCollectionId = ctCollectionOptional.map(
			CTCollection::getCtCollectionId
		).orElse(
			0L
		);

		return _ctEntryLocalService.fetchCTEntries(
			ctCollectionId, resourcePrimKey, queryDefinition);
	}

	@Override
	public Optional<CTEntryBag> getModelChangeCTEntryBagOptional(
		long userId, long classNameId, long classPK) {

		Optional<CTEntry> ctEntryOptional = getModelChangeCTEntryOptional(
			userId, classNameId, classPK);

		if (!ctEntryOptional.isPresent()) {
			return Optional.empty();
		}

		long ctEntryId = ctEntryOptional.map(
			CTEntry::getCtEntryId
		).get();

		Optional<CTCollection> ctCollectionOptional =
			_ctEngineManager.getActiveCTCollectionOptional(userId);

		long ctCollectionId = ctCollectionOptional.map(
			CTCollection::getCtCollectionId
		).orElse(
			0L
		);

		CTEntryBag ctEntryBag = _ctEntryBagLocalService.fetchLatestCTEntryBag(
			ctEntryId, ctCollectionId);

		if (ctEntryBag != null) {
			return Optional.of(ctEntryBag);
		}

		ctCollectionOptional =
			_ctEngineManager.getProductionCTCollectionOptional(userId);

		ctCollectionId = ctCollectionOptional.map(
			CTCollection::getCtCollectionId
		).orElse(
			0L
		);

		ctEntryBag = _ctEntryBagLocalService.fetchLatestCTEntryBag(
			ctEntryId, ctCollectionId);

		return Optional.ofNullable(ctEntryBag);
	}

	@Override
	public Optional<CTEntry> getModelChangeCTEntryOptional(
		long userId, long classNameId, long classPK) {

		Optional<CTEntry> ctEntryOptional =
			getActiveCTCollectionCTEntryOptional(userId, classNameId, classPK);

		if (ctEntryOptional.isPresent()) {
			return ctEntryOptional;
		}

		return getProductionCTCollectionCTEntryOptional(
			userId, classNameId, classPK);
	}

	@Override
	public Optional<CTEntry> getProductionCTCollectionCTEntryOptional(
		long userId, long classNameId, long classPK) {

		long companyId = _getCompanyId(userId);

		if (companyId <= 0) {
			return Optional.empty();
		}

		Optional<CTCollection> ctCollectionOptional =
			_ctEngineManager.getProductionCTCollectionOptional(companyId);

		long ctCollectionId = ctCollectionOptional.map(
			CTCollection::getCtCollectionId
		).orElse(
			0L
		);

		CTEntry ctEntry = _getCTentry(
			companyId, ctCollectionId, classNameId, classPK);

		return Optional.ofNullable(ctEntry);
	}

	@Override
	public boolean isModelUpdateInProgress() {
		return ChangeTrackingThreadLocal.isModelUpdateInProgress();
	}

	@Override
	public Optional<CTEntry> registerModelChange(
			long userId, long classNameId, long classPK, long resourcePrimKey,
			int changeType)
		throws CTException {

		return registerModelChange(
			userId, classNameId, classPK, resourcePrimKey, changeType, false);
	}

	@Override
	public Optional<CTEntry> registerModelChange(
			long userId, long classNameId, long classPK, long resourcePrimKey,
			int changeType, boolean force)
		throws CTException {

		long companyId = _getCompanyId(userId);

		if (companyId <= 0) {
			return Optional.empty();
		}

		if (!_ctEngineManager.isChangeTrackingEnabled(companyId) ||
			!_ctEngineManager.isChangeTrackingSupported(
				companyId, classNameId)) {

			return Optional.empty();
		}

		Optional<CTCollection> ctCollectionOptional =
			_ctEngineManager.getActiveCTCollectionOptional(userId);

		if (!ctCollectionOptional.isPresent()) {
			return Optional.empty();
		}

		CTCollection ctCollection = ctCollectionOptional.get();

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAttribute("force", force);

			return Optional.of(
				_ctEntryLocalService.addCTEntry(
					userId, classNameId, classPK, resourcePrimKey, changeType,
					ctCollection.getCtCollectionId(), serviceContext));
		}
		catch (DuplicateCTEntryException dctee) {
			StringBundler sb = new StringBundler(8);

			sb.append("Duplicate CTEntry with class name ID ");
			sb.append(classNameId);
			sb.append(", class PK ");
			sb.append(classPK);
			sb.append(", and resource primary key ");
			sb.append(resourcePrimKey);
			sb.append(" in change tracking collection ");
			sb.append(ctCollection.getCtCollectionId());

			throw new CTEntryException(
				0L, companyId, userId, classNameId, classPK, resourcePrimKey,
				ctCollection.getCtCollectionId(), sb.toString(), dctee);
		}
		catch (PortalException pe) {
			StringBundler sb = new StringBundler(8);

			sb.append("Unable to register model change  with class name ID ");
			sb.append(classNameId);
			sb.append(", class PK ");
			sb.append(classPK);
			sb.append(", and resource primary key ");
			sb.append(resourcePrimKey);
			sb.append(" in change tracking collection ");
			sb.append(ctCollection.getCtCollectionId());

			throw new CTException(companyId, sb.toString(), pe);
		}
	}

	@Override
	public Optional<CTEntry> unregisterModelChange(
		long userId, long classNameId, long classPK) {

		long companyId = _getCompanyId(userId);

		if (companyId <= 0) {
			return Optional.empty();
		}

		if (!_ctEngineManager.isChangeTrackingEnabled(companyId) ||
			!_ctEngineManager.isChangeTrackingSupported(
				companyId, classNameId)) {

			return Optional.empty();
		}

		Optional<CTEntry> ctEntryOptional = getModelChangeCTEntryOptional(
			userId, classNameId, classPK);

		return ctEntryOptional.map(
			ctEntry -> _ctEntryLocalService.deleteCTEntry(ctEntry));
	}

	private boolean _containsResource(
		CTEntryBag ctEntryBag, long resourcePrimKey) {

		List<CTEntry> relatedCTEntries = ctEntryBag.getRelatedCTEntries();

		Stream<CTEntry> relatedCTEntriesStream = relatedCTEntries.stream();

		if (relatedCTEntriesStream.anyMatch(
				ctEntry -> ctEntry.getResourcePrimKey() == resourcePrimKey)) {

			return true;
		}

		return false;
	}

	private CTEntryBag _copyCTEntryBag(CTEntryBag ctEntryBag)
		throws PortalException {

		CTEntryBag ctEntryBagCopy = _ctEntryBagLocalService.createCTEntryBag(
			PrincipalThreadLocal.getUserId(), ctEntryBag.getOwnerCTEntryId(),
			ctEntryBag.getCtCollectionId(), new ServiceContext());

		_ctEntryLocalService.addCTEntryBagCTEntries(
			ctEntryBagCopy.getCtEntryBagId(),
			_ctEntryBagLocalService.getCTEntryPrimaryKeys(
				ctEntryBag.getCtEntryBagId()));

		return ctEntryBagCopy;
	}

	private CTEntryBag _createCTEntryBag(
			long userId, CTEntry ownerCTEntry, CTEntry relatedCTEntry,
			long activeCTCollectionId)
		throws PortalException {

		CTEntryBag ctEntryBag = _ctEntryBagLocalService.fetchLatestCTEntryBag(
			ownerCTEntry.getCtEntryId(), activeCTCollectionId);

		if (ctEntryBag == null) {
			ctEntryBag = _ctEntryBagLocalService.createCTEntryBag(
				userId, ownerCTEntry.getCtEntryId(), activeCTCollectionId,
				new ServiceContext());

			_ctEntryBagLocalService.addCTEntry(ctEntryBag, relatedCTEntry);
		}
		else if (!_containsResource(
					ctEntryBag, relatedCTEntry.getResourcePrimKey())) {

			_ctEntryBagLocalService.addCTEntry(ctEntryBag, relatedCTEntry);
		}
		else {
			_updateCTEntryInCTEntryBag(
				_copyCTEntryBag(ctEntryBag), relatedCTEntry);
		}

		return ctEntryBag;
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

	private CTEntry _getCTentry(
		long companyId, long ctCollectionId, long classNameId, long classPK) {

		if (!_ctEngineManager.isChangeTrackingEnabled(companyId) ||
			!_ctEngineManager.isChangeTrackingSupported(
				companyId, classNameId)) {

			return null;
		}

		return _ctEntryLocalService.fetchCTEntry(
			ctCollectionId, classNameId, classPK);
	}

	private void _updateCTEntryInCTEntryBag(
		CTEntryBag ctEntryBag, CTEntry ctEntry) {

		List<CTEntry> relatedCTEntries = ctEntryBag.getRelatedCTEntries();

		Stream<CTEntry> relatedCTEntriesStream = relatedCTEntries.stream();

		Optional<CTEntry> previousCTEntryOptional =
			relatedCTEntriesStream.filter(
				relatedCTEntry ->
					relatedCTEntry.getResourcePrimKey() ==
						ctEntry.getResourcePrimKey()
			).findFirst();

		previousCTEntryOptional.ifPresent(
			previousCTEntry -> {
				_ctEntryBagLocalService.removeCTEntry(
					ctEntryBag, previousCTEntry);

				_ctEntryBagLocalService.addCTEntry(ctEntryBag, ctEntry);
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(CTManagerImpl.class);

	@Reference
	private CTEngineManager _ctEngineManager;

	@Reference
	private CTEntryBagLocalService _ctEntryBagLocalService;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	private final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private UserLocalService _userLocalService;

}