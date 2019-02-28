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

package com.liferay.dynamic.data.mapping.change.tracking.internal.service;

import com.liferay.change.tracking.CTEngineManager;
import com.liferay.change.tracking.CTManager;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.exception.CTEntryException;
import com.liferay.change.tracking.exception.CTException;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceWrapper;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class CTDDMStructureLocalServiceWrapper
	extends DDMStructureLocalServiceWrapper {

	public CTDDMStructureLocalServiceWrapper() {
		super(null);
	}

	public CTDDMStructureLocalServiceWrapper(
		DDMStructureLocalService ddmStructureLocalService) {

		super(ddmStructureLocalService);
	}

	@Override
	public DDMStructure addStructure(
			long userId, long groupId, long parentStructureId, long classNameId,
			String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, DDMForm ddmForm,
			DDMFormLayout ddmFormLayout, String storageType, int type,
			ServiceContext serviceContext)
		throws PortalException {

		DDMStructure ddmStructure = _ctManager.executeModelUpdate(
			() -> super.addStructure(
				userId, groupId, parentStructureId, classNameId, structureKey,
				nameMap, descriptionMap, ddmForm, ddmFormLayout, storageType,
				type, serviceContext));

		DDMStructureVersion ddmStructureVersion =
			ddmStructure.getStructureVersion();

		_registerChange(
			ddmStructureVersion, CTConstants.CT_CHANGE_TYPE_ADDITION);

		return ddmStructure;
	}

	@Override
	public DDMStructure fetchStructure(long structureId) {
		DDMStructure ddmStructure = super.fetchStructure(structureId);

		if (_isRetrievable(ddmStructure)) {
			return _populateDDMStructure(ddmStructure);
		}

		return null;
	}

	@Override
	public DDMStructure fetchStructure(
		long groupId, long classNameId, String structureKey,
		boolean includeAncestorStructures) {

		DDMStructure ddmStructure = super.fetchStructure(
			groupId, classNameId, structureKey, includeAncestorStructures);

		if (_isRetrievable(ddmStructure)) {
			return _populateDDMStructure(ddmStructure);
		}

		return null;
	}

	@Override
	public List<DDMStructure> getStructures(
		long companyId, long[] groupIds, long classNameId, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {

		if (!_ctEngineManager.isChangeTrackingEnabled(companyId)) {
			return super.getStructures(
				companyId, groupIds, classNameId, start, end,
				orderByComparator);
		}

		List<DDMStructure> structures = super.getStructures(
			groupIds, classNameId, orderByComparator);

		Stream<DDMStructure> stream = structures.stream();

		return stream.filter(
			structure -> {
				Optional<CTEntry> ctEntryOptional =
					_ctManager.getLatestModelChangeCTEntryOptional(
						PrincipalThreadLocal.getUserId(),
						structure.getStructureId());

				return ctEntryOptional.isPresent();
			}
		).skip(
			start
		).limit(
			end
		).map(
			this::_populateDDMStructure
		).collect(
			Collectors.toList()
		);
	}

	@Override
	public List<DDMStructure> getStructures(long[] groupIds, long classNameId) {
		List<DDMStructure> structures = super.getStructures(
			groupIds, classNameId);

		if (ListUtil.isNotEmpty(structures)) {
			DDMStructure ddmStructure = structures.get(0);

			if (!_ctEngineManager.isChangeTrackingEnabled(
					ddmStructure.getCompanyId())) {

				return structures;
			}
		}

		Stream<DDMStructure> stream = structures.stream();

		return stream.filter(
			structure -> {
				if (_isBasicWebContent(structure)) {
					return true;
				}

				Optional<CTEntry> ctEntryOptional =
					_ctManager.getLatestModelChangeCTEntryOptional(
						PrincipalThreadLocal.getUserId(),
						structure.getStructureId());

				return ctEntryOptional.isPresent();
			}
		).map(
			this::_populateDDMStructure
		).collect(
			Collectors.toList()
		);
	}

	@Override
	public DDMStructure updateStructure(
			long userId, long structureId, long parentStructureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			DDMForm ddmForm, DDMFormLayout ddmFormLayout,
			ServiceContext serviceContext)
		throws PortalException {

		DDMStructure ddmStructure = _ctManager.executeModelUpdate(
			() -> super.updateStructure(
				userId, structureId, parentStructureId, nameMap, descriptionMap,
				ddmForm, ddmFormLayout, serviceContext));

		DDMStructureVersion ddmStructureVersion =
			ddmStructure.getStructureVersion();

		_registerChange(
			ddmStructureVersion, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return ddmStructure;
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		// This is needed because of synchronization

	}

	private boolean _isBasicWebContent(DDMStructure ddmStructure) {
		long journalClassNameId = _portal.getClassNameId(JournalArticle.class);

		if ((ddmStructure.getClassNameId() == journalClassNameId) &&
			"BASIC-WEB-CONTENT".equals(ddmStructure.getStructureKey())) {

			return true;
		}

		return false;
	}

	private boolean _isRetrievable(DDMStructure ddmStructure) {
		if (ddmStructure == null) {
			return false;
		}

		if (!_ctEngineManager.isChangeTrackingEnabled(
				ddmStructure.getCompanyId()) ||
			_isBasicWebContent(ddmStructure)) {

			return true;
		}

		Optional<CTEntry> ctEntryOptional =
			_ctManager.getLatestModelChangeCTEntryOptional(
				PrincipalThreadLocal.getUserId(),
				ddmStructure.getStructureId());

		return ctEntryOptional.isPresent();
	}

	private DDMStructure _populateDDMStructure(DDMStructure ddmStructure) {
		Optional<CTEntry> ctEntryOptional =
			_ctManager.getLatestModelChangeCTEntryOptional(
				PrincipalThreadLocal.getUserId(),
				ddmStructure.getStructureId());

		if (!ctEntryOptional.isPresent()) {
			return ddmStructure;
		}

		Optional<DDMStructureVersion> ddmStructureVersionOptional =
			ctEntryOptional.map(
				CTEntry::getClassPK
			).map(
				_ddmStructureVersionLocalService::fetchDDMStructureVersion
			);

		if (!ddmStructureVersionOptional.isPresent()) {
			return ddmStructure;
		}

		DDMStructureVersion ddmStructureVersion =
			ddmStructureVersionOptional.get();

		ddmStructure.setStructureId(ddmStructureVersion.getStructureId());
		ddmStructure.setGroupId(ddmStructureVersion.getGroupId());
		ddmStructure.setCompanyId(ddmStructureVersion.getCompanyId());
		ddmStructure.setUserId(ddmStructureVersion.getUserId());
		ddmStructure.setUserName(ddmStructureVersion.getUserName());
		ddmStructure.setModifiedDate(ddmStructureVersion.getCreateDate());
		ddmStructure.setParentStructureId(
			ddmStructureVersion.getParentStructureId());
		ddmStructure.setVersion(ddmStructureVersion.getVersion());
		ddmStructure.setName(ddmStructureVersion.getName());
		ddmStructure.setDescription(ddmStructureVersion.getDescription());
		ddmStructure.setDefinition(ddmStructureVersion.getDefinition());
		ddmStructure.setStorageType(ddmStructureVersion.getStorageType());
		ddmStructure.setType(ddmStructureVersion.getType());

		return ddmStructure;
	}

	private void _registerChange(
			DDMStructureVersion ddmStructureVersion, int changeType)
		throws CTException {

		_registerChange(ddmStructureVersion, changeType, false);
	}

	private void _registerChange(
			DDMStructureVersion ddmStructureVersion, int changeType,
			boolean force)
		throws CTException {

		if (ddmStructureVersion == null) {
			return;
		}

		try {
			_ctManager.registerModelChange(
				PrincipalThreadLocal.getUserId(),
				_portal.getClassNameId(DDMStructureVersion.class.getName()),
				ddmStructureVersion.getStructureVersionId(),
				ddmStructureVersion.getStructureId(), changeType, force);
		}
		catch (CTException cte) {
			if (cte instanceof CTEntryException) {
				if (_log.isWarnEnabled()) {
					_log.warn(cte.getMessage());
				}
			}
			else {
				throw cte;
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTDDMStructureLocalServiceWrapper.class);

	@Reference
	private CTEngineManager _ctEngineManager;

	@Reference
	private CTManager _ctManager;

	@Reference
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

	@Reference
	private Portal _portal;

}