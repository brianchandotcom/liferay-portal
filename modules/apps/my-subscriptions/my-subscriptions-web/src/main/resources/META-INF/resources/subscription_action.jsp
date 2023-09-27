<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Subscription subscription = (Subscription)row.getObject();

AssetRenderer<?> assetRenderer = MySubscriptionsUtil.getAssetRenderer(subscription.getClassName(), subscription.getClassPK());
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="actions"
	showWhenSingleIcon="<%= true %>"
>

	<%
	String viewURL = null;

	if (assetRenderer != null) {
		viewURL = assetRenderer.getURLViewInContext(liferayPortletRequest, liferayPortletResponse, currentURL);
	}
	else {
		viewURL = MySubscriptionsUtil.getAssetURLViewInContext(themeDisplay, subscription.getClassName(), subscription.getClassPK());
	}
	%>

	<c:if test="<%= viewURL != null %>">
		<liferay-ui:icon
			icon="search"
			markupView="lexicon"
			message="view"
			url="<%= viewURL %>"
		/>
	</c:if>

	<%
	String displayPopupHREF = null;

	if (assetRenderer != null) {
		String displayPopupURL = assetRenderer.getURLView(liferayPortletResponse, LiferayWindowState.POP_UP);

		if (Validator.isNotNull(displayPopupURL)) {
			StringBundler sb = new StringBundler(7);

			sb.append("javascript:");
			sb.append(liferayPortletResponse.getNamespace());
			sb.append("displayPopup('");
			sb.append(displayPopupURL);
			sb.append("', '");
			sb.append(UnicodeLanguageUtil.get(request, "my-subscription"));
			sb.append("');");

			displayPopupHREF = sb.toString();
		}
	}
	%>

	<c:if test="<%= displayPopupHREF != null %>">
		<liferay-ui:icon
			icon="forms"
			markupView="lexicon"
			message="view-in-popup"
			url="<%= displayPopupHREF %>"
		/>
	</c:if>

	<portlet:actionURL name="unsubscribe" var="unsubscribeURL">
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="subscriptionIds" value="<%= String.valueOf(subscription.getSubscriptionId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		icon="times-circle"
		label="<%= true %>"
		markupView="lexicon"
		message="unsubscribe"
		url="<%= unsubscribeURL %>"
	/>
</liferay-ui:icon-menu>