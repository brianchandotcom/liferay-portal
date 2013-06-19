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

<%@ include file="/html/portlet/portlet_configuration/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

PortletPreferences preferences = renderRequest.getPreferences();

String scopeType = GetterUtil.getString(preferences.getValue("lfrScopeType", null));
String scopeLayoutUuid = GetterUtil.getString(preferences.getValue("lfrScopeLayoutUuid", null));

Group group = layout.getGroup();
%>

<liferay-util:include page="/html/portlet/portlet_configuration/tabs1.jsp">
	<liferay-util:param name="tabs1" value="scope" />
</liferay-util:include>

<portlet:actionURL var="editScopeURL">
	<portlet:param name="struts_action" value="/portlet_configuration/edit_scope" />
</portlet:actionURL>

<aui:form action="<%= editScopeURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.SAVE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="returnToFullPageURL" type="hidden" value="<%= returnToFullPageURL %>" />
	<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />

	<aui:fieldset>
		<aui:select label="scope" name="scopeType">
			<aui:option label="default" selected="<%= Validator.isNull(scopeType) %>" value="" />
			<aui:option label="global" selected='<%= scopeType.equals("company") %>' value="company" />
			<aui:option label="select-layout" selected='<%= scopeType.equals("layout") %>' value="layout" />
		</aui:select>

		<div id="<portlet:namespace />scopeLayoutUuidContainer">
			<aui:select label="scope-layout" name="scopeLayoutUuid">
				<aui:option label='<%= LanguageUtil.get(pageContext,"current-page") + " (" + HtmlUtil.escape(layout.getName(locale)) + ")" %>' selected="<%= scopeLayoutUuid.equals(layout.getUuid()) %>" value="<%= layout.getUuid() %>" />

				<%
				for (Layout curLayout : LayoutLocalServiceUtil.getScopeGroupLayouts(layout.getGroupId(), layout.isPrivateLayout())) {
					if (curLayout.getPlid() == layout.getPlid()) {
						continue;
					}
				%>

					<aui:option label="<%= HtmlUtil.escape(curLayout.getName(locale)) %>" selected="<%= scopeLayoutUuid.equals(curLayout.getUuid()) %>" value="<%= curLayout.getUuid() %>" />

				<%
				}
				%>

			</aui:select>
		</div>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	Liferay.Util.toggleSelectBox('<portlet:namespace />scopeType', 'layout', '<portlet:namespace />scopeLayoutUuidContainer');
</aui:script>