/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.fragment.renderer;

import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.site.cms.site.initializer.internal.display.context.ViewSpaceMembersSummarySectionDisplayContext;
import com.liferay.site.cms.site.initializer.internal.util.InfoItemUtil;

import jakarta.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(service = FragmentRenderer.class)
public class ViewSpaceMembersSummaryJSPSectionFragmentRenderer
	extends BaseJSPSectionFragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "sections";
	}

	@Override
	protected Object getDisplayContext(HttpServletRequest httpServletRequest) {
		return new ViewSpaceMembersSummarySectionDisplayContext(
			InfoItemUtil.getGroupId(httpServletRequest), httpServletRequest,
			_language, _depotEntryLocalService, _groupLocalService,
			_userGroupLocalService, _userLocalService);
	}

	@Override
	protected String getJSPPath() {
		return "/view_space_members_summary.jsp";
	}

	@Override
	protected String getLabelKey() {
		return "space-members-summary";
	}

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
