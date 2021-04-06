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

package com.liferay.click.to.chat.web.internal.servlet.taglib;

import com.liferay.click.to.chat.web.internal.configuration.ClickToChatConfiguration;
import com.liferay.click.to.chat.web.internal.configuration.ProviderOptions;
import com.liferay.click.to.chat.web.internal.constants.ClickToChatWebKeys;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.servlet.taglib.BaseJSPDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.IOException;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author José Abelenda
 */
@Component(
	configurationPid = "com.liferay.click.to.chat.web.internal.configuration.ClickToChatConfiguration",
	immediate = true, service = DynamicInclude.class
)
public class ClickToChatTopHeadJSPDynamicInclude extends BaseJSPDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		Group liveGroup = (Group)httpServletRequest.getAttribute(
			"site.liveGroup");

		UnicodeProperties typeSettingsUnicodeProperties = null;

		if (liveGroup != null) {
			typeSettingsUnicodeProperties =
				liveGroup.getTypeSettingsProperties();
		}
		else {
			typeSettingsUnicodeProperties = new UnicodeProperties();
		}

		boolean clickToChatEnabled = GetterUtil.getBoolean(
			typeSettingsUnicodeProperties.getProperty("clickToChatEnabled"));

		httpServletRequest.setAttribute(
			ClickToChatWebKeys.CLICK_TO_CHAT_ENABLED, clickToChatEnabled);

		if (!_clickToChatConfiguration.enable()) {
			return;
		}

		if (_clickToChatConfiguration.provider() == null) {
			return;
		}

		ProviderOptions provider = _clickToChatConfiguration.provider();

		String providerName = StringUtil.toLowerCase(provider.getValue());

		httpServletRequest.setAttribute(
			ClickToChatWebKeys.CLICK_TO_CHAT_PROVIDER_NAME, providerName);

		httpServletRequest.setAttribute(
			ClickToChatWebKeys.CLICK_TO_CHAT_ACCOUNT_TOKEN,
			_clickToChatConfiguration.accountToken());

		httpServletRequest.setAttribute(
			ClickToChatConfiguration.class.getName(),
			_clickToChatConfiguration);

		super.include(httpServletRequest, httpServletResponse, key);
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"/html/common/themes/top_head.jsp#post");
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_clickToChatConfiguration = ConfigurableUtil.createConfigurable(
			ClickToChatConfiguration.class, properties);
	}

	@Override
	protected String getJspPath() {
		return "/dynamic_include/view.jsp";
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.click.to.chat.web)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClickToChatTopHeadJSPDynamicInclude.class);

	private ClickToChatConfiguration _clickToChatConfiguration;

}