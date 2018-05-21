<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
PortletURL portletURL = renderResponse.createRenderURL();
%>

<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>" />

<%
List<SPIDefinition> spiDefinitions = SPIDefinitionServiceUtil.getSPIDefinitions();
%>

<liferay-ui:search-container
	emptyResultsMessage="no-spi-definitions-are-defined"
	iteratorURL="<%= portletURL %>"
	total="<%= spiDefinitions.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= ListUtil.subList(spiDefinitions, searchContainer.getStart(), searchContainer.getEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.resiliency.spi.model.SPIDefinition"
		modelVar="spiDefinition"
	>
		<liferay-portlet:renderURL varImpl="rowURL">
			<portlet:param name="mvcPath" value="/edit_spi_definition.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="backURL" value="<%= currentURL %>" />
			<portlet:param name="spiDefinitionId" value="<%= String.valueOf(spiDefinition.getSpiDefinitionId()) %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:search-container-column-text
			cssClass="spi-status-column"
			name="status"
		>
			<strong class="label <%= SPIAdminConstants.getStatusCssClass(spiDefinition.getStatus()) %>" data-id="<%= String.valueOf(spiDefinition.getSpiDefinitionId()) %>">
				<liferay-ui:message key="<%= spiDefinition.getStatusLabel() %>" />
			</strong>

			<c:if test="<%= Validator.isNotNull(spiDefinition.getStatusMessage()) %>">
				<liferay-ui:icon-help message="<%= spiDefinition.getStatusMessage() %>" />
			</c:if>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			cssClass="spi-name-column"
			href="<%= rowURL %>"
			name="name"
		/>

		<liferay-ui:search-container-column-text
			cssClass="spi-description-column"
			href="<%= rowURL %>"
			name="description"
		/>

		<%
		StringBundler applicationNamesSB = new StringBundler();

		String portletIds = spiDefinition.getPortletIds();

		for (String portletId : StringUtil.split(portletIds)) {
			applicationNamesSB.append(PortalUtil.getPortletTitle(portletId, locale));
			applicationNamesSB.append(StringPool.COMMA_AND_SPACE);
		}

		applicationNamesSB.append(spiDefinition.getServletContextNames());
		%>

		<liferay-ui:search-container-column-text
			cssClass="spi-applications-column"
			href="<%= rowURL %>"
			name="applications"
			value="<%= applicationNamesSB.toString() %>"
		/>

		<liferay-ui:search-container-column-text
			cssClass="spi-connector-port-column"
			href="<%= rowURL %>"
			name="connector-port"
			property="connectorPort"
		/>

		<liferay-ui:search-container-column-jsp
			align="right"
			cssClass="entry-action"
			path="/spi_definition_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<aui:script use="liferay-spi-definition">
	new Liferay.Portlet.SPIDefinition(
		{
			namespace: '<portlet:namespace />',
			spiDefinitionActionURL: '<portlet:renderURL windowState="<%= LiferayWindowState.MAXIMIZED.toString() %>"><portlet:param name="mvcPath" value="/view.jsp" /></portlet:renderURL>'
		}
	);
</aui:script>