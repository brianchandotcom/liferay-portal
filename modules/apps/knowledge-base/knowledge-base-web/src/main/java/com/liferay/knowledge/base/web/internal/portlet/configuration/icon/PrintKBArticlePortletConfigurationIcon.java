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

package com.liferay.knowledge.base.web.internal.portlet.configuration.icon;

import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ambrín Chaudhary
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_ADMIN,
		"path=/admin/view_article.jsp", "path=/admin/view_articles.jsp"
	},
	service = PortletConfigurationIcon.class
)
public class PrintKBArticlePortletConfigurationIcon
	extends BaseGetKBArticlePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return _language.get(getLocale(portletRequest), "print");
	}

	@Override
	public String getOnClick(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		try {
			StringBundler sb = new StringBundler(5);

			sb.append("window.open('");

			KBArticle kbArticle = getKBArticle(portletRequest);

			sb.append(
				PortletURLBuilder.create(
					_portal.getControlPanelPortletURL(
						portletRequest, KBPortletKeys.KNOWLEDGE_BASE_ADMIN,
						PortletRequest.RENDER_PHASE)
				).setMVCPath(
					"/admin/print_article.jsp"
				).setParameter(
					"resourceClassNameId", kbArticle.getClassNameId()
				).setParameter(
					"resourcePrimKey", kbArticle.getResourcePrimKey()
				).setParameter(
					"viewMode", Constants.PRINT
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildString());

			sb.append("', '', 'directories=no,height=640,location=no,");
			sb.append("menubar=no,resizable=yes,scrollbars=yes,status=0,");
			sb.append("toolbar=0,width=680');");

			return sb.toString();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return StringPool.BLANK;
		}
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return "javascript:void(0);";
	}

	@Override
	public double getWeight() {
		return 109;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PrintKBArticlePortletConfigurationIcon.class);

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}