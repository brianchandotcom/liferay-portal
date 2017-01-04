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

import com.liferay.frontend.taglib.form.navigator.util.FormNavigatorEntryVisibilityConfigurationHelper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorConstants;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntryVisibilitySupervisor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(service = FormNavigatorEntryVisibilitySupervisor.class)
public class SiteLanguagesFormEntryNavigatorVisibilitySupervisor
	implements FormNavigatorEntryVisibilitySupervisor<Group> {

	@Override
	public String getCategoryKey() {
		return FormNavigatorConstants.CATEGORY_KEY_SITES_LANGUAGES;
	}

	@Override
	public String getFormNavigatorId() {
		return FormNavigatorConstants.FORM_NAVIGATOR_ID_SITES;
	}

	@Override
	public boolean isVisible(
		FormNavigatorEntry<Group> formNavigatorEntry, User user, Group group) {

		Class<? extends FormNavigatorEntry> clazz =
			formNavigatorEntry.getClass();

		if ((group == null) &&
			_formNavigatorEntryVisibilityConfigurationHelper.isHidden(
				"add.languages", formNavigatorEntry.getFormNavigatorId(),
				clazz.getName())) {

			return false;
		}
		else if ((group != null) &&
				 _formNavigatorEntryVisibilityConfigurationHelper.isHidden(
					 "update.languages",
					 formNavigatorEntry.getFormNavigatorId(),
					 clazz.getName())) {

			return false;
		}

		return formNavigatorEntry.isVisible(user, group);
	}

	@Reference
	private FormNavigatorEntryVisibilityConfigurationHelper
		_formNavigatorEntryVisibilityConfigurationHelper;

}