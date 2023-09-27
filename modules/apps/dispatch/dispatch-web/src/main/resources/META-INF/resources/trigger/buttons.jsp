<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DispatchTrigger dispatchTrigger = (DispatchTrigger)row.getObject();

DispatchTriggerDisplayContext dispatchTriggerDisplayContext = (DispatchTriggerDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

DispatchTriggerMetadata dispatchTriggerMetadata = dispatchTriggerDisplayContext.getDispatchTriggerMetadata(dispatchTrigger.getDispatchTriggerId());

String runNowButton = "runNowButton" + row.getRowId();
%>

<span aria-hidden="true" class="hide icon-spinner icon-spin dispatch-check-row-icon-spinner<%= row.getRowId() %>"></span>

<c:if test="<%= DispatchTriggerPermission.contains(permissionChecker, dispatchTrigger, ActionKeys.UPDATE) && dispatchTriggerMetadata.isDispatchTaskExecutorReady() %>">
	<aui:button name="<%= runNowButton %>" value="run-now" />
</c:if>

<aui:script use="aui-io-request,aui-parse-content">
	A.one('#<portlet:namespace /><%= runNowButton %>').on('click', function (
		event
	) {
		var data = {
			<portlet:namespace /><%= Constants.CMD %>: 'runProcess',
			<portlet:namespace />dispatchTriggerId:
				'<%= dispatchTrigger.getDispatchTriggerId() %>',
		};

		this.attr('disabled', true);

		var iconSpinnerContainer = A.one(
			'<%= ".dispatch-check-row-icon-spinner" + row.getRowId() %>'
		);

		iconSpinnerContainer.removeClass('hide');

		A.io.request(
			'<liferay-portlet:actionURL name="/dispatch/edit_dispatch_trigger" portletName="<%= portletDisplay.getPortletName() %>" />',
			{
				data: data,
				on: {
					success: function (event, id, obj) {
						var response = JSON.parse(obj.response);

						if (response.success) {
							iconSpinnerContainer.addClass('hide');
						}
						else {
							A.one('#<portlet:namespace /><%= runNowButton %>').attr(
								'disabled',
								false
							);

							iconSpinnerContainer.addClass('hide');

							Liferay.Util.openToast({
								message:
									'<liferay-ui:message key="an-unexpected-error-occurred" />',
								title: '<liferay-ui:message key="danger" />',
								type: 'danger',
							});
						}
					},
				},
			}
		);
	});
</aui:script>