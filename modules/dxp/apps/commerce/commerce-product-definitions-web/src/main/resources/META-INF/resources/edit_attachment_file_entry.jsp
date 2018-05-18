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
CPAttachmentFileEntriesDisplayContext cpAttachmentFileEntriesDisplayContext = (CPAttachmentFileEntriesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpAttachmentFileEntriesDisplayContext.getCPDefinition();

long cpDefinitionId = cpAttachmentFileEntriesDisplayContext.getCPDefinitionId();

CPAttachmentFileEntry cpAttachmentFileEntry = cpAttachmentFileEntriesDisplayContext.getCPAttachmentFileEntry();

long cpAttachmentFileEntryId = cpAttachmentFileEntriesDisplayContext.getCPAttachmentFileEntryId();

int type = CPAttachmentFileEntryConstants.TYPE_IMAGE;
String addMenuTitle = LanguageUtil.get(request, "add-image");

String screenNavigationCategoryKey = cpAttachmentFileEntriesDisplayContext.getScreenNavigationCategoryKey();

if (screenNavigationCategoryKey.equals("attachments")) {
	type = CPAttachmentFileEntryConstants.TYPE_OTHER;
	addMenuTitle = LanguageUtil.get(request, "add-attachment");
}

PortletURL productAttachmentsURL = renderResponse.createRenderURL();

productAttachmentsURL.setParameter("mvcRenderCommandName", "editProductDefinition");
productAttachmentsURL.setParameter("cpDefinitionId", String.valueOf(cpDefinition.getCPDefinitionId()));
productAttachmentsURL.setParameter("screenNavigationCategoryKey", screenNavigationCategoryKey);
productAttachmentsURL.setParameter("type", String.valueOf(type));

String title = (cpAttachmentFileEntry == null) ? addMenuTitle : cpAttachmentFileEntry.getTitle(languageId);

Map<String, Object> data = new HashMap<>();

data.put("direction-right", StringPool.TRUE);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "products"), catalogURL, data);
PortalUtil.addPortletBreadcrumbEntry(request, cpDefinition.getName(languageId), String.valueOf(cpAttachmentFileEntriesDisplayContext.getEditProductDefinitionURL()), data);
PortalUtil.addPortletBreadcrumbEntry(request, screenNavigationCategoryKey, productAttachmentsURL.toString(), data);
PortalUtil.addPortletBreadcrumbEntry(request, title, StringPool.BLANK, data);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	items="<%= CPNavigationItemRegistryUtil.getNavigationItems(renderRequest) %>"
/>

<%@ include file="/breadcrumb.jspf" %>

<portlet:actionURL name="editCPAttachmentFileEntry" var="editProductDefinitionOptionRelActionURL" />

<div class="container-fluid-1280 entry-body">
	<aui:form action="<%= editProductDefinitionOptionRelActionURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveAttachmentFileEntry();" %>'>
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpAttachmentFileEntry == null) ? Constants.ADD : Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= productAttachmentsURL %>" />
		<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinitionId %>" />
		<aui:input name="cpAttachmentFileEntryId" type="hidden" value="<%= cpAttachmentFileEntryId %>" />
		<aui:input name="screenNavigationCategoryKey" type="hidden" value="<%= screenNavigationCategoryKey %>" />
		<aui:input name="type" type="hidden" value="<%= type %>" />
		<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />

		<c:if test="<%= (cpAttachmentFileEntry != null) && !cpAttachmentFileEntry.isNew() %>">
			<liferay-frontend:info-bar>
				<aui:workflow-status id="<%= String.valueOf(cpAttachmentFileEntryId) %>" markupView="lexicon" showHelpMessage="<%= false %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= cpAttachmentFileEntry.getStatus() %>" />
			</liferay-frontend:info-bar>
		</c:if>

		<div class="lfr-form-content">
			<liferay-ui:form-navigator
				backURL="<%= productAttachmentsURL.toString() %>"
				formModelBean="<%= cpAttachmentFileEntry %>"
				id="<%= CPAttachmentFileEntryFormNavigatorConstants.FORM_NAVIGATOR_ID_CP_ATTACHMENT_FILE_ENTRY %>"
				markupView="lexicon"
				showButtons="<%= false %>"
			/>

			<%
			boolean pending = false;

			if (cpAttachmentFileEntry != null) {
				pending = cpAttachmentFileEntry.isPending();
			}
			%>

			<c:if test="<%= pending %>">
				<div class="alert alert-info">
					<liferay-ui:message key="there-is-a-publication-workflow-in-process" />
				</div>
			</c:if>
		</div>

		<aui:button-row cssClass="product-definition-button-row">

			<%
			String saveButtonLabel = "save";

			if ((cpAttachmentFileEntry == null) || cpAttachmentFileEntry.isDraft() || cpAttachmentFileEntry.isApproved() || cpAttachmentFileEntry.isExpired() || cpAttachmentFileEntry.isScheduled()) {
				saveButtonLabel = "save-as-draft";
			}

			String publishButtonLabel = "publish";

			if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, CPAttachmentFileEntry.class.getName())) {
				publishButtonLabel = "submit-for-publication";
			}
			%>

			<aui:button cssClass="btn-lg" disabled="<%= pending %>" name="publishButton" type="submit" value="<%= publishButtonLabel %>" />

			<aui:button cssClass="btn-lg" name="saveButton" primary="<%= false %>" type="submit" value="<%= saveButtonLabel %>" />

			<aui:button cssClass="btn-lg" href="<%= productAttachmentsURL.toString() %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace />saveAttachmentFileEntry(forceDisable) {
		var form = AUI.$(document.<portlet:namespace />fm);

		var ddmForm = Liferay.component("<%= cpDefinitionId %>DDMForm");

		if (ddmForm) {
			var fields = ddmForm.getImmediateFields();

			var fieldValues = [];

			fields.forEach(
				function(field) {
					var fieldValue = {};

					fieldValue.key = field.get('fieldName');

					var value = field.getValue();

					var arrValue = [];

					if (value instanceof Array) {
						arrValue = value;
					}
					else {
						arrValue.push(value);
					}

					fieldValue.value = arrValue;

					fieldValues.push(fieldValue);
				}
			);

			form.fm('ddmFormValues').val(JSON.stringify(fieldValues));
		}

		submitForm(form);
	}
</aui:script>

<aui:script use="aui-base,event-input">
	var publishButton = A.one('#<portlet:namespace />publishButton');

	publishButton.on(
		'click',
		function() {
			var workflowActionInput = A.one('#<portlet:namespace />workflowAction');

			if (workflowActionInput) {
				workflowActionInput.val('<%= WorkflowConstants.ACTION_PUBLISH %>');
			}
		}
	);
</aui:script>