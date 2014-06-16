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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
String action = (String)request.getAttribute("render_controls.jsp-action");
PortletDataHandlerControl[] controls = (PortletDataHandlerControl[])request.getAttribute("render_controls.jsp-controls");
ManifestSummary manifestSummary = (ManifestSummary)request.getAttribute("render_controls.jsp-manifestSummary");
Map<String, String[]> parameterMap = (Map<String, String[]>)GetterUtil.getObject(request.getAttribute("render_controls.jsp-parameterMap"), Collections.emptyMap());
String portletId = (String)request.getAttribute("render_controls.jsp-portletId");

control:
for (int i = 0; i < controls.length; i++) {
%>

	<li class="handler-control">
		<c:choose>
			<c:when test="<%= controls[i] instanceof PortletDataHandlerBoolean %>">

				<%
				Map<String, Object> data = new HashMap<String, Object>();

				PortletDataHandlerBoolean control = (PortletDataHandlerBoolean)controls[i];

				String controlLabel = LanguageUtil.get(pageContext, control.getControlLabel());

				String className = controls[i].getClassName();

				if (Validator.isNotNull(className) && (manifestSummary != null)) {
					long modelAdditionCount = manifestSummary.getModelAdditionCount(className, controls[i].getReferrerClassName());

					if (modelAdditionCount != 0) {
						controlLabel += modelAdditionCount > 0 ? " (" + modelAdditionCount + ")" : StringPool.BLANK;
					}
					else {
						continue control;
					}
				}

				data.put("name", controlLabel);

				PortletDataHandlerControl[] children = control.getChildren();

				String controlName = Validator.isNotNull(control.getNamespace()) ? control.getNamespacedControlName() : (control.getControlName() + StringPool.UNDERLINE + portletId);
				%>

				<aui:input data="<%= data %>" disabled="<%= controls[i].isDisabled() %>" helpMessage="<%= control.getHelpMessage(locale, action) %>" label="<%= controlLabel %>" name="<%= controlName %>" type="checkbox" value="<%= MapUtil.getBoolean(parameterMap, controlName, control.getDefaultState()) %>" />

				<c:if test="<%= children != null %>">
					<ul class="list-unstyled" id="<portlet:namespace /><%= controlName %>Controls">

						<%
						request.setAttribute("render_controls.jsp-controls", children);
						%>

						<liferay-util:include page="/html/portlet/layouts_admin/render_controls.jsp" />
					</ul>

					<aui:script>
						Liferay.Util.toggleBoxes('<portlet:namespace /><%= controlName %>', '<portlet:namespace /><%= controlName %>Controls', false, true);
					</aui:script>
				</c:if>
			</c:when>
			<c:when test="<%= controls[i] instanceof PortletDataHandlerChoice %>">
				<aui:field-wrapper label='<%= "&#9632" + LanguageUtil.get(pageContext, controls[i].getControlLabel()) %>'>

					<%
					PortletDataHandlerChoice control = (PortletDataHandlerChoice)controls[i];

					String[] choices = control.getChoices();

					for (int j = 0; j < choices.length; j++) {
						String choice = choices[j];

						Map<String, Object> data = new HashMap<String, Object>();

						String controlName = LanguageUtil.get(pageContext, choice);

						data.put("name", controlName);
					%>

						<aui:input checked="<%= MapUtil.getBoolean(parameterMap, control.getNamespacedControlName(), control.getDefaultChoiceIndex() == j) %>" data="<%= data %>" helpMessage="<%= control.getHelpMessage(locale, action) %>" label="<%= choice %>" name="<%= control.getNamespacedControlName() %>" type="radio" value="<%= choices[j] %>" />

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