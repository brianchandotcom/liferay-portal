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

package com.liferay.accessibility.menu.web.internal.template;

import com.liferay.accessibility.menu.web.internal.model.AccessibilitySetting;
import com.liferay.accessibility.menu.web.internal.util.AccessibilitySettingsUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.template.TemplateContextContributor;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Evan Thibodeau
 */
@Component(
	property = "type=" + TemplateContextContributor.TYPE_THEME,
	service = TemplateContextContributor.class
)
public class AccessibilityTemplateContextContributor
	implements TemplateContextContributor {

	@Override
	public void prepare(
		Map<String, Object> contextObjects,
		HttpServletRequest httpServletRequest) {

		if (!AccessibilitySettingsUtil.isAccessibilityMenuEnabled(
				httpServletRequest, _configurationProvider)) {

			return;
		}

		StringBundler sb = new StringBundler();

		sb.append(GetterUtil.getString(contextObjects.get("bodyCssClass")));

		for (AccessibilitySetting accessibilitySetting :
				AccessibilitySettingsUtil.getAccessibilitySettings(
					httpServletRequest)) {

			if (accessibilitySetting.isEnabled()) {
				sb.append(StringPool.SPACE);
				sb.append(accessibilitySetting.getCssClass());
			}
		}
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

}