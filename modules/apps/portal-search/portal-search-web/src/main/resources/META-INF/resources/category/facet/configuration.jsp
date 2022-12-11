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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/learn" prefix="liferay-learn" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/react" prefix="react" %><%@
taglib uri="http://liferay.com/tld/template" prefix="liferay-template" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.learn.LearnMessageUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.search.web.internal.category.facet.configuration.CategoryFacetPortletInstanceConfiguration" %><%@
page import="com.liferay.portal.search.web.internal.category.facet.portlet.CategoryFacetPortlet" %><%@
page import="com.liferay.portal.search.web.internal.category.facet.portlet.CategoryFacetPortletPreferences" %><%@
page import="com.liferay.portal.search.web.internal.category.facet.portlet.CategoryFacetPortletPreferencesImpl" %><%@
page import="com.liferay.portal.search.web.internal.facet.display.context.AssetCategoriesSearchFacetDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.util.PortletPreferencesJspUtil" %>

<portlet:defineObjects />

<%
AssetCategoriesSearchFacetDisplayContext assetCategoriesSearchFacetDisplayContext = new AssetCategoriesSearchFacetDisplayContext(request);

CategoryFacetPortletInstanceConfiguration categoryFacetPortletInstanceConfiguration = assetCategoriesSearchFacetDisplayContext.getCategoryFacetPortletInstanceConfiguration();

CategoryFacetPortletPreferences categoryFacetPortletPreferences = new CategoryFacetPortletPreferencesImpl(java.util.Optional.ofNullable(portletPreferences));
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset
			collapsible="<%= true %>"
			label="display-settings"
		>
			<div class="display-template">
				<liferay-template:template-selector
					className="<%= CategoryFacetPortlet.class.getName() %>"
					displayStyle="<%= categoryFacetPortletInstanceConfiguration.displayStyle() %>"
					displayStyleGroupId="<%= assetCategoriesSearchFacetDisplayContext.getDisplayStyleGroupId() %>"
					refreshURL="<%= configurationRenderURL %>"
					showEmptyOption="<%= true %>"
				/>
			</div>
		</liferay-frontend:fieldset>

		<liferay-frontend:fieldset
			collapsible="<%= true %>"
			label="advanced-configuration"
		>
			<aui:input label="category-parameter-name" name="<%= PortletPreferencesJspUtil.getInputName(CategoryFacetPortletPreferences.PREFERENCE_KEY_PARAMETER_NAME) %>" value="<%= categoryFacetPortletPreferences.getParameterName() %>" />

			<aui:input label="max-terms" name="<%= PortletPreferencesJspUtil.getInputName(CategoryFacetPortletPreferences.PREFERENCE_KEY_MAX_TERMS) %>" value="<%= categoryFacetPortletPreferences.getMaxTerms() %>" />

			<aui:input label="frequency-threshold" name="<%= PortletPreferencesJspUtil.getInputName(CategoryFacetPortletPreferences.PREFERENCE_KEY_FREQUENCY_THRESHOLD) %>" value="<%= categoryFacetPortletPreferences.getFrequencyThreshold() %>" />

			<aui:input label="display-frequencies" name="<%= PortletPreferencesJspUtil.getInputName(CategoryFacetPortletPreferences.PREFERENCE_KEY_FREQUENCIES_VISIBLE) %>" type="checkbox" value="<%= categoryFacetPortletPreferences.isFrequenciesVisible() %>" />

			<div id="<portlet:namespace />selectVocabularies">
				<react:component
					module="js/components/SelectVocabularies"
					props='<%=
						HashMapBuilder.<String, Object>put(
							"disabled", assetCategoriesSearchFacetDisplayContext.isLegacyFieldSelected()
						).put(
							"initialSelectedVocabularyIds", StringUtil.merge(categoryFacetPortletPreferences.getVocabularyIds())
						).put(
							"learnMessages", LearnMessageUtil.getJSONObject("portal-search-web")
						).put(
							"namespace", liferayPortletResponse.getNamespace()
						).put(
							"vocabularyIdsInputName", PortletPreferencesJspUtil.getInputName(CategoryFacetPortletPreferences.PREFERENCE_VOCABULARY_IDS)
						).build()
					%>'
				/>
			</div>

			<c:if test="<%= assetCategoriesSearchFacetDisplayContext.isLegacyFieldSelected() %>">
				<p class="mt-3 sheet-text">
					<liferay-ui:message key="select-vocabularies-configuration-disabled-description" />

					<liferay-learn:message
						key="tag-and-category-facet"
						resource="portal-search-web"
					/>
				</p>
			</c:if>
		</liferay-frontend:fieldset>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>