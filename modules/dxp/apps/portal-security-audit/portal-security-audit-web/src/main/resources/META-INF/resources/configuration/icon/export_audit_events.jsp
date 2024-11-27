<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/audit/export_audit_events" var="exportURL" />

<aui:form action='<%= exportURL + "&compress=0&etag=0&strip=0" %>' method="post" name="exportAuditEventsFm">
	<aui:input name="advancedSearch" type="hidden" value='<%= ParamUtil.getBoolean(request, portletDisplay.getNamespace() + "advancedSearch") %>' />
	<aui:input name="andOperator" type="hidden" value='<%= ParamUtil.getBoolean(request, portletDisplay.getNamespace() + "andOperator") %>' />
	<aui:input name="className" type="hidden" value="<%= auditDisplayContext.getClassName() %>" />
	<aui:input name="classPK" type="hidden" value="<%= auditDisplayContext.getClassPK() %>" />
	<aui:input name="clientHost" type="hidden" value="<%= auditDisplayContext.getClientHost() %>" />
	<aui:input name="clientIP" type="hidden" value="<%= auditDisplayContext.getClientIP() %>" />
	<aui:input name="endDateAmPm" type="hidden" value="<%= auditDisplayContext.getEndDateAmPm() %>" />
	<aui:input name="endDateDay" type="hidden" value="<%= auditDisplayContext.getEndDateDay() %>" />
	<aui:input name="endDateHour" type="hidden" value="<%= auditDisplayContext.getEndDateHour() %>" />
	<aui:input name="endDateMinute" type="hidden" value="<%= auditDisplayContext.getEndDateMinute() %>" />
	<aui:input name="endDateMonth" type="hidden" value="<%= auditDisplayContext.getEndDateMonth() %>" />
	<aui:input name="endDateYear" type="hidden" value="<%= auditDisplayContext.getEndDateYear() %>" />
	<aui:input name="eventType" type="hidden" value="<%= auditDisplayContext.getEventType() %>" />
	<aui:input name="groupId" type="hidden" value="<%= auditDisplayContext.getGroupId() %>" />
	<aui:input name="keywords" type="hidden" value='<%= ParamUtil.getString(request, portletDisplay.getNamespace() + "keywords") %>' />
	<aui:input name="serverName" type="hidden" value="<%= auditDisplayContext.getServerName() %>" />
	<aui:input name="serverPort" type="hidden" value="<%= auditDisplayContext.getServerPort() %>" />
	<aui:input name="startDateAmPm" type="hidden" value="<%= auditDisplayContext.getStartDateAmPm() %>" />
	<aui:input name="startDateDay" type="hidden" value="<%= auditDisplayContext.getStartDateDay() %>" />
	<aui:input name="startDateHour" type="hidden" value="<%= auditDisplayContext.getStartDateHour() %>" />
	<aui:input name="startDateMinute" type="hidden" value="<%= auditDisplayContext.getStartDateMinute() %>" />
	<aui:input name="startDateMonth" type="hidden" value="<%= auditDisplayContext.getStartDateMonth() %>" />
	<aui:input name="startDateYear" type="hidden" value="<%= auditDisplayContext.getStartDateYear() %>" />
	<aui:input name="userId" type="hidden" value="<%= auditDisplayContext.getUserId() %>" />
	<aui:input name="userName" type="hidden" value="<%= auditDisplayContext.getUserName() %>" />
</aui:form>

<aui:script>
	Liferay.Util.setPortletConfigurationIconAction(
		'<portlet:namespace />exportAuditEvents',
		() => {
			Liferay.Util.openConfirmModal({
				message:
					'<liferay-ui:message key="warning-this-csv-file-contains-user-supplied-inputs" unicode="<%= true %>" />',
				onConfirm: (isConfirmed) => {
					if (isConfirmed) {
						Liferay.Util.postForm(
							document.<portlet:namespace />exportAuditEventsFm
						);
					}
				},
			});
		}
	);
</aui:script>