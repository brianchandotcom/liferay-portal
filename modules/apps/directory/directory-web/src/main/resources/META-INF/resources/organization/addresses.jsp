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
String className = (String)request.getAttribute("addresses.className");
long classPK = (Long)request.getAttribute("addresses.classPK");

List<Address> addresses = Collections.emptyList();

if (classPK > 0) {
	addresses = AddressServiceUtil.getAddresses(className, classPK);
}
%>

<c:if test="<%= !addresses.isEmpty() %>">
	<h3 class="icon-home"><liferay-ui:message key="address" /></h3>

	<ul class="property-list">

		<%
		for (Address address : addresses) {
		%>

			<li class="<%= (address.isPrimary() && !addresses.isEmpty()) ? "icon-star" : StringPool.BLANK %>">
				<%@ include file="/common/addresses_address.jspf" %>
			</li>

		<%
		}
		%>

	</ul>
</c:if>