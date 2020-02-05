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

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String portletResource = ParamUtil.getString(request, "portletResource");

DDLRecord record = (DDLRecord)request.getAttribute(DDLWebKeys.DYNAMIC_DATA_LISTS_RECORD);

long recordId = BeanParamUtil.getLong(record, request, "recordId");

long recordSetId = BeanParamUtil.getLong(record, request, "recordSetId");

long formDDMTemplateId = ParamUtil.getLong(request, "formDDMTemplateId");

DDLRecordVersion recordVersion = null;

if (record != null) {
	recordVersion = record.getLatestRecordVersion();
}

DDLRecordSet recordSet = DDLRecordSetServiceUtil.getRecordSet(recordSetId);

DDMStructure ddmStructure = recordSet.getDDMStructure();

DDMFormValues ddmFormValues = null;

if (recordVersion != null) {
	ddmFormValues = ddlDisplayContext.getDDMFormValues(recordVersion.getDDMStorageId());
}

String defaultLanguageId = ParamUtil.getString(request, "defaultLanguageId");

if (ddmFormValues != null) {
	defaultLanguageId = LocaleUtil.toLanguageId(ddmFormValues.getDefaultLocale());
}
else if (Validator.isNull(defaultLanguageId)) {
	defaultLanguageId = LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault());
}

String languageId = ParamUtil.getString(request, "languageId", defaultLanguageId);

boolean translating = false;

if (!defaultLanguageId.equals(languageId)) {
	translating = true;
}

if (translating) {
	redirect = currentURL;
}

if (ddlDisplayContext.isAdminPortlet()) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(redirect);

	renderResponse.setTitle((record != null) ? LanguageUtil.format(request, "edit-x", ddmStructure.getName(locale), false) : LanguageUtil.format(request, "new-x", ddmStructure.getName(locale), false));
}
else {
	portletDisplay.setShowBackIcon(false);

	renderResponse.setTitle(recordSet.getName(locale));
}
%>

<portlet:actionURL name="addRecord" var="addRecordURL">
	<portlet:param name="mvcPath" value="/edit_record.jsp" />
</portlet:actionURL>

<portlet:actionURL name="updateRecord" var="updateRecordURL">
	<portlet:param name="mvcPath" value="/edit_record.jsp" />
</portlet:actionURL>

<c:if test="<%= record != null %>">
	<clay:management-toolbar
		infoPanelId="infoPanelId"
		namespace="<%= renderResponse.getNamespace() %>"
		selectable="false"
		showSearch="false"
	/>
</c:if>

