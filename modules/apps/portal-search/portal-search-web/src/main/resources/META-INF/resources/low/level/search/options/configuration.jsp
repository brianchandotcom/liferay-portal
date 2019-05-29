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
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>

<%@ page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.search.web.internal.low.level.search.options.portlet.preferences.LowLevelSearchOptionsPortletPreferences" %><%@
page import="com.liferay.portal.search.web.internal.low.level.search.options.portlet.preferences.LowLevelSearchOptionsPortletPreferencesImpl" %><%@
page import="com.liferay.portal.search.web.internal.util.PortletPreferencesJspUtil" %>

<portlet:defineObjects />

<%
LowLevelSearchOptionsPortletPreferences lowLevelSearchOptionsPortletPreferences = new LowLevelSearchOptionsPortletPreferencesImpl(java.util.Optional.ofNullable(portletPreferences));
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
			<aui:fieldset>
				<aui:input helpMessage="indexes-help" label="indexes" name="<%= PortletPreferencesJspUtil.getInputName(LowLevelSearchOptionsPortletPreferences.PREFERENCE_KEY_INDEXES) %>" type="text" value="<%= lowLevelSearchOptionsPortletPreferences.getIndexesString() %>" />

				<aui:input helpMessage="fields-to-return-help" label="fields-to-return" name="<%= PortletPreferencesJspUtil.getInputName(LowLevelSearchOptionsPortletPreferences.PREFERENCE_KEY_FIELDS_TO_RETURN) %>" type="text" value="<%= lowLevelSearchOptionsPortletPreferences.getFieldsToReturnString() %>" />

				<aui:input helpMessage="contributors-to-include-help" label="contributors-to-include" name="<%= PortletPreferencesJspUtil.getInputName(LowLevelSearchOptionsPortletPreferences.PREFERENCE_KEY_CONTRIBUTORS_TO_INCLUDE) %>" type="text" value="<%= lowLevelSearchOptionsPortletPreferences.getContributorsToIncludeString() %>" />

				<aui:input helpMessage="contributors-to-exclude-help" label="contributors-to-exclude" name="<%= PortletPreferencesJspUtil.getInputName(LowLevelSearchOptionsPortletPreferences.PREFERENCE_KEY_CONTRIBUTORS_TO_EXCLUDE) %>" type="text" value="<%= lowLevelSearchOptionsPortletPreferences.getContributorsToExcludeString() %>" />

				<aui:input helpMessage="federated-search-key-help" label="federated-search-key" name="<%= PortletPreferencesJspUtil.getInputName(LowLevelSearchOptionsPortletPreferences.PREFERENCE_KEY_FEDERATED_SEARCH_KEY) %>" type="text" value="<%= lowLevelSearchOptionsPortletPreferences.getFederatedSearchKeyString() %>" />
			</aui:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>