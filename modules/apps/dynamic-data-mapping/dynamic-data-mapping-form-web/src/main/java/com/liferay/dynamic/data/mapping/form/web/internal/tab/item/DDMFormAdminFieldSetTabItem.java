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

package com.liferay.dynamic.data.mapping.form.web.internal.tab.item;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.util.DDMDisplayTabItem;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Lino Alves
 */
@Component(
	property = "javax.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN,
	service = {DDMDisplayTabItem.class, DDMFormAdminFieldSetTabItem.class}
)
public class DDMFormAdminFieldSetTabItem extends DDMFormAdminTabItem {

	@Override
	public String getTitle(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		return LanguageUtil.get(
			liferayPortletRequest.getHttpServletRequest(), "element-sets");
	}

	@Override
	public String getURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		return PortletURLBuilder.create(
			getPortletURL(liferayPortletRequest, liferayPortletResponse)
		).setParameter(
			"currentTab", "element-set"
		).buildString();
	}

}