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
String backURL = ParamUtil.getString(request, "backURL");

long powwowMeetingId = ParamUtil.getLong(request, "powwowMeetingId");

PowwowMeeting powwowMeeting = PowwowMeetingLocalServiceUtil.fetchPowwowMeeting(powwowMeetingId);
%>

<liferay-util:html-bottom>
	<script src="<%= HttpUtil.getProtocol(request) %>://cdnjs.cloudflare.com/ajax/libs/zeroclipboard/2.1.6/ZeroClipboard.js" type="text/javascript"></script>
</liferay-util:html-bottom>

<liferay-ui:header
	backURL="<%= backURL %>"
	title="meeting-info"
/>

<div class="meeting-container">
	<div id="<portlet:namespace />errorMessage"></div>

	<%
	boolean displayMeetingActions = true;
	%>

	<%@ include file="/meetings/meeting_body.jspf" %>

	<div class="meeting-url">
		<dt>
			<liferay-ui:message key="meeting-url" />
		</dt>
		<dd>
			<input id="<portlet:namespace />meetingURL" readonly="readonly" type="text" value="<%= PowwowUtil.getInvitationURL(powwowMeetingId, null, request) %>" />

			<button class="zeroclipboard-button" data-clipboard-text="<%= PowwowUtil.getInvitationURL(powwowMeetingId, null, request) %>" data-copied="<liferay-ui:message key="copied" />" data-hover="<liferay-ui:message key="copy-to-clipboard" />" id="<portlet:namespace />copyButton">
				<i class="icon-copy"></i>
			</button>
		</dd>
	</div>

	<c:if test="<%= PowwowServiceProviderUtil.isSupportsOptionPassword(powwowMeeting.getProviderType()) %>">
		<c:if test="<%= Validator.isNotNull(PowwowServiceProviderUtil.getOptionPassword(powwowMeeting.getPowwowMeetingId())) %>">
			<div class="meeting-password">
				<dt>
					<liferay-ui:message key="meeting-password" />
				</dt>
				<dd>
					<%= PowwowServiceProviderUtil.getOptionPassword(powwowMeeting.getPowwowMeetingId()) %>
				</dd>
			</div>
		</c:if>
	</c:if>

	<c:if test="<%= PowwowServiceProviderUtil.isSupportsJoinByPhone(powwowMeeting.getProviderType()) %>">
		<div class="join-by-phone">
			<dt>
				<liferay-ui:message key="join-by-phone" />
			</dt>

			<%
			String joinByPhoneDefaultNumbersString = StringPool.BLANK;

			List<String> joinByPhoneDefaultNumbers = PowwowServiceProviderUtil.getJoinByPhoneDefaultNumbers(powwowMeeting.getProviderType());

			if ((joinByPhoneDefaultNumbers != null) && !joinByPhoneDefaultNumbers.isEmpty()) {
				joinByPhoneDefaultNumbersString = StringUtil.merge(joinByPhoneDefaultNumbers, StringPool.SPACE + LanguageUtil.get(request, "or") + StringPool.SPACE);
			}
			%>

			<dd>
				<div class="join-by-phone-content">
					<div class="default-number">
						<i class="icon-phone"></i><span class="title"><liferay-ui:message key="dial" />:</span> <%= joinByPhoneDefaultNumbersString %>
					</div>

					<div class="access-code">
						<span class="title"><%= LanguageUtil.get(request, PowwowServiceProviderUtil.getJoinByPhoneAccessCodeLabel(powwowMeeting.getProviderType())) %>:</span> <%= PowwowServiceProviderUtil.getJoinByPhoneAccessCode(powwowMeetingId) %>
					</div>

					<div class="international-numbers-toggler" id="<portlet:namespace />internationalNumbersToggler">
						<div class="international-numbers-header toggler-header-collapsed">
							<liferay-ui:message key="international-numbers" />
						</div>

						<div class="international-numbers-content toggler-content-collapsed">
							<div id="<portlet:namespace />internationalNumbersTable"></div>
						</div>
					</div>
				</div>
			</dd>
		</div>
	</c:if>

	<div class="provider">
		<dt>
			<liferay-ui:message key="provider" />
		</dt>
		<dd>
			<%= LanguageUtil.get(request, PowwowServiceProviderUtil.getBrandingLabel(powwowMeeting.getProviderType())) %>
		</dd>
	</div>

	<div class="participants">
		<dt>
			<liferay-ui:message key="participants" />
		</dt>
		<dd>
			<liferay-ui:search-container
				total="<%= PowwowParticipantLocalServiceUtil.getPowwowParticipantsCount(powwowMeetingId) %>"
			>
				<liferay-ui:search-container-results>

					<%
					searchContainer.setResults(PowwowParticipantLocalServiceUtil.getPowwowParticipants(powwowMeetingId));
					%>

				</liferay-ui:search-container-results>

				<liferay-ui:search-container-row
					className="com.liferay.powwow.model.PowwowParticipant"
					escapedModel="<%= true %>"
					keyProperty="powwowParticipantId"
					modelVar="powwowParticipant"
				>

					<%
					String displayName = powwowParticipant.getName();

					if (powwowParticipant.getType() == PowwowParticipantConstants.TYPE_HOST) {
						displayName = LanguageUtil.format(request, "x-host", displayName);
					}
					%>

					<liferay-ui:search-container-column-text
						name="name"
						value="<%= displayName %>"
					/>

					<liferay-ui:search-container-column-text
						name="email-address"
						property="emailAddress"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator />
			</liferay-ui:search-container>
		</dd>
	</div>
