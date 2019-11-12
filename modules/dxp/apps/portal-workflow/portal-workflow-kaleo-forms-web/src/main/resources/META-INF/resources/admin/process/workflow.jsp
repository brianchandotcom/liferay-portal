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
String tabs1 = ParamUtil.getString(request, "tabs1", "published");

String redirect = ParamUtil.getString(request, "redirect");
String backURL = HttpUtil.setParameter(currentURL, renderResponse.getNamespace() + "historyKey", "workflow");

KaleoProcess kaleoProcess = (KaleoProcess)request.getAttribute(KaleoFormsWebKeys.KALEO_PROCESS);

long kaleoProcessId = BeanParamUtil.getLong(kaleoProcess, request, "kaleoProcessId");

String workflowDefinition = KaleoFormsUtil.getWorkflowDefinition(kaleoProcess, portletSession);

String workflowDefinitionName = StringPool.BLANK;
int workflowDefinitionVersion = 0;

if (Validator.isNotNull(workflowDefinition)) {
	String[] workflowDefinitionParts = StringUtil.split(workflowDefinition, CharPool.AT);

	workflowDefinitionName = workflowDefinitionParts[0];
	workflowDefinitionVersion = GetterUtil.getInteger(workflowDefinitionParts[1]);

	if (!KaleoFormsUtil.isWorkflowDefinitionActive(themeDisplay.getCompanyId(), workflowDefinitionName, workflowDefinitionVersion)) {
		workflowDefinition = StringPool.BLANK;
	}
}

int status = WorkflowConstants.STATUS_DRAFT;

if (tabs1.equals("published")) {
	status = WorkflowConstants.STATUS_APPROVED;
}
%>

<h3 class="kaleo-process-header"><liferay-ui:message key="workflow" /></h3>

<p class="kaleo-process-message"><liferay-ui:message key="please-select-or-create-a-new-workflow-definition-to-guide-the-completion-of-forms.-you-can-associate-forms-to-workflow-tasks-in-the-next-step" /></p>

<aui:field-wrapper>
	<liferay-ui:message key="selected-workflow" />:

	<%
	String workflowDefinitionDisplay = StringPool.BLANK;

	if (Validator.isNotNull(workflowDefinitionName)) {
		WorkflowDefinition kaleoWorkflowDefinition = KaleoFormsUtil.getWorkflowDefinition(themeDisplay.getCompanyId(), workflowDefinitionName, workflowDefinitionVersion);

		if (kaleoWorkflowDefinition != null) {
			workflowDefinitionDisplay = kaleoWorkflowDefinition.getTitle(themeDisplay.getLanguageId());
		}
	}
	%>

	<span class="badge badge-info" id="<portlet:namespace />workflowDefinitionDisplay"><%= HtmlUtil.escape(workflowDefinitionDisplay) %></span>

	<aui:input name="workflowDefinition" type="hidden" value="<%= workflowDefinition %>">
		<aui:validator name="required" />
	</aui:input>

	<aui:input name="workflowDefinitionName" type="hidden" value="<%= workflowDefinitionName %>" />
	<aui:input name="workflowDefinitionVersion" type="hidden" value="<%= workflowDefinitionVersion %>" />
</aui:field-wrapper>

<liferay-portlet:renderURL varImpl="iteratorURL">
	<portlet:param name="mvcPath" value="/admin/edit_kaleo_process.jsp" />
	<portlet:param name="tabs1" value="<%= tabs1 %>" />
	<portlet:param name="redirect" value="<%= redirect %>" />
	<portlet:param name="historyKey" value="workflow" />
	<portlet:param name="kaleoProcessId" value="<%= String.valueOf(kaleoProcessId) %>" />
</liferay-portlet:renderURL>

<liferay-ui:search-container
	emptyResultsMessage='<%= tabs1.equals("published") ? "there-are-no-published-definitions" : "there-are-no-unpublished-definitions" %>'
	iteratorURL="<%= iteratorURL %>"
