/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatalists.template;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.ddm.template.BaseDDMTemplateHandler;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;

import java.util.Locale;

/**
 * @author Jorge Ferrer
 */
public class DDLTemplateHandler extends BaseDDMTemplateHandler {

	public String getClassName() {
		return DDLRecordSet.class.getName();
	}

	public String getName(Locale locale) {
		String portletTitle = PortalUtil.getPortletTitle(
			PortletKeys.DYNAMIC_DATA_LISTS, locale);

		return portletTitle.concat(StringPool.SPACE).concat(
			LanguageUtil.get(locale, "template"));
	}

	public String getResourceName() {
		return "com.liferay.portlet.dynamicdatalists";
	}

}