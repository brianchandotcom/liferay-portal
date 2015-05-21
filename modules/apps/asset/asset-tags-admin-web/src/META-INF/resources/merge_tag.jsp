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
String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNull(redirect)) {
	PortletURL portletURL = renderResponse.createRenderURL();

	redirect = portletURL.toString();
}

long[] mergeTagIds = StringUtil.split(ParamUtil.getString(renderRequest, "mergeTagIds"), 0L);

List<String> mergeTagNames = new ArrayList();

for (long mergeTagId : mergeTagIds) {
	AssetTag tag = AssetTagLocalServiceUtil.fetchAssetTag(mergeTagId);

	if (tag == null) {
		continue;
	}

	mergeTagNames.add(tag.getName());
}
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	title="merge-tags"
/>

<portlet:actionURL name="mergeTag" var="mergeURL">
	<portlet:param name="mvcPath" value="/merge_tag.jsp" />
</portlet:actionURL>

<aui:form action="<%= mergeURL %>" method="post" name="fm" onSubmit="event.preventDefault();">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="groupId" type="hidden" value="<%= scopeGroupId %>" />

	<div class="merge-tags">
		<span class="merge-tags-label">
			<liferay-ui:message key="tags-to-merge" />
		</span>

		<liferay-ui:asset-tags-selector
			addCallback="onAddTag"
			allowAddEntry="<%= false %>"
			curTags="<%= StringUtil.merge(mergeTagNames) %>"
			hiddenInput="mergeTagNames"
			id="assetTagsSelector"
			removeCallback="onRemoveTag"
		/>
	</div>

	<div class="target-tag-container">
		<aui:select cssClass="target-tag" label="into-this-tag" name="targetTagName">

			<%
			for (String tagName : mergeTagNames) {
			%>

				<aui:option label="<%= tagName %>" />

			<%
			}
			%>

		</aui:select>
	</div>

	<aui:button-row>
		<aui:button type="submit" value="merge" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script use="aui-base,aui-selector">
	var form = A.one('#<portlet:namespace />fm');

	window['<portlet:namespace />onAddTag'] = function(item) {
		if (item.value === undefined) {
			return;
		}

		var targetTag = AUI.$('#<portlet:namespace />targetTagName');

		var addedValue = item.value;

		targetTag.append(
			$(
				'<option>',
				{
					text: addedValue,
					value: addedValue
				}
			)
		);
	};

	window['<portlet:namespace />onRemoveTag'] = function(item) {
		if (item.value === undefined) {
			return;
		}

		var removedValue = item.value;

		AUI.$('#<portlet:namespace />targetTagName option[value="' + removedValue + '"]').remove();
	};

	form.on(
		'submit',
		function(event) {
			var mergeTagNames = A.one('#<portlet:namespace />mergeTagNames').val();

			var mergeText = '<liferay-ui:message key="are-you-sure-you-want-to-merge-x-into-x" />';

			var targetTag = A.one('#<portlet:namespace />targetTagName');

			var tag = targetTag.one(':selected');

			tag = tag.html().trim();

			mergeText = A.Lang.sub(mergeText, [mergeTagNames.split(','), tag]);

			if (confirm(mergeText)) {
				submitForm(form, form.attr('action'));
			}
		}
	);
</aui:script>