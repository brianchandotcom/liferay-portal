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

package com.liferay.translation.web.internal.portlet.action;

import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.GroupKeyInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.translation.constants.TranslationPortletKeys;
import com.liferay.translation.info.item.provider.InfoItemLanguagesProvider;
import com.liferay.translation.web.internal.util.ExportTranslationUtil;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + TranslationPortletKeys.TRANSLATION,
		"mvc.command.name=/translation/get_export_translation_available_locales"
	},
	service = MVCResourceCommand.class
)
public class GetExportTranslationAvailableLocalesMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long classNameId = ParamUtil.getLong(resourceRequest, "classNameId");

		String className = _portal.getClassName(classNameId);

		InfoItemObjectProvider<Object> infoItemObjectProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemObjectProvider.class, className);

		Object object = infoItemObjectProvider.getInfoItem(
			_getInfoItemIdentifier(resourceRequest));

		InfoItemLanguagesProvider<Object> infoItemLanguagesProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemLanguagesProvider.class, className);

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			JSONUtil.put(
				"availableLocales",
				ExportTranslationUtil.getLocalesJSONArray(
					themeDisplay.getLocale(),
					_getAvailableLocales(
						ParamUtil.getLong(resourceRequest, "groupId"),
						infoItemLanguagesProvider, object))
			).put(
				"defaultLanguageId",
				infoItemLanguagesProvider.getDefaultLanguageId(object)
			));
	}

	private Set<Locale> _getAvailableLocales(
			long groupId,
			InfoItemLanguagesProvider<Object> infoItemLanguagesProvider,
			Object object)
		throws Exception {

		if (infoItemLanguagesProvider == null) {
			return _language.getAvailableLocales(groupId);
		}

		Stream<String> stream = Arrays.stream(
			infoItemLanguagesProvider.getAvailableLanguageIds(object));

		Set<Locale> availableLocales = stream.map(
			LocaleUtil::fromLanguageId
		).collect(
			Collectors.toSet()
		);

		Locale siteDefaultLocale = _portal.getSiteDefaultLocale(groupId);

		if (!availableLocales.contains(siteDefaultLocale)) {
			availableLocales.add(siteDefaultLocale);
		}

		return availableLocales;
	}

	private InfoItemIdentifier _getInfoItemIdentifier(
		ResourceRequest resourceRequest) {

		long groupId = ParamUtil.getLong(resourceRequest, "groupId");

		if (groupId == GroupConstants.DEFAULT_PARENT_GROUP_ID) {
			return new ClassPKInfoItemIdentifier(
				ParamUtil.getLong(resourceRequest, "key"));
		}

		return new GroupKeyInfoItemIdentifier(
			groupId, ParamUtil.getString(resourceRequest, "key"));
	}

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}