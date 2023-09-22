<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
page import="com.liferay.portal.search.web.internal.date.facet.configuration.DateFacetPortletInstanceConfiguration" %><%@
page import="com.liferay.portal.search.web.internal.date.facet.display.context.DateFacetCalendarDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.date.facet.display.context.DateFacetDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.date.facet.portlet.DateFacetPortlet" %><%@
page import="com.liferay.portal.search.web.internal.facet.display.context.BucketDisplayContext" %>

<portlet:defineObjects />

<%
DateFacetDisplayContext dateFacetDisplayContext = (DateFacetDisplayContext)java.util.Objects.requireNonNull(request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT));

if (dateFacetDisplayContext.isRenderNothing()) {
	return;
}

BucketDisplayContext customRangeBucketDisplayContext = dateFacetDisplayContext.getCustomRangeBucketDisplayContext();
DateFacetCalendarDisplayContext dateFacetCalendarDisplayContext = dateFacetDisplayContext.getDateFacetCalendarDisplayContext();
DateFacetPortletInstanceConfiguration dateFacetPortletInstanceConfiguration = dateFacetDisplayContext.getDateFacetPortletInstanceConfiguration();
%>

<c:if test="<%= !dateFacetDisplayContext.isRenderNothing() %>">
	<aui:form action="#" method="get" name="fm">
		<aui:input cssClass="facet-parameter-name" name="facet-parameter-name" type="hidden" value="<%= HtmlUtil.escapeAttribute(dateFacetDisplayContext.getParameterName()) %>" />
		<aui:input name="start-parameter-name" type="hidden" value="<%= dateFacetDisplayContext.getPaginationStartParameterName() %>" />

		<liferay-ddm:template-renderer
			className="<%= DateFacetPortlet.class.getName() %>"
			contextObjects='<%=
				HashMapBuilder.<String, Object>put(
					"customRangeBucketDisplayContext", customRangeBucketDisplayContext
				).put(
					"customRangeDateFacetTermDisplayContext", customRangeBucketDisplayContext
				).put(
					"dateFacetCalendarDisplayContext", dateFacetCalendarDisplayContext
				).put(
					"dateFacetDisplayContext", dateFacetDisplayContext
				).put(
					"namespace", liferayPortletResponse.getNamespace()
				).build()
			%>'
			displayStyle="<%= dateFacetPortletInstanceConfiguration.displayStyle() %>"
			displayStyleGroupId="<%= dateFacetDisplayContext.getDisplayStyleGroupId() %>"
			entries="<%= dateFacetDisplayContext.getBucketDisplayContexts() %>"
		>
			<liferay-ui:panel-container
				extended="<%= true %>"
				id='<%= liferayPortletResponse.getNamespace() + "facetDatePanelContainer" %>'
				markupView="lexicon"
				persistState="<%= true %>"
			>
				<liferay-ui:panel
					collapsible="<%= true %>"
					cssClass="search-facet"
					id='<%= liferayPortletResponse.getNamespace() + "facetDatePanel" %>'
					markupView="lexicon"
					persistState="<%= true %>"
					title="<%= dateFacetDisplayContext.getDisplayCaption() %>"
				>
					<c:if test="<%= !dateFacetDisplayContext.isNothingSelected() %>">
						<clay:button
							cssClass="btn-unstyled c-mb-4 facet-clear-btn"
							displayType="link"
							id='<%= liferayPortletResponse.getNamespace() + "facetDateClear" %>'
							onClick="Liferay.Search.FacetUtil.clearSelections(event);"
						>
							<strong><liferay-ui:message key="clear" /></strong>
						</clay:button>
					</c:if>

					<ul class="date list-unstyled">

						<%
						for (BucketDisplayContext bucketDisplayContext : dateFacetDisplayContext.getBucketDisplayContexts()) {
						%>

							<li class="facet-value">
								<a href="<%= HtmlUtil.escapeHREF(bucketDisplayContext.getFilterValue()) %>">
									<span class="term-name <%= bucketDisplayContext.isSelected() ? "facet-term-selected" : "facet-term-unselected" %>">
										<c:choose>
											<c:when test="<%= bucketDisplayContext.isSelected() %>">
												<strong><liferay-ui:message key="<%= HtmlUtil.escape(bucketDisplayContext.getBucketText()) %>" /></strong>
											</c:when>
											<c:otherwise>
												<liferay-ui:message key="<%= HtmlUtil.escape(bucketDisplayContext.getBucketText()) %>" />
											</c:otherwise>
										</c:choose>
									</span>

									<c:if test="<%= bucketDisplayContext.isFrequencyVisible() %>">
										<small class="term-count">
											(<%= bucketDisplayContext.getFrequency() %>)
										</small>
									</c:if>
								</a>
							</li>

						<%
						}
						%>

						<li class="facet-value">
							<a href="<%= HtmlUtil.escapeHREF(customRangeBucketDisplayContext.getFilterValue()) %>" id="<portlet:namespace /><%= customRangeBucketDisplayContext.getBucketText() %>-toggleLink">
								<span class="term-name <%= customRangeBucketDisplayContext.isSelected() ? "facet-term-selected" : "facet-term-unselected" %>">
									<c:choose>
										<c:when test="<%= customRangeBucketDisplayContext.isSelected() %>">
											<strong><liferay-ui:message key="<%= HtmlUtil.escape(customRangeBucketDisplayContext.getBucketText()) %>" />&hellip;</strong>
										</c:when>
										<c:otherwise>
											<liferay-ui:message key="<%= HtmlUtil.escape(customRangeBucketDisplayContext.getBucketText()) %>" />&hellip;
										</c:otherwise>
									</c:choose>
								</span>

								<c:if test="<%= customRangeBucketDisplayContext.isSelected() %>">
									<small class="term-count">
										(<%= customRangeBucketDisplayContext.getFrequency() %>)
									</small>
								</c:if>
							</a>
						</li>
						<li class="<%= !dateFacetCalendarDisplayContext.isSelected() ? "hide" : StringPool.BLANK %> date-custom-range" id="<portlet:namespace />customRange">
							<clay:col
								id='<%= liferayPortletResponse.getNamespace() + "customRangeFrom" %>'
								md="6"
							>
								<aui:field-wrapper label="from">
									<liferay-ui:input-date
										cssClass="date-facet-custom-range-input-date-from"
										dayParam="fromDay"
										dayValue="<%= dateFacetCalendarDisplayContext.getFromDayValue() %>"
										disabled="<%= false %>"
										firstDayOfWeek="<%= dateFacetCalendarDisplayContext.getFromFirstDayOfWeek() %>"
										monthParam="fromMonth"
										monthValue="<%= dateFacetCalendarDisplayContext.getFromMonthValue() %>"
										name="fromInput"
										yearParam="fromYear"
										yearValue="<%= dateFacetCalendarDisplayContext.getFromYearValue() %>"
									/>
								</aui:field-wrapper>
							</clay:col>

							<clay:col
								id='<%= liferayPortletResponse.getNamespace() + "customRangeTo" %>'
								md="6"
							>
								<aui:field-wrapper label="to">
									<liferay-ui:input-date
										cssClass="date-facet-custom-range-input-date-to"
										dayParam="toDay"
										dayValue="<%= dateFacetCalendarDisplayContext.getToDayValue() %>"
										disabled="<%= false %>"
										firstDayOfWeek="<%= dateFacetCalendarDisplayContext.getToFirstDayOfWeek() %>"
										monthParam="toMonth"
										monthValue="<%= dateFacetCalendarDisplayContext.getToMonthValue() %>"
										name="toInput"
										yearParam="toYear"
										yearValue="<%= dateFacetCalendarDisplayContext.getToYearValue() %>"
									/>
								</aui:field-wrapper>
							</clay:col>

							<clay:button
								cssClass="date-facet-custom-range-filter-button"
								disabled="<%= dateFacetCalendarDisplayContext.isRangeBackwards() %>"
								displayType="secondary"
								id='<%= liferayPortletResponse.getNamespace() + "searchCustomRangeButton" %>'
								label="search"
								name='<%= liferayPortletResponse.getNamespace() + "searchCustomRangeButton" %>'
							/>
						</li>
					</ul>
				</liferay-ui:panel>
			</liferay-ui:panel-container>
		</liferay-ddm:template-renderer>
	</aui:form>

	<aui:script use="liferay-search-date-facet">
		new Liferay.Search.DateFacetFilter({
			form: A.one('#<portlet:namespace />fm'),
			fromInputDatePicker: Liferay.component(
				'<portlet:namespace />fromInputDatePicker'
			),
			fromInputName: '<portlet:namespace />fromInput',
			namespace: '<portlet:namespace />',
			parameterName:
				'<%= HtmlUtil.escapeAttribute(dateFacetDisplayContext.getParameterName()) %>',
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