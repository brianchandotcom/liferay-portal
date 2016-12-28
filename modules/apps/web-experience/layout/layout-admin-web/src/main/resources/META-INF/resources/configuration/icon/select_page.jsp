<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

<%@ include file="/init.jsp" %>

<%
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectPage");

Layout selLayout = layoutsAdminDisplayContext.getSelLayout();
%>

<p>
	<c:if test="<%= selLayout != null %>">
		<liferay-ui:message arguments="<%= HtmlUtil.escape(selLayout.getName(locale)) %>" key="the-applications-in-page-x-will-be-replaced-with-the-ones-in-the-page-you-select-below" translateArguments="<%= false %>" />
	</c:if>
</p>

<liferay-util:include page="/html/portal/layout/edit/portlet_applications.jsp" />

<aui:script use="aui-base">
	var copyLayoutId = A.one('#<portlet:namespace />copyLayoutId');

	Liferay.Util.getOpener().Liferay.fire(
		'<%= HtmlUtil.escapeJS(eventName) %>',
		{
			data: copyLayoutId.getDOMNode()
		}
	);
</aui:script>