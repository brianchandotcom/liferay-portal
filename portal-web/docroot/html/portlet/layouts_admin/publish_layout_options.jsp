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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Layout curLayout = (Layout)row.getObject();
%>

<div class="layout">

	<%
	String taglibHref = "javascript:Liferay.LayoutExporter.details({toggle: '#" + renderResponse.getNamespace() + "_detail_" + curLayout.getPlid() + "_toggle img', detail: '#_detail_" + curLayout.getPlid() + "'});";
	%>

	<em class="hide" id="<portlet:namespace /><%= curLayout.getPlid() %>includeChildren"><liferay-ui:message key="include-all-descendent-pages" /></em>

	<liferay-ui:icon cssClass="nobr" id='<%= "_detail_" + curLayout.getPlid() + "_toggle" %>' image="../arrows/01_plus" label="<%= true %>" message="change" target="_self" toolTip="options" url="<%= taglibHref %>" />
</div>

<div class="hide export-layout-detail" id="_detail_<%= curLayout.getPlid() %>" style="border-top: 1px solid #CCC; margin-top: 4px; padding-top: 4px; width: 95%;">
	<c:if test="<%= !curLayout.getChildren().isEmpty() %>">
		<aui:input checked="<%= false %>" label="include-all-descendent-pages" name='<%= "includeChildren_" + curLayout.getPlid() %>' type="checkbox" value="<%= false %>" />
	</c:if>
</div>

<aui:script use="aui-base">
	var childrenMsg = A.one('#<portlet:namespace /><%= curLayout.getPlid() %>includeChildren');

	var childrenCheckbox = A.one('#<portlet:namespace />includeChildren_<%= curLayout.getPlid() %>Checkbox');

	<c:if test="<%= !curLayout.getChildren().isEmpty() %>">
		childrenCheckbox.on(
			'change',
			function(event) {
				childrenMsg.toggle();
			}
		);
	</c:if>
</aui:script>