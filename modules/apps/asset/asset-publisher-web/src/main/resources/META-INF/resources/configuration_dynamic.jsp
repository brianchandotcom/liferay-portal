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
List<AssetRendererFactory<?>> classTypesAssetRendererFactories = (List<AssetRendererFactory<?>>)request.getAttribute("configuration.jsp-classTypesAssetRendererFactories");
PortletURL configurationRenderURL = (PortletURL)request.getAttribute("configuration.jsp-configurationRenderURL");
String redirect = (String)request.getAttribute("configuration.jsp-redirect");
String selectScope = (String)request.getAttribute("configuration.jsp-selectScope");
String selectStyle = (String)request.getAttribute("configuration.jsp-selectStyle");
%>

<liferay-ui:tabs
	formName="fm"
	names="asset-selection,display-settings,subscriptions"
	param="tabs2"
	refresh="<%= false %>"
>
	<liferay-ui:section>
		<liferay-ui:error-marker key="errorSection" value="asset-selection" />

		<%= selectStyle %>

		<liferay-ui:panel-container extended="<%= true %>" id="assetPublisherDynamicSelectionAssetSelectionPanelContainer" persistState="<%= true %>">
			<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="assetPublisherSourcePanel" persistState="<%= true %>" title="source">
				<aui:fieldset cssClass='<%= assetPublisherDisplayContext.isShowScopeSelector() ? StringPool.BLANK : "hide" %>' label="scope">
					<%= selectScope %>
				</aui:fieldset>

				<aui:fieldset label="asset-entry-type">

					<%
					Set<Long> availableClassNameIdsSet = SetUtil.fromArray(assetPublisherDisplayContext.getAvailableClassNameIds());

					// Left list

					List<KeyValuePair> typesLeftList = new ArrayList<KeyValuePair>();

					long[] classNameIds = assetPublisherDisplayContext.getClassNameIds();

					for (long classNameId : classNameIds) {
						String className = PortalUtil.getClassName(classNameId);

						typesLeftList.add(new KeyValuePair(String.valueOf(classNameId), ResourceActionsUtil.getModelResource(locale, className)));
					}

					// Right list

					List<KeyValuePair> typesRightList = new ArrayList<KeyValuePair>();

					Arrays.sort(classNameIds);
					%>

					<aui:select label="" name="preferences--anyAssetType--" title="asset-type">
						<aui:option label="any" selected="<%= assetPublisherDisplayContext.isAnyAssetType() %>" value="<%= true %>" />
						<aui:option label='<%= LanguageUtil.get(request, "select-more-than-one") + StringPool.TRIPLE_PERIOD %>' selected="<%= !assetPublisherDisplayContext.isAnyAssetType() && (classNameIds.length > 1) %>" value="<%= false %>" />

						<optgroup label="<liferay-ui:message key="asset-type" />">

							<%
							for (long classNameId : availableClassNameIdsSet) {
								ClassName className = ClassNameLocalServiceUtil.getClassName(classNameId);

								if (Arrays.binarySearch(classNameIds, classNameId) < 0) {
									typesRightList.add(new KeyValuePair(String.valueOf(classNameId), ResourceActionsUtil.getModelResource(locale, className.getValue())));
								}
							%>

								<aui:option label="<%= ResourceActionsUtil.getModelResource(locale, className.getValue()) %>" selected="<%= (classNameIds.length == 1) && (classNameId == classNameIds[0]) %>" value="<%= classNameId %>" />

							<%
							}
							%>

						</optgroup>
					</aui:select>

					<aui:input name="preferences--classNameIds--" type="hidden" />

					<%
					typesRightList = ListUtil.sort(typesRightList, new KeyValuePairComparator(false, true));
					%>

					<div class="<%= assetPublisherDisplayContext.isAnyAssetType() ? "hide" : "" %>" id="<portlet:namespace />classNamesBoxes">
						<liferay-ui:input-move-boxes
							leftBoxName="currentClassNameIds"
							leftList="<%= typesLeftList %>"
							leftReorder="true"
							leftTitle="selected"
							rightBoxName="availableClassNameIds"
							rightList="<%= typesRightList %>"
							rightTitle="available"
						/>
					</div>

					<%
					List<AssetRendererFactory<?>> assetRendererFactories = ListUtil.sort(AssetRendererFactoryRegistryUtil.getAssetRendererFactories(company.getCompanyId()), new AssetRendererFactoryTypeNameComparator(locale));

					for (AssetRendererFactory<?> assetRendererFactory : assetRendererFactories) {
						ClassTypeReader classTypeReader = assetRendererFactory.getClassTypeReader();

						List<ClassType> classTypes = classTypeReader.getAvailableClassTypes(assetPublisherDisplayContext.getReferencedModelsGroupIds(), locale);

						if (classTypes.isEmpty()) {
							continue;
						}

						classTypesAssetRendererFactories.add(assetRendererFactory);

						String className = AssetPublisherUtil.getClassName(assetRendererFactory);

						Long[] assetSelectedClassTypeIds = AssetPublisherUtil.getClassTypeIds(portletPreferences, className, classTypes);

						// Left list

						List<KeyValuePair> subtypesLeftList = new ArrayList<KeyValuePair>();

						for (long subtypeId : assetSelectedClassTypeIds) {
							subtypesLeftList.add(new KeyValuePair(String.valueOf(subtypeId), HtmlUtil.escape(classTypeReader.getClassType(subtypeId, locale).getName())));
						}

						Arrays.sort(assetSelectedClassTypeIds);

						// Right list

						List<KeyValuePair> subtypesRightList = new ArrayList<KeyValuePair>();

						boolean anyAssetSubtype = GetterUtil.getBoolean(portletPreferences.getValue("anyClassType" + className, Boolean.TRUE.toString()));
					%>

						<div class='asset-subtype <%= (assetSelectedClassTypeIds.length < 1) ? StringPool.BLANK : "hide" %>' id="<portlet:namespace /><%= className %>Options">

							<%
							String label = ResourceActionsUtil.getModelResource(locale, assetRendererFactory.getClassName()) + StringPool.SPACE + assetRendererFactory.getSubtypeTitle(themeDisplay.getLocale());
							%>

							<aui:select label="<%= label %>" name='<%= "preferences--anyClassType" + className + "--" %>'>
								<aui:option label="any" selected="<%= anyAssetSubtype %>" value="<%= true %>" />
								<aui:option label='<%= LanguageUtil.get(request, "select-more-than-one") + StringPool.TRIPLE_PERIOD %>' selected="<%= !anyAssetSubtype && (assetSelectedClassTypeIds.length > 1) %>" value="<%= false %>" />

								<optgroup label="<%= assetRendererFactory.getSubtypeTitle(themeDisplay.getLocale()) %>">

									<%
									for (ClassType classType : classTypes) {
										if (Arrays.binarySearch(assetSelectedClassTypeIds, classType.getClassTypeId()) < 0) {
											subtypesRightList.add(new KeyValuePair(String.valueOf(classType.getClassTypeId()), HtmlUtil.escape(classType.getName())));
										}
									%>

										<aui:option label="<%= HtmlUtil.escapeAttribute(classType.getName()) %>" selected="<%= !anyAssetSubtype && (assetSelectedClassTypeIds.length == 1) && ((assetSelectedClassTypeIds[0]).equals(classType.getClassTypeId())) %>" value="<%= classType.getClassTypeId() %>" />

									<%
									}
									%>

								</optgroup>
							</aui:select>

							<aui:input name='<%= "preferences--classTypeIds" + className + "--" %>' type="hidden" />

							<c:if test="<%= assetPublisherDisplayContext.isShowSubtypeFieldsFilter() %>">
								<div class="asset-subtypefields-wrapper-enable hide" id="<portlet:namespace /><%= className %>subtypeFieldsFilterEnableWrapper">
									<aui:input checked="<%= assetPublisherDisplayContext.isSubtypeFieldsFilterEnabled() %>" label="filter-by-field" name='<%= "preferences--subtypeFieldsFilterEnabled" + className + "--" %>' type="checkbox" />
								</div>

								<span class="asset-subtypefields-message" id="<portlet:namespace /><%= className %>ddmStructureFieldMessage">
									<c:if test="<%= Validator.isNotNull(assetPublisherDisplayContext.getDDMStructureFieldLabel()) && (classNameIds[0] == PortalUtil.getClassNameId(assetRendererFactory.getClassName())) %>">
										<%= HtmlUtil.escape(assetPublisherDisplayContext.getDDMStructureFieldLabel()) + ": " + HtmlUtil.escape(assetPublisherDisplayContext.getDDMStructureDisplayFieldValue()) %>
									</c:if>
								</span>

								<div class="asset-subtypefields-wrapper hide" id="<portlet:namespace /><%= className %>subtypeFieldsWrapper">

									<%
									for (ClassType classType: classTypes) {
										if (classType.getClassTypeFieldsCount() == 0) {
											continue;
										}
									%>

										<span class="asset-subtypefields hide" id="<portlet:namespace /><%= classType.getClassTypeId() %>_<%= className %>Options">
											<liferay-portlet:renderURL portletName="<%= assetPublisherDisplayContext.getPortletResource() %>" var="selectStructureFieldURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
												<portlet:param name="mvcPath" value="/select_structure_field.jsp" />
												<portlet:param name="portletResource" value="<%= assetPublisherDisplayContext.getPortletResource() %>" />
												<portlet:param name="className" value="<%= assetRendererFactory.getClassName() %>" />
												<portlet:param name="classTypeId" value="<%= String.valueOf(classType.getClassTypeId()) %>" />
												<portlet:param name="eventName" value='<%= renderResponse.getNamespace() + "selectDDMStructureField" %>' />
											</liferay-portlet:renderURL>

											<span class="asset-subtypefields-popup" id="<portlet:namespace /><%= classType.getClassTypeId() %>_<%= className %>PopUpButton">
												<aui:button data-href="<%= selectStructureFieldURL.toString() %>" disabled="<%= !assetPublisherDisplayContext.isSubtypeFieldsFilterEnabled() %>" value="select" />
											</span>
										</span>

									<%
									}

									typesRightList = ListUtil.sort(typesRightList, new KeyValuePairComparator(false, true));
									%>

								</div>
							</c:if>

							<div class="<%= assetSelectedClassTypeIds.length > 1 ? StringPool.BLANK : "hide" %>" id="<portlet:namespace /><%= className %>Boxes">
								<liferay-ui:input-move-boxes
									leftBoxName='<%= className + "currentClassTypeIds" %>'
									leftList="<%= subtypesLeftList %>"
									leftReorder="true"
									leftTitle="selected"
									rightBoxName='<%= className + "availableClassTypeIds" %>'
									rightList="<%= subtypesRightList %>"
									rightTitle="available"
								/>
							</div>
						</div>

					<%
					}
					%>

					<c:if test="<%= assetPublisherDisplayContext.isShowSubtypeFieldsFilter() %>">
						<div class="asset-subtypefield-selected <%= Validator.isNull(assetPublisherDisplayContext.getDDMStructureFieldName()) ? "hide" : StringPool.BLANK %>">
							<aui:input name='<%= "preferences--ddmStructureFieldName--" %>' type="hidden" value="<%= assetPublisherDisplayContext.getDDMStructureFieldName() %>" />

							<aui:input name='<%= "preferences--ddmStructureFieldValue--" %>' type="hidden" value="<%= assetPublisherDisplayContext.getDDMStructureFieldValue() %>" />

							<aui:input name='<%= "preferences--ddmStructureDisplayFieldValue--" %>' type="hidden" value="<%= assetPublisherDisplayContext.getDDMStructureDisplayFieldValue() %>" />
						</div>
					</c:if>
				</aui:fieldset>
			</liferay-ui:panel>

			<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="assetPublisherQueryRulesPanelContainer" persistState="<%= true %>" title="filter[action]">
				<div id="<portlet:namespace />queryRules">
					<aui:fieldset label="displayed-assets-must-match-these-rules">
						<liferay-ui:asset-tags-error />

						<%
						DuplicateQueryRuleException dqre = null;
						%>

						<liferay-ui:error exception="<%= DuplicateQueryRuleException.class %>">

							<%
							dqre = (DuplicateQueryRuleException)errorException;

							String name = dqre.getName();
							%>

							<liferay-util:buffer var="messageArgument">
								<em>(<liferay-ui:message key='<%= dqre.isContains() ? "contains" : "does-not-contain" %>' /> - <liferay-ui:message key='<%= dqre.isAndOperator() ? "all" : "any" %>' /> - <liferay-ui:message key='<%= name.equals(("assetTags")) ? "tags" : "categories" %>' />)</em>
							</liferay-util:buffer>

							<liferay-ui:message arguments="<%= messageArgument %>" key="only-one-rule-with-the-combination-x-is-supported" translateArguments="<%= false %>" />
						</liferay-ui:error>

						<%
						String queryLogicIndexesParam = ParamUtil.getString(request, "queryLogicIndexes");

						int[] queryLogicIndexes = null;

						if (Validator.isNotNull(queryLogicIndexesParam)) {
							queryLogicIndexes = StringUtil.split(queryLogicIndexesParam, 0);
						}
						else {
							queryLogicIndexes = new int[0];

							for (int i = 0; true; i++) {
								String queryValues = PrefsParamUtil.getString(portletPreferences, request, "queryValues" + i);

								if (Validator.isNull(queryValues)) {
									break;
								}

								queryLogicIndexes = ArrayUtil.append(queryLogicIndexes, i);
							}

							if (queryLogicIndexes.length == 0) {
								queryLogicIndexes = ArrayUtil.append(queryLogicIndexes, -1);
							}
						}

						int index = 0;

						for (int queryLogicIndex : queryLogicIndexes) {
							String queryValues = StringUtil.merge(portletPreferences.getValues("queryValues" + queryLogicIndex, new String[0]));
							String tagNames = ParamUtil.getString(request, "queryTagNames" + queryLogicIndex, queryValues);
							String categoryIds = ParamUtil.getString(request, "queryCategoryIds" + queryLogicIndex, queryValues);

							if (Validator.isNotNull(tagNames) || Validator.isNotNull(categoryIds) || (queryLogicIndexes.length == 1)) {
								request.setAttribute("configuration.jsp-categorizableGroupIds", assetPublisherDisplayContext.getReferencedModelsGroupIds());
								request.setAttribute("configuration.jsp-index", String.valueOf(index));
								request.setAttribute("configuration.jsp-queryLogicIndex", String.valueOf(queryLogicIndex));

								String cssClass = StringPool.BLANK;

								if (dqre != null) {
									boolean queryContains = PrefsParamUtil.getBoolean(portletPreferences, request, "queryContains" + queryLogicIndex, true);
									boolean queryAndOperator = PrefsParamUtil.getBoolean(portletPreferences, request, "queryAndOperator" + queryLogicIndex);
									String queryName = PrefsParamUtil.getString(portletPreferences, request, "queryName" + queryLogicIndex, "assetTags");

									String dqreQueryName = dqre.getName();

									if ((dqre.isContains() == queryContains) && (dqre.isAndOperator() == queryAndOperator) && dqreQueryName.equals(queryName)) {
										cssClass = "asset-query-rule-error";
									}
								}
						%>

								<div class="lfr-form-row <%= cssClass %>">
									<div class="row-fields">
										<liferay-util:include page="/edit_query_rule.jsp" servletContext="<%= application %>" />
									</div>
								</div>

						<%
							}

							index++;
						}
						%>

					</aui:fieldset>
				</div>

				<aui:input label='<%= LanguageUtil.format(request, "show-only-assets-with-x-as-its-display-page", HtmlUtil.escape(layout.getName(locale)), false) %>' name="preferences--showOnlyLayoutAssets--" type="checkbox" value="<%= assetPublisherDisplayContext.isShowOnlyLayoutAssets() %>" />

				<aui:input label="include-tags-specified-in-the-url" name="preferences--mergeUrlTags--" type="checkbox" value="<%= assetPublisherDisplayContext.isMergeURLTags() %>" />

				<aui:script use="liferay-auto-fields">
					var autoFields = new Liferay.AutoFields(
						{
							contentBox: '#<portlet:namespace />queryRules > fieldset',
							fieldIndexes: '<portlet:namespace />queryLogicIndexes',
							namespace: '<portlet:namespace />',
							url: '<liferay-portlet:renderURL portletName="<%= AssetPublisherPortletKeys.ASSET_PUBLISHER %>" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="mvcPath" value="/edit_query_rule.jsp" /><portlet:param name="categorizableGroupIds" value="<%= StringUtil.merge(assetPublisherDisplayContext.getReferencedModelsGroupIds()) %>" /></liferay-portlet:renderURL>'
						}
					).render();
				</aui:script>
			</liferay-ui:panel>

			<%
			List<AssetEntryQueryProcessor> assetEntryQueryProcessors = AssetPublisherUtil.getAssetEntryQueryProcessors();

			for (AssetEntryQueryProcessor assetEntryQueryProcessor : assetEntryQueryProcessors) {
			%>

				<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id='<%= "assetPublisherPanelContainerSection_" + assetEntryQueryProcessor.getKey() %>' persistState="<%= true %>" title="<%= assetEntryQueryProcessor.getTitle(locale) %>">

					<%
					assetEntryQueryProcessor.include(request, new PipingServletResponse(pageContext));
					%>

				</liferay-ui:panel>

			<%
			}
			%>

			<c:if test="<%= assetPublisherDisplayContext.isOrderingAndGroupingEnabled() %>">
				<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="assetPublisherOrderingAndGroupingPanel" persistState="<%= true %>" title="ordering-and-grouping">
					<aui:fieldset>
						<span class="field-row">

							<%
							String orderByColumn1 = assetPublisherDisplayContext.getOrderByColumn1();
							%>

							<aui:select inlineField="<%= true %>" inlineLabel="left" label="order-by" name="preferences--orderByColumn1--" value="<%= orderByColumn1 %>">
								<c:if test="<%= assetPublisherDisplayContext.isOrderingByTitleEnabled() %>">
									<aui:option label="title" />
								</c:if>

								<aui:option label="create-date" value="createDate" />
								<aui:option label="modified-date" value="modifiedDate" />
								<aui:option label="publish-date" value="publishDate" />
								<aui:option label="expiration-date" value="expirationDate" />
								<aui:option label="priority" value="priority" />

								<c:if test="<%= !AssetPublisherWebConfigurationValues.SEARCH_WITH_INDEX %>">
									<aui:option label="view-count" value="viewCount" />
									<aui:option label="ratings" value="ratings" />
								</c:if>
							</aui:select>

							<%
							String orderByType1 = assetPublisherDisplayContext.getOrderByType1();
							%>

							<aui:select inlineField="<%= true %>" label="" name="preferences--orderByType1--" title="order-by-type" value="<%= orderByType1 %>">
								<aui:option label="ascending" value="ASC" />
								<aui:option label="descending" value="DESC" />
							</aui:select>
						</span>

						<span class="field-row">

							<%
							String orderByColumn2 = assetPublisherDisplayContext.getOrderByColumn2();
							%>

							<aui:select inlineField="<%= true %>" inlineLabel="left" label="and-then-by" name="preferences--orderByColumn2--">
								<aui:option label="title" selected='<%= orderByColumn2.equals("title") %>' />
								<aui:option label="create-date" selected='<%= orderByColumn2.equals("createDate") %>' value="createDate" />
								<aui:option label="modified-date" selected='<%= orderByColumn2.equals("modifiedDate") %>' value="modifiedDate" />
								<aui:option label="publish-date" selected='<%= orderByColumn2.equals("publishDate") %>' value="publishDate" />
								<aui:option label="expiration-date" selected='<%= orderByColumn2.equals("expirationDate") %>' value="expirationDate" />
								<aui:option label="priority" selected='<%= orderByColumn2.equals("priority") %>' value="priority" />

								<c:if test="<%= !AssetPublisherWebConfigurationValues.SEARCH_WITH_INDEX %>">
									<aui:option label="view-count" selected='<%= orderByColumn2.equals("viewCount") %>' value="viewCount" />
									<aui:option label="ratings" selected='<%= orderByColumn2.equals("ratings") %>' value="ratings" />
								</c:if>
							</aui:select>

							<%
							String orderByType2 = assetPublisherDisplayContext.getOrderByType2();
							%>

							<aui:select inlineField="<%= true %>" label="" name="preferences--orderByType2--" title="order-by-type">
								<aui:option label="ascending" selected='<%= orderByType2.equals("ASC") %>' value="ASC" />
								<aui:option label="descending" selected='<%= orderByType2.equals("DESC") %>' value="DESC" />
							</aui:select>
						</span>

						<span class="field-row">

							<%
							long assetVocabularyId = GetterUtil.getLong(portletPreferences.getValue("assetVocabularyId", null));
							%>

							<aui:select inlineField="<%= true %>" inlineLabel="left" label="group-by" name="preferences--assetVocabularyId--">
								<aui:option value="" />
								<aui:option label="asset-types" selected="<%= assetVocabularyId == -1 %>" value="-1" />

								<%
								Group companyGroup = company.getGroup();

								if (scopeGroupId != companyGroup.getGroupId()) {
									List<AssetVocabulary> assetVocabularies = AssetVocabularyLocalServiceUtil.getGroupVocabularies(scopeGroupId, false);

									if (!assetVocabularies.isEmpty()) {
									%>

										<optgroup label="<liferay-ui:message key="vocabularies" />">

											<%
											for (AssetVocabulary assetVocabulary : assetVocabularies) {
												assetVocabulary = assetVocabulary.toEscapedModel();
											%>

												<aui:option label="<%= assetVocabulary.getTitle(locale) %>" selected="<%= assetVocabularyId == assetVocabulary.getVocabularyId() %>" value="<%= assetVocabulary.getVocabularyId() %>" />

											<%
											}
											%>

										</optgroup>

									<%
									}
								}
								%>

								<%
								List<AssetVocabulary> assetVocabularies = AssetVocabularyLocalServiceUtil.getGroupVocabularies(companyGroup.getGroupId(), false);

								if (!assetVocabularies.isEmpty()) {
								%>

									<optgroup label="<liferay-ui:message key="vocabularies" /> (<liferay-ui:message key="global" />)">

										<%
										for (AssetVocabulary assetVocabulary : assetVocabularies) {
											assetVocabulary = assetVocabulary.toEscapedModel();
										%>

											<aui:option label="<%= assetVocabulary.getTitle(locale) %>" selected="<%= assetVocabularyId == assetVocabulary.getVocabularyId() %>" value="<%= assetVocabulary.getVocabularyId() %>" />

										<%
										}
										%>

									</optgroup>

								<%
								}
								%>

							</aui:select>
						</span>
					</aui:fieldset>
				</liferay-ui:panel>
			</c:if>
		</liferay-ui:panel-container>
	</liferay-ui:section>

	<liferay-ui:section>
		<liferay-ui:error-marker key="errorSection" value="display-settings" />

		<%@ include file="/display_settings.jspf" %>
	</liferay-ui:section>

	<liferay-ui:section>
		<liferay-ui:error-marker key="errorSection" value="subscriptions" />

		<c:if test="<%= PortalUtil.isRSSFeedsEnabled() %>">
			<liferay-ui:rss-settings
				delta="<%= assetPublisherDisplayContext.getRSSDelta() %>"
				displayStyle="<%= assetPublisherDisplayContext.getRSSDisplayStyle() %>"
				displayStyles="<%= new String[] {RSSUtil.DISPLAY_STYLE_ABSTRACT, RSSUtil.DISPLAY_STYLE_TITLE} %>"
				enabled="<%= assetPublisherDisplayContext.isEnableRSS() %>"
				feedType="<%= assetPublisherDisplayContext.getRSSFeedType() %>"
				name="<%= assetPublisherDisplayContext.getRSSName() %>"
				nameEnabled="<%= true %>"
			/>
		</c:if>

		<%@ include file="/email_subscription_settings.jspf" %>
	</liferay-ui:section>
