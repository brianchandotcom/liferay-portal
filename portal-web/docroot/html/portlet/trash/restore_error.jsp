<%@ page import="com.liferay.portlet.trash.DuplicateTrashEntryException" %>
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
long duplicatedEntryId = 0;

if (SessionErrors.contains(request, DuplicateTrashEntryException.class.getName())) {
	DuplicateTrashEntryException dtee = (DuplicateTrashEntryException)SessionErrors.get(request, DuplicateTrashEntryException.class.getName());
	duplicatedEntryId = dtee.getDuplicateEntryId();
}
%>


<liferay-ui:header
	backURL="javascript:history.go(-1);"
	title="error"
/>

<liferay-ui:message key="a-document-with-this-name-already-exist" />

<portlet:actionURL var="editActionURL">
	<portlet:param name="struts_action" value="/trash/edit_entry" />
	<portlet:param name="redirect" value="<%= redirect %>" />
</portlet:actionURL>

<aui:form action="<%= editActionURL %>" enctype="multipart/form-data" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.FIX_RESTORE %>" />
	<aui:input name="entryId" type="hidden" value="<%= duplicatedEntryId %>" />

	<aui:field-wrapper>
		<aui:input checked="<%= true %>" id="override" label="override" name="duplicate-action" type="radio" value="override" />

		<aui:input id="rename" label="rename" name="duplicate-action" type="radio" value="rename" />

		<div id="<portlet:namespace />name">
			<aui:input label="new-name" name="new-name" />
		</div>
	</aui:field-wrapper>

	</br>

	<aui:button-row>
		<aui:button href="<%= redirect %>" value="cancel" />
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	Liferay.Util.toggleRadio('<portlet:namespace />override', '', ['<portlet:namespace />name']);
	Liferay.Util.toggleRadio('<portlet:namespace />rename', '<portlet:namespace />name');
</aui:script>

<%--
<aui:script use="aui-base">
A.all('#<portlet:namespace />rename').on(
	'click',
	function(event) {
		var name = A.one('#<portlet:namespace />name');

		if (name) {
			name.toggle();
		}
	}
);
</aui:script>
--%>