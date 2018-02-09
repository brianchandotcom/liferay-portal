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

package com.liferay.commerce.wish.list.taglib.servlet.taglib;

import com.liferay.commerce.wish.list.constants.CommerceWishListPortletKeys;
import com.liferay.frontend.taglib.soy.servlet.taglib.TemplateRendererTag;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Marco Leo
 */
public class AddToWishListTag extends TemplateRendererTag {

	@Override
	public int doStartTag() {
		String randomNamespace = StringUtil.randomId() + StringPool.UNDERLINE;

		Map<String, Object> context = getContext();

		putValue("id", randomNamespace + "id");

		String label = GetterUtil.getString(
			context.get("label"),
			LanguageUtil.get(request, "add-to-wish-list"));

		putValue("label", label);

		putValue(
			"portletNamespace",
			PortalUtil.getPortletNamespace(
				CommerceWishListPortletKeys.COMMERCE_WISH_LIST_CONTENT));

		putValue("uri", _getURI());

		setTemplateNamespace("AddToWishList.render");

		return super.doStartTag();
	}

	@Override
	public String getModule() {
		return "commerce-wish-list-taglib/add_to_wish_list/AddToWishList.es";
	}

	public void setCPDefinitionId(long cpDefinitionId) {
		putValue("cpDefinitionId", String.valueOf(cpDefinitionId));
	}

	public void setCPInstanceId(long cpInstanceId) {
		putValue("cpInstanceId", String.valueOf(cpInstanceId));
	}

	public void setElementClasses(String elementClasses) {
		putValue("elementClasses", elementClasses);
	}

	public void setLabel(String label) {
		putValue("label", LanguageUtil.get(request, label));
	}

	public void setProductContentId(String productContentId) {
		putValue("productContentId", productContentId);
	}

	private String _getURI() {
		PortletURL portletURL = PortletURLFactoryUtil.create(
			request, CommerceWishListPortletKeys.COMMERCE_WISH_LIST_CONTENT,
			PortletRequest.ACTION_PHASE);

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "addCommerceWishListItem");

		return portletURL.toString();
	}

}