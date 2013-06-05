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
String action = (String)request.getAttribute("render_controls.jsp-action");
PortletDataHandlerControl[] controls = (PortletDataHandlerControl[])request.getAttribute("render_controls.jsp-controls");
ManifestSummary manifestSummary = (ManifestSummary)request.getAttribute("render_controls.jsp-manifestSummary");

for (int i = 0; i < controls.length; i++) {
%>

	<li class="handler-control">
		<c:choose>
			<c:when test="<%= controls[i] instanceof PortletDataHandlerBoolean %>">

				<%
				Map<String, Object> data = new HashMap<String, Object>();

				PortletDataHandlerBoolean control = (PortletDataHandlerBoolean)controls[i];

				String controlName = LanguageUtil.get(pageContext, control.getControlName());

				String className = controls[i].getClassName();

				if (Validator.isNotNull(className) && (manifestSummary != null)) {
					long modelCount = manifestSummary.getModelCount(className, controls[i].getReferrerClassName());

					if (modelCount != 0) {
						controlName += modelCount > 0 ? " (" + modelCount + ")" : StringPool.BLANK;
					}
					else {
						continue;
					}
				}

				data.put("name", controlName);

				PortletDataHandlerControl[] children = control.getChildren();
				%>

				<aui:input data="<%= data %>" disabled="<%= controls[i].isDisabled() %>" helpMessage="<%= control.getHelpMessage(locale, action) %>" label="<%= controlName %>" name="<%= control.getNamespacedControlName() %>" type="checkbox" value="<%= control.getDefaultState() %>" />

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

						String controlName = LanguageUtil.get(pageContext, choice);

						data.put("name", controlName);
					%>

						<aui:input checked="<%= control.getDefaultChoiceIndex() == j %>" data="<%= data %>" helpMessage="<%= control.getHelpMessage(locale, action) %>" label="<%= choice %>" name="<%= control.getNamespacedControlName() %>" type="radio" value="<%= choices[j] %>" />

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