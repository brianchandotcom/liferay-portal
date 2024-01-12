<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
FeatureFlagsDisplayContext featureFlagsDisplayContext = (FeatureFlagsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<FeatureFlagDisplay> searchContainer = featureFlagsDisplayContext.getSearchContainer();
%>

<clay:container-fluid>
	<clay:sheet>
		<clay:sheet-header>
			<h2 class="sheet-title"><%= featureFlagsDisplayContext.getTitle() %></h2>

			<div class="sheet-text"><%= featureFlagsDisplayContext.getDescription() %></div>
		</clay:sheet-header>

		<clay:sheet-section><clay:management-toolbar
			managementToolbarDisplayContext="<%= featureFlagsDisplayContext.getManagementToolbarDisplayContext() %>"
		/>
			<div class="my-4">
				<react:component
					module="{FeatureFlagList} from feature-flag-web"
					props='<%=
						HashMapBuilder.<String, Object>put(
							"featureFlags", searchContainer.getResults()
						).build()
					%>'
				/>
			</div>

			<liferay-ui:search-paginator
				markupView="lexicon"
				searchContainer="<%= searchContainer %>"
			/>
		</clay:sheet-section>
	</clay:sheet>
</clay:container-fluid>