<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/layout_set_prototypes/init.jsp" %>

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/layout_set_prototypes/view");
%>

<liferay-ui:error exception="<%= RequiredLayoutSetPrototypeException.class %>" message="you-cannot-delete-site-templates-that-are-used-by-a-site" />

<liferay-util:include page="/html/portlet/layout_set_prototypes/toolbar.jsp" />

<aui:form action="<%= portletURL.toString() %>" method="get" name="fm">
	<liferay-portlet:renderURLParams varImpl="portletURL" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />

	<liferay-ui:search-container
		emptyResultsMessage="no-site-templates-were-found"
		headerNames="name"
		iteratorURL="<%= portletURL %>"
		total="<%= LayoutSetPrototypeLocalServiceUtil.searchCount(company.getCompanyId(), null) %>"
	>
		<aui:input name="deleteLayoutSetPrototypesIds" type="hidden" />

		<liferay-ui:search-container-results
			results="<%= LayoutSetPrototypeLocalServiceUtil.search(company.getCompanyId(), null, searchContainer.getStart(), searchContainer.getEnd(), null) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.LayoutSetPrototype"
			escapedModel="<%= true %>"
			keyProperty="layoutSetPrototypeId"
			modelVar="layoutSetPrototype"
		>

			<%
			String rowURL = null;

			ThemeDisplay siteThemeDisplay = (ThemeDisplay)themeDisplay.clone();

			siteThemeDisplay.setScopeGroupId(layoutSetPrototype.getGroupId());

			PortletURL siteAdministrationURL = PortalUtil.getSiteAdministrationURL(renderResponse, siteThemeDisplay);

			if (siteAdministrationURL != null) {
				rowURL = siteAdministrationURL.toString();
			}
			%>

			<liferay-ui:search-container-column-text
				name="name"
				orderable="<%= true %>"
			>

				<aui:a href="<%= rowURL.toString() %>"><%= layoutSetPrototype.getName(locale) %></aui:a>

				<%
				int mergeFailCount = SitesUtil.getMergeFailCount(layoutSetPrototype);
				%>

				<c:if test="<%= mergeFailCount > PropsValues.LAYOUT_SET_PROTOTYPE_MERGE_FAIL_THRESHOLD %>">
					<liferay-ui:icon
						image="../messages/alert"
						message='<%= LanguageUtil.format(pageContext, "the-propagation-of-changes-from-the-x-has-been-disabled-temporarily-after-x-errors", new Object[] {mergeFailCount, LanguageUtil.get(pageContext, "site-template")}, false) %>'
					/>
				</c:if>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="active"
			>
				<%= LanguageUtil.get(pageContext, layoutSetPrototype.isActive()? "yes" : "no") %>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-jsp
				align="right"
				path="/html/portlet/layout_set_prototypes/layout_set_prototype_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<aui:script use="aui-base,liferay-util-window">
	A.getBody().delegate(
		'click',
		function(event) {
			event.preventDefault();

			var link = event.currentTarget;
			var title = link.get('text');

			Liferay.Util.openWindow(
				{
					id: '<portlet:namespace />' + title,
					title: title,
					uri: link.attr('href')
				}
			);
		},
		'.layoutset-prototype-action a'
	);
</aui:script>