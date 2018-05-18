<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPCategoryContentDisplayContext cpCategoryContentDisplayContext = (CPCategoryContentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
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
							<aui:input id="preferencesAssetCategoryId" name="preferences--assetCategoryId--" type="number" />
						</div>
					</div>
				</aui:fieldset>
			</aui:fieldset-group>
		</div>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" name="submitButton" type="submit" value="save" />
	</aui:button-row>
</aui:form>

<aui:script use="aui-toggler">
	new A.Toggler(
		{
			animated: true,
			content: '#<portlet:namespace />assetCategoryContainer .lfr-use-asset-category-content',
			expanded: <%= cpCategoryContentDisplayContext.useAssetCategory() %>,
			header: '#<portlet:namespace />assetCategoryContainer .lfr-use-asset-category-header',
			on: {
				animatingChange: function(event) {
					var instance = this;

					var expanded = !instance.get('expanded');

					A.one('#<portlet:namespace />useAssetCategory').attr('checked', expanded);

					if (expanded) {
						A.one('#<portlet:namespace />preferencesAssetCategoryId').attr('disabled', false);
					}
					else {
						A.one('#<portlet:namespace />preferencesAssetCategoryId').attr('disabled', true);
					}
				}
			}
		}
	);
</aui:script>