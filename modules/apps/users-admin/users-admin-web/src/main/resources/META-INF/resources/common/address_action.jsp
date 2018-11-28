<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
String mvcActionPath = (String)request.getAttribute("contact_information.jsp-mvcActionPath");

long classPK = (long)request.getAttribute("contact_information.jsp-classPK");

long addressId = ParamUtil.getLong(request, "addressId");
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<liferay-ui:icon
		cssClass="modify-address-link"
		data="<%=
			new HashMap<String, Object>() {
				{
					put("primary-key", String.valueOf(addressId));
					put("title", LanguageUtil.get(request, "edit-address"));
				}
			}
		%>"
		message="edit"
		url="javascript:;"
	/>

	<%
	PortletURL portletURL = renderResponse.createActionURL();

	portletURL.setParameter(ActionRequest.ACTION_NAME, mvcActionPath);
	portletURL.setParameter("redirect", currentURL);
	portletURL.setParameter("classPK", String.valueOf(classPK));
	portletURL.setParameter("primaryKey", String.valueOf(addressId));
	portletURL.setParameter("listType", ListTypeConstants.ADDRESS);

	PortletURL makePrimaryURL = PortletURLUtil.clone(portletURL, renderResponse);

	makePrimaryURL.setParameter(Constants.CMD, "makePrimary");
	%>

	<liferay-ui:icon
		message="make-primary"
		url="<%= makePrimaryURL.toString() %>"
	/>

	<%
	PortletURL removeAddressURL = PortletURLUtil.clone(portletURL, renderResponse);

	removeAddressURL.setParameter(Constants.CMD, Constants.DELETE);
	%>

	<liferay-ui:icon
		message="remove"
		url="<%= removeAddressURL.toString() %>"
	/>
</liferay-ui:icon-menu>