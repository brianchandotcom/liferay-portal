AUI.add(
	'liferay-commerce-product-content',
	function(A) {
		var STR_DDM_FORM_EVENT = 'DDMForm:render';

		var CP_CONTENT_WEB_PORTLET_KEY = 'com_liferay_commerce_product_content_web_internal_portlet_CPContentPortlet';

		var CP_INSTANCE_CHANGE_EVENT = 'CPInstance:change';

		var ProductContent = A.Component.create(
			{
				ATTRS: {
					cpDefinitionId: {
					},
					fullImageSelector: {
					},
					productContentSelector:{
					},
					thumbsContainerSelector: {
					},
					viewAttachmentURL: {
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'productcontent',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._bindUI();
						instance._renderUI();
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},
					checkCPInstance: function() {
						var instance = this;

						var cpDefinitionId = instance.get('cpDefinitionId');

						var portletURL = Liferay.PortletURL.createActionURL();

						portletURL.setPortletId(CP_CONTENT_WEB_PORTLET_KEY);
						portletURL.setName('checkCPInstance');
						portletURL.setParameter('cpDefinitionId', cpDefinitionId);
						portletURL.setParameter('p_auth', Liferay.authToken);

						var ddmFormValues = JSON.stringify(instance.getFormValues());

						var data = {};

						data[instance.get('namespace') + 'ddmFormValues'] = ddmFormValues;

						A.io.request(
							portletURL.toString(),
							{
								data: data,
								on: {
									success: function(event, id, obj) {
										var response = JSON.parse(obj.response);

										if (response.cpInstanceExist) {
											instance._renderCPInstance(response);
											instance.set('cpInstanceId', response.cpInstanceId);
										}

										Liferay.fire(cpDefinitionId + CP_INSTANCE_CHANGE_EVENT, response);
									}
								}
							}
						);
					},
					getCPDefinitionId: function() {
						return this.get('cpDefinitionId');
					},
					getCPInstanceId: function() {
						return this.get('cpInstanceId');
					},
					getFormValues: function() {
						var instance = this;

						var cpDefinitionId = instance.get('cpDefinitionId');

						var ddmForm = Liferay.component(cpDefinitionId + 'DDMForm');

						if (!ddmForm) {
							return [];
						}

						var fields = ddmForm.getImmediateFields();

						var fieldValues = [];

						fields.forEach(
							function(field) {
								var fieldValue = {};

								fieldValue.key = field.get('fieldName');

								var value  =  field.getValue();

								var arrValue = [];

								if (value instanceof Array) {
									arrValue = value;
								} else {
									arrValue.push(value);
								}

								fieldValue.value = arrValue;

								fieldValues.push(fieldValue);
							}
						);

						return fieldValues;
					},
					getProductContent: function() {
						var instance = this;

						return A.one(instance.get('productContentSelector'));
					},
					validateProduct: function(callback) {
						var instance = this;

						var cpDefinitionId = instance.get('cpDefinitionId');

						var ddmForm = Liferay.component(cpDefinitionId + 'DDMForm');

						if (!ddmForm) {
							callback.call(instance, false);
						}
						else {
							ddmForm.validate(callback);
						}

					},
					_bindUI: function() {
						var instance = this;

						var eventHandles = [];

						var cpDefinitionId = instance.get('cpDefinitionId');

						var form = Liferay.component(cpDefinitionId + 'DDMForm');

						if (form) {
							form.after('*:valueChange', instance._ddmFormChange, instance);
						}

						eventHandles.push(
							Liferay.on(cpDefinitionId + STR_DDM_FORM_EVENT, instance._ddmFormRender, instance)
						);

						instance._eventHandles = eventHandles;
					},
					_ddmFormChange: function(valueChangeEvent) {
						var instance = this;

						instance._renderImages();

						instance.checkCPInstance();
					},
					_ddmFormRender: function(event) {
						var instance = this;

						var form = event.form;

						form.after('*:valueChange', instance._ddmFormChange, instance);
					},
					_getThumbsContainer: function() {
						var instance = this;

						return A.one(instance.get('thumbsContainerSelector'));
					},
					_renderImages: function() {
						var instance = this;

						var ddmFormValues = JSON.stringify(instance.getFormValues());

						var data = {};

						data[instance.get('namespace') + 'ddmFormValues'] = ddmFormValues;

						A.io.request(
							instance.get('viewAttachmentURL'),
							{
								data: data,
								on: {
									success: function(event, id, obj) {
										var response = JSON.parse(obj.response);

										instance._renderThumbsImages(response);
									}
								}
							}
						);
					},
					_renderThumbsImages: function(images) {
						var instance = this;

						var thumbsContainer = instance._getThumbsContainer();

						thumbsContainer.setHTML('');

						images.forEach(
							function(image) {
								var thumbContainer = A.Node.create('<div class="thumb" />');

								thumbContainer.setAttribute('data-url', image.url);

								var imageNode = A.Node.create('<img class="center-block img-responsive" />');

								imageNode.setAttribute('src', image.url);

								imageNode.appendTo(thumbContainer);

								thumbContainer.appendTo(thumbsContainer);
							}
						);

						if (images.length > 0) {
							var fullImage = A.one(instance.get('fullImageSelector'));

							fullImage.setAttribute('src', images[0].url);
						}
					},
					_renderCPInstance: function(cpInstance) {
						var instance = this;

						var productContent = instance.getProductContent();

						var skus = productContent.all('[data-text-cp-instance-sku]');
						var prices = productContent.all('[data-text-cp-instance-price]');
						var availabilities = productContent.all('[data-text-cp-instance-availability]');
						var availabilityRanges = productContent.all('[data-text-cp-instance-availability-ranges]');
						var stockQuantities = productContent.all('[data-text-cp-instance-stock-quantity]');
						var gtins = productContent.all('[data-text-cp-instance-gtin]');
						var manufacturerPartNumbers = productContent.all('[data-text-cp-instance-manufacturer-part-number]');

						skus.setHTML(cpInstance.sku);
						prices.setHTML(cpInstance.price);
						gtins.setHTML(cpInstance.gtin);
						manufacturerPartNumbers.setHTML(cpInstance.manufacturerPartNumber);

						if (cpInstance.availability) {
							availabilities.setHTML(cpInstance.availability);
						}

						if (cpInstance.availabilityRange) {
							availabilityRanges.setHTML(cpInstance.availabilityRange);
						}

						if (cpInstance.stockQuantity) {
							stockQuantities.setHTML(cpInstance.stockQuantity);
						}

						productContent.all('[data-cp-instance-id]').each(
							function(node) {
								node.setAttribute('data-cp-instance-id', cpInstance.cpInstanceId);
							}
						);

					},
					_renderUI: function() {
						var instance = this;

						var productContent = instance.getProductContent();

						productContent.all('[data-cp-definition-id]').each(
							function(node) {
								node.setAttribute('data-cp-definition-id', instance.get('cpDefinitionId'))
							}
						);
					}
				}
			}
		);

		Liferay.Portlet.ProductContent = ProductContent;
	},
	'',
	{
		requires: ['aui-base', 'aui-io-request', 'aui-parse-content', 'liferay-portlet-base', 'liferay-portlet-url']
	}
);