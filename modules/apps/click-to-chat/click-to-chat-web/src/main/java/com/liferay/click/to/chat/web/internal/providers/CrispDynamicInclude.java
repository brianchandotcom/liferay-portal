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

package com.liferay.click.to.chat.web.internal.providers;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.HtmlUtil;

import java.io.IOException;

import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

/**
 * @author José Abelenda
 */
public class CrispDynamicInclude extends ProviderDynamicInclude {

	public CrispDynamicInclude(String providerAccountToken, User user) {
		super(providerAccountToken, user);
	}

	@Override
	public String getContentToInclude() {
		return _getChatScript();
	}

	private String _getChatScript() {
		try {
			String script = IOUtils.toString(
				getClass().getResourceAsStream(_MAIN_TEMPLATE_CLASSPATH_PATH),
				StandardCharsets.UTF_8.name());

			return String.format(
				script, HtmlUtil.escapeJS(providerAccountToken),
				user.getEmailAddress(), user.getScreenName());
		}
		catch (IOException ioException) {
			return "<!-- Invalid script -->";
		}
	}

	private static final String _MAIN_TEMPLATE_CLASSPATH_PATH =
		"/template/crisp-template.js";

}