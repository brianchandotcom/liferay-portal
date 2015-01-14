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

<%@ include file="/html/taglib/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_ui_app_view_display_style") + StringPool.UNDERLINE;

String displayStyle = (String)request.getAttribute("liferay-ui:app-view-display-style:displayStyle");
String[] displayStyles = (String[])request.getAttribute("liferay-ui:app-view-display-style:displayStyles");
Map<String, String> requestParams = (Map<String, String>)request.getAttribute("liferay-ui:app-view-display-style:requestParams");
%>

<c:if test="<%= displayStyles.length > 1 %>">
	<div id="<portlet:namespace />displayStyleButtons">
		<liferay-ui:icon-menu direction="down" icon='<%= "../aui/" + HtmlUtil.escapeAttribute(_getIcon(displayStyle)) %>' message="" select="<%= true %>">

			<%
			for (int i = 0; i < displayStyles.length; i++) {
				String dataStyle = displayStyles[i];

				Map<String, Object> data = new HashMap<String, Object>();

				data.put("displayStyle", dataStyle);
			%>

				<liferay-ui:icon data="<%= data %>" image='<%= "../aui/" + HtmlUtil.escapeAttribute(_getIcon(dataStyle)) %>' message="<%= dataStyle %>" onClick='<%= randomNamespace + "onClickDisplayStyle(this);" %>' url="javascript:;" />

			<%
			}
			%>

		</liferay-ui:icon-menu>
	</div>
</c:if>

<c:if test="<%= displayStyles.length > 1 %>">
	<aui:script use="aui-base">
		function changeDisplayStyle(displayStyle) {
			var config = {};

			<%
			Set<String> requestParamNames = requestParams.keySet();

			for (String requestParamName : requestParamNames) {
				String requestParamValue = requestParams.get(requestParamName);
			%>

				config['<portlet:namespace /><%= requestParamName %>'] = '<%= HtmlUtil.escapeJS(requestParamValue) %>';

			<%
			}
			%>

			config['<portlet:namespace />displayStyle'] = displayStyle;
			config['<portlet:namespace />saveDisplayStyle'] = true;

			Liferay.fire(
				'<portlet:namespace />dataRequest',
				{
					requestParams: config,
					src: Liferay.DL_ENTRIES_PAGINATOR
				}
			);
		}

		Liferay.provide(
			window,
			'<%= randomNamespace %>onClickDisplayStyle',
			function(link) {
				var displayStyleItem = A.one(link);

				changeDisplayStyle(displayStyleItem.attr('data-displayStyle'));
			},
			['aui-node']
		);
	</aui:script>
</c:if>

<%!
private String _getIcon(String displayStyle) {
	String displayStyleIcon = displayStyle;

	if (displayStyle.equals("descriptive")) {
		displayStyleIcon = "th-list";
	}
	else if (displayStyle.equals("icon")) {
		displayStyleIcon = "th-large";
	}
	else if (displayStyle.equals("list")) {
		displayStyleIcon = "align-justify";
	}

	return displayStyleIcon;
}
%>