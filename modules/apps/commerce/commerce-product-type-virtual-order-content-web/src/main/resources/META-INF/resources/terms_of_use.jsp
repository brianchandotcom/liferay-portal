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
CommerceVirtualOrderItemContentDisplayContext commerceVirtualOrderItemContentDisplayContext = (CommerceVirtualOrderItemContentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

JournalArticleDisplay articleDisplay = commerceVirtualOrderItemContentDisplayContext.getArticleDisplay();

long commerceVirtualOrderItemId = ParamUtil.getLong(request, "commerceVirtualOrderItemId");
String termsOfUseContent = ParamUtil.getString(request, "termsOfUseContent");
%>

<div class="journal-article-preview p-3">
	<c:choose>
		<c:when test="<%= articleDisplay != null %>">
			<%= articleDisplay.getContent() %>
		</c:when>
		<c:otherwise>
			<%= termsOfUseContent %>
		</c:otherwise>
	</c:choose>

	<aui:button-row>
		<aui:button name="agreeButton" onClick='<%= liferayPortletResponse.getNamespace() + "agreeTermsOfUse();" %>' primary="<%= true %>" value="i-agree" />

		<aui:button name="disagreeButton" onClick='<%= liferayPortletResponse.getNamespace() + "closeDialog();" %>' value="i-disagree" />
	</aui:button-row>
</div>

<aui:script>
	function <portlet:namespace />agreeTermsOfUse() {
		Liferay.Util.getOpener().<portlet:namespace />downloadCommerceVirtualOrderItem(
			'<portlet:namespace />viewTermsOfUseDialog',
			'<%= commerceVirtualOrderItemId %>'
		);
	}

	function <portlet:namespace />closeDialog() {
		Liferay.Util.getOpener().<portlet:namespace />closePopup(
			'<portlet:namespace />viewTermsOfUseDialog'
		);
	}
</aui:script>