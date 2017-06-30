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

<%@ include file="/screen_navigation/init.jsp" %>

<%
PortletURL portletURL = (PortletURL)request.getAttribute("liferay-frontend:screen-navigation:portletURL");
ScreenNavigationCategory selectedScreenNavigationCategory = (ScreenNavigationCategory)request.getAttribute("liferay-frontend:screen-navigation:selectedScreenNavigationCategory");
ScreenNavigationEntry selectedScreenNavigationEntry = (ScreenNavigationEntry)request.getAttribute("liferay-frontend:screen-navigation:selectedScreenNavigationEntry");
List<ScreenNavigationCategory> screenNavigationCategories = (List<ScreenNavigationCategory>)request.getAttribute("liferay-frontend:screen-navigation:screenNavigationCategories");
List<ScreenNavigationEntry> screenNavigationEntries = (List<ScreenNavigationEntry>)request.getAttribute("liferay-frontend:screen-navigation:screenNavigationEntries");
%>

<c:if test="<%= (screenNavigationCategories.size() > 1) || (screenNavigationEntries.size() > 1) %>">
	<aui:nav-bar markupView="lexicon">
		<aui:nav cssClass="navbar-nav">

			<%
			for (ScreenNavigationCategory screenNavigationCategory : screenNavigationCategories) {
				PortletURL screenNavigationCategoryURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

				screenNavigationCategoryURL.setParameter("screenNavigationCategoryKey", screenNavigationCategory.getCategoryKey());
			%>

				<aui:nav-item href="<%= screenNavigationCategoryURL.toString() %>" label="<%= screenNavigationCategory.getLabel(themeDisplay.getLocale()) %>" selected="<%= Objects.equals(selectedScreenNavigationCategory.getCategoryKey(), screenNavigationCategory.getCategoryKey()) %>" />

			<%
			}
			%>

		</aui:nav>
	</aui:nav-bar>
</c:if>

<div class="container-fluid-1280">
	<div class="row">
		<c:if test="<%= screenNavigationEntries.size() > 1 %>">
			<div class="col-md-3">
				<ul class="main-content-nav nav nav-nested">

					<%
					for (ScreenNavigationEntry screenNavigationEntry : screenNavigationEntries) {
						PortletURL screenNavigationEntryURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

						screenNavigationEntryURL.setParameter("screenNavigationCategoryKey", screenNavigationEntry.getCategoryKey());
						screenNavigationEntryURL.setParameter("screenNavigationEntryKey", screenNavigationEntry.getEntryKey());
					%>

						<li class="<%= Objects.equals(selectedScreenNavigationEntry.getEntryKey(), screenNavigationEntry.getEntryKey()) ? "active" : StringPool.BLANK %>">
							<a href="<%= screenNavigationEntryURL %>"><%= screenNavigationEntry.getLabel(themeDisplay.getLocale()) %></a>
						</li>

					<%
					}
					%>

				</ul>
			</div>
		</c:if>

		<div class="<%= (screenNavigationEntries.size() > 1) ? "col-md-9" : "col-md-12" %>">
			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset>

					<%
					selectedScreenNavigationEntry.render(request, response);
					%>

				</aui:fieldset>
			</aui:fieldset-group>
		</div>
	</div>
</div>