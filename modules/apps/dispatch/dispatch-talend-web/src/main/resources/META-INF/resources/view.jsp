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
DispatchTrigger dispatchTrigger = (DispatchTrigger)request.getAttribute(DispatchWebKeys.DISPATCH_TRIGGER);
String fileEntryName = (String)request.getAttribute(DispatchWebKeys.FILE_ENTRY_NAME);
%>

<liferay-portlet:actionURL name="/dispatch_talend/edit_dispatch_talend_job_archive" portletName="<%= DispatchPortletKeys.DISPATCH %>" var="editDispatchTalendJobArchiveActionURL" />

<div class="closed container-fluid container-fluid-max-xl container-form-lg" id="<portlet:namespace />editDispatchTriggerId">
	<div class="sheet">
		<aui:form action="<%= editDispatchTalendJobArchiveActionURL %>" enctype="multipart/form-data" method="post" name="fm">
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="dispatchTriggerId" type="hidden" value="<%= String.valueOf(dispatchTrigger.getDispatchTriggerId()) %>" />

			<aui:model-context bean="<%= dispatchTrigger %>" model="<%= DispatchTrigger.class %>" />

			<p class="<%= Objects.equals(fileEntryName, StringPool.BLANK) ? "hide" : StringPool.BLANK %> text-default" id="<portlet:namespace />fileEntryName">
				<span id="<portlet:namespace />fileEntryRemove">
					<liferay-ui:icon
						icon="times"
						markupView="lexicon"
						message="remove"
					/>
				</span>
				<span>
					<%= fileEntryName %>
				</span>
			</p>

			<c:if test="<%= (dispatchTrigger == null) || !dispatchTrigger.isSystem() %>">
				<div class="<%= Objects.equals(fileEntryName, StringPool.BLANK) ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />fileEntry">
					<aui:input name="jobArchive" required="<%= true %>" type="file" />
				</div>
			</c:if>

			<div class="sheet-footer">
				<aui:button type="submit" value="save" />

				<aui:button href="<%= currentURL %>" type="cancel" />
			<div>
		</aui:form>
	</div>
</div>

<aui:script>
	AUI().ready(function (A) {
		A.one('#<portlet:namespace />fileEntryRemove').on('click', function (
			event
		) {
			event.preventDefault();

			A.one('#<portlet:namespace />fileEntry').removeClass('hide');

			A.one('#<portlet:namespace />fileEntryName').addClass('hide');
		});
	});
</aui:script>