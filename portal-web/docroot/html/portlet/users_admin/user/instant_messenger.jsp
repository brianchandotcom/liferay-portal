<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/users_admin/init.jsp" %>

<%
Contact selContact = (Contact)request.getAttribute("user.selContact");
%>

<h3><liferay-ui:message key="instant-messenger" /></h3>

<c:choose>
	<c:when test="<%= selContact != null %>">
		<aui:model-context bean="<%= selContact %>" model="<%= Contact.class %>" />

		<aui:fieldset>
			<div class="instant-messenger">
				<aui:input label="aim" name="aimSn" />
			</div>

			<div class="instant-messenger">
				<aui:input label="icq" name="icqSn" />

				<c:if test="<%= Validator.isNotNull(selContact.getIcqSn()) %>">
					<img alt="" src="http://web.icq.com/whitepages/online?icq=<%= HtmlUtil.escapeAttribute(selContact.getIcqSn()) %>&img=5" />
				</c:if>
			</div>

			<div class="instant-messenger">
				<aui:input label="jabber" name="jabberSn" />
			</div>

			<div class="instant-messenger">
				<aui:input label="skype" name="skypeSn" />

				<c:if test="<%= Validator.isNotNull(selContact.getSkypeSn()) %>">
					<a href="callto://<%= HtmlUtil.escapeAttribute(selContact.getSkypeSn()) %>"><img alt="<liferay-ui:message key="call-this-user" />" src="http://mystatus.skype.com/smallicon/<%= HtmlUtil.escapeAttribute(selContact.getSkypeSn()) %>" /></a>
				</c:if>
			</div>

			<div class="instant-messenger">
				<aui:input label="windows-live-messenger" name="msnSn" />
			</div>

			<div class="instant-messenger">
				<aui:input label="yim" name="ymSn" />

				<c:if test="<%= Validator.isNotNull(selContact.getYmSn()) %>">
					<img alt="" src="http://opi.yahoo.com/online?u=<%= HtmlUtil.escapeAttribute(selContact.getYmSn()) %>&m=g&t=0" />
				</c:if>
			</div>
		</aui:fieldset>
	</c:when>
	<c:otherwise>
		<div class="alert alert-info"><liferay-ui:message key="this-section-will-be-editable-after-creating-the-user" /></div>
	</c:otherwise>
</c:choose>