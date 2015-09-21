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
			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle("content/Language", locale, getClass());
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

		<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
			<aui:nav cssClass="navbar-nav">

				<%
				for (String title : titles) {
					ItemSelectorViewRenderer curItemSelectorViewRenderer = localizedItemSelectorRendering.getItemSelectorViewRenderer(title);

					ItemSelectorView<ItemSelectorCriterion> itemSelectorView = curItemSelectorViewRenderer.getItemSelectorView();
				%>

					<aui:nav-item
						href="<%= curItemSelectorViewRenderer.getPortletURL().toString() %>"
						label="<%= title %>"
						selected="<%= selectedTab.equals(itemSelectorView.getTitle(locale)) %>"
					/>

				<%
				}
				%>

			</aui:nav>

			<aui:nav-bar-search>
				<div class="<%= initialItemSelectorView.isShowSearch() ? "" : "hide" %>" id="<portlet:namespace />formSearch">
					<aui:form action="<%= currentURL %>" name="searchFm">
						<liferay-ui:input-search markupView="lexicon" />
					</aui:form>
				</div>
			</aui:nav-bar-search>
		</aui:nav-bar>

		<%
		itemSelectorViewRenderer.renderHTML(pageContext);
		%>

	</c:otherwise>
</c:choose>

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_item_selector_web.view_jsp");
%>