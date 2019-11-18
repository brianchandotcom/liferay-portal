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

if (Validator.isNull(redirect)) {
	PortletURL redirectURL = renderResponse.createRenderURL();

	redirectURL.setParameter("mvcPath", "/admin/view.jsp");

	redirect = redirectURL.toString();
}

KaleoProcess kaleoProcess = (KaleoProcess)request.getAttribute(KaleoFormsWebKeys.KALEO_PROCESS);

long kaleoProcessId = BeanParamUtil.getLong(kaleoProcess, request, "kaleoProcessId");

long groupId = BeanParamUtil.getLong(kaleoProcess, request, "groupId", scopeGroupId);

String kaleoProcessName = KaleoFormsUtil.getKaleoProcessName(kaleoProcess, portletSession, locale);

boolean kaleoProcessStarted = false;

if (kaleoProcess != null) {
	kaleoProcessStarted = (DDLRecordLocalServiceUtil.getRecordsCount(kaleoProcess.getDDLRecordSetId(), WorkflowConstants.STATUS_ANY) > 0);
}

String historyKey = ParamUtil.getString(request, "historyKey");

portletDisplay.setShowBackIcon(true);

PortletURL backPortletURL = renderResponse.createRenderURL();

backPortletURL.setParameter("mvcPath", "/admin/view.jsp");

portletDisplay.setURLBack(backPortletURL.toString());

String title = null;

if (Validator.isNull(kaleoProcessName)) {
	title = LanguageUtil.get(request, "new-process");
}
else {
	title = kaleoProcessName;
}

renderResponse.setTitle(title);
%>

<c:if test="<%= kaleoProcessStarted %>">
	<div class="alert alert-info container-fluid-1280">
		<liferay-ui:message key="updating-the-field-set-or-workflow-will-cause-loss-of-data" />
	</div>
</c:if>

<portlet:actionURL name="updateKaleoProcess" var="editKaleoProcessURL">
	<portlet:param name="mvcPath" value="/admin/edit_kaleo_process.jsp" />
	<portlet:param name="redirect" value="<%= backPortletURL.toString() %>" />
</portlet:actionURL>

<aui:form action="<%= editKaleoProcessURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="kaleoProcessId" type="hidden" value="<%= kaleoProcessId %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="scope" type="hidden" value="1" />

	<liferay-ui:error exception="<%= KaleoProcessDDMTemplateIdException.class %>" message="please-enter-a-valid-initial-form" />
	<liferay-ui:error exception="<%= RequiredStructureException.class %>" message="unable-to-delete-field-set-with-forms-associated-with-it" />
	<liferay-ui:error exception="<%= RequiredWorkflowDefinitionException.class %>" message="unable-to-delete-the-workflow-definition-that-is-in-use" />

	<liferay-util:buffer
		var="htmlBottom"
	>
		<aui:button-row cssClass="kaleo-process-buttons">
			<aui:button cssClass='<%= (historyKey.equals("forms") ? StringPool.BLANK : "hide") + " kaleo-process-previous pull-left" %>' icon="icon-circle-arrow-left" value="previous" />

			<aui:button cssClass='<%= (historyKey.equals("forms") ? StringPool.BLANK : "hide") + " kaleo-process-submit pull-right" %>' disabled="<%= true %>" primary="<%= true %>" type="submit" />

			<aui:button cssClass='<%= (historyKey.equals("forms") ? "hide" : StringPool.BLANK) + " kaleo-process-next pull-right" %>' disabled="<%= true %>" icon="icon-circle-arrow-right" iconAlign="right" primary="<%= true %>" value="next" />

			<aui:button cssClass="kaleo-process-cancel pull-right" href="<%= redirect %>" value="cancel" />
		</aui:button-row>
	</liferay-util:buffer>

	<liferay-ui:form-navigator
		displayStyle="steps"
		formName="fm"
		htmlBottom="<%= htmlBottom %>"
		id="kaleo.form"
		markupView="lexicon"
		showButtons="<%= false %>"
	/>

	<aui:script use="liferay-component,liferay-form,liferay-kaleo-forms-admin">
		var afterFormRegistered = function(event) {
			var form = Liferay.Form.get('<portlet:namespace />fm');

			if (form === event.form) {
				Liferay.component('<portlet:namespace/>KaleoFormsAdmin', function() {
					return new Liferay.KaleoFormsAdmin({
						currentURL: '<%= currentURL %>',
						form: form,
						kaleoProcessId: <%= kaleoProcessId %>,
						namespace: '<portlet:namespace />',
						portletId: '<%= PortalUtil.getPortletId(request) %>',
						saveInPortletSessionURL:
							'<portlet:resourceURL id="saveInPortletSession" />',
						tabView: Liferay.component('<portlet:namespace />fmTabview')
					});
				});

				Liferay.component('<portlet:namespace/>KaleoFormsAdmin').syncUI();
			}
		};

		Liferay.after('form:registered', afterFormRegistered);

		var clearAfterFormRegistered = function(event) {
			Liferay.detach('form:registered', afterFormRegistered);
		};

		Liferay.on('destroyPortlet', clearAfterFormRegistered);
	</aui:script>
</aui:form>