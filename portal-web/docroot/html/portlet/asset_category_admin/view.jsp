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

<%@ include file="/html/portlet/asset_category_admin/init.jsp" %>

<%
String keywords = ParamUtil.getString(request, "keywords");

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "vocabularies"), null);
%>

<aui:form name="fm">
	<aui:input name="deleteVocabularyIds" type="hidden" />

	<aui:nav-bar>
		<aui:nav cssClass="navbar-nav">
			<c:if test="<%= AssetPermission.contains(permissionChecker, themeDisplay.getSiteGroupId(), ActionKeys.ADD_VOCABULARY) %>">
				<portlet:renderURL var="addVocabularyURL">
					<portlet:param name="struts_action" value="/asset_category_admin/edit_vocabulary" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
				</portlet:renderURL>

				<aui:nav-item href="<%= addVocabularyURL %>" iconCssClass="icon-plus" label="add-vocabulary" />
			</c:if>

			<c:if test="<%= AssetPermission.contains(permissionChecker, themeDisplay.getSiteGroupId(), ActionKeys.PERMISSIONS) && GroupPermissionUtil.contains(permissionChecker, themeDisplay.getSiteGroupId(), ActionKeys.PERMISSIONS) %>">
				<liferay-security:permissionsURL
					modelResource="com.liferay.portlet.asset"
					modelResourceDescription="<%= themeDisplay.getScopeGroupName() %>"
					resourcePrimKey="<%= String.valueOf(themeDisplay.getSiteGroupId()) %>"
					var="permissionsURL"
					windowState="<%= LiferayWindowState.POP_UP.toString() %>"
				/>

				<aui:nav-item href="<%= permissionsURL %>" iconCssClass="icon-lock" label="permissions" useDialog="<%= true %>" />
			</c:if>

			<aui:nav-item cssClass="hide" dropdown="<%= true %>" id="vocabulariesActionsButton" label="actions">
				<aui:nav-item cssClass="item-remove" iconCssClass="icon-remove" id="deleteSelectedVocabularies" label="delete" />
			</aui:nav-item>
		</aui:nav>

		<aui:nav-bar-search>
			<div class="form-search">
				<liferay-ui:input-search />
			</div>
		</aui:nav-bar-search>
	</aui:nav-bar>

	<liferay-ui:breadcrumb
		showCurrentGroup="<%= false %>"
		showGuestGroup="<%= false %>"
		showLayout="<%= false %>"
		showParentGroups="<%= false %>"
	/>

	<liferay-ui:search-container
		emptyResultsMessage="there-are-no-vocabularies"
		rowChecker="<%= new RowChecker(renderResponse) %>"
	>

		<%
		List<AssetVocabulary> vocabularies = null;

		if (Validator.isNotNull(keywords)) {
			AssetVocabularyDisplay assetVocabularyDisplay = AssetVocabularyServiceUtil.searchVocabulariesDisplay(scopeGroupId, keywords, searchContainer.getStart(), searchContainer.getEnd(), true);

			vocabularies = assetVocabularyDisplay.getVocabularies();
		}
		else {
			vocabularies = AssetVocabularyServiceUtil.getGroupVocabularies(scopeGroupId, true, searchContainer.getStart(), searchContainer.getEnd(), null);
		}
		%>

		<liferay-ui:search-container-results
			results="<%= vocabularies %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portlet.asset.model.AssetVocabulary"
			keyProperty="vocabularyId"
			modelVar="vocabulary"
		>
			<portlet:renderURL var="rowURL">
				<portlet:param name="struts_action" value="/asset_category_admin/view_categories" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
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
							AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassNameId(classNameId);

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
				cssClass="entry-action"
				path="/html/portlet/asset_category_admin/vocabulary_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<aui:script use="aui-base,liferay-util-list-fields">
	A.one('#<portlet:namespace /><%= searchContainerReference.getId() %>SearchContainer').delegate(
		'click',
		function() {
			var hide = (Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>').length == 0);

			A.one('#<portlet:namespace />vocabulariesActionsButton').toggle(!hide);
		},
		'input[type=checkbox]'
	);

	A.one('#<portlet:namespace />deleteSelectedVocabularies').on(
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				<portlet:actionURL var="deleteURL">
					<portlet:param name="struts_action" value="/asset_category_admin/edit_vocabulary" />
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
				</portlet:actionURL>

				document.<portlet:namespace />fm.<portlet:namespace />deleteVocabularyIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');

				submitForm(document.<portlet:namespace />fm, '<%= deleteURL %>');
			}
		}
	);
</aui:script>