</div>

<aui:script use="aui-base,swfdetect,aui-toggler,aui-datatable">
	var copyButton = A.one('#<portlet:namespace />copyButton');

	if (A.SWFDetect.isFlashVersionAtLeast(11, 0, 0) && copyButton) {
		var client = new ZeroClipboard(document.getElementById('<portlet:namespace />copyButton'));

		client.on(
			'error',
			function(event) {
				ZeroClipboard.destroy();
			}
		);

		client.on(
			'ready',
			function(readyEvent) {
				client.on(
					'aftercopy',
					function(event) {
						copyButton.addClass('copied');
					}
				);
			}
		);

		copyButton.on(
			'mouseout',
			function() {
				copyButton.removeClass('copied');
			}
		);
	}
	else {
		if (copyButton) {
			copyButton.hide();
		}

		var meetingURLInput = A.one('#<portlet:namespace />meetingURL');

		if (meetingURLInput) {
			meetingURLInput.removeAttribute('readonly');

			meetingURLInput.addClass('only');
		}
	}

	<c:if test="<%= PowwowServiceProviderUtil.isSupportsJoinByPhone(powwowMeeting.getProviderType()) %>">
		new A.Toggler(
			{
				animated: true,
				container: '#<portlet:namespace />internationalNumbersToggler',
				content: '.international-numbers-content',
				expanded: false,
				header: '.international-numbers-header'
			}
		);

		//Load global phone numbers

		var columns = [
			{
				key: 'lcountry',
				label: 'country'
			},
			{
				key: 'lphone',
				label: 'phone'
			},
			{
				key: 'rcountry',
				label: 'country'
			},
			{
				key: 'rphone',
				label: 'phone'
			}
		];

		var interationalNumbersDisplay = [];

		<%
		Map<String, List<String>> internationalNumbers = PowwowServiceProviderUtil.getJoinByPhoneInternationalNumbers(powwowMeeting.getProviderType());

		if ((internationalNumbers != null) && !internationalNumbers.isEmpty()) {
			for (String country : internationalNumbers.keySet()) {
				for (String number : internationalNumbers.get(country)) {
		%>

					interationalNumbersDisplay.push(
						{
							country: '<%= country %>',
							number: '<%= number %>'
						}
					);

		<%
				}
			}
		}
		%>

		var halfInterationalNumbersDisplay = Math.round(interationalNumbersDisplay.length / 2);

		var dataLeft = interationalNumbersDisplay.slice(0, halfInterationalNumbersDisplay);
		var dataRight = interationalNumbersDisplay.slice(halfInterationalNumbersDisplay);

		var data = [];

		for (var i = 0; i < dataLeft.length; i++) {
			var rcountry = '';
			var rphone = '';

			if (dataRight[i] != undefined) {
				rcountry = dataRight[i].country;
				rphone = dataRight[i].number;
			}

			data.push(
				{
					lcountry: dataLeft[i].country,
					lphone: dataLeft[i].number,
					rcountry: rcountry,
					rphone: rphone
				}
			);
		}

		new A.DataTable(
			{
				className: 'table table-bordered',
				columns: columns,
				data: data
			}
		).render('#<portlet:namespace />internationalNumbersTable');
	</c:if>
</aui:script>