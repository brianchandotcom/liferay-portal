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
String className = (String)request.getAttribute("contact_information.jsp-className");
long classPK = (long)request.getAttribute("contact_information.jsp-classPK");

String emptyResultsMessage = ParamUtil.getString(request, "emptyResultsMessage");

List<Address> addresses = AddressServiceUtil.getAddresses(className, classPK);
%>

<div class="sheet-header">
	<h2 class="autofit-row sheet-title">
		<span class="autofit-col autofit-col-expand">
			<span class="heading-text"><liferay-ui:message key="addresses" /></span>
		</span>
		<span class="autofit-col">
			<span class="heading-end">

				<%
				PortletURL editURL = liferayPortletResponse.createRenderURL();

				editURL.setParameter("mvcPath", "/common/edit_address.jsp");
				editURL.setParameter("redirect", currentURL);
				editURL.setParameter("className", className);
				editURL.setParameter("classPK", String.valueOf(classPK));
				%>

				<liferay-ui:icon
					label="<%= true %>"
					linkCssClass="add-address-link btn btn-secondary btn-sm"
					message="add"
					url="<%= editURL.toString() %>"
				/>
			</span>
		</span>
	</h2>
</div>

<c:if test="<%= addresses.isEmpty() %>">
	<div class="contact-information-empty-results-message-wrapper">
		<liferay-ui:empty-result-message
			message="<%= emptyResultsMessage %>"
		/>
	</div>
</c:if>

<div
	class="<%=
		CSSClassNames.builder(
			"addresses-table-wrapper", "table-responsive"
		).add(
			"hide", addresses.isEmpty()
		).build()
	%>"
>
	<table class="table table-autofit">
		<tbody>

			<%
			for (Address address : addresses) {
			%>

				<tr>
					<td>
						<div class="sticker sticker-secondary sticker-static">
							<aui:icon image="picture" markupView="lexicon" />
						</div>
					</td>
					<td class="table-cell-expand">
						<h4>

							<%
							ListType listType = address.getType();
							%>

							<liferay-ui:message key="<%= listType.getName() %>" />
						</h4>

						<div class="address-display-wrapper">
							<liferay-text-localizer:address-display
								address="<%= address %>"
							/>
						</div>

						<c:if test="<%= address.isPrimary() %>">
							<div class="address-primary-label-wrapper">
								<span class="label label-primary">
									<span class="label-item label-item-expand"><%= StringUtil.toUpperCase(LanguageUtil.get(request, "primary"), locale) %></span>
								</span>
							</div>
						</c:if>
					</td>
					<td>
						<span class="autofit-col lfr-search-container-wrapper">
							<liferay-util:include page="/common/address_action.jsp" servletContext="<%= application %>">
								<liferay-util:param name="addressId" value="<%= String.valueOf(address.getAddressId()) %>" />
							</liferay-util:include>
						</span>
					</td>
				</tr>

			<%
			}
			%>

		</tbody>
	</table>
</div>