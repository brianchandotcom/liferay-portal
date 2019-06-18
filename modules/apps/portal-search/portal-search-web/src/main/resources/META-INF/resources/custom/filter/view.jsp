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

<%@ include file="/custom/init.jsp" %>

<%
CustomFilterDisplayContext customFilterDisplayContext = (CustomFilterDisplayContext)java.util.Objects.requireNonNull(request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT));
%>

<c:if test="<%= !customFilterDisplayContext.isRenderNothing() %>">
	<liferay-ui:panel-container
		extended="<%= true %>"
		id='<%= renderResponse.getNamespace() + "filterCustomPanelContainer" %>'
		markupView="lexicon"
		persistState="<%= true %>"
	>
		<liferay-ui:panel
			collapsible="<%= true %>"
			cssClass="search-facet"
			id='<%= renderResponse.getNamespace() + "filterCustomPanel" %>'
			markupView="lexicon"
			persistState="<%= true %>"
			title="<%= HtmlUtil.escapeAttribute(customFilterDisplayContext.getHeading()) %>"
		>
			<aui:form action="<%= customFilterDisplayContext.getSearchURL() %>" method="get" name="customFilterForm">
				<aui:input cssClass="custom-filter-value-input" data-qa-id="customFilterValueInput" disabled="<%= customFilterDisplayContext.isImmutable() %>" id="<%= renderResponse.getNamespace() + StringUtil.randomId() %>" label="" name="<%= HtmlUtil.escapeAttribute(customFilterDisplayContext.getParameterName()) %>" useNamespace="<%= false %>" value="<%= HtmlUtil.escapeAttribute(customFilterDisplayContext.getFilterValue()) %>" />

				<clay:button
					ariaLabel='<%= LanguageUtil.get(request, "apply") %>'
					disabled="<%= customFilterDisplayContext.isImmutable() %>"
					elementClasses="custom-filter-apply-button"
					label='<%= LanguageUtil.get(request, "apply") %>'
					size="sm"
					style="secondary"
					type="submit"
				/>
			</aui:form>
		</liferay-ui:panel>
	</liferay-ui:panel-container>

	<aui:script use="liferay-search-custom-filter">
		new Liferay.Search.CustomFilter(A.one('#<portlet:namespace/>customFilterForm'));
	</aui:script>
</c:if>