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

				String className = portletDataHandlerBoolean.getClassName();
				String label = LanguageUtil.get(request, resourceBundle, portletDataHandlerBoolean.getLabel());

				if (Validator.isNotNull(className) && (manifestSummary != null)) {
					StagedModelType stagedModelType = new StagedModelType(className, portletDataHandlerBoolean.getReferrerClassName());

					long modelAdditionCount = manifestSummary.getModelAdditionCount(stagedModelType);

					if (modelAdditionCount != 0) {
						label += (modelAdditionCount > 0) ? " (" + modelAdditionCount + ")" : StringPool.BLANK;
					}
					else {
						continue control;
					}
				}

				Map<String, Object> data = HashMapBuilder.<String, Object>put(
					"name", label
				).build();

				if (!childControl) {
					data.put("root-control-id", liferayPortletResponse.getNamespace() + rootControlId);
				}

				String name = Validator.isNotNull(portletDataHandlerBoolean.getNamespace()) ? portletDataHandlerBoolean.getNamespacedName() : (portletDataHandlerBoolean.getName() + StringPool.UNDERLINE + portletId);
				%>

				<aui:input data="<%= data %>" disabled="<%= portletDataHandlerBoolean.isDisabled() || disableInputs %>" helpMessage="<%= portletDataHandlerBoolean.getHelpMessage(locale, action) %>" label="<%= label %>" name="<%= name %>" type="checkbox" value="<%= MapUtil.getBoolean(parameterMap, name, portletDataHandlerBoolean.getDefaultState()) || MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA_ALL) %>" />

				<c:if test="<%= portletDataHandlerBoolean.getChildrenPortletDataHandlerControls() != null %>">
					<ul class="list-unstyled" id="<portlet:namespace /><%= name %>Controls">

						<%
						request.setAttribute("render_controls.jsp-childControl", true);
						request.setAttribute("render_controls.jsp-controls", portletDataHandlerBoolean.getChildrenPortletDataHandlerControls());
						%>

						<liferay-util:include page="/render_controls.jsp" servletContext="<%= application %>" />
					</ul>

					<aui:script>
						Liferay.Util.toggleBoxes(
							'<portlet:namespace /><%= name %>',
							'<portlet:namespace /><%= name %>Controls',
							false,
							true
						);
					</aui:script>
				</c:if>
			</c:when>
			<c:when test="<%= portletDataHandlerControls[i] instanceof PortletDataHandlerChoice %>">
				<label>
					<liferay-ui:message key="<%= portletDataHandlerControls[i].getLabel() %>" />

					<%
					PortletDataHandlerChoice portletDataHandlerChoice = (PortletDataHandlerChoice)portletDataHandlerControls[i];

					String[] choices = portletDataHandlerChoice.getChoices();

					for (String choice : choices) {
						String name = LanguageUtil.get(request, resourceBundle, choice);
						String value = MapUtil.getString(parameterMap, portletDataHandlerChoice.getNamespacedName(), choices[portletDataHandlerChoice.getDefaultChoiceIndex()]);
					%>

						<aui:input
							checked="<%= value.equals(choice) %>"
							data='<%=
								HashMapBuilder.<String, Object>put(
									"name", name
								).build()
							%>'
							disabled="<%= disableInputs %>"
							helpMessage="<%= portletDataHandlerChoice.getHelpMessage(locale, action) %>"
							label="<%= name %>"
							name="<%= portletDataHandlerChoice.getNamespacedName() %>"
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