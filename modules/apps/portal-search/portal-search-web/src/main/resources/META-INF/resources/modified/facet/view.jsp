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
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/ddm" prefix="liferay-ddm" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.search.web.internal.modified.facet.configuration.ModifiedFacetPortletInstanceConfiguration" %><%@
page import="com.liferay.portal.search.web.internal.modified.facet.display.context.ModifiedFacetCalendarDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.modified.facet.display.context.ModifiedFacetDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.modified.facet.display.context.ModifiedFacetTermDisplayContext" %>

<portlet:defineObjects />

<%
ModifiedFacetDisplayContext modifiedFacetDisplayContext = (ModifiedFacetDisplayContext)java.util.Objects.requireNonNull(request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT));

if (modifiedFacetDisplayContext.isRenderNothing()) {
	return;
}

ModifiedFacetTermDisplayContext customRangeModifiedFacetTermDisplayContext = modifiedFacetDisplayContext.getCustomRangeModifiedFacetTermDisplayContext();
ModifiedFacetCalendarDisplayContext modifiedFacetCalendarDisplayContext = modifiedFacetDisplayContext.getModifiedFacetCalendarDisplayContext();
ModifiedFacetPortletInstanceConfiguration modifiedFacetPortletInstanceConfiguration = modifiedFacetDisplayContext.getModifiedFacetPortletInstanceConfiguration();
%>

