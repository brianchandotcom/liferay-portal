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

					<div id="<portlet:namespace />categoriesContainer">
						<div class="lfr-use-categories-header">
							<aui:input checked="<%= cpSearchResultsDisplayContext.useCategories() %>" id="useAssetCategories" label="use-categories" name="preferences--useAssetCategories--" type="checkbox" />
						</div>

						<div class="lfr-use-categories-content toggler-content-collapsed">
							<aui:input id="preferencesAssetCategoryIds" name="preferences--assetCategoryIds--" type="hidden" />

							<liferay-asset:asset-categories-selector
								curCategoryIds="<%= cpSearchResultsDisplayContext.getCategoryIds() %>"
								hiddenInput="assetCategoriesSelectorCategoryIds"
							/>
						</div>
					</div>
				</aui:fieldset>
			</aui:fieldset-group>
		</div>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg btn-primary" name="submitButton" value="save" />
	</aui:button-row>
</aui:form>

<aui:script use="aui-base,event-input">
	var submitButton = A.one('#<portlet:namespace />submitButton');

	submitButton.on(
		'click',
		function() {
			var preferencesAssetCategoryIds = A.one('#<portlet:namespace />preferencesAssetCategoryIds');
			var assetCategoriesSelectorCategoryIds = A.one('#<portlet:namespace />assetCategoriesSelectorCategoryIds');

			preferencesAssetCategoryIds.val(assetCategoriesSelectorCategoryIds.val());

			submitForm(A.one('#<portlet:namespace />fm'));
		}
	);
</aui:script>

<aui:script use="aui-toggler">
	new A.Toggler(
		{
			animated: true,
			content: '#<portlet:namespace />categoriesContainer .lfr-use-categories-content',
			expanded: <%= cpSearchResultsDisplayContext.useCategories() %>,
			header: '#<portlet:namespace />categoriesContainer .lfr-use-categories-header',
			on: {
				animatingChange: function(event) {
					var instance = this;

					var expanded = !instance.get('expanded');

					A.one('#<portlet:namespace />useAssetCategories').attr('checked', expanded);

					if (expanded) {
						A.one('#<portlet:namespace />preferencesAssetCategoryIds').attr('disabled', false);
					}
					else {
						A.one('#<portlet:namespace />preferencesAssetCategoryIds').attr('disabled', true);
					}
				}
			}
		}
	);
</aui:script>