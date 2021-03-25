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
String className = (String)request.getAttribute("contact_information.jsp-className");
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
		message="edit"
		url='<%=
			PortletURLBuilder.createRenderURL(
				liferayPortletResponse
			).setMVCPath(
				"/common/edit_address.jsp"
			).setRedirect(
				currentURL
			).setParameter(
				"className", className
			).setParameter(
				"classPK", classPK
			).setParameter(
				"primaryKey", addressId
			).buildString()
		%>'
	/>

	<%
	PortletURL portletURL = PortletURLBuilder.createActionURL(
		renderResponse
	).setActionName(
		"/users_admin/update_contact_information"
	).setRedirect(
		currentURL
	).setParameter(
		"className", className
	).setParameter(
		"classPK", classPK
	).setParameter(
		"listType", ListTypeConstants.ADDRESS
	).setParameter(
		"primaryKey", addressId
	).build();
	%>

	<liferay-ui:icon
		message="make-primary"
		url='<%=
			PortletURLBuilder.create(
				PortletURLUtil.clone(portletURL, renderResponse)
			).setParameter(
				Constants.CMD, "makePrimary"
			).buildString()
		%>'
	/>

	<liferay-ui:icon
		message="remove"
		url="<%=
			PortletURLBuilder.create(
				PortletURLUtil.clone(portletURL, renderResponse)
			).setParameter(
				Constants.CMD, Constants.DELETE
			).buildString()
		%>"
	/>
</liferay-ui:icon-menu>