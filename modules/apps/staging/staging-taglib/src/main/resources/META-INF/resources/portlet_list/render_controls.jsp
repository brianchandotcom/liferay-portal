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

	<c:choose>
		<c:when test="<%= portletDataHandlerControls[i] instanceof PortletDataHandlerBoolean %>">

			<%
			PortletDataHandlerBoolean portletDataHandlerBoolean = (PortletDataHandlerBoolean)portletDataHandlerControls[i];

			String className = portletDataHandlerBoolean.getClassName();
			String label = LanguageUtil.get(request, resourceBundle, portletDataHandlerBoolean.getLabel());

			long modelAdditionCount = 0;
			long modelDeletionCount = 0;

			if (Validator.isNotNull(className) && (manifestSummary != null)) {
				StagedModelType stagedModelType = new StagedModelType(className, portletDataHandlerBoolean.getReferrerClassName());

				modelAdditionCount = manifestSummary.getModelAdditionCount(stagedModelType);
				modelDeletionCount = manifestSummary.getModelDeletionCount(stagedModelType);

				if ((modelAdditionCount <= 0) && (modelDeletionCount <= 0) && !showAllPortlets) {
					continue control;
				}
			}

			boolean disabled = portletDataHandlerBoolean.isDisabled() || disableInputs;

			String name = Validator.isNotNull(portletDataHandlerBoolean.getNamespace()) ? portletDataHandlerBoolean.getNamespacedName() : (portletDataHandlerBoolean.getName() + StringPool.UNDERLINE + portletId);

			String inputName = disabled ? (name + "Display") : name;

			RenderControlsDisplayContext renderControlsDisplayContext = new RenderControlsDisplayContext(request);

			Map<String, Object> data = HashMapBuilder.<String, Object>put(
				"name", label
			).build();

			if (!childControl) {
				data.put("root-control-id", liferayPortletResponse.getNamespace() + PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + portletId);
			}
			%>

			<li class="handler-control <%= ((modelAdditionCount <= 0) && (modelDeletionCount > 0)) ? "deletions" : StringPool.BLANK %>">
				<c:if test="<%= disabled %>">
					<aui:input name="<%= name %>" type="hidden" value="<%= MapUtil.getBoolean(parameterMap, name, portletDataHandlerBoolean.getDefaultState()) || MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA_ALL) %>" />
				</c:if>

				<liferay-staging:checkbox
					checked="<%= renderControlsDisplayContext.isControlCheckboxEnabled(portletDataHandlerBoolean, parameterMap) %>"
					data="<%= data %>"
					deletions="<%= modelDeletionCount %>"
					disabled="<%= disabled %>"
					items="<%= modelAdditionCount %>"
					label="<%= label %>"
					name="<%= inputName %>"
					popover="<%= portletDataHandlerBoolean.getHelpMessage(locale, action) %>"
				/>

				<c:if test="<%= portletDataHandlerBoolean.getChildrenPortletDataHandlerControls() != null %>">
					<ul class="list-unstyled" id="<portlet:namespace /><%= name %>Controls">

						<%
						request.setAttribute("render_controls.jsp-childControl", true);
						request.setAttribute("render_controls.jsp-controls", portletDataHandlerBoolean.getChildrenPortletDataHandlerControls());
						%>

						<liferay-util:include page="/portlet_list/render_controls.jsp" servletContext="<%= application %>" />
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
			</li>
		</c:when>
		<c:when test="<%= portletDataHandlerControls[i] instanceof PortletDataHandlerChoice %>">
			<li class="handler-control">
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
							label="<%= choice %>"
							name="<%= portletDataHandlerChoice.getNamespacedName() %>"
							type="radio"
							value="<%= choice %>"
						/>

					<%
					}
					%>

				</label>
			</li>
		</c:when>
	</c:choose>

<%
}
%>