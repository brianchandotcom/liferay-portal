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

List<SegmentsCriteriaContributor> segmentsCriteriaContributors = editSegmentsEntryDisplayContext.getSegmentsCriteriaContributors();

JSONArray jsonContributorsArray = JSONFactoryUtil.createJSONArray();

for (int i = 0; i < segmentsCriteriaContributors.size(); i++) {
	SegmentsCriteriaContributor segmentsCriteriaContributor = segmentsCriteriaContributors.get(i);

	Criteria.Criterion criterion = segmentsCriteriaContributor.getCriterion(editSegmentsEntryDisplayContext.getCriteria());

	JSONObject jsonContributorObject = JSONFactoryUtil.createJSONObject();

	jsonContributorObject.put("conjunctionId", (criterion != null) ? criterion.getConjunction() : StringPool.BLANK);
	jsonContributorObject.put("conjunctionInputId", renderResponse.getNamespace() + "criterionConjunction" + segmentsCriteriaContributor.getKey());
	jsonContributorObject.put("initialQuery", (criterion != null) ? criterion.getFilterString() : StringPool.BLANK);
	jsonContributorObject.put("inputId", renderResponse.getNamespace() + "criterionFilter" + segmentsCriteriaContributor.getKey());
	jsonContributorObject.put("modelLabel", segmentsCriteriaContributor.getLabel(locale));
	jsonContributorObject.put("properties", JSONFactoryUtil.createJSONArray(JSONFactoryUtil.looseSerialize(segmentsCriteriaContributor.getFields(locale))));

	jsonContributorsArray.put(jsonContributorObject);
}
%>

<liferay-ui:error exception="<%= SegmentsEntryCriteriaException.class %>" message="invalid-criteria" />
<liferay-ui:error exception="<%= SegmentsEntryKeyException.class %>" message="key-is-already-used" />

<portlet:actionURL name="updateSegmentsEntry" var="updateSegmentsEntryActionURL" />

<aui:form action="<%= updateSegmentsEntryActionURL %>" method="post" name="editSegmentFm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveSegmentsEntry();" %>'>
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="segmentsEntryId" type="hidden" value="<%= segmentsEntryId %>" />
	<aui:input name="type" type="hidden" value="<%= type %>" />
	<aui:input name="dynamic" type="hidden" value="<%= true %>" />

	<%
	String segmentEditRootElementId = renderResponse.getNamespace() + "-segment-edit-root";
	%>

	<div id="<%= segmentEditRootElementId %>"></div>

	<portlet:renderURL var="previewMembersURL">
		<portlet:param name="mvcRenderCommandName" value="editSegmentsEntryUsers" />
		<portlet:param name="tabs1" value="users" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="segmentsEntryId" value="<%= String.valueOf(segmentsEntryId) %>" />
	</portlet:renderURL>

	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="getSegmentsEntryClassPKsCount" var="getSegmentsEntryClassPKsCountURL" />

	<aui:script require='<%= renderRequest.getAttribute(SegmentsWebKeys.RESOLVED_MODULE_NAME) + "/js/index.es as SegmentEdit" %>'>
		SegmentEdit.default(
			'<%= segmentEditRootElementId %>',
			{
				contributors: <%= jsonContributorsArray %>,
				formId: '<portlet:namespace />editSegmentFm',
				initialMembersCount: <%= editSegmentsEntryDisplayContext.getSegmentsEntryClassPKsCount() %>,
				initialSegmentActive: <%= (segmentsEntry == null) ? false : segmentsEntry.isActive() %>,
				initialSegmentName: '<%= (segmentsEntry != null) ? segmentsEntry.getName(locale) : StringPool.BLANK %>',
				locale: '<%= locale %>',
				portletNamespace: '<portlet:namespace />',
				previewMembersURL: '<%= (segmentsEntry != null) ? previewMembersURL : StringPool.BLANK %>',
				redirect: '<%= HtmlUtil.escape(redirect) %>',
				requestMembersCountURL: '<%= getSegmentsEntryClassPKsCountURL %>'
			},
			{
				assetsPath: '<%= PortalUtil.getPathContext(request) %>/assets/',
				spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
			}
		);
	</aui:script>
</aui:form>

<aui:script>
	function <portlet:namespace />saveSegmentsEntry() {
		submitForm(document.<portlet:namespace />editSegmentFm);
	}
</aui:script>