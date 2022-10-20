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
			cssClass="pt-0"
			fluid="<%= true %>"
			method="post"
			name="fm"
		>
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="portletId" type="hidden" value="<%= HtmlUtil.escapeJS(portletConfigurationCSSPortletDisplayContext.getPortletResource()) %>" />

			<liferay-frontend:edit-form-body>
				<liferay-frontend:form-navigator
					id="<%= PortletConfigurationCSSConstants.FORM_NAVIGATOR_ID %>"
					showButtons="<%= false %>"
				/>
			</liferay-frontend:edit-form-body>

			<liferay-frontend:edit-form-footer>
				<liferay-frontend:edit-form-buttons />
			</liferay-frontend:edit-form-footer>
		</liferay-frontend:edit-form>

		<aui:script require="frontend-js-web/liferay/delegate/delegate.es as delegateModule">
			var delegate = delegateModule.default;

			delegate(
				document.getElementById('<portlet:namespace />fm'),
				'change',
				'input[type=checkbox]',
				(event) => {
					var toggle = event.delegateTarget;

					var disableOnChecked =
						toggle.dataset.disableonchecked === undefined ||
						toggle.dataset.disableonchecked === 'true';
					var inputs = document.querySelectorAll(toggle.dataset.inputselector);

					for (var i = 0; i < inputs.length; i++) {
						var input = inputs[i];

						input.disabled = disableOnChecked
							? toggle.checked
							: !toggle.checked;

						if (!input.disabled) {
							input.classList.remove('disabled');

							if (input.labels.length > 0) {
								input.labels[0].classList.remove('disabled');
							}
						}
						else {
							if (input.labels.length > 0) {
								input.labels[0].classList.add('disabled');
							}
						}
					}
				}
			);
		</aui:script>
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/html/portal/portlet_access_denied.jsp" />
	</c:otherwise>
</c:choose>