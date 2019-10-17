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
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_ui_form_navigator_init") + StringPool.UNDERLINE;

String tabs1Param = randomNamespace + "tabs1";
String tabs1Value = GetterUtil.getString(SessionClicks.get(request, namespace + id, null));
%>

<c:choose>
	<c:when test="<%= categoryKeys.length > 1 %>">
		<liferay-ui:tabs
			names="<%= StringUtil.merge(categoryKeys, StringPool.COMMA) %>"
			param="<%= tabs1Param %>"
			refresh="<%= false %>"
			value="<%= tabs1Value %>"
		>

			<%
			for (String categoryKey : categoryKeys) {
				request.setAttribute(FormNavigatorWebKeys.CURRENT_TAB, categoryKey);
				request.setAttribute(FormNavigatorWebKeys.FORM_NAVIGATOR_ENTRIES, FormNavigatorEntryUtil.getFormNavigatorEntries(id, categoryKey, user, formModelBean));
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
		request.setAttribute(FormNavigatorWebKeys.FORM_NAVIGATOR_ENTRIES, FormNavigatorEntryUtil.getFormNavigatorEntries(id, user, formModelBean));
		%>

		<liferay-util:include page="/form_navigator/sections.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>

<c:if test="<%= showButtons %>">
	<div>
		<aui:button primary="<%= true %>" type="submit" />

		<aui:button href="<%= backURL %>" type="cancel" />
	</div>
</c:if>

<aui:script require="metal-dom/src/dom as dom">
	var redirectField = dom.toElement(
		'input[name="<portlet:namespace />redirect"]'
	);
	var tabs1Param = '<portlet:namespace /><%= tabs1Param %>';

	var updateRedirectField = function(event) {
		var redirectURL = new URL(redirectField.value, window.location.origin);

		redirectURL.searchParams.set(tabs1Param, event.id);

		redirectField.value = redirectURL.toString();

		Liferay.Util.Session.set('<portlet:namespace /><%= id %>', event.id);
	};

	var clearFormNavigatorHandles = function(event) {
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
				id: tabs1Value
			});
		}

		Liferay.on('showTab', updateRedirectField);
		Liferay.on('destroyPortlet', clearFormNavigatorHandles);
	}
</aui:script>