<c:if test="<%= !modifiedFacetDisplayContext.isRenderNothing() %>">
	<aui:form action="#" method="get" name="fm">
		<aui:input autocomplete="off" name="inputFacetName" type="hidden" value="modified" />
		<aui:input cssClass="facet-parameter-name" name="facet-parameter-name" type="hidden" value="<%= HtmlUtil.escapeAttribute(modifiedFacetDisplayContext.getParameterName()) %>" />
		<aui:input name="start-parameter-name" type="hidden" value="<%= modifiedFacetDisplayContext.getPaginationStartParameterName() %>" />

		<liferay-ddm:template-renderer
			className="<%= ModifiedFacetTermDisplayContext.class.getName() %>"
			contextObjects='<%=
				HashMapBuilder.<String, Object>put(
					"customRangeModifiedFacetTermDisplayContext", customRangeModifiedFacetTermDisplayContext
				).put(
					"modifiedFacetCalendarDisplayContext", modifiedFacetCalendarDisplayContext
				).put(
					"modifiedFacetDisplayContext", modifiedFacetDisplayContext
				).put(
					"namespace", liferayPortletResponse.getNamespace()
				).build()
			%>'
			displayStyle="<%= modifiedFacetPortletInstanceConfiguration.displayStyle() %>"
			displayStyleGroupId="<%= modifiedFacetDisplayContext.getDisplayStyleGroupId() %>"
			entries="<%= modifiedFacetDisplayContext.getModifiedFacetTermDisplayContexts() %>"
		>
			<liferay-ui:panel-container
				extended="<%= true %>"
				id='<%= liferayPortletResponse.getNamespace() + "facetModifiedPanelContainer" %>'
				markupView="lexicon"
				persistState="<%= true %>"
			>
				<liferay-ui:panel
					collapsible="<%= true %>"
					cssClass="search-facet"
					id='<%= liferayPortletResponse.getNamespace() + "facetModifiedPanel" %>'
					markupView="lexicon"
					persistState="<%= true %>"
					title="last-modified"
				>
					<ul class="list-unstyled modified">

						<%
						for (ModifiedFacetTermDisplayContext modifiedFacetTermDisplayContext : modifiedFacetDisplayContext.getModifiedFacetTermDisplayContexts()) {
						%>

							<li class="facet-value">
								<a href="<%= HtmlUtil.escapeHREF(modifiedFacetTermDisplayContext.getRangeURL()) %>">
									<span class="term-name <%= modifiedFacetTermDisplayContext.isSelected() ? "facet-term-selected" : "facet-term-unselected" %>">
										<liferay-ui:message key="<%= HtmlUtil.escape(modifiedFacetTermDisplayContext.getLabel()) %>" />
									</span>

									<small class="term-count">
										(<%= modifiedFacetTermDisplayContext.getFrequency() %>)
									</small>
								</a>
							</li>

						<%
						}
						%>

						<li class="facet-value">
							<a href="<%= HtmlUtil.escapeHREF(customRangeModifiedFacetTermDisplayContext.getRangeURL()) %>" id="<portlet:namespace /><%= customRangeModifiedFacetTermDisplayContext.getLabel() + "-toggleLink" %>">
								<span class="term-name <%= customRangeModifiedFacetTermDisplayContext.isSelected() ? "facet-term-selected" : "facet-term-unselected" %>"><liferay-ui:message key="<%= HtmlUtil.escape(customRangeModifiedFacetTermDisplayContext.getLabel()) %>" />&hellip;</span>

								<c:if test="<%= customRangeModifiedFacetTermDisplayContext.isSelected() %>">
									<small class="term-count">
										(<%= customRangeModifiedFacetTermDisplayContext.getFrequency() %>)
									</small>
								</c:if>
							</a>
						</li>
						<li class="<%= !modifiedFacetCalendarDisplayContext.isSelected() ? "hide" : StringPool.BLANK %> modified-custom-range" id="<portlet:namespace />customRange">
							<clay:col
								id='<%= liferayPortletResponse.getNamespace() + "customRangeFrom" %>'
								md="6"
							>
								<aui:field-wrapper label="from">
									<liferay-ui:input-date
										cssClass="modified-facet-custom-range-input-date-from"
										dayParam="fromDay"
										dayValue="<%= modifiedFacetCalendarDisplayContext.getFromDayValue() %>"
										disabled="<%= false %>"
										firstDayOfWeek="<%= modifiedFacetCalendarDisplayContext.getFromFirstDayOfWeek() %>"
										monthParam="fromMonth"
										monthValue="<%= modifiedFacetCalendarDisplayContext.getFromMonthValue() %>"
										name="fromInput"
										yearParam="fromYear"
										yearValue="<%= modifiedFacetCalendarDisplayContext.getFromYearValue() %>"
									/>
								</aui:field-wrapper>
							</clay:col>

							<clay:col
								id='<%= liferayPortletResponse.getNamespace() + "customRangeTo" %>'
								md="6"
							>
								<aui:field-wrapper label="to">
									<liferay-ui:input-date
										cssClass="modified-facet-custom-range-input-date-to"
										dayParam="toDay"
										dayValue="<%= modifiedFacetCalendarDisplayContext.getToDayValue() %>"
										disabled="<%= false %>"
										firstDayOfWeek="<%= modifiedFacetCalendarDisplayContext.getToFirstDayOfWeek() %>"
										monthParam="toMonth"
										monthValue="<%= modifiedFacetCalendarDisplayContext.getToMonthValue() %>"
										name="toInput"
										yearParam="toYear"
										yearValue="<%= modifiedFacetCalendarDisplayContext.getToYearValue() %>"
									/>
								</aui:field-wrapper>
							</clay:col>

							<aui:button cssClass="modified-facet-custom-range-filter-button" disabled="<%= modifiedFacetCalendarDisplayContext.isRangeBackwards() %>" name="searchCustomRangeButton" value="search" />
						</li>
					</ul>

					<c:if test="<%= !modifiedFacetDisplayContext.isNothingSelected() %>">
						<aui:button cssClass="btn-link btn-unstyled facet-clear-btn" onClick="Liferay.Search.FacetUtil.clearSelections(event);" value="clear" />
					</c:if>
				</liferay-ui:panel>
			</liferay-ui:panel-container>
		</liferay-ddm:template-renderer>
	</aui:form>

	<aui:script use="liferay-search-modified-facet">
		new Liferay.Search.ModifiedFacetFilter({
			form: A.one('#<portlet:namespace />fm'),
			fromInputDatePicker: Liferay.component(
				'<portlet:namespace />fromInputDatePicker'
			),
			fromInputName: '<portlet:namespace />fromInput',
			namespace: '<portlet:namespace />',
			searchCustomRangeButton: A.one(
				'#<portlet:namespace />searchCustomRangeButton'
			),
			toInputDatePicker: Liferay.component(
				'<portlet:namespace />toInputDatePicker'
			),
			toInputName: '<portlet:namespace />toInput',
		});

		Liferay.Search.FacetUtil.enableInputs(
			document.querySelectorAll('#<portlet:namespace />fm .facet-term')
		);
	</aui:script>
</c:if>