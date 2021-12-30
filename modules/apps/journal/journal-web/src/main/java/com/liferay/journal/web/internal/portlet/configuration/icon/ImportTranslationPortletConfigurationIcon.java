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

package com.liferay.journal.web.internal.portlet.configuration.icon;

import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.web.internal.configuration.FFBulkTranslationConfiguration;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.translation.url.provider.TranslationURLProvider;

import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	configurationPid = "com.liferay.journal.web.internal.configuration.FFBulkTranslationConfiguration",
	immediate = true,
	property = "javax.portlet.name=" + JournalPortletKeys.JOURNAL,
	service = PortletConfigurationIcon.class
)
public class ImportTranslationPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return LanguageUtil.get(
			getResourceBundle(getLocale(portletRequest)), "import-translation");
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		try {
			return PortletURLBuilder.create(
				_translationURLProvider.getImportTranslationURL(
					_getGroupId(portletRequest),
					_portal.getClassNameId(JournalArticle.class.getName()),
					RequestBackedPortletURLFactoryUtil.create(portletRequest))
			).setRedirect(
				_portal.getCurrentURL(portletRequest)
			).setPortletResource(
				() -> {
					ThemeDisplay themeDisplay =
						(ThemeDisplay)portletRequest.getAttribute(
							WebKeys.THEME_DISPLAY);

					PortletDisplay portletDisplay =
						themeDisplay.getPortletDisplay();

					return portletDisplay.getId();
				}
			).buildString();
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		return _ffBulkTranslationConfiguration.bulkTranslationEnabled();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_ffBulkTranslationConfiguration = ConfigurableUtil.createConfigurable(
			FFBulkTranslationConfiguration.class, properties);
	}

	private long _getGroupId(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay.getScopeGroupId();
	}

	private volatile FFBulkTranslationConfiguration
		_ffBulkTranslationConfiguration;

	@Reference
	private Portal _portal;

	@Reference
	private TranslationURLProvider _translationURLProvider;

}