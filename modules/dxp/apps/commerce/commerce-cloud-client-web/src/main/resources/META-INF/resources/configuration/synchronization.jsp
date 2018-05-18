<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
EditConfigurationDisplayContext editConfigurationDisplayContext = (EditConfigurationDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceCloudClientConfiguration commerceCloudClientConfiguration = editConfigurationDisplayContext.getCommerceCloudClientConfiguration();
JSONObject projectConfigurationJSONObject = editConfigurationDisplayContext.getProjectConfiguration();
String redirect = editConfigurationDisplayContext.getViewCategoryURL();

String callbackHost = projectConfigurationJSONObject.getString("callbackHost");

boolean pushSynchronizationEnabled = false;

if (Validator.isNotNull(callbackHost)) {
	pushSynchronizationEnabled = true;
}
else {
	callbackHost = editConfigurationDisplayContext.getDefaultCallbackHost();
}
%>

<aui:input name="<%= Constants.CMD %>" type="hidden" value="synchronization" />

<div class="sheet">
	<aui:fieldset>
		<c:choose>
			<c:when test='<%= projectConfigurationJSONObject.has("exception") %>'>
				<div class="alert alert-danger">
					<liferay-ui:message key="commerce-cloud-is-temporarily-unavailable-or-not-configured-properly" />
				</div>
			</c:when>
			<c:otherwise>
				<aui:input checked="<%= pushSynchronizationEnabled %>" id="pushSynchronizationEnabled" label="get-new-data-as-soon-as-it-is-available" name="pushSynchronizationEnabled" type="radio" value="true" />

				<div class="<%= pushSynchronizationEnabled ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />pushSynchronizationOptions">
					<aui:input name="callbackHost" value="<%= callbackHost %>" />
				</div>

				<aui:input checked="<%= !pushSynchronizationEnabled %>" id="pushSynchronizationDisabled" label="get-new-data-periodically" name="pushSynchronizationEnabled" type="radio" value="false" />

				<div class="<%= pushSynchronizationEnabled ? "hide" : StringPool.BLANK %>" id="<portlet:namespace />pullSynchronizationOptions">
					<aui:input label="check-for-new-forecasts-every" name="forecastingEntriesCheckInterval" suffix="minutes" value="<%= commerceCloudClientConfiguration.forecastingEntriesCheckInterval() %>" />
				</div>
			</c:otherwise>
		</c:choose>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</div>

<aui:script>
	Liferay.Util.toggleRadio('<portlet:namespace />pushSynchronizationDisabled', '<portlet:namespace />pullSynchronizationOptions', '<portlet:namespace />pushSynchronizationOptions');
	Liferay.Util.toggleRadio('<portlet:namespace />pushSynchronizationEnabled', '<portlet:namespace />pushSynchronizationOptions', '<portlet:namespace />pullSynchronizationOptions');
</aui:script>