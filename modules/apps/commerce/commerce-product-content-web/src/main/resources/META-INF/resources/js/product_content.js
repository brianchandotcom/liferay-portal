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

AUI.add(
	'liferay-commerce-product-content',
	A => {
		var STR_DDM_FORM_EVENT = 'DDMForm:render';

		var CP_CONTENT_WEB_PORTLET_KEY =
			'com_liferay_commerce_product_content_web_internal_portlet_CPContentPortlet';

		var CP_INSTANCE_CHANGE_EVENT = 'CPInstance:change';

		var ProductContent = A.Component.create({
			ATTRS: {
				cpDefinitionId: {},
				fullImageSelector: {},
				productContentAuthToken: {},
				productContentSelector: {},
				thumbsContainerSelector: {},
				viewAttachmentURL: {}
			},

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME: 'productcontent',

			prototype: {
				_bindUI() {
					var instance = this;

					var eventHandles = [];

					var cpDefinitionId = instance.get('cpDefinitionId');

					var form = Liferay.component(
						'ProductOptions' + cpDefinitionId + 'DDMForm'
					);

					if (form) {
						form.after(
							'*:valueChange',
							instance._ddmFormChange,
							instance
						);
					}

					eventHandles.push(
						Liferay.on(
							'ProductOptions' +
								cpDefinitionId +
								STR_DDM_FORM_EVENT,
							instance._ddmFormRender,
							instance
						)
					);

					instance._eventHandles = eventHandles;
				},
				_ddmFormChange(_valueChangeEvent) {
					var instance = this;

					instance._renderImages();

					instance.checkCPInstance();
				},
				_ddmFormRender(event) {
					var instance = this;

					var form = event.form;

					form.after(
						'*:valueChange',
						instance._ddmFormChange,
						instance
					);
				},
				_getThumbsContainer() {
					var instance = this;

					return A.one(instance.get('thumbsContainerSelector'));
				},
				_renderCPInstance(cpInstance) {
					var instance = this;

					var productContent = instance.getProductContent();

					var skus = productContent.all(
						'[data-text-cp-instance-sku]'
					);
					var prices = productContent.all(
						'[data-text-cp-instance-price]'
					);
					var subscriptionInfo = productContent.all(
						'[data-text-cp-instance-subscription-info]'
					);
					var deliverySubscriptionInfo = productContent.all(
						'[data-text-cp-instance-delivery-subscription-info]'
					);
					var availabilities = productContent.all(
						'[data-text-cp-instance-availability]'
					);
					var availabilityEstimates = productContent.all(
						'[data-text-cp-instance-availability-estimate]'
					);
					var stockQuantities = productContent.all(
						'[data-text-cp-instance-stock-quantity]'
					);
					var gtins = productContent.all(
						'[data-text-cp-instance-gtin]'
					);
					var manufacturerPartNumbers = productContent.all(
						'[data-text-cp-instance-manufacturer-part-number]'
					);
					var sampleFiles = productContent.all(
						'[data-text-cp-instance-sample-file]'
					);

					var skusShow = productContent
						.all('[data-text-cp-instance-sku-show]')
						.hide();
					var pricesShow = productContent
						.all('[data-text-cp-instance-price-show]')
						.hide();
					var subscriptionInfoShow = productContent
						.all('[data-text-cp-instance-subscription-info-show]')
						.hide();
					var deliverySubscriptionInfoShow = productContent
						.all(
							'[data-text-cp-instance-delivery-subscription-info-show]'
						)
						.hide();
					var availabilitiesShow = productContent
						.all('[data-text-cp-instance-availability-show]')
						.hide();
					var availabilityEstimatesShow = productContent
						.all(
							'[data-text-cp-instance-availability-estimate-show]'
						)
						.hide();
					var stockQuantitiesShow = productContent
						.all('[data-text-cp-instance-stock-quantity-show]')
						.hide();
					var gtinsShow = productContent
						.all('[data-text-cp-instance-gtin-show]')
						.hide();
					var manufacturerPartNumbersShow = productContent
						.all(
							'[data-text-cp-instance-manufacturer-part-number-show]'
						)
						.hide();
					var sampleFilesShow = productContent
						.all('[data-text-cp-instance-sample-file-show]')
						.hide();

					if (cpInstance.sku) {
						skus.setHTML(Liferay.Util.escape(cpInstance.sku));
						skusShow.show();
					}

					if (cpInstance.price) {
						prices.setHTML(Liferay.Util.escape(cpInstance.price));
						pricesShow.show();
					}

					if (cpInstance.subscriptionInfo) {
						subscriptionInfo.setHTML(cpInstance.subscriptionInfo);
						subscriptionInfoShow.show();
					}

					if (cpInstance.deliverySubscriptionInfo) {
						deliverySubscriptionInfo.setHTML(
							cpInstance.deliverySubscriptionInfo
						);
						deliverySubscriptionInfoShow.show();
					}

					if (cpInstance.gtin) {
						gtins.setHTML(cpInstance.gtin);
						gtinsShow.show();
					}

					if (cpInstance.manufacturerPartNumber) {
						manufacturerPartNumbers.setHTML(
							cpInstance.manufacturerPartNumber
						);
						manufacturerPartNumbersShow.show();
					}

					if (cpInstance.availability) {
						availabilities.setHTML(cpInstance.availability);
						availabilitiesShow.show();
					}

					if (cpInstance.availabilityEstimate) {
						availabilityEstimates.setHTML(
							cpInstance.availabilityEstimate
						);
						availabilityEstimatesShow.show();
					}

					if (cpInstance.stockQuantity) {
						stockQuantities.setHTML(cpInstance.stockQuantity);
						stockQuantitiesShow.show();
					}

					if (cpInstance.sampleFile) {
						sampleFiles.setHTML(cpInstance.sampleFile);
						sampleFilesShow.show();
					}

					productContent.all('[data-cp-instance-id]').each(node => {
						node.setAttribute(
							'data-cp-instance-id',
							cpInstance.cpInstanceId
						);
					});
				},
				_renderImages() {
					var instance = this;

					var ddmFormValues = JSON.stringify(
						instance.getFormValues()
					);

					var data = {};

					data[
						instance.get('namespace') + 'ddmFormValues'
					] = ddmFormValues;
					data.groupId = themeDisplay.getScopeGroupId();

					A.io.request(instance.get('viewAttachmentURL'), {
						data,
						on: {
							success(event, id, obj) {
								var response = JSON.parse(obj.response);

								instance._renderThumbsImages(response);
							}
						}
					});
				},
				_renderThumbsImages(images) {
					var instance = this;

					var thumbsContainer = instance._getThumbsContainer();

					thumbsContainer.setHTML('');

					images.forEach(image => {
						var thumbContainer = A.Node.create(
							'<div class="thumb" />'
						);

						thumbContainer.setAttribute('data-url', image.url);

						var imageNode = A.Node.create(
							'<img class="img-fluid" />'
						);

						imageNode.setAttribute('src', image.url);

						imageNode.appendTo(thumbContainer);

						thumbContainer.appendTo(thumbsContainer);
					});

					if (images.length > 0) {
						var fullImage = A.one(
							instance.get('fullImageSelector')
						);

						fullImage.setAttribute('src', images[0].url);
					}
				},
				_renderUI() {
					var instance = this;

					var productContent = instance.getProductContent();

					productContent.all('[data-cp-definition-id]').each(node => {
						node.setAttribute(
							'data-cp-definition-id',
							instance.get('cpDefinitionId')
						);
					});
				},
				checkCPInstance() {
					var instance = this;

					var cpDefinitionId = instance.get('cpDefinitionId');

					var productContentAuthToken = instance.get(
						'productContentAuthToken'
					);

					var portletURL = Liferay.PortletURL.createActionURL();

					portletURL.setPortletId(CP_CONTENT_WEB_PORTLET_KEY);
					portletURL.setName('checkCPInstance');
					portletURL.setParameter('cpDefinitionId', cpDefinitionId);
					portletURL.setParameter('p_auth', Liferay.authToken);
					portletURL.setParameter(
						'p_p_auth',
						productContentAuthToken
					);

					var ddmFormValues = JSON.stringify(
						instance.getFormValues()
					);

					var data = {};

					data[
						'_' + CP_CONTENT_WEB_PORTLET_KEY + '_ddmFormValues'
					] = ddmFormValues;
					data.groupId = themeDisplay.getScopeGroupId();

					A.io.request(portletURL.toString(), {
						data,
						on: {
							success(event, id, obj) {
								var response = JSON.parse(obj.response);

								if (response.cpInstanceExist) {
									instance._renderCPInstance(response);
									instance.set(
										'cpInstanceId',
										response.cpInstanceId
									);
								}

								Liferay.fire(
									cpDefinitionId + CP_INSTANCE_CHANGE_EVENT,
									response
								);
							}
						}
					});
				},
				destructor() {
					var instance = this;

					new A.EventHandle(instance._eventHandles).detach();
				},
				getCPDefinitionId() {
					return this.get('cpDefinitionId');
				},
				getCPInstanceId() {
					return this.get('cpInstanceId');
				},
				getFormValues() {
					var instance = this;

					var cpDefinitionId = instance.get('cpDefinitionId');

					var ddmForm = Liferay.component(
						'ProductOptions' + cpDefinitionId + 'DDMForm'
					);

					if (!ddmForm) {
						return [];
					}

					var fields = ddmForm.getImmediateFields();

					var fieldValues = [];

					fields.forEach(field => {
						var fieldValue = {};

						fieldValue.key = field.get('fieldName');

						var value = field.getValue();

						var arrValue = [];

						if (Array.isArray(value)) {
							arrValue = value;
						} else {
							arrValue.push(value);
						}

						fieldValue.value = arrValue;

						fieldValues.push(fieldValue);
					});

					return fieldValues;
				},
				getProductContent() {
					var instance = this;

					return A.one(instance.get('productContentSelector'));
				},
				getProductContentAuthToken() {
					return this.get('productContentAuthToken');
				},
				initializer(_config) {
					var instance = this;

					instance._bindUI();
					instance._renderUI();
				},
				validateProduct(callback) {
					var instance = this;

					var cpDefinitionId = instance.get('cpDefinitionId');

					var ddmForm = Liferay.component(
						'ProductOptions' + cpDefinitionId + 'DDMForm'
					);

					if (!ddmForm) {
						callback.call(instance, false);
					} else {
						ddmForm.validate(callback);
					}
				}
			}
		});

		Liferay.Portlet.ProductContent = ProductContent;
	},
	'',
	{
		requires: [
			'aui-base',
			'aui-io-request',
			'aui-parse-content',
			'liferay-portlet-base',
			'liferay-portlet-url'
		]
	}
);
