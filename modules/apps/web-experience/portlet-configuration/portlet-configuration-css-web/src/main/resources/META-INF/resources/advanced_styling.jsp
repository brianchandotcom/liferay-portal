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
JSONObject advancedDataJSONObject = portletSetupJSONObject.getJSONObject("advancedData");
%>

<div class="alert alert-info">
	<p>
		<liferay-ui:message key="your-current-portlet-information-is-as-follows" />
	</p>

	<p>
		<liferay-ui:message key="portlet-id" />: <strong>#portlet_<%= portletResource %></strong>
	</p>

	<p>
		<liferay-ui:message key="portlet-classes" />: <strong># + portletClasses + </strong>
	</p>
</div>

<aui:input label="enter-your-custom-css-class-names" name="customCSSClassName" type="text" value='<%= advancedDataJSONObject.getString("customCSSClassName") %>' />

<aui:input label="enter-your-custom-css" name="customCSS" type="textarea" value='<%= advancedDataJSONObject.getString("customCSS") %>' />