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
String commerceStarterKey = ParamUtil.getString(request, "commerceStarterKey");

CommerceStarterDisplayContext commerceStarterDisplayContext = (CommerceStarterDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

List<CommerceStarter> commerceStarters = commerceStarterDisplayContext.getCommerStarters();

CommerceStarter commerceStarter = commerceStarterDisplayContext.getCommerceStarter(commerceStarterKey);
%>

<div class="container-fluid-1280">
	<div class="col-md-8 commerce-wizard-container offset-md-2">
		<portlet:actionURL name="applyCommerceStarter" var="applyCommerceStarterURL">
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="commerceStarterKey" value="<%= commerceStarterKey %>" />
		</portlet:actionURL>

		<aui:form action="<%= applyCommerceStarterURL %>" method="post" name="fm">
			<div class="commerce-wizard-header row">
				<div class="col-md-6">
					<h1><%= commerceStarter.getName(locale) %></h1>
				</div>

				<div class="col-md-6 commerce-starter-controls">
					<aui:button name="applyButton" primary="<%= true %>" value="apply" />

					<c:if test="<%= commerceStarters.size() > 1 %>">
						<portlet:actionURL name="selectCommerceStarter" var="selectCommerceStarterActionURL">
							<portlet:param name="<%= Constants.CMD %>" value="previousCommerceStarter" />
							<portlet:param name="redirect" value="<%= redirect %>" />
							<portlet:param name="commerceStarterKey" value="<%= commerceStarterKey %>" />
						</portlet:actionURL>

						<a class="btn btn-left btn-secondary" href="<%= selectCommerceStarterActionURL.toString() %>" id="previousStarterButton">
							<liferay-ui:icon
								cssClass="commerce-wizard-icon-angle-left"
								icon="angle-left"
								markupView="lexicon"
							/>
						</a>

						<portlet:actionURL name="selectCommerceStarter" var="selectCommerceStarterActionURL">
							<portlet:param name="<%= Constants.CMD %>" value="nextCommerceStarter" />
							<portlet:param name="redirect" value="<%= redirect %>" />
							<portlet:param name="commerceStarterKey" value="<%= commerceStarterKey %>" />
						</portlet:actionURL>

						<a class="btn btn-right btn-secondary" href="<%= selectCommerceStarterActionURL.toString() %>" id="nextStarterButton">
							<liferay-ui:icon
								cssClass="commerce-wizard-icon-angle-right"
								icon="angle-right"
								markupView="lexicon"
							/>
						</a>
					</c:if>
				</div>
			</div>

			<c:if test="<%= commerceStarter != null %>">

				<%
				commerceStarterDisplayContext.renderPreview(commerceStarter);
				%>

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
		</aui:form>
	</div>
</div>

<aui:script use="aui-base,aui-io-request">
	A.one('#<portlet:namespace/>applyButton').on(
		'click',
		function(event) {
			if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-continue-all-contents-will-be-deleted") %>')) {
				applyCommerceStarter();
			}
		}
	);

	Liferay.provide(
		window,
		'applyCommerceStarter',
		function() {
			var loadingMask = new A.LoadingMask(
				{
					'strings.loading' : '<%= UnicodeLanguageUtil.get(request, "this-may-take-several-minutes") %>',
					target : A.getBody()
				}
			);

			loadingMask.show();

			A.io.request(
				'<%= applyCommerceStarterURL.toString() %>',
				{
					on: {
						failure: function() {
							loadingMask.hide();

							var message = this.get('message');

							alert(message);
						},
						success: function(event, id, obj) {
							window.location.href = '<%= commerceStarterDisplayContext.getCurrentSiteRedirect() %>';
						}
					}
				}
			);
		},
		['aui-io-request','aui-loading-mask-deprecated']
	);
</aui:script>