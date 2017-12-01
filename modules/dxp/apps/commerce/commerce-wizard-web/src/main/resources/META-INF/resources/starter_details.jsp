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

CommerceStarter commerceStarter = commerceStarterDisplayContext.getCommerceStarter(commerceStarterKey);
%>

<div class="container-fluid-1280">
	<div class="col-md-8 commerce-wizard-container offset-md-2">
		<portlet:actionURL name="applyCommerceStarter" var="applyCommerceStarterURL" />

		<aui:form action="<%= applyCommerceStarterURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "onSubmit();" %>'>
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="commerceStarterKey" type="hidden" value="<%= commerceStarterKey %>" />

			<div class="commerce-wizard-header row">
				<div class="col-md-6">
					<h1><%= commerceStarter.getName(locale) %></h1>
				</div>

				<div class="col-md-6 commerce-starter-controls">
					<aui:button name="applyButton" primary="<%= true %>" type="submit" value="apply" />

					<a class="btn btn-left btn-secondary" id="previousStarterButton">
						<liferay-ui:icon
							cssClass="commerce-wizard-icon-angle-left"
							icon="angle-left"
							markupView="lexicon"
						/>
					</a>

					<a class="btn btn-right btn-secondary" id="nextStarterButton">
						<liferay-ui:icon
							cssClass="commerce-wizard-icon-angle-right"
							icon="angle-right"
							markupView="lexicon"
						/>
					</a>
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

<aui:script>
	function <portlet:namespace />onSubmit() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-continue-all-contents-will-be-deleted") %>')) {
			submitForm($(document.<portlet:namespace />fm));
		}
	}
</aui:script>