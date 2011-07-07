<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/staging_bar/init.jsp" %>

<%
long layoutRevisionId = ParamUtil.getLong(request, "layoutRevisionId");
%>

<div class="aui-helper-hidden" id="<portlet:namespace />addRootLayoutRevision">
	<portlet:actionURL var="addVariationURL">
		<portlet:param name="struts_action" value="/staging_bar/edit_layouts" />
		<portlet:param name="<%= Constants.CMD %>" value="add_layout_variation" />
		<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
		<portlet:param name="mergeLayoutRevisionId" value="<%= String.valueOf(layoutRevisionId) %>" />
		<portlet:param name="workflowAction" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />
	</portlet:actionURL>

	<aui:form action="<%= addVariationURL %>" method="post" name="fmVariation">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<div class="portlet-msg-info">
			<liferay-ui:message key="new-page-variation-help" />
		</div>

		<aui:input label="page-variation-name" name="variationName" value="" />

		<aui:button-row>
			<aui:button type="submit" />
		</aui:button-row>
	</aui:form>
</div>