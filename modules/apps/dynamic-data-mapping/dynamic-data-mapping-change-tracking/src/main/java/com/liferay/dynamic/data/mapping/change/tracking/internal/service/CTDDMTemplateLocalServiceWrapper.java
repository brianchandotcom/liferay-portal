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
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateVersion;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceWrapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.Portal;

import java.io.File;

import java.util.Locale;
import java.util.Map;
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

		DDMTemplateVersion ddmTemplateVersion =
			ddmTemplate.getTemplateVersion();

		_registerChange(
			ddmTemplateVersion, CTConstants.CT_CHANGE_TYPE_ADDITION);

		return ddmTemplate;
	}

	@Override
	public DDMTemplate fetchTemplate(long templateId) {
		DDMTemplate ddmTemplate = super.fetchTemplate(templateId);

		if (_isBasicWebContent(ddmTemplate)) {
			return ddmTemplate;
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
			return ddmTemplate;
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

		DDMTemplateVersion ddmTemplateVersion =
			ddmTemplate.getTemplateVersion();

		_registerChange(
			ddmTemplateVersion, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return ddmTemplate;
	}

	@Reference(unbind = "-")
	protected void setDDMTemplateLocalService(
		DDMTemplateLocalService ddmTemplateLocalService) {

		// this is needed because of synchronisation

	}

	private boolean _isBasicWebContent(DDMTemplate ddmTemplate) {
		if ("BASIC-WEB-CONTENT".equals(ddmTemplate.getTemplateKey())) {
			return true;
		}

		return false;
	}

	private boolean _isRetrievable(DDMTemplate ddmTemplate) {
		if (ddmTemplate == null) {
			return false;
		}

		if (_ctEngineManager.isChangeTrackingEnabled(
				ddmTemplate.getCompanyId()) ||
			_isBasicWebContent(ddmTemplate)) {

			return true;
		}

		Optional<CTEntry> ctEntryOptional =
			_ctManager.getLatestModelChangeCTEntryOptional(
				PrincipalThreadLocal.getUserId(), ddmTemplate.getTemplateId());

		return ctEntryOptional.isPresent();
	}

	private void _registerChange(
			DDMTemplateVersion ddmTemplateVersion, int changeType)
		throws CTException {

		_registerChange(ddmTemplateVersion, changeType, false);
	}

	private void _registerChange(
			DDMTemplateVersion ddmTemplateVersion, int changeType,
			boolean force)
		throws CTException {

		if (ddmTemplateVersion == null) {
			return;
		}

		try {
			_ctManager.registerModelChange(
				PrincipalThreadLocal.getUserId(),
				_portal.getClassNameId(DDMTemplateVersion.class.getName()),
				ddmTemplateVersion.getTemplateVersionId(),
				ddmTemplateVersion.getTemplateId(), changeType, force);
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
		CTDDMTemplateLocalServiceWrapper.class);

	@Reference
	private CTEngineManager _ctEngineManager;

	@Reference
	private CTManager _ctManager;

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private Portal _portal;

}