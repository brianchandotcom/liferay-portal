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

<clay:sheet-header>
	<clay:content-row
		containerElement="h2"
		cssClass="sheet-title"
	>
		<clay:content-col
			expand="<%= true %>"
		>
			<span class="heading-text"><liferay-ui:message key="addresses" /></span>
		</clay:content-col>

		<clay:content-col>
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
		</clay:content-col>
	</clay:content-row>
</clay:sheet-header>

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
						<clay:sticker
							cssClass="sticker-static"
							displayType="secondary"
							icon="picture"
						/>
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
								<clay:label
									displayType="primary"
									label="primary"
								/>
							</div>
						</c:if>
					</td>
					<td>
						<clay:content-col
							cssClass="lfr-search-container-wrapper"
						>
							<liferay-util:include page="/common/address_action.jsp" servletContext="<%= application %>">
								<liferay-util:param name="addressId" value="<%= String.valueOf(address.getAddressId()) %>" />
							</liferay-util:include>
						</clay:content-col>
					</td>
				</tr>

			<%
			}
			%>

		</tbody>
	</table>
</div>