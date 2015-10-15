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

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "vocabularies"), null);
%>

<liferay-portlet:renderURL varImpl="portletURL" />

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item cssClass="active" label="vocabularies" />
	</aui:nav>

	<aui:nav-bar-search>
		<aui:form action="<%= portletURL %>" name="searchFm">
			<liferay-ui:input-search markupView="lexicon" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<liferay-frontend:management-bar
	checkBoxContainerId="assetVocabulariesSearchContainer"
	includeCheckBox="<%= true %>"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-action-buttons>
		<aui:a cssClass="btn" href="javascript:;" iconCssClass="icon-trash" id="deleteSelectedVocabularies" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<aui:form cssClass="container-fluid-1280" name="fm">
	<aui:input name="deleteVocabularyIds" type="hidden" />

	<liferay-ui:breadcrumb
		showCurrentGroup="<%= false %>"
		showGuestGroup="<%= false %>"
		showLayout="<%= false %>"
		showParentGroups="<%= false %>"
	/>

	<liferay-ui:search-container
		emptyResultsMessage="there-are-no-vocabularies"
		id="assetVocabularies"
		rowChecker="<%= new EmptyOnClickRowChecker(renderResponse) %>"
	>

		<liferay-ui:search-container-results>

			<%
			String keywords = ParamUtil.getString(request, "keywords");

			List<AssetVocabulary> vocabularies = null;

			if (Validator.isNotNull(keywords)) {
				AssetVocabularyDisplay assetVocabularyDisplay = AssetVocabularyServiceUtil.searchVocabulariesDisplay(scopeGroupId, keywords, searchContainer.getStart(), searchContainer.getEnd(), true);

				total = assetVocabularyDisplay.getTotal();

				searchContainer.setTotal(total);

				vocabularies = assetVocabularyDisplay.getVocabularies();
			}
			else {
				total = AssetVocabularyServiceUtil.getGroupVocabulariesCount(scopeGroupId);

				searchContainer.setTotal(total);

				vocabularies = AssetVocabularyServiceUtil.getGroupVocabularies(scopeGroupId, true, searchContainer.getStart(), searchContainer.getEnd(), null);
			}

			searchContainer.setResults(vocabularies);
			%>

		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portlet.asset.model.AssetVocabulary"
			keyProperty="vocabularyId"
			modelVar="vocabulary"
		>
			<portlet:renderURL var="rowURL">
				<portlet:param name="mvcPath" value="/view_categories.jsp" />
				<portlet:param name="vocabularyId" value="<%= String.valueOf(vocabulary.getVocabularyId()) %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-column-text
				href="<%= (AssetCategoryServiceUtil.getVocabularyCategoriesCount(scopeGroupId, vocabulary.getVocabularyId()) > 0) ? rowURL : null %>"
				name="vocabulary"
				value="<%= HtmlUtil.escape(vocabulary.getTitle(locale)) %>"
			/>

			<liferay-ui:search-container-column-text
				name="description"
				value="<%= vocabulary.getDescription(locale) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="text-left"
				name="number-of-categories"
			>
				<span class="badge">
					<%= String.valueOf(vocabulary.getCategoriesCount()) %>
				</span>
			</liferay-ui:search-container-column-text>

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
				cssClass="checkbox-cell entry-action"
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