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
portletURL.setWindowState(WindowState.MAXIMIZED);

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<liferay-ui:error exception="<%= NoSuchMessageException.class %>" message="the-message-could-not-be-found" />
<liferay-ui:error exception="<%= PrincipalException.class %>" message="you-do-not-have-the-required-permissions" />
<liferay-ui:error exception="<%= RequiredMessageException.class %>" message="you-cannot-delete-a-root-message-that-has-more-than-one-immediate-reply" />

<%@ include file="/message_boards.jspf" %>

<aui:script>
	window['<portlet:namespace />deleteMBMessages'] = function (dicussion) {
		var deleteMBMessageIds = Liferay.Util.listCheckedExcept(
			document.<portlet:namespace />fm,
			'<portlet:namespace />allRowIds'
		);

		if (
			deleteMBMessageIds &&
			confirm(
				'<%= UnicodeLanguageUtil.get(portletConfig.getResourceBundle(locale), "are-you-sure-you-want-to-delete-the-selected-messages") %>'
			)
		) {
			document.<portlet:namespace />fm.<portlet:namespace />deleteMBMessageIds.value = deleteMBMessageIds;

			if (dicussion) {
				submitForm(
					document.<portlet:namespace />fm,
					'<portlet:actionURL name="deleteDiscussionMBMessages"><portlet:param name="redirect" value="<%= portletURL.toString() %>" /></portlet:actionURL>'
				);
			}
			else {
				submitForm(
					document.<portlet:namespace />fm,
					'<portlet:actionURL name="deleteMBMessages"><portlet:param name="redirect" value="<%= portletURL.toString() %>" /></portlet:actionURL>'
				);
			}
		}
	};

	window['<portlet:namespace />notSpamMBMessages'] = function () {
		var notSpamMBMessageIds = Liferay.Util.listCheckedExcept(
			document.<portlet:namespace />fm,
			'<portlet:namespace />allRowIds'
		);

		if (
			notSpamMBMessageIds &&
			confirm(
				'<%= UnicodeLanguageUtil.get(portletConfig.getResourceBundle(locale), "are-you-sure-you-want-to-mark-the-selected-messages-as-not-spam") %>'
			)
		) {
			document.<portlet:namespace />fm.<portlet:namespace />notSpamMBMessageIds.value = notSpamMBMessageIds;

			submitForm(
				document.<portlet:namespace />fm,
				'<portlet:actionURL name="markNotSpamMBMessages"><portlet:param name="redirect" value="<%= portletURL.toString() %>" /></portlet:actionURL>'
			);
		}
	};
</aui:script>