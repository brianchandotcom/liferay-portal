<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewPortalPropertiesDisplayContext viewPortalPropertiesDisplayContext = new ViewPortalPropertiesDisplayContext(request, liferayPortletRequest, renderResponse);
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new ViewPortalPropertiesManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, viewPortalPropertiesDisplayContext.getSearchContainer()) %>"
/>

<clay:container-fluid>
	<liferay-ui:search-container
		searchContainer="<%= viewPortalPropertiesDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="java.util.Map.Entry"
			modelVar="entry"
		>

			<%
			String propertyKey = (String)entry.getKey();
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				name="property"
				value="<%= HtmlUtil.escape(StringUtil.shorten(propertyKey, 80)) %>"
			/>

			<%
			String propertyValue = (String)entry.getValue();
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				name="value"
			>
				<c:if test="<%= Validator.isNotNull(propertyValue) %>">
					<span class="lfr-portal-tooltip" title="<%= HtmlUtil.escape(propertyValue) %>">
						<%= HtmlUtil.escape(StringUtil.shorten(propertyValue, 80)) %>
					</span>
				</c:if>
			</liferay-ui:search-container-column-text>

			<%
			List<String> overriddenProperties = viewPortalPropertiesDisplayContext.getOverriddenProperties();

			boolean overriddenPropertyValue = overriddenProperties.contains(propertyKey);

			String message = LanguageUtil.get(request, overriddenPropertyValue ? "the-value-of-this-property-was-overridden-using-the-control-panel-and-is-stored-in-the-database" : "the-value-of-this-property-is-read-from-a-portal.properties-file-or-one-of-its-extension-files");
			%>

			<liferay-ui:search-container-column-text
				name="source"
			>
				<clay:icon
					aria-label="<%= message %>"
					cssClass="lfr-portal-tooltip"
					data-title="<%= message %>"
					symbol='<%= overriddenPropertyValue ? "hdd" : "document" %>'
					tabindex="0"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>

	<aui:button-row>
		<aui:button cssClass="save-server-button" data-cmd="updatePortalProperties" value="save" />
	</aui:button-row>
</clay:container-fluid>