/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.accessibility.menu.web.internal.template;

import com.liferay.accessibility.menu.web.internal.model.AccessibilitySetting;
import com.liferay.accessibility.menu.web.internal.util.AccessibilitySettingsUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
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