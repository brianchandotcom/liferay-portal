<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String modelResource = ParamUtil.getString(request, "modelResource");

String modelResourceName = ResourceActionsUtil.getModelResource(request, modelResource);

ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(company.getCompanyId(), modelResource);

List<String> attributeNames = Collections.list(expandoBridge.getAttributeNames());

ExpandoDisplayContext expandoDisplayContext = new ExpandoDisplayContext(request);

PortletURL portletURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setMVCPath(
	"/view_attributes.jsp"
).setRedirect(
	redirect
).setParameter(
	"modelResource", modelResource
).buildPortletURL();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(modelResourceName);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "custom-field"), String.valueOf(renderResponse.createRenderURL()));

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "view-attributes"), null);
%>

<clay:navigation-bar
	navigationItems='<%= expandoDisplayContext.getNavigationItems("fields") %>'
/>

<clay:management-toolbar
	actionDropdownItems="<%= expandoDisplayContext.getActionDropdownItems() %>"
	additionalProps="<%= expandoDisplayContext.getAdditionalProps() %>"
	creationMenu="<%= expandoDisplayContext.getCreationMenu() %>"
	disabled="<%= attributeNames.size() == 0 %>"
	itemsTotal="<%= attributeNames.size() %>"
	propsTransformer="js/ExpandoManagementToolbarPropsTransformer"
	searchContainerId="customFields"
	selectable="<%= true %>"
	showCreationMenu="<%= expandoDisplayContext.showCreationMenu() %>"
	showSearch="<%= false %>"
/>

<aui:form action="<%= portletURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
	<aui:input name="columnIds" type="hidden" />

	<clay:container-fluid>
		<liferay-site-navigation:breadcrumb
			breadcrumbEntries="<%= BreadcrumbEntriesUtil.getBreadcrumbEntries(request, false, false, false, true, true) %>"
		/>
	</clay:container-fluid>

	<liferay-ui:search-container
		emptyResultsMessage='<%= LanguageUtil.format(request, "no-custom-fields-are-defined-for-x", HtmlUtil.escape(modelResourceName), false) %>'
		id="customFields"
		iteratorURL="<%= portletURL %>"
		rowChecker="<%= new CustomFieldChecker(renderRequest, renderResponse) %>"
		total="<%= attributeNames.size() %>"
	>
		<liferay-ui:search-container-results
			results="<%= attributeNames %>"
		/>

		<liferay-ui:search-container-row
			className="java.lang.String"
			modelVar="name"
			stringKey="<%= true %>"
		>

			<%
			int type = expandoBridge.getAttributeType(name);

			ExpandoColumn expandoColumn = ExpandoColumnLocalServiceUtil.getDefaultTableColumn(company.getCompanyId(), modelResource, name);

			UnicodeProperties typeSettingsUnicodeProperties = expandoColumn.getTypeSettingsProperties();
			%>

			<portlet:renderURL var="rowURL">
				<portlet:param name="mvcPath" value="/edit/expando.jsp" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="columnId" value="<%= String.valueOf(expandoColumn.getColumnId()) %>" />
				<portlet:param name="modelResource" value="<%= modelResource %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-row-parameter
				name="expandoColumn"
				value="<%= expandoColumn %>"
			/>

			<liferay-ui:search-container-row-parameter
				name="modelResource"
				value="<%= modelResource %>"
			/>

			<%@ include file="/attribute_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
			paginate="<%= false %>"
		/>
	</liferay-ui:search-container>
</aui:form>