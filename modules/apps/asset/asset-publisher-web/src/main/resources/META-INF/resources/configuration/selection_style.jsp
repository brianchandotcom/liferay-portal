<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<c:if test="<%= !assetPublisherDisplayContext.isSelectionStyleAssetList() %>">

	<%
	String portletResource = ParamUtil.getString(request, "portletResource");
	%>

	<clay:alert
		displayType="warning"
	>
		<liferay-ui:message key="dynamic-and-manual-asset-selection-are-deprecated,-we-recommend-creating-a-collection-from-current-asset-selection-to-enhance-reusability" />

		<liferay-learn:message
			key="read-more-about-asset-publisher-changes"
			resource="asset-publisher-web"
		/>

		<liferay-portlet:actionURL name="/asset_publisher/add_asset_list" portletName="<%= portletResource %>" var="addAssetListURL">
			<portlet:param name="portletResource" value="<%= portletResource %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</liferay-portlet:actionURL>

		<clay:button
			additionalProps='<%=
				HashMapBuilder.<String, Object>put(
					"portletNamespace", PortalUtil.getPortletNamespace(HtmlUtil.escape(portletResource))
				).put(
					"url", addAssetListURL
				).build()
			%>'
			cssClass="mt-2"
			displayType="warning"
			label="create-collection"
			propsTransformer="{CreateAssetListActionButtonPropsTransformer} from asset-publisher-web"
			small="<%= true %>"
		/>
	</clay:alert>
</c:if>

<aui:fieldset markupView="lexicon">
	<aui:input checked="<%= assetPublisherDisplayContext.isSelectionStyleAssetList() %>" id="selectionStyleAssetList" inlineField="<%= true %>" label="collection" name="preferences--selectionStyle--" onChange='<%= liferayPortletResponse.getNamespace() + "chooseSelectionStyle();" %>' type="radio" value="<%= AssetPublisherSelectionStyleConstants.TYPE_ASSET_LIST %>" />

	<div class="d-flex">
		<aui:input checked="<%= assetPublisherDisplayContext.isSelectionStyleDynamic() %>" id="selectionStyleDynamic" inlineField="<%= true %>" label="dynamic" name="preferences--selectionStyle--" onChange='<%= liferayPortletResponse.getNamespace() + "chooseSelectionStyle();" %>' type="radio" value="<%= AssetPublisherSelectionStyleConstants.TYPE_DYNAMIC %>" />

		<span class="lfr-portal-tooltip ml-2" title="<liferay-ui:message key="dynamic-and-manual-asset-selection-are-deprecated,-we-recommend-creating-a-collection-from-current-asset-selection-to-enhance-reusability" />">
			<liferay-frontend:feature-indicator
				type="deprecated"
			/>
		</span>
	</div>

	<div class="d-flex">
		<aui:input checked="<%= assetPublisherDisplayContext.isSelectionStyleManual() %>" id="selectionStyleManual" inlineField="<%= true %>" label="manual" name="preferences--selectionStyle--" onChange='<%= liferayPortletResponse.getNamespace() + "chooseSelectionStyle();" %>' type="radio" value="<%= AssetPublisherSelectionStyleConstants.TYPE_MANUAL %>" />

		<span class="lfr-portal-tooltip ml-2" title="<liferay-ui:message key="dynamic-and-manual-asset-selection-are-deprecated,-we-recommend-creating-a-collection-from-current-asset-selection-to-enhance-reusability" />">
			<liferay-frontend:feature-indicator
				type="deprecated"
			/>
		</span>
	</div>
</aui:fieldset>

<aui:script>
	function <portlet:namespace />chooseSelectionStyle() {
		Liferay.Util.postForm(document.<portlet:namespace />fm, {
			data: {
				cmd: 'selection-style',
			},
		});
	}
</aui:script>