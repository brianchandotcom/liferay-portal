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

<%@ include file="/html/portlet/journal/init.jsp" %>

<liferay-ui:icon-menu cssClass="actions-button" direction="down" icon="" id="actionsButtonContainer" message="actions" showExpanded="<%= false %>" showWhenSingleIcon="<%= true %>">

	<%
	String taglibOnClick = "Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: '" + (TrashUtil.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE) + "'});";
	%>

	<liferay-ui:icon-delete
		confirmation="are-you-sure-you-want-to-delete-the-selected-entries"
		trash="<%= TrashUtil.isTrashEnabled(scopeGroupId) %>"
		url="<%= taglibOnClick %>"
	/>

	<%
	taglibOnClick = "Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: '" + Constants.EXPIRE + "'});";
	%>

	<liferay-ui:icon
		cssClass="expire-articles-button"
		image="time"
		message="expire"
		onClick="<%= taglibOnClick %>"
		url="javascript:;"
	/>

	<%
	taglibOnClick = "Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: '" + Constants.MOVE + "'});";
	%>

	<liferay-ui:icon
		cssClass="move-articles-button"
		image="submit"
		message="move"
		onClick="<%= taglibOnClick %>"
		url="javascript:;"
	/>
</liferay-ui:icon-menu>

<span class="add-button" id="<portlet:namespace />addButtonContainer">
	<liferay-util:include page="/html/portlet/journal/add_button.jsp" />
</span>

<span class="sort-button" id="<portlet:namespace />sortButtonContainer">
	<liferay-util:include page="/html/portlet/journal/sort_button.jsp" />
</span>

<span class="manage-button">
	<c:if test="<%= !user.isDefaultUser() %>">
		<liferay-ui:icon-menu direction="down" icon="" message="manage" showExpanded="<%= false %>" showWhenSingleIcon="<%= true %>">

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

			<c:if test="<%= PortalUtil.isRSSFeedsEnabled() %>">
				<liferay-ui:icon
					message="feeds"
					url="<%= taglibURL %>"
				/>
			</c:if>
		</liferay-ui:icon-menu>
	</c:if>
</span>

<aui:script>
	<c:if test="<%= PortalUtil.isRSSFeedsEnabled() %>">
		function <portlet:namespace />openFeedsView() {
			Liferay.Util.openWindow(
				{
					id: '<portlet:namespace />openFeedsView',
					title: '<%= UnicodeLanguageUtil.get(pageContext, "feeds") %>',
					uri: '<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/journal/view_feeds" /></liferay-portlet:renderURL>'
				}
			);
		}
	</c:if>

	<%
	Portlet portlet = PortletLocalServiceUtil.getPortletById(portletDisplay.getId());
	%>

	function <portlet:namespace />openStructuresView() {
		Liferay.Util.openDDMPortlet(
			{
				availableFields: 'Liferay.FormBuilder.AVAILABLE_FIELDS.WCM_STRUCTURE',
				dialog: {
					destroyOnHide: true
				},
				refererPortletName: '<%= PortletKeys.JOURNAL %>',
				refererWebDAVToken: '<%= portlet.getWebDAVStorageToken() %>',
				showGlobalScope: 'false',
				showManageTemplates: 'true',
				storageType: '<%= PropsValues.JOURNAL_ARTICLE_STORAGE_TYPE %>',
				structureName: 'structure',
				structureType: 'com.liferay.portlet.journal.model.JournalArticle',
				templateType: '<%= DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY %>',
				title: '<%= UnicodeLanguageUtil.get(pageContext, "structures") %>'
			}
		);
	}

	function <portlet:namespace />openTemplatesView() {
		Liferay.Util.openDDMPortlet(
			{
				availableFields: 'Liferay.FormBuilder.AVAILABLE_FIELDS.WCM_STRUCTURE',
				classNameId: '<%= PortalUtil.getClassNameId(DDMStructure.class) %>',
				classPK: -1,
				dialog: {
					width: 820
				},
				groupId: <%= scopeGroupId %>,
				refererPortletName: '<%= PortletKeys.JOURNAL %>',
				refererWebDAVToken: '<%= portlet.getWebDAVStorageToken() %>',
				storageType: '<%= PropsValues.JOURNAL_ARTICLE_STORAGE_TYPE %>',
				structureName: 'structure',
				structureType: 'com.liferay.portlet.journal.model.JournalArticle',
				struts_action: '/dynamic_data_mapping/view_template',
				templateType: '<%= DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY %>',
				title: '<%= UnicodeLanguageUtil.get(pageContext, "templates") %>'
			}
		);
	}
</aui:script>