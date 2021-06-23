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
DispatchLogDisplayContext dispatchLogDisplayContext = (DispatchLogDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

DispatchLog dispatchLog = dispatchLogDisplayContext.getDispatchLog();
%>

<portlet:actionURL name="/dispatch/edit_dispatch_log" var="editDispatchLogActionURL" />

<div class="container-fluid container-fluid-max-xl container-view">
	<div class="sheet">
		<aui:form action="<%= editDispatchLogActionURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
			<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
			<aui:input name="dispatchLogId" type="hidden" value="<%= String.valueOf(dispatchLog.getDispatchLogId()) %>" />

			<div class="lfr-form-content">
				<aui:fieldset>
					<aui:input disabled="<%= true %>" label="start-date" name="startDate" value='<%= (dispatchLog.getStartDate() != null) ? fastDateFormat.format(dispatchLog.getStartDate()) : "" %>' />

					<%
					DispatchTaskStatus dispatchTaskStatus = DispatchTaskStatus.valueOf(dispatchLog.getStatus());
					%>

					<aui:input disabled="<%= true %>" name="status" value="<%= LanguageUtil.get(request, dispatchTaskStatus.getLabel()) %>" />

					<aui:input disabled="<%= true %>" label="runtime" name="runTime" value='<%= dispatchLogDisplayContext.getExecutionTimeMills() + " ms" %>' />

					<aui:input disabled="<%= true %>" label="error" name="error" type="textarea" value="<%= dispatchLog.getError() %>" />

					<aui:input disabled="<%= true %>" label="output" name="output" type="textarea" value="<%= dispatchLog.getOutput() %>" />
				</aui:fieldset>

				<div class="sheet-footer">
					<aui:button href="<%= backURL %>" type="cancel" />
				</div>
			</div>
		</aui:form>
	</div>
</div>