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

package com.liferay.commerce.product.type.virtual.order.content.web.internal.display.context;

import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.commerce.product.type.virtual.order.content.web.internal.display.context.helper.CommerceVirtualOrderItemContentRequestHelper;
import com.liferay.commerce.product.type.virtual.order.content.web.internal.portlet.configuration.CommerceVirtualOrderItemContentPortletInstanceConfiguration;
import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem;
import com.liferay.commerce.product.type.virtual.order.service.CommerceVirtualOrderItemLocalService;
import com.liferay.commerce.product.type.virtual.order.util.comparator.CommerceVirtualOrderItemCreateDateComparator;
import com.liferay.commerce.product.type.virtual.service.CPDefinitionVirtualSettingService;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceVirtualOrderItemContentDisplayContext {

	public CommerceVirtualOrderItemContentDisplayContext(
			CommerceChannelLocalService commerceChannelLocalService,
			CommerceVirtualOrderItemLocalService
				commerceVirtualOrderItemLocalService,
			CPDefinitionHelper cpDefinitionHelper,
			CPDefinitionVirtualSettingService cpDefinitionVirtualSettingService,
			CPInstanceHelper cpInstanceHelper,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		_commerceChannelLocalService = commerceChannelLocalService;
		_commerceVirtualOrderItemLocalService =
			commerceVirtualOrderItemLocalService;
		_cpDefinitionHelper = cpDefinitionHelper;
		_cpDefinitionVirtualSettingService = cpDefinitionVirtualSettingService;
		_cpInstanceHelper = cpInstanceHelper;
		_httpServletRequest = httpServletRequest;

		_commerceVirtualOrderItemContentRequestHelper =
			new CommerceVirtualOrderItemContentRequestHelper(
				httpServletRequest);

		PortletDisplay portletDisplay =
			_commerceVirtualOrderItemContentRequestHelper.getPortletDisplay();

		_commerceVirtualOrderItemContentPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				CommerceVirtualOrderItemContentPortletInstanceConfiguration.
					class);
	}

	public JournalArticleDisplay getArticleDisplay() throws Exception {
		if (_articleDisplay != null) {
			return _articleDisplay;
		}

		HttpServletRequest httpServletRequest =
			_commerceVirtualOrderItemContentRequestHelper.getRequest();

		long groupId = ParamUtil.getLong(httpServletRequest, "groupId");
		String articleId = ParamUtil.getString(httpServletRequest, "articleId");
		double version = ParamUtil.getDouble(httpServletRequest, "version");

		JournalArticle article = JournalArticleLocalServiceUtil.fetchArticle(
			groupId, articleId, version);

		if (article == null) {
			return _articleDisplay;
		}

		ThemeDisplay themeDisplay =
			_commerceVirtualOrderItemContentRequestHelper.getThemeDisplay();

		int page = ParamUtil.getInteger(httpServletRequest, "page");

		_articleDisplay = JournalArticleLocalServiceUtil.getArticleDisplay(
			article, null, null, themeDisplay.getLanguageId(), page,
			new PortletRequestModel(
				_commerceVirtualOrderItemContentRequestHelper.
					getLiferayPortletRequest(),
				_commerceVirtualOrderItemContentRequestHelper.
					getLiferayPortletResponse()),
			themeDisplay);

		return _articleDisplay;
	}

	public String getCommerceOrderItemThumbnailSrc(
			CommerceOrderItem commerceOrderItem)
		throws Exception {

		return _cpInstanceHelper.getCPInstanceThumbnailSrc(
			CommerceUtil.getCommerceAccountId(
				(CommerceContext)_httpServletRequest.getAttribute(
					CommerceWebKeys.COMMERCE_CONTEXT)),
			commerceOrderItem.getCPInstanceId());
	}

	public String getCPDefinitionURL(
			long cpDefinitionId, ThemeDisplay themeDisplay)
		throws PortalException {

		return _cpDefinitionHelper.getFriendlyURL(cpDefinitionId, themeDisplay);
	}

	public CPDefinitionVirtualSetting getCPDefinitionVirtualSetting(
			CommerceOrderItem commerceOrderItem)
		throws PortalException {

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			_cpDefinitionVirtualSettingService.fetchCPDefinitionVirtualSetting(
				CPInstance.class.getName(),
				commerceOrderItem.getCPInstanceId());

		if ((cpDefinitionVirtualSetting == null) ||
			!cpDefinitionVirtualSetting.isOverride()) {

			cpDefinitionVirtualSetting =
				_cpDefinitionVirtualSettingService.
					fetchCPDefinitionVirtualSetting(
						CPDefinition.class.getName(),
						commerceOrderItem.getCPDefinitionId());
		}

		return cpDefinitionVirtualSetting;
	}

	public String getDisplayStyle() {
		return _commerceVirtualOrderItemContentPortletInstanceConfiguration.
			displayStyle();
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId > 0) {
			return _displayStyleGroupId;
		}

		_displayStyleGroupId =
			_commerceVirtualOrderItemContentPortletInstanceConfiguration.
				displayStyleGroupId();

		if (_displayStyleGroupId <= 0) {
			_displayStyleGroupId =
				_commerceVirtualOrderItemContentRequestHelper.getScopeGroupId();
		}

		return _displayStyleGroupId;
	}

	public ResourceURL getDownloadResourceURL(long commerceVirtualOrderItemId) {
		LiferayPortletResponse liferayPortletResponse =
			_commerceVirtualOrderItemContentRequestHelper.
				getLiferayPortletResponse();

		ResourceURL resourceURL = liferayPortletResponse.createResourceURL();

		resourceURL.setParameter(
			"commerceVirtualOrderItemId",
			String.valueOf(commerceVirtualOrderItemId));

		resourceURL.setResourceID(
			"/commerce_virtual_order_item_content" +
				"/download_commerce_virtual_order_item");

		return resourceURL;
	}

	public String getDownloadURL(
			CommerceVirtualOrderItem commerceVirtualOrderItem)
		throws Exception {

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			getCPDefinitionVirtualSetting(
				commerceVirtualOrderItem.getCommerceOrderItem());

		if ((cpDefinitionVirtualSetting == null) ||
			!cpDefinitionVirtualSetting.isTermsOfUseRequired()) {

			return String.valueOf(
				getDownloadResourceURL(
					commerceVirtualOrderItem.getCommerceVirtualOrderItemId()));
		}

		LiferayPortletResponse liferayPortletResponse =
			_commerceVirtualOrderItemContentRequestHelper.
				getLiferayPortletResponse();

		PortletURL portletURL = PortletURLBuilder.createRenderURL(
			liferayPortletResponse
		).setMVCRenderCommandName(
			"/commerce_virtual_order_item_content" +
				"/view_commerce_virtual_order_item_terms_of_use"
		).setParameter(
			"commerceVirtualOrderItemId",
			commerceVirtualOrderItem.getCommerceVirtualOrderItemId()
		).setParameter(
			"groupId",
			_commerceVirtualOrderItemContentRequestHelper.getScopeGroupId()
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildPortletURL();

		if (cpDefinitionVirtualSetting.isUseTermsOfUseJournal()) {
			JournalArticle termsOfUseJournalArticle =
				cpDefinitionVirtualSetting.getTermsOfUseJournalArticle();

			portletURL.setParameter(
				"articleId", termsOfUseJournalArticle.getArticleId());
			portletURL.setParameter(
				"version",
				String.valueOf(termsOfUseJournalArticle.getVersion()));
		}
		else {
			portletURL.setParameter(
				"termsOfUseContent",
				cpDefinitionVirtualSetting.getTermsOfUseContent(
					_commerceVirtualOrderItemContentRequestHelper.getLocale()));
		}

		return portletURL.toString();
	}

	public List<KeyValuePair> getKeyValuePairs(
			long cpDefinitionId, String json, Locale locale)
		throws PortalException {

		return _cpInstanceHelper.getKeyValuePairs(cpDefinitionId, json, locale);
	}

	public PortletURL getPortletURL() throws PortalException {
		LiferayPortletResponse liferayPortletResponse =
			_commerceVirtualOrderItemContentRequestHelper.
				getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		String delta = ParamUtil.getString(
			_commerceVirtualOrderItemContentRequestHelper.getRequest(),
			"delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String deltaEntry = ParamUtil.getString(
			_commerceVirtualOrderItemContentRequestHelper.getRequest(),
			"deltaEntry");

		if (Validator.isNotNull(deltaEntry)) {
			portletURL.setParameter("deltaEntry", deltaEntry);
		}

		return portletURL;
	}

	public SearchContainer<CommerceVirtualOrderItem> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			_commerceVirtualOrderItemContentRequestHelper.
				getLiferayPortletRequest(),
			getPortletURL(), null, "no-items-were-found");

		long commerceChannelGroupId =
			_commerceChannelLocalService.getCommerceChannelGroupIdBySiteGroupId(
				_commerceVirtualOrderItemContentRequestHelper.
					getScopeGroupId());

		long commerceAccountId = CommerceUtil.getCommerceAccountId(
			(CommerceContext)_httpServletRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT));

		_searchContainer.setResultsAndTotal(
			() ->
				_commerceVirtualOrderItemLocalService.
					getCommerceVirtualOrderItems(
						commerceChannelGroupId, commerceAccountId,
						_searchContainer.getStart(), _searchContainer.getEnd(),
						new CommerceVirtualOrderItemCreateDateComparator()),
			_commerceVirtualOrderItemLocalService.
				getCommerceVirtualOrderItemsCount(
					commerceChannelGroupId, commerceAccountId));

		return _searchContainer;
	}

	public boolean hasCommerceChannel() throws PortalException {
		HttpServletRequest httpServletRequest =
			_commerceVirtualOrderItemContentRequestHelper.getRequest();

		CommerceContext commerceContext =
			(CommerceContext)httpServletRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		long commerceChannelId = commerceContext.getCommerceChannelId();

		if (commerceChannelId > 0) {
			return true;
		}

		return false;
	}

	private JournalArticleDisplay _articleDisplay;
	private final CommerceChannelLocalService _commerceChannelLocalService;
	private final CommerceVirtualOrderItemContentPortletInstanceConfiguration
		_commerceVirtualOrderItemContentPortletInstanceConfiguration;
	private final CommerceVirtualOrderItemContentRequestHelper
		_commerceVirtualOrderItemContentRequestHelper;
	private final CommerceVirtualOrderItemLocalService
		_commerceVirtualOrderItemLocalService;
	private final CPDefinitionHelper _cpDefinitionHelper;
	private final CPDefinitionVirtualSettingService
		_cpDefinitionVirtualSettingService;
	private final CPInstanceHelper _cpInstanceHelper;
	private long _displayStyleGroupId;
	private final HttpServletRequest _httpServletRequest;
	private SearchContainer<CommerceVirtualOrderItem> _searchContainer;

}