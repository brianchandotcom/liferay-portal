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

package com.liferay.site.admin.web.internal.servlet.taglib.ui;

import com.liferay.frontend.taglib.form.navigator.util.FormNavigatorContextProvider;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorConstants;
import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = FormNavigatorContextProvider.ID_KEY + "=" + FormNavigatorConstants.FORM_NAVIGATOR_ID_SITES,
	service = FormNavigatorContextProvider.class
)
public class SiteFormNavigatorContextProvider implements
	FormNavigatorContextProvider<Group> {

	@Override
	public String getContext(Group group) {
		return group == null ? "add" : "update";
	}
}
