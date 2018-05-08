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
List<FragmentCollection> fragmentCollections = FragmentCollectionServiceUtil.getFragmentCollections(themeDisplay.getScopeGroupId());
%>

<liferay-ui:error exception="<%= RequiredFragmentEntryException.class %>" message="the-collection-cannot-be-deleted-because-it-contains-a-fragment-required-by-one-or-more-templates" />

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= fragmentDisplayContext.getFragmentCollectionNavigationItems() %>"
/>

<div class="container-fluid container-fluid-max-xl container-view">
	<div class="row">
		<nav class="menubar menubar-transparent menubar-vertical-expand-lg">
			<ul class="nav nav-nested">
				<li class="nav-item">
					<strong class="text-uppercase">
						<liferay-ui:message key="collections" />
					</strong>

					<ul class="nav nav-stacked">

						<%
						for (FragmentCollection fragmentCollection : fragmentCollections) {
						%>

							<li class="nav-item">

								<%
								PortletURL fragmentCollectionURL = renderResponse.createRenderURL();

								fragmentCollectionURL.setParameter("fragmentCollectionId", String.valueOf(fragmentCollection.getFragmentCollectionId()));
								%>

								<a class="nav-link truncate-text <%= (fragmentCollection.getFragmentCollectionId() == fragmentDisplayContext.getFragmentCollectionId()) ? "active" : StringPool.BLANK %>" href="<%= fragmentCollectionURL.toString() %>">
									<%= fragmentCollection.getName() %>
								</a>
							</li>

						<%
						}
						%>

					</ul>
				</li>
			</ul>
		</nav>
	</div>
</div>

<aui:script>
	window.<portlet:namespace />exportSelectedFragmentCollections = function() {
		submitForm(document.querySelector('#<portlet:namespace />fm'), '<portlet:resourceURL id="/fragment/export_fragment_collections" />');
	}
</aui:script>