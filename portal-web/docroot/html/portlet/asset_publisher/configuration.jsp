<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
String tabs2 = ParamUtil.getString(request, "tabs2");

String redirect = ParamUtil.getString(request, "redirect");

String typeSelection = ParamUtil.getString(request, "typeSelection", StringPool.BLANK);

AssetRendererFactory rendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(typeSelection);

Group scopeGroup = themeDisplay.getScopeGroup();
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationActionURL" />
<liferay-portlet:renderURL portletConfiguration="true" varImpl="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm" onSubmit="event.preventDefault();">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL.toString() %>" />
	<aui:input name="assetEntryType" type="hidden" value="<%= typeSelection %>" />
	<aui:input name="typeSelection" type="hidden" />
	<aui:input name="assetEntryId" type="hidden" />
	<aui:input name="assetParentId" type="hidden" />
	<aui:input name="preferences--assetTitle--" type="hidden" />
	<aui:input name="assetEntryOrder" type="hidden" value="-1" />

	<c:if test="<%= typeSelection.equals(StringPool.BLANK) %>">

		<%
		String rootPortletId = PortletConstants.getRootPortletId(portletResource);
		%>

		<c:choose>
			<c:when test="<%= rootPortletId.equals(PortletKeys.RELATED_ASSETS) %>">
				<aui:input name="preferences--selectionStyle--" type="hidden" value="dynamic" />
			</c:when>
			<c:otherwise>
				<aui:select label="asset-selection" name="preferences--selectionStyle--" onChange='<%= renderResponse.getNamespace() + "chooseSelectionStyle();" %>'>
					<aui:option label="dynamic" selected='<%= selectionStyle.equals("dynamic") %>'/>
					<aui:option label="manual" selected='<%= selectionStyle.equals("manual") %>'/>
				</aui:select>
			</c:otherwise>
		</c:choose>

		<liferay-util:buffer var="selectAssetTypeInput">

			<%
			Set<Long> availableClassNameIdsSet = SetUtil.fromArray(availableClassNameIds);

			// Left list

			List<KeyValuePair> typesLeftList = new ArrayList<KeyValuePair>();

			for (long classNameId : classNameIds) {
				ClassName className = ClassNameServiceUtil.getClassName(classNameId);

				typesLeftList.add(new KeyValuePair(String.valueOf(classNameId), ResourceActionsUtil.getModelResource(locale, className.getValue())));
			}

			// Right list

			List<KeyValuePair> typesRightList = new ArrayList<KeyValuePair>();

			Arrays.sort(classNameIds);
			%>

			<aui:select label='<%= selectionStyle.equals("manual") ? "asset-type" : StringPool.BLANK %>' name="preferences--anyAssetType--">
				<aui:option label="any" selected="<%= anyAssetType %>" value="<%= true %>" />
				<aui:option label='<%= LanguageUtil.get(pageContext, "select-more-than-one") + "..." %>' selected="<%= !anyAssetType && (classNameIds.length > 1) %>" value="<%= false %>" />

				<optgroup label="<liferay-ui:message key="asset-type" />">

					<%
					for (long classNameId : availableClassNameIdsSet) {
						ClassName className = ClassNameServiceUtil.getClassName(classNameId);

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
					leftTitle="selected"
					rightTitle="available"
					leftBoxName="currentClassNameIds"
					rightBoxName="availableClassNameIds"
					leftReorder="true"
					leftList="<%= typesLeftList %>"
					rightList="<%= typesRightList %>"
				/>
			</div>
		</liferay-util:buffer>

		<liferay-util:buffer var="selectScope">

			<%
			Set<Group> groups = new HashSet<Group>();

			groups.add(company.getGroup());
			groups.add(scopeGroup);

			for (Layout curLayout : LayoutLocalServiceUtil.getLayouts(layout.getGroupId(), layout.isPrivateLayout())) {
				if (curLayout.hasScopeGroup()) {
					groups.add(curLayout.getScopeGroup());
				}
			}

			// Left list

			List<KeyValuePair> scopesLeftList = new ArrayList<KeyValuePair>();

			for (long groupId : groupIds) {
				Group group = GroupLocalServiceUtil.getGroup(groupId);

				scopesLeftList.add(new KeyValuePair(_getKey(group), _getName(group, pageContext)));
			}

			// Right list

			List<KeyValuePair> scopesRightList = new ArrayList<KeyValuePair>();

			Arrays.sort(groupIds);
			%>

			<aui:select label="" name="preferences--defaultScope--">
				<aui:option label='<%= LanguageUtil.get(pageContext,"select-more-than-one") + "..." %>' selected="<%= groupIds.length > 1 %>" value="<%= false %>" />

				<optgroup label="<liferay-ui:message key="scopes" />">

					<%
					for (Group group : groups) {
						if (Arrays.binarySearch(groupIds, group.getGroupId()) < 0) {
							scopesRightList.add(new KeyValuePair(_getKey(group), _getName(group, pageContext)));
						}
					%>

						<aui:option label="<%= _getName(group, pageContext) %>" selected="<%= (groupIds.length == 1) && (group.getGroupId() == groupIds[0]) %>" value="<%= group.getGroupId() %>" />

					<%
					}
					%>

				</optgroup>
			</aui:select>

			<aui:input name="preferences--scopeIds--" type="hidden" />

			<%
			scopesRightList = ListUtil.sort(scopesRightList, new KeyValuePairComparator(false, true));
			%>

			<div class="<%= defaultScope ? "aui-helper-hidden" : "" %>" id="<portlet:namespace />scopesBoxes">
				<liferay-ui:input-move-boxes
					leftTitle="selected"
					rightTitle="available"
					leftBoxName="currentScopeIds"
					rightBoxName="availableScopeIds"
					leftReorder="true"
					leftList="<%= scopesLeftList %>"
					rightList="<%= scopesRightList %>"
				/>
			</div>
		</liferay-util:buffer>

		<c:choose>
			<c:when test='<%= selectionStyle.equals("manual") %>'>
				<liferay-ui:panel-container extended="<%= true %>" id="assetPublisherSelectionStylePanelContainer" persistState="<%= true %>">
					<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="assetPublisherSelectionStylePanel" persistState="<%= true %>" title="selection">
						<aui:fieldset label="scope">
							<%= selectScope %>
						</aui:fieldset>

						<aui:fieldset>

							<%
							String portletId = portletResource;
							%>

							<%= selectAssetTypeInput %>

							<%
							for (long groupId : groupIds) {
							%>

								<div class="add-asset-selector">
									<div class="lfr-meta-actions edit-controls">
										<%@ include file="/html/portlet/asset_publisher/add_asset.jspf" %>

										<liferay-ui:icon-menu align="left" cssClass="select-existing-selector" icon='<%= themeDisplay.getPathThemeImages() + "/common/search.png" %>' message="select-existing" showWhenSingleIcon="<%= true %>">

											<%
											for (AssetRendererFactory curRendererFactory : AssetRendererFactoryRegistryUtil.getAssetRendererFactories()) {
												if (curRendererFactory.isSelectable()) {
													String taglibURL = "javascript:" + renderResponse.getNamespace() + "selectionForType('" + curRendererFactory.getClassName() + "')";
												%>

													<liferay-ui:icon
														message="<%= ResourceActionsUtil.getModelResource(locale, curRendererFactory.getClassName()) %>" src="<%= curRendererFactory.getIconPath(renderRequest) %>" url="<%= taglibURL %>"
													/>

												<%
												}
											}
											%>

										</liferay-ui:icon-menu>
									</div>
								</div>

							<%
							}

							List<String> deletedAssets = new ArrayList<String>();

							List<String> headerNames = new ArrayList<String>();

							headerNames.add("type");
							headerNames.add("title");
							headerNames.add(StringPool.BLANK);

							SearchContainer searchContainer = new SearchContainer(renderRequest, new DisplayTerms(renderRequest), new DisplayTerms(renderRequest), SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, configurationRenderURL, headerNames, LanguageUtil.get(pageContext, "no-assets-selected"));

							int total = assetEntryXmls.length;

							searchContainer.setTotal(total);

							List results = ListUtil.fromArray(assetEntryXmls);

							int end = (assetEntryXmls.length < searchContainer.getEnd()) ? assetEntryXmls.length : searchContainer.getEnd();

							results = results.subList(searchContainer.getStart(), end);

							searchContainer.setResults(results);

							List resultRows = searchContainer.getResultRows();

							for (int i = 0; i < results.size(); i++) {
								String assetEntryXml = (String)results.get(i);

								Document doc = SAXReaderUtil.read(assetEntryXml);

								Element root = doc.getRootElement();

								int assetEntryOrder = searchContainer.getStart() + i;

								DocUtil.add(root, "asset-order", assetEntryOrder);

								if (assetEntryOrder == (total - 1)) {
									DocUtil.add(root, "last", true);
								}
								else {
									DocUtil.add(root, "last", false);
								}

								String assetEntryClassName = root.element("asset-entry-type").getText();
								String assetEntryUuid = root.element("asset-entry-uuid").getText();

								AssetEntry assetEntry = null;

								boolean deleteAssetEntry = true;

								for (long groupId : groupIds) {
									try {
										assetEntry = AssetEntryLocalServiceUtil.getEntry(groupId, assetEntryUuid);

										assetEntry = assetEntry.toEscapedModel();

										deleteAssetEntry = false;
									}
									catch (NoSuchEntryException nsee) {
									}
								}

								if (deleteAssetEntry) {
									deletedAssets.add(assetEntryUuid);

									continue;
								}

								ResultRow row = new ResultRow(doc, null, assetEntryOrder);

								PortletURL rowURL = renderResponse.createRenderURL();

								rowURL.setParameter("struts_action", "/portlet_configuration/edit_configuration");
								rowURL.setParameter("redirect", redirect);
								rowURL.setParameter("backURL", redirect);
								rowURL.setParameter("portletResource", portletResource);
								rowURL.setParameter("typeSelection", assetEntryClassName);
								rowURL.setParameter("assetEntryId", String.valueOf(assetEntry.getEntryId()));
								rowURL.setParameter("assetEntryOrder", String.valueOf(assetEntryOrder));

								// Type

								row.addText(ResourceActionsUtil.getModelResource(locale, assetEntryClassName), rowURL);

								// Title

								AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(assetEntry.getClassName());

								AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(assetEntry.getClassPK());

								String title = HtmlUtil.escape(assetRenderer.getTitle(locale));

								if (assetEntryClassName.equals(DLFileEntryConstants.getClassName())) {
									FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(assetEntry.getClassPK());

									fileEntry = fileEntry.toEscapedModel();

									StringBundler sb = new StringBundler(6);

									sb.append("<img alt=\"\" class=\"dl-file-icon\" src=\"");
									sb.append(themeDisplay.getPathThemeImages());
									sb.append("/file_system/small/");
									sb.append(fileEntry.getIcon());
									sb.append(".png\" />");
									sb.append(title);

									row.addText(sb.toString(), rowURL);
								}
								else {
									row.addText(title, rowURL);
								}

								// Action

								row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/asset_publisher/asset_selection_action.jsp");

								// Add result row

								resultRows.add(row);
							}

							AssetPublisherUtil.removeAndStoreSelection(deletedAssets, preferences);
							%>

							<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
						</aui:fieldset>
					</liferay-ui:panel>
					<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="assetPublisherSelectionDisplaySettingsPanel" persistState="<%= true %>" title="display-settings">
						<%@ include file="/html/portlet/asset_publisher/display_settings.jspf" %>
					</liferay-ui:panel>
				</liferay-ui:panel-container>

				<aui:button-row>
					<aui:button onClick='<%= renderResponse.getNamespace() + "saveSelectBoxes();" %>' type="submit" />
				</aui:button-row>
			</c:when>
			<c:when test='<%= selectionStyle.equals("dynamic") %>'>
				<liferay-ui:panel-container extended="<%= true %>" id="assetPublisherDynamicSelectionStylePanelContainer" persistState="<%= true %>">
					<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="assetPublisherSourcePanel" persistState="<%= true %>" title="source">
						<aui:fieldset label="scope">
							<%= selectScope %>
						</aui:fieldset>

						<aui:fieldset label="asset-entry-type">
							<%= selectAssetTypeInput %>
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

						<aui:input label='<%= LanguageUtil.format(pageContext, "show-only-assets-with-x-as-its-display-page", layout.getName(locale), false) %>' name="preferences--showOnlyLayoutAssets--" type="checkbox" value="<%= showOnlyLayoutAssets %>" />

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

							Liferay.Util.toggleSelectBox('<portlet:namespace />defaultScope','false','<portlet:namespace />scopesBoxes');
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
									<aui:option label="priority" selected='<%= orderByColumn1.equals("priority") %>'><liferay-ui:message key="priority" /></aui:option>
									<aui:option label="view-count" selected='<%= orderByColumn1.equals("viewCount") %>' value="viewCount" />
									<aui:option label="ratings" selected='<%= orderByColumn1.equals("ratings") %>'><liferay-ui:message key="ratings" /></aui:option>
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
									<aui:option label="priority" selected='<%= orderByColumn2.equals("priority") %>'><liferay-ui:message key="priority" /></aui:option>
									<aui:option label="view-count" selected='<%= orderByColumn2.equals("viewCount") %>' value="viewCount" />
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

													<aui:option label="<%= assetVocabulary.getName() %>" selected="<%= assetVocabularyId == assetVocabulary.getVocabularyId() %>" value="<%= assetVocabulary.getVocabularyId() %>" />

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

												<aui:option label="<%= assetVocabulary.getName() %>" selected="<%= assetVocabularyId == assetVocabulary.getVocabularyId() %>" value="<%= assetVocabulary.getVocabularyId() %>" />

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
					<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="assetPublisherDisplaySettingsPanel" persistState="<%= true %>" title="display-settings">
						<%@ include file="/html/portlet/asset_publisher/display_settings.jspf" %>
					</liferay-ui:panel>
					<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="assetPublisherRssPanel" persistState="<%= true %>" title="rss">
						<aui:fieldset>
							<aui:input label="enable-rss-subscription" name="preferences--enableRss--" type="checkbox" value="<%= enableRSS %>" />

							<div id="<portlet:namespace />rssOptions">
								<aui:input label="rss-feed-name" name="preferences--rssName--" type="text" value="<%= rssName %>" />

								<aui:select label="maximum-items-to-display" name="preferences--rssDelta--">
									<aui:option label="1" selected="<%= rssDelta == 1 %>" />
									<aui:option label="2" selected="<%= rssDelta == 2 %>" />
									<aui:option label="3" selected="<%= rssDelta == 3 %>" />
									<aui:option label="4" selected="<%= rssDelta == 4 %>" />
									<aui:option label="5" selected="<%= rssDelta == 5 %>" />
									<aui:option label="10" selected="<%= rssDelta == 10 %>" />
									<aui:option label="15" selected="<%= rssDelta == 15 %>" />
									<aui:option label="20" selected="<%= rssDelta == 20 %>" />
									<aui:option label="25" selected="<%= rssDelta == 25 %>" />
									<aui:option label="30" selected="<%= rssDelta == 30 %>" />
									<aui:option label="40" selected="<%= rssDelta == 40 %>" />
									<aui:option label="50" selected="<%= rssDelta == 50 %>" />
									<aui:option label="60" selected="<%= rssDelta == 60 %>" />
									<aui:option label="70" selected="<%= rssDelta == 70 %>" />
									<aui:option label="80" selected="<%= rssDelta == 80 %>" />
									<aui:option label="90" selected="<%= rssDelta == 90 %>" />
									<aui:option label="100" selected="<%= rssDelta == 100 %>" />
								</aui:select>

								<aui:select label="display-style" name="preferences--rssDisplayStyle--">
									<aui:option label="<%= RSSUtil.DISPLAY_STYLE_ABSTRACT %>" selected="<%= rssDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_ABSTRACT) %>" />
									<aui:option label="<%= RSSUtil.DISPLAY_STYLE_TITLE %>" selected="<%= rssDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_TITLE) %>" />
								</aui:select>

								<aui:select label="format" name="preferences--rssFormat--">
									<aui:option label="RSS 1.0" selected='<%= rssFormat.equals("rss10") %>' value="rss10" />
									<aui:option label="RSS 2.0" selected='<%= rssFormat.equals("rss20") %>' value="rss20" />
									<aui:option label="Atom 1.0" selected='<%= rssFormat.equals("atom10") %>' value="atom10" />
								</aui:select>
							</div>
						</aui:fieldset>
					</liferay-ui:panel>
				</liferay-ui:panel-container>

				<aui:button-row>
					<aui:button onClick='<%= renderResponse.getNamespace() + "saveSelectBoxes();" %>' type="submit" />
				</aui:button-row>
			</c:when>
		</c:choose>
	</c:if>
</aui:form>

<c:if test="<%= Validator.isNotNull(typeSelection) %>">
	<%@ include file="/html/portlet/asset_publisher/select_asset.jspf" %>
</c:if>

<aui:script>
	function <portlet:namespace />chooseSelectionStyle() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'selection-style';

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />moveSelectionDown(assetEntryOrder) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'move-selection-down';
		document.<portlet:namespace />fm.<portlet:namespace />assetEntryOrder.value = assetEntryOrder;

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />moveSelectionUp(assetEntryOrder) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'move-selection-up';
		document.<portlet:namespace />fm.<portlet:namespace />assetEntryOrder.value = assetEntryOrder;

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />selectAsset(assetEntryId, assetEntryOrder) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'add-selection';
		document.<portlet:namespace />fm.<portlet:namespace />assetEntryId.value = assetEntryId;
		document.<portlet:namespace />fm.<portlet:namespace />assetEntryOrder.value = assetEntryOrder;

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />selectionForType(type) {
		document.<portlet:namespace />fm.<portlet:namespace />typeSelection.value = type;
		document.<portlet:namespace />fm.<portlet:namespace />assetEntryOrder.value = -1;

		submitForm(document.<portlet:namespace />fm, '<%= configurationRenderURL.toString() %>');
	}

	Liferay.provide(
		window,
		'<portlet:namespace />saveSelectBoxes',
		function() {
			if (document.<portlet:namespace />fm.<portlet:namespace />scopeIds) {
				document.<portlet:namespace />fm.<portlet:namespace />scopeIds.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentScopeIds);
			}

			if (document.<portlet:namespace />fm.<portlet:namespace />classNameIds) {
				document.<portlet:namespace />fm.<portlet:namespace />classNameIds.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentClassNameIds);
			}

			document.<portlet:namespace />fm.<portlet:namespace />metadataFields.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentMetadataFields);

			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);

	Liferay.Util.toggleSelectBox('<portlet:namespace />anyAssetType','false','<portlet:namespace />classNamesBoxes');
	Liferay.Util.toggleSelectBox('<portlet:namespace />defaultScope','false','<portlet:namespace />scopesBoxes');
	Liferay.Util.toggleBoxes('<portlet:namespace />enableRssCheckbox','<portlet:namespace />rssOptions');

	Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />selectionStyle);
</aui:script>

<%!
private String _getKey(Group group) throws Exception {
	String key = null;

	if (group.isLayout()) {
		Layout layout = LayoutLocalServiceUtil.getLayout(group.getClassPK());

		key = "Layout" + StringPool.UNDERLINE + layout.getLayoutId();
	}
	else if (group.isLayoutPrototype()) {
		key = "Group" + StringPool.UNDERLINE + GroupConstants.DEFAULT;
	}
	else {
		key = "Group" + StringPool.UNDERLINE + group.getGroupId();
	}

	return key;
}

private String _getName(Group group, PageContext pageContext) throws Exception {
	String name = null;

	if (group.isLayoutPrototype()) {
		name = LanguageUtil.get(pageContext, "default");
	}
	else {
		name = HtmlUtil.escape(group.getDescriptiveName());
	}

	return name;
}
%>