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

package com.liferay.chat.web.portlet;

import com.liferay.chat.web.constants.ChatPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Map;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-chat",
		"com.liferay.portlet.icon=/icons/chat.png",
		"com.liferay.portlet.system=true",
		"com.liferay.portlet.use-default-template=false",
		"javax.portlet.display-name=Chat", "javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + ChatPortletKeys.CHAT,
		"javax.portlet.portlet-info.keywords=Chat",
		"javax.portlet.portlet-info.short-title=Chat",
		"javax.portlet.portlet-info.title=Chat",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class ChatPortlet extends MVCPortlet {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		if (!hasPortletId()) {
			addPortletIdLayoutStaticPortletsAll();
		}
	}

	protected void addPortletIdLayoutStaticPortletsAll() {
		String[] layoutStaticPortletsAll =
			PropsValues.LAYOUT_STATIC_PORTLETS_ALL;

		layoutStaticPortletsAll = ArrayUtil.append(
			layoutStaticPortletsAll, ChatPortletKeys.CHAT);

		PropsUtil.set(
			PropsKeys.LAYOUT_STATIC_PORTLETS_ALL,
			StringUtil.merge(layoutStaticPortletsAll));

		PropsValues.LAYOUT_STATIC_PORTLETS_ALL = layoutStaticPortletsAll;
	}

	@Deactivate
	@Modified
	protected void deactivate(Map<String, Object> properties) {
		if (hasPortletId()) {
			removePortletIdLayoutStaticPortletsAll();
		}
	}

	protected boolean hasPortletId() {
		return ArrayUtil.contains(
			PropsValues.LAYOUT_STATIC_PORTLETS_ALL, ChatPortletKeys.CHAT,
			false);
	}

	protected void removePortletIdLayoutStaticPortletsAll() {
		String[] layoutStaticPortletsAll =
			PropsValues.LAYOUT_STATIC_PORTLETS_ALL;

		layoutStaticPortletsAll = ArrayUtil.remove(
			layoutStaticPortletsAll, ChatPortletKeys.CHAT);

		PropsUtil.set(
			PropsKeys.LAYOUT_STATIC_PORTLETS_ALL,
			StringUtil.merge(layoutStaticPortletsAll));

		PropsValues.LAYOUT_STATIC_PORTLETS_ALL = layoutStaticPortletsAll;
	}

}