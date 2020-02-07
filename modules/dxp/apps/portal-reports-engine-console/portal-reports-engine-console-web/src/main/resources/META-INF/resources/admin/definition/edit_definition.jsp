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

<portlet:renderURL var="viewDefinitionsURL">
	<portlet:param name="tabs1" value="definitions" />
</portlet:renderURL>

<%
String backURL = ParamUtil.getString(request, "backURL", viewDefinitionsURL);

Definition definition = (Definition)request.getAttribute(ReportsEngineWebKeys.DEFINITION);

long definitionId = BeanParamUtil.getLong(definition, request, "definitionId");

long sourceId = BeanParamUtil.getLong(definition, request, "sourceId");

String reportName = StringPool.BLANK;

if (definition != null) {
	reportName = definition.getReportName();
}

if (reportsEngineDisplayContext.isAdminPortlet()) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);

	renderResponse.setTitle((definition != null) ? LanguageUtil.format(request, "edit-x", definition.getName(locale), false) : LanguageUtil.get(request, "new-report-definition"));
}
else {
	portletDisplay.setShowBackIcon(false);

	renderResponse.setTitle(definition.getName(locale));
}
%>

<portlet:renderURL var="definitionsURL">
	<portlet:param name="tabs1" value="definitions" />
</portlet:renderURL>

<div class="report-message"></div>

<portlet:actionURL name="editDefinition" var="actionURL">
	<portlet:param name="mvcPath" value="/admin/definition/edit_definition.jsp" />
</portlet:actionURL>

