<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/rss_settings/init.jsp" %>

<%
String displayStyle = (String)request.getAttribute("liferay-rss:rss-settings:displayStyle");
String[] displayStyles = (String[])request.getAttribute("liferay-rss:rss-settings:displayStyles");
String feedType = (String)request.getAttribute("liferay-rss:rss-settings:feedType");
%>

<div class="taglib-rss-settings">
	<aui:input inlineLabel="right" label="enable-rss-subscription" labelCssClass="simple-toggle-switch" name="preferences--enableRss--" type="toggle-switch" value='<%= GetterUtil.getBoolean((String)request.getAttribute("liferay-rss:rss-settings:enabled")) %>' />

	<div id="<portlet:namespace />rssOptions">
		<c:if test='<%= GetterUtil.getBoolean((String)request.getAttribute("liferay-rss:rss-settings:nameEnabled")) %>'>
			<clay:row>
				<clay:col>
					<aui:input label="rss-feed-name" name="preferences--rssName--" type="text" value='<%= (String)request.getAttribute("liferay-rss:rss-settings:name") %>' />
				</clay:col>
			</clay:row>
		</c:if>

		<clay:row>
			<clay:col
				md="4"
			>
				<aui:select label="maximum-items-to-display" name="preferences--rssDelta--" value='<%= GetterUtil.getInteger((String)request.getAttribute("liferay-rss:rss-settings:delta")) %>'>
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
			</clay:col>

			<clay:col
				md="4"
			>
				<aui:select label="display-style" name="preferences--rssDisplayStyle--">

					<%
					for (String curDisplayStyle : displayStyles) {
					%>

						<aui:option label="<%= curDisplayStyle %>" selected="<%= displayStyle.equals(curDisplayStyle) %>" />

					<%
					}
					%>

				</aui:select>
			</clay:col>

			<clay:col
				md="4"
			>
				<aui:select label="format" name="preferences--rssFeedType--">

					<%
					for (String type : RSSUtil.FEED_TYPES) {
					%>

						<aui:option label="<%= RSSUtil.getFeedTypeName(type) %>" selected="<%= feedType.equals(type) %>" value="<%= type %>" />

					<%
					}
					%>

				</aui:select>
			</clay:col>
		</clay:row>
	</div>
</div>

<aui:script>
	Liferay.Util.toggleBoxes(
		'<portlet:namespace />enableRss',
		'<portlet:namespace />rssOptions'
	);
</aui:script>