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
			<liferay-ui:search-container-column-text colspan="<%= 2 %>">
				<%= appDisplay.getName() %>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="descriptive" markupView="lexicon" />
	</liferay-ui:search-container>
</div>