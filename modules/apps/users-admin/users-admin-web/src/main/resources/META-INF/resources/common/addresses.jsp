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
				<liferay-ui:icon
					label="<%= true %>"
					linkCssClass="add-address-link btn btn-secondary btn-sm"
					message="add"
					url='<%=
						PortletURLBuilder.createRenderURL(
							liferayPortletResponse
						).setMVCPath(
							"/common/edit_address.jsp"
						).setRedirect(
							currentURL
						).setParameter(
							"className", className
						).setParameter(
							"classPK", classPK
						).buildString()
					%>'
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
		CSSClasses.builder(
			"addresses-table-wrapper"
		).add(
			"hide", addresses.isEmpty()
		).build()
	%>"
>
	<ul class="list-group list-group-flush">

		<%
		for (Address address : addresses) {
		%>

			<li class="list-group-item list-group-item-flex">
				<clay:content-col>
					<clay:sticker
						cssClass="sticker-static"
						displayType="secondary"
						icon="picture"
					/>
				</clay:content-col>

				<clay:content-col
					expand="<%= true %>"
				>
					<h3>

						<%
						ListType listType = address.getListType();
						%>

						<liferay-ui:message key="<%= listType.getName() %>" />
					</h3>

					<div class="address-display-wrapper list-group-text">
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
				</clay:content-col>

				<clay:content-col
					cssClass="lfr-search-container-wrapper"
				>
					<liferay-util:include page="/common/address_action.jsp" servletContext="<%= application %>">
						<liferay-util:param name="addressId" value="<%= String.valueOf(address.getAddressId()) %>" />
					</liferay-util:include>
				</clay:content-col>
			</li>

		<%
		}
		%>

	</ul>
</div>