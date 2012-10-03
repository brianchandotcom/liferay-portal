/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs.template;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portletdisplaytemplate.BasePortletDisplayTemplateHandler;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.blogs.model.BlogsEntry;

import java.util.Locale;

/**
 * @author Juan Fernández
 */
public class BlogsPortletDisplayTemplateHandler
	extends BasePortletDisplayTemplateHandler {

	public String getClassName() {
		return BlogsEntry.class.getName();
	}

	public String getName(Locale locale) {
		String portletTitle = PortalUtil.getPortletTitle(
			PortletKeys.BLOGS, locale);

		return portletTitle.concat(StringPool.SPACE).concat(
			LanguageUtil.get(locale, "template"));
	}

	public String getResourceName() {
		return "com.liferay.portlet.blogs";
	}

	@Override
	protected String getTemplatesConfigPath() {
		return PropsValues.BLOGS_DISPLAY_TEMPLATES_CONFIG;
	}

	@Override
	protected String getTemplatesHelpKey() {
		return PropsKeys.BLOGS_DISPLAY_TEMPLATES_HELP;
	}

}