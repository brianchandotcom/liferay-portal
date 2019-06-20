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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.search.web.internal.custom.facet.display.context.CustomFacetDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.custom.facet.display.context.CustomFacetTermDisplayContext" %>

<portlet:defineObjects />

<style>
	.facet-checkbox-label {
		display: block;
	}

	.facet-term-selected {
		font-weight: 600;
	}

	.facet-term-unselected {
		font-weight: 400;
	}
</style>

<%
CustomFacetDisplayContext customFacetDisplayContext = (CustomFacetDisplayContext)java.util.Objects.requireNonNull(request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT));
%>

<c:choose>
	<c:when test="<%= customFacetDisplayContext.isRenderNothing() %>">
		<aui:input autocomplete="off" name="<%= HtmlUtil.escapeAttribute(customFacetDisplayContext.getParameterName()) %>" type="hidden" value="<%= customFacetDisplayContext.getParameterValue() %>" />
	</c:when>
	<c:otherwise>
		<liferay-ui:panel-container
			extended="<%= true %>"
			id='<%= renderResponse.getNamespace() + "facetCustomPanelContainer" %>'
			markupView="lexicon"
			persistState="<%= true %>"
		>
			<liferay-ui:panel
				collapsible="<%= true %>"
				cssClass="search-facet"
				id='<%= renderResponse.getNamespace() + "facetCustomPanel" %>'
				markupView="lexicon"
				persistState="<%= true %>"
				title="<%= customFacetDisplayContext.getDisplayCaption() %>"
			>
				<aui:form method="post" name="customFacetForm">
					<aui:input autocomplete="off" name="<%= HtmlUtil.escapeAttribute(customFacetDisplayContext.getParameterName()) %>" type="hidden" value="<%= customFacetDisplayContext.getParameterValue() %>" />
					<aui:input cssClass="facet-parameter-name" name="facet-parameter-name" type="hidden" value="<%= customFacetDisplayContext.getParameterName() %>" />

					<aui:fieldset>
						<ul class="list-unstyled">

							<%
							int i = 0;

							for (CustomFacetTermDisplayContext customFacetTermDisplayContext : customFacetDisplayContext.getTermDisplayContexts()) {
								i++;
							%>

								<li class="facet-value">
									<label class="facet-checkbox-label" for="<portlet:namespace />term_<%= i %>">
										<input class="facet-term" data-term-id="<%= customFacetTermDisplayContext.getFieldName() %>" id="<portlet:namespace />term_<%= i %>" name="<portlet:namespace />term_<%= i %>" onChange="Liferay.Search.FacetUtil.changeSelection(event);" type="checkbox" <%= customFacetTermDisplayContext.isSelected() ? "checked" : StringPool.BLANK %> />

										<span class="term-name <%= customFacetTermDisplayContext.isSelected() ? "facet-term-selected" : "facet-term-unselected" %>">
											<%= HtmlUtil.escape(customFacetTermDisplayContext.getFieldName()) %>
										</span>

										<c:if test="<%= customFacetTermDisplayContext.isFrequencyVisible() %>">
											<small class="term-count">
												(<%= customFacetTermDisplayContext.getFrequency() %>)
											</small>
										</c:if>
									</label>
								</li>

							<%
							}
							%>

						</ul>
					</aui:fieldset>

					<c:if test="<%= !customFacetDisplayContext.isNothingSelected() %>">
						<a class="text-default" href="javascript:;" onClick="Liferay.Search.FacetUtil.clearSelections(event);"><small><liferay-ui:message key="clear" /></small></a>
					</c:if>
				</aui:form>
			</liferay-ui:panel>
		</liferay-ui:panel-container>
	</c:otherwise>
</c:choose>

<aui:script use="liferay-search-facet-util"></aui:script>