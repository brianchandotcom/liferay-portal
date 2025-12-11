<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String action = (String)request.getAttribute("render_controls.jsp-action");
boolean childControl = GetterUtil.getBoolean(String.valueOf(request.getAttribute("render_controls.jsp-childControl")));
boolean disableInputs = GetterUtil.getBoolean(request.getAttribute("render_controls.jsp-disableInputs"));
ManifestSummary manifestSummary = (ManifestSummary)request.getAttribute("render_controls.jsp-manifestSummary");
Map<String, String[]> parameterMap = (Map<String, String[]>)GetterUtil.getObject(request.getAttribute("render_controls.jsp-parameterMap"), Collections.emptyMap());
PortletDataHandlerControl[] portletDataHandlerControls = (PortletDataHandlerControl[])request.getAttribute("render_controls.jsp-controls");
String portletId = (String)request.getAttribute("render_controls.jsp-portletId");
String rootControlId = (String)request.getAttribute("render_controls.jsp-rootControlId");

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

				String controlLabel = LanguageUtil.get(request, resourceBundle, portletDataHandlerBoolean.getControlLabel());

				String className = portletDataHandlerControls[i].getClassName();

				if (Validator.isNotNull(className) && (manifestSummary != null)) {
					StagedModelType stagedModelType = new StagedModelType(className, portletDataHandlerControls[i].getReferrerClassName());

					long modelAdditionCount = manifestSummary.getModelAdditionCount(stagedModelType);

					if (modelAdditionCount != 0) {
						controlLabel += (modelAdditionCount > 0) ? " (" + modelAdditionCount + ")" : StringPool.BLANK;
					}
					else {
						continue control;
					}
				}

				Map<String, Object> data = HashMapBuilder.<String, Object>put(
					"name", controlLabel
				).build();

				if (!childControl) {
					data.put("root-control-id", liferayPortletResponse.getNamespace() + rootControlId);
				}

				PortletDataHandlerControl[] childrenPortletDataHandlerControls = portletDataHandlerBoolean.getChildrenPortletDataHandlerControls();

				String controlName = Validator.isNotNull(portletDataHandlerBoolean.getNamespace()) ? portletDataHandlerBoolean.getNamespacedControlName() : (portletDataHandlerBoolean.getControlName() + StringPool.UNDERLINE + portletId);
				%>

				<aui:input data="<%= data %>" disabled="<%= portletDataHandlerControls[i].isDisabled() || disableInputs %>" helpMessage="<%= portletDataHandlerBoolean.getHelpMessage(locale, action) %>" label="<%= controlLabel %>" name="<%= controlName %>" type="checkbox" value="<%= MapUtil.getBoolean(parameterMap, controlName, portletDataHandlerBoolean.getDefaultState()) || MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA_ALL) %>" />

				<c:if test="<%= childrenPortletDataHandlerControls != null %>">
					<ul class="list-unstyled" id="<portlet:namespace /><%= controlName %>Controls">

						<%
						request.setAttribute("render_controls.jsp-childControl", true);
						request.setAttribute("render_controls.jsp-controls", childrenPortletDataHandlerControls);
						%>

						<liferay-util:include page="/render_controls.jsp" servletContext="<%= application %>" />
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
						String defaultChoice = (choices != null) ? choices[portletDataHandlerChoice.getDefaultChoiceIndex()] : "";

						String controlValue = MapUtil.getString(parameterMap, portletDataHandlerChoice.getNamespacedControlName(), defaultChoice);

						String controlName = LanguageUtil.get(request, resourceBundle, choice);
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
							label="<%= controlName %>"
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