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

ExpandoDisplayContext expandoDisplayContext = new ExpandoDisplayContext(request, renderRequest, renderResponse);

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
	propsTransformer="{ExpandoManagementToolbarPropsTransformer} from expando-web"
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
		searchContainer="<%= expandoDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="java.lang.String"
			modelVar="name"
			stringKey="<%= true %>"
		>

			<%
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

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200 table-title"
				name="name"
			>

				<%
				String localizedName = name;

				boolean propertyLocalizeFieldName = GetterUtil.getBoolean(typeSettingsUnicodeProperties.getProperty(ExpandoColumnConstants.PROPERTY_LOCALIZE_FIELD_NAME), true);

				if (propertyLocalizeFieldName) {
					localizedName = LanguageUtil.get(request, name);

					if (name.equals(localizedName)) {
						localizedName = TextFormatter.format(name, TextFormatter.J);
					}
				}
				%>

				<a href="<%= rowURL %>"><strong><%= HtmlUtil.escape(localizedName) %></strong></a>

				<br />
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200"
				href="<%= rowURL %>"
				name="key"
				value="<%= HtmlUtil.escape(name) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200"
				href="<%= rowURL %>"
				name="type"
				value="<%= LanguageUtil.get(request, ExpandoColumnConstants.getTypeLabel(expandoBridge.getAttributeType(name))) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
				href="<%= rowURL %>"
				name="hidden"
			>

				<%
				boolean hidden = GetterUtil.getBoolean(typeSettingsUnicodeProperties.getProperty(ExpandoColumnConstants.PROPERTY_HIDDEN));
				%>

				<liferay-ui:message key="<%= String.valueOf(hidden) %>" />
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
				href="<%= rowURL %>"
				name="searchable"
			>

				<%
				int indexType = GetterUtil.getInteger(typeSettingsUnicodeProperties.getProperty(ExpandoColumnConstants.INDEX_TYPE));
				%>

				<c:choose>
					<c:when test="<%= indexType == ExpandoColumnConstants.INDEX_TYPE_KEYWORD %>">
						<liferay-ui:message key="as-keyword" />
					</c:when>
					<c:when test="<%= indexType == ExpandoColumnConstants.INDEX_TYPE_TEXT %>">
						<liferay-ui:message key="as-text" />
					</c:when>
					<c:otherwise>
						<liferay-ui:message key="not-searchable" />
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-jsp
				cssClass="autofit-col"
				path="/expando_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
			paginate="<%= false %>"
		/>
	</liferay-ui:search-container>
</aui:form>