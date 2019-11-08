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
int deltaDefault = GetterUtil.getInteger(SessionClicks.get(request, "com.liferay.product.navigation.control.menu.web_addPanelNumItems", "10"));

int delta = ParamUtil.getInteger(request, "delta", deltaDefault);

String displayStyleDefault = GetterUtil.getString(SessionClicks.get(request, "com.liferay.product.navigation.control.menu.web_addPanelDisplayStyle", "descriptive"));
String displayStyle = ParamUtil.getString(request, "displayStyle", displayStyleDefault);

String keywords = ParamUtil.getString(request, "keywords");

String panelTitle = "recent";

if (Validator.isNotNull(keywords)) {
	panelTitle = "search-results";
}
%>

<div class="display-style-bar">
	<span class="dropdown" id="<portlet:namespace />numItemsContainer">
		<a aria-expanded="true" class="dropdown-toggle" data-toggle="dropdown" href="javascript:;">
			<span class="item-title"><%= delta %></span>

			<liferay-ui:icon
				icon="caret-double"
				markupView="lexicon"
			/>
		</a>

		<ul class="dropdown-menu">

			<%
			for (int curDelta : PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES) {
				if (curDelta > SearchContainer.MAX_DELTA) {
					continue;
				}

				Map<String, Object> data = new HashMap<String, Object>();

				data.put("delta", curDelta);
			%>

				<li class="num-item <%= (delta == curDelta) ? "active" : StringPool.BLANK %>">
					<aui:a cssClass="dropdown-item num-item" data="<%= data %>" href="javascript:;" label="<%= String.valueOf(curDelta) %>" />
				</li>

			<%
			}
			%>

		</ul>
	</span>
	<span class="float-right" id="<portlet:namespace />displayStyleContainer">

		<%
		Map<String, Object> data = new HashMap<String, Object>();

		data.put("displaystyle", "icon");
		%>

		<liferay-ui:icon
			data="<%= data %>"
			icon="cards2"
			linkCssClass='<%= displayStyle.equals("icon") ? "display-style" : "display-style active" %>'
			markupView="lexicon"
			url="javascript:;"
		/>

		<%
		data.put("displaystyle", "descriptive");
		%>

		<liferay-ui:icon
			data="<%= data %>"
			icon="list"
			linkCssClass='<%= displayStyle.equals("descriptive") ? "display-style" : "display-style active" %>'
			markupView="lexicon"
			url="javascript:;"
		/>
	</span>
</div>

<div class="add-content-button">

	<%
	String redirectURL = PortalUtil.getLayoutFullURL(layout, themeDisplay);

	redirectURL = HttpUtil.addParameter(redirectURL, "portletResource", portletDisplay.getId());
	%>

	<liferay-asset:asset-add-button
		redirect="<%= redirectURL %>"
		useDialog="<%= false %>"
	/>
</div>

<div class="lfr-content-category panel-page-category">
	<a class="collapse-icon list-group-heading panel-header panel-header-link" data-toggle="collapse" href="#manageRecentPanel">
		<liferay-ui:message key="<%= panelTitle %>" />
	</a>

	<div class="collapse in list-group-panel" id="manageRecentPanel">
		<div class="list-group-item">
			<ul class="<%= displayStyle.equals("descriptive") ? "tabular-list-group" : "list-unstyled row" %>">

				<%
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

				BaseModelSearchResult<AssetEntry> baseModelSearchResult = assetHelper.searchAssetEntries(request, assetEntryQuery, 0, delta);

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

					Map<String, Object> itemData = new HashMap<String, Object>();

					itemData.put("class-name", assetEntry.getClassName());
					itemData.put("class-pk", assetEntry.getClassPK());

					if (hasAddToPagePermission) {
						itemData.put("draggable", true);
					}

					itemData.put("instanceable", true);
					itemData.put("portlet-id", portletId);
					itemData.put("title", title);
				%>

					<c:choose>
						<c:when test='<%= displayStyle.equals("icon") %>'>
							<li class="col-md-6 col-sm-6 col-xs-6 drag-content-item lfr-content-item" <%= AUIUtil.buildData(itemData) %>>
								<clay:vertical-card
									verticalCard="<%= new AssetRendererVerticalCard(assetRenderer, liferayPortletRequest) %>"
								/>
							</li>
						</c:when>
						<c:otherwise>

							<%
							String thumbnailPath = HtmlUtil.escapeAttribute(assetRenderer.getThumbnailPath(liferayPortletRequest));
							%>

							<li class="drag-content-item entry-display-style lfr-content-item list-group-item" <%= AUIUtil.buildData(itemData) %>>
								<div class=" list-group-item-field">
									<c:choose>
										<c:when test="<%= Validator.isNotNull(thumbnailPath) %>">
											<div class="user-icon user-icon-square user-icon-xl">
												<img alt="thumbnail" class="img-responsive img-rounded" src="<%= thumbnailPath %>" />
											</div>
										</c:when>
										<c:otherwise>
											<span class="sticker sticker-secondary">
												<liferay-ui:icon
													icon="<%= assetRenderer.getIconCssClass() %>"
													markupView="lexicon"
												/>
											</span>
										</c:otherwise>
									</c:choose>
								</div>

								<div class=" list-group-item-content">
									<h1><%= title %></h1>

									<%
									Date modifiedDate = assetEntry.getModifiedDate();

									String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);
									%>

									<h2 class="text-default">
										<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(assetRenderer.getUserName()), modifiedDateDescription} %>" key="x-modified-x-ago" />
									</h2>

									<h3 class="text-default">
										<%= HtmlUtil.escape(StringUtil.shorten(assetRenderer.getSummary(liferayPortletRequest, liferayPortletResponse), 120)) %>
									</h3>
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

<aui:script use="aui-base">
	A.one('#<portlet:namespace />numItemsContainer').delegate(
		'click',
		function(event) {
			var delta = event.currentTarget.attr('data-delta');

			Liferay.fire('AddContent:refreshContentList', {
				delta: delta
			});
		},
		'.num-item'
	);

	A.one('#<portlet:namespace />displayStyleContainer').delegate(
		'click',
		function(event) {
			var displayStyle = event.currentTarget.attr('data-displaystyle');

			Liferay.fire('AddContent:refreshContentList', {
				displayStyle: displayStyle
			});
		},
		'.display-style'
	);
</aui:script>