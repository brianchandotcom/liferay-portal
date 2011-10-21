<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%
String strutsAction = ParamUtil.getString(request, "struts_action");

long groupId = ParamUtil.getLong(request, "groupId", scopeGroupId);
String structureId = ParamUtil.getString(request, "structureId");

TemplateSearch searchContainer = (TemplateSearch)request.getAttribute("liferay-ui:search:searchContainer");

TemplateDisplayTerms displayTerms = (TemplateDisplayTerms)searchContainer.getDisplayTerms();
%>

<liferay-ui:search-toggle
	id="toggle_id_journal_template_search"
	displayTerms="<%= displayTerms %>"
	buttonLabel="search"
>
	<aui:fieldset>
		<aui:input label="id" name="<%= displayTerms.TEMPLATE_ID %>" size="20" type="text" value="<%= displayTerms.getTemplateId() %>" />

		<aui:input name="<%= displayTerms.NAME %>" size="20" type="text" value="<%= displayTerms.getName() %>" />

		<aui:input name="<%= displayTerms.DESCRIPTION %>" type="text" size="20" value="<%= displayTerms.getDescription() %>" />

		<c:if test='<%= strutsAction.equalsIgnoreCase("/journal/select_template") %>'>
			<aui:select label="my-sites" name="<%= displayTerms.GROUP_IDS %>">
				<c:if test="<%= themeDisplay.getCompanyGroupId() != scopeGroupId %>">
					<aui:option label="" value="<%= displayTerms.getGroupIds(renderRequest) %>" />
					<aui:option label="global" selected="<%= displayTerms.getGroupId() == themeDisplay.getCompanyGroupId() %>" value="<%= themeDisplay.getCompanyGroupId() %>" />
				</c:if>

				<aui:option label="<%= themeDisplay.getParentGroupName() %>" selected="<%= displayTerms.getGroupId() == themeDisplay.getParentGroupId() %>" value="<%= themeDisplay.getParentGroupId() %>" />

				<%
				Layout scopeLayout = themeDisplay.getScopeLayout();
				%>

				<c:if test="<%= scopeLayout != null %>">

					<%
					Group scopeGroup = scopeLayout.getScopeGroup();
					%>

					<aui:option label='<%= LanguageUtil.get(pageContext, "current-page") + " (" + HtmlUtil.escape(scopeLayout.getName(locale)) + ")" %>' selected="<%= (displayTerms.getGroupIds().length == 1) && (displayTerms.getGroupIds()[0] == scopeGroup.getGroupId()) %>" value="<%= scopeGroup.getGroupId() %>" />
				</c:if>
			</aui:select>
		</c:if>
	</aui:fieldset>
</liferay-ui:search-toggle>

<%
boolean showAddTemplateButton = JournalPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_TEMPLATE);
boolean showPermissionsButton = GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.PERMISSIONS);
%>

<c:if test="<%= showAddTemplateButton || showPermissionsButton %>">
	<aui:button-row>
		<c:if test="<%= showAddTemplateButton %>">
			<aui:button onClick='<%= renderResponse.getNamespace() + "addTemplate();" %>' value="add-template" />
		</c:if>

		<c:if test="<%= showPermissionsButton %>">
			<liferay-security:permissionsURL
				modelResource="com.liferay.portlet.journal"
				modelResourceDescription="<%= HtmlUtil.escape(themeDisplay.getScopeGroupName()) %>"
				resourcePrimKey="<%= String.valueOf(scopeGroupId) %>"
				var="permissionsURL"
			/>

			<aui:button href="<%= permissionsURL %>" value="permissions" />
		</c:if>
	</aui:button-row>
</c:if>

<c:if test="<%= Validator.isNotNull(displayTerms.getStructureId()) %>">
	<aui:input name="<%= displayTerms.STRUCTURE_ID %>" type="hidden" value="<%= displayTerms.getStructureId() %>" />

	<div class="portlet-msg-info">
		<liferay-ui:message key="filter-by-structure" />: <%= displayTerms.getStructureId() %><br />
	</div>
</c:if>

<aui:script>
	function <portlet:namespace />addTemplate() {
		var url = '<portlet:renderURL><portlet:param name="struts_action" value="/journal/edit_template" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" /><portlet:param name="structureId" value="<%= Validator.isNotNull(structureId) ? structureId : displayTerms.getStructureId() %>" /></portlet:renderURL>';

		if (toggle_id_journal_template_searchcurClickValue == 'basic') {
			url += '&<portlet:namespace /><%= displayTerms.NAME %>=' + document.<portlet:namespace />fm.<portlet:namespace /><%= displayTerms.KEYWORDS %>.value;

			submitForm(document.hrefFm, url);
		}
		else {
			document.<portlet:namespace />fm.method = 'post';
			submitForm(document.<portlet:namespace />fm, url);
		}
	}

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) || windowState.equals(LiferayWindowState.POP_UP) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace /><%= displayTerms.TEMPLATE_ID %>);
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace /><%= displayTerms.KEYWORDS %>);
	</c:if>
</aui:script>