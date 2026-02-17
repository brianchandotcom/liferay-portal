/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.fragment.renderer;

import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
public abstract class BaseBulkActionTaskComponentSectionFragmentRenderer
	extends BaseComponentSectionFragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "bulk-actions";
	}

	protected int getBulkActionTaskItemsCount(
			String executionStatus, HttpServletRequest httpServletRequest)
		throws PortalException {

		ObjectEntry cmsBulkActionTaskObjectEntry =
			(ObjectEntry)httpServletRequest.getAttribute(
				InfoDisplayWebKeys.INFO_ITEM);

		if (cmsBulkActionTaskObjectEntry == null) {
			return -1;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		ObjectDefinition objectDefinition =
			objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_CMS_BULK_ACTION_TASK", themeDisplay.getCompanyId());

		return 5;
	}

	@Reference
	protected ObjectDefinitionLocalService objectDefinitionLocalService;

	@Reference
	protected ObjectEntryLocalService objectEntryLocalService;

	@Reference
	protected ObjectRelationshipLocalService objectRelationshipLocalService;

}