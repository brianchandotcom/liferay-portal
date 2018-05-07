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
boolean showIconLabel = ((Boolean)request.getAttribute("view.jsp-showIconLabel")).booleanValue();

AssetEntry assetEntry = (AssetEntry)request.getAttribute("view.jsp-assetEntry");
AssetRenderer<?> assetRenderer = (AssetRenderer<?>)request.getAttribute("view.jsp-assetRenderer");

boolean showEditURL = ParamUtil.getBoolean(request, "showEditURL", true);

PortletURL editPortletURL = null;

if (showEditURL && assetRenderer.hasEditPermission(permissionChecker)) {
	PortletURL redirectURL = liferayPortletResponse.createLiferayPortletURL(plid, portletDisplay.getId(), PortletRequest.RENDER_PHASE, false);

	redirectURL.setParameter("mvcPath", "/add_asset_redirect.jsp");

	String fullContentRedirect = (String)request.getAttribute("view.jsp-fullContentRedirect");

	if (fullContentRedirect != null) {
		redirectURL.setParameter("redirect", fullContentRedirect);
	}
	else {
		redirectURL.setParameter("redirect", currentURL);
	}

	redirectURL.setWindowState(LiferayWindowState.POP_UP);

	editPortletURL = assetRenderer.getURLEdit(liferayPortletRequest, liferayPortletResponse, LiferayWindowState.POP_UP, redirectURL);
}

List<AssetEntryAction> assetEntryActions = assetPublisherDisplayContext.getAssetEntryActions(assetEntry.getClassName());
%>

<c:if test="<%= (editPortletURL != null) || ListUtil.isNotEmpty(assetEntryActions) %>">
	<div class="pull-right">
		<liferay-ui:icon-menu
			cssClass="visible-interaction"
			direction="left-side"
			icon="<%= StringPool.BLANK %>"
			markupView="lexicon"
			message="<%= StringPool.BLANK %>"
			showWhenSingleIcon="<%= true %>"
		>
			<c:if test="<%= editPortletURL != null %>">

				<%
				editPortletURL.setParameter("hideDefaultSuccessMessage", Boolean.TRUE.toString());
				editPortletURL.setParameter("showHeader", Boolean.FALSE.toString());

				Map<String, Object> data = new HashMap<String, Object>();

				data.put("destroyOnHide", true);
				data.put("id", HtmlUtil.escape(portletDisplay.getNamespace()) + "editAsset");
				data.put("title", LanguageUtil.format(request, "edit-x", HtmlUtil.escape(assetRenderer.getTitle(locale)), false));
				%>

				<liferay-ui:icon
					data="<%= data %>"
					message='<%= showIconLabel ? LanguageUtil.format(request, "edit-x-x", new Object[] {"hide-accessible", HtmlUtil.escape(assetRenderer.getTitle(locale))}, false) : LanguageUtil.format(request, "edit-x", HtmlUtil.escape(assetRenderer.getTitle(locale)), false) %>'
					method="get"
					url="<%= editPortletURL.toString() %>"
					useDialog="<%= true %>"
				/>
			</c:if>

			<c:if test="<%= ListUtil.isNotEmpty(assetEntryActions) %>">

				<%
				for (AssetEntryAction assetEntryAction : assetEntryActions) {
					if (!assetEntryAction.hasPermission(permissionChecker, assetRenderer)) {
						continue;
					}

					Map<String, Object> data = new HashMap<String, Object>();

					data.put("destroyOnHide", true);
					data.put("title", assetEntryAction.getDialogTitle(locale));
				%>

					<liferay-ui:icon
						data="<%= data %>"
						message="<%= assetEntryAction.getMessage(locale) %>"
						method="get"
						url="<%= assetEntryAction.getDialogURL(request, assetRenderer) %>"
						useDialog="<%= true %>"
					/>

				<%
				}
				%>

			</c:if>
		</liferay-ui:icon-menu>
	</div>
</c:if>