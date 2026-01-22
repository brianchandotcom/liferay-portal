/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.fragment.renderer;

import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Kevin Tan
 */
@Component(service = FragmentRenderer.class)
public class TasksOverviewSectionFragmentRenderer
	extends BaseComponentSectionFragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "sections";
	}

	@Override
	public void render(
			FragmentRendererContext fragmentRendererContext,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		String layoutMode = ParamUtil.getString(
			httpServletRequest, "p_l_mode", Constants.VIEW);

		if (Objects.equals(layoutMode, Constants.READ)) {
			return;
		}

		super.render(
			fragmentRendererContext, httpServletRequest, httpServletResponse);
	}

	@Override
	protected String getComponentName() {
		return "TasksOverview";
	}

	@Override
	protected String getLabelKey() {
		return "tasks-overview";
	}

	@Override
	protected String getModuleName() {
		return "site-cmp-site-initializer";
	}

	@Override
	protected Map<String, Object> getProps(
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest) {

		return HashMapBuilder.<String, Object>put(
			"cmpProjectId",
			() -> {
				Object object = httpServletRequest.getAttribute(
					InfoDisplayWebKeys.INFO_ITEM);

				if (object instanceof ObjectEntry) {
					ObjectEntry objectEntry = (ObjectEntry)object;

					return objectEntry.getObjectEntryId();
				}

				return null;
			}
		).build();
	}

}