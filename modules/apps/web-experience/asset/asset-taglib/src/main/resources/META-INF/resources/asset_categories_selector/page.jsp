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

<%@ include file="/asset_categories_selector/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_ui_asset_categories_selector_page") + StringPool.UNDERLINE;

String className = (String)request.getAttribute("liferay-asset:asset-categories-selector:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-asset:asset-categories-selector:classPK"));
long classTypePK = GetterUtil.getLong((String)request.getAttribute("liferay-asset:asset-categories-selector:classTypePK"));
String curCategoryIds = GetterUtil.getString((String)request.getAttribute("liferay-asset:asset-categories-selector:curCategoryIds"), "");
long[] groupIds = (long[])request.getAttribute("liferay-asset:asset-categories-selector:groupIds");
String hiddenInput = (String)request.getAttribute("liferay-asset:asset-categories-selector:hiddenInput");
boolean ignoreRequestValue = GetterUtil.getBoolean(request.getAttribute("liferay-asset:asset-categories-selector:ignoreRequestValue"));
PortletURL portletURL = (PortletURL)request.getAttribute("liferay-asset:asset-categories-selector:portletURL");
boolean showRequiredLabel = GetterUtil.getBoolean((String)request.getAttribute("liferay-asset:asset-categories-selector:showRequiredLabel"), true);

int maxEntries = GetterUtil.getInteger(PropsUtil.get(PropsKeys.ASSET_CATEGORIES_SELECTOR_MAX_ENTRIES));

List<AssetVocabulary> vocabularies = AssetVocabularyServiceUtil.getGroupVocabularies(groupIds);
%>

<c:choose>
	<c:when test="<%= Validator.isNotNull(className) %>">

		<%
		vocabularies = AssetUtil.filterVocabularies(vocabularies, className, classTypePK);

		for (AssetVocabulary vocabulary : vocabularies) {
			vocabulary = vocabulary.toEscapedModel();

			if (AssetCategoryServiceUtil.getVocabularyCategoriesCount(vocabulary.getGroupId(), vocabulary.getVocabularyId()) == 0) {
				continue;
			}

			String curCategoryNames = StringPool.BLANK;

			if (Validator.isNotNull(className) && (classPK > 0)) {
				List<AssetCategory> categories = AssetCategoryServiceUtil.getCategories(className, classPK);

				curCategoryIds = ListUtil.toString(categories, AssetCategory.CATEGORY_ID_ACCESSOR);
				curCategoryNames = ListUtil.toString(categories, AssetCategory.NAME_ACCESSOR);
			}

			if (!ignoreRequestValue) {
				String curCategoryIdsParam = request.getParameter(hiddenInput + StringPool.UNDERLINE + vocabulary.getVocabularyId());

				if (Validator.isNotNull(curCategoryIdsParam)) {
					curCategoryIds = curCategoryIdsParam;
				}
			}

			String[] categoryIdsTitles = AssetCategoryUtil.getCategoryIdsTitles(curCategoryIds, curCategoryNames, vocabulary.getVocabularyId(), themeDisplay);
		%>

			<span class="field-content">
				<label id="<%= namespace %>assetCategoriesLabel_<%= vocabulary.getVocabularyId() %>">
					<%= vocabulary.getTitle(locale) %>

					<c:if test="<%= vocabulary.getGroupId() != themeDisplay.getSiteGroupId() %>">

						<%
						Group vocabularyGroup = GroupLocalServiceUtil.getGroup(vocabulary.getGroupId());
						%>

						(<%= vocabularyGroup.getDescriptiveName(locale) %>)
					</c:if>

					<c:if test="<%= vocabulary.isRequired(PortalUtil.getClassNameId(className), classTypePK) && showRequiredLabel %>">
						<span class="icon-asterisk text-warning">
							<span class="hide-accessible"><liferay-ui:message key="required" /></span>
						</span>
					</c:if>
				</label>

				<div class="lfr-tags-selector-content" data-vocabulary-id="<%= vocabulary.getVocabularyId() %>" id="<%= namespace + randomNamespace %>assetCategoriesSelector_<%= vocabulary.getVocabularyId() %>">
					<aui:input name="<%= hiddenInput + StringPool.UNDERLINE + vocabulary.getVocabularyId() %>" type="hidden" />
				</div>
			</span>

			<aui:script use="liferay-asset-taglib-categories-selector">
				new Liferay.AssetTaglibCategoriesSelector(
					{
						contentBox: '#<%= namespace + randomNamespace %>assetCategoriesSelector_<%= vocabulary.getVocabularyId() %>',
						curEntries: '<%= HtmlUtil.escapeJS(categoryIdsTitles[1]) %>',
						curEntryIds: '<%= categoryIdsTitles[0] %>',
						hiddenInput: '#<%= namespace + hiddenInput + StringPool.UNDERLINE + vocabulary.getVocabularyId() %>',
						instanceVar: '<%= namespace + randomNamespace %>',
						labelNode: '#<%= namespace %>assetCategoriesLabel_<%= vocabulary.getVocabularyId() %>',
						maxEntries: <%= maxEntries %>,
						moreResultsLabel: '<liferay-ui:message key="load-more-results" />',

						<c:if test="<%= portletURL != null %>">
							portletURL: '<%= portletURL.toString() %>',
						</c:if>

						singleSelect: <%= !vocabulary.isMultiValued() %>,
						title: '<liferay-ui:message arguments="<%= vocabulary.getTitle(locale) %>" key="select-x" translateArguments="<%= false %>" />',
						vocabularyGroupIds: '<%= StringUtil.merge(groupIds) %>',
						vocabularyIds: '<%= String.valueOf(vocabulary.getVocabularyId()) %>'
					}
				).render();
			</aui:script>

		<%
		}
		%>

	</c:when>
	<c:otherwise>

		<%
		if (!ignoreRequestValue) {
			String curCategoryIdsParam = request.getParameter(hiddenInput);

			if (curCategoryIdsParam != null) {
				curCategoryIds = curCategoryIdsParam;
			}
		}

		String[] categoryIdsTitles = AssetCategoryUtil.getCategoryIdsTitles(curCategoryIds, StringPool.BLANK, 0, themeDisplay);
		%>

		<div class="lfr-tags-selector-content" id="<%= namespace + randomNamespace %>assetCategoriesSelector">
			<aui:input name="<%= hiddenInput %>" type="hidden" />
		</div>

		<aui:script use="liferay-asset-taglib-categories-selector">
			new Liferay.AssetTaglibCategoriesSelector(
				{
					contentBox: '#<%= namespace + randomNamespace %>assetCategoriesSelector',
					curEntries: '<%= HtmlUtil.escapeJS(categoryIdsTitles[1]) %>',
					curEntryIds: '<%= categoryIdsTitles[0] %>',
					hiddenInput: '#<%= namespace + hiddenInput %>',
					instanceVar: '<%= namespace + randomNamespace %>',
					maxEntries: <%= maxEntries %>,
					moreResultsLabel: '<liferay-ui:message key="load-more-results" />',
					namespace: '<%= namespace %>',

					<c:if test="<%= portletURL != null %>">
						portletURL: '<%= portletURL.toString() %>',
					</c:if>

					vocabularyGroupIds: '<%= StringUtil.merge(groupIds) %>',
					vocabularyIds: '<%= ListUtil.toString(vocabularies, "vocabularyId") %>'
				}
			).render();
		</aui:script>
	</c:otherwise>
</c:choose>