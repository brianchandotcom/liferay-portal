<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/directory/init.jsp" %>

<%
List<Organization> organizations = (List<Organization>)request.getAttribute("user.organizations");

String className = (String)request.getAttribute("phones.className");
long classPK = (Long)request.getAttribute("phones.classPK");

List<Phone> personalPhones = Collections.emptyList();
List<Phone> organizationPhones = new ArrayList<Phone>();

if (classPK > 0) {
	personalPhones = PhoneServiceUtil.getPhones(className, classPK);
}

for (int i = 0; i < organizations.size(); i++) {
	try {
		organizationPhones.addAll(PhoneServiceUtil.getPhones(Organization.class.getName(), organizations.get(i).getOrganizationId()));
	}
	catch (Exception e) {
	}
}
%>

<c:if test="<%= !personalPhones.isEmpty() || !organizationPhones.isEmpty() %>">
	<h3><liferay-ui:message key="phones" /></h3>

	<c:if test="<%= !organizationPhones.isEmpty() %>">
		<h4><liferay-ui:message key="organization-phones" /></h4>

		<ul class="property-list">

		<%
		for (Phone phone: organizationPhones) {
		%>

			<li class="<%= phone.isPrimary() ? "primary" : "" %>">
				<%= phone.getNumber() %><aui:spacer /><%= phone.getExtension() %><aui:spacer /><%= LanguageUtil.get(pageContext, phone.getType().getName()) %>
			</li>

		<%
		}
		%>

		</ul>
	</c:if>

	<c:if test="<%= !personalPhones.isEmpty() %>">
		<h4><liferay-ui:message key="personal-phones" /></h4>

		<ul class="property-list">

		<%
		for (Phone phone: personalPhones) {
		%>

			<li class="<%= phone.isPrimary() ? "primary" : "" %>">
				<%= phone.getNumber() %><aui:spacer /><%= phone.getExtension() %><aui:spacer /><%= LanguageUtil.get(pageContext, phone.getType().getName()) %>
			</li>

		<%
		}
		%>

		</ul>
	</c:if>
</c:if>