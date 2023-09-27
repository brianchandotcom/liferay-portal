/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-commerce-product-content',
	(A) => {
		const CP_INSTANCE_CHANGE_EVENT = 'CPInstance:change';

		const ProductContent = A.Component.create({
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
					const instance = this;

					const eventHandles = [];

					eventHandles.push(
						Liferay.on(
							'product-instance-changed',
							instance._formFieldsChange,
							instance
						)
					);

					instance._eventHandles = eventHandles;
				},
				_formFieldsChange(dispatchedPayload) {
					const instance = this;

					const cpDefinitionId = instance.get('cpDefinitionId');

					const cpInstance = dispatchedPayload.cpInstance;

					const formFields = dispatchedPayload.formFields;

					instance.set('cpInstanceId', cpInstance.cpInstanceId);

					if (formFields) {
						instance.set('formFields', formFields);
					}

					instance._renderImages();
					instance._renderCPInstance(cpInstance);

					Liferay.fire(
						cpDefinitionId + CP_INSTANCE_CHANGE_EVENT,
						cpInstance
					);
				},
				_getThumbsContainer() {
					const instance = this;

					return A.one(instance.get('thumbsContainerSelector'));
				},
				_renderCPInstance(cpInstance) {
					const instance = this;

					const productContent = instance.getProductContent();

					const skus = productContent.all(
						'[data-text-cp-instance-sku]'
					);
					const prices = productContent.all(
						'[data-text-cp-instance-price]'
					);
					const subscriptionInfo = productContent.all(
						'[data-text-cp-instance-subscription-info]'
					);
					const deliverySubscriptionInfo = productContent.all(
						'[data-text-cp-instance-delivery-subscription-info]'
					);
					const availabilities = productContent.all(
						'[data-text-cp-instance-availability]'
					);
					const availabilityEstimates = productContent.all(
						'[data-text-cp-instance-availability-estimate]'
					);
					const stockQuantities = productContent.all(
						'[data-text-cp-instance-stock-quantity]'
					);
					const gtins = productContent.all(
						'[data-text-cp-instance-gtin]'
					);
					const manufacturerPartNumbers = productContent.all(
						'[data-text-cp-instance-manufacturer-part-number]'
					);
					const sampleFiles = productContent.all(
						'[data-text-cp-instance-sample-file]'
					);

					const skusShow = productContent
						.all('[data-text-cp-instance-sku-show]')
						.hide();
					const pricesShow = productContent
						.all('[data-text-cp-instance-price-show]')
						.hide();
					const subscriptionInfoShow = productContent
						.all('[data-text-cp-instance-subscription-info-show]')
						.hide();
					const deliverySubscriptionInfoShow = productContent
						.all(
							'[data-text-cp-instance-delivery-subscription-info-show]'
						)
						.hide();
					const availabilitiesShow = productContent
						.all('[data-text-cp-instance-availability-show]')
						.hide();
					const availabilityEstimatesShow = productContent
						.all(
							'[data-text-cp-instance-availability-estimate-show]'
						)
						.hide();
					const stockQuantitiesShow = productContent
						.all('[data-text-cp-instance-stock-quantity-show]')
						.hide();
					const gtinsShow = productContent
						.all('[data-text-cp-instance-gtin-show]')
						.hide();
					const manufacturerPartNumbersShow = productContent
						.all(
							'[data-text-cp-instance-manufacturer-part-number-show]'
						)
						.hide();
					const sampleFilesShow = productContent
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
						subscriptionInfo.setHTML(
							Liferay.Util.escape(cpInstance.subscriptionInfo)
						);
						subscriptionInfoShow.show();
					}

					if (cpInstance.deliverySubscriptionInfo) {
						deliverySubscriptionInfo.setHTML(
							Liferay.Util.escape(
								cpInstance.deliverySubscriptionInfo
							)
						);
						deliverySubscriptionInfoShow.show();
					}

					if (cpInstance.gtin) {
						gtins.setHTML(Liferay.Util.escape(cpInstance.gtin));
						gtinsShow.show();
					}

					if (cpInstance.manufacturerPartNumber) {
						manufacturerPartNumbers.setHTML(
							Liferay.Util.escape(
								cpInstance.manufacturerPartNumber
							)
						);
						manufacturerPartNumbersShow.show();
					}

					if (cpInstance.availability) {
						availabilities.setHTML(
							Liferay.Util.escape(cpInstance.availability)
						);
						availabilitiesShow.show();
					}

					if (cpInstance.availabilityEstimate) {
						availabilityEstimates.setHTML(
							Liferay.Util.escape(cpInstance.availabilityEstimate)
						);
						availabilityEstimatesShow.show();
					}

					if (cpInstance.stockQuantity) {
						stockQuantities.setHTML(
							Liferay.Util.escape(cpInstance.stockQuantity)
						);
						stockQuantitiesShow.show();
					}

					if (cpInstance.sampleFile) {
						sampleFiles.setHTML(
							Liferay.Util.escape(cpInstance.sampleFile)
						);
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
					const instance = this;

					const formFields = instance.get('formFields');

					const data = {};

					data[
						instance.get('namespace') + 'formFields'
					] = JSON.stringify(formFields);
					data.groupId = themeDisplay.getScopeGroupId();

					// eslint-disable-next-line @liferay/aui/no-io
					A.io.request(instance.get('viewAttachmentURL'), {
						data,
						on: {
							success(event, id, object) {
								const response = JSON.parse(object.response);

								instance._renderThumbsImages(response);
							},
						},
					});
				},
				_renderThumbsImages(images) {
					const instance = this;

					const thumbsContainer = instance._getThumbsContainer();

					thumbsContainer.setHTML('');

					images.forEach((image) => {
						const thumbContainer = A.Node.create(
							'<div class="thumb" />'
						);

						thumbContainer.setAttribute('data-url', image.url);

						const imageNode = A.Node.create(
							'<img class="img-fluid" />'
						);

						imageNode.setAttribute('src', image.url);

						imageNode.appendTo(thumbContainer);

						thumbContainer.appendTo(thumbsContainer);
					});

					if (images.length) {
						const fullImage = A.one(
							instance.get('fullImageSelector')
						);

						fullImage.setAttribute('src', images[0].url);
					}
				},
				_renderUI() {
					const instance = this;

					const productContent = instance.getProductContent();

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
					const instance = this;

					new A.EventHandle(instance._eventHandles).detach();
				},
				getCPDefinitionId() {
					return this.get('cpDefinitionId');
				},
				getCPInstanceId() {
					return this.get('cpInstanceId');
				},
				getFormValues() {
					const instance = this;

					return instance.get('formFields');
				},
				getProductContent() {
					const instance = this;

					return A.one(instance.get('productContentSelector'));
				},
				getProductContentAuthToken() {
					return this.get('productContentAuthToken');
				},
				initializer(_config) {
					const instance = this;

					instance._bindUI();
					instance._renderUI();
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
