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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CommerceHealthStatus commerceHealthStatus = (CommerceHealthStatus)row.getObject();

String fixIssueButton = "fixIssueButton" + row.getRowId();
%>

<aui:button disabled="<%= commerceHealthStatus.isFixed(themeDisplay.getScopeGroupId()) %>" name="<%= fixIssueButton %>" value="fix-issue" />

<aui:script use="aui-io-request,aui-parse-content,liferay-notification">
	A.one('#<portlet:namespace /><%= fixIssueButton %>').on(
		'click',
		function(event) {
			var data = {
				'<%= PortalUtil.getPortletNamespace(CommerceAdminPortletKeys.COMMERCE_ADMIN) %>key': '<%= commerceHealthStatus.getKey() %>'
			};

			this.attr('disabled', true);

			var iconCheckContainer = A.one('<%= ".commerce-health-status-check-row-icon-check" + row.getRowId() %>');
			var iconSpinnerContainer = A.one('<%= ".commerce-health-status-check-row-icon-spinner" + row.getRowId() %>');
			var iconTimesContainer = A.one('<%= ".commerce-health-status-check-row-icon-times" + row.getRowId() %>');

			iconCheckContainer.addClass('hide');
			iconSpinnerContainer.removeClass('hide');
			iconTimesContainer.addClass('hide');

			A.io.request(
				'<liferay-portlet:actionURL name="fixCommerceHealthStatusIssue" portletName="<%= CommerceAdminPortletKeys.COMMERCE_ADMIN %>" />',
				{
					data: data,
					on: {
						success: function(event, id, obj) {
							var response = JSON.parse(obj.response);

							if (response.success) {
								iconCheckContainer.removeClass('hide');
								iconSpinnerContainer.addClass('hide');
								iconTimesContainer.addClass('hide');
							}
							else {
								A.one('#<portlet:namespace /><%= fixIssueButton %>').attr('disabled', false);

								iconCheckContainer.addClass('hide');
								iconSpinnerContainer.addClass('hide');
								iconTimesContainer.removeClass('hide');

								new Liferay.Notification(
									{
										message: '<liferay-ui:message key="an-unexpected-error-occurred" />',
										render: true,
										title: '<liferay-ui:message key="danger" />',
										type: 'danger'
									}
								);
							}
						}
					}
				}
			);
		}
	);
</aui:script>