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

<div class="container-fluid-1280">
	<div class="col-md-8 commerce-wizard-container offset-md-2">
		<div class="commerce-wizard-header">
			<h1><liferay-ui:message key="choose-your-method" /></h1>
		</div>

		<portlet:actionURL name="selectMethod" var="selectMethodURL" />

		<aui:form action="<%= selectMethodURL %>" method="post" name="fm">
			<div class="commerce-wizard-select-method row">
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="selectedMethod" type="hidden" />

				<div class="col-md-4 commerce-wizard-method" data-value="<%= CommerceWizardConstants.WIZARD_PREDEFINED_METHOD %>">
					<liferay-ui:icon
						cssClass="commerce-wizard-icon-magic"
						icon="magic"
						markupView="lexicon"
					/>

					<h4><liferay-ui:message key="predefined" /></h4>

					<p class="text-default"><liferay-ui:message key="commerce-wizard-predefined-description" /></p>
				</div>

				<div class="col-md-4 commerce-wizard-method-disabled" data-value="<%= CommerceWizardConstants.WIZARD_CUSTOM_METHOD %>">
					<liferay-ui:icon
						cssClass="commerce-wizard-icon-pencil"
						icon="pencil"
						markupView="lexicon"
					/>

					<h4><liferay-ui:message key="custom" /></h4>
				</div>

				<div class="col-md-4 commerce-wizard-method-disabled" data-value="<%= CommerceWizardConstants.WIZARD_IMPORT_METHOD %>">
					<liferay-ui:icon
						cssClass="commerce-wizard-icon-download"
						icon="download"
						markupView="lexicon"
					/>

					<h4><liferay-ui:message key="import" /></h4>
				</div>
			</div>

			<div class="commerce-wizard-button-row">
				<a class="btn btn-left btn-secondary disabled" href="<%= redirect %>" id="previousButton">
					<liferay-ui:icon
						cssClass="commerce-wizard-icon-angle-left"
						icon="angle-left"
						markupView="lexicon"
					/>

					<liferay-ui:message key="previous" />
				</a>

				<a class="btn btn-right btn-secondary hide" id="nextButton" onclick="<%= renderResponse.getNamespace() + "selectMethod();" %>">
					<liferay-ui:message key="next" />

					<liferay-ui:icon
						cssClass="commerce-wizard-icon-angle-right"
						icon="angle-right"
						markupView="lexicon"
					/>
				</a>
			</div>
		</aui:form>
	</div>
</div>

<aui:script>
	$('.commerce-wizard-select-method .commerce-wizard-method').click(
		function() {
			$(this).parent().find('.commerce-wizard-method').removeClass('selected-method');
			$(this).addClass('selected-method');

			var val = $(this).attr('data-value');

			$(this).parent().find('#<portlet:namespace />selectedMethod').val(val);

			$('#nextButton').removeClass('hide');
		}
	);

	function <portlet:namespace />selectMethod() {
		var form = AUI.$(document.<portlet:namespace />fm);

		submitForm(form);
	}
</aui:script>