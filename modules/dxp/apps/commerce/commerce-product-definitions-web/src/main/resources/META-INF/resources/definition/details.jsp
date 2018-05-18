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
CPDefinitionsDisplayContext cpDefinitionsDisplayContext = (CPDefinitionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpDefinitionsDisplayContext.getCPDefinition();

long cpDefinitionId = cpDefinitionsDisplayContext.getCPDefinitionId();

String productTypeName = BeanParamUtil.getString(cpDefinition, request, "productTypeName");

String friendlyURLBase = themeDisplay.getPortalURL() + CPConstants.SEPARATOR_PRODUCT_URL;

boolean neverExpire = ParamUtil.getBoolean(request, "neverExpire", true);

if ((cpDefinition != null) && (cpDefinition.getExpirationDate() != null)) {
	neverExpire = false;
}
%>

<portlet:actionURL name="editProductDefinition" var="editProductDefinitionActionURL" />

<aui:form action="<%= editProductDefinitionActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpDefinition == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpDefinitionId" type="hidden" value="<%= String.valueOf(cpDefinitionId) %>" />
	<aui:input name="productTypeName" type="hidden" value="<%= productTypeName %>" />
	<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<liferay-ui:error-marker
				key="<%= WebKeys.ERROR_SECTION %>"
				value="details"
			/>

			<aui:model-context bean="<%= cpDefinition %>" model="<%= CPDefinition.class %>" />

			<liferay-ui:error exception="<%= CPDefinitionMetaDescriptionException.class %>" message="the-meta-description-is-too-long" />
			<liferay-ui:error exception="<%= CPDefinitionMetaKeywordsException.class %>" message="the-meta-keywords-are-too-long" />
			<liferay-ui:error exception="<%= CPDefinitionMetaTitleException.class %>" message="the-meta-title-is-too-long" />

			<liferay-ui:error exception="<%= CPFriendlyURLEntryException.class %>">

				<%
				CPFriendlyURLEntryException cpfuee = (CPFriendlyURLEntryException)errorException;
				%>

				<%@ include file="/error_friendly_url_exception.jspf" %>
			</liferay-ui:error>

			<c:if test="<%= (cpDefinition != null) && !cpDefinition.isNew() %>">
				<liferay-frontend:info-bar>
					<aui:workflow-status id="<%= String.valueOf(cpDefinitionId) %>" markupView="lexicon" showHelpMessage="<%= false %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= cpDefinition.getStatus() %>" />
				</liferay-frontend:info-bar>
			</c:if>

			<aui:input autoFocus="<%= true %>" label="name" localized="<%= true %>" name="nameMapAsXML" type="text">
				<aui:validator name="required" />
			</aui:input>

			<aui:input label="short-description" localized="<%= true %>" name="shortDescriptionMapAsXML" resizable="<%= true %>" type="textarea" />

			<%
			String descriptionMapAsXML = StringPool.BLANK;

			if (cpDefinition != null) {
				descriptionMapAsXML = cpDefinition.getDescriptionMapAsXML();
			}
			%>

			<aui:field-wrapper>
				<label class="control-label" for="<portlet:namespace />descriptionMapAsXML"><liferay-ui:message key="full-description" /></label>

				<div class="entry-content form-group">
					<liferay-ui:input-localized
						editorName="alloyeditor"
						name="descriptionMapAsXML"
						type="editor"
						xml="<%= descriptionMapAsXML %>"
					/>
				</div>
			</aui:field-wrapper>
		</aui:fieldset>

		<aui:fieldset collapsible="<%= true %>" label="seo">
			<div class="form-group">
				<label for="<portlet:namespace />urlTitleMapAsXML"><liferay-ui:message key="friendly-url" /><liferay-ui:icon-help message='<%= LanguageUtil.format(request, "for-example-x", "<em>news</em>", false) %>' /></label>

				<liferay-ui:input-localized
					defaultLanguageId="<%= LocaleUtil.toLanguageId(themeDisplay.getSiteDefaultLocale()) %>"
					inputAddon="<%= StringUtil.shorten(friendlyURLBase.toString(), 40) %>"
					name="urlTitleMapAsXML"
					xml="<%= HttpUtil.decodeURL(cpDefinitionsDisplayContext.getUrlTitleMapAsXML()) %>"
				/>
			</div>

			<aui:input label="meta-title" localized="<%= true %>" name="metaTitleMapAsXML" type="text" />

			<aui:input label="meta-description" localized="<%= true %>" name="metaDescriptionMapAsXML" type="textarea" />

			<aui:input label="meta-keywords" localized="<%= true %>" name="metaKeywordsMapAsXML" type="textarea" />
		</aui:fieldset>

		<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="schedule">
			<aui:input name="published" />

			<aui:input formName="fm" name="displayDate" />

			<aui:input dateTogglerCheckboxLabel="never-expire" disabled="<%= neverExpire %>" formName="fm" name="expirationDate" />
		</aui:fieldset>

		<c:if test="<%= cpDefinitionsDisplayContext.hasCustomAttributesAvailable() %>">
			<aui:fieldset collapsible="<%= true %>" label="custom-attribute">
				<liferay-expando:custom-attribute-list
					className="<%= CPDefinition.class.getName() %>"
					classPK="<%= (cpDefinition != null) ? cpDefinition.getCPDefinitionId() : 0 %>"
					editable="<%= true %>"
					label="<%= true %>"
				/>
			</aui:fieldset>
		</c:if>

		<aui:fieldset>

			<%
			boolean pending = false;

			if (cpDefinition != null) {
				pending = cpDefinition.isPending();
			}
			%>

			<c:if test="<%= pending %>">
				<div class="alert alert-info">
					<liferay-ui:message key="there-is-a-publication-workflow-in-process" />
				</div>
			</c:if>

			<aui:button-row cssClass="product-definition-button-row">

				<%
				String saveButtonLabel = "save";

				if ((cpDefinition == null) || cpDefinition.isDraft() || cpDefinition.isApproved() || cpDefinition.isExpired() || cpDefinition.isScheduled()) {
					saveButtonLabel = "save-as-draft";
				}

				String publishButtonLabel = "publish";

				if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, CPDefinition.class.getName())) {
					publishButtonLabel = "submit-for-publication";
				}
				%>

				<aui:button cssClass="btn-primary" disabled="<%= pending %>" name="publishButton" type="submit" value="<%= publishButtonLabel %>" />

				<aui:button name="saveButton" primary="<%= false %>" type="submit" value="<%= saveButtonLabel %>" />

				<aui:button cssClass="btn-link" href="<%= catalogURL %>" type="cancel" />
			</aui:button-row>
		</aui:fieldset>
	</aui:fieldset-group>
</aui:form>

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

<c:if test="<%= cpDefinition == null %>">
	<aui:script sandbox="<%= true %>" use="aui-base">
		var form = $(document.<portlet:namespace />fm);

		var nameInput = form.fm('nameMapAsXML');
		var urlInput = form.fm('urlTitleMapAsXML');
		var urlTitleInputLocalized = Liferay.component('<portlet:namespace />urlTitleMapAsXML');

		var onNameInput = _.debounce(
			function(event) {
				urlInput.val(nameInput.val());

				urlTitleInputLocalized.updateInputLanguage(nameInput.val());
			},
			200
		);

		nameInput.on('input', onNameInput);
	</aui:script>
</c:if>