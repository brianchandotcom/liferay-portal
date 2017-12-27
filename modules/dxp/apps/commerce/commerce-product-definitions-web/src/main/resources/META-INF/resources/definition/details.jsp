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
CPDefinitionsDisplayContext cpDefinitionsDisplayContext = (CPDefinitionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpDefinitionsDisplayContext.getCPDefinition();

long cpDefinitionId = cpDefinitionsDisplayContext.getCPDefinitionId();

String productTypeName = BeanParamUtil.getString(cpDefinition, request, "productTypeName");

String friendlyURLBase = themeDisplay.getPortalURL() + CPConstants.SEPARATOR_PRODUCT_URL;

boolean neverExpire = ParamUtil.getBoolean(request, "neverExpire", true);

if ((cpDefinition != null) && (cpDefinition.getExpirationDate() != null)) {
	neverExpire = false;
}

String defaultLanguageId = LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault());

Set<Locale> availableLocalesSet = new HashSet<>();

availableLocalesSet.add(LocaleUtil.fromLanguageId(defaultLanguageId));
availableLocalesSet.addAll(cpDefinitionsDisplayContext.getAvailableLocales());

Locale[] availableLocales = availableLocalesSet.toArray(new Locale[availableLocalesSet.size()]);
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
			<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="details" />

			<aui:model-context bean="<%= cpDefinition %>" model="<%= CPDefinition.class %>" />

			<liferay-ui:error exception="<%= CPFriendlyURLEntryException.class %>">

				<%
				CPFriendlyURLEntryException cpfuee = (CPFriendlyURLEntryException)errorException;
				%>

				<%@ include file="/error_friendly_url_exception.jspf" %>
			</liferay-ui:error>

			<c:if test="<%= (cpDefinition != null) && !cpDefinition.isNew() %>">
				<liferay-frontend:info-bar>
					<aui:workflow-status
						id="<%= String.valueOf(cpDefinitionId) %>"
						markupView="lexicon"
						showHelpMessage="<%= false %>"
						showIcon="<%= false %>"
						showLabel="<%= false %>"
						status="<%= cpDefinition.getStatus() %>"
					/>
				</liferay-frontend:info-bar>
			</c:if>

			<aui:translation-manager
				availableLocales="<%= availableLocales %>"
				defaultLanguageId="<%= defaultLanguageId %>"
				id="translationManager"
			/>

			<aui:input autoFocus="<%= true %>" label="title" localized="<%= true %>" name="titleMapAsXML" type="text" wrapperCssClass="commerce-product-definition-title">
				<aui:validator name="required" />
			</aui:input>

			<aui:input label="short-description" localized="<%= true %>" name="shortDescriptionMapAsXML" type="textarea" wrapperCssClass="commerce-product-definition-description" />

			<%
			String descriptionMapAsXML = StringPool.BLANK;

			if (cpDefinition != null) {
				descriptionMapAsXML = cpDefinition.getDescriptionMapAsXML();
			}
			%>

			<aui:field-wrapper cssClass="commerce-product-definition-description" label="full-description">
				<div class="entry-content form-group">
					<liferay-ui:input-localized editorName="alloyeditor" name="descriptionMapAsXML" type="editor" xml="<%= descriptionMapAsXML %>" />
				</div>
			</aui:field-wrapper>
		</aui:fieldset>

		<aui:fieldset collapsible="<%= true %>" label="seo">
			<div class="commerce-product-definition-url-title form-group">
				<label for="<portlet:namespace />friendlyURL"><liferay-ui:message key="friendly-url" /> <liferay-ui:icon-help message='<%= LanguageUtil.format(request, "for-example-x", "<em>/news</em>", false) %>' /></label>

				<div class="input-group lfr-friendly-url-input-group">
					<span class="input-group-addon" id="<portlet:namespace />urlBase">
						<span class="input-group-constrain"><liferay-ui:message key="<%= StringUtil.shorten(friendlyURLBase.toString(), 40) %>" /></span>
					</span>

					<liferay-ui:input-localized cssClass="form-control" defaultLanguageId="<%= LocaleUtil.toLanguageId(themeDisplay.getSiteDefaultLocale()) %>" name="urlTitleMapAsXML" xml="<%= HttpUtil.decodeURL(cpDefinitionsDisplayContext.getUrlTitleMapAsXML()) %>" />
				</div>
			</div>

			<aui:input label="meta-title" localized="<%= true %>" name="metaTitleMapAsXML" type="text" wrapperCssClass="commerce-product-definition-meta-title" />

			<aui:input label="meta-keywords" localized="<%= true %>" name="metaKeywordsMapAsXML" type="textarea" wrapperCssClass="commerce-product-definition-meta-keywords" />

			<aui:input label="meta-description" localized="<%= true %>" name="metaDescriptionMapAsXML" type="textarea" wrapperCssClass="commerce-product-definition-meta-description" />
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

