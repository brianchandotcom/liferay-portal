<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceDataIntegrationProcessLogDisplayContext commerceDataIntegrationProcessLogDisplayContext = (CommerceDataIntegrationProcessLogDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog = commerceDataIntegrationProcessLogDisplayContext.getCommerceDataIntegrationProcessLog();

long timeMillis = commerceDataIntegrationProcessLog.getEndDate().getTime() - commerceDataIntegrationProcessLog.getStartDate().getTime();

String startDate = commerceDataIntegrationProcessLogDisplayContext.getFormattedDate(commerceDataIntegrationProcessLog.getStartDate());
%>

<portlet:actionURL name="editCommerceDataIntegrationProcessLog" var="editCommerceDataIntegrationProcessLogActionURL" />

<div class="container-fluid-1280 sheet">
	<aui:form action="<%= editCommerceDataIntegrationProcessLogActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="cDataIntegrationProcessLogId" type="hidden" value="<%= String.valueOf(commerceDataIntegrationProcessLog.getCommerceDataIntegrationProcessLogId()) %>" />

		<div class="lfr-form-content">
			<aui:fieldset>
				<aui:input disabled="<%= true %>" label="start-date" name="startDate" value="<%= startDate %>" />

				<aui:input disabled="<%= true %>" name="status" value="<%= LanguageUtil.get(request, BackgroundTaskConstants.getStatusLabel(commerceDataIntegrationProcessLog.getStatus())) %>" />

				<aui:input disabled="<%= true %>" label="runtime" name="runTime" value='<%= timeMillis + " ms" %>' />

				<aui:input disabled="<%= true %>" label="error" name="error" type="textarea" value="<%= commerceDataIntegrationProcessLog.getError() %>" />

				<aui:input disabled="<%= true %>" label="output" name="output" type="textarea" value="<%= commerceDataIntegrationProcessLog.getOutput() %>" />
			</aui:fieldset>

			<aui:button-row>
				<aui:button cssClass="btn-lg" href="<%= backURL %>" type="cancel" />
			</aui:button-row>
		</div>
	</aui:form>
</div>