/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.frontend.taglib.clay.servlet.taglib;

import com.liferay.frontend.taglib.clay.servlet.taglib.BaseVerticalCard;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
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
				_log.debug(exception);
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