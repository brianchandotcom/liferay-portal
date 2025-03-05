/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.site.cms.site.initializer.internal.configuration.CMSSiteInitializerConfiguration;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sam Ziemer
 */
public class StructuresSectionDisplayContext extends BaseSectionDisplayContext {

	public StructuresSectionDisplayContext(
		CMSSiteInitializerConfiguration cmsSiteInitializerConfiguration,
		HttpServletRequest httpServletRequest) {

		super(cmsSiteInitializerConfiguration, httpServletRequest);
	}

	@Override
	public String getAPIURL() {
		return "/o/object-admin/v1.0/object-definitions?filter=" +
			"objectFolderExternalReferenceCode eq 'L_CMS_CONTENT_STRUCTURES' " +
				"or objectFolderExternalReferenceCode eq 'L_CMS_FILE_TYPES'";
	}

	@Override
	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(_getHref("L_CMS_CONTENT_STRUCTURES"));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "content"));
			}
		).addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(_getHref("L_CMS_FILE_TYPES"));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "file"));
			}
		).build();
	}

	private String _getHref(String objectFolderExternalReferenceCode) {
		try {
			return HttpComponentsUtil.addParameters(
				PortalUtil.getLayoutFullURL(
					LayoutLocalServiceUtil.getLayoutByFriendlyURL(
						themeDisplay.getScopeGroupId(), false,
						"/structure-builder"),
					themeDisplay),
				"objectFolderExternalReferenceCode",
				objectFolderExternalReferenceCode);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StructuresSectionDisplayContext.class);

}