<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/users_stats/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2");

String redirect = ParamUtil.getString(request, "redirect");
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationActionURL" />
<liferay-portlet:renderURL portletConfiguration="true" varImpl="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL.toString() %>" />

	<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="topUsersRankingCounters" persistState="<%= true %>" title="ranking">
		<aui:input label='<%= LanguageUtil.format(pageContext, "rank-on-x", LanguageUtil.format(pageContext, "participation", false), false) %>' name="preferences--rankByParticipation--" type="checkbox" value="<%= rankByParticipation %>" />

		<br />

		<aui:input label='<%= LanguageUtil.format(pageContext, "rank-on-x", LanguageUtil.format(pageContext, "contribution", false), false) %>' name="preferences--rankByContribution--" type="checkbox" value="<%= rankByContribution %>" />
	</liferay-ui:panel>

	<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="topUsersShowCounters" persistState="<%= true %>" title="counters">
	<div id="<portlet:namespace />displayCounters">
		<aui:input label='display-additional-counters' name="preferences--displayAdditionalCounters--" type="checkbox" value="<%= displayAdditionalCounters %>" />
		<aui:fieldset label="">
			<%
			for (int displayCounterIndex : displayCounterIndexes) {
				request.setAttribute("configuration.jsp-index", String.valueOf(displayCounterIndex));
			%>
			<div class="lfr-form-row">
				<div class="row-fields">
					<liferay-util:include page="/html/portlet/users_stats/edit_display_counter.jsp" />
				</div>
			</div>
			<%
			}
			%>
		</aui:fieldset>
	</div>
	<aui:script use="liferay-auto-fields">
		var autoFields = new Liferay.AutoFields(
			{
				contentBox: '#<portlet:namespace />displayCounters > fieldset',
				fieldIndexes: '<portlet:namespace />preferences--displayCounterIndexes--',
				url: '<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="struts_action" value="/portlet_configuration/edit_display_counters" /></portlet:renderURL>'
			}
		).render();
	</aui:script>
	</liferay-ui:panel>
	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>