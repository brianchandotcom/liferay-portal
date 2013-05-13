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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Juan Fernández
 */
public class DDMTemplateHelperUtil {

	public static DDMStructure fetchStructure(DDMTemplate ddmTemplate) {
		return getDDMTemplateHelper().fetchStructure(ddmTemplate);
	}

	public static String getAutocompleteJSON(
			HttpServletRequest request, String language)
		throws Exception {

		return getDDMTemplateHelper().getAutocompleteJSON(request, language);
	}

	public static DDMTemplateHelper getDDMTemplateHelper() {
		PortalRuntimePermission.checkGetBeanProperty(
			DDMTemplateHelperUtil.class);

		return _ddmTemplateHelper;
	}

	public void setDDMTemplateHelper(DDMTemplateHelper ddmTemplateHelper) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_ddmTemplateHelper = ddmTemplateHelper;
	}

	private static DDMTemplateHelper _ddmTemplateHelper;

}