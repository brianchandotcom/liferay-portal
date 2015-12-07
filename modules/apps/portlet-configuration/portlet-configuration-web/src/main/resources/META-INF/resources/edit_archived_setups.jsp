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
String redirect = ParamUtil.getString(request, "redirect");
String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/edit_archived_setups.jsp");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("returnToFullPageURL", returnToFullPageURL);
portletURL.setParameter("portletResource", portletResource);

SearchContainer<ArchivedSettings> archivedSettingsSearch = new SearchContainer<ArchivedSettings>(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, null, "there-are-no-archived-setups");

List<ArchivedSettings> archivedSettingsList = SettingsFactoryUtil.getPortletInstanceArchivedSettingsList(scopeGroupId, selPortlet.getRootPortletId());

int archivedSettingsCount = archivedSettingsList.size();

archivedSettingsSearch.setTotal(archivedSettingsCount);

archivedSettingsList = ListUtil.subList(archivedSettingsList, archivedSettingsSearch.getStart(), archivedSettingsSearch.getEnd());

archivedSettingsSearch.setResults(archivedSettingsList);
%>

<portlet:renderURL var="backURL">
	<portlet:param name="mvcPath" value="/edit_configuration.jsp" />
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

<portlet:actionURL name="updateArchivedSetup" var="updateArchivedSetupURL">
	<portlet:param name="mvcPath" value="/edit_archived_setups.jsp" />
	<portlet:param name="portletConfiguration" value="<%= Boolean.TRUE.toString() %>" />
</portlet:actionURL>

<aui:form action="<%= updateArchivedSetupURL %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="returnToFullPageURL" type="hidden" value="<%= returnToFullPageURL %>" />
	<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />

	<liferay-ui:search-container
		searchContainer="<%= archivedSettingsSearch %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.settings.ArchivedSettings"
			keyProperty="name"
			modelVar="archivedSettings"
		>
			<liferay-ui:search-container-column-text
				name="name"
			/>

			<liferay-ui:search-container-column-text
				name="user-name"
				property="userName"
			/>

			<liferay-ui:search-container-column-date
				name="modified-date"
				property="modifiedDate"
			/>

			<liferay-ui:search-container-column-jsp
				path="/archived_setup_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>

	<aui:input label="archive-name-for-current-setup" name="name" size="20" type="text" />

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<%
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "archived"), currentURL);
%>