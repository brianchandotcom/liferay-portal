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
String redirect = ParamUtil.getString(request, "redirect", assetCategoriesDisplayContext.getDefaultRedirect());

long vocabularyId = ParamUtil.getLong(request, "vocabularyId");

AssetVocabulary vocabulary = null;

if (vocabularyId > 0) {
	vocabulary = AssetVocabularyServiceUtil.fetchVocabulary(vocabularyId);
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((vocabulary == null) ? LanguageUtil.get(request, "add-vocabulary") : vocabulary.getTitle(locale));
%>

<portlet:actionURL name="editVocabulary" var="editVocabularyURL">
	<portlet:param name="mvcPath" value="/edit_vocabulary.jsp" />
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= editVocabularyURL %>"
	cssClass="container-form-lg"
	name="fm"
	onSubmit='<%= liferayPortletResponse.getNamespace() + "confirmation(event);" %>'
>
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="vocabularyId" type="hidden" value="<%= vocabularyId %>" />

	<liferay-frontend:edit-form-body>
		<liferay-ui:error exception="<%= DuplicateVocabularyException.class %>" message="please-enter-a-unique-name" />
		<liferay-ui:error exception="<%= VocabularyNameException.class %>" message="please-enter-a-valid-name" />

		<aui:model-context bean="<%= vocabulary %>" model="<%= AssetVocabulary.class %>" />

		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset
				collapsed="<%= false %>"
				collapsible="<%= true %>"
				label="details"
			>
				<aui:input autoFocus="<%= true %>" label="name" name="title" placeholder="name" />

				<aui:input name="description" placeholder="description" />

				<aui:input helpMessage="multi-valued-help" inlineLabel="right" label="allow-multiple-categories" labelCssClass="simple-toggle-switch" name="multiValued" type="toggle-switch" value="<%= (vocabulary != null) ? vocabulary.isMultiValued() : true %>" />

				<label><liferay-ui:message key="visibility" /> <liferay-ui:icon-help message="visibility-help" /></label>

				<div class="form-group" id="<portlet:namespace />visibilityOptions">
					<aui:input checked="<%= (vocabulary != null) ? (vocabulary.getVisibilityType() == AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC) : true %>" disabled="<%= !(vocabulary == null) %>" id="visibilityTypePublic" label="public" name="visibilityType" type="radio" value="<%= AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC %>" />

					<aui:input checked="<%= (vocabulary != null) ? (vocabulary.getVisibilityType() == AssetVocabularyConstants.VISIBILITY_TYPE_INTERNAL) : false %>" disabled="<%= !(vocabulary == null) %>" label="internal" name="visibilityType" type="radio" value="<%= AssetVocabularyConstants.VISIBILITY_TYPE_INTERNAL %>" />
				</div>
			</liferay-frontend:fieldset>

			<%@ include file="/edit_vocabulary_settings.jspf" %>

			<c:if test="<%= vocabulary == null %>">
				<liferay-frontend:fieldset
					collapsed="<%= true %>"
					collapsible="<%= true %>"
					label="permissions"
				>
					<liferay-ui:input-permissions
						modelName="<%= AssetVocabulary.class.getName() %>"
					/>
				</liferay-frontend:fieldset>
			</c:if>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script>
	function <portlet:namespace />confirmation(event) {
		<c:if test="<%= vocabulary == null %>">
			var message =
				'<liferay-ui:message key="are-you-sure-you-want-to-create-this-vocabulary-only-with-internal-visibility" />';

			var visibilityTypePublic = document.getElementById(
				'<portlet:namespace />visibilityTypePublic'
			);

			var visibilityTypePublicChecked = visibilityTypePublic.checked;

			if (visibilityTypePublicChecked) {
				message =
					'<liferay-ui:message key="are-you-sure-you-want-to-create-this-vocabulary-with-public-visibility" />';
			}

			if (
				!confirm(
					message +
						' \n\n<liferay-ui:message key="the-action-of-setting-a-vocabulary-either-with-internal-or-public-visibility-cannot-be-reversed" />'
				)
			) {
				event.preventDefault();
			}
		</c:if>
	}
</aui:script>