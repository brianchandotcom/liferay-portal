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
CPSearchResultsDisplayContext cpSearchResultsDisplayContext = (CPSearchResultsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
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
							className="<%= CPSearchResultsPortlet.class.getName() %>"
							displayStyle="<%= cpSearchResultsDisplayContext.getDisplayStyle() %>"
							displayStyleGroupId="<%= cpSearchResultsDisplayContext.getDisplayStyleGroupId() %>"
							refreshURL="<%= PortalUtil.getCurrentURL(request) %>"
							showEmptyOption="<%= true %>"
						/>
					</div>

					<%
					String configurationMethod = cpSearchResultsDisplayContext.getConfigurationMethod();
					%>

					<aui:select id="configurationMethod" name="preferences--configurationMethod--" showEmptyOption="<%= true %>">
						<aui:option label="<%= CPSearchResultsConfigurationConstants.SELECT_CATEGORIES %>" selected="<%= configurationMethod.equals(CPSearchResultsConfigurationConstants.SELECT_CATEGORIES) %>" value="<%= CPSearchResultsConfigurationConstants.SELECT_CATEGORIES %>" />

						<%
						for (String type : cpSearchResultsDisplayContext.getCPDefinitionLinkTypes()) {
						%>

							<aui:option label="<%= type %>" selected="<%= configurationMethod.equals(type) %>" value="<%= type %>" />

						<%
						}
						%>

					</aui:select>

					<%
					String categoriesContainerCssClass = StringPool.BLANK;

					if (!configurationMethod.equals(CPSearchResultsConfigurationConstants.SELECT_CATEGORIES)) {
						categoriesContainerCssClass += "hide";
					}
					%>

					<div class="<%= categoriesContainerCssClass %>" id="<portlet:namespace />categoriesContainer">
						<aui:input id="preferencesAssetCategoryIds" name="preferences--assetCategoryIds--" type="hidden" />

						<liferay-ui:asset-categories-selector
							curCategoryIds="<%= cpSearchResultsDisplayContext.getCategoryIds() %>"
							hiddenInput="assetCategoriesSelectorCategoryIds"
						/>
					</div>
				</aui:fieldset>
			</aui:fieldset-group>
		</div>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" name="submitButton" value="save" />
	</aui:button-row>
</aui:form>

<aui:script use="aui-base,event-input">
	var submitButton = A.one('#<portlet:namespace />submitButton');

	submitButton.on(
		'click',
		function() {
			if (!A.one('#<portlet:namespace />categoriesContainer').hasClass('hide')) {
				var preferencesAssetCategoryIds = A.one('#<portlet:namespace />preferencesAssetCategoryIds');
				var assetCategoriesSelectorCategoryIds = A.one('#<portlet:namespace />assetCategoriesSelectorCategoryIds');

				preferencesAssetCategoryIds.val(assetCategoriesSelectorCategoryIds.val());

				submitForm(A.one('#<portlet:namespace />fm'));
			}
			else {
				submitForm(A.one('#<portlet:namespace />fm'));
			}
		}
	);

	A.one('#<portlet:namespace />configurationMethod').on(
		'change',
		function() {
			if (A.one('#<portlet:namespace />configurationMethod').val() == '<%= CPSearchResultsConfigurationConstants.SELECT_CATEGORIES %>') {
				A.one('#<portlet:namespace />categoriesContainer').removeClass('hide');
			}
			else {
				if (!A.one('#<portlet:namespace />categoriesContainer').hasClass('hide')) {
					A.one('#<portlet:namespace />categoriesContainer').addClass('hide');
				}
			}
		}
	);
</aui:script>