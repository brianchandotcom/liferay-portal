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

package com.liferay.layout.admin.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseVerticalCard;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class CollectionProvidersVerticalCard extends BaseVerticalCard {

	public CollectionProvidersVerticalCard(
		long groupId, InfoCollectionProvider<?> infoCollectionProvider,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		super(null, renderRequest, null);

		_groupId = groupId;
		_infoCollectionProvider = infoCollectionProvider;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
	}

	@Override
	public String getCssClass() {
		return "select-collection-action-option card-interactive " +
			"card-interactive-secondary";
	}

	@Override
	public Map<String, String> getDynamicAttributes() {
		Map<String, String> data = new HashMap<>();

		try {
			data.put(
				"data-select-layout-master-layout-url",
				PortletURLBuilder.createRenderURL(
					_renderResponse
				).setMVCPath(
					"/select_layout_master_layout.jsp"
				).setRedirect(
					ParamUtil.getString(_httpServletRequest, "redirect")
				).setBackURL(
					themeDisplay.getURLCurrent()
				).setParameter(
					"collectionPK", _infoCollectionProvider.getKey()
				).setParameter(
					"collectionType",
					InfoListProviderItemSelectorReturnType.class.getName()
				).setParameter(
					"groupId", _groupId
				).setParameter(
					"privateLayout",
					ParamUtil.getBoolean(_httpServletRequest, "privateLayout")
				).setParameter(
					"selPlid", ParamUtil.getLong(_httpServletRequest, "selPlid")
				).buildString());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		data.put("role", "button");
		data.put("tabIndex", "0");

		return data;
	}

	@Override
	public String getIcon() {
		return "list";
	}

	@Override
	public String getImageSrc() {
		return StringPool.BLANK;
	}

	@Override
	public String getSubtitle() {
		String className = _infoCollectionProvider.getCollectionItemClassName();

		if (Validator.isNotNull(className)) {
			return ResourceActionsUtil.getModelResource(
				themeDisplay.getLocale(), className);
		}

		return StringPool.BLANK;
	}

	@Override
	public String getTitle() {
		return _infoCollectionProvider.getLabel(themeDisplay.getLocale());
	}

	@Override
	public Boolean isFlushHorizontal() {
		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CollectionProvidersVerticalCard.class);

	private final long _groupId;
	private final HttpServletRequest _httpServletRequest;
	private final InfoCollectionProvider<?> _infoCollectionProvider;
	private final RenderResponse _renderResponse;

}