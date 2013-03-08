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

package com.liferay.portal.xsl;

import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateContextType;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.template.RestrictedTemplate;
import com.liferay.portal.template.TemplateContextHelper;

import java.util.Map;

/**
 * @author Tina Tian
 */
@DoPrivileged
public class XSLManager implements TemplateManager {

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

	public Template getTemplate(
		TemplateResource templateResource,
		TemplateContextType templateContextType) {

		return getTemplate(templateResource, null, templateContextType);
	}

	public Template getTemplate(
		TemplateResource templateResource,
		TemplateResource errorTemplateResource,
		TemplateContextType templateContextType) {

		Template template = null;

		XSLTemplateResource xslTemplateResource =
			(XSLTemplateResource)templateResource;

		Map<String, Object> context = _templateContextHelper.getHelperUtilities(
			templateContextType);

		if (templateContextType.equals(TemplateContextType.EMPTY)) {
			template = new XSLTemplate(
				xslTemplateResource, errorTemplateResource, null,
				_templateContextHelper);
		}
		else if (templateContextType.equals(TemplateContextType.RESTRICTED)) {
			template = new RestrictedTemplate(
				new XSLTemplate(
					xslTemplateResource, errorTemplateResource, context,
					_templateContextHelper),
				_templateContextHelper.getRestrictedVariables());
		}
		else if (templateContextType.equals(TemplateContextType.STANDARD)) {
			template = new XSLTemplate(
				xslTemplateResource, errorTemplateResource, context,
				_templateContextHelper);
		}

		return template;
	}

	public void init() {
	}

	public void setTemplateContextHelper(
		TemplateContextHelper templateContextHelper) {

		_templateContextHelper = templateContextHelper;
	}

	private TemplateContextHelper _templateContextHelper;

}