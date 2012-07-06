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
String enableRecycleBin = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ENABLE_RECYCLE_BIN);

int trashEntriesMaxAge = PrefsPropsUtil.getInteger(company.getCompanyId(), PropsKeys.TRASH_ENTRIES_MAX_AGE);
%>

<h3><liferay-ui:message key="recycle-bin" /></h3>

<aui:fieldset>
	<aui:select label="enable-recycle-bin" name='<%= "settings--" + PropsKeys.ENABLE_RECYCLE_BIN + "--" %>'>
		<aui:option label="enabled-by-default" selected="<%= enableRecycleBin.equals(TrashConstants.ENABLED_BY_DEFAULT) %>" value="<%= TrashConstants.ENABLED_BY_DEFAULT %>" />
		<aui:option label="disabled-by-default" selected="<%= enableRecycleBin.equals(TrashConstants.DISABLED_BY_DEFAULT) %>" value="<%= TrashConstants.DISABLED_BY_DEFAULT %>" />
		<aui:option label="disabled" selected="<%= enableRecycleBin.equals(TrashConstants.DISABLED) %>" value="<%= TrashConstants.DISABLED %>" />
	</aui:select>

	<aui:input label="set-the-number-of-days-that-files-will-be-kept-in-the-recycle-bin" name='<%= "settings--" + PropsKeys.TRASH_ENTRIES_MAX_AGE + "--" %>' type="text" value="<%= trashEntriesMaxAge %>">
		<aui:validator name="number" />
	</aui:input>
</aui:fieldset>