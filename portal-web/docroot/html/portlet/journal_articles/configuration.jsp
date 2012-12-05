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

<%@ include file="/html/portlet/journal_articles/init.jsp" %>

<%
int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);

String redirect = ParamUtil.getString(request, "redirect");

groupId = ParamUtil.getLong(request, "groupId", groupId);
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationActionURL" />
<liferay-portlet:renderURL portletConfiguration="true" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm1">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value='<%= configurationRenderURL + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur=" + cur %>' />
	<aui:input name="preferences--ddmStructureId--" type="hidden" value="<%= ddmStructureId %>" />

	<liferay-ui:panel-container extended="<%= true %>" id="journalArticlesSettingsPanelContainer" persistState="<%= true %>">
		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="journalArticlesFilterPanel" persistState="<%= true %>" title="filter">
			<aui:fieldset>
				<aui:select label="site" name="preferences--groupId--">
					<aui:option label="global" selected="<%= groupId == themeDisplay.getCompanyGroupId() %>" value="<%= themeDisplay.getCompanyGroupId() %>" />

					<%
					List<Group> mySites = user.getMySites();

					for (int i = 0; i < mySites.size(); i++) {
						Group group = mySites.get(i);

						String groupName = HtmlUtil.escape(group.getDescriptiveName(locale));

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
					String ddmStructureName = StringPool.BLANK;
					String ddmStructureDescription = StringPool.BLANK;

					if (ddmStructure != null) {
						ddmStructureName = HtmlUtil.escape(ddmStructure.getName(locale));
						ddmStructureDescription = HtmlUtil.escape(ddmStructure.getDescription(locale));
					}
					else {
						ddmStructureName = LanguageUtil.get(pageContext, "any");
					}
					%>

					<div id="<portlet:namespace />structure">
						<%= ddmStructureName %>

						<c:if test="<%= Validator.isNotNull (ddmStructureDescription) %>">
							<em>(<%= ddmStructureDescription %>)</em>
						</c:if>
					</div>

					<aui:button onClick='<%= renderResponse.getNamespace() + "openStructureSelector();" %>' value="select" />

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
					for (int pageDeltaValue : PropsValues.JOURNAL_ARTICLES_PAGE_DELTA_VALUES) {
					%>

						<aui:option label="<%= pageDeltaValue %>" selected="<%= pageDelta == pageDeltaValue %>" />

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
	function <portlet:namespace />openStructureSelector() {
		Liferay.Util.openDDMPortlet(
			{
			chooseCallback: '<portlet:namespace />selectStructure',
			classNameId: '<%= PortalUtil.getClassNameId(DDMStructure.class) %>',
			classPK: <%= ddmStructureId %>,
			ddmResource: '<%= ddmResource %>',
			dialog: {
				width: 820
			},
			groupId: <%= groupId %>,
			saveCallback: '<portlet:namespace />selectStructure',
			storageType: '<%= PropsValues.DYNAMIC_DATA_LISTS_STORAGE_TYPE %>',
			structureName: 'structure',
			structureType: 'com.liferay.portlet.journal.model.JournalArticle',
			title: '<%= UnicodeLanguageUtil.get(pageContext, "structures") %>'
			}
		);
	}

	Liferay.provide(
		window,
		'<portlet:namespace />removeStructure',
		function() {
			var A = AUI();

			document.<portlet:namespace />fm1.<portlet:namespace />ddmStructureId.value = "";

			A.one('#<portlet:namespace />structure').html('<%= UnicodeLanguageUtil.get(pageContext, "any") %>');
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />selectStructure',
		function(ddmStructureId, ddmStructureName, dialog) {
			var A = AUI();

			document.<portlet:namespace />fm1.<portlet:namespace />ddmStructureId.value = ddmStructureId;

			A.one('#<portlet:namespace />structure').html(ddmStructureId + ' <em>(' + ddmStructureName + ')</em>');

			if (dialog) {
				dialog.close();
			}
		},
		['aui-base']
	);
</aui:script>