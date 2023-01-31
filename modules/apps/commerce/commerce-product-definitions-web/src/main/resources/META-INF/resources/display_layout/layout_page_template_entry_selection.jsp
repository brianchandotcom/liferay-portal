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
CPDefinitionDisplayLayoutDisplayContext cpDefinitionDisplayLayoutDisplayContext = (CPDefinitionDisplayLayoutDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDisplayLayout cpDisplayLayout = cpDefinitionDisplayLayoutDisplayContext.getCPDisplayLayout();

String layoutPageTemplateEntryName = cpDefinitionDisplayLayoutDisplayContext.getLayoutPageTemplateEntryName(cpDisplayLayout);
%>

<aui:input id="pagesContainerInput" ignoreRequestValue="<%= true %>" name="layoutPageTemplateEntryUuid" type="hidden" value="<%= (cpDisplayLayout == null) ? StringPool.BLANK : cpDisplayLayout.getLayoutPageTemplateEntryUuid() %>" />

<aui:field-wrapper cssClass="mt-3" helpMessage="product-display-page-help" label="product-display-page">
	<p class="text-default">
		<span class="<%= Validator.isNull(layoutPageTemplateEntryName) ? "hide" : StringPool.BLANK %>" id="<portlet:namespace />displayPageItemRemove" role="button">
			<aui:icon cssClass="icon-monospaced" image="times" markupView="lexicon" />
		</span>
		<span id="<portlet:namespace />displayPageNameInput">
			<c:choose>
				<c:when test="<%= Validator.isNull(layoutPageTemplateEntryName) %>">
					<span class="text-muted"><liferay-ui:message key="none" /></span>
				</c:when>
				<c:otherwise>
					<%= layoutPageTemplateEntryName %>
				</c:otherwise>
			</c:choose>
		</span>
	</p>
</aui:field-wrapper>

<aui:button name="chooseLayoutPageTemplateEntry" value="choose" />