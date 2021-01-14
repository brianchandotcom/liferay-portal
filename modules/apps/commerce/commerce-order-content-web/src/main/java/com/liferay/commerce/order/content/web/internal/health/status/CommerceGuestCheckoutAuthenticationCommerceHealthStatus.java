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

package com.liferay.commerce.order.content.web.internal.health.status;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.commerce.configuration.CommerceOrderCheckoutConfiguration;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.constants.CommerceHealthStatusConstants;
import com.liferay.commerce.product.channel.CommerceChannelHealthStatus;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.importer.CPFileImporter;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.journal.constants.JournalContentPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"commerce.channel.health.status.display.order:Integer=40",
		"commerce.channel.health.status.key=" + CommerceHealthStatusConstants.COMMERCE_GUEST_CHECKOUT_AUTHENTICATION_COMMERCE_HEALTH_STATUS_KEY
	},
	service = CommerceChannelHealthStatus.class
)
public class CommerceGuestCheckoutAuthenticationCommerceHealthStatus
	implements CommerceChannelHealthStatus {

	@Override
	public void fixIssue(long companyId, long commerceChannelId)
		throws PortalException {

		if (isFixed(companyId, commerceChannelId)) {
			return;
		}

		CommerceChannel commerceChannel =
			_commerceChannelService.getCommerceChannel(commerceChannelId);

		String name = "Authentication";

		String friendlyURL =
			StringPool.FORWARD_SLASH + StringUtil.toLowerCase(name);

		boolean privateLayout = true;

		List<Layout> layouts = _layoutService.getLayouts(
			commerceChannel.getSiteGroupId(), true);

		if (layouts.isEmpty()) {
			privateLayout = false;
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		serviceContext.setTimeZone(TimeZone.getDefault());

		Layout layout = _layoutService.addLayout(
			commerceChannel.getSiteGroupId(), privateLayout,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, name, name, null,
			LayoutConstants.TYPE_PORTLET, true, friendlyURL, serviceContext);

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		layoutTypePortlet.setLayoutTemplateId(0, "2_columns_i", false);

		layoutTypePortlet.addPortletId(
			PrincipalThreadLocal.getUserId(),
			"com_liferay_login_web_portlet_LoginPortlet", "column-1", 0);

		String journalArticlePortletId = layoutTypePortlet.addPortletId(
			PrincipalThreadLocal.getUserId(),
			JournalContentPortletKeys.JOURNAL_CONTENT, "column-2", 0);

		PortletPreferences portletPreferences =
			_portletPreferencesFactory.getPortletSetup(
				layout, journalArticlePortletId, null);

		ClassLoader classLoader =
			CommerceGuestCheckoutAuthenticationCommerceHealthStatus.class.
				getClassLoader();

		String dependenciesFilePath =
			"com/liferay/commerce/order/content/web/internal/dependencies/";

		try {
			String journalArticleJsonStirng = StringUtil.read(
				classLoader, dependenciesFilePath + "journal-articles.json");

			JSONArray jsonArray = _jsonFactory.createJSONArray(
				journalArticleJsonStirng);

			_cpFileImporter.createJournalArticles(
				jsonArray, classLoader,
				dependenciesFilePath + "journal_articles/", serviceContext);

			List<JournalArticle> journalArticles =
				_journalArticleLocalService.getArticlesByStructureId(
					serviceContext.getScopeGroupId(),
					"guest-checkout-authentication-structure", 0, 1, null);

			JournalArticle journalArticle = journalArticles.get(0);

			AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
				JournalArticle.class.getName(),
				journalArticle.getResourcePrimKey());

			Map<String, String[]> parameterMap = HashMapBuilder.put(
				"articleId", new String[] {journalArticle.getArticleId()}
			).put(
				"assetEntryId",
				new String[] {String.valueOf(assetEntry.getEntryId())}
			).put(
				"groupId",
				new String[] {String.valueOf(journalArticle.getGroupId())}
			).put(
				"showAvailableLocales", new String[] {Boolean.TRUE.toString()}
			).build();

			for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
				portletPreferences.setValues(entry.getKey(), entry.getValue());
			}

			portletPreferences.store();

			_layoutLocalService.updateLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(), layout.getTypeSettings());
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}
	}

	@Override
	public String getDescription(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(
			resourceBundle,
			CommerceHealthStatusConstants.
				COMMERCE_GUEST_CHECKOUT_AUTHENTICATION_COMMERCE_HEALTH_STATUS_DESCRIPTION);
	}

	@Override
	public String getKey() {
		return CommerceHealthStatusConstants.
			COMMERCE_GUEST_CHECKOUT_AUTHENTICATION_COMMERCE_HEALTH_STATUS_KEY;
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(
			resourceBundle,
			CommerceHealthStatusConstants.
				COMMERCE_GUEST_CHECKOUT_AUTHENTICATION_COMMERCE_HEALTH_STATUS_KEY);
	}

	@Override
	public boolean isFixed(long companyId, long commerceChannelId)
		throws PortalException {

		CommerceChannel commerceChannel =
			_commerceChannelService.getCommerceChannel(commerceChannelId);

		String commerceChannelType = commerceChannel.getType();

		if (!commerceChannelType.equals(
				CommerceChannelConstants.CHANNEL_TYPE_SITE)) {

			return true;
		}

		CommerceOrderCheckoutConfiguration commerceOrderCheckoutConfiguration =
			_configurationProvider.getConfiguration(
				CommerceOrderCheckoutConfiguration.class,
				new GroupServiceSettingsLocator(
					commerceChannel.getGroupId(),
					CommerceConstants.SERVICE_NAME_ORDER));

		if (!commerceOrderCheckoutConfiguration.guestCheckoutEnabled()) {
			return true;
		}

		String name = "Authentication";

		String friendlyURL =
			StringPool.FORWARD_SLASH + StringUtil.toLowerCase(name);

		boolean privateLayout = true;

		List<Layout> layouts = _layoutService.getLayouts(
			commerceChannel.getSiteGroupId(), true);

		if (layouts.isEmpty()) {
			privateLayout = false;
		}

		Layout layout = _layoutLocalService.fetchLayoutByFriendlyURL(
			commerceChannel.getSiteGroupId(), privateLayout, friendlyURL);

		if (layout != null) {
			return true;
		}

		return false;
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private CPFileImporter _cpFileImporter;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutService _layoutService;

	@Reference
	private PortletPreferencesFactory _portletPreferencesFactory;

}