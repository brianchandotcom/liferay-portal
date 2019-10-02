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

<%@ include file="/ddm_form_renderer/init.jsp" %>

<%
if (ddmFormInstance != null) {
	ddmFormInstanceId = ddmFormInstance.getFormInstanceId();
}
%>

<c:choose>
	<c:when test="<%= ddmFormInstanceId == 0 %>">
		<clay:alert
			message='<%= LanguageUtil.get(resourceBundle, "select-an-existing-form-or-add-a-form-to-be-displayed-in-this-application") %>'
			style="info"
			title='<%= LanguageUtil.get(resourceBundle, "info") %>'
		/>
	</c:when>
	<c:otherwise>

		<%
		Locale displayLocale = LocaleUtil.fromLanguageId(languageId);
		%>

		<c:choose>
			<c:when test="<%= isFormAvailable %>">
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
						%>

						<liferay-ui:message arguments="<%= HtmlUtil.escape(msvv.getFieldName()) %>" key="validation-failed-for-field-x" translateArguments="<%= false %>" />
					</liferay-ui:error>

					<liferay-ui:error exception="<%= DDMFormValuesValidationException.RequiredValue.class %>">

						<%
						DDMFormValuesValidationException.RequiredValue rv = (DDMFormValuesValidationException.RequiredValue)errorException;
						%>

						<liferay-ui:message arguments="<%= HtmlUtil.escape(rv.getFieldName()) %>" key="no-value-is-defined-for-field-x" translateArguments="<%= false %>" />
					</liferay-ui:error>

					<liferay-ui:error exception="<%= NoSuchFormInstanceException.class %>" message="the-selected-form-no-longer-exists" />
					<liferay-ui:error exception="<%= NoSuchStructureException.class %>" message="unable-to-retrieve-the-definition-of-the-selected-form" />
					<liferay-ui:error exception="<%= NoSuchStructureLayoutException.class %>" message="unable-to-retrieve-the-layout-of-the-selected-form" />

					<liferay-ui:error-principal />

					<c:if test="<%= !hasAddFormInstanceRecordPermission %>">
						<clay:alert
							message='<%= LanguageUtil.get(resourceBundle, "you-do-not-have-the-permission-to-submit-this-form") %>'
							style="warning"
							title='<%= LanguageUtil.get(resourceBundle, "warning") %>'
						/>
					</c:if>

					<c:if test="<%= showFormBasicInfo %>">
						<div class="ddm-form-basic-info">
							<div class="container-fluid-1280">
								<h1 class="ddm-form-name"><%= HtmlUtil.escape(ddmFormInstance.getName(displayLocale)) %></h1>

								<%
								String description = HtmlUtil.escape(ddmFormInstance.getDescription(displayLocale));
								%>

								<c:if test="<%= Validator.isNotNull(description) %>">
									<h5 class="ddm-form-description"><%= description %></h5>
								</c:if>
							</div>
						</div>
					</c:if>

					<div class="container-fluid-1280 ddm-form-builder-app">
						<%= ddmFormHTML %>

						<aui:input name="empty" type="hidden" value="" />
					</div>
				</div>
			</c:when>
			<c:when test="<%= !hasViewFormInstancePermission %>">
				<clay:alert
					message='<%= LanguageUtil.get(resourceBundle, "you-do-not-have-the-permission-to-view-this-form") %>'
					style="warning"
					title='<%= LanguageUtil.get(resourceBundle, "warning") %>'
				/>
			</c:when>
			<c:otherwise>
				<clay:alert
					message='<%= LanguageUtil.get(resourceBundle, "this-form-not-available-or-it-was-not-published") %>'
					style="warning"
					title='<%= LanguageUtil.get(resourceBundle, "warning") %>'
				/>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>