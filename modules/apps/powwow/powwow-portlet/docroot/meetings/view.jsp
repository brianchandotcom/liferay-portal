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
long powwowMeetingId = ParamUtil.getLong(request, "powwowMeetingId");
long powwowParticipantId = ParamUtil.getLong(request, "powwowParticipantId");
String hash = ParamUtil.getString(request, "hash");

if ((powwowMeetingId <= 0) && (powwowParticipantId <= 0)) {
	if (themeDisplay.isSignedIn() && MeetingsPermission.contains(permissionChecker, themeDisplay.getScopeGroupId(), ActionKeys.ADD_MEETING)) {
%>

		<%@ include file="/meetings/meetings.jspf" %>

<%
	}
	else {
		renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		renderRequest.setAttribute(WebKeys.PORTLET_DECORATE, Boolean.FALSE);
	}
}
else {
	PowwowParticipant powwowParticipant = PowwowParticipantLocalServiceUtil.fetchPowwowParticipant(powwowParticipantId);

	if (powwowParticipant == null) {
		if (user.getUserId() > 0) {
			powwowParticipant = PowwowParticipantLocalServiceUtil.fetchPowwowParticipant(powwowMeetingId, user.getUserId());
		}
		else {
			powwowParticipant = PowwowParticipantLocalServiceUtil.fetchPowwowParticipant(powwowMeetingId, user.getEmailAddress());
		}

		if (powwowParticipant != null) {
			powwowParticipantId = powwowParticipant.getPowwowParticipantId();
		}
	}

	PowwowMeeting powwowMeeting = PowwowMeetingLocalServiceUtil.fetchPowwowMeeting(powwowMeetingId);

	boolean powwowMeetingHost = false;

	if ((powwowMeeting != null) && (powwowParticipant != null) && (powwowParticipant.getType() == PowwowParticipantConstants.TYPE_HOST)) {
		powwowMeetingHost = true;
	}
%>

	<div class="message-container" id="<portlet:namespace />messageContainer">
		<c:choose>
			<c:when test="<%= powwowMeeting == null %>">
				<div class="alert alert-error">
					<liferay-ui:message key="the-meeting-you-have-requested-no-longer-exists" />
				</div>
			</c:when>
			<c:when test="<%= (powwowMeeting != null) && (powwowMeeting.getStatus() == PowwowMeetingConstants.STATUS_COMPLETED) && !PowwowServiceProviderUtil.isPowwowMeetingRunning(powwowMeeting.getPowwowMeetingId()) %>">
				<div class="alert alert-error">
					<liferay-ui:message key="the-meeting-you-have-requested-has-already-completed" />
				</div>
			</c:when>
			<c:otherwise>
				<h3>
					<liferay-ui:message key="meeting-info" />
				</h3>

				<c:if test="<%= !powwowMeetingHost && (powwowMeeting.getStatus() == PowwowMeetingConstants.STATUS_SCHEDULED) %>">
					<div class="alert">
						<liferay-ui:message key="the-meeting-you-have-requested-has-not-yet-started" />

						<c:if test="<%= !themeDisplay.isSignedIn() %>">
							<liferay-ui:message key="if-you-are-the-meeting-host,-please-login-to-start-the-meeting" />
						</c:if>
					</div>
				</c:if>

				<%
				boolean displayMeetingActions = false;
				%>

				<%@ include file="/meetings/meeting_body.jspf" %>

				<aui:form name="fm" onSubmit="event.preventDefault();">
					<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
					<aui:input name="powwowMeetingId" type="hidden" value="<%= powwowMeetingId %>" />
					<aui:input name="powwowParticipantId" type="hidden" value="<%= powwowParticipantId %>" />
					<aui:input name="hash" type="hidden" value="<%= hash %>" />

					<aui:model-context bean="<%= powwowParticipant %>" model="<%= PowwowParticipant.class %>" />

					<c:if test="<%= PowwowServiceProviderUtil.isSupportsPresettingParticipantName(powwowMeeting.getProviderType()) %>">
						<aui:input autoFocus="<%= true %>" label="enter-your-full-name" name="name" />
					</c:if>

					<aui:button-row>
						<c:choose>
							<c:when test="<%= powwowMeetingHost && (powwowMeeting.getStatus() == PowwowMeetingConstants.STATUS_SCHEDULED) %>">
								<aui:button type="submit" value="start-meeting" />
							</c:when>
							<c:otherwise>
								<aui:button type="submit" value="join-meeting" />
							</c:otherwise>
						</c:choose>
					</aui:button-row>
				</aui:form>
			</c:otherwise>
		</c:choose>
	</div>

	<aui:script use="aui-io-request,aui-loading-mask-deprecated">
		var form = A.one('#<portlet:namespace />fm');
		var messageContainer = A.one('#<portlet:namespace />messageContainer');

		if (form) {
			form.on(
				'submit',
				function(event) {
					<c:if test="<%= PowwowServiceProviderUtil.isSupportsPresettingParticipantName(powwowMeeting.getProviderType()) %>">
						var name = A.one('#<portlet:namespace />name');

						if (name && !name.val()) {
							name.focus();

							return false;
						}
					</c:if>

					var loadingMask = new A.LoadingMask(
						{
							'strings.loading': '<%= UnicodeLanguageUtil.get(request, "the-meeting-has-not-yet-started.-you-will-be-automatically-connected-once-the-host-arrives.-please-wait") %>',
							target: A.one('.powwow-portlet')
						}
					);

					loadingMask.show();

					var io = A.io.request(
						'<liferay-portlet:actionURL name="joinPowwowMeeting" />',
						{
							dataType: 'JSON',
							form: {
								id: form
							},
							on: {
								complete: function(event, id, obj) {
									var responseText = obj.responseText;

									var responseData = A.JSON.parse(responseText);

									if (responseData.success) {
										loadingMask.hide();

										window.location.href = responseData.joinPowwowMeetingURL;
									}
									else if (responseData.retry) {
										setTimeout(
											function() {
												io.start();
											},
											5000
										);
									}
									else {
										loadingMask.hide();

										messageContainer.html('<span class="alert alert-error"><liferay-ui:message key="the-meeting-you-have-requested-no-longer-exists" /></span>');
									}
								}
							}
						}
					);
				}
			);
		}
	</aui:script>

<%
}
%>