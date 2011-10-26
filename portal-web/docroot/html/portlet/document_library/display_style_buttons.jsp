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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
String navigation = ParamUtil.getString(request, "navigation", "documents-home");

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

long fileEntryTypeId = ParamUtil.getLong(request, "fileEntryTypeId", -1);

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(PortletKeys.DOCUMENT_LIBRARY, "display-style", PropsValues.DL_DEFAULT_DISPLAY_VIEW);
}

String keywords = ParamUtil.getString(request, "keywords");
%>

<c:if test="<%= displayViews.length > 1 %>">
	<aui:script use="aui-dialog,aui-dialog-iframe">
		var buttonRow = A.one('#<portlet:namespace />displayStyleToolbar');

		function onButtonClick(displayStyle) {
			var config = {
				'<portlet:namespace />struts_action': '<%= Validator.isNull(keywords) ? "/document_library/view" : "/document_library/search" %>',
				'<portlet:namespace />navigation': '<%= HtmlUtil.escapeJS(navigation) %>',
				'<portlet:namespace />folderId': '<%= String.valueOf(folderId) %>',
				'<portlet:namespace />displayStyle': displayStyle,
				'<portlet:namespace />saveDisplayStyle': <%= Boolean.TRUE.toString() %>
			};

			if (<%= Validator.isNull(keywords) %>) {
				config['<portlet:namespace />viewEntries'] = <%= Boolean.TRUE.toString() %>;
			}
			else {
				config['<portlet:namespace />keywords'] = '<%= HtmlUtil.escapeJS(keywords) %>';
			}

			if (<%= fileEntryTypeId != -1 %>) {
				config['<portlet:namespace />fileEntryTypeId'] = '<%= String.valueOf(fileEntryTypeId) %>';
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
					src: 0
				}
			);
		}

		var displayStyleToolbar = new A.Toolbar(
			{
				activeState: true,
				boundingBox: buttonRow,
				children: [

					<%
					if (displayViews.length > 0) {
					%>

						{
							handler: A.bind(onButtonClick, null, '<%= displayViews[0] %>'),
							icon: 'display-<%= displayViews[0] %>',
							title: '<%= UnicodeLanguageUtil.get(pageContext, displayViews[0] + "-view") %>'
						}

					<%
						for (int i = 1; i < displayViews.length; i++) {
					%>

							, {
								handler: A.bind(onButtonClick, null, '<%= displayViews[i] %>'),
								icon: 'display-<%= displayViews[i] %>',
								title: '<%= UnicodeLanguageUtil.get(pageContext, displayViews[i] + "-view") %>'
							}

					<%
						}
					}
					%>

				]
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