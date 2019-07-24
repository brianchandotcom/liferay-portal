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
String redirect = ParamUtil.getString(request, "redirect");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(LanguageUtil.get(request, "add-category"));
%>

<portlet:actionURL name="/server_admin/edit_server" var="addLogCategoryURL">
	<portlet:param name="cmd" value="addLogLevel" />
	<portlet:param name="redirect" value="<%= String.valueOf(redirect) %>" />
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

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>