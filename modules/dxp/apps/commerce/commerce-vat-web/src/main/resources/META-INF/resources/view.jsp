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
CommerceVatNumberDisplayContext commerceVatNumberDisplayContext = (CommerceVatNumberDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<CommerceVatNumber> commerceVatNumberSearchContainer = commerceVatNumberDisplayContext.getSearchContainer();

PortletURL portletURL = commerceVatNumberDisplayContext.getPortletURL();

portletURL.setParameter("searchContainerId", "commerceVatNumbers");

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<%@ include file="/navbar.jspf" %>

<c:if test="<%= hasManageCommerceVatNumbersPermission %>">
	<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>">
		<liferay-util:param name="searchContainerId" value="commerceVatNumbers" />
	</liferay-util:include>

	<div class="container-fluid-1280" id="<portlet:namespace />vatNumbersContainer">
		<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" />
			<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
			<aui:input name="deleteCommerceVatNumberIds" type="hidden" />

			<div class="vat-numbers-container" id="<portlet:namespace />entriesContainer">
				<liferay-ui:search-container
					id="commerceVatNumbers"
					searchContainer="<%= commerceVatNumberSearchContainer %>"
				>
					<liferay-ui:search-container-row
						className="com.liferay.commerce.vat.model.CommerceVatNumber"
						cssClass="entry-display-style"
						keyProperty="commerceVatNumberId"
						modelVar="commerceVatNumber"
					>
						<c:choose>
							<c:when test="<%= hasManageCommerceVatNumbersPermission %>">

								<%
								PortletURL rowURL = renderResponse.createRenderURL();

								rowURL.setParameter("mvcRenderCommandName", "editCommerceVatNumber");
								rowURL.setParameter("redirect", currentURL);
								rowURL.setParameter("commerceVatNumberId", String.valueOf(commerceVatNumber.getCommerceVatNumberId()));
								%>

								<liferay-ui:search-container-column-text
									cssClass="table-cell-content"
									href="<%= rowURL %>"
									name="vat-number"
									property="vatNumber"
								/>
							</c:when>
							<c:otherwise>
								<liferay-ui:search-container-column-text
									cssClass="table-cell-content"
									name="vat-number"
									property="vatNumber"
								/>
							</c:otherwise>
						</c:choose>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-content"
							name="valid"
							value='<%= LanguageUtil.get(request, commerceVatNumber.isValid() ? "yes" : "no") %>'
						/>

						<liferay-ui:search-container-column-date
							cssClass="table-cell-content"
							name="create-date"
							property="createDate"
						/>

						<liferay-ui:search-container-column-jsp
							cssClass="entry-action-column"
							path="/vat_number_action.jsp"
						/>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator
						displayStyle="list"
						markupView="lexicon"
					/>
				</liferay-ui:search-container>
			</div>
		</aui:form>
	</div>
</c:if>