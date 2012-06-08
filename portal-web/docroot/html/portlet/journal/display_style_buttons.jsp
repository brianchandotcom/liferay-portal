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

<%
String navigation = ParamUtil.getString(liferayPortletRequest, "navigation", "home");

long folderId = GetterUtil.getLong((String)liferayPortletRequest.getAttribute("view.jsp-folderId"));

String structureId = ParamUtil.getString(liferayPortletRequest, "structureId");

String displayStyle = ParamUtil.getString(liferayPortletRequest, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(PortletKeys.JOURNAL, "display-style", PropsValues.JOURNAL_DEFAULT_DISPLAY_VIEW);
}

String keywords = ParamUtil.getString(liferayPortletRequest, "keywords");

boolean advancedSearch = ParamUtil.getBoolean(liferayPortletRequest, DisplayTerms.ADVANCED_SEARCH, false);
%>

<c:if test="<%= displayViews.length > 1 %>">
	<aui:script use="aui-base,aui-toolbar">
		var buttonRow = A.one('#<portlet:namespace />displayStyleToolbar');

		function onButtonClick(displayStyle) {
			var config = {
				'<portlet:namespace />struts_action': '<%= Validator.isNull(keywords) ? "/journal/view" : "/journal/search" %>',
				'<portlet:namespace />navigation': '<%= HtmlUtil.escapeJS(navigation) %>',
				'<portlet:namespace />folderId': '<%= folderId %>',
				'<portlet:namespace />displayStyle': displayStyle,
				'<portlet:namespace />viewEntries': <%= Boolean.FALSE.toString() %>,
				'<portlet:namespace />viewEntriesPage': <%= Boolean.TRUE.toString() %>,
				'<portlet:namespace />viewFolders': <%= Boolean.FALSE.toString() %>,
				'<portlet:namespace />saveDisplayStyle': <%= Boolean.TRUE.toString() %>,
				'<portlet:namespace />searchType': <%= JournalSearchConstants.FRAGMENT %>
			};

			if (<%= Validator.isNull(keywords) %>) {
				config['<portlet:namespace />viewEntries'] = <%= Boolean.TRUE.toString() %>;
			}
			else {
				config['<portlet:namespace />keywords'] = '<%= HtmlUtil.escapeJS(keywords) %>';
			}

			if (<%= !structureId.equals("0") %>) {
				config['<portlet:namespace />structureId'] = '<%= HtmlUtil.escapeJS(structureId) %>';
			}

			updateDisplayStyle(config);
		}

		function updateDisplayStyle(config) {
			var displayStyle = config['<portlet:namespace />displayStyle'];

			<%
			for (int i = 0; i < displayViews.length; i++) {
			%>

				displayStyleToolbar.item(<%= i %>).StateInteraction.set('active', (displayStyle === '<%= displayViews[i] %>'));

			<%
			}
			%>

			Liferay.fire(
				'<portlet:namespace />dataRequest',
				{
					requestParams: config,
					src: Liferay.JOURNAL_ENTRIES_PAGINATOR
				}
			);
		};

		var displayStyleToolbarChildren = [];

		<%
		for (int i = 0; i < displayViews.length; i++) {
		%>

			displayStyleToolbarChildren.push(
				{
					handler: A.bind(onButtonClick, null, '<%= displayViews[i] %>'),
					icon: 'display-<%= displayViews[i] %>',
					title: '<%= UnicodeLanguageUtil.get(pageContext, displayViews[i] + "-view") %>'
				}
			);

		<%
		}
		%>

		var displayStyleToolbar = new A.Toolbar(
			{
				activeState: true,
				boundingBox: buttonRow,
				children: displayStyleToolbarChildren
			}
		).render();

		var index = 0;

		<%
		for (int i = 0; i < displayViews.length; i++) {
			if (displayStyle.equals(displayViews[i])) {
		%>

				index = <%= i %>;

		<%
				break;
			}
		}
		%>

		displayStyleToolbar.item(index).StateInteraction.set('active', true);

		buttonRow.setData('displayStyleToolbar', displayStyleToolbar);
	</aui:script>
</c:if>