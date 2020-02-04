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

package com.liferay.staging.bar.web.internal.theme.contributor;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.template.TemplateContextContributor;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.staging.bar.web.internal.servlet.taglib.ui.StagingBarControlMenuJSPDynamicInclude;

import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = "type=" + TemplateContextContributor.TYPE_THEME,
	service = TemplateContextContributor.class
)
public class StagingBarTemplateContextContributor
	implements TemplateContextContributor {

	@Override
	public void prepare(
		Map<String, Object> contextObjects,
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		try {
			if (_stagingBarControlMenuJSPDynamicInclude.isShow(
					httpServletRequest)) {

				StringBuilder sb = new StringBuilder();

				sb.append(
					GetterUtil.getString(contextObjects.get("bodyCssClass")));

				Layout layout = themeDisplay.getLayout();

				if (!layout.isSystem() || layout.isTypeControlPanel() ||
					!Objects.equals(
						layout.getFriendlyURL(),
						PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL)) {

					sb.append(StringPool.SPACE);
					sb.append("has-staging-bar");
				}

				Group group = themeDisplay.getScopeGroup();

				if (group.isStagingGroup()) {
					sb.append(StringPool.SPACE);
					sb.append("staging local-staging");
				}
				else if (themeDisplay.isShowStagingIcon() &&
						 group.hasStagingGroup()) {

					sb.append(StringPool.SPACE);
					sb.append("live-view");
				}
				else if (themeDisplay.isShowStagingIcon() &&
						 group.isStagedRemotely()) {

					sb.append(StringPool.SPACE);
					sb.append("staging remote-staging");
				}

				contextObjects.put("bodyCssClass", sb.toString());
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		contextObjects.put("show_staging", themeDisplay.isShowStagingIcon());

		if (themeDisplay.isShowStagingIcon()) {
			contextObjects.put(
				"staging_text",
				LanguageUtil.get(httpServletRequest, "staging"));
		}
	}

	@Reference(unbind = "-")
	protected void setCustomizationSettingsControlMenuJSPDynamicInclude(
		StagingBarControlMenuJSPDynamicInclude
			stagingBarControlMenuJSPDynamicInclude) {

		_stagingBarControlMenuJSPDynamicInclude =
			stagingBarControlMenuJSPDynamicInclude;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagingBarTemplateContextContributor.class);

	private StagingBarControlMenuJSPDynamicInclude
		_stagingBarControlMenuJSPDynamicInclude;

}