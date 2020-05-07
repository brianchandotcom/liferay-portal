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

RedirectDisplayContext redirectDisplayContext = new RedirectDisplayContext(request, liferayPortletRequest, liferayPortletResponse);

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
	<aui:input name="updateReferences" type="hidden" value="" />

	<c:if test="<%= redirectEntry != null %>">
		<aui:input name="redirectEntryId" type="hidden" value="<%= redirectEntry.getRedirectEntryId() %>" />
	</c:if>

	<liferay-frontend:edit-form-body>
		<liferay-ui:error exception="<%= DuplicateRedirectEntrySourceURLException.class %>" focusField="sourceURL" message="there-is-already-a-redirect-set-for-the-same-source-url" />
		<liferay-ui:error exception="<%= RequiredRedirectEntryDestinationURLException.class %>" focusField="destinationURL" message="the-destination-url-must-be-specified" />
		<liferay-ui:error exception="<%= RequiredRedirectEntrySourceURLException.class %>" focusField="sourceURL" message="the-source-url-must-be-specified" />

		<%
		String sourceURL = (redirectEntry != null) ? redirectEntry.getSourceURL() : ParamUtil.getString(request, "sourceURL");
		String destinationURL = (redirectEntry != null) ? redirectEntry.getDestinationURL() : ParamUtil.getString(request, "destinationURL");
		%>

		<aui:field-wrapper cssClass="form-group" label="source-url" name="sourceURL" required="<%= true %>">
			<div class="form-text"><%= RedirectUtil.getGroupBaseURL(themeDisplay) %></div>

			<div class="input-group">
				<div class="input-group-item input-group-item-shrink input-group-prepend">
					<span class="input-group-text">/</span>
				</div>

				<div class="input-group-item">
					<aui:input autoFocus="<%= Validator.isNull(sourceURL) || Validator.isNotNull(destinationURL) %>" label="" name="sourceURL" required="<%= true %>" type="text" value="<%= sourceURL %>" />
				</div>
			</div>
		</aui:field-wrapper>

		<%
		boolean autoFocusDestination = Validator.isNotNull(sourceURL) && Validator.isNull(destinationURL);

		Map<String, Object> data = HashMapBuilder.<String, Object>put(
			"autofocus", autoFocusDestination
		).put(
			"initialDestinationUrl", (redirectEntry != null) ? redirectEntry.getDestinationURL() : StringPool.BLANK
		).put(
			"namespace", liferayPortletResponse.getNamespace()
		).build();
		%>

		<div class="destination-url">
			<aui:input name="destinationURL" value="<%= destinationURL %>" />

			<react:component
				data="<%= data %>"
				module="js/DestinationUrlInput"
			/>
		</div>

		<aui:select label="type" name="permanent">
			<aui:option selected="<%= (redirectEntry != null) ? redirectEntry.isPermanent() : false %>" value="<%= true %>">
				<liferay-ui:message arguments="<%= HttpServletResponse.SC_MOVED_PERMANENTLY %>" key="permanent-x" />
			</aui:option>

			<aui:option selected="<%= (redirectEntry != null) ? !redirectEntry.isPermanent() : true %>" value="<%= false %>">
				<liferay-ui:message arguments="<%= HttpServletResponse.SC_FOUND %>" key="temporary-x" />
			</aui:option>
		</aui:select>

		<aui:input helpMessage="the-redirect-will-be-active-until-the-chosen-date.-leave-it-empty-to-avoid-expiration" name="expirationDate" type="date" value="<%= redirectDisplayContext.getExpirationDateInputValue(redirectEntry) %>" />
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" value='<%= LanguageUtil.get(request, (redirectEntry == null) ? "create" : "save") %>' />

		<aui:button href="<%= redirect %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<div>
	<react:component
		data='<%=
			HashMapBuilder.<String, Object>put(
				"saveButtonLabel", LanguageUtil.get(request, (redirectEntry == null) ? "create" : "save")
			).build() %>'
		module="js/ChainedRedirections"
	/>
</div>

<portlet:actionURL name="/redirect/check_redirect_entry_chain" var="checkRedirectEntryChainURL" />

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"checkRedirectEntryChainURL", checkRedirectEntryChainURL
		).build()
	%>'
	module="js/editRedirectEntry"
/>