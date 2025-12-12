<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/portlet_list/init.jsp" %>

<%
String action = (String)request.getAttribute("render_controls.jsp-action");
boolean childControl = GetterUtil.getBoolean(String.valueOf(request.getAttribute("render_controls.jsp-childControl")));
ManifestSummary manifestSummary = (ManifestSummary)request.getAttribute("render_controls.jsp-manifestSummary");
PortletDataHandlerControl[] portletDataHandlerControls = (PortletDataHandlerControl[])request.getAttribute("render_controls.jsp-controls");

String portletId = (String)request.getAttribute("render_controls.jsp-portletId");

if (Validator.isNotNull(portletId)) {
	PortletBag portletBag = PortletBagPool.get(portletId);

	ResourceBundle portletResourceBundle = portletBag.getResourceBundle(locale);

	if (portletResourceBundle != null) {
		resourceBundle = new AggregateResourceBundle(resourceBundle, portletResourceBundle);
	}
}

control:
for (int i = 0; i < portletDataHandlerControls.length; i++) {
%>

	<li class="handler-control">
		<c:choose>
			<c:when test="<%= portletDataHandlerControls[i] instanceof PortletDataHandlerBoolean %>">

				<%
				PortletDataHandlerBoolean portletDataHandlerBoolean = (PortletDataHandlerBoolean)portletDataHandlerControls[i];

				String className = portletDataHandlerBoolean.getClassName();
				String controlLabel = LanguageUtil.get(request, resourceBundle, portletDataHandlerBoolean.getControlLabel());

				if (Validator.isNotNull(className) && (manifestSummary != null)) {
					StagedModelType stagedModelType = new StagedModelType(className, portletDataHandlerBoolean.getReferrerClassName());

					long modelAdditionCount = manifestSummary.getModelAdditionCount(stagedModelType);

					if (modelAdditionCount != 0) {
						controlLabel += (modelAdditionCount > 0) ? " (" + modelAdditionCount + ")" : StringPool.BLANK;
					}
					else if (!showAllPortlets) {
						continue control;
					}
				}

				Map<String, Object> data = HashMapBuilder.<String, Object>put(
					"name", controlLabel
				).build();

				if (!childControl) {
					data.put("root-control-id", liferayPortletResponse.getNamespace() + PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + portletId);
				}

				String controlName = Validator.isNotNull(portletDataHandlerBoolean.getNamespace()) ? portletDataHandlerBoolean.getNamespacedControlName() : (portletDataHandlerBoolean.getControlName() + StringPool.UNDERLINE + portletId);

				String controlInputName = controlName;

				boolean disabled = portletDataHandlerBoolean.isDisabled() || disableInputs;
				%>

				<c:if test="<%= disabled %>">

					<%
					controlInputName += "Display";
					%>

					<aui:input name="<%= controlName %>" type="hidden" value="<%= MapUtil.getBoolean(parameterMap, controlName, portletDataHandlerBoolean.getDefaultState()) || MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA_ALL) %>" />
				</c:if>

				<%
				RenderControlsDisplayContext renderControlsDisplayContext = new RenderControlsDisplayContext(request);
				%>

				<aui:input checked="<%= renderControlsDisplayContext.isControlCheckboxEnabled(portletDataHandlerBoolean, parameterMap) %>" data="<%= data %>" disabled="<%= disabled %>" helpMessage="<%= portletDataHandlerBoolean.getHelpMessage(locale, action) %>" ignoreRequestValue="<%= disabled %>" label="<%= controlLabel %>" name="<%= controlInputName %>" type="checkbox" />

				<c:if test="<%= portletDataHandlerBoolean.getChildrenPortletDataHandlerControls() != null %>">
					<ul class="list-unstyled" id="<portlet:namespace /><%= controlName %>Controls">

						<%
						request.setAttribute("render_controls.jsp-childControl", true);
						request.setAttribute("render_controls.jsp-controls", portletDataHandlerBoolean.getChildrenPortletDataHandlerControls());
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
			<c:when test="<%= portletDataHandlerControls[i] instanceof PortletDataHandlerChoice %>">
				<label>
					<liferay-ui:message key="<%= portletDataHandlerControls[i].getControlLabel() %>" />

					<%
					PortletDataHandlerChoice portletDataHandlerChoice = (PortletDataHandlerChoice)portletDataHandlerControls[i];

					String[] choices = portletDataHandlerChoice.getChoices();

					for (String choice : choices) {
						String controlName = LanguageUtil.get(request, resourceBundle, choice);
						String controlValue = MapUtil.getString(parameterMap, portletDataHandlerChoice.getNamespacedControlName(), choices[portletDataHandlerChoice.getDefaultChoiceIndex()]);
					%>

						<aui:input
							checked="<%= controlValue.equals(choice) %>"
							data='<%=
								HashMapBuilder.<String, Object>put(
									"name", controlName
								).build()
							%>'
							disabled="<%= disableInputs %>"
							helpMessage="<%= portletDataHandlerChoice.getHelpMessage(locale, action) %>"
							label="<%= choice %>"
							name="<%= portletDataHandlerChoice.getNamespacedControlName() %>"
							type="radio"
							value="<%= choice %>"
						/>

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