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

package com.liferay.journal.web.internal.portlet.action;

import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.exception.MaxAddMenuFavItemsException;
import com.liferay.journal.web.internal.configuration.JournalWebConfiguration;
import com.liferay.journal.web.internal.util.JournalPortletUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Eudaldo Alonso
 */
@Component(
	configurationPid = "com.liferay.journal.web.internal.configuration.JournalWebConfiguration",
	property = {
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"mvc.command.name=/journal/add_menu_fav_item"
	},
	service = MVCActionCommand.class
)
public class AddMenuFavItemMVCActionCommand extends BaseMVCActionCommand {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_journalWebConfiguration = ConfigurableUtil.createConfigurable(
			JournalWebConfiguration.class, properties);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(actionRequest);

		String key = JournalPortletUtil.getAddMenuFavItemKey(
			actionRequest, actionResponse);

		String[] addMenuFavItems = portalPreferences.getValues(
			JournalPortletKeys.JOURNAL, key, new String[0]);

		if (addMenuFavItems.length >=
				_journalWebConfiguration.maxAddMenuItems()) {

			hideDefaultErrorMessage(actionRequest);

			throw new MaxAddMenuFavItemsException();
		}

		String ddmStructureKey = ParamUtil.getString(
			actionRequest, "ddmStructureKey");

		portalPreferences.setValues(
			JournalPortletKeys.JOURNAL, key,
			ArrayUtil.append(addMenuFavItems, ddmStructureKey));
	}

	private volatile JournalWebConfiguration _journalWebConfiguration;

}