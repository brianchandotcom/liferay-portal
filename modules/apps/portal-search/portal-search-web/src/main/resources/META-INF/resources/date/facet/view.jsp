<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/ddm" prefix="liferay-ddm" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %>

<%@ page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
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
	<aui:form action="#" autocomplete="off" method="get" name="fm">
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
		/>
	</aui:form>

	<liferay-frontend:component
		context='<%=
			HashMapBuilder.<String, Object>put(
				"namespace", liferayPortletResponse.getNamespace()
			).build()
		%>'
		module="{FacetUtil} from portal-search-web"
	/>

	<aui:script use="liferay-search-date-facet">
		new Liferay.Search.DateFacetFilter({
			form: A.one('#<portlet:namespace />fm'),
			fromInputName: '<portlet:namespace />fromInput',
			namespace: '<portlet:namespace />',
			parameterName:
				'<%= HtmlUtil.escapeAttribute(dateFacetDisplayContext.getParameterName()) %>',
			searchCustomRangeButton: A.one(
				'#<portlet:namespace />searchCustomRangeButton'
			),
			searchCustomRangeToggleName:
				'<portlet:namespace /><%= customRangeBucketDisplayContext.getBucketText() %>',
			toInputName: '<portlet:namespace />toInput',
		});
	</aui:script>
</c:if>