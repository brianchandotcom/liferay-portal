/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.fragment.renderer;

import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.business.type.ObjectFieldBusinessTypeRegistry;
import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.cmp.site.initializer.internal.display.context.ViewTaskInfoSummarySectionDisplayContext;
import com.liferay.site.cmp.site.initializer.internal.util.ObjectEntryUtil;

import jakarta.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Leite
 */
@Component(service = FragmentRenderer.class)
public class ViewTaskInfoSummaryJSPSectionFragmentRenderer
	extends BaseJSPSectionFragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "sections";
	}

	@Override
	protected Object getDisplayContext(HttpServletRequest httpServletRequest)
		throws PortalException {

		ObjectEntry objectEntry = ObjectEntryUtil.getObjectEntry(
			httpServletRequest);

		if (objectEntry == null) {
			return null;
		}

		return new ViewTaskInfoSummarySectionDisplayContext(
			_objectFieldBusinessTypeRegistry.getObjectFieldBusinessType(
				ObjectFieldConstants.BUSINESS_TYPE_ASSIGNEE),
			objectEntry,
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY));
	}

	@Override
	protected String getJSPPath() {
		return "/view_task_info_summary.jsp";
	}

	@Override
	protected String getLabelKey() {
		return "task-info-summary";
	}

	@Reference
	private ObjectFieldBusinessTypeRegistry _objectFieldBusinessTypeRegistry;

}