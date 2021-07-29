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

package com.liferay.template.web.internal.portlet.template;

import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.template.BaseTemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.template.constants.TemplatePortletKeys;
import com.liferay.template.web.internal.constants.TemplateConstants;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + TemplatePortletKeys.TEMPLATE,
	service = TemplateHandler.class
)
public class InformationTemplatesTemplateHandler extends BaseTemplateHandler {

	@Override
	public String getClassName() {
		return InfoItemFormProvider.class.getName();
	}

	@Override
	public String getName(Locale locale) {
		String portletTitle = _portal.getPortletTitle(
			TemplatePortletKeys.TEMPLATE,
			ResourceBundleUtil.getBundle(locale, getClass()));

		return LanguageUtil.format(locale, "x-template", portletTitle, false);
	}

	@Override
	public String getResourceName() {
		return TemplateConstants.RESOURCE_NAME;
	}

	@Override
	public Map<String, TemplateVariableGroup> getTemplateVariableGroups(
			long classPK, String language, Locale locale) {

		return new HashMap<>();
	}

	@Override
	public boolean isDisplayTemplateHandler() {
		return false;
	}

	@Reference
	private Portal _portal;

}