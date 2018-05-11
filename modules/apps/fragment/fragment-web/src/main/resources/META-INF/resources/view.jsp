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
	navigationItems="<%= fragmentDisplayContext.getNavigationItems() %>"
/>

<div class="container-fluid container-fluid-max-xl container-view">
	<div class="row">
		<div class="col-lg-3">
			<nav class="menubar menubar-transparent menubar-vertical-expand-lg">
				<ul class="nav nav-nested">
					<li class="nav-item">
						<portlet:renderURL var="editFragmentCollectionURL">
							<portlet:param name="mvcRenderCommandName" value="/fragment/edit_fragment_collection" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
						</portlet:renderURL>

						<c:choose>
							<c:when test="<%= ListUtil.isNotEmpty(fragmentCollections) %>">
								<div class="align-items-center autofit-row">
									<div class="autofit-col autofit-col-expand">
										<strong class="text-uppercase">
											<liferay-ui:message key="collections" />
										</strong>
									</div>

									<div class="autofit-col autofit-col-end">
										<c:if test="<%= fragmentDisplayContext.isShowAddButton(FragmentActionKeys.ADD_FRAGMENT_COLLECTION) %>">
											<liferay-ui:icon
												icon="plus"
												iconCssClass="btn btn-monospaced btn-outline-borderless btn-outline-secondary"
												markupView="lexicon"
												url="<%= editFragmentCollectionURL %>"
											/>
										</c:if>
									</div>
								</div>

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
							</c:when>
							<c:otherwise>
								<p class="text-uppercase">
									<strong><liferay-ui:message key="collections" /></strong>
								</p>

								<liferay-frontend:empty-result-message
									actionDropdownItems="<%= fragmentDisplayContext.isShowAddButton(FragmentActionKeys.ADD_FRAGMENT_COLLECTION) ? fragmentDisplayContext.getActionDropdownItems() : null %>"
									animationType="<%= EmptyResultMessageKeys.AnimationType.NONE %>"
									description='<%= LanguageUtil.get(request, "collections-are-needed-to-create-fragments") %>'
									elementType='<%= LanguageUtil.get(request, "collections") %>'
								/>
							</c:otherwise>
						</c:choose>
					</li>
				</ul>
			</nav>
		</div>

		<div class="col-lg-9">

			<%
			FragmentCollection fragmentCollection = fragmentDisplayContext.getFragmentCollection();
			%>

			<c:if test="<%= fragmentCollection != null %>">
				<div class="sheet">
					<div class="align-items-center autofit-row h3">
						<div class="autofit-col">
							<%= fragmentCollection.getName() %>
						</div>

						<div class="autofit-col autofit-col-end inline-item-after">
							<liferay-util:include page="/fragment_collection_action.jsp" servletContext="<%= application %>" />
						</div>
					</div>

					<liferay-util:include page="/view_fragment_entries.jsp" servletContext="<%= application %>" />
				</div>
			</c:if>
		</div>
	</div>
</div>

<aui:script>
	window.<portlet:namespace />exportSelectedFragmentCollections = function() {
		submitForm(document.querySelector('#<portlet:namespace />fm'), '<portlet:resourceURL id="/fragment/export_fragment_collections" />');
	}
</aui:script>