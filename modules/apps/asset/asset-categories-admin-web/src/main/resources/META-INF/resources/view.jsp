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
String displayStyle = ParamUtil.getString(request, "displayStyle", "list");

String keywords = ParamUtil.getString(request, "keywords");

PortletURL iteratorURL = renderResponse.createRenderURL();

SearchContainer vocabulariesSearchContainer = new SearchContainer(renderRequest, iteratorURL, null, "there-are-no-vocabularies.-you-can-add-a-vocabulary-by-clicking-the-plus-button-on-the-bottom-right-corner");

vocabulariesSearchContainer.setRowChecker(new EmptyOnClickRowChecker(renderResponse));

List<AssetVocabulary> vocabularies = null;
int vocabulariesCount = 0;

if (Validator.isNotNull(keywords)) {
	AssetVocabularyDisplay assetVocabularyDisplay = AssetVocabularyServiceUtil.searchVocabulariesDisplay(scopeGroupId, keywords, vocabulariesSearchContainer.getStart(), vocabulariesSearchContainer.getEnd(), true);

	vocabulariesCount = assetVocabularyDisplay.getTotal();

	vocabulariesSearchContainer.setTotal(vocabulariesCount);

	vocabularies = assetVocabularyDisplay.getVocabularies();
}
else {
	vocabulariesCount = AssetVocabularyServiceUtil.getGroupVocabulariesCount(scopeGroupId);

	vocabulariesSearchContainer.setTotal(vocabulariesCount);

	vocabularies = AssetVocabularyServiceUtil.getGroupVocabularies(scopeGroupId, true, vocabulariesSearchContainer.getStart(), vocabulariesSearchContainer.getEnd(), null);
}

vocabulariesSearchContainer.setResults(vocabularies);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "vocabularies"), null);
%>

<liferay-portlet:renderURL varImpl="portletURL" />

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item cssClass="active" label="vocabularies" selected="<%= true %>" />
	</aui:nav>

	<c:if test="<%= Validator.isNotNull(keywords) || (vocabulariesCount > 0) %>">
		<aui:nav-bar-search>
			<aui:form action="<%= portletURL %>" name="searchFm">
				<liferay-ui:input-search markupView="lexicon" />
			</aui:form>
		</aui:nav-bar-search>
	</c:if>
</aui:nav-bar>

<c:if test="<%= Validator.isNotNull(keywords) || (vocabulariesCount > 0) %>">
	<liferay-frontend:management-bar
		checkBoxContainerId="assetVocabulariesSearchContainer"
		includeCheckBox="<%= true %>"
	>
		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-filters>
				<liferay-frontend:management-bar-navigation
					navigationKeys='<%= new String[] {"all"} %>'
					portletURL="<%= renderResponse.createRenderURL() %>"
				/>
			</liferay-frontend:management-bar-filters>
	
			<liferay-frontend:management-bar-display-buttons
				displayViews='<%= new String[] {"list"} %>'
				portletURL="<%= portletURL %>"
				selectedDisplayStyle="<%= displayStyle %>"
			/>
		</liferay-frontend:management-bar-buttons>

		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button href="javascript:;" iconCssClass="icon-trash" id="deleteSelectedVocabularies" />
		</liferay-frontend:management-bar-action-buttons>
	</liferay-frontend:management-bar>
</c:if>

<aui:form cssClass="container-fluid-1280" name="fm">
	<aui:input name="deleteVocabularyIds" type="hidden" />

	<liferay-ui:breadcrumb
		showCurrentGroup="<%= false %>"
		showGuestGroup="<%= false %>"
		showLayout="<%= false %>"
		showParentGroups="<%= false %>"
	/>

	<liferay-ui:search-container
		id="assetVocabularies"
		searchContainer="<%= vocabulariesSearchContainer %>"
	>

		<liferay-ui:search-container-row
			className="com.liferay.portlet.asset.model.AssetVocabulary"
			cssClass="selectable"
			keyProperty="vocabularyId"
			modelVar="vocabulary"
		>
			<portlet:renderURL var="rowURL">
				<portlet:param name="mvcPath" value="/view_categories.jsp" />
				<portlet:param name="vocabularyId" value="<%= String.valueOf(vocabulary.getVocabularyId()) %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-column-text
				href="<%= (AssetCategoryServiceUtil.getVocabularyCategoriesCount(scopeGroupId, vocabulary.getVocabularyId()) > 0) ? rowURL : null %>"
				name="name"
				value="<%= HtmlUtil.escape(vocabulary.getTitle(locale)) %>"
			/>

			<liferay-ui:search-container-column-text
				name="description"
				value="<%= vocabulary.getDescription(locale) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="text-left"
				name="number-of-categories"
				value="<%= String.valueOf(vocabulary.getCategoriesCount()) %>"
			/>

			<liferay-ui:search-container-column-text
				name="asset-type"
			>

				<%
				long[] selectedClassNameIds = vocabulary.getSelectedClassNameIds();
				long[] selectedClassTypePKs = vocabulary.getSelectedClassTypePKs();

				for (int i = 0; i < selectedClassNameIds.length; i++) {
					long classNameId = selectedClassNameIds[i];
					long classTypePK = selectedClassTypePKs[i];

					String name = LanguageUtil.get(request, "all-asset-types");

					if (classNameId != AssetCategoryConstants.ALL_CLASS_NAME_ID) {
						if (classTypePK != -1) {
							AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassNameId(classNameId);

							ClassTypeReader classTypeReader = assetRendererFactory.getClassTypeReader();

							ClassType classType = classTypeReader.getClassType(classTypePK, locale);

							name = classType.getName();
						}
						else {
							name = ResourceActionsUtil.getModelResource(locale, PortalUtil.getClassName(classNameId));
						}
					}

					StringBundler sb = new StringBundler();

					sb.append(name);

					if (vocabulary.isRequired(classNameId, classTypePK)) {
						sb.append(StringPool.SPACE);
						sb.append(StringPool.STAR);
					}

					if ((i + 1) < selectedClassNameIds.length) {
						sb.append(StringPool.COMMA);
					}
				%>

					<%= sb.toString() %>

				<%
				}
				%>

			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-jsp
				cssClass="list-group-item-field"
				path="/vocabulary_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<c:if test="<%= AssetPermission.contains(permissionChecker, themeDisplay.getSiteGroupId(), ActionKeys.ADD_VOCABULARY) %>">
	<portlet:renderURL var="addVocabularyURL">
		<portlet:param name="mvcPath" value="/edit_vocabulary.jsp" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-vocabulary") %>' url="<%= addVocabularyURL.toString() %>" />
	</liferay-frontend:add-menu>
</c:if>

<aui:script sandbox="<%= true %>">
	var Util = Liferay.Util;

	var form = $(document.<portlet:namespace />fm);

	$('#<portlet:namespace />deleteSelectedVocabularies').on(
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				<portlet:actionURL name="deleteVocabulary" var="deleteVocabularyURL">
					<portlet:param name="redirect" value="<%= currentURL %>" />
				</portlet:actionURL>

				form.fm('deleteVocabularyIds').val(Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

				submitForm(form, '<%= deleteVocabularyURL %>');
			}
		}
	);
</aui:script>