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
List<FragmentCollection> systemFragmentCollections = (List<FragmentCollection>)request.getAttribute(FragmentWebKeys.SYSTEM_FRAGMENT_COLLECTIONS);

List<FragmentCollectionContributor> fragmentCollectionContributors = fragmentDisplayContext.getFragmentCollectionContributors(locale);
%>

<clay:container-fluid
	cssClass="container-view"
>
	<clay:row>
		<clay:col
			lg="3"
		>
			<nav class="menubar menubar-transparent menubar-vertical-expand-lg">
				<ul class="nav nav-nested">
					<li class="nav-item">
						<portlet:renderURL var="editFragmentCollectionURL">
							<portlet:param name="mvcRenderCommandName" value="/fragment/edit_fragment_collection" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
						</portlet:renderURL>

						<c:choose>
							<c:when test="<%= ListUtil.isNotEmpty(fragmentCollections) || ListUtil.isNotEmpty(fragmentCollectionContributors) || MapUtil.isNotEmpty(inheritedFragmentCollections) %>">
								<clay:content-row
									cssClass="mb-4"
									verticalAlign="center"
								>
									<clay:content-col
										expand="<%= true %>"
									>
										<strong class="text-uppercase">
											<liferay-ui:message key="collections" />
										</strong>
									</clay:content-col>

									<clay:content-col>
										<ul class="navbar-nav">
											<li>
												<c:if test="<%= FragmentPermission.contains(permissionChecker, scopeGroupId, FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES) %>">
													<clay:link
														borderless="<%= true %>"
														cssClass="component-action"
														href="<%= editFragmentCollectionURL %>"
														icon="plus"
														type="button"
													/>
												</c:if>
											</li>
											<li>

												<%
												Map<String, Object> fragmentCollectionsViewContext = fragmentDisplayContext.getFragmentCollectionsViewContext();
												%>

												<clay:dropdown-actions
													additionalProps='<%=
														HashMapBuilder.<String, Object>put(
															"deleteFragmentCollectionURL", fragmentCollectionsViewContext.get("deleteFragmentCollectionURL")
														).put(
															"exportFragmentCollectionsURL", fragmentCollectionsViewContext.get("exportFragmentCollectionsURL")
														).put(
															"viewDeleteFragmentCollectionsURL", fragmentCollectionsViewContext.get("viewDeleteFragmentCollectionsURL")
														).put(
															"viewExportFragmentCollectionsURL", fragmentCollectionsViewContext.get("viewExportFragmentCollectionsURL")
														).put(
															"viewImportURL", fragmentCollectionsViewContext.get("viewImportURL")
														).build()
													%>'
													dropdownItems="<%= fragmentDisplayContext.getCollectionsDropdownItems() %>"
													propsTransformer="js/FragmentCollectionViewDefaultPropsTransformer"
												/>
											</li>
										</ul>
									</clay:content-col>
								</clay:content-row>

								<ul class="mb-2 nav nav-stacked">
									<c:if test="<%= ListUtil.isNotEmpty(fragmentCollectionContributors) || ListUtil.isNotEmpty(systemFragmentCollections) %>">
										<span class="text-truncate">
											<liferay-ui:message key="default" />
										</span>

										<%
										for (FragmentCollectionContributor fragmentCollectionContributor : fragmentCollectionContributors) {
										%>

											<li class="nav-item">
												<a
													class="d-flex nav-link <%= Objects.equals(fragmentCollectionContributor.getFragmentCollectionKey(), fragmentDisplayContext.getFragmentCollectionKey()) ? "active" : StringPool.BLANK %>"
													href="<%=
														PortletURLBuilder.createRenderURL(
															renderResponse
														).setMVCRenderCommandName(
															"/fragment/view"
														).setParameter(
															"fragmentCollectionKey", fragmentCollectionContributor.getFragmentCollectionKey()
														).buildString()
													%>"
												>
													<span class="text-truncate"><%= HtmlUtil.escape(fragmentCollectionContributor.getName(locale)) %></span>

													<liferay-ui:icon
														icon="lock"
														iconCssClass="ml-1 text-muted"
														markupView="lexicon"
													/>
												</a>
											</li>

										<%
										}

										for (FragmentCollection fragmentCollection : systemFragmentCollections) {
										%>

											<li class="nav-item">
												<a
													class="d-flex nav-link <%= (fragmentCollection.getFragmentCollectionId() == fragmentDisplayContext.getFragmentCollectionId()) ? "active" : StringPool.BLANK %>"
													href="<%=
														PortletURLBuilder.createRenderURL(
															renderResponse
														).setMVCRenderCommandName(
															"/fragment/view"
														).setParameter(
															"fragmentCollectionId", fragmentCollection.getFragmentCollectionId()
														).buildString()
													%>"
												>
													<span class="text-truncate"><%= HtmlUtil.escape(fragmentCollection.getName()) %></span>

													<c:if test="<%= fragmentDisplayContext.isLocked(fragmentCollection) %>">
														<liferay-ui:icon
															icon="lock"
															iconCssClass="ml-1 text-muted"
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

								<ul class="mb-2 nav nav-stacked">

									<%
									for (Map.Entry<String, List<FragmentCollection>> entry : inheritedFragmentCollections.entrySet()) {
									%>

										<span class="text-truncate"><%= entry.getKey() %></span>

										<%
										for (FragmentCollection fragmentCollection : entry.getValue()) {
										%>

											<li class="nav-item">
												<a
													class="d-flex nav-link <%= (fragmentCollection.getFragmentCollectionId() == fragmentDisplayContext.getFragmentCollectionId()) ? "active" : StringPool.BLANK %>"
													href="<%=
														PortletURLBuilder.createRenderURL(
															renderResponse
														).setMVCRenderCommandName(
															"/fragment/view"
														).setParameter(
															"fragmentCollectionId", fragmentCollection.getFragmentCollectionId()
														).buildString()
													%>"
												>
													<span class="text-truncate"><%= HtmlUtil.escape(fragmentCollection.getName()) %></span>

													<liferay-ui:icon
														icon="lock"
														iconCssClass="ml-1 text-muted"
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
										<span class="text-truncate"><%= HtmlUtil.escape(fragmentDisplayContext.getGroupName(scopeGroupId)) %></span>

										<%
										for (FragmentCollection fragmentCollection : fragmentCollections) {
										%>

											<li class="nav-item">
												<a
													class="d-flex nav-link <%= (fragmentCollection.getFragmentCollectionId() == fragmentDisplayContext.getFragmentCollectionId()) ? "active" : StringPool.BLANK %>"
													href="<%=
														PortletURLBuilder.createRenderURL(
															renderResponse
														).setMVCRenderCommandName(
															"/fragment/view"
														).setParameter(
															"fragmentCollectionId", fragmentCollection.getFragmentCollectionId()
														).buildString()
													%>"
												>
													<span class="text-truncate"><%= HtmlUtil.escape(fragmentCollection.getName()) %></span>

													<c:if test="<%= fragmentDisplayContext.isLocked(fragmentCollection) %>">
														<liferay-ui:icon
															icon="lock"
															iconCssClass="ml-1 text-muted"
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
									additionalProps="<%= fragmentDisplayContext.getFragmentCollectionsViewContext() %>"
									animationType="<%= EmptyResultMessageKeys.AnimationType.NONE %>"
									buttonPropsTransformer="js/FragmentCollectionViewButtonPropsTransformer"
									description='<%= LanguageUtil.get(request, "collections-are-needed-to-create-fragments") %>'
									elementType='<%= LanguageUtil.get(request, "collections") %>'
									propsTransformer="js/FragmentCollectionViewDefaultPropsTransformer"
									propsTransformerServletContext="<%= application %>"
								/>
							</c:otherwise>
						</c:choose>
					</li>
				</ul>
			</nav>
		</clay:col>

		<clay:col
			lg="9"
		>
			<c:if test="<%= (fragmentDisplayContext.getFragmentCollection() != null) || (fragmentDisplayContext.getFragmentCollectionContributor() != null) %>">
				<clay:sheet
					size="full"
				>
					<h2 class="sheet-title">
						<clay:content-row
							verticalAlign="center"
						>
							<clay:content-col>
								<%= fragmentDisplayContext.getFragmentCollectionName() %>
							</clay:content-col>

							<c:if test="<%= fragmentDisplayContext.showFragmentCollectionActions() %>">
								<clay:content-col
									cssClass="inline-item-after"
								>
									<liferay-util:include page="/fragment_collection_action.jsp" servletContext="<%= application %>" />
								</clay:content-col>
							</c:if>
						</clay:content-row>
					</h2>

					<clay:sheet-section>
						<c:if test="<%= !ListUtil.isEmpty(fragmentDisplayContext.getNavigationItems()) %>">
							<clay:navigation-bar
								navigationItems="<%= fragmentDisplayContext.getNavigationItems() %>"
							/>
						</c:if>

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
					</clay:sheet-section>
				</clay:sheet>
			</c:if>
		</clay:col>
	</clay:row>
</clay:container-fluid>

<aui:form cssClass="hide" name="fm">
</aui:form>