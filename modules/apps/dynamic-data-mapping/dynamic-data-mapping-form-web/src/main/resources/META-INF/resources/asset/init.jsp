<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/react" prefix="react" %>

<%@ page import="com.liferay.dynamic.data.mapping.form.web.internal.display.context.DDMFormViewFormInstanceRecordDisplayContext" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %>

<portlet:defineObjects />

<aui:script>
	Liferay.namespace('Forms').portletNamespace = '<portlet:namespace />';
</aui:script>