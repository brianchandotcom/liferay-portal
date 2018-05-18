<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
String catalogNavigationItem = ParamUtil.getString(request, "catalogNavigationItem", "view-all-product-definitions");

CPDefinitionsDisplayContext cpDefinitionsDisplayContext = (CPDefinitionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer productDefinitionSearchContainer = cpDefinitionsDisplayContext.getSearchContainer();

String displayStyle = cpDefinitionsDisplayContext.getDisplayStyle();

PortletURL portletURL = cpDefinitionsDisplayContext.getPortletURL();

portletURL.setParameter("searchContainerId", "cpDefinitions");

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	items="<%= CPNavigationItemRegistryUtil.getNavigationItems(renderRequest) %>"
/>

<%@ include file="/navbar_definitions.jspf" %>

<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>">
	<liferay-util:param name="searchContainerId" value="cpDefinitions" />
</liferay-util:include>

<portlet:actionURL name="editProductDefinition" var="restoreTrashEntriesURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
</portlet:actionURL>

<liferay-trash:undo
	portletURL="<%= restoreTrashEntriesURL %>"
/>

<div class="product-definitions-container" id="<portlet:namespace />productDefinitionsContainer">
	<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
		<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="cpDefinitionInfoPanel" var="sidebarPanelURL" />

		<liferay-frontend:sidebar-panel
			resourceURL="<%= sidebarPanelURL %>"
			searchContainerId="cpDefinitions"
		>
			<liferay-util:include page="/definition_info_panel.jsp" servletContext="<%= application %>" />
		</liferay-frontend:sidebar-panel>

		<div class="sidenav-content">
			<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
				<aui:input name="<%= Constants.CMD %>" type="hidden" />
				<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
				<aui:input name="deleteCPDefinitionIds" type="hidden" />

				<div class="products-container" id="<portlet:namespace />entriesContainer">
					<liferay-ui:search-container
						emptyResultsMessage="no-products-were-found"
						id="cpDefinitions"
						searchContainer="<%= productDefinitionSearchContainer %>"
					>
						<liferay-ui:search-container-row
							className="com.liferay.commerce.product.model.CPDefinition"
							cssClass="entry-display-style"
							keyProperty="CPDefinitionId"
							modelVar="cpDefinition"
						>

							<%
							String thumbnailSrc = cpDefinition.getDefaultImageThumbnailSrc(themeDisplay);

							PortletURL rowURL = renderResponse.createRenderURL();

							rowURL.setParameter("mvcRenderCommandName", "editProductDefinition");
							rowURL.setParameter("redirect", currentURL);
							rowURL.setParameter("cpDefinitionId", String.valueOf(cpDefinition.getCPDefinitionId()));
							rowURL.setParameter("screenNavigationCategoryKey", CPDefinitionScreenNavigationConstants.CATEGORY_KEY_DETAILS);
							%>

							<c:choose>
								<c:when test='<%= displayStyle.equals("descriptive") %>'>
									<%@ include file="/definition_descriptive.jspf" %>
								</c:when>
								<c:when test='<%= displayStyle.equals("icon") %>'>

									<%
									row.setCssClass("entry-card lfr-asset-folder " + row.getCssClass());
									%>

									<liferay-ui:search-container-column-text>
										<c:choose>
											<c:when test="<%= Validator.isNull(thumbnailSrc) %>">
												<liferay-frontend:icon-vertical-card
													actionJsp="/definition_action.jsp"
													actionJspServletContext="<%= application %>"
													cssClass="entry-display-style"
													icon="documents-and-media"
													resultRow="<%= row %>"
													rowChecker="<%= cpDefinitionsDisplayContext.getRowChecker() %>"
													title="<%= HtmlUtil.escape(cpDefinition.getName(languageId)) %>"
													url="<%= rowURL.toString() %>"
												>
													<%@ include file="/definition_vertical_card.jspf" %>
												</liferay-frontend:icon-vertical-card>
											</c:when>
											<c:otherwise>
												<liferay-frontend:vertical-card
													actionJsp="/definition_action.jsp"
													actionJspServletContext="<%= application %>"
													cssClass="entry-display-style"
													imageUrl="<%= thumbnailSrc %>"
													resultRow="<%= row %>"
													rowChecker="<%= cpDefinitionsDisplayContext.getRowChecker() %>"
													title="<%= HtmlUtil.escape(cpDefinition.getName(languageId)) %>"
													url="<%= rowURL.toString() %>"
												>
													<%@ include file="/definition_vertical_card.jspf" %>
												</liferay-frontend:vertical-card>
											</c:otherwise>
										</c:choose>
									</liferay-ui:search-container-column-text>
								</c:when>
								<c:otherwise>
									<%@ include file="/definition_columns.jspf" %>
								</c:otherwise>
							</c:choose>
						</liferay-ui:search-container-row>

						<liferay-ui:search-iterator
							displayStyle="<%= displayStyle %>"
							markupView="lexicon"
							searchContainer="<%= productDefinitionSearchContainer %>"
						/>
					</liferay-ui:search-container>
				</div>
			</aui:form>
		</div>
	</div>
</div>