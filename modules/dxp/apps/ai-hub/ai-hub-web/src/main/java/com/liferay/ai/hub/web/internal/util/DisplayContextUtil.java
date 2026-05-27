/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.web.internal.util;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectEntryServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Carolina Barbosa
 */
public class DisplayContextUtil {

	public static String getAIHubURL(ThemeDisplay themeDisplay)
		throws Exception {

		Company company = themeDisplay.getCompany();
		Group group = themeDisplay.getScopeGroup();

		return StringBundler.concat(
			company.getPortalURL(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			"/web", group.getFriendlyURL());
	}

	public static boolean isReadOnly(
			long companyId, String externalReferenceCode,
			String objectDefinitionExternalReferenceCode)
		throws PortalException {

		if (Validator.isNull(externalReferenceCode)) {
			return false;
		}

		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.
				getObjectDefinitionByExternalReferenceCode(
					objectDefinitionExternalReferenceCode, companyId);

		return !ObjectEntryServiceUtil.hasModelResourcePermission(
			ObjectEntryServiceUtil.getObjectEntry(
				externalReferenceCode, GroupConstants.DEFAULT_PARENT_GROUP_ID,
				objectDefinition.getObjectDefinitionId()),
			ActionKeys.UPDATE);
	}

}