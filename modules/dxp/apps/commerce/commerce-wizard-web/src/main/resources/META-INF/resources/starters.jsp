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
CommerceStarterDisplayContext commerceStarterDisplayContext = (CommerceStarterDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

List<CommerceStarter> commerceStarters = commerceStarterDisplayContext.getCommerStarters();
%>

<div class="container-fluid-1280">
	<div class="col-md-8 commerce-wizard-container offset-md-2">
		<div class="commerce-wizard-header">
			<h1><liferay-ui:message key="choose-a-pre-defined-setting" /></h1>
		</div>

		<c:if test="<%= !commerceStarters.isEmpty() %>">
			<div id="commerce-starters-container">

				<%
				for (CommerceStarter commerceStarter : commerceStarters) {
				%>

					<div class="commerce-starter row">
						<div class="col-md-3">

							<%
							String thumbnailSrc = commerceStarter.getThumbnailSrc();
							%>

							<img src="<%= thumbnailSrc %>" />
						</div>

						<div class="col-md-6">
							<h3><%= commerceStarter.getName(locale) %></h3>

							<p><%= commerceStarter.getDescription(locale) %></p>
						</div>

						<div class="col-md-3">
							<portlet:renderURL var="viewCommerceStarterDetailsURL">
								<portlet:param name="mvcRenderCommandName" value="viewCommerceStarterDetails" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="commerceStarterKey" value="<%= commerceStarter.getKey() %>" />
							</portlet:renderURL>

							<a class="btn-primary btn-sm" href="<%= viewCommerceStarterDetailsURL.toString() %>">
								<liferay-ui:message key="view-details" />
							</a>
						</div>
					</div>

				<%
				}
				%>

			</div>
		</c:if>

		<div class="commerce-wizard-button-row">
			<a class="btn btn-left btn-secondary" href="<%= redirect %>" id="previousButton">
				<liferay-ui:icon
					cssClass="commerce-wizard-icon-angle-left"
					icon="angle-left"
					markupView="lexicon"
				/>

				<liferay-ui:message key="previous" />
			</a>
		</div>
	</div>
</div>