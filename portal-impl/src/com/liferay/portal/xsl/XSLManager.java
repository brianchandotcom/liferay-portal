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

package com.liferay.portal.xsl;

import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.template.BaseTemplateManager;
import com.liferay.portal.template.TemplateContextHelper;

/**
 * @author Tina Tian
 */
@DoPrivileged
public class XSLManager extends BaseTemplateManager {

	public void destroy() {
		if (_templateContextHelper == null) {
			return;
		}

		_templateContextHelper.removeAllHelperUtilities();

		_templateContextHelper = null;
	}

	public void destroy(ClassLoader classLoader) {
		_templateContextHelper.removeHelperUtilities(classLoader);
	}

	public String getName() {
		return TemplateConstants.LANG_TYPE_XSL;
	}

	public void init() {
	}

	public void setTemplateContextHelper(
		TemplateContextHelper templateContextHelper) {

		_templateContextHelper = templateContextHelper;
	}

	@Override
	protected Template doGetTemplate(
		TemplateResource templateResource,
		TemplateResource errorTemplateResource, boolean restricted) {

		XSLTemplateResource xslTemplateResource =
			(XSLTemplateResource)templateResource;

		return new XSLTemplate(
			xslTemplateResource, errorTemplateResource, _templateContextHelper);
	}

	private TemplateContextHelper _templateContextHelper;

}