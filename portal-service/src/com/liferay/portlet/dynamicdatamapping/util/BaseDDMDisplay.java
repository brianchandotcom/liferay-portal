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

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;

import java.util.Locale;
import java.util.Set;

/**
 * @author Eduardo Garcia
 */
public class BaseDDMDisplay implements DDMDisplay {

	public String getEditTemplateTitle(
		DDMStructure structure, DDMTemplate template, Locale locale) {

		String title = StringPool.BLANK;

		if (structure != null) {
			if (template != null) {
				title =
					template.getName(locale) + " (" +
						structure.getName(locale) + ")";
			}
			else {
				title = LanguageUtil.format(
					locale, "new-template-for-structure-x",
					structure.getName(locale), false);
			}
		}
		else if (template != null) {
			title = template.getName(locale);
		}
		else {
			title = getDefaultEditTemplateTitle(locale);
		}

		return title;
	}

	public String getEditTemplateTitle(long classNameId, Locale locale) {
		if (classNameId > 0) {
			TemplateHandler templateHandler =
				TemplateHandlerRegistryUtil.getTemplateHandler(classNameId);

			if (templateHandler != null) {
				return LanguageUtil.get(locale, "new") + StringPool.SPACE +
					templateHandler.getName(locale);
			}
		}

		return getDefaultEditTemplateTitle(locale);
	}

	public String getPortletId() {
		return PortletKeys.DYNAMIC_DATA_MAPPING;
	}

	public String getTemplateType(DDMTemplate template, Locale locale) {
		return LanguageUtil.get(locale, template.getType());
	}

	public Set<String> getViewTemplatesExcludedColumns() {
		return _viewTemplateExcludedColumns;
	}

	public String getViewTemplatesTitle(
		DDMStructure structure, boolean isControlPanel, Locale locale) {

		String title = StringPool.BLANK;

		if (structure != null) {
			title = LanguageUtil.format(
				locale, "templates-for-structure-x", structure.getName(locale),
				false);
		}
		else {
			title = getDefaultViewTemplateTitle(locale);
		}

		return title;
	}

	public String getViewTemplatesTitle(DDMStructure structure, Locale locale) {
		return getViewTemplatesTitle(structure, false, locale);
	}

	protected String getDefaultEditTemplateTitle(Locale locale) {
		return LanguageUtil.get(locale, "new-template");
	}

	protected String getDefaultViewTemplateTitle(Locale locale) {
		return LanguageUtil.get(locale, "templates");
	}

	private static Set<String> _viewTemplateExcludedColumns = SetUtil.fromArray(
		new String[] {"structure"});

}