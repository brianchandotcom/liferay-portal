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

<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.search.web.internal.facet.display.context.AssetEntriesSearchFacetDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.facet.display.context.AssetEntriesSearchFacetTermDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.type.facet.constants.TypeFacetWebKeys" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<portlet:defineObjects />

<style>
	.facet-checkbox-label {
		display: block;
	}
</style>

<%
AssetEntriesSearchFacetDisplayContext assetEntriesSearchFacetDisplayContext = (AssetEntriesSearchFacetDisplayContext)java.util.Objects.requireNonNull(request.getAttribute(TypeFacetWebKeys.DISPLAY_CONTEXT));
%>

<c:choose>
	<c:when test="<%= assetEntriesSearchFacetDisplayContext.isRenderNothing() %>">
		<aui:input autocomplete="off" name="<%= HtmlUtil.escapeAttribute(assetEntriesSearchFacetDisplayContext.getParameterName()) %>" type="hidden" value="<%= assetEntriesSearchFacetDisplayContext.getParameterValue() %>" />
	</c:when>
	<c:otherwise>
		<liferay-ui:panel-container extended="<%= true %>" id='<%= renderResponse.getNamespace() + "facetAssetEntriesPanelContainer" %>' markupView="lexicon" persistState="<%= true %>">
			<liferay-ui:panel collapsible="<%= true %>" cssClass="search-facet" id='<%= renderResponse.getNamespace() + "facetAssetEntriesPanel" %>' markupView="lexicon" persistState="<%= true %>" title="type">
				<aui:form method="post" name="assetEntriesFacetForm">
					<aui:input autocomplete="off" name="<%= HtmlUtil.escapeAttribute(assetEntriesSearchFacetDisplayContext.getParameterName()) %>" type="hidden" value="<%= assetEntriesSearchFacetDisplayContext.getParameterValue() %>" />
					<aui:input cssClass="facet-parameter-name" name="facet-parameter-name" type="hidden" value="<%= assetEntriesSearchFacetDisplayContext.getParameterName() %>" />

					<aui:fieldset>
						<ul class="asset-type list-unstyled">

							<%
							int i = 0;

							for (AssetEntriesSearchFacetTermDisplayContext assetEntriesSearchFacetTermDisplayContext : assetEntriesSearchFacetDisplayContext.getTermDisplayContexts()) {
								i++;
							%>

								<li class="facet-value">
									<label class="facet-checkbox-label" for="<portlet:namespace />term_<%= i %>">
										<input
											class="facet-term"
											data-term-id="<%= assetEntriesSearchFacetTermDisplayContext.getAssetType() %>"
											id="<portlet:namespace />term_<%= i %>"
											name="<portlet:namespace />term_<%= i %>"
											onChange='<portlet:namespace />changeSelection(event);'
											type="checkbox"
											<%= assetEntriesSearchFacetTermDisplayContext.isSelected() ? "checked" : StringPool.BLANK %>
										/>

										<span class="term-name">
											<%= HtmlUtil.escape(assetEntriesSearchFacetTermDisplayContext.getTypeName()) %>
										</span>

										<c:if test="<%= assetEntriesSearchFacetTermDisplayContext.isFrequencyVisible() %>">
											<small class="term-count">
												(<%= assetEntriesSearchFacetTermDisplayContext.getFrequency() %>)
											</small>
										</c:if>
									</label>
								</li>

							<%
							}
							%>

						</ul>
					</aui:fieldset>

					<c:if test="<%= !assetEntriesSearchFacetDisplayContext.isNothingSelected() %>">
						<a class="text-default" href="javascript:;" onClick="<portlet:namespace />clearSelections(event);"><small><liferay-ui:message key="clear" /></small></a>
					</c:if>
				</aui:form>
			</liferay-ui:panel>
		</liferay-ui:panel-container>
	</c:otherwise>
</c:choose>

<aui:script>
	function <portlet:namespace />addURLParameter(key, value, parameterArray) {
		key = encodeURI(key);
		value = encodeURI(value);

		parameterArray[parameterArray.length] = [key, value].join('=');

		return parameterArray;
	}

	function <portlet:namespace />removeURLParameters(key, parameterArray) {
		key = encodeURI(key);

		var newParameters = [];

		AUI.$.each(
			parameterArray,
			function(index, item) {
				var itemSplit = item.split('=');

				if (itemSplit) {
					if (itemSplit[0] != key) {
						newParameters.push(item);
					}
				}
			}
		);

		return newParameters;
	}

	function <portlet:namespace />setURLParameters(form, selections) {
		var formParameterName = $('#' + form.id + ' input.facet-parameter-name');

		var key = formParameterName[0].value;

		var parameterArray = document.location.search.substr(1).split('&');

		var newParameters = <portlet:namespace />removeURLParameters(key, parameterArray);

		for (var i = 0; i < selections.length; i++) {
			newParameters = <portlet:namespace />addURLParameter(key, selections[i], newParameters);
		}

		document.location.search = newParameters.join('&');
	}

	Liferay.provide(
		window,
		'<portlet:namespace />changeSelection',
		function(event) {
			var form = event.currentTarget.form;

			if (!form) {
				return;
			}

			var selections = [];

			var formCheckboxes = $('#' + form.id + ' input.facet-term');

			formCheckboxes.each(
				function(index, value) {
					if (value.checked) {
						selections.push(value.getAttribute('data-term-id'));
					}
				}
			);

			<portlet:namespace />setURLParameters(form, selections);
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />clearSelections',
		function(event) {
			var form = $(event.currentTarget).closest('form')[0];

			if (!form) {
				return;
			}

			var selections = [];

			<portlet:namespace />setURLParameters(form, selections);
		},
		['aui-base']
	);
</aui:script>