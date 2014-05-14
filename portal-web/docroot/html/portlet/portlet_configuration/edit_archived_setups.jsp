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

<%@ include file="/html/portlet/portlet_configuration/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/portlet_configuration/edit_archived_setups");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("returnToFullPageURL", returnToFullPageURL);
portletURL.setParameter("portletResource", portletResource);
%>

<liferay-util:include page="/html/portlet/portlet_configuration/tabs1.jsp">
	<liferay-util:param name="tabs1" value="setup" />
</liferay-util:include>

<portlet:renderURL var="backURL">
	<portlet:param name="struts_action" value="/portlet_configuration/edit_configuration" />
	<portlet:param name="redirect" value="<%= redirect %>" />
	<portlet:param name="returnToFullPageURL" value="<%= returnToFullPageURL %>" />
	<portlet:param name="portletResource" value="<%= portletResource %>" />
</portlet:renderURL>

<liferay-ui:header
	backURL="<%= backURL %>"
	title="archived-setups"
/>

<liferay-ui:error exception="<%= NoSuchPortletItemException.class %>" message="the-setup-could-not-be-found" />
<liferay-ui:error exception="<%= PortletItemNameException.class %>" message="please-enter-a-valid-setup-name" />

<portlet:actionURL var="editArchivedSetupsURL">
	<portlet:param name="struts_action" value="/portlet_configuration/edit_archived_setups" />
</portlet:actionURL>

<aui:form action="<%= editArchivedSetupsURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.SAVE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="returnToFullPageURL" type="hidden" value="<%= returnToFullPageURL %>" />
	<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />

	<%
	List<String> headerNames = new ArrayList<String>();

	headerNames.add("name");
	headerNames.add("user");
	headerNames.add("modified-date");
	headerNames.add(StringPool.BLANK);

	SearchContainer<ArchivedSettings> searchContainer = new SearchContainer<ArchivedSettings>(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, "there-are-no-archived-setups");

	List<ArchivedSettings> archivedSettingsList = SettingsFactoryUtil.getPortletInstanceArchivedSettingsList(scopeGroupId, selPortlet.getRootPortletId());

	int total = archivedSettingsList.size();

	searchContainer.setTotal(total);

	List<ArchivedSettings> results = ListUtil.subList(archivedSettingsList, searchContainer.getStart(), searchContainer.getEnd());

	searchContainer.setResults(results);

	List<ResultRow> resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		ArchivedSettings archivedSettings = results.get(i);

		ResultRow row = new ResultRow(new Object[] {archivedSettings, portletResource}, archivedSettings.getName(), i);

		// Name

		row.addText(archivedSettings.getName());

		// User

		row.addText(archivedSettings.getUserName());

		// Date

		row.addDate(archivedSettings.getModifiedDate());

		// Action

		row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/portlet_configuration/archived_setup_action.jsp");

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

	<div class="separator"><!-- --></div>

	<aui:input label="archive-name-for-current-setup" name="name" size="20" type="text" />

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<%
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "archived"), currentURL);
%>