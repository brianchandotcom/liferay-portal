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
PortletDataHandlerControl[] controls = (PortletDataHandlerControl[])request.getAttribute("render_controls.jsp-controls");

for (int i = 0; i < controls.length; i++) {
%>

	<li class="handler-control">
		<c:choose>
			<c:when test="<%= controls[i] instanceof PortletDataHandlerBoolean %>">

				<%
				PortletDataHandlerBoolean control = (PortletDataHandlerBoolean)controls[i];
				PortletDataHandlerControl[] children = control.getChildren();

				Map<String, Object> data = new HashMap<String, Object>();

				data.put("name", LanguageUtil.get(pageContext, controls[i].getControlName()));
				%>

				<aui:input data="<%= data %>" disabled="<%= controls[i].isDisabled() %>" label="<%= controls[i].getControlName() %>" name="<%= control.getNamespacedControlName() %>" type="checkbox" value="<%= control.getDefaultState() %>" />

				<c:if test="<%= children != null %>">
					<ul id="<portlet:namespace /><%= control.getNamespacedControlName() %>Controls">

						<%
						request.setAttribute("render_controls.jsp-controls", children);
						%>

						<liferay-util:include page="/html/portlet/layouts_admin/render_controls.jsp" />
					</ul>

					<aui:script>
						Liferay.Util.toggleBoxes('<portlet:namespace /><%= control.getNamespacedControlName() %>Checkbox','<portlet:namespace /><%= control.getNamespacedControlName() %>Controls');
					</aui:script>
				</c:if>
			</c:when>
			<c:when test="<%= controls[i] instanceof PortletDataHandlerChoice %>">
				<aui:field-wrapper label='<%= "&#9632" + LanguageUtil.get(pageContext, controls[i].getControlName()) %>'>

					<%
					PortletDataHandlerChoice control = (PortletDataHandlerChoice)controls[i];
					String[] choices = control.getChoices();

					for (int j = 0; j < choices.length; j++) {
						String choice = choices[j];

						Map<String, Object> data = new HashMap<String, Object>();

						data.put("name", LanguageUtil.get(pageContext, choice));
					%>

						<aui:input checked="<%= control.getDefaultChoiceIndex() == j %>" data="<%= data %>" label="<%= choice %>" name="<%= control.getNamespacedControlName() %>" type="radio" value="<%= choices[j] %>" />

					<%
					}
					%>

				</aui:field-wrapper>
			</c:when>
		</c:choose>
	</li>

<%
}
%>