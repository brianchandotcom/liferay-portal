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

long vocabularyId = ParamUtil.getLong(request, "vocabularyId");

AssetVocabulary vocabulary = null;

if (vocabularyId > 0) {
	vocabulary = AssetVocabularyServiceUtil.fetchVocabulary(vocabularyId);
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(((vocabulary == null) ? LanguageUtil.get(request, "add-new-vocabulary") : vocabulary.getTitle(locale)));
%>

<portlet:actionURL name="editVocabulary" var="editVocabularyURL">
	<portlet:param name="mvcPath" value="/edit_vocabulary.jsp" />
</portlet:actionURL>

<aui:form action="<%= editVocabularyURL %>" cssClass="container-fluid-1280" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="vocabularyId" type="hidden" value="<%= vocabularyId %>" />

	<liferay-ui:error exception="<%= DuplicateVocabularyException.class %>" message="please-enter-a-unique-name" />
	<liferay-ui:error exception="<%= VocabularyNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= vocabulary %>" model="<%= AssetVocabulary.class %>" />

	<aui:fieldset-group>
		<aui:fieldset>
			<aui:input autoFocus="<%= true %>" label="name" name="title" />

			<aui:input name="description" />

			<aui:input helpMessage="multi-valued-help" label="allow-multiple-categories" name="multiValued" type="toggle-switch" value="<%= (vocabulary != null) ? vocabulary.isMultiValued() : true %>" />
		</aui:fieldset>

		<%@ include file="/edit_vocabulary_settings.jspf" %>

		<c:if test="<%= vocabulary == null %>">
			<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="permissions">
				<liferay-ui:input-permissions
					modelName="<%= AssetVocabulary.class.getName() %>"
				/>
			</aui:fieldset>
		</c:if>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>