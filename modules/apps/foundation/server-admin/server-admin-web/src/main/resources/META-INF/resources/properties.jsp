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
String[] tabs2Names = new String[] {"system-properties", "portal-properties"};

if (!ArrayUtil.contains(tabs2Names, tabs2)) {
	tabs2 = tabs2Names[0];
}

String keywords = ParamUtil.getString(request, "keywords");

PortletURL serverURL = renderResponse.createRenderURL();

serverURL.setParameter("mvcRenderCommandName", "/server_admin/view");
serverURL.setParameter("tabs1", tabs1);
serverURL.setParameter("tabs2", tabs2);
%>

<div class="server-admin-tabs">
	<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
		<aui:nav cssClass="navbar-nav">

			<%
			for (String tabs2Name : tabs2Names) {
				serverURL.setParameter("tabs2", tabs2Name);
			%>

				<aui:nav-item href="<%= serverURL.toString() %>" label="<%= tabs2Name %>" selected="<%= tabs2.equals(tabs2Name) %>" />

			<%
			}

			serverURL.setParameter("tabs2", tabs2);
			%>

		</aui:nav>

		<aui:nav-bar-search>
			<liferay-ui:input-search autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" markupView="lexicon" placeholder='<%= LanguageUtil.get(request, "keywords") %>' />
		</aui:nav-bar-search>
	</aui:nav-bar>

	<%
	Map<String, String> filteredProperties = new TreeMap<String, String>();

	Properties properties = null;

	if (tabs2.equals("portal-properties")) {
		properties = PropsUtil.getProperties(true);
	}
	else {
		properties = System.getProperties();
	}

	for (Map.Entry<Object, Object> entry : properties.entrySet()) {
		String property = (String)entry.getKey();
		String value = StringPool.BLANK;

		if (ArrayUtil.contains(PropsValues.ADMIN_OBFUSCATED_PROPERTIES, property)) {
			value = StringPool.EIGHT_STARS;
		}
		else {
			value = (String)entry.getValue();
		}

		if (Validator.isNull(keywords) || property.contains(keywords) || value.contains(keywords)) {
			filteredProperties.put(property, value);
		}
	}

	List filteredPropertiesList = ListUtil.fromCollection(filteredProperties.entrySet());

	PortletPreferences serverPortletPreferences = PrefsPropsUtil.getPreferences();

	Map<String, String[]> serverPortletPreferencesMap = serverPortletPreferences.getMap();

	PortletPreferences companyPortletPreferences = PrefsPropsUtil.getPreferences(company.getCompanyId());

	Map<String, String[]> companyPortletPreferencesMap = companyPortletPreferences.getMap();
	%>

	<liferay-ui:search-container
		emptyResultsMessage='<%= tabs2.equals("portal-properties") ? "no-portal-properties-were-found-that-matched-the-keywords" : "no-system-properties-were-found-that-matched-the-keywords" %>'
		iteratorURL="<%= serverURL %>"
		total="<%= filteredPropertiesList.size() %>"
	>
		<liferay-ui:search-container-results
			results="<%= ListUtil.subList(filteredPropertiesList, searchContainer.getStart(), searchContainer.getEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="java.util.Map.Entry"
			modelVar="entry"
		>

			<%
			String property = (String)entry.getKey();
			String value = (String)entry.getValue();

			boolean overriddenPropertyValue = false;

			if (tabs2.equals("portal-properties")) {
				if (serverPortletPreferencesMap.containsKey(property)) {
					value = serverPortletPreferences.getValue(property, StringPool.BLANK);

					overriddenPropertyValue = true;
				}

				if (companyPortletPreferencesMap.containsKey(property)) {
					value = companyPortletPreferences.getValue(property, StringPool.BLANK);

					overriddenPropertyValue = true;
				}
			}
			%>

			<liferay-ui:search-container-column-text
				name="property"
				value="<%= HtmlUtil.escape(StringUtil.shorten(property, 80)) %>"
			/>

			<liferay-ui:search-container-column-text
				name="value"
			>
				<c:if test="<%= Validator.isNotNull(value) %>">
					<c:choose>
						<c:when test="<%= value.length() > 80 %>">
							<span onmouseover="Liferay.Portal.ToolTip.show(this, '<%= HtmlUtil.escapeJS(value) %>');">
								<%= HtmlUtil.escape(StringUtil.shorten(value, 80)) %>
							</span>
						</c:when>
						<c:otherwise>
							<%= HtmlUtil.escape(value) %>
						</c:otherwise>
					</c:choose>
				</c:if>
			</liferay-ui:search-container-column-text>

			<c:if test='<%= tabs2.equals("portal-properties") %>'>
				<liferay-ui:search-container-column-text
					name="source"
				>
					<liferay-ui:icon
						iconCssClass='<%= overriddenPropertyValue ? "icon-hdd" : "icon-file-alt" %>'
						message='<%= LanguageUtil.get(request, overriddenPropertyValue ? "the-value-of-this-property-was-overridden-using-the-control-panel-and-is-stored-in-the-database" : "the-value-of-this-property-is-read-from-a-portal.properties-file-or-one-of-its-extension-files") %>'
					/>
				</liferay-ui:search-container-column-text>
			</c:if>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</div>