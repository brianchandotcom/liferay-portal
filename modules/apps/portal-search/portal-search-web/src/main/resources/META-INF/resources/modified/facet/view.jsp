<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
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
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.search.web.internal.facet.display.context.BucketDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.modified.facet.configuration.ModifiedFacetPortletInstanceConfiguration" %><%@
page import="com.liferay.portal.search.web.internal.modified.facet.display.context.ModifiedFacetCalendarDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.modified.facet.display.context.ModifiedFacetDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.modified.facet.portlet.ModifiedFacetPortlet" %>

<portlet:defineObjects />

<%
ModifiedFacetDisplayContext modifiedFacetDisplayContext = (ModifiedFacetDisplayContext)java.util.Objects.requireNonNull(request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT));

if (modifiedFacetDisplayContext.isRenderNothing()) {
	return;
}

BucketDisplayContext customRangeBucketDisplayContext = modifiedFacetDisplayContext.getCustomRangeBucketDisplayContext();
ModifiedFacetCalendarDisplayContext modifiedFacetCalendarDisplayContext = modifiedFacetDisplayContext.getModifiedFacetCalendarDisplayContext();
ModifiedFacetPortletInstanceConfiguration modifiedFacetPortletInstanceConfiguration = modifiedFacetDisplayContext.getModifiedFacetPortletInstanceConfiguration();
%>

<c:if test="<%= !modifiedFacetDisplayContext.isRenderNothing() %>">
	<aui:form action="#" method="get" name="fm">
		<aui:input autocomplete="off" name="inputFacetName" type="hidden" value="modified" />
		<aui:input cssClass="facet-parameter-name" name="facet-parameter-name" type="hidden" value="<%= HtmlUtil.escapeAttribute(modifiedFacetDisplayContext.getParameterName()) %>" />
		<aui:input name="start-parameter-name" type="hidden" value="<%= modifiedFacetDisplayContext.getPaginationStartParameterName() %>" />

		<liferay-ddm:template-renderer
			className="<%= ModifiedFacetPortlet.class.getName() %>"
			contextObjects='<%=
				HashMapBuilder.<String, Object>put(
					"customRangeBucketDisplayContext", customRangeBucketDisplayContext
				).put(
					"customRangeModifiedFacetTermDisplayContext", customRangeBucketDisplayContext
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
			entries="<%= modifiedFacetDisplayContext.getBucketDisplayContexts() %>"
		>
			<clay:panel-group>
				<clay:panel
					collapseClassNames="search-facet"
					displayTitle='<%= LanguageUtil.get(request, "last-modified") %>'
					expanded="<%= true %>"
				>
					<c:if test="<%= !modifiedFacetDisplayContext.isNothingSelected() %>">
						<clay:button
							cssClass="btn-unstyled c-mb-4 facet-clear-btn"
							displayType="link"
							id='<%= liferayPortletResponse.getNamespace() + "facetModifiedClear" %>'
							onClick="Liferay.Search.FacetUtil.clearSelections(event);"
						>
							<strong><liferay-ui:message key="clear" /></strong>
						</clay:button>
					</c:if>

					<ul class="list-unstyled modified">

						<%
						for (BucketDisplayContext bucketDisplayContext : modifiedFacetDisplayContext.getBucketDisplayContexts()) {
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

							<clay:button
								cssClass="modified-facet-custom-range-filter-button"
								disabled="<%= modifiedFacetCalendarDisplayContext.isRangeBackwards() %>"
								displayType="secondary"
								id='<%= liferayPortletResponse.getNamespace() + "searchCustomRangeButton" %>'
								label="search"
								name='<%= liferayPortletResponse.getNamespace() + "searchCustomRangeButton" %>'
							/>
						</li>
					</ul>

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
				</clay:panel>
			</clay:panel-group>
		</liferay-ddm:template-renderer>
	</aui:form>
</c:if>