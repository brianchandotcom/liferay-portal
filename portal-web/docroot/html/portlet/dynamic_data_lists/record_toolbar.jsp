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

<%@ include file="/html/portlet/dynamic_data_lists/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

DDLRecord record = (DDLRecord)request.getAttribute(WebKeys.DYNAMIC_DATA_LISTS_RECORD);

long formDDMTemplateId = ParamUtil.getLong(request, "formDDMTemplateId");
%>

<div class="record-toolbar" id="<portlet:namespace />recordToolbar"></div>

<aui:script use="aui-toolbar,aui-dialog-iframe-deprecated,liferay-util-window">
	var permissionPopUp = null;

	var toolbarChildren = [
		<c:if test="<%= record != null %>">
			<portlet:renderURL var="viewHistoryURL">
				<portlet:param name="struts_action" value="/dynamic_data_lists/view_record_history" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="recordId" value="<%= String.valueOf(record.getRecordId()) %>" />
				<portlet:param name="formDDMTemplateId" value="<%= String.valueOf(formDDMTemplateId) %>" />
			</portlet:renderURL>

			{
				icon: 'icon-time',
				label: '<%= UnicodeLanguageUtil.get(pageContext, "view-history") %>',
				on: {
					click: function(event) {
						event.domEvent.preventDefault();

						window.location.href = '<%= viewHistoryURL %>';
					}
				}
			}
		</c:if>
	];

	new A.Toolbar(
		{
			boundingBox: '#<portlet:namespace />recordToolbar',
			children: toolbarChildren
		}
	).render();
</aui:script>