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
String category = ParamUtil.getString(request, "category");
int state = ParamUtil.getInteger(request, "state");
%>

<aui:nav-bar markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<portlet:renderURL var="viewURL" />

		<aui:nav-item
			href="<%= viewURL %>"
			label="apps"
			selected="true"
		/>
	</aui:nav>
</aui:nav-bar>

<div class="container-fluid-1280">
	<liferay-ui:search-container>
		<liferay-ui:search-container-results>

			<%
			List<Bundle> bundles = BundleManagerUtil.getBundles();

			List<AppDisplay> appDisplays = AppDisplayFactoryUtil.getAppDisplays(bundles, category, state);

			int end = searchContainer.getEnd();

			if (end > appDisplays.size()) {
				end = appDisplays.size();
			}

			searchContainer.setResults(appDisplays.subList(searchContainer.getStart(), end));

			searchContainer.setTotal(appDisplays.size());
			%>

		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.marketplace.app.manager.web.util.AppDisplay"
			modelVar="appDisplay"
		>
			<liferay-ui:search-container-column-image
				src="<%= appDisplay.getIconURL() %>"
			/>

			<liferay-ui:search-container-column-text colspan="<%= 2 %>">
				<h5>
					<a href="<%= HttpUtil.encodeURL(appDisplay.getDisplayURL(renderResponse)) %>">
						<%= appDisplay.getTitle() %>
					</a>
				</h5>

				<h6 class="text-default">
					<%= appDisplay.getDescription() %>
				</h6>

				<div class="additional-info text-default">
					<div class="additional-info-item">
						<strong>
							<liferay-ui:message key="version" />:
						</strong>

						<%= appDisplay.getVersion() %>
					</div>

					<div class="additional-info-item">
						<strong>
							<liferay-ui:message key="status" />:
						</strong>

						<liferay-ui:message key="<%= BundleStateConstants.getLabel(appDisplay.getState()) %>" />
					</div>
				</div>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="descriptive" markupView="lexicon" />
	</liferay-ui:search-container>
</div>