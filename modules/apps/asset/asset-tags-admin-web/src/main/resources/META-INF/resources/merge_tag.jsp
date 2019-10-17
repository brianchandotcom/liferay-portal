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

<liferay-frontend:edit-form
	action="<%= mergeURL %>"
	method="post"
	name="fm"
	onSubmit="event.preventDefault();"
>
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="groupId" type="hidden" value="<%= scopeGroupId %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset>
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
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script require="metal-dom/src/all/dom as dom">
	var targetTagNameSelect = document.getElementById(
		'<portlet:namespace />targetTagName'
	);

	if (targetTagNameSelect) {
		window['<portlet:namespace />onAddTag'] = function(item) {
			var value = item.value;

			if (value !== undefined) {
				dom.append(
					targetTagNameSelect,
					Liferay.Util.sub(
						'<option value="{0}">{1}</option>',
						value,
						value
					)
				);
			}
		};

		window['<portlet:namespace />onRemoveTag'] = function(item) {
			var value = item.value;

			if (value !== undefined) {
				var targetTagNameOption = targetTagNameSelect.querySelector(
					'option[value="' + value + '"]'
				);

				dom.exitDocument(targetTagNameOption);
			}
		};
	}

	var form = document.<portlet:namespace />fm;
	var mergeTagNamesInput = document.getElementById(
		'<portlet:namespace />mergeTagNames'
	);

	if (form && mergeTagNamesInput && targetTagNameSelect) {
		form.addEventListener('submit', function(event) {
			var mergeTagNames = mergeTagNamesInput.value.split(',');

			if (mergeTagNames.length < 2) {
				alert(
					'<liferay-ui:message arguments="2" key="please-choose-at-least-x-tags" />'
				);

				return;
			}

			var mergeText = Liferay.Util.sub(
				'<liferay-ui:message key="are-you-sure-you-want-to-merge-x-into-x" />',
				mergeTagNames,
				targetTagNameSelect.value
			);

			if (confirm(mergeText)) {
				submitForm(form);
			}
		});
	}
</aui:script>