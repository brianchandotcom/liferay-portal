<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

String restoreEntryAction = ParamUtil.getString(request, "restoreEntryAction", "/trash/edit_entry");

long trashEntryId = ParamUtil.getLong(request, "trashEntryId");

String className = ParamUtil.getString(request, "className");
long classPK = ParamUtil.getLong(request, "classPK");

TrashEntry entry = null;

if (trashEntryId > 0) {
	entry = TrashEntryLocalServiceUtil.getEntry(trashEntryId);
}
else if (Validator.isNotNull(className) && (classPK > 0)) {
	entry = TrashEntryLocalServiceUtil.fetchEntry(className, classPK);
}

if (entry != null) {
	className = entry.getClassName();
	classPK = entry.getClassPK();
}

String duplicateEntryId = ParamUtil.getString(request, "duplicateEntryId");
String oldName = ParamUtil.getString(request, "oldName");

String overrideMessage = ParamUtil.getString(request, "overrideMessage");
String renameMessage = ParamUtil.getString(request, "renameMessage");
%>

<div class="alert alert-block" id="<portlet:namespace />messageContainer">
	<liferay-ui:message arguments="<%= new String[] {oldName} %>" key="an-entry-with-name-x-already-exists" translateArguments="<%= false %>" />
</div>

<portlet:actionURL var="restoreActionURL">
	<portlet:param name="struts_action" value="<%= restoreEntryAction %>" />
</portlet:actionURL>

<aui:form action="<%= restoreActionURL %>" enctype="multipart/form-data" method="post" name="restoreTrashEntryFm" onSubmit="event.preventDefault();">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="trashEntryId" type="hidden" value="<%= trashEntryId %>" />
	<aui:input name="duplicateEntryId" type="hidden" value="<%= duplicateEntryId %>" />
	<aui:input name="oldName" type="hidden" value="<%= oldName %>" />

	<aui:fieldset>
		<aui:input checked="<%= true %>" id="override" label="<%= overrideMessage %>" name="<%= Constants.CMD %>" type="radio" value="<%= Constants.OVERRIDE %>" />

		<aui:input id="rename" label="<%= renameMessage %>" name="<%= Constants.CMD %>" type="radio" value="<%= Constants.RENAME %>" />

		<aui:input cssClass="new-file-name" label="" name="newName" title="<%= renameMessage %>" value="<%= TrashUtil.getNewName(themeDisplay, className, classPK, oldName) %>" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button cssClass="btn-cancel" type="cancel" />

		<aui:button type="submit" />
	</aui:button-row>
</aui:form>