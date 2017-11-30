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

		<div class="commerce-wizard-select-method row">
			<div class="col-md-4 commerce-wizard-method">
				<portlet:actionURL name="selectMethod" var="selectMethodURL">
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="selectedMethod" value="<%= CommerceWizardConstants.WIZARD_PREDEFINED_METHOD %>" />
				</portlet:actionURL>

				<a href="<%= selectMethodURL.toString() %>">
					<liferay-ui:icon
						cssClass="commerce-wizard-icon-magic"
						icon="magic"
						markupView="lexicon"
					/>
				</a>

				<h4><liferay-ui:message key="predefined" /></h4>

				<p class="text-default"><liferay-ui:message key="commerce-wizard-predefined-description" /></p>
			</div>

			<div class="col-md-4 commerce-wizard-method-disabled">
				<liferay-ui:icon
					cssClass="commerce-wizard-icon-pencil"
					icon="pencil"
					markupView="lexicon"
				/>

				<h4><liferay-ui:message key="custom" /></h4>
			</div>

			<div class="col-md-4 commerce-wizard-method-disabled">
				<liferay-ui:icon
					cssClass="commerce-wizard-icon-download"
					icon="download"
					markupView="lexicon"
				/>

				<h4><liferay-ui:message key="import" /></h4>
			</div>
		</div>
	</div>
</div>