<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<h3>
	<liferay-ui:message key="write-something-for-this-mfa-sample" />
</h3>

<aui:input label="mfa-sample" name="mfaSample" showRequiredLabel="yes" />

<aui:button-row>
	<aui:button type="submit" value="submit" />
</aui:button-row>