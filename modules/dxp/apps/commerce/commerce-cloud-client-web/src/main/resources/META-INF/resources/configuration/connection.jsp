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
EditConfigurationDisplayContext editConfigurationDisplayContext = (EditConfigurationDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceCloudClientConfiguration commerceCloudClientConfiguration = editConfigurationDisplayContext.getCommerceCloudClientConfiguration();
String redirect = editConfigurationDisplayContext.getViewCategoryURL();
%>

<aui:input name="<%= Constants.CMD %>" type="hidden" value="connection" />

<div class="sheet">
	<aui:fieldset>
		<aui:input name="serverUrl" value="<%= commerceCloudClientConfiguration.serverUrl() %>">
			<aui:validator name="url" />
		</aui:input>

		<aui:input name="projectId" value="<%= commerceCloudClientConfiguration.projectId() %>" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</div>