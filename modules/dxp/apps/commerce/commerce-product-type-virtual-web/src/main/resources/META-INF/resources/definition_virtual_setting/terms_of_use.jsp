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
CPDefinitionVirtualSettingDisplayContext cpDefinitionVirtualSettingDisplayContext = (CPDefinitionVirtualSettingDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinitionVirtualSetting cpDefinitionVirtualSetting = cpDefinitionVirtualSettingDisplayContext.getCPDefinitionVirtualSetting();

JournalArticle journalArticle = cpDefinitionVirtualSettingDisplayContext.getJournalArticle();

long termsOfUseJournalArticleResourcePrimKey = BeanParamUtil.getLong(cpDefinitionVirtualSetting, request, "termsOfUseJournalArticleResourcePrimKey");

boolean useTermsOfUseJournal = false;

if (termsOfUseJournalArticleResourcePrimKey > 0) {
	useTermsOfUseJournal = true;
}
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="terms-of-use"
/>

<aui:model-context bean="<%= cpDefinitionVirtualSetting %>" model="<%= CPDefinitionVirtualSetting.class %>" />

<liferay-ui:error exception="<%= CPDefinitionVirtualSettingTermsOfUseArticleResourcePKException.class %>" message="please-select-an-existing-web-content" />
<liferay-ui:error exception="<%= CPDefinitionVirtualSettingTermsOfUseContentException.class %>" message="please-enter-terms-of-use-content" />
<liferay-ui:error exception="<%= CPDefinitionVirtualSettingTermsOfUseException.class %>" message="please-select-an-existing-web-content-or-enter-terms-of-use-content" />

<aui:fieldset>
	<aui:input label="enable-terms-of-use" name="termsOfUseRequired" />
</aui:fieldset>

<div class="col-md-3">
	<h4 class="text-default"><liferay-ui:message key="select-existing-content-or-add-terms-of-use-in-the-space-below" /></h4>
</div>

<div class="col-md-9">
	<aui:fieldset>
		<p class="text-default">
			<span class="<%= (termsOfUseJournalArticleResourcePrimKey > 0) ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />journalArticleRemove" role="button">
				<aui:icon cssClass="icon-monospaced" image="times" markupView="lexicon" />
			</span>
			<span id="<portlet:namespace />journalArticleNameInput">
				<c:choose>
					<c:when test="<%= (journalArticle != null) %>">
						<%= journalArticle.getTitle() %>
					</c:when>
					<c:otherwise>
						<span class="text-muted"><liferay-ui:message key="none" /></span>
					</c:otherwise>
				</c:choose>
			</span>
		</p>

		<aui:button name="selectArticle" value="select-web-content" />

		<aui:field-wrapper cssClass="lfr-definition-virtual-setting-content">
			<h4 class="text-default"><liferay-ui:message key="or" /></h4>

			<div class="entry-content form-group">
				<liferay-ui:input-localized
					cssClass="form-control"
					disabled="<%= useTermsOfUseJournal %>"
					editorName="alloyeditor"
					name="termsOfUseContent"
					type="editor"
					xml='<%= BeanPropertiesUtil.getString(cpDefinitionVirtualSetting, "termsOfUseContent") %>'
				/>
			</div>
		</aui:field-wrapper>
	</aui:fieldset>
</div>

<aui:script sandbox="<%= true %>">
	var journalArticleRemove = $('#<portlet:namespace />journalArticleRemove');
	var journalArticleNameInput = $('#<portlet:namespace />journalArticleNameInput');

	$('#<portlet:namespace />selectArticle').on(
		'click',
		function(event) {
			event.preventDefault();

			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						destroyOnHide: true,
						modal: true
					},
					eventName: 'selectJournalArticle',
					id: '',
					title: '<liferay-ui:message key="select-web-content" />',
					uri: '<%= cpDefinitionVirtualSettingDisplayContext.getTermsOfUseJournalArticleBrowserURL() %>'
				},
				function(event) {
					$('#<portlet:namespace />termsOfUseJournalArticleResourcePrimKey').val(event.assetclasspk);

					$('#<portlet:namespace />termsOfUseContent').attr('disabled', true);

					journalArticleRemove.removeClass('hide');

					journalArticleNameInput.html(event.assettitle);
				}
			);
		}
	);

	$('#<portlet:namespace />journalArticleRemove').on(
		'click',
		function(event) {
			event.preventDefault();

			var contentCheckbox = $('#<portlet:namespace />termsOfUseRequired');

			if (contentCheckbox.attr('checked')) {
				$('#<portlet:namespace />termsOfUseContent').attr('disabled', false);
			}

			$('#<portlet:namespace />termsOfUseJournalArticleResourcePrimKey').val(0);

			journalArticleNameInput.html('<liferay-ui:message key="none" />');

			journalArticleRemove.addClass('hide');
		}
	);
</aui:script>

<aui:script>
	AUI().ready(
		'node', 'event',
		function(A) {
			selectContentType(A);

			A.one('#<portlet:namespace />termsOfUseRequired').on(
				'click',
				function(b) {
					selectContentType(A);
				}
			);
		}
	);

	function selectContentType(A) {
		var contentCheckbox = A.one('#<portlet:namespace />termsOfUseRequired');

		var isContentSelected = A.one('#<portlet:namespace />journalArticleRemove').hasClass('hide');

		if (contentCheckbox.attr('checked')) {
			A.one('#<portlet:namespace />selectArticle').attr('disabled', false);
			A.one('#<portlet:namespace />termsOfUseContent').attr('disabled', !isContentSelected);
		}
		else {
			A.one('#<portlet:namespace />selectArticle').attr('disabled', true);
			A.one('#<portlet:namespace />termsOfUseContent').attr('disabled', true);
		}
	}
</aui:script>