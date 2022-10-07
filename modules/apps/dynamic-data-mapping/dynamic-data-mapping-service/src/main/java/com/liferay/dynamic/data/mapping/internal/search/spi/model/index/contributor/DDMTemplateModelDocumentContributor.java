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

package com.liferay.dynamic.data.mapping.internal.search.spi.model.index.contributor;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateVersion;
import com.liferay.dynamic.data.mapping.security.permission.DDMPermissionSupport;
import com.liferay.dynamic.data.mapping.service.DDMTemplateVersionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	property = "indexer.class.name=com.liferay.dynamic.data.mapping.model.DDMTemplate",
	service = ModelDocumentContributor.class
)
public class DDMTemplateModelDocumentContributor
	implements ModelDocumentContributor<DDMTemplate> {

	@Override
	public void contribute(Document document, DDMTemplate ddmTemplate) {
		document.addKeyword(Field.CLASS_NAME_ID, ddmTemplate.getClassNameId());
		document.addKeyword(Field.CLASS_PK, ddmTemplate.getClassPK());
		document.addKeyword("language", ddmTemplate.getLanguage());
		document.addKeyword("mode", ddmTemplate.getMode());
		document.addKeyword(
			"resourceClassNameId", ddmTemplate.getResourceClassNameId());

		try {
			DDMTemplateVersion templateVersion =
				ddmTemplateVersionLocalService.getTemplateVersion(
					ddmTemplate.getTemplateId(), ddmTemplate.getVersion());

			document.addKeyword(Field.STATUS, templateVersion.getStatus());
			document.addKeyword(Field.VERSION, templateVersion.getVersion());
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		try {
			document.addKeyword(
				"resourcePermissionName",
				_ddmPermissionSupport.getTemplateModelResourceName(
					ddmTemplate.getResourceClassNameId()));
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		document.addKeyword("type", ddmTemplate.getType());
		document.addLocalizedText(
			Field.DESCRIPTION,
			_localization.populateLocalizationMap(
				ddmTemplate.getDescriptionMap(),
				ddmTemplate.getDefaultLanguageId(), ddmTemplate.getGroupId()));
		document.addLocalizedText(
			Field.NAME,
			_localization.populateLocalizationMap(
				ddmTemplate.getNameMap(), ddmTemplate.getDefaultLanguageId(),
				ddmTemplate.getGroupId()));
	}

	protected String[] getLanguageIds(
		String defaultLanguageId, String content) {

		String[] languageIds = _localization.getAvailableLanguageIds(content);

		if (languageIds.length == 0) {
			languageIds = new String[] {defaultLanguageId};
		}

		return languageIds;
	}

	@Reference
	protected DDMTemplateVersionLocalService ddmTemplateVersionLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		DDMTemplateModelDocumentContributor.class);

	@Reference
	private DDMPermissionSupport _ddmPermissionSupport;

	@Reference
	private Localization _localization;

}