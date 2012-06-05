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
String navigation = ParamUtil.getString(request, "navigation", "home");

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

String structureId = ParamUtil.getString(request, "structureId");

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(PortletKeys.JOURNAL, "display-style", PropsValues.JOURNAL_DEFAULT_DISPLAY_VIEW);
}

String keywords = ParamUtil.getString(request, "keywords");

boolean advancedSearch = ParamUtil.getBoolean(request, DisplayTerms.ADVANCED_SEARCH, false);
%>

<c:if test="<%= displayViews.length > 1 %>">
	<aui:script use="aui-base,aui-toolbar">
		var buttonRow = A.one('#<portlet:namespace />displayStyleToolbar');

		var onButtonClick = function(displayStyle) {

			<%
			PortletURL iconURL = renderResponse.createRenderURL();

			iconURL.setParameter("struts_action", "/journal/view");
			iconURL.setParameter("displayStyle", "icon");
			iconURL.setParameter("saveDisplayStyle", Boolean.TRUE.toString());
			iconURL.setParameter("folderId", String.valueOf(folderId));
			iconURL.setParameter("navigation", String.valueOf(navigation));

			PortletURL descriptiveURL = renderResponse.createRenderURL();

			descriptiveURL.setParameter("struts_action", "/journal/view");
			descriptiveURL.setParameter("displayStyle", "descriptive");
			descriptiveURL.setParameter("saveDisplayStyle", Boolean.TRUE.toString());
			descriptiveURL.setParameter("folderId", String.valueOf(folderId));
			descriptiveURL.setParameter("navigation", String.valueOf(navigation));

			PortletURL listURL = renderResponse.createRenderURL();

			listURL.setParameter("struts_action", "/journal/view");
			listURL.setParameter("displayStyle", "list");
			listURL.setParameter("saveDisplayStyle", Boolean.TRUE.toString());
			listURL.setParameter("folderId", String.valueOf(folderId));
			listURL.setParameter("navigation", String.valueOf(navigation));

			if (Validator.isNotNull(keywords)) {
				iconURL.setParameter("keywords", HtmlUtil.escape(keywords));
				iconURL.setParameter(DisplayTerms.ADVANCED_SEARCH, String.valueOf(advancedSearch));

				descriptiveURL.setParameter("keywords", HtmlUtil.escape(keywords));
				descriptiveURL.setParameter(DisplayTerms.ADVANCED_SEARCH, String.valueOf(advancedSearch));

				listURL.setParameter("keywords", HtmlUtil.escape(keywords));
				listURL.setParameter(DisplayTerms.ADVANCED_SEARCH, String.valueOf(advancedSearch));
			}

			if (Validator.isNotNull(structureId)) {
				iconURL.setParameter("structureId", HtmlUtil.escape(structureId));

				descriptiveURL.setParameter("structureId", HtmlUtil.escape(structureId));

				listURL.setParameter("structureId", HtmlUtil.escape(structureId));
			}
			%>

			if (displayStyle === 'icon') {
				updateDisplayStyle('<%= iconURL %>', displayStyle);
			}
			else if (displayStyle === 'descriptive') {
				updateDisplayStyle('<%= descriptiveURL %>', displayStyle);
			}
			else if (displayStyle === 'list') {
				updateDisplayStyle('<%= listURL %>', displayStyle);
			}
		};

		var updateDisplayStyle = function(url, displayStyle) {

			<%
			for (int i = 0; i < displayViews.length; i++) {
			%>

				displayStyleToolbar.item(<%= i %>).StateInteraction.set('active', (displayStyle === '<%= displayViews[i] %>'));

			<%
			}
			%>

			location.href = url;
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