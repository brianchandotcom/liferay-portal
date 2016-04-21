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

<%@ include file="/html/taglib/ui/restore_entry/init.jsp" %>

<%
String overrideMessage = (String)request.getAttribute("liferay-ui:restore-entry:overrideMessage");
String renameMessage = (String)request.getAttribute("liferay-ui:restore-entry:renameMessage");
PortletURL restoreURL = (PortletURL)request.getAttribute("liferay-ui:restore-entry:restoreURL");

String redirect = ParamUtil.getString(request, "redirect");

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
boolean overridable = ParamUtil.getBoolean(request, "overridable");
%>

<div class="container-fluid-1280">
	<div class="alert alert-danger hide" id="<portlet:namespace />errorMessageContainer"></div>

	<aui:form action="<%= restoreURL %>" enctype="multipart/form-data" method="post" name="restoreTrashEntryFm" onSubmit="event.preventDefault();">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="trashEntryId" type="hidden" value="<%= trashEntryId %>" />
		<aui:input name="duplicateEntryId" type="hidden" value="<%= duplicateEntryId %>" />
		<aui:input name="oldName" type="hidden" value="<%= oldName %>" />

		<aui:fieldset-group markupview="lexicon">
			<aui:fieldset>
				<p class="text-muted" id="<portlet:namespace />messageContainer">
					<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(oldName)} %>" key="an-entry-with-name-x-already-exists" translateArguments="<%= false %>" />
				</p>

				<c:choose>
					<c:when test="<%= overridable %>">
						<aui:input checked="<%= true %>" id="override" label="<%= HtmlUtil.escape(overrideMessage) %>" name="<%= Constants.CMD %>" type="radio" value="<%= Constants.OVERRIDE %>" />

						<aui:input id="rename" label="<%= HtmlUtil.escape(renameMessage) %>" name="<%= Constants.CMD %>" type="radio" value="<%= Constants.RENAME %>" />
					</c:when>
					<c:otherwise>
						<aui:input id="rename" name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.RENAME %>" />
					</c:otherwise>
				</c:choose>

				<aui:input cssClass="new-file-name" label='<%= overridable ? "" : HtmlUtil.escape(renameMessage) %>' name="newName" title="<%= HtmlUtil.escapeAttribute(renameMessage) %>" value="<%= TrashUtil.getNewName(themeDisplay, className, classPK, oldName) %>" />
			</aui:fieldset>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button type="submit" />

			<aui:button cssClass="btn-cancel" type="cancel" />
		</aui:button-row>
	</aui:form>
</div>