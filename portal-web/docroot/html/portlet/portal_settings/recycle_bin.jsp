<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/portal_settings/init.jsp" %>

<%
int trashEnabled = PrefsPropsUtil.getInteger(company.getCompanyId(), PropsKeys.TRASH_ENABLED);

int trashEntriesMaxAge = PrefsPropsUtil.getInteger(company.getCompanyId(), PropsKeys.TRASH_ENTRIES_MAX_AGE);
%>

<h3><liferay-ui:message key="recycle-bin" /></h3>

<aui:fieldset>
	<aui:select label="enable-recycle-bin" name='<%= "settings--" + PropsKeys.TRASH_ENABLED + "--" %>'>
		<aui:option label="enabled-by-default" selected="<%= trashEnabled == TrashUtil.TRASH_ENABLED_BY_DEFAULT %>" value="<%= TrashUtil.TRASH_ENABLED_BY_DEFAULT %>" />
		<aui:option label="disabled-by-default" selected="<%= trashEnabled == TrashUtil.TRASH_DISABLED_BY_DEFAULT %>" value="<%= TrashUtil.TRASH_DISABLED_BY_DEFAULT %>" />
		<aui:option label="disabled" selected="<%= trashEnabled == TrashUtil.TRASH_DISABLED %>" value="<%= TrashUtil.TRASH_DISABLED %>" />
	</aui:select>

	<aui:input label="number-of-days-that-files-will-be-kept-in-the-recycle-bin" name='<%= "settings--" + PropsKeys.TRASH_ENTRIES_MAX_AGE + "--" %>' type="text" value="<%= trashEntriesMaxAge %>">
		<aui:validator name="min">1</aui:validator>
	</aui:input>
</aui:fieldset>