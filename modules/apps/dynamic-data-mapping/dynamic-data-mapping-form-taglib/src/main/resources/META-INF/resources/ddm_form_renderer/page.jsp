<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/ddm_form_renderer/init.jsp" %>

<%
if (ddmFormInstance != null) {
	ddmFormInstanceId = ddmFormInstance.getFormInstanceId();
}
%>

<c:choose>
	<c:when test="<%= ddmFormInstanceId == 0 %>">
		<clay:alert
			message="select-an-existing-form-or-add-a-form-to-be-displayed-in-this-application"
		/>
	</c:when>
	<c:otherwise>

		<%
		Locale displayLocale = LocaleUtil.fromLanguageId(languageId);
		%>

		<c:choose>
			<c:when test="<%= formAvailable %>">
				<div class="portlet-forms">
					<c:if test="<%= Validator.isNull(redirectURL) %>">
						<aui:input name="redirect" type="hidden" value='<%= ParamUtil.getString(request, "redirect", PortalUtil.getCurrentURL(request)) %>' />
					</c:if>

					<aui:input name="groupId" type="hidden" value="<%= ddmFormInstance.getGroupId() %>" />
					<aui:input name="formInstanceId" type="hidden" value="<%= ddmFormInstance.getFormInstanceId() %>" />
					<aui:input name="languageId" type="hidden" value="<%= languageId %>" />
					<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_PUBLISH %>" />

					<liferay-ui:error exception="<%= DDMFormRenderingException.class %>" message="unable-to-render-the-selected-form" />
					<liferay-ui:error exception="<%= DDMFormValuesValidationException.class %>" message="field-validation-failed" />

					<liferay-ui:error exception="<%= DDMFormValuesValidationException.MustSetValidValue.class %>">

						<%
						DDMFormValuesValidationException.MustSetValidValue msvv = (DDMFormValuesValidationException.MustSetValidValue)errorException;

						String fieldLabelValue = msvv.getFieldLabelValue(displayLocale);

						if (Validator.isNull(fieldLabelValue)) {
							fieldLabelValue = msvv.getFieldName();
						}
						%>

						<liferay-ui:message arguments="<%= HtmlUtil.escape(fieldLabelValue) %>" key="validation-failed-for-field-x" translateArguments="<%= false %>" />
					</liferay-ui:error>

					<liferay-ui:error exception="<%= DDMFormValuesValidationException.RequiredValue.class %>">

						<%
						DDMFormValuesValidationException.RequiredValue rv = (DDMFormValuesValidationException.RequiredValue)errorException;

						String fieldLabelValue = rv.getFieldLabelValue(displayLocale);

						if (Validator.isNull(fieldLabelValue)) {
							fieldLabelValue = rv.getFieldName();
						}
						%>

						<liferay-ui:message arguments="<%= HtmlUtil.escape(fieldLabelValue) %>" key="no-value-is-defined-for-field-x" translateArguments="<%= false %>" />
					</liferay-ui:error>

					<liferay-ui:error exception="<%= NoSuchFormInstanceException.class %>" message="the-selected-form-no-longer-exists" />
					<liferay-ui:error exception="<%= NoSuchStructureException.class %>" message="unable-to-retrieve-the-definition-of-the-selected-form" />
					<liferay-ui:error exception="<%= NoSuchStructureLayoutException.class %>" message="unable-to-retrieve-the-layout-of-the-selected-form" />

					<liferay-ui:error-principal />

					<c:if test="<%= !hasAddFormInstanceRecordPermission %>">
						<clay:alert
							displayType="warning"
							message="you-do-not-have-the-permission-to-submit-this-form"
						/>
					</c:if>

					<c:if test="<%= showFormBasicInfo %>">
						<div class="ddm-form-basic-info">
							<clay:container-fluid>
								<h1 class="ddm-form-name"><%= HtmlUtil.escape(ddmFormInstance.getName(displayLocale)) %></h1>

								<%
								String description = HtmlUtil.escape(ddmFormInstance.getDescription(displayLocale));
								%>

								<c:if test="<%= Validator.isNotNull(description) %>">
									<h5 class="ddm-form-description"><%= description %></h5>
								</c:if>
							</clay:container-fluid>
						</div>
					</c:if>

					<clay:container-fluid
						cssClass="ddm-form-builder-app"
					>
						<%= ddmFormHTML %>

						<aui:input name="empty" type="hidden" value="" />
					</clay:container-fluid>
				</div>
			</c:when>
			<c:when test="<%= !hasViewFormInstancePermission %>">
				<clay:alert
					displayType="warning"
					message="you-do-not-have-the-permission-to-view-this-form"
				/>
			</c:when>
			<c:otherwise>
				<clay:alert
					displayType="warning"
					message="this-form-not-available-or-it-was-not-published"
				/>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>