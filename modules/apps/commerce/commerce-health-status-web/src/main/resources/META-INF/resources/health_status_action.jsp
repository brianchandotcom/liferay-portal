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

<%
CommerceHealthStatusDisplayContext commerceHealthStatusDisplayContext = (CommerceHealthStatusDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CommerceHealthHttpStatus commerceHealthHttpStatus = (CommerceHealthHttpStatus)row.getObject();

String fixIssueButton = "fixIssueButton" + row.getRowId();
%>

<c:if test="<%= commerceHealthStatusDisplayContext.hasManageCommerceHealthStatusPermission() %>">
	<aui:button disabled="<%= commerceHealthHttpStatus.isFixed(company.getCompanyId(), themeDisplay.getScopeGroupId()) %>" name="<%= fixIssueButton %>" value="fix-issue" />

	<aui:script use="aui-io-request,aui-parse-content,liferay-notification">
		A.one('#<portlet:namespace /><%= fixIssueButton %>').on('click', function (
			event
		) {
			var data = {
				<portlet:namespace />key:
					'<%= HtmlUtil.escapeJS(commerceHealthHttpStatus.getKey()) %>',
			};

			this.attr('disabled', true);

			var iconCheckContainer = A.one(
				'<%= ".commerce-health-status-check-row-icon-check" + row.getRowId() %>'
			);
			var iconSpinnerContainer = A.one(
				'<%= ".commerce-health-status-check-row-icon-spinner" + row.getRowId() %>'
			);
			var iconTimesContainer = A.one(
				'<%= ".commerce-health-status-check-row-icon-times" + row.getRowId() %>'
			);

			iconCheckContainer.addClass('hide');
			iconSpinnerContainer.removeClass('hide');
			iconTimesContainer.addClass('hide');

			A.io.request(
				'<liferay-portlet:actionURL name="/commerce_health_check/fix_commerce_health_status_issue" portletName="<%= portletDisplay.getPortletName() %>" />',
				{
					data: data,
					on: {
						success: function (event, id, obj) {
							var response = JSON.parse(obj.response);

							if (response.success) {
								iconCheckContainer.removeClass('hide');
								iconSpinnerContainer.addClass('hide');
								iconTimesContainer.addClass('hide');
							}
							else {
								A.one(
									'#<portlet:namespace /><%= fixIssueButton %>'
								).attr('disabled', false);

								iconCheckContainer.addClass('hide');
								iconSpinnerContainer.addClass('hide');
								iconTimesContainer.removeClass('hide');

								new Liferay.Notification({
									closeable: true,
									delay: {
										hide: 5000,
										show: 0,
									},
									duration: 500,
									message:
										'<liferay-ui:message key="an-unexpected-error-occurred" />',
									render: true,
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
</c:if>