<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
	<c:if test="<%= recordVersion != null %>">
		<div class="sidenav-menu-slider">
			<div class="sidebar sidebar-default sidenav-menu">
				<div class="sidebar-header">
					<aui:icon cssClass="d-inline-block d-sm-none icon-monospaced sidenav-close text-default" image="times" markupView="lexicon" url="javascript:;" />
				</div>

				<liferay-ui:tabs
					cssClass="navbar-no-collapse"
					names="details,versions"
					refresh="<%= false %>"
					type="dropdown"
				>
					<liferay-ui:section>
						<div class="sidebar-body">
							<h3 class="version">
								<liferay-ui:message key="version" /> <%= HtmlUtil.escape(recordVersion.getVersion()) %>
							</h3>

							<div>
								<aui:model-context bean="<%= recordVersion %>" model="<%= DDLRecordVersion.class %>" />

								<aui:workflow-status model="<%= DDLRecord.class %>" status="<%= recordVersion.getStatus() %>" />
							</div>

							<div>
								<h5>
									<strong><liferay-ui:message key="created" /></strong>
								</h5>

								<p>

									<%
									Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
									%>

									<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(recordVersion.getUserName()), dateFormatDateTime.format(recordVersion.getCreateDate())} %>" key="by-x-on-x" translateArguments="<%= false %>" />
								</p>
							</div>
						</div>
					</liferay-ui:section>

					<liferay-ui:section>
						<div class="sidebar-body">
							<liferay-util:include page="/view_record_history.jsp" servletContext="<%= application %>">
								<liferay-util:param name="redirect" value="<%= redirect %>" />
							</liferay-util:include>
						</div>
					</liferay-ui:section>
				</liferay-ui:tabs>
			</div>
		</div>
	</c:if>

	<div class="sidenav-content">
		<aui:form action="<%= (record == null) ? addRecordURL : updateRecordURL %>" cssClass="container-fluid-1280" enctype="multipart/form-data" method="post" name="fm">
			<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
			<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />
			<aui:input name="recordId" type="hidden" value="<%= recordId %>" />
			<aui:input name="groupId" type="hidden" value="<%= recordSet.getGroupId() %>" />
			<aui:input name="recordSetId" type="hidden" value="<%= recordSetId %>" />
			<aui:input name="formDDMTemplateId" type="hidden" value="<%= formDDMTemplateId %>" />
			<aui:input name="defaultLanguageId" type="hidden" value="<%= defaultLanguageId %>" />
			<aui:input name="languageId" type="hidden" value="<%= languageId %>" />
			<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_PUBLISH %>" />

			<div class="lfr-form-content">
				<liferay-ui:error exception="<%= DuplicateFileEntryException.class %>" message="a-file-with-that-name-already-exists" />

				<liferay-ui:error exception="<%= FileSizeException.class %>">
					<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(DLValidatorUtil.getMaxAllowableSize(), locale) %>" key="please-enter-a-file-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
				</liferay-ui:error>

				<liferay-ui:error exception="<%= StorageFieldRequiredException.class %>" message="please-fill-out-all-required-fields" />

				<%
				long classNameId = PortalUtil.getClassNameId(DDMStructure.class);

				long classPK = recordSet.getDDMStructureId();

				if (formDDMTemplateId > 0) {
					classNameId = PortalUtil.getClassNameId(DDMTemplate.class);

					classPK = formDDMTemplateId;
				}
				%>

				<c:choose>
					<c:when test="<%= ddlDisplayContext.isFormView() %>">
						<liferay-ddm:html
							classNameId="<%= classNameId %>"
							classPK="<%= classPK %>"
							ddmFormValues="<%= ddmFormValues %>"
							defaultEditLocale="<%= LocaleUtil.fromLanguageId(defaultLanguageId) %>"
							defaultLocale="<%= LocaleUtil.fromLanguageId(defaultLanguageId) %>"
							groupId="<%= recordSet.getGroupId() %>"
							repeatable="<%= translating ? false : true %>"
							requestedLocale="<%= locale %>"
						/>
					</c:when>
					<c:otherwise>
						<aui:fieldset-group markupView="lexicon">
							<aui:fieldset>
								<liferay-ddm:html
									classNameId="<%= classNameId %>"
									classPK="<%= classPK %>"
									ddmFormValues="<%= ddmFormValues %>"
									defaultEditLocale="<%= LocaleUtil.fromLanguageId(defaultLanguageId) %>"
									defaultLocale="<%= LocaleUtil.fromLanguageId(defaultLanguageId) %>"
									groupId="<%= recordSet.getGroupId() %>"
									repeatable="<%= translating ? false : true %>"
									requestedLocale="<%= locale %>"
								/>
							</aui:fieldset>
						</aui:fieldset-group>
					</c:otherwise>
				</c:choose>

				<%
				boolean pending = false;

				if (recordVersion != null) {
					pending = recordVersion.isPending();
				}
				%>

				<c:if test="<%= pending %>">
					<div class="alert alert-info">
						<liferay-ui:message key="there-is-a-publication-workflow-in-process" />
					</div>
				</c:if>
			</div>

			<aui:button-row>

				<%
				String saveButtonLabel = "save";

				if ((recordVersion == null) || recordVersion.isDraft() || recordVersion.isApproved()) {
					saveButtonLabel = "save-as-draft";
				}

				String publishButtonLabel = "publish";

				if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, DDLRecordSet.class.getName(), recordSetId)) {
					publishButtonLabel = "submit-for-publication";
				}

				if (ddlDisplayContext.isFormView()) {
					publishButtonLabel = "submit";
				}
				%>

				<c:if test="<%= ddlDisplayContext.isShowSaveRecordButton() %>">
					<aui:button name="saveButton" onClick='<%= renderResponse.getNamespace() + "setWorkflowAction(true);" %>' primary="<%= false %>" type="submit" value="<%= saveButtonLabel %>" />
				</c:if>

				<c:if test="<%= ddlDisplayContext.isShowPublishRecordButton() %>">
					<aui:button disabled="<%= pending %>" name="publishButton" onClick='<%= renderResponse.getNamespace() + "setWorkflowAction(false);" %>' type="submit" value="<%= publishButtonLabel %>" />
				</c:if>

				<c:if test="<%= ddlDisplayContext.isShowCancelButton() %>">
					<aui:button href="<%= redirect %>" name="cancelButton" type="cancel" />
				</c:if>
			</aui:button-row>
		</aui:form>
	</div>
</div>

<aui:script>
	function <portlet:namespace />setWorkflowAction(draft) {
		if (draft) {
			document.<portlet:namespace />fm.<portlet:namespace />workflowAction.value = <%= WorkflowConstants.ACTION_SAVE_DRAFT %>;
		}
		else {
			document.<portlet:namespace />fm.<portlet:namespace />workflowAction.value = <%= WorkflowConstants.ACTION_PUBLISH %>;
		}
	}
</aui:script>

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view_record_set.jsp");
portletURL.setParameter("recordSetId", String.valueOf(recordSetId));

PortalUtil.addPortletBreadcrumbEntry(request, recordSet.getName(locale), portletURL.toString());

if (record != null) {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.format(request, "edit-x", ddmStructure.getName(locale), false), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.format(request, "add-x", ddmStructure.getName(locale), false), currentURL);
}
%>