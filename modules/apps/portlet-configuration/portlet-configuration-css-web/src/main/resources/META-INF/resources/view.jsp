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

<%@ include file="/init.jsp" %>

<c:choose>
	<c:when test="<%= portletConfigurationCSSPortletDisplayContext.hasAccess() %>">
		<portlet:actionURL name="updateLookAndFeel" var="updateLookAndFeelURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</portlet:actionURL>

		<liferay-frontend:edit-form
			action="<%= updateLookAndFeelURL %>"
			method="post"
			name="fm"
		>
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="portletId" type="hidden" value="<%= portletConfigurationCSSPortletDisplayContext.getPortletResource() %>" />

			<liferay-frontend:edit-form-body>
				<liferay-frontend:form-navigator
					id="<%= PortletConfigurationCSSConstants.FORM_NAVIGATOR_ID %>"
					showButtons="<%= false %>"
				/>
			</liferay-frontend:edit-form-body>

			<liferay-frontend:edit-form-footer>
				<aui:button type="submit" />

				<aui:button type="cancel" />
			</liferay-frontend:edit-form-footer>
		</liferay-frontend:edit-form>

		<aui:script require="metal-dom/src/dom as dom">
			dom.delegate(
				document.getElementById('<portlet:namespace />fm'),
				'change',
				'input[type=checkbox]',
				function(event) {
					var toggle = event.delegateTarget;

					var disableOnChecked = toggle.dataset.disableonchecked;
					var inputs = document.querySelectorAll(toggle.dataset.inputselector);

					for (var i = 0; i < inputs.length; i++) {
						var input = inputs[i];

						input.disabled = disableOnChecked
							? !toggle.checked
							: toggle.checked;
					}
				}
			);
		</aui:script>
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/html/portal/portlet_access_denied.jsp" />
	</c:otherwise>
</c:choose>