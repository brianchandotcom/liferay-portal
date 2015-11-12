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
ConfigurationModel configurationModel = (ConfigurationModel)request.getAttribute("configurationModel");
String ddmFormHTML = (String)request.getAttribute("DYNAMIC_DATA_MAPPING_FORM_HTML");

PortletURL redirectURL = renderResponse.createRenderURL();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirectURL.toString());

renderResponse.setTitle(configurationModel.getName());
%>

<portlet:actionURL name="bindConfiguration" var="bindConfigActionURL" />
<portlet:actionURL name="deleteConfiguration" var="deleteConfigActionURL" />

<div class="container-fluid-1280">
	<aui:form action="<%= bindConfigActionURL %>" method="post" name="fm">
		<aui:input
			name="redirect"
			type="hidden"
			value="<%= redirectURL %>"
		/>
		<aui:input
			name="pid"
			type="hidden"
			value="<%= configurationModel.getID() %>"
		/>
		<aui:input
			name="factoryPid"
			type="hidden"
			value="<%= configurationModel.getFactoryPid() %>"
		/>

		<div class="lfr-ddm-container" id="lfr-ddm-container">
			<%= ddmFormHTML %>
		</div>

		<aui:button-row>
			<c:choose>
				<c:when test="<%= configurationModel.getConfiguration() != null %>">
					<aui:button
						type="submit"
						value="update"
					/>

					<%
					String deleteAttributesOnClickValue = renderResponse.getNamespace() + "deleteConfig();";
					%>

					<aui:button
						onClick="<%= deleteAttributesOnClickValue %>"
						type="button"
						value="delete"
					/>
				</c:when>
				<c:otherwise>
					<aui:button
						type="submit"
						value="save"
					/>
				</c:otherwise>
			</c:choose>

			<aui:button
				href="<%= redirectURL.toString() %>"
				type="cancel"
			/>
		</aui:button-row>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace />setDDMFieldNamespaceAndSubmit(actionURL) {
		if (actionURL) {
			document.<portlet:namespace />fm.action = actionURL;
		}

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />deleteConfig() {
		var actionURL = "<%= deleteConfigActionURL.toString() %>";

		<portlet:namespace />setDDMFieldNamespaceAndSubmit(actionURL);
	}
</aui:script>