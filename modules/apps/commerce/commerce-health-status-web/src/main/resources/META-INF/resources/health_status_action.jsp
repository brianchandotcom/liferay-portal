<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceHealthStatusDisplayContext commerceHealthStatusDisplayContext = (CommerceHealthStatusDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CommerceHealthStatus commerceHealthStatus = (CommerceHealthStatus)row.getObject();

String fixIssueButton = "fixIssueButton" + row.getRowId();
%>

<c:if test="<%= commerceHealthStatusDisplayContext.hasManageCommerceHealthStatusPermission() %>">
	<aui:button disabled="<%= commerceHealthStatus.isFixed(company.getCompanyId(), themeDisplay.getScopeGroupId()) %>" name="<%= fixIssueButton %>" value="fix-issue" />

	<aui:script use="aui-io-request,aui-parse-content">
		A.one('#<portlet:namespace /><%= fixIssueButton %>').on('click', function (
			event
		) {
			var data = {
				<portlet:namespace />key:
					'<%= HtmlUtil.escapeJS(commerceHealthStatus.getKey()) %>',
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
</c:if>