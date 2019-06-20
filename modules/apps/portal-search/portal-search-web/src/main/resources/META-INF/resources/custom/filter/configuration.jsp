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

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.search.web.internal.custom.filter.portlet.CustomFilterPortletPreferences" %><%@
page import="com.liferay.portal.search.web.internal.custom.filter.portlet.CustomFilterPortletPreferencesImpl" %><%@
page import="com.liferay.portal.search.web.internal.custom.filter.portlet.action.ConfigurationDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.custom.filter.portlet.action.OccurEntriesHolder" %><%@
page import="com.liferay.portal.search.web.internal.custom.filter.portlet.action.OccurEntry" %><%@
page import="com.liferay.portal.search.web.internal.custom.filter.portlet.action.QueryTypeEntriesHolder" %><%@
page import="com.liferay.portal.search.web.internal.custom.filter.portlet.action.QueryTypeEntry" %><%@
page import="com.liferay.portal.search.web.internal.util.PortletPreferencesJspUtil" %>

<%@ page import="java.util.Objects" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
ConfigurationDisplayContext configurationDisplayContext = (ConfigurationDisplayContext)java.util.Objects.requireNonNull(request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT));

CustomFilterPortletPreferences customFilterPortletPreferences = new CustomFilterPortletPreferencesImpl(java.util.Optional.of(portletPreferences));

QueryTypeEntriesHolder queryTypeEntriesHolder = configurationDisplayContext.getQueryTypeEntriesHolder();
OccurEntriesHolder occurEntriesHolder = configurationDisplayContext.getOccurEntriesHolder();
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset-group>
			<aui:input helpMessage="filter-field-help" label="filter-field" name="<%= PortletPreferencesJspUtil.getInputName(CustomFilterPortletPreferences.PREFERENCE_KEY_FILTER_FIELD) %>" value="<%= customFilterPortletPreferences.getFilterFieldString() %>" />

			<aui:input helpMessage="filter-value-help" label="filter-value" name="<%= PortletPreferencesJspUtil.getInputName(CustomFilterPortletPreferences.PREFERENCE_KEY_FILTER_VALUE) %>" value="<%= customFilterPortletPreferences.getFilterValueString() %>" />

			<aui:select helpMessage="filter-query-type-help" label="filter-query-type" name="<%= PortletPreferencesJspUtil.getInputName(CustomFilterPortletPreferences.PREFERENCE_KEY_FILTER_QUERY_TYPE) %>">

				<%
				for (QueryTypeEntry queryTypeEntry : queryTypeEntriesHolder.getQueryTypeEntries()) {
				%>

					<aui:option data-field="<%= queryTypeEntry.getTypeId() %>" data-type="<%= queryTypeEntry.getTypeId() %>" label="<%= queryTypeEntry.getName() %>" selected="<%= Objects.equals(customFilterPortletPreferences.getFilterQueryType(), queryTypeEntry.getTypeId()) %>" value="<%= queryTypeEntry.getTypeId() %>" />

				<%
				}
				%>

			</aui:select>

			<aui:select helpMessage="occur-help" label="occur" name="<%= PortletPreferencesJspUtil.getInputName(CustomFilterPortletPreferences.PREFERENCE_KEY_OCCUR) %>">

				<%
				for (OccurEntry occurEntry : occurEntriesHolder.getOccurEntries()) {
				%>

					<aui:option label="<%= occurEntry.getName() %>" selected="<%= Objects.equals(customFilterPortletPreferences.getOccur(), occurEntry.getOccur()) %>" value="<%= occurEntry.getOccur() %>" />

				<%
				}
				%>

			</aui:select>

			<aui:input helpMessage="query-name-help" label="query-name" name="<%= PortletPreferencesJspUtil.getInputName(CustomFilterPortletPreferences.PREFERENCE_KEY_QUERY_NAME) %>" value="<%= customFilterPortletPreferences.getQueryNameString() %>" />

			<aui:input helpMessage="parent-query-name-help" label="parent-query-name" name="<%= PortletPreferencesJspUtil.getInputName(CustomFilterPortletPreferences.PREFERENCE_KEY_PARENT_QUERY_NAME) %>" value="<%= customFilterPortletPreferences.getParentQueryNameString() %>" />

			<aui:input helpMessage="boost-help" label="boost" name="<%= PortletPreferencesJspUtil.getInputName(CustomFilterPortletPreferences.PREFERENCE_KEY_BOOST) %>" value="<%= customFilterPortletPreferences.getBoostString() %>" />

			<aui:input helpMessage="custom-heading-help" label="custom-heading" name="<%= PortletPreferencesJspUtil.getInputName(CustomFilterPortletPreferences.PREFERENCE_KEY_CUSTOM_HEADING) %>" value="<%= customFilterPortletPreferences.getCustomHeadingString() %>" />

			<aui:input helpMessage="custom-parameter-name-help" label="custom-parameter-name" name="<%= PortletPreferencesJspUtil.getInputName(CustomFilterPortletPreferences.PREFERENCE_KEY_PARAMETER_NAME) %>" value="<%= customFilterPortletPreferences.getParameterNameString() %>" />

			<aui:input helpMessage="invisible-help" label="invisible" name="<%= PortletPreferencesJspUtil.getInputName(CustomFilterPortletPreferences.PREFERENCE_KEY_INVISIBLE) %>" type="checkbox" value="<%= customFilterPortletPreferences.isInvisible() %>" />

			<aui:input helpMessage="immutable-help" label="immutable" name="<%= PortletPreferencesJspUtil.getInputName(CustomFilterPortletPreferences.PREFERENCE_KEY_IMMUTABLE) %>" type="checkbox" value="<%= customFilterPortletPreferences.isImmutable() %>" />

			<aui:input helpMessage="disabled-help" label="disabled" name="<%= PortletPreferencesJspUtil.getInputName(CustomFilterPortletPreferences.PREFERENCE_KEY_DISABLED) %>" type="checkbox" value="<%= customFilterPortletPreferences.isDisabled() %>" />

			<aui:input helpMessage="federated-search-key-help" label="federated-search-key" name="<%= PortletPreferencesJspUtil.getInputName(CustomFilterPortletPreferences.PREFERENCE_KEY_FEDERATED_SEARCH_KEY) %>" type="text" value="<%= customFilterPortletPreferences.getFederatedSearchKeyString() %>" />
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>