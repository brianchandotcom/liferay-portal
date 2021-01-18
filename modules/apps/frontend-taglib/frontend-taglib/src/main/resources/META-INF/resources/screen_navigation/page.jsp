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
String containerCssClass = (String)request.getAttribute("liferay-frontend:screen-navigation:containerCssClass");
String containerWrapperCssClass = (String)request.getAttribute("liferay-frontend:screen-navigation:containerWrapperCssClass");
String fullContainerCssClass = (String)request.getAttribute("liferay-frontend:screen-navigation:fullContainerCssClass");
String headerContainerCssClass = (String)request.getAttribute("liferay-frontend:screen-navigation:headerContainerCssClass");
String id = (String)request.getAttribute("liferay-frontend:screen-navigation:id");
boolean inverted = (boolean)request.getAttribute("liferay-frontend:screen-navigation:inverted");
String menubarCssClass = (String)request.getAttribute("liferay-frontend:screen-navigation:menubarCssClass");
String navCssClass = (String)request.getAttribute("liferay-frontend:screen-navigation:navCssClass");
PortletURL portletURL = (PortletURL)request.getAttribute("liferay-frontend:screen-navigation:portletURL");
ScreenNavigationCategory selectedScreenNavigationCategory = (ScreenNavigationCategory)request.getAttribute("liferay-frontend:screen-navigation:selectedScreenNavigationCategory");
ScreenNavigationEntry<?> selectedScreenNavigationEntry = (ScreenNavigationEntry<?>)request.getAttribute("liferay-frontend:screen-navigation:selectedScreenNavigationEntry");
List<ScreenNavigationCategory> screenNavigationCategories = (List<ScreenNavigationCategory>)request.getAttribute("liferay-frontend:screen-navigation:screenNavigationCategories");
List<ScreenNavigationEntry<Object>> screenNavigationEntries = (List<ScreenNavigationEntry<Object>>)request.getAttribute("liferay-frontend:screen-navigation:screenNavigationEntries");

LiferayPortletResponse finalLiferayPortletResponse = liferayPortletResponse;
%>

<c:if test="<%= ListUtil.isNotEmpty(screenNavigationCategories) && (screenNavigationCategories.size() > 1) %>">
	<div class="page-header">
		<c:if test="<%= Validator.isNotNull(headerContainerCssClass) %>">
			<div class="<%= headerContainerCssClass %>">
		</c:if>

			<clay:navigation-bar
				inverted="<%= inverted %>"
				navigationItems='<%=
					new JSPNavigationItemList(pageContext) {
						{
							for (ScreenNavigationCategory screenNavigationCategory : screenNavigationCategories) {
								PortletURL screenNavigationCategoryURL = PortletURLUtil.clone(portletURL, finalLiferayPortletResponse);

								add(
									navigationItem -> {
										navigationItem.setActive((selectedScreenNavigationCategory != null) && Objects.equals(selectedScreenNavigationCategory.getCategoryKey(), screenNavigationCategory.getCategoryKey()));
										navigationItem.setHref(screenNavigationCategoryURL, "screenNavigationCategoryKey", screenNavigationCategory.getCategoryKey(), "screenNavigationEntryKey", StringPool.BLANK);
										navigationItem.setLabel(screenNavigationCategory.getLabel(themeDisplay.getLocale()));
									});
							}
						}
					}
				%>'
			/>

		<c:if test="<%= Validator.isNotNull(headerContainerCssClass) %>">
			</div>
		</c:if>
	</div>
</c:if>

<c:if test="<%= (selectedScreenNavigationEntry != null) && ListUtil.isNotEmpty(screenNavigationEntries) %>">
	<c:if test="<%= Validator.isNotNull(containerWrapperCssClass) %>">
		<div class="<%= containerWrapperCssClass %>">
	</c:if>

		<c:if test="<%= screenNavigationEntries.size() > 1 %>">
			<div class="row">
		</c:if>

		<c:if test="<%= screenNavigationEntries.size() > 1 %>">
			<div class="<%= navCssClass %>">
				<nav class="<%= menubarCssClass %>">
					<a aria-controls="<%= id %>" aria-expanded="false" class="menubar-toggler" data-toggle="liferay-collapse" href="#<%= id %>" role="button">
						<%= selectedScreenNavigationEntry.getLabel(locale) %>

						<aui:icon image="caret-bottom" markupView="lexicon" />
					</a>

					<div class="collapse menubar-collapse" id="<%= id %>">
						<ul class="nav nav-nested">

							<%
							for (ScreenNavigationEntry<Object> screenNavigationEntry : screenNavigationEntries) {
								PortletURL screenNavigationEntryURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

								screenNavigationEntryURL.setParameter("screenNavigationCategoryKey", screenNavigationEntry.getCategoryKey());
								screenNavigationEntryURL.setParameter("screenNavigationEntryKey", screenNavigationEntry.getEntryKey());
							%>

								<li class="nav-item">
									<a class="nav-link <%= Objects.equals(selectedScreenNavigationEntry.getEntryKey(), screenNavigationEntry.getEntryKey()) ? "active" : StringPool.BLANK %>" href="<%= screenNavigationEntryURL %>"><%= screenNavigationEntry.getLabel(themeDisplay.getLocale()) %></a>
								</li>

							<%
							}
							%>

						</ul>
					</div>
				</nav>
			</div>
		</c:if>

		<div class="<%= (screenNavigationEntries.size() > 1) ? containerCssClass : fullContainerCssClass %>">

			<%
			selectedScreenNavigationEntry.render(request, PipingServletResponse.createPipingServletResponse(pageContext));
			%>

		</div>

		<c:if test="<%= screenNavigationEntries.size() > 1 %>">
			</div>
		</c:if>

	<c:if test="<%= Validator.isNotNull(containerWrapperCssClass) %>">
		</div>
	</c:if>
</c:if>