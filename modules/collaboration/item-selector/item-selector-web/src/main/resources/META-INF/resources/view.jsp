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
		%>

		<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
			<aui:nav cssClass="navbar-nav">

				<%
				for (String title : titles) {
					ItemSelectorViewRenderer curItemSelectorViewRenderer = localizedItemSelectorRendering.getItemSelectorViewRenderer(title);

					PortletURL portletURL = curItemSelectorViewRenderer.getPortletURL();
				%>

					<aui:nav-item
						href="<%= portletURL.toString() %>"
						label="<%= title %>"
						selected="<%= selectedTab.equals(title) %>"
					/>

				<%
				}
				%>

			</aui:nav>

			<%
			ItemSelectorView<ItemSelectorCriterion> itemSelectorView = itemSelectorViewRenderer.getItemSelectorView();
			%>

			<c:if test="<%= itemSelectorView.isShowSearch() %>">

				<%
				PortletURL searchURL = PortletURLUtil.clone(currentURLObj, liferayPortletResponse);

				searchURL.setParameter("resetCur", Boolean.TRUE.toString());
				%>

				<aui:nav-bar-search>
					<aui:form action='<%= HttpUtil.removeParameter(searchURL.toString(), liferayPortletResponse.getNamespace() + "keywords") %>' name="searchFm">
						<liferay-ui:input-search markupView="lexicon" />
					</aui:form>
				</aui:nav-bar-search>
			</c:if>
		</aui:nav-bar>

		<%
		boolean showGroupSelector = ParamUtil.getBoolean(request, "showGroupSelector");
		%>

		<c:choose>
			<c:when test="<%= showGroupSelector %>">
				<liferay-item-selector:group-selector />
			</c:when>
			<c:otherwise>

				<%
				itemSelectorViewRenderer.renderHTML(pageContext);
				%>

			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_item_selector_web.view_jsp");
%>