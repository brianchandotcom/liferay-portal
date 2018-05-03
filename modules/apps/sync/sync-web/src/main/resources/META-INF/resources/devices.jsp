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
String tabs1 = ParamUtil.getString(request, "tabs1", "devices");

String keywords = ParamUtil.getString(request, "keywords");

int delta = ParamUtil.getInteger(request, "delta", SearchContainer.DEFAULT_DELTA);
String orderByCol = ParamUtil.getString(request, "orderByCol", "name");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("delta", String.valueOf(delta));
%>

<liferay-frontend:management-bar>
	<c:if test="<%= Validator.isNull(keywords) %>">
		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-display-buttons
				displayViews='<%= new String[] {"list"} %>'
				portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
				selectedDisplayStyle="list"
			/>
		</liferay-frontend:management-bar-buttons>

		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all"} %>'
				portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
			/>

			<liferay-frontend:management-bar-sort
				orderByCol="<%= orderByCol %>"
				orderByType="<%= orderByType %>"
				orderColumns='<%= new String[] {"build", "last-seen", "name", "type"} %>'
				portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
			/>

			<%
			PortletURL searchURL = renderResponse.createRenderURL();

			searchURL.setParameter("tabs1", tabs1);
			%>

			<li>
				<aui:form action="<%= searchURL.toString() %>" name="searchFm">
					<liferay-ui:input-search
						markupView="lexicon"
						placeholder='<%= LanguageUtil.get(request, "search") %>'
					/>
				</aui:form>
			</li>
		</liferay-frontend:management-bar-filters>
	</c:if>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<aui:form method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<liferay-ui:search-container
			emptyResultsMessage="no-devices-were-found"
			iteratorURL="<%= portletURL %>"
		>

			<%
			List<SyncDevice> syncDevices = new ArrayList<>();

			OrderByComparator orderByComparator = null;

			if (orderByCol.equals("name")) {
				orderByComparator = OrderByComparatorFactoryUtil.create("SyncDevice", "userName", orderByType.equals("asc"));
			}
			else if (orderByCol.equals("build")) {
				orderByComparator = OrderByComparatorFactoryUtil.create("SyncDevice", "buildNumber", orderByType.equals("asc"));
			}
			else if (orderByCol.equals("last-seen")) {
				orderByComparator = OrderByComparatorFactoryUtil.create("SyncDevice", "modifiedDate", orderByType.equals("asc"));
			}
			else {
				OrderByComparatorFactoryUtil.create("SyncDevice", orderByCol, orderByType.equals("asc"));
			}

			String portletId = (String)request.getAttribute(WebKeys.PORTLET_ID);

			if (portletId.equals(SyncPortletKeys.SYNC_ADMIN_PORTLET)) {
				syncDevices = SyncDeviceLocalServiceUtil.search(themeDisplay.getCompanyId(), keywords, searchContainer.getStart(), searchContainer.getEnd(), orderByComparator);
			}
			else {
				syncDevices = SyncDeviceLocalServiceUtil.getSyncDevices(themeDisplay.getUserId(), searchContainer.getStart(), searchContainer.getEnd(), orderByComparator);
			}
			%>

			<liferay-ui:search-container-results
				results="<%= syncDevices %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.sync.model.SyncDevice"
				escapedModel="<%= true %>"
				keyProperty="syncDeviceId"
				modelVar="syncDevice"
			>
				<liferay-ui:search-container-column-text
					cssClass="content-column name-column title-column"
					name="name"
					property="userName"
				/>

				<liferay-ui:search-container-column-text
					name="location"
					value="<%= syncDevice.getHostname() %>"
				/>

				<liferay-ui:search-container-column-text
					name="type"
					orderable="<%= true %>"
				/>

				<liferay-ui:search-container-column-text
					name="build"
					property="buildNumber"
				/>

				<liferay-ui:search-container-column-date
					name="last-seen"
					property="modifiedDate"
				/>

				<liferay-ui:search-container-column-text
					name="status"
					translate="<%= true %>"
					value="<%= SyncDeviceConstants.getStatusLabel(syncDevice.getStatus()) %>"
				/>

				<liferay-ui:search-container-column-jsp
					align="right"
					cssClass="entry-action-column"
					path="/devices_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>