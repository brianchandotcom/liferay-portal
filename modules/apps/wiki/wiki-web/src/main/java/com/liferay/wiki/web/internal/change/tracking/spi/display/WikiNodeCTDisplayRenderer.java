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

package com.liferay.wiki.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiNode;

import java.util.Locale;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Noor Najjar
 */
@Component(service = CTDisplayRenderer.class)
public class WikiNodeCTDisplayRenderer extends BaseCTDisplayRenderer<WikiNode> {

	@Override
	public String getEditURL(
		HttpServletRequest httpServletRequest, WikiNode wikiNode) {

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, WikiPortletKeys.WIKI_ADMIN,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/wiki/edit_node"
		).setRedirect(
			_portal.getCurrentURL(httpServletRequest)
		).setBackURL(
			ParamUtil.getString(httpServletRequest, "backURL")
		).setParameter(
			"nodeId", wikiNode.getNodeId()
		).buildString();
	}

	@Override
	public Class<WikiNode> getModelClass() {
		return WikiNode.class;
	}

	@Override
	public String getTitle(Locale locale, WikiNode wikiNode) {
		return wikiNode.getName();
	}

	@Override
	protected void buildDisplay(DisplayBuilder<WikiNode> displayBuilder) {
		WikiNode wikiNode = displayBuilder.getModel();

		displayBuilder.display(
			"name", wikiNode.getName()
		).display(
			"description", wikiNode.getDescription()
		).display(
			"created-by",
			() -> {
				String userName = wikiNode.getUserName();

				if (Validator.isNotNull(userName)) {
					return userName;
				}

				return null;
			}
		).display(
			"create-date", wikiNode.getCreateDate()
		).display(
			"last-modified", wikiNode.getModifiedDate()
		);
	}

	@Reference
	private Portal _portal;

}