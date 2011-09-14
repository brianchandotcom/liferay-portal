<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
<%@ include file="/html/common/init.jsp" %>

<%@ page import="com.liferay.portal.setup.SetupWizardUtil" %>

<%
boolean propertiesFileCreated = GetterUtil.getBoolean((String)request.getAttribute("setup_wizard_propertiesFileCreated"));

UnicodeProperties properties = (UnicodeProperties)request.getAttribute("setup_wizard_properties");
%>
<liferay-util:buffer var="credentials">
	<p>
		<liferay-ui:message key="after-restarting-you-will-be-able-to-login"/>
	
		<dl class="credentials">
			<dt><liferay-ui:message key="email-address" />:</dt>
			<dd><%= properties.get(PropsKeys.DEFAULT_ADMIN_EMAIL_ADDRESS) %></dd>
			<dt><liferay-ui:message key="password" />:</dt>
			<dd><%= PropsUtil.get("default.admin.password") %></dd>
		</dl>
	</p>
</liferay-util:buffer>

<div id="messages-wrapper">
	<c:choose>
		<c:when test="<%= propertiesFileCreated %>">
			<aui:fieldset cssClass="fieldset-header" label="congratulations">
				<p>
					<liferay-ui:message key="your-configuration-has-finished-sucessfully" />
					<br />
					<span class="tooltip">
						<liferay-ui:message key="this-configuration-has-been-saved-in" arguments='<%= "<span>" + PropsValues.LIFERAY_HOME + StringPool.SLASH + SetupWizardUtil.LIFERAY_SETUP_PROPERTIES_FILE + "</span>" %>'/>
					</span>
				</p>
				<p>
					<liferay-ui:message key="the-application-server-must-be-restarted-to-apply-changes" />
				</p>
				
				<%= credentials %>
				
			</aui:fieldset>
		</c:when>
		<c:otherwise>
			<aui:fieldset cssClass="fieldset-header" label="create-a-configuration-file">
				<p>
					<liferay-ui:message key="sorry-we-were-not-able-to-write-configuration-file" arguments='<%= "<span>" + PropsValues.LIFERAY_HOME + "</span>" %>'/>
				</p>
				
				<%= credentials %>
				
			</aui:fieldset>

			<aui:fieldset cssClass="fieldset-header" label="your-portal-ext-properties">
				<div class="wrapper last">
					<aui:input inputCssClass="properties-text" name="portal-ext" label="" type="textarea" value="<%= properties.toString() %>" wrap="soft" />
				</div>
			</aui:fieldset>
		</c:otherwise>
	</c:choose>
</div>