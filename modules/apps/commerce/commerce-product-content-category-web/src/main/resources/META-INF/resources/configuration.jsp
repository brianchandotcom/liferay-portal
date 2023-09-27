<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPCategoryContentDisplayContext cpCategoryContentDisplayContext = (CPCategoryContentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

long assetCategoryId = 0;

AssetCategory assetCategory = cpCategoryContentDisplayContext.getAssetCategory();

if (assetCategory != null) {
	assetCategoryId = assetCategory.getCategoryId();
}
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<div class="portlet-configuration-body-content">
		<div class="container-fluid container-fluid-max-xl">
			<div class="sheet">
				<div class="panel-group panel-group-flush">
					<aui:fieldset>
						<div class="display-template">
							<liferay-template:template-selector
								className="<%= CPCategoryContentPortlet.class.getName() %>"
								displayStyle="<%= cpCategoryContentDisplayContext.getDisplayStyle() %>"
								displayStyleGroupId="<%= cpCategoryContentDisplayContext.getDisplayStyleGroupId() %>"
								refreshURL="<%= PortalUtil.getCurrentURL(request) %>"
								showEmptyOption="<%= true %>"
							/>
						</div>

						<div id="<portlet:namespace />assetCategoryContainer">
							<div class="lfr-use-asset-category-header">
								<aui:input checked="<%= cpCategoryContentDisplayContext.useAssetCategory() %>" id="useAssetCategory" label="use-asset-category" name="preferences--useAssetCategory--" type="checkbox" />
							</div>

							<div class="lfr-use-asset-category-content toggler-content-collapsed">
								<aui:input id="preferencesAssetCategoryId" name="preferences--assetCategoryId--" type="number" value="<%= assetCategoryId %>" />
							</div>
						</div>
					</aui:fieldset>
				</div>
			</div>
		</div>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" name="submitButton" type="submit" value="save" />
	</aui:button-row>
</aui:form>

<aui:script use="aui-toggler">
	new A.Toggler({
		animated: true,
		content:
			'#<portlet:namespace />assetCategoryContainer .lfr-use-asset-category-content',
		expanded: <%= cpCategoryContentDisplayContext.useAssetCategory() %>,
		header:
			'#<portlet:namespace />assetCategoryContainer .lfr-use-asset-category-header',
		on: {
			animatingChange: function (event) {
				var instance = this;

				var expanded = !instance.get('expanded');

				A.one('#<portlet:namespace />useAssetCategory').attr(
					'checked',
					expanded
				);

				if (expanded) {
					A.one('#<portlet:namespace />preferencesAssetCategoryId').attr(
						'disabled',
						false
					);
				}
				else {
					A.one('#<portlet:namespace />preferencesAssetCategoryId').attr(
						'disabled',
						true
					);
				}
			},
		},
	});
</aui:script>