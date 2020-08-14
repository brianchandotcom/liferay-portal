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
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLWrapper;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.questions.web.internal.configuration.QuestionsConfiguration;
import com.liferay.questions.web.internal.constants.QuestionsPortletKeys;
import com.liferay.questions.web.internal.constants.QuestionsWebKeys;

import java.io.IOException;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
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
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-questions",
		"com.liferay.portlet.display-category=category.collaboration",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"javax.portlet.display-name=Questions",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + QuestionsPortletKeys.QUESTIONS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user"
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

		renderRequest.setAttribute(QuestionsWebKeys.DEFAULT_RANK, lowestRank);

		renderRequest.setAttribute(
			QuestionsWebKeys.TAG_SELECTOR_URL,
			_getTagSelectorURL(renderRequest, renderResponse));

		super.doView(renderRequest, renderResponse);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_questionsConfiguration = ConfigurableUtil.createConfigurable(
			QuestionsConfiguration.class, properties);
	}

	@Reference(unbind = "-")
	protected void setItemSelector(ItemSelector itemSelector) {
		_itemSelector = itemSelector;
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
			return null;
		}
	}

	private ItemSelector _itemSelector;

	@Reference
	private Portal _portal;

	private volatile QuestionsConfiguration _questionsConfiguration;

}