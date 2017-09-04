AUI.add(
	'liferay-commerce-product-content',
	function(A) {

		var STR_DDM_FORM_EVENT = 'DDMForm:render';

		var ProductContent = A.Component.create(
			{
				ATTRS: {
					cpDefinitionId: {
					},
					fullImageSelector: {
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
					getFormValues: function() {
						var instance = this;

						var cpDefinitionId = instance.get('cpDefinitionId');

						var ddmForm = Liferay.component(cpDefinitionId + 'DDMForm');

						if (!ddmForm) {
							return {};
						}

						var fields = ddmForm.getImmediateFields();

						var fieldValues = [];

						fields.forEach(
							function(field) {
								var fieldValue = {};

								fieldValue.key = field.get('fieldName');
								fieldValue.value = field.getValue();

								fieldValues.push(fieldValue);

							}
						);

						return fieldValues;
					},
					_bindUI: function() {
						var instance = this;

						var eventHandles = [];

						var cpDefinitionId = instance.get('cpDefinitionId');

						eventHandles.push(
							Liferay.on(cpDefinitionId + STR_DDM_FORM_EVENT, instance._ddmFormRender, instance)
						);

						instance._eventHandles = eventHandles;
					},
					_ddmFormChange: function(valueChangeEvent) {

						var instance = this;

						instance._renderImages();
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
								var thumbContainer = A.Node.create('<div class="card thumb" />');

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
					_renderUI: function() {
						var instance = this;

						A.all('[data-cp-definition-id]').each(
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
		requires: ['aui-base', 'aui-io-request', 'aui-parse-content', 'liferay-portlet-base']
	}
);