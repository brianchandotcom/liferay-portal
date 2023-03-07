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

package com.liferay.knowledge.base.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.web.internal.constants.KBWebKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Vy Bui
 */
@Component(service = CTDisplayRenderer.class)
public class KBArticleCTDisplayRenderer
	extends BaseCTDisplayRenderer<KBArticle> {

	@Override
	public String getEditURL(
		HttpServletRequest httpServletRequest, KBArticle kbArticle) {

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, KBWebKeys.KNOWLEDGE_BASE_ADMIN_PORTLET,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/knowledge_base/view_kb_article"
		).setRedirect(
			_portal.getCurrentURL(httpServletRequest)
		).setBackURL(
			ParamUtil.getString(httpServletRequest, "backURL")
		).setParameter(
			"kbArticleId", kbArticle.getKbArticleId()
		).buildString();
	}

	@Override
	public Class<KBArticle> getModelClass() {
		return KBArticle.class;
	}

	@Override
	public String getTitle(Locale locale, KBArticle model)
		throws PortalException {

		return model.getTitle();
	}

	@Override
	protected void buildDisplay(DisplayBuilder<KBArticle> displayBuilder) {
		KBArticle kbArticle = displayBuilder.getModel();

		displayBuilder.display(
			"name", kbArticle.getTitle()
		).display(
			"content", kbArticle.getContent()
		).display(
			"description", kbArticle.getDescription()
		).display(
			"created-by",
			() -> {
				String userName = kbArticle.getUserName();

				if (Validator.isNotNull(userName)) {
					return userName;
				}

				return null;
			}
		).display(
			"create-date", kbArticle.getCreateDate()
		).display(
			"last-modified", kbArticle.getModifiedDate()
		);
	}

	@Reference
	private Portal _portal;

}