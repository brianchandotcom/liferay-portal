<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/dynamic_include/init.jsp" %>

<div>
	<react:component
		module="js/pages/entry/activity/ActivitySection.es"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"workflowInstanceId", GetterUtil.getLong(request.getAttribute(WorkflowWebKeys.WORKFLOW_INSTANCE_ID))
			).build()
		%>'
	/>
</div>