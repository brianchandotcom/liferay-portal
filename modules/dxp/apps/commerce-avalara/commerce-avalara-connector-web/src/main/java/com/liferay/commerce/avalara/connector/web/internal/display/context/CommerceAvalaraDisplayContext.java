/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.avalara.connector.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Katie Nesterovich
 */
public class CommerceAvalaraDisplayContext {

	public CommerceAvalaraDisplayContext(
		Language language, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_language = language;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public List<NavigationItem> getNavigationItems() {
		String toolbarItem = ParamUtil.getString(
			_renderRequest, "toolbarItem", "view-credentials");

		return NavigationItemList.of(
			NavigationItemBuilder.setActive(
				toolbarItem.equals("view-credentials")
			).setHref(
				PortletURLBuilder.create(
					_renderResponse.createRenderURL()
				).setMVCPath(
					"/view.jsp"
				).setParameter(
					"toolbarItem", "view-credentials"
				).setParameter(
					"type", getType()
				).buildString()
			).setLabel(
				_language.get(_renderRequest.getLocale(), "credentials")
			).build());
	}

	public int getType() {
		return ParamUtil.getInteger(_renderRequest, "type");
	}

	private final Language _language;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}