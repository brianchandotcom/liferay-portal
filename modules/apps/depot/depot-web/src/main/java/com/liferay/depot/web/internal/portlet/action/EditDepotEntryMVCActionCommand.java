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

package com.liferay.depot.web.internal.portlet.action;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.depot.web.internal.constants.DepotPortletKeys;
import com.liferay.depot.web.internal.util.DepotEntryURLUtil;
import com.liferay.portal.kernel.exception.GroupNameException;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DepotPortletKeys.DEPOT_ADMIN,
		"mvc.command.name=/depot_entry/edit"
	},
	service = MVCActionCommand.class
)
public class EditDepotEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long depotEntryId = ParamUtil.getLong(actionRequest, "depotEntryId");

		DepotEntry depotEntry = _depotEntryLocalService.getDepotEntry(
			depotEntryId);

		long groupId = depotEntry.getGroupId();

		Group group = _groupService.getGroup(groupId);

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name", group.getNameMap());
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(
				actionRequest, "description", group.getDescriptionMap());

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DepotEntry.class.getName(), actionRequest);

		//languages
		UnicodeProperties formTypeSettingsProperties =
			PropertiesParamUtil.getProperties(
				actionRequest, "TypeSettingsProperties--");

		Locale defaultLocale = LocaleUtil.fromLanguageId(
			formTypeSettingsProperties.getProperty("languageId"));

		if (!group.isGuest()) {
			_validateDefaultLocaleGroupName(nameMap, defaultLocale);
		}

		_depotEntryLocalService.updateDepotEntry(
			depotEntryId, nameMap, descriptionMap, serviceContext);

		UnicodeProperties typeSettingsProperties =
			group.getTypeSettingsProperties();

		boolean inheritLocales = GetterUtil.getBoolean(
			typeSettingsProperties.getProperty("inheritLocales"));

		if (formTypeSettingsProperties.containsKey("inheritLocales")) {
			inheritLocales = GetterUtil.getBoolean(
				formTypeSettingsProperties.getProperty("inheritLocales"));
		}

		if (inheritLocales) {
			formTypeSettingsProperties.setProperty(
				PropsKeys.LOCALES,
				StringUtil.merge(
					LocaleUtil.toLanguageIds(
						LanguageUtil.getAvailableLocales())));
		}

		if (formTypeSettingsProperties.containsKey(PropsKeys.LOCALES) &&
			Validator.isNull(
				formTypeSettingsProperties.getProperty(PropsKeys.LOCALES))) {

			throw new LocaleException(
				LocaleException.TYPE_DEFAULT,
				"Must have at least one valid locale for site " + groupId);
		}

		typeSettingsProperties.putAll(formTypeSettingsProperties);

		try {
			_groupService.updateGroup(
				group.getGroupId(), typeSettingsProperties.toString());
		}
		catch (LocaleException le) {
			SessionErrors.add(actionRequest, le.getClass(), le);
			RenderURL editDepotEntryRenderURL =
				DepotEntryURLUtil.getEditDepotEntryRenderURL(
					depotEntryId,
					ParamUtil.getString(actionRequest, "redirect"),
					_portal.getLiferayPortletResponse(actionResponse));

			sendRedirect(
				actionRequest, actionResponse,
				editDepotEntryRenderURL.toString());
		}
	}

	private void _validateDefaultLocaleGroupName(
			Map<Locale, String> nameMap, Locale defaultLocale)
		throws PortalException {

		if ((nameMap == null) || Validator.isNull(nameMap.get(defaultLocale))) {
			throw new GroupNameException();
		}
	}

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private GroupService _groupService;

	@Reference
	private Portal _portal;

}