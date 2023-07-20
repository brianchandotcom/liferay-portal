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
LayoutPageTemplateDisplayContext layoutPageTemplateDisplayContext = new LayoutPageTemplateDisplayContext(request, renderRequest, renderResponse);

List<LayoutPageTemplateCollection> layoutPageTemplateCollections = layoutPageTemplateDisplayContext.getLayoutPageTemplateCollections();
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= layoutPageTemplatesAdminDisplayContext.getNavigationItems() %>"
/>

<liferay-ui:success key="layoutPageTemplatePublished" message="the-page-template-was-published-successfully" />

<clay:container-fluid
	cssClass="container-view"
>
	<clay:row>
		<clay:col
			lg="3"
		>
			<portlet:renderURL var="editLayoutPageTemplateCollectionURL">
				<portlet:param name="mvcRenderCommandName" value="/layout_page_template_admin/edit_layout_page_template_collection" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:renderURL>

			<c:choose>
				<c:when test="<%= ListUtil.isNotEmpty(layoutPageTemplateCollections) %>">
					<clay:content-row
						verticalAlign="center"
					>
						<clay:content-col
							expand="<%= true %>"
						>
							<strong class="text-uppercase">
								<liferay-ui:message key="page-template-sets" />
							</strong>
						</clay:content-col>

						<clay:content-col
							verticalAlign="end"
						>
							<ul class="navbar-nav">
								<c:if test="<%= layoutPageTemplateDisplayContext.isShowAddButton(LayoutPageTemplateActionKeys.ADD_LAYOUT_PAGE_TEMPLATE_COLLECTION) %>">
									<li>
										<clay:link
											borderless="<%= true %>"
											cssClass="component-action"
											href="<%= editLayoutPageTemplateCollectionURL.toString() %>"
											icon="plus"
											type="button"
										/>
									</li>
								</c:if>

								<li>
									<portlet:renderURL var="viewLayoutPageTemplateCollectionURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
										<portlet:param name="mvcRenderCommandName" value="/layout_page_template_admin/select_layout_page_template_collections" />
									</portlet:renderURL>

									<portlet:renderURL var="redirectURL">
										<portlet:param name="tabs1" value="page-templates" />
									</portlet:renderURL>

									<liferay-portlet:actionURL copyCurrentRenderParameters="<%= false %>" name="/layout_page_template_admin/delete_layout_page_template_collection" var="deleteLayoutPageTemplateCollectionURL">
										<portlet:param name="redirect" value="<%= redirectURL %>" />
									</liferay-portlet:actionURL>

									<clay:dropdown-actions
										additionalProps='<%=
											HashMapBuilder.<String, Object>put(
												"deleteLayoutPageTemplateCollectionURL", deleteLayoutPageTemplateCollectionURL.toString()
											).put(
												"viewLayoutPageTemplateCollectionURL", viewLayoutPageTemplateCollectionURL.toString()
											).build()
										%>'
										aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
										dropdownItems="<%= layoutPageTemplateDisplayContext.getCollectionsDropdownItems() %>"
										propsTransformer="js/ActionsComponentPropsTransformer"
									/>
								</li>
							</ul>
						</clay:content-col>
					</clay:content-row>

					<%
					for (LayoutPageTemplateCollection layoutPageTemplateCollection : layoutPageTemplateCollections) {
						VerticalNavItemList verticalNavItemList = new VerticalNavItemList();

						final RenderResponse renderResponse1 = renderResponse;

						verticalNavItemList.add(
							verticalNavItem -> {
								String name = HtmlUtil.escape(layoutPageTemplateCollection.getName());

								verticalNavItem.setHref(
									PortletURLBuilder.createRenderURL(
										renderResponse1
									).setTabs1(
										"page-templates"
									).setParameter(
										"layoutPageTemplateCollectionId", layoutPageTemplateCollection.getLayoutPageTemplateCollectionId()
									).buildString());
								verticalNavItem.setLabel(name);
								verticalNavItem.setId(name);
								verticalNavItem.setActive(layoutPageTemplateCollection.getLayoutPageTemplateCollectionId() == layoutPageTemplateDisplayContext.getLayoutPageTemplateCollectionId());
							});
					%>

						<clay:vertical-nav
							verticalNavItems="<%= verticalNavItemList %>"
						/>

					<%
					}
					%>

				</c:when>
				<c:otherwise>
					<p class="text-uppercase">
						<strong><liferay-ui:message key="page-template-sets" /></strong>
					</p>

					<liferay-frontend:empty-result-message
						actionDropdownItems="<%= layoutPageTemplateDisplayContext.isShowAddButton(LayoutPageTemplateActionKeys.ADD_LAYOUT_PAGE_TEMPLATE_COLLECTION) ? layoutPageTemplateDisplayContext.getActionDropdownItems() : null %>"
						animationType="<%= EmptyResultMessageKeys.AnimationType.NONE %>"
						description='<%= LanguageUtil.get(request, "page-template-sets-are-needed-to-create-page-templates") %>'
						elementType='<%= LanguageUtil.get(request, "page-template-sets") %>'
					/>
				</c:otherwise>
			</c:choose>
		</clay:col>

		<clay:col
			lg="9"
		>

			<%
			LayoutPageTemplateCollection layoutPageTemplateCollection = layoutPageTemplateDisplayContext.getLayoutPageTemplateCollection();
			%>

			<c:if test="<%= layoutPageTemplateCollection != null %>">
				<clay:sheet
					size="full"
				>
					<h2 class="sheet-title">
						<clay:content-row
							verticalAlign="center"
						>
							<clay:content-col>
								<span>
									<%= HtmlUtil.escape(layoutPageTemplateCollection.getName()) %>
								</span>
							</clay:content-col>

							<clay:content-col
								cssClass="inline-item-after"
								verticalAlign="end"
							>

								<%
								LayoutPageTemplateCollectionActionDropdownItem layoutPageTemplateCollectionActionDropdownItem = new LayoutPageTemplateCollectionActionDropdownItem(request, renderResponse);
								%>

								<clay:dropdown-actions
									aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
									dropdownItems="<%= layoutPageTemplateCollectionActionDropdownItem.getActionDropdownItems(layoutPageTemplateCollection) %>"
									propsTransformer="js/propsTransformers/LayoutPageTemplateCollectionPropsTransformer"
								/>
							</clay:content-col>
						</clay:content-row>
					</h2>

					<clay:sheet-section>
						<liferay-util:include page="/view_layout_page_template_entries.jsp" servletContext="<%= application %>" />
					</clay:sheet-section>
				</clay:sheet>
			</c:if>
		</clay:col>
	</clay:row>
</clay:container-fluid>