>
	<liferay-portlet:renderURL portletName="<%= KaleoDesignerPortletKeys.KALEO_DESIGNER %>" var="addURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="mvcPath" value="/designer/edit_kaleo_definition_version.jsp" />
		<portlet:param name="closeRedirect" value="<%= backURL %>" />
	</liferay-portlet:renderURL>

	<aui:button onClick='<%= "javascript:" + renderResponse.getNamespace() + "editWorkflow('" + addURL + "');" %>' primary="<%= true %>" value="add-workflow" />

	<div class="separator"><!-- --></div>

	<liferay-portlet:renderURL varImpl="portletURL">
		<portlet:param name="mvcPath" value="/admin/edit_kaleo_process.jsp" />
		<portlet:param name="tabs1" value="<%= tabs1 %>" />
		<portlet:param name="redirect" value="<%= redirect %>" />
		<portlet:param name="historyKey" value="workflow" />
		<portlet:param name="kaleoProcessId" value="<%= String.valueOf(kaleoProcessId) %>" />
	</liferay-portlet:renderURL>

	<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
		<aui:nav cssClass="kaleo-process-workflow-nav-tabs nav-bar-workflow nav-tabs nav-tabs-default">
			<liferay-portlet:renderURL var="viewPublishedURL">
				<portlet:param name="mvcPath" value="/admin/edit_kaleo_process.jsp" />
				<portlet:param name="tabs1" value="published" />
				<portlet:param name="redirect" value="<%= redirect %>" />
				<portlet:param name="historyKey" value="workflow" />
				<portlet:param name="kaleoProcessId" value="<%= String.valueOf(kaleoProcessId) %>" />
			</liferay-portlet:renderURL>

			<aui:nav-item href="<%= viewPublishedURL %>" label="published" selected='<%= tabs1.equals("published") %>' />

			<liferay-portlet:renderURL var="viewUnpublishedURL">
				<portlet:param name="mvcPath" value="/admin/edit_kaleo_process.jsp" />
				<portlet:param name="tabs1" value="unpublished" />
				<portlet:param name="redirect" value="<%= redirect %>" />
				<portlet:param name="historyKey" value="workflow" />
				<portlet:param name="kaleoProcessId" value="<%= String.valueOf(kaleoProcessId) %>" />
			</liferay-portlet:renderURL>

			<aui:nav-item href="<%= viewUnpublishedURL %>" label="unpublished" selected='<%= tabs1.equals("unpublished") %>' />
		</aui:nav>
	</aui:nav-bar>

	<c:choose>
		<c:when test='<%= tabs1.equals("published") %>'>

			<%
			searchContainer.setTotal(WorkflowDefinitionManagerUtil.getActiveWorkflowDefinitionsCount(company.getCompanyId()));
			%>

			<liferay-ui:search-container-results
				results="<%= WorkflowDefinitionManagerUtil.getActiveWorkflowDefinitions(company.getCompanyId(), searchContainer.getStart(), searchContainer.getEnd(), null) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.kernel.workflow.WorkflowDefinition"
				modelVar="workflowDefinitionVar"
			>
				<liferay-ui:search-container-row-parameter
					name="backURL"
					value="<%= backURL %>"
				/>

				<c:if test="<%= kaleoProcess != null %>">
					<liferay-ui:search-container-row-parameter
						name="kaleoProcessId"
						value="<%= String.valueOf(kaleoProcess.getKaleoProcessId()) %>"
					/>
				</c:if>

				<liferay-ui:search-container-column-text
					name="title"
					value="<%= HtmlUtil.escape(workflowDefinitionVar.getTitle(themeDisplay.getLanguageId())) %>"
				/>

				<liferay-ui:search-container-column-text
					name="version"
					value="<%= String.valueOf(workflowDefinitionVar.getVersion()) %>"
				/>

				<liferay-ui:search-container-column-jsp
					align="right"
					cssClass="entry-action"
					path="/admin/process/workflow_action.jsp"
				/>
			</liferay-ui:search-container-row>
		</c:when>
		<c:otherwise>
			<liferay-ui:search-container-results
				results="<%= kaleoFormsAdminDisplayContext.getSearchContainerResults(searchContainer, status) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion"
				keyProperty="kaleoDefinitionVersionId"
				modelVar="kaleoDefinitionVersion"
			>
				<liferay-ui:search-container-row-parameter
					name="backURL"
					value="<%= backURL %>"
				/>

				<c:if test="<%= kaleoProcess != null %>">
					<liferay-ui:search-container-row-parameter
						name="kaleoProcessId"
						value="<%= String.valueOf(kaleoProcess.getKaleoProcessId()) %>"
					/>
				</c:if>

				<liferay-ui:search-container-row-parameter
					name="name"
					value="<%= kaleoDefinitionVersion.getName() %>"
				/>

				<liferay-ui:search-container-column-text
					name="title"
					value="<%= HtmlUtil.escape(kaleoDefinitionVersion.getTitle(themeDisplay.getLanguageId())) %>"
				/>

				<liferay-ui:search-container-row-parameter
					name="version"
					value="<%= kaleoDefinitionVersion.getVersion() %>"
				/>

				<liferay-ui:search-container-column-jsp
					align="right"
					cssClass="entry-action"
					path="/admin/process/kaleo_draft_definition_action.jsp"
				/>
			</liferay-ui:search-container-row>
		</c:otherwise>
	</c:choose>

	<liferay-ui:search-iterator
		markupView="lexicon"
	/>
</liferay-ui:search-container>

<aui:script>
	Liferay.on(
		'<portlet:namespace />chooseWorkflow',
		function(event) {
			var A = AUI();

			var workflowDefinition = event.name + '@' + event.version;

			A.one('#<portlet:namespace />workflowDefinition').val(
				workflowDefinition
			);

			A.one('#<portlet:namespace />workflowDefinitionDisplay').html(
				A.Lang.sub('{title}', {
					title: Liferay.Util.escapeHTML(event.title)
				})
			);

			var kaleoFormsAdmin = Liferay.component(
				'<portlet:namespace/>KaleoFormsAdmin'
			);

			kaleoFormsAdmin.saveInPortletSession({
				workflowDefinition: workflowDefinition
			});

			kaleoFormsAdmin.updateNavigationControls();
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />editWorkflow',
		function(uri) {
			var A = AUI();

			var WIN = A.config.win;

			Liferay.Util.openWindow({
				id: A.guid(),
				refreshWindow: WIN,
				title: '<liferay-ui:message key="workflow" />',
				uri: uri
			});
		},
		['liferay-util']
	);
</aui:script>