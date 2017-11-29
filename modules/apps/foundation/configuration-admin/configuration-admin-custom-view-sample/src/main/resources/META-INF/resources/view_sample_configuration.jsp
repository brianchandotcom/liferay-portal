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
String redirect = ParamUtil.getString(request, "redirect");
String pid = ParamUtil.getString(request, "pid");

boolean sampleCheckbox = (boolean)request.getAttribute("sampleCheckbox");
int sampleNumber = (int)request.getAttribute("sampleNumber");
String sampleText = (String)request.getAttribute("sampleText");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);
%>

<portlet:actionURL name="saveConfiguration" var="saveConfigurationActionURL" />

<div class="container-fluid-1280">
	<aui:form action="<%= saveConfigurationActionURL %>" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="pid" type="hidden" value="<%= pid %>" />

		<aui:input label="Sample Checkbox" name="sampleCheckbox" type="checkbox" value="<%= sampleCheckbox %>" />
		<aui:input label="Sample Number" name="sampleNumber" value="<%= sampleNumber %>" />
		<aui:input label="Sample Text" name="sampleText" value="<%= sampleText %>" />

		<aui:button-row>
			<aui:button cssClass="btn-lg" name="save" type="submit" value="save" />
			<aui:button cssClass="btn-lg" href="<%= redirect %>" name="cancel" type="cancel" />
		</aui:button-row>
	</aui:form>
</div>