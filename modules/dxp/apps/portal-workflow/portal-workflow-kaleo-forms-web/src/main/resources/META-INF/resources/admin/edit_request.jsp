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

<%@ include file="/admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");

KaleoProcess kaleoProcess = (KaleoProcess)request.getAttribute(KaleoFormsWebKeys.KALEO_PROCESS);

long classPK = ParamUtil.getLong(request, "classPK");

long kaleoProcessId = BeanParamUtil.getLong(kaleoProcess, request, "kaleoProcessId", classPK);

long groupId = BeanParamUtil.getLong(kaleoProcess, request, "groupId", scopeGroupId);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(LanguageUtil.format(request, "new-x", kaleoProcess.getName(locale), false));
%>

<div class="container-fluid-1280 sidenav-container sidenav-right">
	<portlet:actionURL name="startWorkflowInstance" var="startWorkflowInstanceURL" />

	<aui:form action="<%= startWorkflowInstanceURL %>" cssClass="lfr-dynamic-form" enctype="multipart/form-data" method="post" name="fm1">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
		<aui:input name="kaleoProcessId" type="hidden" value="<%= String.valueOf(kaleoProcessId) %>" />
		<aui:input name="ddlRecordSetId" type="hidden" value="<%= String.valueOf(kaleoProcess.getDDLRecordSetId()) %>" />
		<aui:input name="ddmTemplateId" type="hidden" value="<%= String.valueOf(kaleoProcess.getDDMTemplateId()) %>" />
		<aui:input name="defaultLanguageId" type="hidden" value="<%= themeDisplay.getLanguageId() %>" />
		<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_PUBLISH %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>

				<%
				DDMTemplate ddmTemplate = kaleoProcess.getDDMTemplate();
				%>

				<liferay-ddm:html
					classNameId="<%= PortalUtil.getClassNameId(DDMTemplate.class) %>"
					classPK="<%= ddmTemplate.getTemplateId() %>"
					requestedLocale="<%= locale %>"
				/>
			</aui:fieldset>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button cssClass="btn-lg" name="saveButton" type="submit" value="save" />

			<aui:button cssClass="btn-lg" href="<%= redirect %>" name="cancelButton" type="cancel" />
		</aui:button-row>
	</aui:form>
</div>

<%
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.format(request, "add-x", kaleoProcess.getName(locale), false), currentURL);
%>