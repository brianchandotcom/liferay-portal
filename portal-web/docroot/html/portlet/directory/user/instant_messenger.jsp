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

<%@ include file="/html/portlet/directory/init.jsp" %>

<%
Contact selContact = (Contact)request.getAttribute("user.selContact");

String aim = selContact.getAimSn();
String icq = selContact.getIcqSn();
String jabber = selContact.getJabberSn();
String skype = selContact.getSkypeSn();
String ym = selContact.getYmSn();
%>

<c:if test="<%= Validator.isNotNull(aim) || Validator.isNotNull(icq) || Validator.isNotNull(jabber) || Validator.isNotNull(skype) || Validator.isNotNull(ym) %>">
	<h3 class="icon-comments"><liferay-ui:message key="instant-messenger" /></h3>

	<dl class="property-list">
		<c:if test="<%= Validator.isNotNull(aim) %>">
			<dt>
				<liferay-ui:message key="aim" />
			</dt>
			<dd>
				<%= HtmlUtil.escape(aim) %>
			</dd>
		</c:if>

		<c:if test="<%= Validator.isNotNull(icq) %>">
			<dt>
				<liferay-ui:message key="icq" />
			</dt>
			<dd>
				<%= HtmlUtil.escape(icq) %>

				<img alt="" class="instant-messenger-logo" src="http://web.icq.com/whitepages/online?icq=<%= HtmlUtil.escapeAttribute(icq) %>&img=5" />
			</dd>
		</c:if>

		<c:if test="<%= Validator.isNotNull(jabber) %>">
			<dt>
				<liferay-ui:message key="jabber" />
			</dt>
			<dd>
				<%= HtmlUtil.escape(jabber) %>
			</dd>
		</c:if>

		<c:if test="<%= Validator.isNotNull(skype) %>">
			<dt>
				<liferay-ui:message key="skype" />
			</dt>
			<dd>
				<%= HtmlUtil.escape(skype) %>
				<a href="callto://<%= HtmlUtil.escapeAttribute(skype) %>"><img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="call-this-user" />" class="instant-messenger-logo" src="http://mystatus.skype.com/smallicon/<%= HtmlUtil.escapeAttribute(skype) %>" /></a>
			</dd>
		</c:if>

		<c:if test="<%= Validator.isNotNull(ym) %>">
			<dt>
				<liferay-ui:message key="yim" />
			</dt>
			<dd>
				<%= HtmlUtil.escape(ym) %>
				<img alt="" class="instant-messenger-logo" src="http://opi.yahoo.com/online?u=<%= HtmlUtil.escapeAttribute(ym) %>&m=g&t=0" />
			</dd>
		</c:if>
	</dl>
</c:if>