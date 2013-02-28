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
Group liveGroup = (Group)request.getAttribute("site.liveGroup");

UnicodeProperties groupTypeSettings = null;

if (liveGroup != null) {
	groupTypeSettings = liveGroup.getTypeSettingsProperties();
}
else {
	groupTypeSettings = new UnicodeProperties();
}

int companyTrashEnabled = PrefsPropsUtil.getInteger(company.getCompanyId(), PropsKeys.TRASH_ENABLED);
int groupTrashEnabled = PropertiesParamUtil.getInteger(groupTypeSettings, request, "trashEnabled", Trash.TRASH_DEFAULT_VALUE);

int trashEntriesMaxAge = PropertiesParamUtil.getInteger(groupTypeSettings, request, "trashEntriesMaxAge", PrefsPropsUtil.getInteger(company.getCompanyId(), PropsKeys.TRASH_ENTRIES_MAX_AGE));
%>

<aui:fieldset>
	<aui:select label="enable-recycle-bin" name="trashEnabled">
		<aui:option label='<%= LanguageUtil.format(locale, "use-portal-settings-currently-x", (companyTrashEnabled == Trash.TRASH_ENABLED_BY_DEFAULT) ? "enabled" : "disabled", true) %>' selected="<%= groupTrashEnabled == Trash.TRASH_DEFAULT_VALUE %>" value="<%= Trash.TRASH_DEFAULT_VALUE %>" />
		<aui:option label="enabled" selected="<%= groupTrashEnabled == Trash.TRASH_ENABLED %>" value="<%= Trash.TRASH_ENABLED %>" />
		<aui:option label="disabled" selected="<%= groupTrashEnabled == Trash.TRASH_DISABLED %>" value="<%= Trash.TRASH_DISABLED %>" />
	</aui:select>

	<aui:input disabled="<%= groupTrashEnabled != Trash.TRASH_ENABLED %>" label="number-of-days-that-files-will-be-kept-in-the-recycle-bin" name="trashEntriesMaxAge" type="text" value="<%= trashEntriesMaxAge %>">
		<aui:validator name="min">1</aui:validator>
	</aui:input>
</aui:fieldset>

<aui:script>
	Liferay.Util.disableSelectBoxes('<portlet:namespace />trashEntriesMaxAge', '<%= Trash.TRASH_ENABLED %>', '<portlet:namespace />trashEnabled');
</aui:script>