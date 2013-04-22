<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/asset_publisher/init.jsp" %>

<%
List<AssetRendererFactory> classTypesAssetRendererFactories = (List<AssetRendererFactory>)request.getAttribute("configuration.jsp-classTypesAssetRendererFactories");
PortletURL configurationRenderURL = (PortletURL)request.getAttribute("configuration.jsp-configurationRenderURL");
String redirect = (String)request.getAttribute("configuration.jsp-redirect");
String rootPortletId = (String)request.getAttribute("configuration.jsp-rootPortletId");
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
				<aui:fieldset cssClass='<%= rootPortletId.equals(PortletKeys.RELATED_ASSETS) ? "aui-helper-hidden" : "" %>' label="scope">
					<%= selectScope %>
				</aui:fieldset>

				<aui:fieldset label="asset-entry-type">

					<%
					Set<Long> availableClassNameIdsSet = SetUtil.fromArray(availableClassNameIds);

					// Left list

					List<KeyValuePair> typesLeftList = new ArrayList<KeyValuePair>();

					for (long classNameId : classNameIds) {
						String className = PortalUtil.getClassName(classNameId);

						typesLeftList.add(new KeyValuePair(String.valueOf(classNameId), ResourceActionsUtil.getModelResource(locale, className)));
					}

					// Right list

					List<KeyValuePair> typesRightList = new ArrayList<KeyValuePair>();

					Arrays.sort(classNameIds);
					%>

					<aui:select label="" name="preferences--anyAssetType--">
						<aui:option label="any" selected="<%= anyAssetType %>" value="<%= true %>" />
						<aui:option label='<%= LanguageUtil.get(pageContext, "select-more-than-one") + StringPool.TRIPLE_PERIOD %>' selected="<%= !anyAssetType && (classNameIds.length > 1) %>" value="<%= false %>" />

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

					<div class="<%= anyAssetType ? "aui-helper-hidden" : "" %>" id="<portlet:namespace />classNamesBoxes">
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
					for (AssetRendererFactory assetRendererFactory : AssetRendererFactoryRegistryUtil.getAssetRendererFactories()) {
						Map<Long, String> assetAvailableClassTypes = assetRendererFactory.getClassTypes(new long[] {themeDisplay.getCompanyGroupId(), scopeGroupId}, themeDisplay.getLocale());

						if (assetAvailableClassTypes.isEmpty()) {
							continue;
						}

						classTypesAssetRendererFactories.add(assetRendererFactory);

						String className = AssetPublisherUtil.getClassName(assetRendererFactory);

						Set<Long> assetAvailableClassTypeIdsSet = assetAvailableClassTypes.keySet();

						Long[] assetAvailableClassTypeIds = assetAvailableClassTypeIdsSet.toArray(new Long[assetAvailableClassTypeIdsSet.size()]);

						Long[] assetSelectedClassTypeIds = AssetPublisherUtil.getClassTypeIds(preferences, className, assetAvailableClassTypeIds);

						// Left list

						List<KeyValuePair> subTypesLeftList = new ArrayList<KeyValuePair>();

						for (long subTypeId : assetSelectedClassTypeIds) {
							subTypesLeftList.add(new KeyValuePair(String.valueOf(subTypeId), HtmlUtil.escape(assetAvailableClassTypes.get(subTypeId))));
						}

						Arrays.sort(assetSelectedClassTypeIds);

						// Right list

						List<KeyValuePair> subTypesRightList = new ArrayList<KeyValuePair>();

						boolean anyAssetSubType = GetterUtil.getBoolean(preferences.getValue("anyClassType" + className, Boolean.TRUE.toString()));
					%>

						<div class='asset-subtype <%= (assetSelectedClassTypeIds.length < 1) ? StringPool.BLANK : "aui-helper-hidden" %>' id="<portlet:namespace /><%= className %>Options">
							<aui:select label='<%= LanguageUtil.format(pageContext, "x-subtype", ResourceActionsUtil.getModelResource(locale, assetRendererFactory.getClassName())) %>' name='<%= "preferences--anyClassType" + className + "--" %>'>
								<aui:option label="any" selected="<%= anyAssetSubType %>" value="<%= true %>" />
								<aui:option label='<%= LanguageUtil.get(pageContext, "select-more-than-one") + StringPool.TRIPLE_PERIOD %>' selected="<%= !anyAssetSubType && (assetSelectedClassTypeIds.length > 1) %>" value="<%= false %>" />

								<optgroup label="<liferay-ui:message key="subtype" />">

									<%
									for (Long classTypeId : assetAvailableClassTypes.keySet()) {
										if (Arrays.binarySearch(assetSelectedClassTypeIds, classTypeId) < 0) {
											subTypesRightList.add(new KeyValuePair(String.valueOf(classTypeId), HtmlUtil.escape(assetAvailableClassTypes.get(classTypeId))));
										}
									%>

										<aui:option label="<%= HtmlUtil.escapeAttribute(assetAvailableClassTypes.get(classTypeId)) %>" selected="<%= !anyAssetSubType && (assetSelectedClassTypeIds.length == 1) && (classTypeId.equals(assetSelectedClassTypeIds[0])) %>" value="<%= classTypeId %>" />

									<%
									}
									%>

								</optgroup>
							</aui:select>

							<aui:input name='<%= "preferences--classTypeIds" + className + "--" %>' type="hidden" />

							<%
							typesRightList = ListUtil.sort(typesRightList, new KeyValuePairComparator(false, true));
							%>

							<div class="<%= assetSelectedClassTypeIds.length > 1 ? StringPool.BLANK : "aui-helper-hidden" %>" id="<portlet:namespace /><%= className %>Boxes">
								<liferay-ui:input-move-boxes
									leftBoxName='<%= className + "currentClassTypeIds" %>'
									leftList="<%= subTypesLeftList %>"
									leftReorder="true"
									leftTitle="selected"
									rightBoxName='<%= className + "availableClassTypeIds" %>'
									rightList="<%= subTypesRightList %>"
									rightTitle="available"
								/>
							</div>

							<%
							for (long assetAvailableClassTypeId : assetAvailableClassTypeIds) {
								List<Tuple> classTypeFieldNames = assetRendererFactory.getClassTypeFieldNames(assetAvailableClassTypeId, locale);

								if (classTypeFieldNames.isEmpty()) {
									continue;
								}
							%>

								<span class="asset-subtypefields aui-helper-hidden" id="<portlet:namespace /><%= assetAvailableClassTypeId %>_<%= className %>Options">
									<portlet:renderURL var="selectStructureFieldURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
										<portlet:param name="struts_action" value="/portlet_configuration/select_structure_field" />
										<portlet:param name="portletResource" value="<%= portletResource %>" />
										<portlet:param name="className" value="<%= assetRendererFactory.getClassName() %>" />
										<portlet:param name="classTypeId" value="<%= String.valueOf(assetAvailableClassTypeId) %>" />
									</portlet:renderURL>

									<span class="asset-subtypefields-popup" data-href="<%= selectStructureFieldURL.toString() %>">
										<%= LanguageUtil.get(pageContext, "filter-by-field") %>
									</span>
								</span>

							<%
							}
							%>

						</div>

					<%
					}
					%>

					<div class="asset-subtypefield-selected <%= Validator.isNull(ddmStructureFieldName) ? "aui-helper-hidden" : StringPool.BLANK %>" id="<portlet:namespace />ddmStructureFieldValueContainer">
						<aui:input name='<%= "preferences--ddmStructureFieldName--" %>' type="hidden" value="<%= ddmStructureFieldName %>" />

						<aui:input name='<%= "preferences--ddmStructureFieldValue--" %>' type="hidden" value="<%= ddmStructureFieldValue %>" />

						<span id="<portlet:namespace />ddmStructureFieldMessage">
							<c:if test="<%= Validator.isNotNull(ddmStructureFieldLabel) %>">
								<%= ddmStructureFieldLabel + StringPool.RAQUO + ddmStructureFieldValue %>
							</c:if>
						</span>
					</div>
				</aui:fieldset>
			</liferay-ui:panel>

			<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="assetPublisherQueryRulesPanelContainer" persistState="<%= true %>" title="filter[action]">
				<liferay-ui:asset-tags-error />

				<div id="<portlet:namespace />queryRules">
					<aui:fieldset label="displayed-assets-must-match-these-rules">

						<%
						String queryLogicIndexesParam = ParamUtil.getString(request, "queryLogicIndexes");

						int[] queryLogicIndexes = null;

						if (Validator.isNotNull(queryLogicIndexesParam)) {
							queryLogicIndexes = StringUtil.split(queryLogicIndexesParam, 0);
						}
						else {
							queryLogicIndexes = new int[0];

							for (int i = 0; true; i++) {
								String queryValues = PrefsParamUtil.getString(preferences, request, "queryValues" + i);

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
							String queryValues = StringUtil.merge(preferences.getValues("queryValues" + queryLogicIndex , new String[0]));
							String tagNames = ParamUtil.getString(request, "queryTagNames" + queryLogicIndex, queryValues);
							String categoryIds = ParamUtil.getString(request, "queryCategoryIds" + queryLogicIndex, queryValues);

							if (Validator.isNotNull(tagNames) || Validator.isNotNull(categoryIds) || (queryLogicIndexes.length == 1)) {
								request.setAttribute("configuration.jsp-categorizableGroupIds", _getCategorizableGroupIds(groupIds));
								request.setAttribute("configuration.jsp-index", String.valueOf(index));
								request.setAttribute("configuration.jsp-queryLogicIndex", String.valueOf(queryLogicIndex));
						%>

								<div class="lfr-form-row">
									<div class="row-fields">
										<liferay-util:include page="/html/portlet/asset_publisher/edit_query_rule.jsp" />
									</div>
								</div>

						<%
							}

							index++;
						}
						%>

					</aui:fieldset>
				</div>

				<aui:input label='<%= LanguageUtil.format(pageContext, "show-only-assets-with-x-as-its-display-page", HtmlUtil.escape(layout.getName(locale)), false) %>' name="preferences--showOnlyLayoutAssets--" type="checkbox" value="<%= showOnlyLayoutAssets %>" />

				<aui:input label="include-tags-specified-in-the-url" name="preferences--mergeUrlTags--" type="checkbox" value="<%= mergeUrlTags %>" />

				<aui:input helpMessage="include-tags-set-by-other-applications-help" label="include-tags-set-by-other-applications" name="preferences--mergeLayoutTags--" type="checkbox" value="<%= mergeLayoutTags %>" />

				<aui:script use="liferay-auto-fields">
					var autoFields = new Liferay.AutoFields(
						{
							contentBox: '#<portlet:namespace />queryRules > fieldset',
							fieldIndexes: '<portlet:namespace />queryLogicIndexes',
							url: '<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="struts_action" value="/portlet_configuration/edit_query_rule" /></portlet:renderURL>'
						}
					).render();
				</aui:script>
			</liferay-ui:panel>

			<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="assetPublisherCustomUserAttributesQueryRulesPanelContainer" persistState="<%= true %>" title="custom-user-attributes">
				<aui:input helpMessage="custom-user-attributes-help" label="displayed-assets-must-match-these-custom-user-profile-attributes" name="preferences--customUserAttributes--" value="<%= customUserAttributes %>" />
			</liferay-ui:panel>

			<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="assetPublisherOrderingAndGroupingPanel" persistState="<%= true %>" title="ordering-and-grouping">
				<aui:fieldset>
					<span class="aui-field-row">
						<aui:select inlineField="<%= true %>" inlineLabel="left" label="order-by" name="preferences--orderByColumn1--">
							<aui:option label="title" selected='<%= orderByColumn1.equals("title") %>' />
							<aui:option label="create-date" selected='<%= orderByColumn1.equals("createDate") %>' value="createDate" />
							<aui:option label="modified-date" selected='<%= orderByColumn1.equals("modifiedDate") %>' value="modifiedDate" />
							<aui:option label="publish-date" selected='<%= orderByColumn1.equals("publishDate") %>' value="publishDate" />
							<aui:option label="expiration-date" selected='<%= orderByColumn1.equals("expirationDate") %>' value="expirationDate" />
							<aui:option label="priority" selected='<%= orderByColumn1.equals("priority") %>' value="priority" />
							<aui:option label="view-count" selected='<%= orderByColumn1.equals("viewCount") %>' value="viewCount" />
							<aui:option label="ratings" selected='<%= orderByColumn1.equals("ratings") %>' value="ratings" />
						</aui:select>

						<aui:select inlineField="<%= true %>" label="" name="preferences--orderByType1--">
							<aui:option label="ascending" selected='<%= orderByType1.equals("ASC") %>' value="ASC" />
							<aui:option label="descending" selected='<%= orderByType1.equals("DESC") %>' value="DESC" />
						</aui:select>
					</span>

					<span class="aui-field-row">
						<aui:select inlineField="<%= true %>" inlineLabel="left" label="and-then-by" name="preferences--orderByColumn2--">
							<aui:option label="title" selected='<%= orderByColumn2.equals("title") %>' />
							<aui:option label="create-date" selected='<%= orderByColumn2.equals("createDate") %>' value="createDate" />
							<aui:option label="modified-date" selected='<%= orderByColumn2.equals("modifiedDate") %>' value="modifiedDate" />
							<aui:option label="publish-date" selected='<%= orderByColumn2.equals("publishDate") %>' value="publishDate" />
							<aui:option label="expiration-date" selected='<%= orderByColumn2.equals("expirationDate") %>' value="expirationDate" />
							<aui:option label="priority" selected='<%= orderByColumn2.equals("priority") %>' value="priority" />
							<aui:option label="view-count" selected='<%= orderByColumn2.equals("viewCount") %>' value="viewCount" />
							<aui:option label="ratings" selected='<%= orderByColumn2.equals("ratings") %>' value="ratings" />
						</aui:select>

						<aui:select inlineField="<%= true %>" label="" name="preferences--orderByType2--">
							<aui:option label="ascending" selected='<%= orderByType2.equals("ASC") %>' value="ASC" />
							<aui:option label="descending" selected='<%= orderByType2.equals("DESC") %>' value="DESC" />
						</aui:select>
					</span>

					<span class="aui-field-row">
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
		</liferay-ui:panel-container>
	</liferay-ui:section>

	<liferay-ui:section>
		<liferay-ui:error-marker key="errorSection" value="display-settings" />

		<%@ include file="/html/portlet/asset_publisher/display_settings.jspf" %>
	</liferay-ui:section>

	<liferay-ui:section>
		<liferay-ui:error-marker key="errorSection" value="subscriptions" />

		<c:if test="<%= PortalUtil.isRSSFeedsEnabled() %>">
			<liferay-ui:rss-settings
				delta="<%= rssDelta %>"
				displayStyle="<%= rssDisplayStyle %>"
				displayStyles="<%= new String[] {RSSUtil.DISPLAY_STYLE_ABSTRACT, RSSUtil.DISPLAY_STYLE_TITLE} %>"
				enabled="<%= enableRSS %>"
				feedType="<%= rssFeedType %>"
				name="<%= rssName %>"
				nameEnabled="<%= true %>"
			/>
		</c:if>

		<%@ include file="/html/portlet/asset_publisher/email_subscription_settings.jspf" %>
	</liferay-ui:section>
</liferay-ui:tabs>

<aui:button-row>
	<aui:button onClick='<%= renderResponse.getNamespace() + "saveSelectBoxes();" %>' type="submit" />
</aui:button-row>

<aui:script use="aui-base">
	var MAP_DDM_STRUCTURES = {};

	var assetSelector = A.one('#<portlet:namespace />anyAssetType');
	var assetMultipleSelector = A.one('#<portlet:namespace />currentClassNameIds');
	var ddmStructureFieldValueContainer = A.one('#<portlet:namespace />ddmStructureFieldValueContainer');
	var ddmStructureFieldName = A.one('#<portlet:namespace />ddmStructureFieldName');
	var orderByColumn1 = A.one('#<portlet:namespace />orderByColumn1');
	var orderByColumn2 = A.one('#<portlet:namespace />orderByColumn2');
	var sourcePanel = A.one('#assetPublisherSourcePanel');

	var ddmStructureFieldValue = ddmStructureFieldValueContainer.one('#<portlet:namespace />ddmStructureFieldValue');

	<%
	for (AssetRendererFactory curRendererFactory : classTypesAssetRendererFactories) {
		String className = AssetPublisherUtil.getClassName(curRendererFactory);
	%>

		Liferay.Util.toggleSelectBox('<portlet:namespace />anyClassType<%= className %>','false','<portlet:namespace /><%= className %>Boxes');

		var <%= className %>Options = A.one('#<portlet:namespace /><%= className %>Options');

		function <portlet:namespace />toggle<%= className %>(removeOrderBySubType) {
			var assetOptions = assetMultipleSelector.all('option');

			if ((assetSelector.val() == '<%= curRendererFactory.getClassNameId() %>') ||
				((assetSelector.val() == 'false') && (assetOptions.size() == 1) && (assetOptions.item(0).val() == '<%= curRendererFactory.getClassNameId() %>'))) {

				<%= className %>Options.show();
			}
			else {
				<%= className %>Options.hide();
			}

			if (removeOrderBySubType) {
				var orderByColumn1SubType = orderByColumn1.one('.order-by-subtype');

				if (orderByColumn1SubType) {
					orderByColumn1SubType.remove();
				}

				var orderByColumn2SubType = orderByColumn2.one('.order-by-subtype');

				if (orderByColumn2SubType) {
					orderByColumn2SubType.remove();
				}
			}
		}

		<%
		Map<Long, String> assetAvailableClassTypes = curRendererFactory.getClassTypes(new long[] {themeDisplay.getCompanyGroupId(), scopeGroupId}, themeDisplay.getLocale());

		if (assetAvailableClassTypes.isEmpty()) {
			continue;
		}

		Set<Long> assetAvailableClassTypeIdsSet = assetAvailableClassTypes.keySet();

		for (long subTypeId : assetAvailableClassTypeIdsSet) {
			List<Tuple> classTypeFieldNames = curRendererFactory.getClassTypeFieldNames(subTypeId, locale);

			if (classTypeFieldNames.isEmpty()) {
				continue;
			}
		%>

			var optgroupClose = '</optgroup>';
			var optgroupOpen = '<optgroup class="order-by-subtype" label="<%= HtmlUtil.escape(assetAvailableClassTypes.get(subTypeId)) %>">';

			var columnBuffer1 = [optgroupOpen];
			var columnBuffer2 = [optgroupOpen];

			<%
			for (Tuple classTypeFieldName : classTypeFieldNames) {
				String value = DDMIndexerUtil.encodeName(subTypeId, (String)classTypeFieldName.getObject(1));
				String selectedOrderByColumn1 = StringPool.BLANK;
				String selectedOrderByColumn2 = StringPool.BLANK;

				if (orderByColumn1.equals(value)) {
					selectedOrderByColumn1 = "selected";
				}

				if (orderByColumn2.equals(value)) {
					selectedOrderByColumn2 = "selected";
				}
			%>

				columnBuffer1.push('<option <%= selectedOrderByColumn1 %> value="<%= value %>"><%= (String)classTypeFieldName.getObject(0) %></option>');
				columnBuffer2.push('<option <%= selectedOrderByColumn2 %> value="<%= value %>"><%= (String)classTypeFieldName.getObject(0) %></option>');

			<%
			}
			%>

			columnBuffer1.push(optgroupClose);
			columnBuffer2.push(optgroupClose);

			MAP_DDM_STRUCTURES['<%= className %>_<%= subTypeId %>_optTextOrderByColumn1'] = columnBuffer1.join('');
			MAP_DDM_STRUCTURES['<%= className %>_<%= subTypeId %>_optTextOrderByColumn2'] = columnBuffer2.join('');

		<%
		}
		%>

		var <%= className %>SubTypeSelector = A.one('#<portlet:namespace />anyClassType<%= className %>');

		function <%= className %>toggleSubclassesFields() {
			var selectedSubType = <%= className %>SubTypeSelector.val();

			var structureOptions = A.one('#<portlet:namespace />' + selectedSubType + '_<%= className %>Options');

			if (structureOptions) {
				structureOptions.show();
			}

			if ((selectedSubType != 'false') && (selectedSubType != 'true')) {
				var orderByColumn1SubType = orderByColumn1.one('.order-by-subtype');

				if (orderByColumn1SubType) {
					orderByColumn1SubType.remove();
				}

				var orderByColumn2SubType = orderByColumn2.one('.order-by-subtype');

				if (orderByColumn2SubType) {
					orderByColumn2SubType.remove();
				}

				orderByColumn1.appendChild(MAP_DDM_STRUCTURES['<%= className %>_' + selectedSubType + '_optTextOrderByColumn1']);
				orderByColumn2.appendChild(MAP_DDM_STRUCTURES['<%= className %>_' + selectedSubType + '_optTextOrderByColumn2']);
			}
		}

		<%= className %>toggleSubclassesFields();

		<%= className %>SubTypeSelector.on(
			'change',
			function(event) {
				sourcePanel.all('.asset-subtypefields').hide();

				ddmStructureFieldValueContainer.hide();

				<%= className %>toggleSubclassesFields();
			}
		);

	<%
	}
	%>

	function <portlet:namespace />toggleSubclasses(removeOrderBySubType) {

		<%
		for (AssetRendererFactory curRendererFactory : classTypesAssetRendererFactories) {
			String className = AssetPublisherUtil.getClassName(curRendererFactory);
		%>

			<portlet:namespace />toggle<%= className %>(removeOrderBySubType);

		<%
		}
		%>

	}

	<portlet:namespace />toggleSubclasses(false);

	assetSelector.on(
		'change',
		function(event) {
			ddmStructureFieldValueContainer.hide();

			ddmStructureFieldName.val('');
			ddmStructureFieldValue.val('');

			<portlet:namespace />toggleSubclasses(true);
		}
	);

	Liferay.after(
		'inputmoveboxes:moveItem',
		function(event) {
			if ((event.fromBox.get('id') == '<portlet:namespace />currentClassNameIds') || (event.toBox.get('id') == '<portlet:namespace />currentClassNameIds')) {
				<portlet:namespace />toggleSubclasses();
			}
		}
	);

	sourcePanel.delegate(
		'click',
		function(event) {
			Liferay.Util.selectEntity(
				{
					dialog: {
						align: Liferay.Util.Window.ALIGN_CENTER,
						constrain: true,
						modal: true,
						stack: true,
						width: 600
					},
					eventName: '<portlet:namespace />selectDDMStructureField',
					id: '<portlet:namespace />selectDDMStructure' + event.target.id,
					title: '<%= UnicodeLanguageUtil.format(pageContext, "select-x", "structure-field") %>',
					uri: event.target.attr('data-href')
				},
				function(event) {
					var ddmStructureFieldName = A.one('#<portlet:namespace />ddmStructureFieldName');

					ddmStructureFieldName.val(event.name);

					var ddmStructureFieldvalue = A.one('#<portlet:namespace />ddmStructureFieldValue');

					ddmStructureFieldvalue.val(event.value);

					var ddmStructureFieldValueContainer = A.one('#<portlet:namespace />ddmStructureFieldValueContainer');

					var ddmStructureFieldMessage = A.one('#<portlet:namespace />ddmStructureFieldMessage');

					ddmStructureFieldMessage.html(event.label + ' <%= StringPool.RAQUO %> ' + event.value);

					ddmStructureFieldValueContainer.show();
				}
			);
		},
		'.asset-subtypefields-popup'
	);
</aui:script>

<%!
private long[] _getCategorizableGroupIds(long[] groupIds) throws Exception {
	Set<Long> categorizableGroupIds = new HashSet<Long>(groupIds.length);

	for (long groupId : groupIds) {
		Group group = GroupLocalServiceUtil.getGroup(groupId);

		if (group.isLayout()) {
			groupId = group.getParentGroupId();
		}

		categorizableGroupIds.add(groupId);
	}

	return ArrayUtil.toLongArray(categorizableGroupIds);
}
%>