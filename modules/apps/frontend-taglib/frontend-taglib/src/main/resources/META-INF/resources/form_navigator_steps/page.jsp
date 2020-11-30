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

<%@ include file="/form_navigator_steps/init.jsp" %>

<%
FormNavigatorStepsDisplayContext formNavigatorStepsDisplayContext = new FormNavigatorStepsDisplayContext(request, liferayPortletResponse);

List<String> filterCategoryKeys = new ArrayList<>();
List<String> filterCategoryLabels = new ArrayList<>();

String[] categoryKeys = formNavigatorStepsDisplayContext.getCategoryKeys();
String[] categoryLabels = formNavigatorStepsDisplayContext.getCategoryLabels();
String[][] categorySectionKeys = formNavigatorStepsDisplayContext.getCategorySectionKeys();
String[][] categorySectionLabels = formNavigatorStepsDisplayContext.getCategorySectionLabels();

for (int i = 0; i < categoryKeys.length; i++) {
	String categoryKey = categoryKeys[i];

	List<FormNavigatorEntry<Object>> formNavigatorEntries = formNavigatorStepsDisplayContext.getFormNavigatorEntries(categoryKey);

	if (ListUtil.isNotEmpty(formNavigatorEntries)) {
		filterCategoryKeys.add(categoryKey);
		filterCategoryLabels.add(categoryLabels[i]);
	}
}

String curSection = formNavigatorStepsDisplayContext.getCurSection();
%>

<c:choose>
	<c:when test="<%= filterCategoryKeys.size() > 1 %>">
		<liferay-ui:tabs
			names="<%= StringUtil.merge(filterCategoryLabels) %>"
			param="<%= formNavigatorStepsDisplayContext.getTabs1Param() %>"
			refresh="<%= false %>"
			value="<%= formNavigatorStepsDisplayContext.getTabs1Value() %>"
		>

			<%
			for (String categoryKey : filterCategoryKeys) {
				List<FormNavigatorEntry<Object>> formNavigatorEntries = formNavigatorStepsDisplayContext.getFormNavigatorEntries(categoryKey);

				request.setAttribute("currentTab", categoryKey);
			%>

				<liferay-ui:section>
					<%@ include file="/form_navigator_steps/steps.jspf" %>
				</liferay-ui:section>

			<%
			}

			String errorTab = (String)request.getAttribute("errorTab");

			if (Validator.isNotNull(errorTab)) {
				request.setAttribute(WebKeys.ERROR_SECTION, errorTab);
			}
			%>

		</liferay-ui:tabs>
	</c:when>
	<c:otherwise>

		<%
		List<FormNavigatorEntry<Object>> formNavigatorEntries = formNavigatorStepsDisplayContext.getFormNavigatorEntries();
		%>

		<%@ include file="/form_navigator_steps/steps.jspf" %>
	</c:otherwise>
</c:choose>

<c:if test="<%= formNavigatorStepsDisplayContext.isShowButtons() %>">
	<aui:button-row>
		<aui:button primary="<%= true %>" type="submit" />

		<aui:button href="<%= formNavigatorStepsDisplayContext.getBackURL() %>" type="cancel" />
	</aui:button-row>
</c:if>

<aui:script sandbox="<%= true %>">
	var redirectField = document.querySelector(
		'input[name="<portlet:namespace />redirect"]'
	);
	var tabs1Param = '<%= formNavigatorStepsDisplayContext.getTabs1Param() %>';

	var updateRedirectField = function (event) {
		var redirectURL = new URL(redirectField.value, window.location.origin);

		redirectURL.searchParams.set(tabs1Param, event.id);

		redirectField.value = redirectURL.toString();

		Liferay.Util.Session.set(
			'<portlet:namespace /><%= formNavigatorStepsDisplayContext.getId() %>',
			event.id
		);
	};

	var clearFormNavigatorHandles = function (event) {
		if (event.portletId === '<%= portletDisplay.getRootPortletId() %>') {
			Liferay.detach('showTab', updateRedirectField);
			Liferay.detach('destroyPortlet', clearFormNavigatorHandles);
		}
	};

	if (redirectField) {
		var currentURL = new URL(document.location.href);

		var tabs1Value = currentURL.searchParams.get(tabs1Param);

		if (tabs1Value) {
			updateRedirectField({
				id: tabs1Value,
			});
		}

		Liferay.on('showTab', updateRedirectField);
		Liferay.on('destroyPortlet', clearFormNavigatorHandles);
	}
</aui:script>