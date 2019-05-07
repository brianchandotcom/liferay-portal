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

package com.liferay.knowledge.base.item.selector.web.internal.display.context;

import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.item.selector.ItemSelectorReturnTypeResolverHandler;
import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.item.selector.criterion.KBAttachmentItemSelectorCriterion;
import com.liferay.knowledge.base.item.selector.web.internal.KBAttachmentItemSelectorView;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roberto Díaz
 */
public class KBAttachmentItemSelectorViewDisplayContext {

	public KBAttachmentItemSelectorViewDisplayContext(
		KBAttachmentItemSelectorCriterion kbAttachmentItemSelectorCriterion,
		KBAttachmentItemSelectorView kbAttachmentItemSelectorView,
		ItemSelectorReturnTypeResolverHandler
			itemSelectorReturnTypeResolverHandler,
		String itemSelectedEventName, boolean search, PortletURL portletURL) {

		_kbAttachmentItemSelectorCriterion = kbAttachmentItemSelectorCriterion;
		_kbAttachmentItemSelectorView = kbAttachmentItemSelectorView;
		_itemSelectorReturnTypeResolverHandler =
			itemSelectorReturnTypeResolverHandler;
		_itemSelectedEventName = itemSelectedEventName;
		_search = search;
		_portletURL = portletURL;
	}

	public long getAttachmentsFolderId() throws PortalException {
		KBArticle kbArticle = KBArticleLocalServiceUtil.getLatestKBArticle(
			_kbAttachmentItemSelectorCriterion.getResourcePrimKey(),
			WorkflowConstants.STATUS_APPROVED);

		return kbArticle.getAttachmentsFolderId();
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public ItemSelectorReturnTypeResolver getItemSelectorReturnTypeResolver() {
		return _itemSelectorReturnTypeResolverHandler.
			getItemSelectorReturnTypeResolver(
				_kbAttachmentItemSelectorCriterion,
				_kbAttachmentItemSelectorView, FileEntry.class);
	}

	public KBAttachmentItemSelectorCriterion
		getKBAttachmentItemSelectorCriterion() {

		return _kbAttachmentItemSelectorCriterion;
	}

	public PortletURL getPortletURL(
			HttpServletRequest httpServletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortletException {

		PortletURL portletURL = PortletURLUtil.clone(
			_portletURL, liferayPortletResponse);

		portletURL.setParameter(
			"selectedTab",
			String.valueOf(getTitle(httpServletRequest.getLocale())));

		return portletURL;
	}

	public String getTitle(Locale locale) {
		return _kbAttachmentItemSelectorView.getTitle(locale);
	}

	public PortletURL getUploadURL(
		LiferayPortletResponse liferayPortletResponse) {

		PortletURL portletURL = liferayPortletResponse.createActionURL(
			KBPortletKeys.KNOWLEDGE_BASE_ADMIN);

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "uploadKBArticleAttachments");
		portletURL.setParameter(
			"resourcePrimKey",
			String.valueOf(
				_kbAttachmentItemSelectorCriterion.getResourcePrimKey()));

		return portletURL;
	}

	public boolean isSearch() {
		return _search;
	}

	private final String _itemSelectedEventName;
	private final ItemSelectorReturnTypeResolverHandler
		_itemSelectorReturnTypeResolverHandler;
	private final KBAttachmentItemSelectorCriterion
		_kbAttachmentItemSelectorCriterion;
	private final KBAttachmentItemSelectorView _kbAttachmentItemSelectorView;
	private final PortletURL _portletURL;
	private final boolean _search;

}