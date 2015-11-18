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
Contact selContact = (Contact)request.getAttribute("user.selContact");

String jabberSn = selContact.getJabberSn();
String skypeSn = selContact.getSkypeSn();
%>

<c:if test="<%= Validator.isNotNull(jabberSn) || Validator.isNotNull(skypeSn) %>">
	<h3 class="icon-comments"><liferay-ui:message key="instant-messenger" /></h3>

	<dl class="property-list">
		<c:if test="<%= Validator.isNotNull(jabberSn) %>">
			<dt>
				<liferay-ui:message key="jabber" />
			</dt>
			<dd>
				<%= HtmlUtil.escape(jabberSn) %>
			</dd>
		</c:if>

		<c:if test="<%= Validator.isNotNull(skypeSn) %>">
			<dt>
				<liferay-ui:message key="skype" />
			</dt>
			<dd>
				<%= HtmlUtil.escape(skypeSn) %>
				<a href="callto://<%= HtmlUtil.escapeAttribute(skypeSn) %>"><img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="call-this-user" />" class="instant-messenger-logo" src="http://mystatus.skype.com/smallicon/<%= HtmlUtil.escapeAttribute(skypeSn) %>" /></a>
			</dd>
		</c:if>
	</dl>
</c:if>