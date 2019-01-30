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
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.util.comparator.CTEntryCreateDateComparator;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ListUtil;

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
	public Optional<CTEntry> registerModelChange(
			long userId, long classNameId, long classPK, long resourcePrimKey,
			int changeType)
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
			return Optional.of(
				_ctEntryLocalService.addCTEntry(
					userId, classNameId, classPK, resourcePrimKey,
					ctCollection.getCtCollectionId(), changeType,
					new ServiceContext()));
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

		Optional<CTEntry> ctEntryOptional = getModelChangeCTEntryOptional(
			userId, classNameId, classPK);

		return ctEntryOptional.map(
			ctEntry -> _ctEntryLocalService.deleteCTEntry(ctEntry));
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

	private static final Log _log = LogFactoryUtil.getLog(CTManagerImpl.class);

	@Reference
	private CTEngineManager _ctEngineManager;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}