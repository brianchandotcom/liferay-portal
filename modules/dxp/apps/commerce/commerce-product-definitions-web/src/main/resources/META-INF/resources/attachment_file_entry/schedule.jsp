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
CPAttachmentFileEntriesDisplayContext cpAttachmentFileEntriesDisplayContext = (CPAttachmentFileEntriesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPAttachmentFileEntry cpAttachmentFileEntry = cpAttachmentFileEntriesDisplayContext.getCPAttachmentFileEntry();

boolean neverExpire = ParamUtil.getBoolean(request, "neverExpire", true);

if ((cpAttachmentFileEntry != null) && (cpAttachmentFileEntry.getExpirationDate() != null)) {
	neverExpire = false;
}
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="details"
/>

<aui:model-context bean="<%= cpAttachmentFileEntry %>" model="<%= CPAttachmentFileEntry.class %>" />

<liferay-ui:error exception="<%= CPAttachmentFileEntryExpirationDateException.class %>" message="please-enter-a-valid-expiration-date" />

<aui:fieldset>
	<aui:input formName="fm" name="displayDate" />

	<aui:input dateTogglerCheckboxLabel="never-expire" disabled="<%= neverExpire %>" formName="fm" name="expirationDate" />
</aui:fieldset>