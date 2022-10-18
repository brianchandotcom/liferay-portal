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

package com.liferay.questions.web.internal.portlet;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.flags.taglib.servlet.taglib.util.FlagsTagUtil;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.message.boards.moderation.configuration.MBModerationGroupConfiguration;
import com.liferay.message.boards.service.MBStatsUserLocalService;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLWrapper;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.questions.web.internal.configuration.QuestionsConfiguration;
import com.liferay.questions.web.internal.constants.QuestionsPortletKeys;
import com.liferay.questions.web.internal.constants.QuestionsWebKeys;

import java.io.IOException;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 */
@Component(
	configurationPid = "com.liferay.questions.web.internal.configuration.QuestionsConfiguration",
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-questions",
		"com.liferay.portlet.display-category=category.collaboration",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.preferences-unique-per-layout=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.scopeable=true",
		"com.liferay.portlet.single-page-application=false",
		"javax.portlet.display-name=Questions",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + QuestionsPortletKeys.QUESTIONS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user",
		"javax.portlet.version=3.0"
	},
	service = Portlet.class
)
public class QuestionsPortlet extends MVCPortlet {

	@Override
	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(
			QuestionsConfiguration.class.getName(), _questionsConfiguration);

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ItemSelectorCriterion itemSelectorCriterion =
			new ImageItemSelectorCriterion();

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new FileEntryItemSelectorReturnType(),
			new URLItemSelectorReturnType());

		PortletURL portletURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest),
			"EDITOR_NAME_selectItem", itemSelectorCriterion);

		renderRequest.setAttribute(
			QuestionsWebKeys.IMAGE_BROWSE_URL, portletURL.toString());

		String lowestRank = Stream.of(
			_portal.getPortalProperties()
		).map(
			properties -> properties.getProperty("message.boards.user.ranks")
		).map(
			s -> s.split(",")
		).flatMap(
			Arrays::stream
		).min(
			Comparator.comparing(rank -> rank.split("=")[1])
		).map(
			rank -> rank.split("=")[0]
		).orElse(
			"Youngling"
		);

		Company company = themeDisplay.getCompany();

		renderRequest.setAttribute(
			QuestionsWebKeys.COMPANY_NAME, company.getName());

		renderRequest.setAttribute(QuestionsWebKeys.DEFAULT_RANK, lowestRank);

		renderRequest.setAttribute(
			QuestionsWebKeys.FLAGS_PROPERTIES,
			HashMapBuilder.<String, Object>put(
				"context",
				HashMapBuilder.<String, Object>put(
					"namespace", _portal.getPortletNamespace(PortletKeys.FLAGS)
				).build()
			).put(
				"props",
				() -> HashMapBuilder.<String, Object>put(
					"captchaURI", FlagsTagUtil.getCaptchaURI(httpServletRequest)
				).put(
					"isFlagEnabled",
					FlagsTagUtil.isFlagsEnabled(themeDisplay) &&
					GetterUtil.getBoolean(
						PropsUtil.get("feature.flag.LPS-159928"))
				).put(
					"pathTermsOfUse",
					_portal.getPathMain() + "/portal/terms_of_use"
				).put(
					"reasons",
					FlagsTagUtil.getReasons(
						themeDisplay.getCompanyId(), httpServletRequest)
				).put(
					"uri", FlagsTagUtil.getURI(httpServletRequest)
				).put(
					"viewMode",
					Objects.equals(
						Constants.VIEW,
						ParamUtil.getString(
							themeDisplay.getRequest(), "p_l_mode",
							Constants.VIEW))
				).build()
			).build());
		renderRequest.setAttribute(
			QuestionsWebKeys.TAG_SELECTOR_URL,
			_getTagSelectorURL(renderRequest, renderResponse));
		renderRequest.setAttribute(
			QuestionsWebKeys.TRUSTED_USER, _isTrustedUser(renderRequest));

		super.doView(renderRequest, renderResponse);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_questionsConfiguration = ConfigurableUtil.createConfigurable(
			QuestionsConfiguration.class, properties);
	}

	private String _getTagSelectorURL(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		try {
			PortletURL portletURL = PortletProviderUtil.getPortletURL(
				renderRequest, AssetTag.class.getName(),
				PortletProvider.Action.BROWSE);

			PortletURLWrapper portletURLWrapper = new PortletURLWrapper(
				portletURL);

			if (portletURL == null) {
				return null;
			}

			portletURLWrapper.setParameter(
				"eventName", renderResponse.getNamespace() + "selectTag");
			portletURLWrapper.setParameter(
				"selectedTagNames", "{selectedTagNames}");
			portletURLWrapper.setWindowState(LiferayWindowState.POP_UP);

			return portletURLWrapper.toString();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return null;
		}
	}

	private boolean _isTrustedUser(RenderRequest renderRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			MBModerationGroupConfiguration mbModerationGroupConfiguration =
				_configurationProvider.getGroupConfiguration(
					MBModerationGroupConfiguration.class,
					themeDisplay.getScopeGroupId());

			if (!mbModerationGroupConfiguration.
					enableMessageBoardsModeration()) {

				return true;
			}

			long messageCountByUserId =
				_mbStatsUserLocalService.getMessageCountByUserId(
					themeDisplay.getUserId());

			if (messageCountByUserId <
					mbModerationGroupConfiguration.
						minimumContributedMessages()) {

				return false;
			}
		}
		catch (ConfigurationException configurationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(configurationException);
			}
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		QuestionsPortlet.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private MBStatsUserLocalService _mbStatsUserLocalService;

	@Reference
	private Portal _portal;

	private volatile QuestionsConfiguration _questionsConfiguration;

}