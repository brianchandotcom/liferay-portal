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

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(LanguageUtil.get(request, "merge-tags"));
%>

<portlet:actionURL name="mergeTag" var="mergeURL">
	<portlet:param name="mvcPath" value="/merge_tag.jsp" />
</portlet:actionURL>

<aui:form action="<%= mergeURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit="event.preventDefault();">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="groupId" type="hidden" value="<%= scopeGroupId %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<label for="<portlet:namespace />assetTagsSelector">
				<liferay-ui:message key="tags" />
			</label>

			<div class="button-holder">
				<liferay-asset:asset-tags-selector
					addCallback="onAddTag"
					allowAddEntry="<%= false %>"
					hiddenInput="mergeTagNames"
					id="assetTagsSelector"
					removeCallback="onRemoveTag"
					tagNames="<%= StringUtil.merge(assetTagsDisplayContext.getMergeTagNames()) %>"
				/>
			</div>

			<aui:select cssClass="target-tag" label="into-this-tag" name="targetTagName">

				<%
				for (String tagName : assetTagsDisplayContext.getMergeTagNames()) {
				%>

					<aui:option label="<%= tagName %>" />

				<%
				}
				%>

			</aui:select>
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script sandbox="<%= true %>">
	var form = $('#<portlet:namespace />fm');

	window['<portlet:namespace />onAddTag'] = function(item) {
		if (item.value !== undefined) {
			var targetTag = $('#<portlet:namespace />targetTagName');

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
		}
	};

	window['<portlet:namespace />onRemoveTag'] = function(item) {
		if (item.value !== undefined) {
			var removedValue = item.value;

			$('#<portlet:namespace />targetTagName option[value="' + removedValue + '"]').remove();
		}
	};

	form.on(
		'submit',
		function(event) {
			var mergeTagNames = $('#<portlet:namespace />mergeTagNames').val();

			var mergeTagNamesArray = mergeTagNames.split(',');

			if (mergeTagNamesArray.length < 2) {
				alert('<liferay-ui:message arguments="2" key="please-choose-at-least-x-tags" />');

				return;
			}

			var mergeText = '<liferay-ui:message key="are-you-sure-you-want-to-merge-x-into-x" />';

			var targetTag = $('#<portlet:namespace />targetTagName');

			var tag = targetTag.find(':selected');

			tag = String(tag.html()).trim();

			mergeText = _.sub(mergeText, mergeTagNamesArray, tag);

			if (confirm(mergeText)) {
				submitForm(form, form.attr('action'));
			}
		}
	);
</aui:script>