<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
Object modelContext = (Object)request.getAttribute("liferay-frontend:screen-navigation:modelContext");
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
				activeItemAriaCurrent='<%= ListUtil.isNotEmpty(screenNavigationEntries) && (screenNavigationEntries.size() > 1) ? "false" : "page" %>'
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
								String statusLabel = screenNavigationEntry.getStatusLabel(themeDisplay.getLocale(), modelContext);
							%>

								<li class="nav-item">
									<a
										aria-current="<%= Objects.equals(selectedScreenNavigationEntry.getEntryKey(), screenNavigationEntry.getEntryKey()) ? "page" : "false" %>"
										class="nav-link <%= Objects.equals(selectedScreenNavigationEntry.getEntryKey(), screenNavigationEntry.getEntryKey()) ? "active" : StringPool.BLANK %> <%= Validator.isNotNull(statusLabel) ? "align-items-center d-flex" : StringPool.BLANK %>"
										href="<%=
											PortletURLBuilder.create(
												PortletURLUtil.clone(portletURL, liferayPortletResponse)
											).setParameter(
												"screenNavigationCategoryKey", screenNavigationEntry.getCategoryKey()
											).setParameter(
												"screenNavigationEntryKey", screenNavigationEntry.getEntryKey()
											).buildPortletURL()
										%>"
									>
										<%= screenNavigationEntry.getLabel(themeDisplay.getLocale()) %>

										<c:if test="<%= Validator.isNotNull(statusLabel) %>">
											<clay:label
												cssClass="ml-2"
												displayType="<%= screenNavigationEntry.getStatusStyle(modelContext) %>"
												label="<%= statusLabel %>"
											/>
										</c:if>
									</a>
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
			String selectedScreenNavigationEntryLabel = selectedScreenNavigationEntry.getLabel(themeDisplay.getLocale());

			if (Validator.isNotNull(selectedScreenNavigationEntryLabel)) {
				PortalUtil.addPageSubtitle(selectedScreenNavigationEntryLabel, request);
			}

			if (selectedScreenNavigationCategory != null) {
				String selectedScreenNavigationCategoryLabel = selectedScreenNavigationCategory.getLabel(themeDisplay.getLocale());

				if (Validator.isNotNull(selectedScreenNavigationCategoryLabel)) {
					PortalUtil.addPageSubtitle(selectedScreenNavigationCategoryLabel, request);
				}
			}

			selectedScreenNavigationEntry.render(request, PipingServletResponseFactory.createPipingServletResponse(pageContext));
			%>

		</div>

		<c:if test="<%= screenNavigationEntries.size() > 1 %>">
			</div>
		</c:if>

	<c:if test="<%= Validator.isNotNull(containerWrapperCssClass) %>">
		</div>
	</c:if>
</c:if>