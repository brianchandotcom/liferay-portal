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

<%@ include file="/html/portlet/asset_tags_navigation/init.jsp" %>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="true" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<aui:fieldset>
		<ul class="lfr-tree list-unstyled">
			<li class="tree-item">
				<aui:input label="show-unused-tags" name="preferences--showZeroAssetCount--" type="checkbox" value="<%= showZeroAssetCount %>" />
			</li>

			<li class="tree-item">
				<aui:input name="preferences--showAssetCount--" type="checkbox" value="<%= showAssetCount %>" />

				<ul class="hide lfr-tree list-unstyled" id="<portlet:namespace />assetCountOptions">
					<li class="tree-item">
						<aui:select helpMessage="asset-type-asset-count-help" label="asset-type" name="preferences--classNameId--">
							<aui:option label="any" value="<%= classNameId == 0 %>" />

							<%
							List<AssetRendererFactory> assetRendererFactories = ListUtil.sort(AssetRendererFactoryRegistryUtil.getAssetRendererFactories(company.getCompanyId()), new AssetRendererFactoryTypeNameComparator(locale));

							for (AssetRendererFactory assetRendererFactory : assetRendererFactories) {
							%>

								<aui:option label="<%= ResourceActionsUtil.getModelResource(locale, assetRendererFactory.getClassName()) %>" selected="<%= classNameId == assetRendererFactory.getClassNameId() %>" value="<%= assetRendererFactory.getClassNameId() %>" />

							<%
							}
							%>

						</aui:select>
					</li>
				</ul>
			</li>

			<li class="tree-item">
				<ul class="lfr-tree list-unstyled" id="<portlet:namespace />displayTemplateSettings">
					<div class="display-template">

						<%
						TemplateHandler templateHandler = TemplateHandlerRegistryUtil.getTemplateHandler(AssetTag.class.getName());

						List<String> displayStyles = new ArrayList<String>();

						displayStyles.add("number");
						displayStyles.add("cloud");
						%>

						<liferay-ui:ddm-template-selector
							classNameId="<%= PortalUtil.getClassNameId(templateHandler.getClassName()) %>"
							displayStyle="<%= displayStyle %>"
							displayStyleGroupId="<%= displayStyleGroupId %>"
							displayStyles="<%= displayStyles %>"
							refreshURL="<%= currentURL %>"
						/>
					</div>
				</ul>
			</li>

			<li class="tree-item">
				<aui:input label="max-num-of-tags" name="preferences--maxAssetTags--" type="text" value="<%= maxAssetTags %>" />
			</li>
		</ul>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script use="aui-base">
	var showAssetCount = A.one('#<portlet:namespace />showAssetCount');

	function showHiddenFields() {
		var assetCountOptions = A.one('#<portlet:namespace />assetCountOptions');

		if (showAssetCount && assetCountOptions) {
			if (showAssetCount.get('checked')) {
				assetCountOptions.show();
			}
			else {
				assetCountOptions.hide();
			}
		}
	}

	showHiddenFields();

	showAssetCount.on('change', showHiddenFields);
</aui:script>