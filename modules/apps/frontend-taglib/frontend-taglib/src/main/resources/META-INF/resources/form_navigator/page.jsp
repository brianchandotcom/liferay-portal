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

<%@ include file="/form_navigator/init.jsp" %>

<%
FormNavigatorDisplayContext formNavigatorDisplayContext = new FormNavigatorDisplayContext(request);

String[] categoryKeys = formNavigatorDisplayContext.getCategoryKeys();
%>

<c:choose>
	<c:when test="<%= categoryKeys.length > 1 %>">
		<liferay-ui:tabs
			names="<%= StringUtil.merge(categoryKeys, StringPool.COMMA) %>"
			param="<%= formNavigatorDisplayContext.getTabs1Param() %>"
			refresh="<%= false %>"
			value="<%= GetterUtil.getString(SessionClicks.get(request, namespace + formNavigatorDisplayContext.getId(), null)) %>"
		>

			<%
			for (String categoryKey : categoryKeys) {
				request.setAttribute(FormNavigatorWebKeys.CURRENT_TAB, categoryKey);
				request.setAttribute(FormNavigatorWebKeys.FORM_NAVIGATOR_ENTRIES, formNavigatorDisplayContext.getFormNavigatorEntries(categoryKey));
			%>

				<liferay-ui:section>
					<liferay-util:include page="/form_navigator/sections.jsp" servletContext="<%= application %>" />
				</liferay-ui:section>

			<%
			}

			String errorTab = (String)request.getAttribute(FormNavigatorWebKeys.ERROR_TAB);

			if (Validator.isNotNull(errorTab)) {
				request.setAttribute(WebKeys.ERROR_SECTION, errorTab);
			}
			%>

		</liferay-ui:tabs>
	</c:when>
	<c:otherwise>

		<%
		request.setAttribute(FormNavigatorWebKeys.FORM_NAVIGATOR_ENTRIES, formNavigatorDisplayContext.getFormNavigatorEntries());
		%>

		<liferay-util:include page="/form_navigator/sections.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>

<c:if test="<%= formNavigatorDisplayContext.isShowButtons() %>">
	<div>
		<aui:button primary="<%= true %>" type="submit" />

		<aui:button href="<%= formNavigatorDisplayContext.getBackURL() %>" type="cancel" />
	</div>
</c:if>

<aui:script sandbox="<%= true %>">
	var redirectField = document.querySelector(
		'input[name="<portlet:namespace />redirect"]'
	);

	var tabs1Param = '<%= formNavigatorDisplayContext.getTabs1Param() %>';

	var updateRedirectField = function (event) {
		var redirectURL = new URL(redirectField.value, window.location.origin);

		redirectURL.searchParams.set(tabs1Param, event.id);

		redirectField.value = redirectURL.toString();

		Liferay.Util.Session.set(
			'<portlet:namespace /><%= formNavigatorDisplayContext.getId() %>',
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