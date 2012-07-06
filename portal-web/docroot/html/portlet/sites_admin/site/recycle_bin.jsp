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

String enableRecycleBinProperty = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ENABLE_RECYCLE_BIN);

boolean enableRecycleBin = true;

if (enableRecycleBinProperty.equals(TrashConstants.DISABLED) || enableRecycleBinProperty.equals(TrashConstants.DISABLED_BY_DEFAULT)) {
	enableRecycleBin = false;
}

enableRecycleBin = PropertiesParamUtil.getBoolean(groupTypeSettings, request, "enableRecycleBin", enableRecycleBin);

int trashEntriesMaxAge = PropertiesParamUtil.getInteger(groupTypeSettings, request, "trashEntriesMaxAge", PrefsPropsUtil.getInteger(company.getCompanyId(), PropsKeys.TRASH_ENTRIES_MAX_AGE));
%>

<liferay-ui:error-marker key="errorSection" value="analytics" />

<aui:fieldset>
	<aui:input label="enable-recycle-bin" name="enableRecycleBin" type="checkbox" value="<%= enableRecycleBin %>" />

	<aui:input label="set-the-number-of-days-that-files-will-be-kept-in-the-recycle-bin" name="trashEntriesMaxAge" type="text" value="<%= trashEntriesMaxAge %>">
		<aui:validator name="number" />
	</aui:input>
</aui:fieldset>