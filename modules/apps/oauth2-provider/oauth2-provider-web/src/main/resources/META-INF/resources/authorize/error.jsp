<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/authorize/init.jsp" %>

<liferay-ui:error-header />

<liferay-ui:error key="clientIdInvalid" message="invalid-client-id" />

<liferay-ui:error key="redirectURIMissing" message="missing-redirect-uri" />

<liferay-ui:error-principal />