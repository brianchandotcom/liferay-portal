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

DispatchTrigger dispatchTrigger = dispatchLogDisplayContext.getDispatchTrigger();

Format dateTimeFormat = FastDateFormatFactoryUtil.getDateTime(FastDateFormatConstants.SHORT, FastDateFormatConstants.LONG, locale, TimeZone.getTimeZone(dispatchTrigger.getTimeZoneId()));
%>

<portlet:actionURL name="/dispatch/edit_dispatch_log" var="editDispatchLogActionURL" />

<div class="container-fluid container-fluid-max-xl container-view">
	<div class="card">
		<div class="card-body">
			<clay:content-row>
				<clay:content-col
					expand="<%= true %>"
				>
					<clay:row>
						<clay:col
							md="2"
						>
							<liferay-ui:message key="start-date" />
						</clay:col>

						<clay:col
							md="8"
						>
							<%= (dispatchLog.getStartDate() != null) ? fastDateTimeFormat.format(dispatchLog.getStartDate()) : "" %>
						</clay:col>
					</clay:row>

					<clay:row>
						<clay:col
							md="2"
						>
							<liferay-ui:message key="scheduled-start-date" />
						</clay:col>

						<clay:col
							md="8"
						>
							<%= (dispatchLog.getStartDate() != null) ? dateTimeFormat.format(dispatchLog.getStartDate()) : "" %>
						</clay:col>
					</clay:row>

					<clay:row>
						<clay:col
							md="2"
						>
							<liferay-ui:message key="status" />
						</clay:col>

						<%
						DispatchTaskStatus dispatchTaskStatus = DispatchTaskStatus.valueOf(dispatchLog.getStatus());
						%>

						<clay:col
							cssClass='<%= String.format("background-task-status-row background-task-status-%s %s", dispatchTaskStatus.getLabel(), dispatchTaskStatus.getCssClass()) %>'
							md="8"
						>
							<h6><liferay-ui:message key="<%= dispatchTaskStatus.getLabel() %>" /></h6>
						</clay:col>
					</clay:row>

					<clay:row>
						<clay:col
							md="2"
						>
							<liferay-ui:message key="runtime" />
						</clay:col>

						<clay:col
							md="8"
						>
							<%= dispatchLogDisplayContext.getExecutionTimeMills() + " ms" %>
						</clay:col>
					</clay:row>

					<c:if test="<%= dispatchLog.getError() != null %>">
						<clay:row>
							<clay:col
								md="2"
							>
								<liferay-ui:message key="error" />
							</clay:col>

							<clay:col
								md="8"
							>
								<pre><%= dispatchLog.getError() %></pre>
							</clay:col>
						</clay:row>
					</c:if>

					<c:if test="<%= dispatchLog.getOutput() != null %>">
						<clay:row>
							<clay:col
								md="2"
							>
								<liferay-ui:message key="output" />
							</clay:col>

							<clay:col
								md="8"
							>
								<pre><%= dispatchLog.getOutput() %></pre>
							</clay:col>
						</clay:row>
					</c:if>
				</clay:content-col>
			</clay:content-row>

			<div class="mt-4">
				<clay:link
					displayType="primary"
					href="<%= backURL %>"
					label="back"
					type="button"
				/>
			</div>
		</div>
	</div>
</div>