</liferay-ui:tabs>

<aui:button-row>
	<aui:button onClick='<%= renderResponse.getNamespace() + "saveSelectBoxes();" %>' type="submit" />
</aui:button-row>

<aui:script sandbox="<%= true %>">
	var Util = Liferay.Util;

	var MAP_DDM_STRUCTURES = {};

	var assetSelector = $('#<portlet:namespace />anyAssetType');
	var assetMultipleSelector = $('#<portlet:namespace />currentClassNameIds');
	var ddmStructureFieldName = $('#<portlet:namespace />ddmStructureFieldName');
	var orderByColumn1 = $('#<portlet:namespace />orderByColumn1');
	var orderByColumn2 = $('#<portlet:namespace />orderByColumn2');
	var sourcePanel = $('#assetPublisherSourcePanel');

	<%
	for (AssetRendererFactory<?> curRendererFactory : classTypesAssetRendererFactories) {
		String className = AssetPublisherUtil.getClassName(curRendererFactory);
	%>

		Util.toggleSelectBox('<portlet:namespace />anyClassType<%= className %>', 'false', '<portlet:namespace /><%= className %>Boxes');

		var <%= className %>Options = $('#<portlet:namespace /><%= className %>Options');

		function <portlet:namespace />toggle<%= className %>(removeOrderBySubtype) {
			var assetOptions = assetMultipleSelector.find('option');

			var showOptions = ((assetSelector.val() == '<%= curRendererFactory.getClassNameId() %>') ||
				((assetSelector.val() == 'false') && (assetOptions.length == 1) && (assetOptions.eq(0).val() == '<%= curRendererFactory.getClassNameId() %>')));

			<%= className %>Options.toggleClass('hide', !showOptions);

			if (removeOrderBySubtype) {
				orderByColumn1.find('.order-by-subtype').remove();
				orderByColumn2.find('.order-by-subtype').remove();
			}

			<c:if test="<%= assetPublisherDisplayContext.isShowSubtypeFieldsFilter() %>">
				<%= className %>toggleSubclassesFields(true);
			</c:if>
		}

		<%
		ClassTypeReader classTypeReader = curRendererFactory.getClassTypeReader();

		List<ClassType> assetAvailableClassTypes = classTypeReader.getAvailableClassTypes(assetPublisherDisplayContext.getReferencedModelsGroupIds(), locale);

		if (assetAvailableClassTypes.isEmpty()) {
			continue;
		}

		for (ClassType classType: assetAvailableClassTypes) {
			List<ClassTypeField> classTypeFields = classType.getClassTypeFields();

			if (classTypeFields.isEmpty()) {
				continue;
			}
		%>

			var optgroupClose = '</optgroup>';
			var optgroupOpen = '<optgroup class="order-by-subtype" label="<%= HtmlUtil.escape(classType.getName()) %>">';

			var columnBuffer1 = [optgroupOpen];
			var columnBuffer2 = [optgroupOpen];

			<%
			String orderByColumn1 = assetPublisherDisplayContext.getOrderByColumn1();
			String orderByColumn2 = assetPublisherDisplayContext.getOrderByColumn2();

			for (ClassTypeField classTypeField : classTypeFields) {
				String value = DDMIndexerUtil.encodeName(classTypeField.getClassTypeId(), classTypeField.getName());
				String selectedOrderByColumn1 = StringPool.BLANK;
				String selectedOrderByColumn2 = StringPool.BLANK;

				if (orderByColumn1.equals(value)) {
					selectedOrderByColumn1 = "selected";
				}

				if (orderByColumn2.equals(value)) {
					selectedOrderByColumn2 = "selected";
				}
			%>

				columnBuffer1.push('<option <%= selectedOrderByColumn1 %> value="<%= value %>"><%= HtmlUtil.escapeJS(classTypeField.getLabel()) %></option>');
				columnBuffer2.push('<option <%= selectedOrderByColumn2 %> value="<%= value %>"><%= HtmlUtil.escapeJS(classTypeField.getLabel()) %></option>');

			<%
			}
			%>

			columnBuffer1.push(optgroupClose);
			columnBuffer2.push(optgroupClose);

			MAP_DDM_STRUCTURES['<%= className %>_<%= classType.getClassTypeId() %>_optTextOrderByColumn1'] = columnBuffer1.join('');
			MAP_DDM_STRUCTURES['<%= className %>_<%= classType.getClassTypeId() %>_optTextOrderByColumn2'] = columnBuffer2.join('');

		<%
		}
		%>

		var <%= className %>SubtypeSelector = $('#<portlet:namespace />anyClassType<%= className %>');

		<c:if test="<%= assetPublisherDisplayContext.isShowSubtypeFieldsFilter() %>">
			function <%= className %>toggleSubclassesFields(hideSubtypeFilterEnableWrapper) {
				var subtypeFieldsWrapper = $('#<portlet:namespace /><%= className %>subtypeFieldsWrapper, #<portlet:namespace /><%= className %>subtypeFieldsFilterEnableWrapper');

				var selectedSubtype = <%= className %>SubtypeSelector.val();

				var structureOptions = $('#<portlet:namespace />' + selectedSubtype + '_<%= className %>Options');

				structureOptions.removeClass('hide');

				if ((selectedSubtype != 'false') && (selectedSubtype != 'true')) {
					orderByColumn1.find('.order-by-subtype').remove();
					orderByColumn2.find('.order-by-subtype').remove();

					orderByColumn1.append(MAP_DDM_STRUCTURES['<%= className %>_' + selectedSubtype + '_optTextOrderByColumn1']);
					orderByColumn2.append(MAP_DDM_STRUCTURES['<%= className %>_' + selectedSubtype + '_optTextOrderByColumn2']);

					if (structureOptions.length) {
						subtypeFieldsWrapper.removeClass('hide');
					}
					else if (hideSubtypeFilterEnableWrapper) {
						subtypeFieldsWrapper.addClass('hide');
					}
				}
				else if (hideSubtypeFilterEnableWrapper) {
					subtypeFieldsWrapper.addClass('hide');
				}
			}

			<%= className %>toggleSubclassesFields(false);

			<%= className %>SubtypeSelector.on(
				'change',
				function(event) {
					setDDMFields('<%= className %>', '', '', '', '');

					var subtypeFieldsFilterEnabled = $('#<portlet:namespace />subtypeFieldsFilterEnabled<%= className %>');

					subtypeFieldsFilterEnabled.prop('checked', false);

					sourcePanel.find('.asset-subtypefields').addClass('hide');

					<%= className %>toggleSubclassesFields(true);
				}
			);
		</c:if>

	<%
	}
	%>

	function <portlet:namespace />toggleSubclasses(removeOrderBySubtype) {

		<%
		for (AssetRendererFactory<?> curRendererFactory : classTypesAssetRendererFactories) {
			String className = AssetPublisherUtil.getClassName(curRendererFactory);
		%>

			<portlet:namespace />toggle<%= className %>(removeOrderBySubtype);

		<%
		}
		%>

	}

	<portlet:namespace />toggleSubclasses(false);

	assetSelector.on(
		'change',
		function(event) {
			ddmStructureFieldName.val('');

			$('#<portlet:namespace />ddmStructureFieldValue').val('');

			<portlet:namespace />toggleSubclasses(true);
		}
	);

	sourcePanel.on(
		'click',
		'.asset-subtypefields-wrapper-enable .field',
		function(event) {
			var assetSubtypeFieldsPopupNodes = $('.asset-subtypefields-popup .btn');

			Util.toggleDisabled(assetSubtypeFieldsPopupNodes, !$(event.target).prop('checked'));
		}
	);

	Liferay.after(
		'inputmoveboxes:moveItem',
		function(event) {
			if ((event.fromBox.attr('id') == '<portlet:namespace />currentClassNameIds') || (event.toBox.attr('id') == '<portlet:namespace />currentClassNameIds')) {
				<portlet:namespace />toggleSubclasses();
			}
		}
	);

	sourcePanel.on(
		'click',
		'.asset-subtypefields-popup',
		function(event) {
			var currentTarget = $(event.currentTarget);
			var btn = $('.btn', currentTarget);
			var uri = btn.data('href');

			uri = Util.addParams('_<%= assetPublisherDisplayContext.getPortletResource() %>_ddmStructureDisplayFieldValue=' + encodeURIComponent($('#<portlet:namespace />ddmStructureDisplayFieldValue').val()), uri);
			uri = Util.addParams('_<%= assetPublisherDisplayContext.getPortletResource() %>_ddmStructureFieldName=' + encodeURIComponent($('#<portlet:namespace />ddmStructureFieldName').val()), uri);
			uri = Util.addParams('_<%= assetPublisherDisplayContext.getPortletResource() %>_ddmStructureFieldValue=' + encodeURIComponent($('#<portlet:namespace />ddmStructureFieldValue').val()), uri);

			Util.selectEntity(
				{
					dialog: {
						constrain: true,
						modal: true,
						width: 600
					},
					eventName: '<portlet:namespace />selectDDMStructureField',
					id: '<portlet:namespace />selectDDMStructure' + currentTarget.attr('id'),
					title: '<liferay-ui:message arguments="structure-field" key="select-x" />',
					uri: uri
				},
				function(event) {
					setDDMFields(event.className, event.name, event.value, event.displayValue, event.label + ': ' + event.displayValue);
				}
			);
		}
	);

	function setDDMFields(className, name, value, displayValue, message) {
		$('#<portlet:namespace />ddmStructureFieldName').val(name);
		$('#<portlet:namespace />ddmStructureFieldValue').val(value);
		$('#<portlet:namespace />ddmStructureDisplayFieldValue').val(displayValue);

		$('#<portlet:namespace />' + className + 'ddmStructureFieldMessage').html(_.escape(message));
	}
</aui:script>

<%!
private String _getSectionId(String name) {
	return TextFormatter.format(name, TextFormatter.M);
}

private String _getSectionJsp(String name) {
	return TextFormatter.format(name, TextFormatter.N);
}
%>