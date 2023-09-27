<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
portletDisplay.setShowStagingIcon(false);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= layoutsAdminDisplayContext.getNavigationItems() %>"
/>

<c:choose>
	<c:when test='<%= Objects.equals(layoutsAdminDisplayContext.getTabs1(), "utility-pages") %>'>
		<liferay-util:include page="/view_layout_utility_page_entries.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<liferay-ui:success key='<%= portletDisplay.getId() + "requestProcessed" %>' message="your-request-completed-successfully" />

		<liferay-ui:success key='<%= portletDisplay.getPortletName() + "layoutUpdated" %>' message='<%= LanguageUtil.get(resourceBundle, "the-page-was-updated-successfully") %>' />

		<liferay-ui:success key="layoutPublished" message="the-page-was-published-successfully" />

		<%@ include file="/friendly_url_warning_message.jspf" %>

		<liferay-ui:error embed="<%= false %>" exception="<%= GroupInheritContentException.class %>" message="this-page-cannot-be-deleted-and-cannot-have-child-pages-because-it-is-associated-with-a-site-template" />

		<clay:management-toolbar
			managementToolbarDisplayContext="<%= new LayoutsAdminManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, layoutsAdminDisplayContext) %>"
			propsTransformer="js/LayoutsManagementToolbarPropsTransformer"
		/>

		<liferay-ui:error exception="<%= LayoutTypeException.class %>">

			<%
			LayoutTypeException lte = (LayoutTypeException)errorException;
			%>

			<c:if test="<%= lte.getType() == LayoutTypeException.FIRST_LAYOUT %>">
				<liferay-ui:message arguments='<%= "layout.types." + lte.getLayoutType() %>' key="the-first-page-cannot-be-of-type-x" />
			</c:if>
		</liferay-ui:error>

		<liferay-ui:error exception="<%= RequiredSegmentsExperienceException.MustNotDeleteSegmentsExperienceReferencedBySegmentsExperiments.class %>" message="this-page-cannot-be-deleted-because-it-has-ab-tests-in-progress" />

		<aui:form cssClass="container-fluid container-fluid-max-xl" name="fm">
			<c:choose>
				<c:when test="<%= layoutsAdminDisplayContext.hasLayouts() %>">
					<c:choose>
						<c:when test="<%= layoutsAdminDisplayContext.isSearch() %>">
							<liferay-util:include page="/flattened_view.jsp" servletContext="<%= application %>" />
						</c:when>
						<c:otherwise>

							<%
							MillerColumnsDisplayContext millerColumnsDisplayContext = (MillerColumnsDisplayContext)request.getAttribute(LayoutAdminWebKeys.MILLER_COLUMNS_DISPLAY_CONTEXT);
							%>

							<div>
								<react:component
									module="js/layout/Layout"
									props="<%= millerColumnsDisplayContext.getLayoutData() %>"
								/>
							</div>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<liferay-frontend:empty-result-message
						actionDropdownItems="<%= layoutsAdminDisplayContext.isShowAddRootLayoutButton() ? layoutsAdminDisplayContext.getAddLayoutDropdownItems() : null %>"
						buttonCssClass="secondary"
						description='<%= LanguageUtil.get(request, "fortunately-it-is-very-easy-to-add-new-ones") %>'
						elementType='<%= LanguageUtil.get(request, "pages") %>'
					/>
				</c:otherwise>
			</c:choose>
		</aui:form>
	</c:otherwise>
</c:choose>