<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" portletName="<%= WorkflowPortletKeys.WORKFLOW_INSTANCE_TRACKER %>" var="baseResourceURL" />

<div>
	<react:component
		module="{WorkflowStatus} from portal-workflow-taglib"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"baseResourceURL", String.valueOf(baseResourceURL)
			).putAll(
				workflowStatusDisplayContext.getData(request, locale)
			).build()
		%>'
	/>
</div>