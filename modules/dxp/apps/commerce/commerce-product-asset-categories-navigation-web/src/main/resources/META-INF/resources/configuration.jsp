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
long assetVocabularyId = 0;

if (assetVocabulary != null) {
	assetVocabularyId = assetVocabulary.getVocabularyId();
}
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<div class="portlet-configuration-body-content">
		<div class="container-fluid-1280">
			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset>
					<div class="display-template">
						<liferay-ddm:template-selector
							className="<%= CPAssetCategoriesNavigationPortlet.class.getName() %>"
							displayStyle="<%= cpAssetCategoriesNavigationDisplayContext.getDisplayStyle() %>"
							displayStyleGroupId="<%= cpAssetCategoriesNavigationDisplayContext.getDisplayStyleGroupId() %>"
							refreshURL="<%= PortalUtil.getCurrentURL(request) %>"
							showEmptyOption="<%= true %>"
						/>
					</div>

					<aui:select label="vocabulary" name="preferences--assetVocabularyId--" showEmptyOption="<%= true %>">

						<%
						for (AssetVocabulary curAssetVocabulary : cpAssetCategoriesNavigationDisplayContext.getAssetVocabularies()) {
						%>

							<aui:option label="<%= curAssetVocabulary.getTitle(locale) %>" selected="<%= curAssetVocabulary.getVocabularyId() == assetVocabularyId %>" value="<%= curAssetVocabulary.getVocabularyId() %>" />

						<%
						}
						%>

					</aui:select>
				</aui:fieldset>
			</aui:fieldset-group>
		</div>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />
	</aui:button-row>
</aui:form>