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

<div id="<portlet:namespace />entries">

	<%
	String displayStyleDefault = GetterUtil.getString(SessionClicks.get(request, "com.liferay.control.menu.web_addPanelDisplayStyle", "descriptive"));
	String displayStyle = ParamUtil.getString(request, "displayStyle", displayStyleDefault);
	String keywords = ParamUtil.getString(request, "keywords");

	String panelTitle = "recent";

	if (Validator.isNotNull(keywords)) {
		panelTitle = "search-results";
	}
	%>

	<div class="lfr-content-category panel-page-category">
		<a class="collapse-icon list-group-heading" data-toggle="collapse" href="#manageRecentPanel">
			<liferay-ui:message key="<%= panelTitle %>" />
		</a>

		<div class="collapse in list-group-panel" id="manageRecentPanel">
			<div class="list-group-item">
				<ul class="<%= displayStyle.equals("descriptive") ? "tabular-list-group" : "list-unstyled row" %>">

					<%
					int deltaDefault = GetterUtil.getInteger(SessionClicks.get(request, "com.liferay.control.menu.web_addPanelNumItems", "10"));
					int delta = ParamUtil.getInteger(request, "delta", deltaDefault);

					long[] availableClassNameIds = AssetRendererFactoryRegistryUtil.getClassNameIds(company.getCompanyId());

					for (long classNameId : availableClassNameIds) {
						AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassNameId(classNameId);

						if (!assetRendererFactory.isSelectable()) {
							availableClassNameIds = ArrayUtil.remove(availableClassNameIds, classNameId);
						}
					}

					AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

					assetEntryQuery.setClassNameIds(availableClassNameIds);
					assetEntryQuery.setEnd(delta);
					assetEntryQuery.setGroupIds(new long[] {scopeGroupId});
					assetEntryQuery.setKeywords(keywords);
					assetEntryQuery.setOrderByCol1("modifiedDate");
					assetEntryQuery.setOrderByCol2("title");
					assetEntryQuery.setOrderByType1("DESC");
					assetEntryQuery.setOrderByType2("ASC");
					assetEntryQuery.setStart(0);

					BaseModelSearchResult<AssetEntry> baseModelSearchResult = AssetUtil.searchAssetEntries(request, assetEntryQuery, 0, delta);

					for (AssetEntry assetEntry : baseModelSearchResult.getBaseModels()) {
						String className = PortalUtil.getClassName(assetEntry.getClassNameId());
						long classPK = assetEntry.getClassPK();

						AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(className);

						if (assetRendererFactory == null) {
							continue;
						}

						AssetRenderer<?> assetRenderer = null;

						try {
							assetRenderer = assetRendererFactory.getAssetRenderer(classPK);
						}
						catch (Exception e) {
						}

						if ((assetRenderer == null) || !assetRenderer.isDisplayable()) {
							continue;
						}

						String title = HtmlUtil.escape(StringUtil.shorten(assetRenderer.getTitle(themeDisplay.getLocale()), 60));

						String portletId = PortletProviderUtil.getPortletId(assetEntry.getClassName(), PortletProvider.Action.ADD);

						boolean hasAddToPagePermission = PortletPermissionUtil.contains(permissionChecker, layout, portletId, ActionKeys.ADD_TO_PAGE);

						Map<String, Object> data = new HashMap<String, Object>();

						data.put("class-name", assetEntry.getClassName());
						data.put("class-pk", assetEntry.getClassPK());

						if (hasAddToPagePermission) {
							data.put("draggable", true);
						}

						data.put("instanceable", true);
						data.put("portlet-id", portletId);
						data.put("title", title);

						String thumbnailPath = HtmlUtil.escapeAttribute(assetRenderer.getThumbnailPath(liferayPortletRequest));
					%>

						<c:choose>
							<c:when test='<%= displayStyle.equals("icon") %>'>
								<li class="col-md-6 col-sm-6 col-xs-6 drag-content-item lfr-content-item" <%= AUIUtil.buildData(data) %>>
									<c:choose>
										<c:when test="<%= Validator.isNotNull(thumbnailPath) %>">
											<liferay-frontend:vertical-card
												imageUrl="<%= thumbnailPath %>"
												title="<%= title %>"
											/>
										</c:when>
										<c:otherwise>
											<liferay-frontend:icon-vertical-card
												icon="<%= assetRenderer.getIconCssClass() %>"
												title="<%= title %>"
											/>
										</c:otherwise>
									</c:choose>
								</li>
							</c:when>
							<c:otherwise>
								<li class="drag-content-item entry-display-style lfr-content-item list-group-item" <%= AUIUtil.buildData(data) %>>
									<div class=" list-group-item-field">
										<c:choose>
											<c:when test="<%= Validator.isNotNull(thumbnailPath) %>">
												<div class="user-icon user-icon-square user-icon-xl">
													<img alt="thumbnail" class="img-responsive img-rounded" src="<%= thumbnailPath %>" />
												</div>
											</c:when>
											<c:otherwise>
												<liferay-ui:icon
													icon="<%= assetRenderer.getIconCssClass() %>"
													markupView="lexicon"
												/>
											</c:otherwise>
										</c:choose>
									</div>

									<div class=" list-group-item-content">
										<h4><%= title %></h4>

										<%
										Date createDate = assetRenderer.getDisplayDate();

										String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true);
										%>

										<h6 class="text-default">
											<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(assetRenderer.getUserName()), modifiedDateDescription} %>" key="x-modified-x-ago" />
										</h6>

										<h5 class="text-default">
											<%= HtmlUtil.escape(StringUtil.shorten(assetRenderer.getSummary(liferayPortletRequest, liferayPortletResponse), 120)) %>
										</h5>
									</div>
								</li>
							</c:otherwise>
						</c:choose>

					<%
					}
					%>

				</ul>
			</div>
		</div>
	</div>
</div>