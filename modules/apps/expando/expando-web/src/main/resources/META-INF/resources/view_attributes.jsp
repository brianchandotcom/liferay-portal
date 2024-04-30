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

ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(company.getCompanyId(), modelResource);

ExpandoDisplayContext expandoDisplayContext = (ExpandoDisplayContext)request.getAttribute(ExpandoDisplayContext.class.getName());

PortletURL portletURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setMVCRenderCommandName(
	"/expando/view_attributes"
).setRedirect(
	redirect
).setParameter(
	"modelResource", modelResource
).buildPortletURL();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(ResourceActionsUtil.getModelResource(request, modelResource));
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new ExpandoManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, expandoDisplayContext.getSearchContainer()) %>"
	propsTransformer="{ExpandoManagementToolbarPropsTransformer} from expando-web"
/>

<aui:form action="<%= portletURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
	<aui:input name="columnIds" type="hidden" />

	<clay:container-fluid>
		<liferay-site-navigation:breadcrumb
			breadcrumbEntries="<%= expandoDisplayContext.getBreadcrumbEntries() %>"
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

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200 table-title"
				href="<%= rowURL %>"
				name="name"
				value="<%= HtmlUtil.escape(localizedName) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200"
				name="key"
				value="<%= HtmlUtil.escape(name) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200"
				name="type"
				value="<%= LanguageUtil.get(request, ExpandoColumnConstants.getTypeLabel(expandoBridge.getAttributeType(name))) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
				name="hidden"
			>

				<%
				boolean hidden = GetterUtil.getBoolean(typeSettingsUnicodeProperties.getProperty(ExpandoColumnConstants.PROPERTY_HIDDEN));
				%>

				<liferay-ui:message key="<%= String.valueOf(hidden) %>" />
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
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
				path="/expando_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
			paginate="<%= false %>"
		/>
	</liferay-ui:search-container>
</aui:form>