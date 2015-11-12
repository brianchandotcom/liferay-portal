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
ConfigurationModel configurationModel = (ConfigurationModel)request.getAttribute(ConfigurationAdminWebKeys.CONFIGURATION_MODEL);
String ddmFormHTML = (String)request.getAttribute(DDMWebKeys.DYNAMIC_DATA_MAPPING_FORM_HTML);

PortletURL portletURL = renderResponse.createRenderURL();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(portletURL.toString());

renderResponse.setTitle(configurationModel.getName());
%>

<portlet:actionURL name="bindConfiguration" var="bindConfigActionURL" />
<portlet:actionURL name="deleteConfiguration" var="deleteConfigActionURL" />

<div class="container-fluid-1280">
	<aui:form action="<%= bindConfigActionURL %>" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= portletURL %>" />
		<aui:input name="factoryPid" type="hidden" value="<%= configurationModel.getFactoryPid() %>" />
		<aui:input name="pid" type="hidden" value="<%= configurationModel.getID() %>" />

		<div class="lfr-ddm-container" id="lfr-ddm-container">
			<%= ddmFormHTML %>
		</div>

		<aui:button-row>
			<c:choose>
				<c:when test="<%= configurationModel.getConfiguration() != null %>">
					<aui:button type="submit" value="update" />

					<aui:button onClick='<%= renderResponse.getNamespace() + "deleteConfig();" %>' type="button" value="delete" />
				</c:when>
				<c:otherwise>
					<aui:button type="submit" value="save" />
				</c:otherwise>
			</c:choose>

			<aui:button href="<%= portletURL.toString() %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace />deleteConfig() {
		var actionURL = "<%= deleteConfigActionURL.toString() %>";

		<portlet:namespace />setDDMFieldNamespaceAndSubmit(actionURL);
	}

	function <portlet:namespace />setDDMFieldNamespaceAndSubmit(actionURL) {
		if (actionURL) {
			document.<portlet:namespace />fm.action = actionURL;
		}

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>