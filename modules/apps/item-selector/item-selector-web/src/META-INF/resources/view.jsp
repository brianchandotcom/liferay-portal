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
LocalizedItemSelectorRendering localizedItemSelectorRendering = LocalizedItemSelectorRendering.get(liferayPortletRequest);

List<String> titles = localizedItemSelectorRendering.getTitles();
%>

<c:choose>
	<c:when test="<%= titles.isEmpty() %>">

		<%
		if (_log.isWarnEnabled()) {
			String[] criteria = ParamUtil.getParameterValues(renderRequest, "criteria");

			_log.warn("No item selector views found for " + StringUtil.merge(criteria, StringPool.COMMA_AND_SPACE));
		}
		%>

		<div class="alert alert-info">

			<%
			ResourceBundle resourceBundle = ResourceBundle.getBundle("content/Language", locale);
			%>

			<%= LanguageUtil.get(resourceBundle, "selection-is-not-available") %>
		</div>
	</c:when>
	<c:otherwise>

		<%
		String selectedTab = localizedItemSelectorRendering.getSelectedTab();

		if (Validator.isNull(selectedTab)) {
			selectedTab = titles.get(0);
		}

		ItemSelectorViewRenderer itemSelectorViewRenderer = localizedItemSelectorRendering.getItemSelectorViewRenderer(selectedTab);

		ItemSelectorView<ItemSelectorCriterion> initialItemSelectorView = itemSelectorViewRenderer.getItemSelectorView();
		%>

		<div class="form-search <%= initialItemSelectorView.isShowSearch() ? "" : "hide" %>" id="<portlet:namespace />formSearch">
			<aui:form action="<%= currentURL %>" cssClass="basic-search input-group"  name="searchFm">
				<div class="input-group-input">
					<div class="basic-search-slider">
						<button class="basic-search-close btn btn-default" type="button"><span class="icon-remove"></span></button>

						<aui:input name="selectedTab" type="hidden" value="<%= selectedTab %>" />

						<%
						String keywords = ParamUtil.getString(request, "keywords");
						%>

						<aui:input cssClass="form-control" label="" name="keywords" placeholder="search" type="text" />
					</div>
				</div>
				<div class="input-group-btn">
					<aui:button cssClass="btn btn-default" icon="icon-search" type="submit" value="" />
				</div>
			</aui:form>
		</div>

		<liferay-ui:tabs names="<%= StringUtil.merge(titles) %>" param="selectedTab" refresh="<%= false %>" type="pills" value="<%= selectedTab %>">

			<%
			for (String title : titles) {
				ItemSelectorViewRenderer curItemSelectorViewRenderer = localizedItemSelectorRendering.getItemSelectorViewRenderer(title);

				Map<String, Object> data = new HashMap<String, Object>();

				ItemSelectorView<ItemSelectorCriterion> itemSelectorView = curItemSelectorViewRenderer.getItemSelectorView();

				data.put("showSearch", itemSelectorView.isShowSearch());

				if (selectedTab.equals(itemSelectorView.getTitle(locale))) {
					data.put("portletURL", currentURL);
				}
				else {
					data.put("portletURL", itemSelectorViewRenderer.getPortletUrl());
				}
			%>

				<liferay-ui:section data="<%= data %>">
					<div>

						<%
						curItemSelectorViewRenderer.renderHTML(pageContext);
						%>

					</div>
				</liferay-ui:section>

			<%
			}
			%>

		</liferay-ui:tabs>
	</c:otherwise>
</c:choose>

<aui:script use="aui-base">
	Liferay.on(
		'showTab',
		function(event) {
			var searchForm = A.one('#<portlet:namespace />searchFm');

			if (searchForm) {
				A.one('#<portlet:namespace />selectedTab').val(event.id);

				var tabSection = event.tabSection;

				var showSearch = tabSection.getData('showSearch');

				var formSearch = A.one('#<portlet:namespace />formSearch');

				if (formSearch) {
					formSearch.toggle(showSearch === 'true');

					var searchFm = A.one('#<portlet:namespace />searchFm');

					searchFm.setAttribute('action', tabSection.getData('portletURL'));
				}
			}
		}
	);
</aui:script>

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_item_selector_web.view_jsp");
%>