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

<liferay-ui:icon-menu align="left" cssClass="actions-button" direction="down" icon="" id="actionsButtonContainer" message="actions" showExpanded="<%= false %>" showWhenSingleIcon="<%= true %>">

	<%
	String taglibURL = "javascript:" + renderResponse.getNamespace() + "openPermissionsView()";
	%>

	<liferay-ui:icon
		image="permissions"
		message="permissions"
		url="<%= taglibURL %>"
	/>

	<c:choose>
		<c:when test="<%= SubscriptionLocalServiceUtil.isSubscribed(company.getCompanyId(), user.getUserId(), JournalArticle.class.getName(), scopeGroupId) %>">
			<portlet:actionURL var="unsubscribeURL">
				<portlet:param name="struts_action" value="/journal/edit_article" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNSUBSCRIBE %>" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:actionURL>

			<liferay-ui:icon
				image="unsubscribe"
				message="unsubscribe"
				url="<%= unsubscribeURL %>"
			/>
		</c:when>
		<c:otherwise>
			<portlet:actionURL var="subscribeURL">
				<portlet:param name="struts_action" value="/journal/edit_article" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SUBSCRIBE %>" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:actionURL>

			<liferay-ui:icon
				image="subscribe"
				message="subscribe"
				url="<%= subscribeURL %>"
			/>
		</c:otherwise>
	</c:choose>

	<%
	taglibURL = "javascript:" + renderResponse.getNamespace() + "deleteArticles();";
	%>

	<liferay-ui:icon
		cssClass="delete-articles-button"
		image="delete"
		message="delete"
		url="<%= taglibURL %>"
	/>

	<%
	taglibURL = "javascript:" + renderResponse.getNamespace() + "expireArticles();";
	%>

	<liferay-ui:icon
		cssClass="expire-articles-button"
		image="time"
		message="expire"
		url="<%= taglibURL %>"
	/>
</liferay-ui:icon-menu>

<span class="add-button" id="<portlet:namespace />addButtonContainer">
	<liferay-util:include page="/html/portlet/journal/add_button.jsp" />
</span>

<span class="manage-button">
	<c:if test="<%= !user.isDefaultUser() %>">
		<liferay-ui:icon-menu align="left" direction="down" icon="" message="manage" showExpanded="<%= false %>" showWhenSingleIcon="<%= true %>">

			<%
			String taglibURL = "javascript:" + renderResponse.getNamespace() + "openStructuresView()";
			%>

			<liferay-ui:icon
				message="structures"
				url="<%= taglibURL %>"
			/>

			<%
			taglibURL = "javascript:" + renderResponse.getNamespace() + "openTemplatesView()";
			%>

			<liferay-ui:icon
				message="templates"
				url="<%= taglibURL %>"
			/>

			<%
			taglibURL = "javascript:" + renderResponse.getNamespace() + "openFeedsView()";
			%>

			<liferay-ui:icon
				message="feeds"
				url="<%= taglibURL %>"
			/>
		</liferay-ui:icon-menu>
	</c:if>
</span>

<aui:script>
	function <portlet:namespace />openFeedsView() {
		Liferay.Util.openWindow(
			{
				dialog: {
					width: 820
				},
				id: '<portlet:namespace />openFeedsView',
				title: '<%= UnicodeLanguageUtil.get(pageContext, "feeds") %>',
				uri: '<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/journal/view_feeds" /></liferay-portlet:renderURL>'
			}
		);
	}

	function <portlet:namespace />openPermissionsView() {
		Liferay.Util.openWindow(
			{
				dialog: {
					width: 820
				},
				id: '<portlet:namespace />openPermissionsView',
				title: '<%= UnicodeLanguageUtil.get(pageContext, "permissions") %>',
				uri: '<liferay-security:permissionsURL modelResource="com.liferay.portlet.journal" modelResourceDescription="<%= HtmlUtil.escape(themeDisplay.getScopeGroupName()) %>" resourcePrimKey="<%= String.valueOf(scopeGroupId) %>" windowState="<%= LiferayWindowState.POP_UP.toString() %>" />'
			}
		);
	}

	function <portlet:namespace />openStructuresView() {
		Liferay.Util.openWindow(
			{
				dialog: {
					width: 820
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
					width: 820
				},
				id: '<portlet:namespace />openTemplatesView',
				title: '<%= UnicodeLanguageUtil.get(pageContext, "templates") %>',
				uri: '<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/journal/view_templates" /></liferay-portlet:renderURL>'
			}
		);
	}
</aui:script>

<aui:script use="aui-base">
	var buttons = A.all('.delete-articles-button, .expire-articles-button');

	if (buttons.size()) {
		var resultsGrid = A.one('.results-grid');

		if (resultsGrid) {
			resultsGrid.delegate(
				'click',
				function(event) {
					if (resultsGrid.one(':checked') == null) {
						buttons.hide();
					}
					else {
						buttons.show();
					}
				},
				':checkbox'
			);

			if (resultsGrid.one(':checked') == null) {
				buttons.hide();
			}
		}
	}
</aui:script>