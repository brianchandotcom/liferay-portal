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

SearchContainer<JournalArticle> journalArticleSearchContainer = cpDefinitionVirtualSettingDisplayContext.getJournalArticleSearchContainer();

boolean termsOfUseRequired = BeanParamUtil.getBoolean(cpDefinitionVirtualSetting, request, "termsOfUseRequired");
%>

<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="terms-of-use" />

<aui:model-context bean="<%= cpDefinitionVirtualSetting %>" model="<%= CPDefinitionVirtualSetting.class %>" />

<liferay-util:buffer var="removeJournalArticleIcon">
	<liferay-ui:icon
		icon="times"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<div class="lfr-definition-virtual-setting-terms-of-use-header">
	<aui:fieldset>
		<aui:input name="termsOfUseRequired" />
	</aui:fieldset>
</div>

<div class="lfr-definition-virtual-setting-terms-of-use-content toggler-content-collapsed">
	<aui:fieldset>

		<%
		boolean insertText = true;

		if ((cpDefinitionVirtualSetting != null) && Validator.isNull(cpDefinitionVirtualSetting.getTermsOfUseContentMap())) {
			insertText = false;
		}
		%>

		<aui:input checked="<%= insertText %>" cssClass="lfr-definition-virtual-setting-terms-of-use-type" label="insert-text" name="insertText" type="radio" />

		<%
		String wrapperCssClass = "lfr-definition-virtual-setting-terms-of-use-value ";

		if ((cpDefinitionVirtualSetting != null) && (cpDefinitionVirtualSetting.getTermsOfUseContentMap() == null)) {
		    wrapperCssClass += "hidden";
		}
		%>

		<aui:field-wrapper cssClass="<%= wrapperCssClass %>">
			<div class="entry-content form-group">
				<liferay-ui:input-localized
					cssClass="form-control"
					editorName="alloyeditor"
					name="termsOfUseContent"
					type="editor"
					xml='<%= BeanPropertiesUtil.getString(cpDefinitionVirtualSetting, "termsOfUseContent") %>'
				/>
			</div>
		</aui:field-wrapper>
	</aui:fieldset>

	<aui:fieldset>

		<%
		boolean selectWebContent = false;

		String articleContainerCssClass = "hidden lfr-definition-virtual-setting-terms-of-use-value";

		if((cpDefinitionVirtualSetting != null) && Validator.isNotNull(cpDefinitionVirtualSetting.getTermsOfUseJournalArticleResourcePK())){
			selectWebContent = true;

			articleContainerCssClass = "lfr-definition-virtual-setting-terms-of-use-value";
		}
		%>

		<aui:input checked="<%= selectWebContent %>" cssClass="lfr-definition-virtual-setting-terms-of-use-type" label="select-web-content" name="selectWebContent" type="radio" />

		<div class="<%= articleContainerCssClass %>">
			<liferay-ui:search-container
				cssClass="lfr-definition-virtual-setting-journal-article"
				curParam="curJournalArticle"
				headerNames="title,null"
				id="journalArticleSearchContainer"
				iteratorURL="<%= currentURLObj %>"
				searchContainer="<%= journalArticleSearchContainer %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.journal.model.JournalArticle"
					keyProperty="id"
					modelVar="journalArticle"
				>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="title"
					>
						<liferay-ui:icon
							iconCssClass="icon-ok-sign"
							label="<%= true %>"
							message="<%= HtmlUtil.escape(journalArticle.getTitle(languageId)) %>"
						/>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text>
						<a class="modify-journal-article-link" data-rowId="<%= journalArticle.getResourcePrimKey() %>" href="javascript:;"><%= removeJournalArticleIcon %></a>
					</liferay-ui:search-container-column-text>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator markupView="lexicon" searchContainer="<%= journalArticleSearchContainer %>" />
			</liferay-ui:search-container>

			<%
			String cssClass = "modify-journal-article-link ";

			if (journalArticleSearchContainer.hasResults()) {
				cssClass += "hidden";
			}
			%>

			<aui:button cssClass="<%= cssClass %>" name="selectArticle" value="select-web-content" />
		</div>
	</aui:fieldset>
</div>

<aui:script sandbox="<%= true %>">
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
					uri: '<%= cpDefinitionVirtualSettingDisplayContext.getAssetBrowserURL() %>'
				},
				function(event) {
					$('#<portlet:namespace />termsOfUseJournalArticleResourcePK').val(event.assetclasspk);

					var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />journalArticleSearchContainer');

					var rowColumns = [];

					rowColumns.push(event.assettitle);

					rowColumns.push('<a class="modify-journal-article-link" data-rowId="' + event.assetclasspk + '" href="javascript:;"><%= UnicodeFormatter.toString(removeJournalArticleIcon) %></a>');

					searchContainer.addRow(rowColumns, event.assetclasspk);

					searchContainer.updateDataStore();

					$('#<portlet:namespace />selectArticle').addClass('hidden');
				}
			);
		}
	);
</aui:script>

<aui:script use="aui-toggler">
	var container = A.one('#<portlet:namespace />fileEntryContainer');

	var termsOfUseTypes = container.all('.lfr-definition-virtual-setting-terms-of-use-type');
	var termsOfUseValues = container.all('.lfr-definition-virtual-setting-terms-of-use-value');

	var selectTermsOfUseType = function(index) {
		termsOfUseTypes.attr('checked', false);

		termsOfUseTypes.item(index).attr('checked', true);

		termsOfUseValues.addClass('hidden');

		termsOfUseValues.item(index).removeClass('hidden');
	};

	container.delegate(
		'change',
		function(event) {
			var index = termsOfUseTypes.indexOf(event.currentTarget);

			selectTermsOfUseType(index);
		},
		'.lfr-definition-virtual-setting-terms-of-use-type'
	);

	new A.Toggler(
		{
			animated: true,
			content: '#<portlet:namespace />fileEntryContainer .lfr-definition-virtual-setting-terms-of-use-content',
			expanded: <%= termsOfUseRequired %>,
			header: '#<portlet:namespace />fileEntryContainer .lfr-definition-virtual-setting-terms-of-use-header',
			on: {
				animatingChange: function(event) {
					var instance = this;

					var expanded = !instance.get('expanded');

					A.one('#<portlet:namespace />termsOfUseRequired').attr('checked', expanded);

					if (expanded) {
						termsOfUseTypes.each(
							function(item, index) {
								if (item.get('checked')) {
									termsOfUseValues.item(index).removeClass('hidden');
								}
							}
						);
					}
					else {
						termsOfUseValues.addClass('hidden');
					}
				}
			}
		}
	);
</aui:script>

<aui:script use="liferay-search-container">
	var Util = Liferay.Util;

	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />journalArticleSearchContainer');

	var searchContainerContentBox = searchContainer.get('contentBox');

	searchContainerContentBox.delegate(
		'click',
		function(event) {
			var link = event.currentTarget;

			var rowId = link.attr('data-rowId');

			var tr = link.ancestor('tr');

			searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));

			searchContainer.updateDataStore();

			$('#<portlet:namespace />selectArticle').removeClass('hidden');
		},
		'.modify-journal-article-link'
	);
</aui:script>