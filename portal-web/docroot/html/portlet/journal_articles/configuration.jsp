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

<%@ include file="/html/portlet/journal_articles/init.jsp" %>

<%
int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);

String redirect = ParamUtil.getString(request, "redirect");

groupId = ParamUtil.getLong(request, "groupId", groupId);

JournalStructure structure= null;

if (Validator.isNotNull(structureId)) {
	try {
		structure = JournalStructureLocalServiceUtil.getStructure(groupId, structureId);
	}
	catch (NoSuchStructureException nsse1) {
		try {
			structure = JournalStructureLocalServiceUtil.getStructure(themeDisplay.getCompanyGroupId(), structureId);
		}
		catch (NoSuchStructureException nsse2) {
			structureId = StringPool.BLANK;

			preferences.setValue("structure-id", structureId);

			preferences.store();
		}
	}
}
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationActionURL" />
<liferay-portlet:renderURL portletConfiguration="true" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm1">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value='<%= configurationRenderURL + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur=" + cur %>' />
	<aui:input name="preferences--structureId--" type="hidden" value="<%= structureId %>" />

	<liferay-ui:panel-container extended="<%= true %>" id="journalArticlesSettingsPanelContainer" persistState="<%= true %>">
		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="journalArticlesFilterPanel" persistState="<%= true %>" title="filter">
			<aui:fieldset>
				<aui:select label="site" name="preferences--groupId--">
					<aui:option label="global" selected="<%= groupId == themeDisplay.getCompanyGroupId() %>" value="<%= themeDisplay.getCompanyGroupId() %>" />

					<%
					List<Group> mySites = user.getMySites();

					for (int i = 0; i < mySites.size(); i++) {
						Group group = mySites.get(i);

						String groupName = HtmlUtil.escape(group.getDescriptiveName());

						if (group.isUser()) {
							groupName = LanguageUtil.get(pageContext, "my-site");
						}
					%>

						<aui:option label="<%= groupName %>" selected="<%= groupId == group.getGroupId() %>" value="<%= group.getGroupId() %>" />

					<%
					}
					%>

				</aui:select>

				<aui:select label="web-content-type" name="preferences--type--">
					<aui:option value="" />

					<%
					for (int i = 0; i < JournalArticleConstants.TYPES.length; i++) {
					%>

						<aui:option label="<%= JournalArticleConstants.TYPES[i] %>" selected="<%= type.equals(JournalArticleConstants.TYPES[i]) %>" />

					<%
					}
					%>

				</aui:select>

				<aui:field-wrapper label="structure">

					<%
					String structureName = StringPool.BLANK;
					String structureDescription = StringPool.BLANK;

					if (structure != null) {
						structureName = structure.getName(locale);
						structureDescription = structure.getDescription(locale);
					}
					else {
						structureName = LanguageUtil.get(pageContext, "any");
					}
					%>

					<div id="<portlet:namespace />structure">
						<%= structureName %>

						<c:if test="<%= Validator.isNotNull (structureDescription) %>">
							<em>(<%= structureDescription %>)</em>
						</c:if>
					</div>

					<liferay-portlet:renderURL portletName="<%= PortletKeys.JOURNAL %>" windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="selectStructureURL">
						<portlet:param name="struts_action" value="/journal/select_structure" />
						<portlet:param name="structureId" value="<%= structureId %>" />
					</liferay-portlet:renderURL>

					<%
					String taglibOpenStructureWindow = "var folderWindow = window.open('" + selectStructureURL + "','structure', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680'); void(''); folderWindow.focus();";
					%>

					<aui:button onClick="<%= taglibOpenStructureWindow %>" value="select" />

					<aui:button name="removeStructureButton" onClick='<%= renderResponse.getNamespace() + "removeStructure();" %>' value="remove" />
				</aui:field-wrapper>
			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="journalArticlesDisplaySettings" persistState="<%= true %>" title="display-settings">
			<aui:fieldset>
				<aui:select label="display-url" name="preferences--pageUrl--">
					<aui:option label="maximized" selected='<%= pageUrl.equals("maximized") %>' />
					<aui:option label="normal" selected='<%= pageUrl.equals("normal") %>' />
					<aui:option label="pop-up" selected='<%= pageUrl.equals("popUp") %>' value="popUp" />
					<aui:option label="view-in-context" selected='<%= pageUrl.equals("viewInContext") %>' value="viewInContext" />
				</aui:select>

				<aui:select label="display-per-page" name="preferences--pageDelta--">

					<%
					String[] pageDeltaValues = PropsUtil.getArray(PropsKeys.JOURNAL_ARTICLES_PAGE_DELTA_VALUES);

					for (int i = 0; i < pageDeltaValues.length; i++) {
					%>

						<aui:option label="<%= pageDeltaValues[i] %>" selected="<%= pageDelta == GetterUtil.getInteger(pageDeltaValues[i]) %>" />

					<%
					}
					%>

				</aui:select>

				<aui:field-wrapper label="order-by-column">
					<aui:select inlineField="<%= true %>" label="" name="preferences--orderByCol--">
						<aui:option label="display-date" selected='<%= orderByCol.equals("display-date") %>' />
						<aui:option label="create-date" selected='<%= orderByCol.equals("create-date") %>' />
						<aui:option label="modified-date" selected='<%= orderByCol.equals("modified-date") %>' />
						<aui:option label="title" selected='<%= orderByCol.equals("title") %>' />
						<aui:option label="id" selected='<%= orderByCol.equals("id") %>' />
					</aui:select>

					<aui:select label="" name="preferences--orderByType--">
						<aui:option label="ascending" selected='<%= orderByType.equals("asc") %>' value="asc" />
						<aui:option label="descending" selected='<%= orderByType.equals("desc") %>' value="desc" />
					</aui:select>
				</aui:field-wrapper>
			</aui:fieldset>
		</liferay-ui:panel>
	</liferay-ui:panel-container>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />removeStructure',
		function() {
			var A = AUI();

			document.<portlet:namespace />fm1.<portlet:namespace />structureId.value = "";

			A.one('#<portlet:namespace />structure').html('<liferay-ui:message key="any" />');
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'<%= PortalUtil.getPortletNamespace(PortletKeys.JOURNAL) %>selectStructure',
		function(structureId, name) {
			var A = AUI();

			document.<portlet:namespace />fm1.<portlet:namespace />structureId.value = structureId;

			A.one('#<portlet:namespace />structure').html(structureId + ' <em>(' + name + ')</em>');
		},
		['aui-base']
	);
</aui:script>