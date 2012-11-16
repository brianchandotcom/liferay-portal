<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
List<AssetRendererFactory> classTypesAssetRendererFactories = (List<AssetRendererFactory>) request.getAttribute("configuration.jsp-classTypesAssetRendererFactories");
PortletURL configurationRenderURL = (PortletURL) request.getAttribute("configuration.jsp-configurationRenderURL");
String redirect = (String) request.getAttribute("configuration.jsp-redirect");
String rootPortletId = (String) request.getAttribute("configuration.jsp-rootPortletId");
String selectScope = (String) request.getAttribute("configuration.jsp-selectScope");
%>

<liferay-ui:panel-container extended="<%= true %>" id="assetPublisherSelectionStylePanelContainer" persistState="<%= true %>">
	<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="assetPublisherSelectionStylePanel" persistState="<%= true %>" title="selection">
		<aui:fieldset label="scope">
			<%= selectScope %>
		</aui:fieldset>

		<aui:fieldset>

			<%
			classNameIds = availableClassNameIds;

			String portletId = portletResource;

			for (long groupId : groupIds) {
			%>

				<div class="add-asset-selector">
					<div class="lfr-meta-actions edit-controls">
						<%@ include file="/html/portlet/asset_publisher/add_asset.jspf" %>

						<liferay-ui:icon-menu align="left" cssClass="select-existing-selector" icon='<%= themeDisplay.getPathThemeImages() + "/common/search.png" %>' message="select-existing" showWhenSingleIcon="<%= true %>">

							<%
							for (AssetRendererFactory curRendererFactory : AssetRendererFactoryRegistryUtil.getAssetRendererFactories()) {
								if (curRendererFactory.isSelectable()) {
									String taglibURL = "javascript:" + renderResponse.getNamespace() + "selectionForType('" + groupId + "', '" + curRendererFactory.getClassName() + "')";
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

			<c:if test="<%= !deletedAssets.isEmpty() %>">
				<div class="portlet-msg-info">
					<liferay-ui:message key="the-selected-assets-have-been-removed-from-the-list-because-they-do-not-belong-in-the-scope-of-this-portlet" />
				</div>
			</c:if>

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

<aui:script>
	function <portlet:namespace />selectionForType(groupId, type) {
		document.<portlet:namespace />fm.<portlet:namespace />groupId.value = groupId;
		document.<portlet:namespace />fm.<portlet:namespace />typeSelection.value = type;
		document.<portlet:namespace />fm.<portlet:namespace />assetEntryOrder.value = -1;

		submitForm(document.<portlet:namespace />fm, '<%= configurationRenderURL.toString() %>');
	}
</aui:script>