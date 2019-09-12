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

<%@ include file="/portlet_list/init.jsp" %>

<%
String action = (String)request.getAttribute("render_controls.jsp-action");
boolean childControl = GetterUtil.getBoolean(String.valueOf(request.getAttribute("render_controls.jsp-childControl")));
PortletDataHandlerControl[] controls = (PortletDataHandlerControl[])request.getAttribute("render_controls.jsp-controls");
ManifestSummary manifestSummary = (ManifestSummary)request.getAttribute("render_controls.jsp-manifestSummary");
String portletId = (String)request.getAttribute("render_controls.jsp-portletId");

if (Validator.isNotNull(portletId)) {
	PortletBag portletBag = PortletBagPool.get(portletId);

	ResourceBundle portletResourceBundle = portletBag.getResourceBundle(locale);

	if (portletResourceBundle != null) {
		resourceBundle = new AggregateResourceBundle(resourceBundle, portletResourceBundle);
	}
}

control:
for (int i = 0; i < controls.length; i++) {
%>

	<li class="handler-control">
		<c:choose>
			<c:when test="<%= controls[i] instanceof PortletDataHandlerBoolean %>">

				<%
				Map<String, Object> data = new HashMap<String, Object>();

				PortletDataHandlerBoolean control = (PortletDataHandlerBoolean)controls[i];

				String controlLabel = LanguageUtil.get(request, resourceBundle, control.getControlLabel());

				String className = controls[i].getClassName();

				if (Validator.isNotNull(className) && (manifestSummary != null)) {
					StagedModelType stagedModelType = new StagedModelType(className, controls[i].getReferrerClassName());

					long modelAdditionCount = manifestSummary.getModelAdditionCount(stagedModelType);

					if (modelAdditionCount != 0) {
						controlLabel += modelAdditionCount > 0 ? " (" + modelAdditionCount + ")" : StringPool.BLANK;
					}
					else if (!showAllPortlets) {
						continue control;
					}
				}

				data.put("name", controlLabel);

				if (!childControl) {
					data.put("root-control-id", liferayPortletResponse.getNamespace() + PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + portletId);
				}

				PortletDataHandlerControl[] children = control.getChildren();

				String controlName = Validator.isNotNull(control.getNamespace()) ? control.getNamespacedControlName() : (control.getControlName() + StringPool.UNDERLINE + portletId);

				String controlInputName = controlName;

				boolean disabled = controls[i].isDisabled() || disableInputs;

				if (disabled) {
					controlInputName += "Display";
				%>

					<aui:input name="<%= controlName %>" type="hidden" value="<%= MapUtil.getBoolean(parameterMap, controlName, control.getDefaultState()) || MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA_ALL) %>" />

				<%
				}
				%>

				<aui:input checked="<%= MapUtil.getBoolean(parameterMap, controlName, control.getDefaultState()) || MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA_ALL) %>" data="<%= data %>" disabled="<%= disabled %>" helpMessage="<%= control.getHelpMessage(locale, action) %>" ignoreRequestValue="<%= disabled %>" label="<%= controlLabel %>" name="<%= controlInputName %>" type="checkbox" />

				<c:if test="<%= children != null %>">
					<ul class="list-unstyled" id="<portlet:namespace /><%= controlName %>Controls">

						<%
						request.setAttribute("render_controls.jsp-childControl", true);
						request.setAttribute("render_controls.jsp-controls", children);
						%>

						<liferay-util:include page="/portlet_list/render_controls.jsp" servletContext="<%= application %>" />
					</ul>

					<aui:script>
						Liferay.Util.toggleBoxes(
							'<portlet:namespace /><%= controlName %>',
							'<portlet:namespace /><%= controlName %>Controls',
							false,
							true
						);
					</aui:script>
				</c:if>
			</c:when>
			<c:when test="<%= controls[i] instanceof PortletDataHandlerChoice %>">
				<label>
					<%= LanguageUtil.get(request, resourceBundle, controls[i].getControlLabel()) %>

					<%
					PortletDataHandlerChoice control = (PortletDataHandlerChoice)controls[i];

					String[] choices = control.getChoices();

					for (int j = 0; j < choices.length; j++) {
						String choice = choices[j];

						String defaultChoice = (choices != null) ? choices[control.getDefaultChoiceIndex()] : "";

						String controlValue = MapUtil.getString(parameterMap, control.getNamespacedControlName(), defaultChoice);

						Map<String, Object> data = new HashMap<String, Object>();

						String controlName = LanguageUtil.get(request, resourceBundle, choice);

						data.put("name", controlName);
					%>

						<aui:input checked="<%= controlValue.equals(choices[j]) %>" data="<%= data %>" disabled="<%= disableInputs %>" helpMessage="<%= control.getHelpMessage(locale, action) %>" label="<%= choice %>" name="<%= control.getNamespacedControlName() %>" type="radio" value="<%= choices[j] %>" />

					<%
					}
					%>

				</label>
			</c:when>
		</c:choose>
	</li>

<%
}
%>