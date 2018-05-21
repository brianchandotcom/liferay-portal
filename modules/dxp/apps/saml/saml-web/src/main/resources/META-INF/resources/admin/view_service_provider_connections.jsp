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

portletURL.setParameter("tabs1", "service-provider-connections");
%>

<liferay-ui:search-container
	emptyResultsMessage="there-are-no-service-providers"
	headerNames="name"
	iteratorURL="<%= portletURL %>"
	total="<%= GetterUtil.getInteger(renderRequest.getAttribute(SamlWebKeys.SAML_IDP_SP_CONNECTIONS_COUNT)) %>"
>
	<liferay-ui:search-container-results
		results="<%= (List<SamlIdpSpConnection>)renderRequest.getAttribute(SamlWebKeys.SAML_IDP_SP_CONNECTIONS) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.saml.persistence.model.SamlIdpSpConnection"
		escapedModel="<%= true %>"
		keyProperty="samlIdpSpConnectionId"
		modelVar="samlIdpSpConnection"
	>
		<portlet:renderURL var="rowURL">
			<portlet:param name="mvcRenderCommandName" value="/admin/edit_service_provider_connection" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="samlIdpSpConnectionId" value="<%= String.valueOf(samlIdpSpConnection.getSamlIdpSpConnectionId()) %>" />
		</portlet:renderURL>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			name="name"
			property="name"
		/>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			name="saml-entity-id"
			property="samlSpEntityId"
		/>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			name="enabled"
			property="enabled"
		/>

		<liferay-ui:search-container-column-jsp
			align="right"
			cssClass="entry-action"
			path="/admin/service_provider_connection_action.jsp"
			valign="top"
		/>
	</liferay-ui:search-container-row>

	<portlet:renderURL var="addServiceProviderURL">
		<portlet:param name="mvcRenderCommandName" value="/admin" />
		<portlet:param name="mvcPath" value="/admin/edit_service_provider_connection.jsp" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</portlet:renderURL>

	<aui:button-row>
		<aui:button href="<%= addServiceProviderURL %>" label="add-service-provider" value="add-service-provider" />
	</aui:button-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>