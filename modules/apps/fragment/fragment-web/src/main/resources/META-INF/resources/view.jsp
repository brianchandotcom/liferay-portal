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
List<FragmentCollection> fragmentCollections = (List<FragmentCollection>)request.getAttribute(FragmentWebKeys.FRAGMENT_COLLECTIONS);
Map<String, List<FragmentCollection>> inheritedFragmentCollections = (Map<String, List<FragmentCollection>>)request.getAttribute(FragmentWebKeys.INHERITED_FRAGMENT_COLLECTIONS);

List<FragmentCollectionContributor> fragmentCollectionContributors = fragmentDisplayContext.getFragmentCollectionContributors(locale);
%>

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
							<c:when test="<%= ListUtil.isNotEmpty(fragmentCollections) || ListUtil.isNotEmpty(fragmentCollectionContributors) || MapUtil.isNotEmpty(inheritedFragmentCollections) %>">
								<div class="autofit-row autofit-row-center mb-4">
									<div class="autofit-col autofit-col-expand">
										<strong class="text-uppercase">
											<liferay-ui:message key="collections" />
										</strong>
									</div>

									<div class="autofit-col autofit-col-end">
										<ul class="navbar-nav">
											<li>
												<c:if test="<%= FragmentPermission.contains(permissionChecker, scopeGroupId, FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES) %>">
													<liferay-ui:icon
														icon="plus"
														iconCssClass="btn btn-monospaced btn-outline-borderless btn-outline-secondary btn-sm"
														markupView="lexicon"
														url="<%= editFragmentCollectionURL %>"
													/>
												</c:if>
											</li>
											<li>
												<clay:dropdown-actions
													componentId='<%= renderResponse.getNamespace() + "actionsComponent" %>'
													dropdownItems="<%= fragmentDisplayContext.getCollectionsDropdownItems() %>"
												/>
											</li>
										</ul>
									</div>
								</div>

								<ul class="mb-2 nav nav-stacked">
									<c:if test="<%= ListUtil.isNotEmpty(fragmentCollectionContributors) %>">
										<span class="truncate-text">
											<liferay-ui:message key="default" />
										</span>

										<%
										for (FragmentCollectionContributor fragmentCollectionContributor : fragmentCollectionContributors) {
										%>

											<li class="nav-item">

												<%
												PortletURL fragmentCollectionURL = renderResponse.createRenderURL();

												fragmentCollectionURL.setParameter("mvcRenderCommandName", "/fragment/view");
												fragmentCollectionURL.setParameter("fragmentCollectionKey", String.valueOf(fragmentCollectionContributor.getFragmentCollectionKey()));
												%>

												<a class="nav-link truncate-text <%= Objects.equals(fragmentCollectionContributor.getFragmentCollectionKey(), fragmentDisplayContext.getFragmentCollectionKey()) ? "active" : StringPool.BLANK %>" href="<%= fragmentCollectionURL.toString() %>">
													<%= HtmlUtil.escape(fragmentCollectionContributor.getName(locale)) %>

													<liferay-ui:icon
														icon="lock"
														iconCssClass="text-muted"
														markupView="lexicon"
													/>
												</a>
											</li>

										<%
										}
										%>

									</c:if>
								</ul>

								<ul class="mb-2 nav nav-stacked">

									<%
									for (Map.Entry<String, List<FragmentCollection>> entry : inheritedFragmentCollections.entrySet()) {
									%>

										<span class="truncate-text"><%= entry.getKey() %></span>

										<%
										for (FragmentCollection fragmentCollection : entry.getValue()) {
										%>

											<li class="nav-item">

												<%
												PortletURL fragmentCollectionURL = renderResponse.createRenderURL();

												fragmentCollectionURL.setParameter("mvcRenderCommandName", "/fragment/view");
												fragmentCollectionURL.setParameter("fragmentCollectionId", String.valueOf(fragmentCollection.getFragmentCollectionId()));
												%>

												<a class="nav-link truncate-text <%= (fragmentCollection.getFragmentCollectionId() == fragmentDisplayContext.getFragmentCollectionId()) ? "active" : StringPool.BLANK %>" href="<%= fragmentCollectionURL.toString() %>">
													<%= HtmlUtil.escape(fragmentCollection.getName()) %>

													<liferay-ui:icon
														icon="lock"
														iconCssClass="text-muted"
														markupView="lexicon"
													/>
												</a>
											</li>

									<%
										}
									}
									%>

								</ul>

								<ul class="mb-2 nav nav-stacked">
									<c:if test="<%= ListUtil.isNotEmpty(fragmentCollections) %>">
										<span class="truncate-text"><%= fragmentDisplayContext.getGroupName(scopeGroupId) %></span>

										<%
										for (FragmentCollection fragmentCollection : fragmentCollections) {
										%>

											<li class="nav-item">

												<%
												PortletURL fragmentCollectionURL = renderResponse.createRenderURL();

												fragmentCollectionURL.setParameter("mvcRenderCommandName", "/fragment/view");
												fragmentCollectionURL.setParameter("fragmentCollectionId", String.valueOf(fragmentCollection.getFragmentCollectionId()));
												%>

												<a class="nav-link truncate-text <%= (fragmentCollection.getFragmentCollectionId() == fragmentDisplayContext.getFragmentCollectionId()) ? "active" : StringPool.BLANK %>" href="<%= fragmentCollectionURL.toString() %>">
													<%= HtmlUtil.escape(fragmentCollection.getName()) %>

													<c:if test="<%= fragmentCollection.getGroupId() != scopeGroupId %>">
														<liferay-ui:icon
															icon="lock"
															iconCssClass="text-muted"
															markupView="lexicon"
														/>
													</c:if>
												</a>
											</li>

										<%
										}
										%>

									</c:if>
								</ul>
							</c:when>
							<c:otherwise>
								<p class="text-uppercase">
									<strong><liferay-ui:message key="collections" /></strong>
								</p>

								<liferay-frontend:empty-result-message
									actionDropdownItems="<%= FragmentPermission.contains(permissionChecker, scopeGroupId, FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES) ? fragmentDisplayContext.getActionDropdownItems() : null %>"
									animationType="<%= EmptyResultMessageKeys.AnimationType.NONE %>"
									componentId='<%= renderResponse.getNamespace() + "emptyResultMessageComponent" %>'
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
			<c:if test="<%= (fragmentDisplayContext.getFragmentCollection() != null) || (fragmentDisplayContext.getFragmentCollectionContributor() != null) %>">
				<div class="sheet">
					<h2 class="sheet-title">
						<div class="autofit-row autofit-row-center">
							<div class="autofit-col">
								<%= fragmentDisplayContext.getFragmentCollectionName() %>
							</div>

							<c:if test="<%= fragmentDisplayContext.showFragmentCollectionActions() %>">
								<div class="autofit-col autofit-col-end inline-item-after">
									<liferay-util:include page="/fragment_collection_action.jsp" servletContext="<%= application %>" />
								</div>
							</c:if>
						</div>
					</h2>

					<div class="sheet-section">
						<clay:navigation-bar
							navigationItems="<%= fragmentDisplayContext.getNavigationItems() %>"
						/>

						<c:choose>
							<c:when test="<%= fragmentDisplayContext.isSelectedFragmentCollectionContributor() %>">
								<liferay-util:include page="/view_contributed_fragment_entries.jsp" servletContext="<%= application %>" />
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="<%= fragmentDisplayContext.isViewResources() %>">
										<liferay-util:include page="/view_resources.jsp" servletContext="<%= application %>" />
									</c:when>
									<c:otherwise>
										<liferay-util:include page="/view_fragment_entries.jsp" servletContext="<%= application %>" />
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</c:if>
		</div>
	</div>
</div>

<aui:form cssClass="hide" name="fragmentCollectionsFm">
</aui:form>

<liferay-frontend:component
	context="<%= fragmentDisplayContext.getFragmentCollectionsViewContext() %>"
	module="js/FragmentCollectionsView.es"
/>