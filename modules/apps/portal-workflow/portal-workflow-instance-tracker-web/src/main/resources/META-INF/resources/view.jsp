<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://liferay.com/tld/react" prefix="react" %>

<%@ page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %>

<react:component
	module="js/components/WorkflowInstanceTracker"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"workflowInstanceId", ParamUtil.getLong(request, "instanceId")
		).build()
	%>'
/>