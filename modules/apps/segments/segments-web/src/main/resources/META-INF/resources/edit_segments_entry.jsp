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
EditSegmentsEntryDisplayContext editSegmentsEntryDisplayContext = (EditSegmentsEntryDisplayContext)request.getAttribute(SegmentsWebKeys.EDIT_SEGMENTS_ENTRY_DISPLAY_CONTEXT);

String redirect = ParamUtil.getString(request, "redirect", editSegmentsEntryDisplayContext.getRedirect());

String backURL = ParamUtil.getString(request, "backURL", redirect);

SegmentsEntry segmentsEntry = editSegmentsEntryDisplayContext.getSegmentsEntry();

long segmentsEntryId = editSegmentsEntryDisplayContext.getSegmentsEntryId();

String type = editSegmentsEntryDisplayContext.getType();

if (Validator.isNotNull(backURL)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);
}

renderResponse.setTitle(editSegmentsEntryDisplayContext.getTitle(locale));
%>

<liferay-ui:error embed="<%= false %>" exception="<%= SegmentsEntryCriteriaException.class %>" message="invalid-criteria" />
<liferay-ui:error embed="<%= false %>" exception="<%= SegmentsEntryKeyException.class %>" message="key-is-already-used" />

<portlet:actionURL name="updateSegmentsEntry" var="updateSegmentsEntryActionURL" />

<aui:form action="<%= updateSegmentsEntryActionURL %>" method="post" name="editSegmentFm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveSegmentsEntry();" %>'>
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="segmentsEntryId" type="hidden" value="<%= segmentsEntryId %>" />
	<aui:input name="type" type="hidden" value="<%= type %>" />
	<aui:input name="dynamic" type="hidden" value="<%= true %>" />

	<%
	String segmentEditRootElementId = renderResponse.getNamespace() + "-segment-edit-root";
	%>

	<div id="<%= segmentEditRootElementId %>">
		<div class="inline-item my-5 p-5 w-100">
			<span aria-hidden="true" class="loading-animation"></span>
		</div>
	</div>

	<portlet:renderURL var="previewMembersURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="mvcRenderCommandName" value="previewSegmentsEntryUsers" />
		<portlet:param name="segmentsEntryId" value="<%= String.valueOf(segmentsEntryId) %>" />
	</portlet:renderURL>

	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="getSegmentsEntryClassPKsCount" var="getSegmentsEntryClassPKsCountURL" />
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="getSegmentsFieldValueName" var="getSegmentsFieldValueNameURL" />

	<aui:script require='<%= npmResolvedPackageName + "/js/index.es as SegmentEdit" %>'>
		SegmentEdit.default(
			'<%= segmentEditRootElementId %>',
			{
				contributors: <%= editSegmentsEntryDisplayContext.getContributorsJSONArray() %>,
				formId: '<portlet:namespace />editSegmentFm',
				initialMembersCount: <%= editSegmentsEntryDisplayContext.getSegmentsEntryClassPKsCount() %>,
				initialSegmentActive: <%= (segmentsEntry == null) ? false : segmentsEntry.isActive() %>,
				initialSegmentName: '<%= (segmentsEntry != null) ? HtmlUtil.escapeJS(segmentsEntry.getName(locale)) : StringPool.BLANK %>',
				locale: '<%= locale %>',
				portletNamespace: '<portlet:namespace />',
				previewMembersURL: '<%= previewMembersURL %>',
				propertyGroups: <%= editSegmentsEntryDisplayContext.getPropertyGroupsJSONArray(locale) %>,
				redirect: '<%= HtmlUtil.escape(redirect) %>',
				requestMembersCountURL: '<%= getSegmentsEntryClassPKsCountURL %>',
				showInEditMode: <%= editSegmentsEntryDisplayContext.isShowInEditMode() %>,
				source: '<%= editSegmentsEntryDisplayContext.getSource() %>'
			},
			{
				assetsPath: '<%= PortalUtil.getPathContext(request) + "/assets" %>',
				namespace: '<portlet:namespace />',
				requestFieldValueNameURL: '<%= getSegmentsFieldValueNameURL %>',
				spritemap: '<%= themeDisplay.getPathThemeImages() + "/lexicon/icons.svg" %>'
			}
		);
	</aui:script>
</aui:form>

<aui:script>
	function <portlet:namespace />saveSegmentsEntry() {
		submitForm(document.<portlet:namespace />editSegmentFm);
	}
</aui:script>