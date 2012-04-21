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

<%@ include file="/html/portlet/journal/init.jsp" %>

<span class="add-button" id="<portlet:namespace />addButtonContainer">
	<liferay-util:include page="/html/portlet/journal/add_button.jsp" />
</span>

<span class="manage-button">
	<c:if test="<%= !user.isDefaultUser() %>">
		<liferay-ui:icon-menu align="left" direction="down" icon="" message="manage" showExpanded="<%= false %>" showWhenSingleIcon="<%= true %>">

			<%
			String taglibUrl = "javascript:" + renderResponse.getNamespace() + "openStructuresView()";
			%>

			<liferay-ui:icon
				message="structures"
				url="<%= taglibUrl %>"
			/>

			<%
			taglibUrl = "javascript:" + renderResponse.getNamespace() + "openTemplatesView()";
			%>

			<liferay-ui:icon
				message="templates"
				url="<%= taglibUrl %>"
			/>

			<%
			taglibUrl = "javascript:" + renderResponse.getNamespace() + "openFeedsView()";
			%>

			<liferay-ui:icon
				message="feeds"
				url="<%= taglibUrl %>"
			/>
		</liferay-ui:icon-menu>
	</c:if>
</span>

<aui:script>
	function <portlet:namespace />openFeedsView() {
		Liferay.Util.openWindow(
			{
				dialog: {
					width:820
				},
				id: '<portlet:namespace />openFeedsView',
				title: '<%= UnicodeLanguageUtil.get(pageContext, "feeds") %>',
				uri: '<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/journal/view_feeds" /></liferay-portlet:renderURL>'
			}
		);
	}

	function <portlet:namespace />openStructuresView() {
		Liferay.Util.openWindow(
			{
				dialog: {
					width:820
				},
				id: '<portlet:namespace />openStructuresView',
				title: '<%= UnicodeLanguageUtil.get(pageContext, "structures") %>',
				uri: '<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/journal/view_structures" /></liferay-portlet:renderURL>'
			}
		);
	}

	function <portlet:namespace />openTemplatesView() {
		Liferay.Util.openWindow(
			{
				dialog: {
					width:820
				},
				id: '<portlet:namespace />openTemplatesView',
				title: '<%= UnicodeLanguageUtil.get(pageContext, "templates") %>',
				uri: '<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/journal/view_templates" /></liferay-portlet:renderURL>'
			}
		);
	}
</aui:script>