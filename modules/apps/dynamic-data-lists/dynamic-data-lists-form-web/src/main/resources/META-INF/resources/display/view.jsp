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

<%@ include file="/display/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect", currentURL);

DDLRecordSet recordSet = ddlFormDisplayContext.getRecordSet();
%>

<c:choose>
	<c:when test="<%= (recordSet == null) %>">
		<div class="alert alert-info">
			<liferay-ui:message key="select-an-existing-form-or-add-a-form-to-be-displayed-in-this-application" />
		</div>
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="<%= ddlFormDisplayContext.isFormAvailable() %>">
				<portlet:actionURL name="addRecord" var="addRecordActionURL" />

				<div class="portlet-forms">
					<aui:form action="<%= addRecordActionURL %>" method="post" name="fm">

						<%
						String redirectURL = ddlFormDisplayContext.getRedirectURL();
						%>

						<c:if test="<%= Validator.isNull(redirectURL) %>">
							<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
						</c:if>

						<aui:input name="groupId" type="hidden" value="<%= recordSet.getGroupId() %>" />
						<aui:input name="recordSetId" type="hidden" value="<%= recordSet.getRecordSetId() %>" />
						<aui:input name="availableLanguageId" type="hidden" value="<%= themeDisplay.getLanguageId() %>" />
						<aui:input name="defaultLanguageId" type="hidden" value="<%= themeDisplay.getLanguageId() %>" />
						<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_PUBLISH %>" />

						<liferay-ui:error exception="<%= CaptchaMaxChallengesException.class %>" message="maximum-number-of-captcha-attempts-exceeded" />
						<liferay-ui:error exception="<%= CaptchaTextException.class %>" message="text-verification-failed" />
						<liferay-ui:error exception="<%= DDMFormRenderingException.class %>" message="unable-to-render-the-selected-form" />
						<liferay-ui:error exception="<%= DDMFormValuesValidationException.class %>" message="field-validation-failed" />

						<liferay-ui:error exception="<%= DDMFormValuesValidationException.MustSetValidValues.class %>">

							<%
							DDMFormValuesValidationException.MustSetValidValues msvv = (DDMFormValuesValidationException.MustSetValidValues)errorException;

							List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults = msvv.getDDMFormFieldEvaluationResults();

							for (DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult : ddmFormFieldEvaluationResults) {
							%>

								<liferay-ui:message
									arguments="<%= new Object[] {ddmFormFieldEvaluationResult.getName(), ddmFormFieldEvaluationResult.getErrorMessage()} %>"
									key="validation-failed-for-field-x"
									translateArguments="<%= false %>"
								/>

								<br />

							<%
							}
							%>

						</liferay-ui:error>

						<liferay-ui:error exception="<%= DDMFormValuesValidationException.RequiredValue.class %>">

							<%
							DDMFormValuesValidationException.RequiredValue rv = (DDMFormValuesValidationException.RequiredValue)errorException;
							%>

							<liferay-ui:message arguments="<%= rv.getFieldName() %>" key="no-value-defined-for-field-x" translateArguments="<%= false %>" />
						</liferay-ui:error>

						<liferay-ui:error exception="<%= NoSuchRecordSetException.class %>" message="the-selected-form-no-longer-exists" />
						<liferay-ui:error exception="<%= NoSuchStructureException.class %>" message="unable-to-retrieve-the-definition-of-the-selected-form" />
						<liferay-ui:error exception="<%= NoSuchStructureLayoutException.class %>" message="unable-to-retrieve-the-layout-of-the-selected-form" />

						<liferay-ui:error-principal />

						<div class="ddl-form-basic-info">
							<div class="container-fluid-1280">
								<h1 class="ddl-form-name"><%= recordSet.getName(locale) %></h1>

								<%
								String description = recordSet.getDescription(locale);
								%>

								<c:if test="<%= Validator.isNotNull(description) %>">
									<h2 class="ddl-form-description"><%= description %></h2>
								</c:if>
							</div>
						</div>

						<div class="container-fluid-1280 ddl-form-builder-app">
							<%= request.getAttribute(DDMWebKeys.DYNAMIC_DATA_MAPPING_FORM_HTML) %>
						</div>
					</aui:form>
				</div>
			</c:when>
			<c:otherwise>
				<div class="alert alert-warning">
					<liferay-ui:message key="this-form-not-available-or-it-was-not-published" />
				</div>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>

<c:if test="<%= ddlFormDisplayContext.isShowConfigurationIcon() %>">
	<div class="icons-container lfr-meta-actions">
		<div class="lfr-icon-actions">
			<liferay-ui:icon
				cssClass="lfr-icon-action lfr-icon-action-configuration"
				iconCssClass="icon-cog"
				label="<%= true %>"
				message="select-form"
				method="get"
				onClick="<%= portletDisplay.getURLConfigurationJS() %>"
				url="<%= portletDisplay.getURLConfiguration() %>"
			/>
		</div>
	</div>
</c:if>