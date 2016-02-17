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
String redirect = ParamUtil.getString(request, "redirect");
%>

<aui:fieldset markupView="lexicon">

	<%
	PortletURL redirectURL = renderResponse.createRenderURL();

	redirectURL.setParameter("hideDefaultSuccessMessage", Boolean.TRUE.toString());
	redirectURL.setParameter("mvcPath", "/add_asset_redirect.jsp");
	redirectURL.setParameter("redirect", redirect);
	redirectURL.setWindowState(LiferayWindowState.POP_UP);

	List<Long> addPortletURLsGroupIds = new ArrayList();
	%>

	<aui:select label="scope" name="selectScope">

		<%
		long[] groupIds = assetPublisherDisplayContext.getGroupIds();

		for (long groupId : groupIds) {
			Map<String, PortletURL> addPortletURLs = AssetUtil.getAddPortletURLs(liferayPortletRequest, liferayPortletResponse, groupId, assetPublisherDisplayContext.getClassNameIds(), assetPublisherDisplayContext.getClassTypeIds(), assetPublisherDisplayContext.getAllAssetCategoryIds(), assetPublisherDisplayContext.getAllAssetTagNames(), redirectURL.toString());

			if ((addPortletURLs != null) && !addPortletURLs.isEmpty()) {
				addPortletURLsGroupIds.add(groupId);
		%>

				<aui:option label="<%= HtmlUtil.escape((GroupLocalServiceUtil.getGroup(groupId)).getDescriptiveName(locale)) %>" selected="<%= groupId == scopeGroupId %>" value="<%= groupId %>" />

		<%
			}
		}
		%>

	</aui:select>

	<%
	for (Long groupId : addPortletURLsGroupIds) {
		Map<String, PortletURL> addPortletURLs = AssetUtil.getAddPortletURLs(liferayPortletRequest, liferayPortletResponse, groupId, assetPublisherDisplayContext.getClassNameIds(), assetPublisherDisplayContext.getClassTypeIds(), assetPublisherDisplayContext.getAllAssetCategoryIds(), assetPublisherDisplayContext.getAllAssetTagNames(), redirectURL.toString());
	%>

		<div class='asset-entry-type <%= (groupId == scopeGroupId) ? StringPool.BLANK : "hide" %>' id="<%= liferayPortletResponse.getNamespace() + groupId %>">
			<aui:select cssClass="asset-entry-type-select" label="asset-entry-type" name="selectAssetEntryType">

				<%
				for (Map.Entry<String, PortletURL> entry : addPortletURLs.entrySet()) {
					AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(_getClassName(entry.getKey()));

					String message = _getMessage(entry.getKey(), locale);

					long curGroupId = groupId;

					Group group = GroupLocalServiceUtil.fetchGroup(groupId);

					if (!group.isStagedPortlet(assetRendererFactory.getPortletId()) && !group.isStagedRemotely()) {
						curGroupId = group.getLiveGroupId();
					}

					Map<String, Object> data = new HashMap<String, Object>();

					data.put("title", LanguageUtil.format((HttpServletRequest)pageContext.getRequest(), "new-x", HtmlUtil.escape(message), false));
					data.put("url", _getURL(curGroupId, plid, entry.getValue()));
				%>

					<aui:option data="<%= data %>" label="<%= HtmlUtil.escape(message) %>" />

				<%
				}
				%>

			</aui:select>
		</div>

		<aui:script>
			Liferay.Util.toggleSelectBox('<portlet:namespace />selectScope', '<%= groupId %>', '<portlet:namespace /><%= groupId %>');
		</aui:script>

	<%
	}
	%>

	<aui:button-row>

		<%
		String taglibOnClick = renderResponse.getNamespace() + "addAssetEntry();";
		%>

		<aui:button cssClass="btn-lg" onClick="<%= taglibOnClick %>" value="add" />
	</aui:button-row>
</aui:fieldset>

<aui:script>
	function <portlet:namespace />addAssetEntry() {
		var A = AUI();

		var visibleItem = A.one('.asset-entry-type:not(.hide)');

		var assetEntryTypeSelector = visibleItem.one('.asset-entry-type-select');

		var index = assetEntryTypeSelector.get('selectedIndex');

		var selectedOption = assetEntryTypeSelector.get('options').item(index);

		var title = selectedOption.attr('data-title');
		var url = selectedOption.attr('data-url');

		var dialog = Liferay.Util.getWindow();

		dialog.iframe.set('uri', url);
		dialog.titleNode.html(title);
	}
</aui:script>

<%!
private String _getClassName(String className) {
	int pos = className.indexOf(AssetUtil.CLASSNAME_SEPARATOR);

	if (pos != -1) {
		className = className.substring(0, pos);
	}

	return className;
}

private String _getMessage(String className, Locale locale) {
	String message = null;

	int pos = className.indexOf(AssetUtil.CLASSNAME_SEPARATOR);

	if (pos != -1) {
		message = className.substring(pos + AssetUtil.CLASSNAME_SEPARATOR.length());

		className = className.substring(0, pos);
	}

	AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(className);

	if (pos == -1) {
		message = assetRendererFactory.getTypeName(locale);
	}

	return message;
}

private String _getURL(long groupId, long plid, PortletURL addPortletURL) {
	addPortletURL.setParameter("hideDefaultSuccessMessage", Boolean.TRUE.toString());
	addPortletURL.setParameter("groupId", String.valueOf(groupId));
	addPortletURL.setParameter("showHeader", Boolean.FALSE.toString());

	String addPortletURLString = addPortletURL.toString();

	addPortletURLString = HttpUtil.addParameter(addPortletURLString, "refererPlid", plid);

	return addPortletURLString;
}
%>