<aui:script use="aui-base">
	function afterDeletingAvailableLocale(event) {
		var descriptionInputLocalized = Liferay.component('<portlet:namespace />descriptionMapAsXML');
		var metaDescriptionInputLocalized = Liferay.component('<portlet:namespace />metaDescriptionMapAsXML');
		var metaKeywordsInputLocalized = Liferay.component('<portlet:namespace />metaKeywordsMapAsXML');
		var metaTitleInputLocalized = Liferay.component('<portlet:namespace />metaTitleMapAsXML');
		var shortDescriptionInputLocalized = Liferay.component('<portlet:namespace />shortDescriptionMapAsXML');
		var titleInputLocalized = Liferay.component('<portlet:namespace />titleMapAsXML');var metaDescriptionInputLocalized = Liferay.component('<portlet:namespace />metaDescriptionMapAsXML');
		var urlTitleInputLocalized = Liferay.component('<portlet:namespace />urlTitleMapAsXML');

		var locale = event.locale;

		descriptionInputLocalized.removeInputLanguage(locale);
		metaDescriptionInputLocalized.removeInputLanguage(locale);
		metaKeywordsInputLocalized.removeInputLanguage(locale);
		metaTitleInputLocalized.removeInputLanguage(locale);
		shortDescriptionInputLocalized.removeInputLanguage(locale);
		titleInputLocalized.removeInputLanguage(locale);
		urlTitleInputLocalized.removeInputLanguage(locale);
	}

	function afterEditingLocaleChange(event) {
		var descriptionInputLocalized = Liferay.component('<portlet:namespace />descriptionMapAsXML');
		var metaDescriptionInputLocalized = Liferay.component('<portlet:namespace />metaDescriptionMapAsXML');
		var metaKeywordsInputLocalized = Liferay.component('<portlet:namespace />metaKeywordsMapAsXML');
		var metaTitleInputLocalized = Liferay.component('<portlet:namespace />metaTitleMapAsXML');
		var shortDescriptionInputLocalized = Liferay.component('<portlet:namespace />shortDescriptionMapAsXML');
		var titleInputLocalized = Liferay.component('<portlet:namespace />titleMapAsXML');
		var urlTitleInputLocalized = Liferay.component('<portlet:namespace />urlTitleMapAsXML');

		var editingLocale = event.newVal;
		var items = descriptionInputLocalized.get('items');
		var selectedIndex = items.indexOf(editingLocale);

		descriptionInputLocalized.set('selected', selectedIndex);
		descriptionInputLocalized.selectFlag(editingLocale);

		metaDescriptionInputLocalized.set('selected', selectedIndex);
		metaDescriptionInputLocalized.selectFlag(editingLocale);

		metaKeywordsInputLocalized.set('selected', selectedIndex);
		metaKeywordsInputLocalized.selectFlag(editingLocale);

		metaTitleInputLocalized.set('selected', selectedIndex);
		metaTitleInputLocalized.selectFlag(editingLocale);

		shortDescriptionInputLocalized.set('selected', selectedIndex);
		shortDescriptionInputLocalized.selectFlag(editingLocale);

		titleInputLocalized.set('selected', selectedIndex);
		titleInputLocalized.selectFlag(editingLocale);

		urlTitleInputLocalized.set('selected', selectedIndex);
		urlTitleInputLocalized.selectFlag(editingLocale);
	}

	var translationManager = Liferay.component('<portlet:namespace />translationManager');

	if (translationManager) {
		translationManager.on('deleteAvailableLocale', afterDeletingAvailableLocale);
		translationManager.on('editingLocaleChange', afterEditingLocaleChange);
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

<c:if test="<%= cpDefinition == null %>">
	<aui:script sandbox="<%= true %>" use="aui-base">
		var form = $(document.<portlet:namespace />fm);

		var titleInput = form.fm('titleMapAsXML');
		var urlInput = form.fm('urlTitleMapAsXML');
		var urlTitleInputLocalized = Liferay.component('<portlet:namespace />urlTitleMapAsXML');

		var onTitleInput = _.debounce(
			function(event) {
				urlInput.val(titleInput.val());

				urlTitleInputLocalized.updateInputLanguage(titleInput.val());
			},
			200
		);

		titleInput.on('input', onTitleInput);
	</aui:script>
</c:if>