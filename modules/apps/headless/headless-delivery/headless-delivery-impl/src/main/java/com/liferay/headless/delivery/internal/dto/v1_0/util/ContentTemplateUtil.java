/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.util;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.headless.delivery.dto.v1_0.ContentTemplate;
import com.liferay.headless.delivery.dto.v1_0.util.CreatorUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.GroupUtil;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

/**
 * @author Javier Gamarra
 */
public class ContentTemplateUtil {

	public static ContentTemplate toContentTemplate(
		DDMTemplate ddmTemplate, DTOConverterContext dtoConverterContext,
		GroupLocalService groupLocalService, Portal portal,
		UserLocalService userLocalService) {

		Group group = groupLocalService.fetchGroup(ddmTemplate.getGroupId());

		return new ContentTemplate() {
			{
				actions = dtoConverterContext.getActions();
				assetLibraryKey = GroupUtil.getAssetLibraryKey(group);
				availableLanguages = LocaleUtil.toW3cLanguageIds(
					ddmTemplate.getAvailableLanguageIds());
				contentStructureId = ddmTemplate.getClassPK();
				creator = CreatorUtil.toCreator(
					dtoConverterContext, portal,
					userLocalService.fetchUser(ddmTemplate.getUserId()));
				dateCreated = ddmTemplate.getCreateDate();
				dateModified = ddmTemplate.getModifiedDate();
				description = ddmTemplate.getDescription(
					dtoConverterContext.getLocale());
				description_i18n = LocalizedMapUtil.getI18nMap(
					dtoConverterContext.isAcceptAllLanguages(),
					ddmTemplate.getDescriptionMap());
				id = ddmTemplate.getTemplateKey();
				name = ddmTemplate.getName(dtoConverterContext.getLocale());
				name_i18n = LocalizedMapUtil.getI18nMap(
					dtoConverterContext.isAcceptAllLanguages(),
					ddmTemplate.getNameMap());
				programmingLanguage = ddmTemplate.getLanguage();
				siteId = GroupUtil.getSiteId(group);
				templateScript = ddmTemplate.getScript();
			}
		};
	}

}