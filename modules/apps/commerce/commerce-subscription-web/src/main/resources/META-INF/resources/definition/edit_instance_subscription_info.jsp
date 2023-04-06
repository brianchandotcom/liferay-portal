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
CPInstanceSubscriptionInfoDisplayContext cpInstanceSubscriptionInfoDisplayContext = (CPInstanceSubscriptionInfoDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpInstanceSubscriptionInfoDisplayContext.getCPDefinition();
CPInstance cpInstance = cpInstanceSubscriptionInfoDisplayContext.getCPInstance();
long cpInstanceId = cpInstanceSubscriptionInfoDisplayContext.getCPInstanceId();
List<CPSubscriptionType> cpSubscriptionTypes = cpInstanceSubscriptionInfoDisplayContext.getCPSubscriptionTypes();

String defaultCPSubscriptionType = StringPool.BLANK;

if (!cpSubscriptionTypes.isEmpty()) {
	CPSubscriptionType firstCPSubscriptionType = cpSubscriptionTypes.get(0);

	defaultCPSubscriptionType = firstCPSubscriptionType.getName();
}

PortletURL productSkusURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setMVCRenderCommandName(
	"/cp_definitions/edit_cp_definition"
).setParameter(
	"cpDefinitionId", cpDefinition.getCPDefinitionId()
).setParameter(
	"screenNavigationCategoryKey", cpInstanceSubscriptionInfoDisplayContext.getScreenNavigationCategoryKey()
).buildPortletURL();

boolean overrideSubscriptionInfo = BeanParamUtil.getBoolean(cpInstance, request, "overrideSubscriptionInfo", false);
boolean subscriptionEnabled = BeanParamUtil.getBoolean(cpInstance, request, "subscriptionEnabled", false);
int subscriptionLength = BeanParamUtil.getInteger(cpInstance, request, "subscriptionLength", 1);
String subscriptionType = BeanParamUtil.getString(cpInstance, request, "subscriptionType", defaultCPSubscriptionType);
long maxSubscriptionCycles = BeanParamUtil.getLong(cpInstance, request, "maxSubscriptionCycles");

boolean deliverySubscriptionEnabled = BeanParamUtil.getBoolean(cpInstance, request, "deliverySubscriptionEnabled", false);
int deliverySubscriptionLength = BeanParamUtil.getInteger(cpInstance, request, "deliverySubscriptionLength", 1);
String deliverySubscriptionType = BeanParamUtil.getString(cpInstance, request, "deliverySubscriptionType", defaultCPSubscriptionType);
long deliveryMaxSubscriptionCycles = BeanParamUtil.getLong(cpInstance, request, "deliveryMaxSubscriptionCycles");

String defaultCPSubscriptionTypeLabel = StringPool.BLANK;
String defaultDeliveryCPSubscriptionTypeLabel = StringPool.BLANK;

CPSubscriptionType cpSubscriptionType = cpInstanceSubscriptionInfoDisplayContext.getCPSubscriptionType(subscriptionType);
CPSubscriptionType deliveryCPSubscriptionType = cpInstanceSubscriptionInfoDisplayContext.getCPSubscriptionType(deliverySubscriptionType);

if (cpSubscriptionType != null) {
	defaultCPSubscriptionTypeLabel = cpSubscriptionType.getLabel(locale);
}

if (deliveryCPSubscriptionType != null) {
	defaultDeliveryCPSubscriptionTypeLabel = deliveryCPSubscriptionType.getLabel(locale);
}

CPSubscriptionTypeJSPContributor paymentCPSubscriptionTypeJSPContributor = cpInstanceSubscriptionInfoDisplayContext.getCPSubscriptionTypeJSPContributor(subscriptionType);
CPSubscriptionTypeJSPContributor deliveryCPSubscriptionTypeJSPContributor = cpInstanceSubscriptionInfoDisplayContext.getCPSubscriptionTypeJSPContributor(deliverySubscriptionType);

boolean ending = false;
boolean deliveryEnding = false;

if (maxSubscriptionCycles > 0) {
	ending = true;
}

if (deliveryMaxSubscriptionCycles > 0) {
	deliveryEnding = true;
}
%>

<aui:alert closeable="<%= false %>" type="warning">
	<liferay-ui:message key="all-channels-associated-with-this-product-must-have-at-least-one-payment-method-active-that-supports-recurring-payments" />
</aui:alert>

<portlet:actionURL name="/cp_definitions/edit_cp_instance" var="editProductInstanceShippingInfoActionURL" />

<aui:form action="<%= editProductInstanceShippingInfoActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="updateSubscriptionInfo" />
	<aui:input name="redirect" type="hidden" value="<%= String.valueOf(cpInstanceSubscriptionInfoDisplayContext.getPortletURL()) %>" />
	<aui:input name="cpInstanceId" type="hidden" value="<%= cpInstanceId %>" />

	<aui:model-context bean="<%= cpInstance %>" model="<%= CPInstance.class %>" />

	<commerce-ui:panel
		title='<%= LanguageUtil.get(request, "subscriptions") %>'
	>
		<aui:input checked="<%= overrideSubscriptionInfo %>" label="override-subscription-settings" name="overrideSubscriptionInfo" type="toggle-switch" value="<%= overrideSubscriptionInfo %>" />

		<div class="<%= overrideSubscriptionInfo ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />subscriptionInfo">
			<commerce-ui:panel
				collapsed="<%= !subscriptionEnabled %>"
				collapseLabel='<%= LanguageUtil.get(request, "enable") %>'
				collapseSwitchName='<%= liferayPortletResponse.getNamespace() + "subscriptionEnabled" %>'
				title='<%= LanguageUtil.get(request, "payment-subscription") %>'
			>
				<aui:select name="subscriptionType" onChange='<%= liferayPortletResponse.getNamespace() + "selectSubscriptionType(this);" %>'>

					<%
					for (CPSubscriptionType curCPSubscriptionType : cpSubscriptionTypes) {
					%>

						<aui:option data-label="<%= curCPSubscriptionType.getLabel(locale) %>" label="<%= curCPSubscriptionType.getLabel(locale) %>" selected="<%= subscriptionType.equals(curCPSubscriptionType.getName()) %>" value="<%= curCPSubscriptionType.getName() %>" />

					<%
					}
					%>

				</aui:select>

				<div id="<portlet:namespace />subscriptionTypeContributors">

					<%
					for (CPSubscriptionType curCPSubscriptionType : cpSubscriptionTypes) {
						CPSubscriptionTypeJSPContributor cpSubscriptionTypeJSPContributor = cpInstanceSubscriptionInfoDisplayContext.getCPSubscriptionTypeJSPContributor(curCPSubscriptionType.getName());

						if (cpSubscriptionTypeJSPContributor == null) {
							continue;
						}
					%>

					<div class="<%= !cpSubscriptionTypeJSPContributor.equals(paymentCPSubscriptionTypeJSPContributor) ? "hide" : "" %>" id="<portlet:namespace />subscriptionTypeContributor<%= curCPSubscriptionType.getName() %>">

						<%
						cpSubscriptionTypeJSPContributor.render(cpInstance, request, PipingServletResponseFactory.createPipingServletResponse(pageContext));
						%>

					</div>

					<%
					}
					%>

				</div>

				<div id="<portlet:namespace />cycleLengthContainer">
					<aui:input name="subscriptionLength" suffix="<%= defaultCPSubscriptionTypeLabel %>" value="<%= String.valueOf(subscriptionLength) %>">
						<aui:validator name="digits" />

						<aui:validator errorMessage='<%= LanguageUtil.format(request, "please-enter-a-value-greater-than-or-equal-to-x", 1) %>' name="custom">
							function(val) {
								var subscriptionEnabled = window.document.querySelector('#<portlet:namespace />subscriptionEnabled');

								if (!subscriptionEnabled.checked) {
									return true;
								}

								if (subscriptionEnabled.checked && parseInt(val, 10) > 0) {
									return true;
								}

								return false;
							}
						</aui:validator>
					</aui:input>
				</div>

				<div id="<portlet:namespace />neverEndsContainer">
					<div class="never-ends-header">
						<aui:input checked="<%= ending ? false : true %>" name="neverEnds" type="toggle-switch" />
					</div>

					<div class="never-ends-content">
						<aui:input disabled="<%= ending ? false : true %>" helpMessage="max-subscription-cycles-help" label="end-after" name="maxSubscriptionCycles" suffix='<%= LanguageUtil.get(request, "cycles") %>' value="<%= String.valueOf(maxSubscriptionCycles) %>">
							<aui:validator name="digits" />

							<aui:validator errorMessage='<%= LanguageUtil.format(request, "please-enter-a-value-greater-than-or-equal-to-x", 1) %>' name="custom">
								function(val) {
									var subscriptionNeverEndsCheckbox = window.document.querySelector('#<portlet:namespace />neverEnds');

									if (subscriptionNeverEndsCheckbox && subscriptionNeverEndsCheckbox.checked) {
										return true;
									}

									if (parseInt(val, 10) > 0) {
										return true;
									}

									return false;
								}
							</aui:validator>
						</aui:input>
					</div>
				</div>
			</commerce-ui:panel>

			<commerce-ui:panel
				collapsed="<%= !deliverySubscriptionEnabled %>"
				collapseLabel='<%= LanguageUtil.get(request, "enable") %>'
				collapseSwitchName='<%= liferayPortletResponse.getNamespace() + "deliverySubscriptionEnabled" %>'
				title='<%= LanguageUtil.get(request, "delivery-subscription") %>'
			>
				<aui:select label="subscription-type" name="deliverySubscriptionType" onChange='<%= liferayPortletResponse.getNamespace() + "selectDeliverySubscriptionType(this);" %>'>

					<%
					for (CPSubscriptionType curCPSubscriptionType : cpSubscriptionTypes) {
					%>

						<aui:option data-label="<%= curCPSubscriptionType.getLabel(locale) %>" label="<%= curCPSubscriptionType.getLabel(locale) %>" selected="<%= deliverySubscriptionType.equals(curCPSubscriptionType.getName()) %>" value="<%= curCPSubscriptionType.getName() %>" />

					<%
					}
					%>

				</aui:select>

				<div id="<portlet:namespace />deliverySubscriptionTypeContributors">

					<%
					for (CPSubscriptionType curCPSubscriptionType : cpSubscriptionTypes) {
						CPSubscriptionTypeJSPContributor cpSubscriptionTypeJSPContributor = cpInstanceSubscriptionInfoDisplayContext.getCPSubscriptionTypeJSPContributor(curCPSubscriptionType.getName());

						if (cpSubscriptionTypeJSPContributor == null) {
							continue;
						}
					%>

					<div class="<%= !cpSubscriptionTypeJSPContributor.equals(deliveryCPSubscriptionTypeJSPContributor) ? "hide" : "" %>" id="<portlet:namespace />deliverySubscriptionTypeContributor<%= curCPSubscriptionType.getName() %>">

						<%
						cpSubscriptionTypeJSPContributor.render(cpInstance, request, PipingServletResponseFactory.createPipingServletResponse(pageContext), false);
						%>

					</div>

					<%
					}
					%>

				</div>

				<div id="<portlet:namespace />deliveryCycleLengthContainer">
					<aui:input label="subscription-length" name="deliverySubscriptionLength" suffix="<%= defaultDeliveryCPSubscriptionTypeLabel %>" value="<%= String.valueOf(deliverySubscriptionLength) %>">
						<aui:validator name="digits" />

						<aui:validator errorMessage='<%= LanguageUtil.format(request, "please-enter-a-value-greater-than-or-equal-to-x", 1) %>' name="custom">
							function(val) {
								var deliverySubscriptionEnabled = window.document.querySelector('#<portlet:namespace />deliverySubscriptionEnabled');

								if (!deliverySubscriptionEnabled.checked) {
									return true;
								}

								if (deliverySubscriptionEnabled.checked && parseInt(val, 10) > 0) {
									return true;
								}

								return false;
							}
						</aui:validator>
					</aui:input>
				</div>

				<div id="<portlet:namespace />deliveryNeverEndsContainer">
					<div class="never-ends-header">
						<aui:input checked="<%= deliveryEnding ? false : true %>" label="never-ends" name="deliveryNeverEnds" type="toggle-switch" />
					</div>

					<div class="never-ends-content">
						<aui:input disabled="<%= deliveryEnding ? false : true %>" helpMessage="max-subscription-cycles-help" label="end-after" name="deliveryMaxSubscriptionCycles" suffix='<%= LanguageUtil.get(request, "cycles") %>' value="<%= String.valueOf(deliveryMaxSubscriptionCycles) %>">
							<aui:validator name="digits" />

							<aui:validator errorMessage='<%= LanguageUtil.format(request, "please-enter-a-value-greater-than-or-equal-to-x", 1) %>' name="custom">
								function(val) {
									var deliveryNeverEndsCheckbox = window.document.querySelector('#<portlet:namespace />deliveryNeverEnds');

									if (deliveryNeverEndsCheckbox && deliveryNeverEndsCheckbox.checked) {
										return true;
									}

									if (parseInt(val, 10) > 0) {
										return true;
									}

									return false;
								}
							</aui:validator>
						</aui:input>
					</div>
				</div>
			</commerce-ui:panel>
		</div>
	</commerce-ui:panel>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= productSkusURL.toString() %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	Liferay.Util.toggleBoxes(
		'<portlet:namespace />overrideSubscriptionInfo',
		'<portlet:namespace />subscriptionInfo'
	);

	Liferay.provide(
		window,
		'<portlet:namespace />selectSubscriptionType',
		(element) => {
			if (!element) {
				return;
			}

			var A = AUI();

			var subscriptionType = A.one(element).val();

			A.one('#<portlet:namespace />subscriptionTypeContributors')
				.get('children')
				.hide();

			var subscriptionTypeContributor = A.one(
				'#<portlet:namespace />subscriptionTypeContributor' +
					subscriptionType
			);

			if (subscriptionTypeContributor) {
				subscriptionTypeContributor.show();
			}
		},
		['liferay-portlet-url']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />selectDeliverySubscriptionType',
		(element) => {
			if (!element) {
				return;
			}

			var A = AUI();

			var subscriptionType = A.one(element).val();

			A.one('#<portlet:namespace />deliverySubscriptionTypeContributors')
				.get('children')
				.hide();

			var deliverySubscriptionTypeContributor = A.one(
				'#<portlet:namespace />deliverySubscriptionTypeContributor' +
					subscriptionType
			);

			if (deliverySubscriptionTypeContributor) {
				deliverySubscriptionTypeContributor.show();
			}
		},
		['liferay-portlet-url']
	);
</aui:script>

<aui:script use="liferay-form">
	A.one('#<portlet:namespace />neverEnds').on('change', (event) => {
		var formValidator = Liferay.Form.get('<portlet:namespace />fm')
			.formValidator;

		formValidator.validateField('<portlet:namespace />maxSubscriptionCycles');
	});

	A.one('#<portlet:namespace />deliveryNeverEnds').on('change', (event) => {
		var formValidator = Liferay.Form.get('<portlet:namespace />fm')
			.formValidator;

		formValidator.validateField(
			'<portlet:namespace />deliveryMaxSubscriptionCycles'
		);
	});
</aui:script>

<aui:script use="aui-toggler">
	new A.Toggler({
		animated: true,
		content: '#<portlet:namespace />neverEndsContainer .never-ends-content',
		expanded: <%= ending %>,
		header: '#<portlet:namespace />neverEndsContainer .never-ends-header',
		on: {
			animatingChange: function (event) {
				var instance = this;

				if (!instance.get('expanded')) {
					A.one('#<portlet:namespace />maxSubscriptionCycles').attr(
						'disabled',
						false
					);
				}
				else {
					A.one('#<portlet:namespace />maxSubscriptionCycles').attr(
						'disabled',
						true
					);
				}
			},
		},
	});

	new A.Toggler({
		animated: true,
		content:
			'#<portlet:namespace />deliveryNeverEndsContainer .never-ends-content',
		expanded: <%= deliveryEnding %>,
		header:
			'#<portlet:namespace />deliveryNeverEndsContainer .never-ends-header',
		on: {
			animatingChange: function (event) {
				var instance = this;

				if (!instance.get('expanded')) {
					A.one(
						'#<portlet:namespace />deliveryMaxSubscriptionCycles'
					).attr('disabled', false);
				}
				else {
					A.one(
						'#<portlet:namespace />deliveryMaxSubscriptionCycles'
					).attr('disabled', true);
				}
			},
		},
	});
</aui:script>