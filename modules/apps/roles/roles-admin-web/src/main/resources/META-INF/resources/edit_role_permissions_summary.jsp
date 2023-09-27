<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
EditRolePermissionsSummaryDisplayContext editRolePermissionsSummaryDisplayContext = new EditRolePermissionsSummaryDisplayContext(request, response, liferayPortletRequest, liferayPortletResponse, roleDisplayContext, application);

SearchContainer<PermissionDisplay> searchContainer = editRolePermissionsSummaryDisplayContext.getSearchContainer();
%>

<aui:form action="#" method="POST" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<clay:sheet>
		<clay:sheet-header>
			<h3 class="sheet-title"><liferay-ui:message key="summary" /></h3>
		</clay:sheet-header>

		<clay:sheet-section>
			<liferay-ui:search-iterator
				markupView="deprecated"
				paginate="<%= false %>"
				searchContainer="<%= searchContainer %>"
				searchResultCssClass="show-quick-actions-on-hover table table-autofit"
			/>
		</clay:sheet-section>

		<c:if test="<%= searchContainer.getTotal() > 0 %>">
			<clay:sheet-footer>
				<clay:content-row>
					<clay:content-col
						cssClass="taglib-search-iterator-page-iterator-bottom"
						expand="<%= true %>"
					>
						<liferay-ui:search-paginator
							markupView="lexicon"
							searchContainer="<%= searchContainer %>"
						/>
					</clay:content-col>
				</clay:content-row>
			</clay:sheet-footer>
		</c:if>
	</clay:sheet>
</aui:form>