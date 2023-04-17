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

<%@ include file="/init.jsp" %>

<%
EditSegmentsEntryDisplayContext editSegmentsEntryDisplayContext = (EditSegmentsEntryDisplayContext)request.getAttribute(EditSegmentsEntryDisplayContext.class.getName());

String backURL = editSegmentsEntryDisplayContext.getBackURL();

if (Validator.isNotNull(backURL)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);
}

renderResponse.setTitle(editSegmentsEntryDisplayContext.getTitle(locale));
%>

<liferay-ui:error embed="<%= false %>" exception="<%= SegmentsEntryCriteriaException.class %>" message="invalid-criteria" />
<liferay-ui:error embed="<%= false %>" exception="<%= SegmentsEntryKeyException.class %>" message="key-is-already-used" />
<liferay-ui:error embed="<%= false %>" exception="<%= SegmentsEntryNameException.class %>" message="please-enter-a-valid-name" />

<portlet:actionURL name="/segments/update_segments_entry" var="updateSegmentsEntryActionURL" />

<aui:form action="<%= updateSegmentsEntryActionURL %>" method="post" name="editSegmentFm">
	<aui:input name="redirect" type="hidden" value="<%= editSegmentsEntryDisplayContext.getRedirect() %>" />
	<aui:input name="groupId" type="hidden" value="<%= editSegmentsEntryDisplayContext.getGroupId() %>" />
	<aui:input name="segmentsEntryId" type="hidden" value="<%= editSegmentsEntryDisplayContext.getSegmentsEntryId() %>" />
	<aui:input name="segmentsEntryKey" type="hidden" value="<%= editSegmentsEntryDisplayContext.getSegmentsEntryKey() %>" />
	<aui:input name="type" type="hidden" value="<%= editSegmentsEntryDisplayContext.getType() %>" />
	<aui:input name="dynamic" type="hidden" value="<%= true %>" />

	<div id="<%= liferayPortletResponse.getNamespace() %>-segment-edit-root">
		<div class="inline-item my-5 p-5 w-100">
			<span aria-hidden="true" class="loading-animation"></span>
		</div>

		<react:component
			module="js/SegmentsApp.es"
			props="<%= editSegmentsEntryDisplayContext.getData() %>"
		/>
	</div>
</aui:form>