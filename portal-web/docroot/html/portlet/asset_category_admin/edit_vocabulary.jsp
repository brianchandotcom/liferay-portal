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

<%@ include file="/html/portlet/asset_category_admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

AssetVocabulary vocabulary = (AssetVocabulary)request.getAttribute(WebKeys.ASSET_VOCABULARY);

long vocabularyId = BeanParamUtil.getLong(vocabulary, request, "vocabularyId");
%>

<liferay-ui:header
	title='<%= (vocabulary != null) ? vocabulary.getTitle(locale) : "add-new-vocabulary" %>'
/>

<portlet:actionURL var="editVocabularyURL">
	<portlet:param name="struts_action" value="/asset_category_admin/edit_vocabulary" />
	<portlet:param name="redirect" value="<%= redirect %>" />
</portlet:actionURL>

<aui:form action="<%= editVocabularyURL %>" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= vocabulary == null ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="vocabularyId" type="hidden" value="<%= vocabularyId %>" />

	<liferay-ui:error exception="<%= DuplicateVocabularyException.class %>" message="please-enter-a-unique-name" />
	<liferay-ui:error exception="<%= VocabularyNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= vocabulary %>" model="<%= AssetVocabulary.class %>" />

	<aui:fieldset>
		<div>
			<div class="add-vocabulary-layer asset-category-layer">
				<aui:input autoFocus="<%= true %>" label="name" name="title" />

				<aui:input name="description" />

				<%@ include file="/html/portlet/asset_category_admin/edit_vocabulary_settings.jspf" %>

				<c:choose>
					<c:when test="<%= vocabulary == null %>">
						<aui:field-wrapper cssClass="vocabulary-permissions-actions" label="permissions">
							<liferay-ui:input-permissions
								modelName="<%= AssetVocabulary.class.getName() %>"
							/>
						</aui:field-wrapper>
					</c:when>
				</c:choose>

				<aui:button-row>
					<aui:button type="submit" />

					<aui:button href="<%= redirect %>" type="cancel" />
				</aui:button-row>
			</div>
		</div>
	</aui:fieldset>
</aui:form>