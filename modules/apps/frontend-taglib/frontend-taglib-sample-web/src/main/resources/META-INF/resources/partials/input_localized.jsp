<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<clay:container-fluid>
	<form>
		<aui:input id="inputLocalizedId!" label="Sample label" localized="<%= true %>" name="input-localized-name" type="text" value="" />
	</form>
</clay:container-fluid>