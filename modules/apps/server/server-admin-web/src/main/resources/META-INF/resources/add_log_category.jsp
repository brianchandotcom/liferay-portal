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
int delta = ParamUtil.getInteger(request, SearchContainer.DEFAULT_DELTA_PARAM, SearchContainer.DEFAULT_DELTA);

PortletURL serverURL = renderResponse.createRenderURL();

serverURL.setParameter("mvcRenderCommandName", "/server_admin/view");
serverURL.setParameter("tabs1", tabs1);
serverURL.setParameter("delta", String.valueOf(delta));

String backURL = serverURL.toString();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);
%>

<portlet:actionURL name="/server_admin/edit_server" var="addLogCategoryURL">
	<portlet:param name="cmd" value="addLogLevel" />
	<portlet:param name="redirect" value="<%= String.valueOf(serverURL) %>" />
</portlet:actionURL>

<aui:form action="<%= addLogCategoryURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:input cssClass="lfr-input-text-container" label="logger-name" name="loggerName" type="text" />

			<aui:select label="log-level" name="priority">

				<%
				for (int i = 0; i < Levels.ALL_LEVELS.length; i++) {
				%>

					<aui:option label="<%= Levels.ALL_LEVELS[i] %>" selected="<%= Level.INFO.equals(Levels.ALL_LEVELS[i]) %>" />

				<%
				}
				%>

			</aui:select>
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= backURL %>" type="cancel" />
	</aui:button-row>
</aui:form>