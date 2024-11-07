<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPCategoryContentDisplayContext cpCategoryContentDisplayContext = (CPCategoryContentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

String useAssetCategoryContentCssClass = "lfr-use-asset-category-content ";

if (!cpCategoryContentDisplayContext.useAssetCategory()) {
	useAssetCategoryContentCssClass += "hide";
}

String assetCategoryExternalReferenceCode = "";

AssetCategory assetCategory = cpCategoryContentDisplayContext.getAssetCategory();

if (assetCategory != null) {
	assetCategoryExternalReferenceCode = assetCategory.getExternalReferenceCode();
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
								displayStyleGroupKey="<%= cpCategoryContentDisplayContext.getDisplayStyleGroupKey() %>"
								refreshURL="<%= PortalUtil.getCurrentURL(request) %>"
								showEmptyOption="<%= true %>"
							/>
						</div>

						<div id="<portlet:namespace />assetCategoryContainer">
							<div class="lfr-use-asset-category-header">
								<aui:input checked="<%= cpCategoryContentDisplayContext.useAssetCategory() %>" id="useAssetCategory" label="use-asset-category" name="preferences--useAssetCategory--" type="checkbox" />
							</div>

							<div class="<%= useAssetCategoryContentCssClass %>" id="<portlet:namespace />assetCategoryContent">
								<aui:input id="preferencesAssetCategoryExternalReferenceCode" name="preferences--assetCategoryExternalReferenceCode--" type="text" value="<%= assetCategoryExternalReferenceCode %>" />
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

<liferay-frontend:component
	module="{configuration} from commerce-product-content-category-web"
/>