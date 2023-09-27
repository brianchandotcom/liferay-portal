<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset>
			<aui:select label="maximum-activities-to-load-at-once" name="preferences--max--" value="<%= socialActivitiesDisplayContext.getMax() %>">
				<aui:option label="1" />
				<aui:option label="2" />
				<aui:option label="3" />
				<aui:option label="4" />
				<aui:option label="5" />
				<aui:option label="10" />
				<aui:option label="15" />
				<aui:option label="20" />
				<aui:option label="25" />
				<aui:option label="30" />
				<aui:option label="40" />
				<aui:option label="50" />
				<aui:option label="60" />
				<aui:option label="70" />
				<aui:option label="80" />
				<aui:option label="90" />
				<aui:option label="100" />
			</aui:select>
		</liferay-frontend:fieldset>

		<c:if test="<%= PortalUtil.isRSSFeedsEnabled() %>">
			<liferay-rss:rss-settings
				delta="<%= socialActivitiesDisplayContext.getRSSDelta() %>"
				displayStyle="<%= socialActivitiesDisplayContext.getRSSDisplayStyle() %>"
				enabled="<%= socialActivitiesDisplayContext.isRSSEnabled() %>"
				feedType="<%= socialActivitiesDisplayContext.getRSSFeedType() %>"
			/>
		</c:if>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>