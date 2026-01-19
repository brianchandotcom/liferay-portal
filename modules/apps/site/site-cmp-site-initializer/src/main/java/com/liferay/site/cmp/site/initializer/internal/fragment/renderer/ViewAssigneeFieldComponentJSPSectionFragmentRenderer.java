/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.fragment.renderer;

import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.cmp.site.initializer.internal.display.context.ViewAssigneeSectionDisplayContext;

import jakarta.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Franca
 */
@Component(service = FragmentRenderer.class)
public class ViewAssigneeFieldComponentJSPSectionFragmentRenderer
	extends BaseJSPSectionFragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "assignee-field";
	}

	@Override
	protected Object getDisplayContext(HttpServletRequest httpServletRequest)
		throws PortalException {

		Object object = httpServletRequest.getAttribute(
			InfoDisplayWebKeys.INFO_ITEM);

		if (!(object instanceof ObjectEntry)) {
			return null;
		}

		return new ViewAssigneeSectionDisplayContext(
			_classNameLocalService, httpServletRequest, _language,
			(ObjectEntry)object, _roleLocalService, _userLocalService,
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY));
	}

	@Override
	protected String getJSPPath() {
		return "/view_assignee.jsp";
	}

	@Override
	protected String getLabelKey() {
		return "assignee-field";
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private Language _language;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}