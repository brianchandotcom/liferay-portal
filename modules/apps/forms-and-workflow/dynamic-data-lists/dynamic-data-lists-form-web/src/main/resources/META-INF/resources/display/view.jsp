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

<liferay-util:dynamic-include key="com.liferay.dynamic.data.lists.form.web#/display/view.jsp#pre" />

<%
String redirect = ParamUtil.getString(request, "redirect", currentURL);

long recordSetId = ddlFormDisplayContext.getRecordSetId();
%>

<c:choose>
	<c:when test="<%= recordSetId == 0 %>">
		<div class="alert alert-info">
			<liferay-ui:message key="select-an-existing-form-or-add-a-form-to-be-displayed-in-this-application" />
		</div>
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="<%= ddlFormDisplayContext.isShowSuccessPage() %>">

				<%
				DDMFormSuccessPageSettings ddmFormSuccessPageSettings = ddlFormDisplayContext.getDDMFormSuccessPageSettings();

				LocalizedValue title = ddmFormSuccessPageSettings.getTitle();
				LocalizedValue body = ddmFormSuccessPageSettings.getBody();
				%>

				<div class="portlet-forms">
					<div class="ddl-form-basic-info">
						<div class="container-fluid-1280">
							<h1 class="ddl-form-name"><%= GetterUtil.getString(title.getString(locale), title.getString(title.getDefaultLocale())) %></h1>

							<h5 class="ddl-form-description"><%= GetterUtil.getString(body.getString(locale), body.getString(body.getDefaultLocale())) %></h5>
						</div>
					</div>
				</div>
			</c:when>
			<c:when test="<%= ddlFormDisplayContext.isFormAvailable() %>">
				<portlet:actionURL name="addRecord" var="addRecordActionURL" />

				<div class="portlet-forms">
					<aui:form action="<%= addRecordActionURL %>" data-DDLRecordSetId="<%= recordSetId %>" method="post" name="fm">

						<%
						String redirectURL = ddlFormDisplayContext.getRedirectURL();
						%>

						<c:if test="<%= Validator.isNull(redirectURL) %>">
							<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
						</c:if>

						<%
						DDLRecordSet recordSet = ddlFormDisplayContext.getRecordSet();
						%>

						<aui:input name="groupId" type="hidden" value="<%= recordSet.getGroupId() %>" />
						<aui:input name="recordSetId" type="hidden" value="<%= recordSet.getRecordSetId() %>" />
						<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_PUBLISH %>" />

						<liferay-ui:error exception="<%= CaptchaTextException.class %>" message="text-verification-failed" />
						<liferay-ui:error exception="<%= DDMFormRenderingException.class %>" message="unable-to-render-the-selected-form" />
						<liferay-ui:error exception="<%= DDMFormValuesValidationException.class %>" message="field-validation-failed" />

						<liferay-ui:error exception="<%= DDMFormValuesValidationException.MustSetValidValue.class %>">

							<%
							DDMFormValuesValidationException.MustSetValidValue msvv = (DDMFormValuesValidationException.MustSetValidValue)errorException;
							%>

							<liferay-ui:message arguments="<%= msvv.getFieldName() %>" key="validation-failed-for-field-x" translateArguments="<%= false %>" />
						</liferay-ui:error>

						<liferay-ui:error exception="<%= DDMFormValuesValidationException.RequiredValue.class %>">

							<%
							DDMFormValuesValidationException.RequiredValue rv = (DDMFormValuesValidationException.RequiredValue)errorException;
							%>

							<liferay-ui:message arguments="<%= rv.getFieldName() %>" key="no-value-is-defined-for-field-x" translateArguments="<%= false %>" />
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
									<h5 class="ddl-form-description"><%= description %></h5>
								</c:if>
							</div>
						</div>

						<div class="container-fluid-1280 ddl-form-builder-app">
							<%= ddlFormDisplayContext.getDDMFormHTML() %>
						</div>
					</aui:form>
				</div>

				<aui:script use="aui-base">
					var <portlet:namespace />intervalId;

					function <portlet:namespace />clearPortletHandlers(event) {
						if (<portlet:namespace />intervalId) {
							clearInterval(<portlet:namespace />intervalId);
						}

						Liferay.detach('destroyPortlet', <portlet:namespace />clearPortletHandlers);
					};

					Liferay.on('destroyPortlet', <portlet:namespace />clearPortletHandlers);

					<c:choose>
						<c:when test="<%= ddlFormDisplayContext.isAutosaveEnabled() %>">
							var <portlet:namespace />form;

							<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="addRecord" var="autoSaveRecordURL">
								<portlet:param name="autoSave" value="<%= Boolean.TRUE.toString() %>" />
							</liferay-portlet:resourceURL>

							function <portlet:namespace />autoSave() {
								A.io.request('<%= autoSaveRecordURL.toString() %>',
									{
										data: {
											<portlet:namespace />recordSetId: <%= recordSetId %>,
											<portlet:namespace />serializedDDMFormValues: JSON.stringify(<portlet:namespace />form.toJSON())
										},
										method: 'POST'
									}
								);
							}

							function <portlet:namespace />startAutoSave() {
								if (<portlet:namespace />intervalId) {
									clearInterval(<portlet:namespace />intervalId);
								}

								<portlet:namespace />intervalId = setInterval(<portlet:namespace />autoSave, 60000);
							}

							<portlet:namespace />form = Liferay.component('<%= ddlFormDisplayContext.getContainerId() %>DDMForm');

							if (<portlet:namespace />form) {
								<portlet:namespace />startAutoSave();
							}
							else {
								Liferay.after(
									Liferay.namespace('DDM').Form + ':render',
									function(event) {
										<portlet:namespace />form = Liferay.component(event.containerId + 'DDMForm');

										if (<portlet:namespace />form) {
											<portlet:namespace />startAutoSave();
										}
									}
								);
							}
						</c:when>
						<c:otherwise>
							function <portlet:namespace />startAutoExtendSession() {
								if (<portlet:namespace />intervalId) {
									clearInterval(<portlet:namespace />intervalId);
								}

								var tenSeconds = 10000;

								var time = Liferay.Session.get('sessionLength') || tenSeconds;

								<portlet:namespace />intervalId = setInterval(<portlet:namespace />extendSession, (time/2));
							}

							function <portlet:namespace />extendSession() {
								Liferay.Session.extend();
							}

							<portlet:namespace />startAutoExtendSession();
						</c:otherwise>
					</c:choose>
				</aui:script>
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

<liferay-util:dynamic-include key="com.liferay.dynamic.data.lists.form.web#/display/view.jsp#post" />