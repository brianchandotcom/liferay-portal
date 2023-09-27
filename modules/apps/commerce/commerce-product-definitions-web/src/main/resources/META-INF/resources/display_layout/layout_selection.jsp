<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPDefinitionDisplayLayoutDisplayContext cpDefinitionDisplayLayoutDisplayContext = (CPDefinitionDisplayLayoutDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDisplayLayout cpDisplayLayout = cpDefinitionDisplayLayoutDisplayContext.getCPDisplayLayout();

String layoutBreadcrumb = cpDefinitionDisplayLayoutDisplayContext.getLayoutBreadcrumb(cpDisplayLayout);
%>

<aui:input id="pagesContainerInput" ignoreRequestValue="<%= true %>" name="layoutUuid" type="hidden" value="<%= (cpDisplayLayout == null) ? StringPool.BLANK : cpDisplayLayout.getLayoutUuid() %>" />

<aui:field-wrapper cssClass="mt-3" helpMessage="product-display-page-help" label="product-display-page">
	<p class="text-default">
		<span class="<%= Validator.isNull(layoutBreadcrumb) ? "hide" : StringPool.BLANK %>" id="<portlet:namespace />displayPageItemRemove" role="button">
			<aui:icon cssClass="icon-monospaced" image="times" markupView="lexicon" />
		</span>
		<span id="<portlet:namespace />displayPageNameInput">
			<c:choose>
				<c:when test="<%= Validator.isNull(layoutBreadcrumb) %>">
					<span class="text-muted"><liferay-ui:message key="none" /></span>
				</c:when>
				<c:otherwise>
					<%= layoutBreadcrumb %>
				</c:otherwise>
			</c:choose>
		</span>
	</p>
</aui:field-wrapper>

<aui:button name="chooseLayout" value="choose" />