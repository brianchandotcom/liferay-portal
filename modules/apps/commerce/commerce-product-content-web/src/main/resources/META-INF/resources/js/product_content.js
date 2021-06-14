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
	(A) => {
		var CP_CONTENT_WEB_PORTLET_KEY =
			'com_liferay_commerce_product_content_web_internal_portlet_CPContentPortlet';

		var CP_INSTANCE_CHANGE_EVENT = 'CPInstance:change';

		var DDM_FORM_HANDLER_MODULE =
			'commerce-frontend-js/utilities/forms/DDMFormHandler';

		var ProductContent = A.Component.create({
			ATTRS: {
				checkCPInstanceActionURL: {},
				cpDefinitionId: {},
				fullImageSelector: {},
				productContentAuthToken: {},
				productContentSelector: {},
				thumbsContainerSelector: {},
				viewAttachmentURL: {},
			},

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME: 'productcontent',

			prototype: {
				_bindUI() {
					var instance = this;

					var eventHandles = [];

					var checkCPInstanceActionURL = instance.get(
						'checkCPInstanceActionURL'
					);

					var cpDefinitionId = instance.get('cpDefinitionId');

					var DDMFormInstance = Liferay.component(
						'ProductOptions' + cpDefinitionId
					);

					if (DDMFormInstance) {
						Liferay.Loader.require(
							DDM_FORM_HANDLER_MODULE,
							(module) => {
								var DDMFormHandler = module.default;

								var FormHandlerConfiguration = {
									DDMFormInstance,
									actionURL: checkCPInstanceActionURL,
									addToCartId: 'addToCartId',
									portletId: CP_CONTENT_WEB_PORTLET_KEY,
								};

								new DDMFormHandler(FormHandlerConfiguration);
							}
						);
					}

					eventHandles.push(
						Liferay.on(
							'product-instance-changed',
							instance._ddmFormChange,
							instance
						)
					);

					instance._eventHandles = eventHandles;
				},
				_ddmFormChange(dispatchedPayload) {
					var instance = this;

					var cpDefinitionId = instance.get('cpDefinitionId');

					var cpInstance = dispatchedPayload.cpInstance;

					var ddmFormValues = dispatchedPayload.formFields;

					instance.set('cpInstanceId', cpInstance.cpInstanceId);

					if (ddmFormValues) {
						instance.set('ddmFormValues', ddmFormValues);
					}

					instance._renderImages();
					instance._renderCPInstance(cpInstance);

					Liferay.fire(
						cpDefinitionId + CP_INSTANCE_CHANGE_EVENT,
						cpInstance
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

					productContent.all('[data-cp-instance-id]').each((node) => {
						node.setAttribute(
							'data-cp-instance-id',
							cpInstance.cpInstanceId
						);
					});
				},
				_renderImages() {
					var instance = this;

					var ddmFormValues = instance.get('ddmFormValues');

					var data = {};

					data[
						instance.get('namespace') + 'ddmFormValues'
					] = JSON.stringify(ddmFormValues);
					data.groupId = themeDisplay.getScopeGroupId();

					A.io.request(instance.get('viewAttachmentURL'), {
						data,
						on: {
							success(event, id, object) {
								var response = JSON.parse(object.response);

								instance._renderThumbsImages(response);
							},
						},
					});
				},
				_renderThumbsImages(images) {
					var instance = this;

					var thumbsContainer = instance._getThumbsContainer();

					thumbsContainer.setHTML('');

					images.forEach((image) => {
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

					productContent
						.all('[data-cp-definition-id]')
						.each((node) => {
							node.setAttribute(
								'data-cp-definition-id',
								instance.get('cpDefinitionId')
							);
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

					return instance.get('ddmFormValues');
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
					}
					else {
						ddmForm.validate(callback);
					}
				},
			},
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
			'liferay-portlet-url',
		],
	}
);
