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
String className = (String)request.getAttribute("phones.className");
long classPK = (Long)request.getAttribute("phones.classPK");

List<Phone> phones = Collections.emptyList();

if (classPK > 0) {
	phones = PhoneServiceUtil.getPhones(className, classPK);
}
%>

<c:if test="<%= !phones.isEmpty() %>">
	<h3 class="icon-phone-sign"><liferay-ui:message key="phones" /></h3>

	<ul class="property-list">

		<%
		for (Phone phone : phones) {
		%>

			<li class="<%= (phone.isPrimary() && !phones.isEmpty()) ? "icon-star" : StringPool.BLANK %>">
				<%= HtmlUtil.escape(phone.getNumber()) %> <%= phone.getExtension() %> <%= LanguageUtil.get(request, phone.getType().getName()) %>
			</li>

		<%
		}
		%>

	</ul>
</c:if>