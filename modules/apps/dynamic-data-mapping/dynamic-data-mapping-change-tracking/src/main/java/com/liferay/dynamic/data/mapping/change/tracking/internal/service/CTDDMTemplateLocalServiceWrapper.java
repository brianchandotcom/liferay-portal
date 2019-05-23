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

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.engine.CTEngineManager;
import com.liferay.change.tracking.engine.CTManager;
import com.liferay.change.tracking.engine.exception.CTEngineException;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateVersion;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceWrapper;
import com.liferay.dynamic.data.mapping.service.DDMTemplateVersionLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.File;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class CTDDMTemplateLocalServiceWrapper
	extends DDMTemplateLocalServiceWrapper {

	public CTDDMTemplateLocalServiceWrapper() {
		super(null);
	}

	public CTDDMTemplateLocalServiceWrapper(
		DDMTemplateLocalService ddmTemplateLocalService) {

		super(ddmTemplateLocalService);
	}

	@Override
	public DDMTemplate addTemplate(
			long userId, long groupId, long classNameId, long classPK,
			long resourceClassNameId, String templateKey,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String type, String mode, String language, String script,
			boolean cacheable, boolean smallImage, String smallImageURL,
			File smallImageFile, ServiceContext serviceContext)
		throws PortalException {

		DDMTemplate ddmTemplate = _ctManager.executeModelUpdate(
			() -> super.addTemplate(
				userId, groupId, classNameId, classPK, resourceClassNameId,
				templateKey, nameMap, descriptionMap, type, mode, language,
				script, cacheable, smallImage, smallImageURL, smallImageFile,
				serviceContext));

		if (!_isClassNameChangeTracked(classNameId)) {
			return ddmTemplate;
		}

		DDMTemplateVersion ddmTemplateVersion =
			ddmTemplate.getTemplateVersion();

		_registerChange(
			ddmTemplateVersion, CTConstants.CT_CHANGE_TYPE_ADDITION, true);

		return ddmTemplate;
	}

	@Override
	public DDMTemplate fetchDDMTemplate(long templateId) {
		DDMTemplate ddmTemplate = super.fetchDDMTemplate(templateId);

		if (_isRetrievable(ddmTemplate)) {
			return _populateDDMTemplate(ddmTemplate);
		}

		return null;
	}

	@Override
	public DDMTemplate fetchTemplate(long templateId) {
		DDMTemplate ddmTemplate = super.fetchTemplate(templateId);

		if (_isRetrievable(ddmTemplate)) {
			return _populateDDMTemplate(ddmTemplate);
		}

		return null;
	}

	@Override
	public DDMTemplate fetchTemplate(
		long groupId, long classNameId, String templateKey,
		boolean includeAncestorTemplates) {

		DDMTemplate ddmTemplate = super.fetchTemplate(
			groupId, classNameId, templateKey, includeAncestorTemplates);

		if (_isRetrievable(ddmTemplate)) {
			return _populateDDMTemplate(ddmTemplate);
		}

		return null;
	}

	@Override
	public DDMTemplate updateTemplate(
			long userId, long templateId, long classPK,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String type, String mode, String language, String script,
			boolean cacheable, boolean smallImage, String smallImageURL,
			File smallImageFile, ServiceContext serviceContext)
		throws PortalException {

		DDMTemplate ddmTemplate = _ctManager.executeModelUpdate(
			() -> super.updateTemplate(
				userId, templateId, classPK, nameMap, descriptionMap, type,
				mode, language, script, cacheable, smallImage, smallImageURL,
				smallImageFile, serviceContext));

		if (!_isClassNameChangeTracked(ddmTemplate.getClassNameId())) {
			return ddmTemplate;
		}

		DDMTemplateVersion ddmTemplateVersion =
			ddmTemplate.getTemplateVersion();

		_registerChange(
			ddmTemplateVersion, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return ddmTemplate;
	}

	@Reference(unbind = "-")
	protected void setDDMTemplateLocalService(
		DDMTemplateLocalService ddmTemplateLocalService) {

		// Needed for synchronization

	}

	private boolean _isBasicWebContent(DDMTemplate ddmTemplate) {
		if (Objects.equals(ddmTemplate.getTemplateKey(), "BASIC-WEB-CONTENT")) {
			return true;
		}

		return false;
	}

	private boolean _isClassNameChangeTracked(long classNameId) {
		String className;

		try {
			className = _portal.getClassName(classNameId);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get class name from id " + classNameId, e);
			}

			return false;
		}

		if (ArrayUtil.contains(_CHANGE_TRACKED_CLASS_NAMES, className)) {
			return true;
		}

		return false;
	}

	private boolean _isRetrievable(DDMTemplate ddmTemplate) {
		if (ddmTemplate == null) {
			return false;
		}

		if (!_ctEngineManager.isChangeTrackingEnabled(
				ddmTemplate.getCompanyId()) ||
			_isBasicWebContent(ddmTemplate)) {

			return true;
		}

		if (!_isClassNameChangeTracked(ddmTemplate.getClassNameId())) {
			return true;
		}

		Optional<CTEntry> ctEntryOptional =
			_ctManager.getLatestModelChangeCTEntryOptional(
				ddmTemplate.getCompanyId(), PrincipalThreadLocal.getUserId(),
				ddmTemplate.getTemplateId());

		return ctEntryOptional.isPresent();
	}

	private DDMTemplate _populateDDMTemplate(DDMTemplate ddmTemplate) {
		Optional<CTEntry> ctEntryOptional =
			_ctManager.getLatestModelChangeCTEntryOptional(
				ddmTemplate.getCompanyId(), PrincipalThreadLocal.getUserId(),
				ddmTemplate.getTemplateId());

		if (!ctEntryOptional.isPresent()) {
			return ddmTemplate;
		}

		Optional<DDMTemplateVersion> ddmTemplateVersionOptional =
			ctEntryOptional.map(
				CTEntry::getModelClassPK
			).map(
				_ddmTemplateVersionLocalService::fetchDDMTemplateVersion
			);

		if (!ddmTemplateVersionOptional.isPresent()) {
			return ddmTemplate;
		}

		DDMTemplateVersion ddmTemplateVersion =
			ddmTemplateVersionOptional.get();

		ddmTemplate.setVersionUserId(ddmTemplateVersion.getUserId());
		ddmTemplate.setVersionUserName(ddmTemplateVersion.getUserName());
		ddmTemplate.setClassNameId(ddmTemplateVersion.getClassNameId());
		ddmTemplate.setClassPK(ddmTemplateVersion.getClassPK());
		ddmTemplate.setVersion(ddmTemplateVersion.getVersion());
		ddmTemplate.setNameMap(ddmTemplateVersion.getNameMap());
		ddmTemplate.setDescriptionMap(ddmTemplateVersion.getDescriptionMap());
		ddmTemplate.setLanguage(ddmTemplateVersion.getLanguage());
		ddmTemplate.setScript(ddmTemplateVersion.getScript());

		return ddmTemplate;
	}

	private void _registerChange(
			DDMTemplateVersion ddmTemplateVersion, int changeType)
		throws CTEngineException {

		_registerChange(ddmTemplateVersion, changeType, false);
	}

	private void _registerChange(
			DDMTemplateVersion ddmTemplateVersion, int changeType,
			boolean force)
		throws CTEngineException {

		if (ddmTemplateVersion == null) {
			return;
		}

		try {
			_ctManager.registerModelChange(
				ddmTemplateVersion.getCompanyId(),
				PrincipalThreadLocal.getUserId(),
				_portal.getClassNameId(DDMTemplateVersion.class.getName()),
				ddmTemplateVersion.getTemplateVersionId(),
				ddmTemplateVersion.getTemplateId(), changeType, force);
		}
		catch (CTEngineException ctee) {
			if (ctee instanceof CTEngineException) {
				if (_log.isWarnEnabled()) {
					_log.warn(ctee.getMessage());
				}
			}
			else {
				throw ctee;
			}
		}
	}

	private static final String[] _CHANGE_TRACKED_CLASS_NAMES = {
		DDMStructure.class.getName(), JournalArticle.class.getName()
	};

	private static final Log _log = LogFactoryUtil.getLog(
		CTDDMTemplateLocalServiceWrapper.class);

	@Reference
	private CTEngineManager _ctEngineManager;

	@Reference
	private CTManager _ctManager;

	// Needed for synchronization

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	// Needed for synchronization

	@Reference
	private DDMTemplateVersionLocalService _ddmTemplateVersionLocalService;

	@Reference
	private Portal _portal;

}