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

<%@ include file="/html/portlet/dockbar/init.jsp" %>

<%
boolean viewEntries = ParamUtil.getBoolean(request, "viewEntries");
boolean viewPreview = ParamUtil.getBoolean(request, "viewPreview");
%>

<c:choose>
	<c:when test="<%= viewEntries %>">
		<div id="<portlet:namespace />entries">

			<%
			String keywords = ParamUtil.getString(request, "keywords");

			String panelTitle = "recent";

			if (Validator.isNotNull(keywords)) {
				panelTitle = "search-results";
			}
			%>

			<liferay-ui:panel collapsible="<%= false %>" cssClass="clearfix lfr-component panel-page-category recent" extended="<%= true %>" id="manageRecentPanel" persistState="<%= true %>" title="<%= panelTitle %>">
				<aui:nav cssClass="nav-list no-margin-nav-list">

					<%
					int deltaDefault = GetterUtil.getInteger(SessionClicks.get(request, "liferay_addpanel_numitems", "10"));
					int delta = ParamUtil.getInteger(request, "delta", deltaDefault);

					String displayStyleDefault = GetterUtil.getString(SessionClicks.get(request, "liferay_addpanel_displaystyle", "descriptive"));
					String displayStyle = ParamUtil.getString(request, "displayStyle", displayStyleDefault);

					long[] groupIds = new long[]{scopeGroupId};

					long[] availableClassNameIds = AssetRendererFactoryRegistryUtil.getClassNameIds(company.getCompanyId());

					for (long classNameId : availableClassNameIds) {
						AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(PortalUtil.getClassName(classNameId));

						if (!assetRendererFactory.isSelectable()) {
							availableClassNameIds = ArrayUtil.remove(availableClassNameIds, classNameId);
						}
					}

					AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

					assetEntryQuery.setClassNameIds(availableClassNameIds);
					assetEntryQuery.setEnd(delta);
					assetEntryQuery.setGroupIds(groupIds);
					assetEntryQuery.setKeywords(keywords);
					assetEntryQuery.setOrderByCol1("modifiedDate");
					assetEntryQuery.setOrderByCol2("title");
					assetEntryQuery.setOrderByType1("DESC");
					assetEntryQuery.setOrderByType2("ASC");
					assetEntryQuery.setStart(0);

					List<AssetEntry> results = null;

					if (PropsValues.ASSET_PUBLISHER_SEARCH_WITH_INDEX && (assetEntryQuery.getLinkedAssetEntryId() == 0)) {
						Hits hits = AssetUtil.search(request, assetEntryQuery, 0, delta);

						results = AssetUtil.getAssetEntries(hits);
					}
					else {
						results = AssetEntryServiceUtil.getEntries(assetEntryQuery);
					}

					for (AssetEntry assetEntry : results) {
						String className = PortalUtil.getClassName(assetEntry.getClassNameId());
						long classPK = assetEntry.getClassPK();

						AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(className);

						if (assetRendererFactory == null) {
							continue;
						}

						AssetRenderer assetRenderer = null;

						try {
							assetRenderer = assetRendererFactory.getAssetRenderer(classPK);
						}
						catch (Exception e) {
						}

						if ((assetRenderer == null) || !assetRenderer.isDisplayable()) {
							continue;
						}

						String title = HtmlUtil.escape(StringUtil.shorten(assetRenderer.getTitle(themeDisplay.getLocale()), 60));

						Map<String, Object> data = new HashMap<String, Object>();

						data.put("class-name", assetEntry.getClassName());
						data.put("class-pk", assetEntry.getClassPK());
						data.put("draggable", true);
						data.put("instanceable", true);
						data.put("portlet-id", assetRenderer.getAddToPagePortletId());
						data.put("title", title);
					%>

						<aui:nav-item cssClass='<%= displayStyle.equals("icon") ? "content-shortcut lfr-content-item span6" : "has-preview content-shortcut lfr-content-item row-fluid" %>'
							data="<%= data %>"
							href=""
							iconClass='<%= displayStyle.equals("icon") ? "" : "icon-file" %>'
							label='<%= displayStyle.equals("list") ? title : "" %>' >

							<c:choose>
								<c:when test='<%= !displayStyle.equals("list") %>' >
									<img class='<%= displayStyle.equals("descriptive") ? "span4" : StringPool.BLANK %>' src="<%= assetRenderer.getThumbnailPath(liferayPortletRequest) %>" />

									<c:choose>
										<c:when test='<%= displayStyle.equals("descriptive") %>' >
											<div class="span8">
												<div><%= title %></div>
												<div><%= StringUtil.shorten(assetRenderer.getSummary(themeDisplay.getLocale()), 120) %></div>
											</div>
										</c:when>
										<c:when test='<%= displayStyle.equals("icon") %>' >
											<p class="text-center"><%= title %></p>
										</c:when>
									</c:choose>
								</c:when>
								<c:otherwise >
									<div <%= AUIUtil.buildData(data) %> class="add-content-item">
										<liferay-ui:message key="add" />
									</div>
								</c:otherwise>
							</c:choose>

						</aui:nav-item>

					<%
					}
					%>

				</aui:nav>
			</liferay-ui:panel>
		</div>
	</c:when>

	<c:when test="<%= viewPreview %>">

		<%
		long classPK = ParamUtil.getLong(request, "classPK");
		String className = ParamUtil.getString(request, "className");
		%>

		<c:if test="<%= (classPK > 0) && Validator.isNotNull(className) %>">

			<%
			AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(className, classPK);
			AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(className);
			AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(classPK);

			request.setAttribute("add_panel.jsp-assetEntry", assetEntry);
			request.setAttribute("add_panel.jsp-assetRendererFactory", assetRendererFactory);
			request.setAttribute("add_panel.jsp-assetRenderer", assetRenderer);
			%>

			<div id="<portlet:namespace />preview">
				<liferay-util:include page="<%= assetRenderer.getPreviewPath(liferayPortletRequest, liferayPortletResponse) %>" portletId="<%= assetRendererFactory.getPortletId() %>" />
			</div>
		</c:if>
	</c:when>
</c:choose>