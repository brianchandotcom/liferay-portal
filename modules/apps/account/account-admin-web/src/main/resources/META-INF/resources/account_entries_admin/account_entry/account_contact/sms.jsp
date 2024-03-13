<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="sms"
/>

<aui:model-context bean='<%= (Contact)request.getAttribute("accountEntryContact") %>' model="<%= Contact.class %>" />

<aui:input label="sms" name="smsSn" />