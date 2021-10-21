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
ViewSXPElementsDisplayContext viewSXPElementsDisplayContext = (ViewSXPElementsDisplayContext)request.getAttribute(SXPWebKeys.VIEW_SXP_ELEMENTS_DISPLAY_CONTEXT);
%>

<portlet:actionURL name="/sxp_blueprint_admin/delete_sxp_element" var="deleteSXPElementURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<portlet:actionURL name="/sxp_blueprint_admin/edit_sxp_element" var="hideSXPElementURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="<%= Constants.CMD %>" value="hide" />
	<portlet:param name="hidden" value="<%= Boolean.TRUE.toString() %>" />
</portlet:actionURL>

<portlet:actionURL name="/sxp_blueprint_admin/edit_sxp_element" var="showSXPElementURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="<%= Constants.CMD %>" value="hide" />
	<portlet:param name="hidden" value="<%= Boolean.FALSE.toString() %>" />
</portlet:actionURL>

<clay:management-toolbar
	additionalProps='<%=
		HashMapBuilder.<String, Object>put(
			"deleteSXPElementURL", deleteSXPElementURL
		).put(
			"hideSXPElementURL", hideSXPElementURL
		).put(
			"showSXPElementURL", showSXPElementURL
		).build()
	%>'
	managementToolbarDisplayContext="<%= (ViewSXPElementsManagementToolbarDisplayContext)request.getAttribute(SXPWebKeys.VIEW_SXP_ELEMENTS_MANAGEMENT_TOOLBAR_DISPLAY_CONTEXT) %>"
	propsTransformer="sxp_blueprint_admin/js/view_sxp_elements/SXPElementEntriesManagementToolbarPropsTransformer"
	searchContainerId="sxpElementEntries"
	supportsBulkActions="<%= true %>"
/>

<clay:container-fluid>
	<aui:form method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<liferay-ui:search-container
			cssClass="blueprints-search-container"
			id="sxpElementEntries"
			searchContainer="<%= viewSXPElementsDisplayContext.getSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.search.experiences.model.SXPElement"
				keyProperty="sxpElementId"
				modelVar="sxpElement"
			>
				<%@ include file="/sxp_blueprint_admin/sxp_element_search_columns.jspf" %>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="<%= viewSXPElementsDisplayContext.getDisplayStyle() %>"
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</clay:container-fluid>