<aui:form action="<%= actionURL %>" cssClass="container-fluid-1280" enctype="multipart/form-data" method="post" name="fm">
	<liferay-ui:error exception="<%= DefinitionFileException.InvalidDefinitionFile.class %>" message="please-enter-a-valid-file" />
	<liferay-ui:error exception="<%= DefinitionNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= definition %>" model="<%= Definition.class %>" />

	<aui:input name="redirect" type="hidden" value="<%= definitionsURL %>" />
	<aui:input name="viewDefinitionsURL" type="hidden" value="<%= viewDefinitionsURL %>" />
	<aui:input name="definitionId" type="hidden" />

	<c:if test="<%= definition != null %>">
		<liferay-frontend:info-bar>
			<span class="text-muted">
				<span class="definition-id-label"><liferay-ui:message key="id" />:</span>

				<span class="definition-id-value"><%= definition.getDefinitionId() %></span>
			</span>
		</liferay-frontend:info-bar>
	</c:if>

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:input label="definition-name" name="name" />

			<aui:input name="description" />

			<aui:select label="data-source-name" name="sourceId">
				<aui:option label="<%= ReportDataSourceType.PORTAL.getValue() %>" selected="<%= sourceId == 0 %>" value="<%= 0 %>" />

				<%
				List<Source> sources = SourceServiceUtil.getSources(themeDisplay.getSiteGroupId(), null, null, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

				for (Source source : sources) {
				%>

					<aui:option label="<%= HtmlUtil.escape(source.getName(locale)) %>" selected="<%= sourceId == source.getSourceId() %>" value="<%= source.getSourceId() %>" />

				<%
				}
				%>

			</aui:select>

			<aui:field-wrapper>
				<aui:input cssClass="template-report" name="templateReport" style='<%= Validator.isNull(reportName) ? "display: block;" : "display: none;" %>' type="file" />

				<span class="existing-report" style="<%= Validator.isNull(reportName) ? "display: none;" : "display: block;" %>">
					<%= HtmlUtil.escape(reportName) %>

					<img class="remove-existing-report" src="<%= themeDisplay.getPathThemeImages() %>/arrows/02_x.png" />

					<aui:input name="reportName" type="hidden" value="<%= reportName %>" />
				</span>

				<aui:button cssClass="cancel-update-template-report" style="display: none;" value="cancel" />
			</aui:field-wrapper>
		</aui:fieldset>

		<aui:fieldset collapsible="<%= true %>" cssClass="options-group" label="report-parameters">
			<aui:input cssClass="report-parameters" name="reportParameters" type="hidden" />

			<aui:row>
				<aui:col width="<%= 35 %>">
					<aui:input cssClass="parameters-key" name="key" size="20" type="text" />
				</aui:col>

				<aui:col width="<%= 35 %>">

					<%
					Calendar calendar = CalendarFactoryUtil.getCalendar(timeZone, locale);
					%>

					<aui:field-wrapper>
						<aui:input cssClass="parameters-value parameters-value-field-set" name="value" size="20" type="text" />

						<liferay-ui:input-date
							cssClass="parameters-input-date"
							dayParam="parameterDateDay"
							dayValue="<%= calendar.get(Calendar.DATE) %>"
							disabled="<%= false %>"
							firstDayOfWeek="<%= calendar.getFirstDayOfWeek() - 1 %>"
							monthParam="parameterDateMonth"
							monthValue="<%= calendar.get(Calendar.MONTH) %>"
							yearParam="parameterDateYear"
							yearValue="<%= calendar.get(Calendar.YEAR) %>"
						/>
					</aui:field-wrapper>
				</aui:col>

				<aui:col width="<%= 15 %>">
					<aui:select cssClass="parameters-input-type" label="type" name="type">
						<aui:option label="text" value="text" />
						<aui:option label="date" value="date" />
					</aui:select>
				</aui:col>

				<aui:col width="<%= 15 %>">
					<aui:button-row cssClass="add-parameter">
						<aui:button value="add-parameter" />
					</aui:button-row>
				</aui:col>
			</aui:row>

			<aui:field-wrapper>
				<aui:col>
					<div class="report-tags"></div>
				</aui:col>
			</aui:field-wrapper>
		</aui:fieldset>

		<c:if test="<%= definition == null %>">
			<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="permissions">
				<liferay-ui:input-permissions
					modelName="<%= Definition.class.getName() %>"
				/>
			</aui:fieldset>
		</c:if>
	</aui:fieldset-group>

	<aui:button-row>
		<portlet:renderURL var="viewURL">
			<portlet:param name="mvcPath" value="/admin/view.jsp" />
			<portlet:param name="tabs1" value="definitions" />
		</portlet:renderURL>

		<aui:button cssClass="btn-lg" type="submit" value='<%= (definition != null) ? "update" : "save" %>' />

		<c:if test="<%= definition != null %>">
			<c:if test="<%= DefinitionPermissionChecker.contains(permissionChecker, definition, ReportsActionKeys.ADD_REPORT) %>">
				<aui:button cssClass="btn-lg" onClick='<%= renderResponse.getNamespace() + "addReport();" %>' value="add-report" />

				<aui:button cssClass="btn-lg" onClick='<%= renderResponse.getNamespace() + "addScheduler();" %>' value="add-schedule" />
			</c:if>

			<aui:button cssClass="btn-lg" onClick='<%= renderResponse.getNamespace() + "deleteDefinition();" %>' value="delete" />
		</c:if>

		<aui:button cssClass="btn-lg" href="<%= viewURL %>" type="cancel" />
	</aui:button-row>
</aui:form>

<script type="text/javascript">
	AUI().ready(function(A) {
		Liferay.Report.initialize({
			namespace: '<portlet:namespace />',
			parameters:
				'<%= HtmlUtil.escapeJS(BeanParamUtil.getString(definition, request, "reportParameters")) %>'
		});
	});

	function <portlet:namespace />addReport() {
		submitForm(
			document.<portlet:namespace />fm,
			'<portlet:renderURL><portlet:param name="mvcPath" value="/admin/report/generate_report.jsp" /><portlet:param name="definitionId" value="<%= String.valueOf(definitionId) %>" /></portlet:renderURL>'
		);
	}

	function <portlet:namespace />addScheduler() {
		submitForm(
			document.<portlet:namespace />fm,
			'<portlet:renderURL><portlet:param name="mvcPath" value="/admin/report/edit_schedule.jsp" /><portlet:param name="definitionId" value="<%= String.valueOf(definitionId) %>" /></portlet:renderURL>'
		);
	}

	function <portlet:namespace />deleteDefinition() {
		if (
			confirm(
				'<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>'
			)
		) {
			submitForm(
				document.<portlet:namespace />fm,
				'<portlet:actionURL name="deleteDefinition"><portlet:param name="redirect" value="<%= definitionsURL %>" /></portlet:actionURL>'
			);
		}
	}
</script>