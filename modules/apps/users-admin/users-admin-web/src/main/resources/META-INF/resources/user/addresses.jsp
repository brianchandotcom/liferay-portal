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

<%
User selUser = (User)request.getAttribute(UsersAdminWebKeys.SELECTED_USER);

Contact selContact = null;

if (selUser != null) {
	selContact = selUser.getContact();
}

request.setAttribute("addresses.className", Contact.class.getName());

if (selContact != null) {
	request.setAttribute("addresses.classPK", selContact.getContactId());
}
else {
	request.setAttribute("addresses.classPK", 0L);
}
%>

<%@ include file="/common/addresses.jsp" %>