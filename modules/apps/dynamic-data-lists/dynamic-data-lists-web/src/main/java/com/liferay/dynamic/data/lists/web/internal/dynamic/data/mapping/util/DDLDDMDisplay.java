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

package com.liferay.dynamic.data.lists.web.internal.dynamic.data.mapping.util;

import com.liferay.dynamic.data.lists.constants.DDLPortletKeys;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.BaseDDMDisplay;
import com.liferay.dynamic.data.mapping.util.DDMDisplay;
import com.liferay.dynamic.data.mapping.util.DDMDisplayTabItem;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.Portal;

import java.util.Locale;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	property = "javax.portlet.name=" + DDLPortletKeys.DYNAMIC_DATA_LISTS,
	service = DDMDisplay.class
)
public class DDLDDMDisplay extends BaseDDMDisplay {

	@Override
	public DDMDisplayTabItem getDefaultTabItem() {
		return new DDMDisplayTabItem() {

			@Override
			public String getTitle(
				LiferayPortletRequest liferayPortletRequest,
				LiferayPortletResponse liferayPortletResponse) {

				Locale locale = portal.getLocale(liferayPortletRequest);

				String viewStructuresTitle = DDLDDMDisplay.this.getTitle(
					locale);

				if (Objects.equals(
						viewStructuresTitle,
						portal.getPortletTitle(liferayPortletResponse))) {

					return viewStructuresTitle;
				}

				return getViewTemplatesTitle(null, locale);
			}

		};
	}

	@Override
	public String getPortletId() {
		return DDLPortletKeys.DYNAMIC_DATA_LISTS;
	}

	@Override
	public String getStorageType() {
		return StorageType.JSON.getValue();
	}

	@Override
	public String getStructureName(Locale locale) {
		return LanguageUtil.get(getResourceBundle(locale), "data-definition");
	}

	@Override
	public String getStructureType() {
		return DDLRecordSet.class.getName();
	}

	@Override
	public long getTemplateHandlerClassNameId(
		DDMTemplate template, long classNameId) {

		return portal.getClassNameId(DDLRecordSet.class);
	}

	@Override
	public String getTitle(Locale locale) {
		return LanguageUtil.get(getResourceBundle(locale), "data-definitions");
	}

	@Override
	public boolean isShowBackURLInTitleBar() {
		return true;
	}

	@Reference
	protected Portal portal;

}