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

<%@ include file="/html/portlet/trash/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String trashEntryId = ParamUtil.getString(request, "trashEntryId");

String duplicateEntryId = ParamUtil.getString(request, "duplicateEntryId");
String oldName = ParamUtil.getString(request, "oldName");
%>

<div class="portlet-msg-alert" id="<portlet:namespace />messageContainer">
	<liferay-ui:message arguments="<%= new String[] {oldName} %>" key="an-entry-with-name-x-already-exists" />
</div>

<portlet:actionURL var="editActionURL">
	<portlet:param name="struts_action" value="/trash/edit_entry" />
	<portlet:param name="redirect" value="<%= redirect %>" />
</portlet:actionURL>

<aui:form action="<%= editActionURL %>" enctype="multipart/form-data" method="post" name="restoreTrashEntryFm" onSubmit="event.preventDefault();">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="trashEntryId" type="hidden" value="<%= trashEntryId %>" />
	<aui:input name="duplicateEntryId" type="hidden" value="<%= duplicateEntryId %>" />
	<aui:input name="oldName" type="hidden" value="<%= oldName %>" />

	<aui:fieldset>
		<aui:input checked="<%= true %>" id="override" label="overwrite-the-existing-entry-with-the-one-from-the-recycle-bin" name="<%= Constants.CMD %>" type="radio" value="<%= Constants.OVERRIDE %>" />

		<aui:input id="rename" label="keep-both-entries-and-rename-the-entry-from-the-recycle-bin-as" name="<%= Constants.CMD %>" type="radio" value="<%= Constants.RENAME %>" />

		<aui:input cssClass="new-file-name" label="" name="newName" title="keep-both-entries-and-rename-the-entry-from-the-recycle-bin-as" value="<%= TrashUtil.getNewName(themeDisplay, oldName) %>" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="cancel" />

		<aui:button type="submit" />
	</aui:button-row>
</aui:form>