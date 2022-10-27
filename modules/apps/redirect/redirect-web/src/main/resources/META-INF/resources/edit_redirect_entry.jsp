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
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");

RedirectEntry redirectEntry = (RedirectEntry)request.getAttribute(RedirectEntry.class.getName());

String destinationURL = (redirectEntry != null) ? redirectEntry.getDestinationURL() : ParamUtil.getString(request, "destinationURL");
String sourceURL = (redirectEntry != null) ? redirectEntry.getSourceURL() : ParamUtil.getString(request, "sourceURL");

RedirectEntriesDisplayContext redirectEntriesDisplayContext = (RedirectEntriesDisplayContext)request.getAttribute(RedirectEntriesDisplayContext.class.getName());

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

if (redirectEntry == null) {
	renderResponse.setTitle(LanguageUtil.get(request, "new-redirect"));
}
else {
	renderResponse.setTitle(LanguageUtil.get(request, "edit-redirect"));
}
%>

<portlet:actionURL name="/redirect/edit_redirect_entry" var="editRedirectEntryURL" />

<liferay-frontend:edit-form
	action="<%= editRedirectEntryURL %>"
	method="post"
	name="fm"
	onSubmit="event.preventDefault();"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="updateChainedRedirectEntries" type="hidden" value="" />

	<c:if test="<%= redirectEntry != null %>">
		<aui:input name="redirectEntryId" type="hidden" value="<%= redirectEntry.getRedirectEntryId() %>" />
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
					<aui:input autoFocus="<%= Validator.isNull(sourceURL) || Validator.isNotNull(destinationURL) %>" label="" name="sourceURL" required="<%= true %>" type="text" value="<%= URLCodec.decodeURL(sourceURL) %>" />
				</div>
			</div>
		</aui:field-wrapper>

		<%
		boolean autoFocusDestination = Validator.isNotNull(sourceURL) && Validator.isNull(destinationURL);
		%>

		<div class="destination-url">
			<aui:input name="destinationURL" value="<%= destinationURL %>" />

			<react:component
				module="js/DestinationUrlInput"
				props='<%=
					HashMapBuilder.<String, Object>put(
						"autofocus", autoFocusDestination
					).put(
						"initialDestinationUrl", (redirectEntry != null) ? redirectEntry.getDestinationURL() : ParamUtil.getString(request, "destinationURL")
					).put(
						"namespace", liferayPortletResponse.getNamespace()
					).build()
				%>'
			/>
		</div>

		<aui:select helpMessage="the-redirect-type-affects-how-search-engines-and-users-browsers-cache-treat-it" label="type" name="permanent">
			<aui:option selected="<%= (redirectEntry != null) ? redirectEntry.isPermanent() : false %>" value="<%= true %>">
				<liferay-ui:message arguments="<%= HttpServletResponse.SC_MOVED_PERMANENTLY %>" key="permanent-x" />
			</aui:option>

			<aui:option selected="<%= (redirectEntry != null) ? !redirectEntry.isPermanent() : true %>" value="<%= false %>">
				<liferay-ui:message arguments="<%= HttpServletResponse.SC_FOUND %>" key="temporary-x" />
			</aui:option>
		</aui:select>

		<aui:input helpMessage="the-redirect-will-be-active-until-the-chosen-date.-leave-it-empty-to-avoid-expiration" name="expirationDate" type="date" value="<%= redirectEntriesDisplayContext.getExpirationDateInputValue(redirectEntry) %>" />

		<c:if test="<%= redirectEntry != null %>">
			<clay:alert
				cssClass="hide"
				id='<%= liferayPortletResponse.getNamespace() + "typeInfoAlert" %>'
				message="changes-to-this-redirect-might-not-be-immediately-seen-for-users-whose-browsers-have-cached-the-old-redirect-configuration"
			/>
		</c:if>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons
			redirect="<%= redirect %>"
			submitLabel='<%= LanguageUtil.get(request, (redirectEntry == null) ? "create" : "save") %>'
		/>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<div>
	<react:component
		module="js/ChainedRedirections"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"saveButtonLabel", LanguageUtil.get(request, (redirectEntry == null) ? "create" : "save")
			).build()
		%>'
	/>
</div>

<portlet:resourceURL id="/redirect/get_redirect_entry_chain_cause" var="getRedirectEntryChainCauseURL" />

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"getRedirectEntryChainCauseURL", getRedirectEntryChainCauseURL
		).put(
			"initialDestinationURL", destinationURL
		).put(
			"initialIsPermanent", (redirectEntry != null) ? redirectEntry.isPermanent() : false
		).build()
	%>'
	module="js/editRedirectEntry"
/>