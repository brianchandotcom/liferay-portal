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

<%@ include file="/html/portlet/asset_tag_admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(renderRequest, "redirect");

long[] mergeTagIds = StringUtil.split(ParamUtil.getString(renderRequest, "mergeTagIds"), 0L);
%>

<liferay-ui:header
	title="merge-tags"
/>

<portlet:actionURL var="mergeURL">
	<portlet:param name="struts_action" value="/asset_tag_admin/merge_tag" />
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.MERGE %>" />
	<portlet:param name="redirect" value="<%= redirect %>" />
</portlet:actionURL>

<aui:form action="<%= mergeURL %>" name="fm" onSubmit="event.preventDefault();">
	<aui:input name="mergeTagIds" type="hidden" />
	<div class="merge-tags">
		<span class="merge-tags-label">
		   <liferay-ui:message key="tags-to-merge" />
		</span>

		<div class="merge-tags-container" id="<portlet:namespace />mergeTagsContainer">

			<%
			for (long mergeTagId : mergeTagIds) {
				AssetTag tag = AssetTagLocalServiceUtil.getTag(mergeTagId);
			%>

				<div class="merge-tag" data-tag-id="<%= tag.getTagId() %>" data-tag-name="<%= tag.getName() %>">
					<span class="merge-tag-name"><%= tag.getName() %></span>

					<i class="icon-remove-sign"></i>
				</div>

			<%
			}
			%>

		</div>
	</div>

	<div class="target-tag-container">
		<aui:select cssClass="target-tag" label="into-this-tag" name="targetTagId">

			<%
			for (long mergeTagId : mergeTagIds) {
				AssetTag tag = AssetTagLocalServiceUtil.getTag(mergeTagId);
			%>

				<aui:option label="<%= tag.getName() %>" value="<%= tag.getTagId() %>" />

			<%
			}
			%>

		</aui:select>
	</div>

	<c:if test="<%= PropsValues.ASSET_TAG_PROPERTIES_ENABLED %>">
		<aui:input name="overrideTagsProperties" type="checkbox" />
	</c:if>

	<aui:button-row>
		<aui:button type="submit" value="merge" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script use="aui-base">
	A.one('#<portlet:namespace />mergeTagsContainer').delegate(
		'click',
		function(event) {
			var currentTarget = event.currentTarget;

			var mergeTag = currentTarget.ancestor('.merge-tag');

			mergeTag.hide();
		},
		'.icon-remove-sign'
	);

	var form = A.one('#<portlet:namespace />fm');

	form.on(
		'submit',
		function(event) {
			var mergeText = '<liferay-ui:message key="are-you-sure-you-want-to-merge-x-into-x" />';

			var targetTag = A.one('#<portlet:namespace />targetTagId');

			var mergeTagIds = [];
			var mergeTagNames = [];

			A.all('.merge-tag:visible').each(
				function(item, index, collection) {
					mergeTagIds.push(item.attr('data-tag-id'));
					mergeTagNames.push(item.attr('data-tag-name'));
				}
			);

			var tag = targetTag.one(':selected');

			mergeText = A.Lang.sub(mergeText, [mergeTagNames, A.Lang.trim(tag.html())]);

			if (confirm(mergeText)) {
				document.<portlet:namespace />fm.<portlet:namespace />mergeTagIds.value = mergeTagIds;

				submitForm(form, form.attr('action'));
			}
		}
	);
</aui:script>