<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
EditRedirectEntryDisplayContext editRedirectEntryDisplayContext = (EditRedirectEntryDisplayContext)request.getAttribute(EditRedirectEntryDisplayContext.class.getName());

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(editRedirectEntryDisplayContext.getRedirect());

renderResponse.setTitle(editRedirectEntryDisplayContext.getTitle());
%>

<liferay-frontend:edit-form
	action="<%= editRedirectEntryDisplayContext.getEditRedirectEntryURL() %>"
	method="post"
	name="fm"
	onSubmit="event.preventDefault();"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= editRedirectEntryDisplayContext.getRedirect() %>" />
	<aui:input name="backURL" type="hidden" value="<%= editRedirectEntryDisplayContext.getBackURL() %>" />
	<aui:input name="updateChainedRedirectEntries" type="hidden" value="" />

	<c:if test="<%= editRedirectEntryDisplayContext.getRedirectEntryId() != 0 %>">
		<aui:input name="redirectEntryId" type="hidden" value="<%= editRedirectEntryDisplayContext.getRedirectEntryId() %>" />
	</c:if>

	<liferay-frontend:edit-form-body>
		<liferay-ui:error exception="<%= CircularRedirectEntryException.DestinationURLMustNotBeEqualToSourceURL.class %>" focusField="destinationURL" message="destination-url-cannot-be-the-same-as-source-url" />
		<liferay-ui:error exception="<%= CircularRedirectEntryException.MustNotFormALoopWithAnotherRedirectEntry.class %>" focusField="sourceURL" message="please-change-the-source-or-destination-url-to-avoid-redirect-loop" />
		<liferay-ui:error exception="<%= DuplicateRedirectEntrySourceURLException.class %>" focusField="sourceURL" message="there-is-already-a-redirect-set-for-the-same-source-url" />

		<liferay-ui:error exception="<%= LayoutFriendlyURLException.class %>" focusField="sourceURL">

			<%
			LayoutFriendlyURLException lfurle = (LayoutFriendlyURLException)errorException;
			%>

			<c:if test="<%= lfurle.getType() == LayoutFriendlyURLException.ADJACENT_SLASHES %>">
				<liferay-ui:message key="please-enter-a-source-url-that-does-not-have-adjacent-slashes" />
			</c:if>

			<c:if test="<%= lfurle.getType() == LayoutFriendlyURLException.DOES_NOT_START_WITH_SLASH %>">
				<liferay-ui:message key="please-enter-a-source-url-that-begins-with-a-slash" />
			</c:if>

			<c:if test="<%= lfurle.getType() == LayoutFriendlyURLException.ENDS_WITH_SLASH %>">
				<liferay-ui:message key="please-enter-a-source-url-that-does-not-end-with-a-slash" />
			</c:if>

			<c:if test="<%= lfurle.getType() == LayoutFriendlyURLException.INVALID_CHARACTERS %>">
				<liferay-ui:message key="please-enter-a-source-url-with-valid-characters" />
			</c:if>

			<c:if test="<%= lfurle.getType() == LayoutFriendlyURLException.KEYWORD_CONFLICT %>">
				<liferay-ui:message arguments="<%= lfurle.getKeywordConflict() %>" key="please-enter-a-source-url-that-does-not-conflict-with-the-keyword-x" translateArguments="<%= false %>" />
			</c:if>

			<c:if test="<%= lfurle.getType() == LayoutFriendlyURLException.TOO_LONG %>">
				<liferay-ui:message key="the-source-url-is-too-long" />
			</c:if>

			<c:if test="<%= lfurle.getType() == LayoutFriendlyURLException.TOO_SHORT %>">
				<liferay-ui:message key="please-enter-a-source-url-that-is-at-least-two-characters-long" />
			</c:if>
		</liferay-ui:error>

		<liferay-ui:error exception="<%= RequiredRedirectEntryDestinationURLException.class %>" focusField="destinationURL" message="the-destination-url-must-be-specified" />
		<liferay-ui:error exception="<%= RequiredRedirectEntrySourceURLException.class %>" focusField="sourceURL" message="the-source-url-must-be-specified" />

		<aui:field-wrapper cssClass="form-group" label="source-url" name="sourceURL" required="<%= true %>">
			<div class="form-text"><%= RedirectUtil.getGroupBaseURL(themeDisplay) %></div>

			<div class="input-group">
				<div class="input-group-item input-group-item-shrink input-group-prepend">
					<span class="input-group-text">/</span>
				</div>

				<div class="input-group-item">
					<aui:input label="" name="sourceURL" required="<%= true %>" type="text" value="<%= URLCodec.decodeURL(editRedirectEntryDisplayContext.getSourceURL()) %>" />
				</div>
			</div>
		</aui:field-wrapper>

		<div class="destination-url">
			<aui:input name="destinationURL" value="<%= editRedirectEntryDisplayContext.getDestinationURL() %>" />

			<react:component
				module="js/DestinationUrlInput"
				props='<%=
					HashMapBuilder.<String, Object>put(
						"autofocus", editRedirectEntryDisplayContext.isAutoFocusDestinationURL()
					).put(
						"initialDestinationUrl", editRedirectEntryDisplayContext.getDestinationURL()
					).put(
						"namespace", liferayPortletResponse.getNamespace()
					).build()
				%>'
			/>
		</div>

		<aui:select helpMessage="the-redirect-type-affects-how-search-engines-and-users-browsers-cache-treat-it" label="type" name="permanent">
			<aui:option selected="<%= editRedirectEntryDisplayContext.isRedirectEntryPermanent() %>" value="<%= true %>">
				<liferay-ui:message arguments="<%= HttpServletResponse.SC_MOVED_PERMANENTLY %>" key="permanent-x" />
			</aui:option>

			<aui:option selected="<%= editRedirectEntryDisplayContext.isRedirectEntryTemporary() %>" value="<%= false %>">
				<liferay-ui:message arguments="<%= HttpServletResponse.SC_FOUND %>" key="temporary-x" />
			</aui:option>
		</aui:select>

		<aui:input helpMessage="the-redirect-will-be-active-until-the-chosen-date.-leave-it-empty-to-avoid-expiration" name="expirationDate" type="date" value="<%= editRedirectEntryDisplayContext.getExpirationDateInputValue() %>" />

		<c:if test="<%= editRedirectEntryDisplayContext.isShowAlertMessage() %>">
			<clay:alert
				cssClass="hide"
				id='<%= liferayPortletResponse.getNamespace() + "typeInfoAlert" %>'
				message="changes-to-this-redirect-might-not-be-immediately-seen-for-users-whose-browsers-have-cached-the-old-redirect-configuration"
			/>
		</c:if>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons
			redirect="<%= editRedirectEntryDisplayContext.getRedirect() %>"
			submitLabel="<%= editRedirectEntryDisplayContext.getSubmitButtonLabel() %>"
		/>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<div>
	<react:component
		module="js/ChainedRedirections"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"saveButtonLabel", editRedirectEntryDisplayContext.getSubmitButtonLabel()
			).build()
		%>'
	/>
</div>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"getRedirectEntryChainCauseURL", editRedirectEntryDisplayContext.getRedirectEntryChainCauseURL()
		).put(
			"initialDestinationURL", editRedirectEntryDisplayContext.getDestinationURL()
		).put(
			"initialIsPermanent", editRedirectEntryDisplayContext.isRedirectEntryPermanent()
		).build()
	%>'
	module="js/editRedirectEntry"
/>