AUI.add(
	'liferay-commerce-product-content',
	function(A) {
		var AArray = A.Array;

		var Lang = A.Lang;

		var STR_DDM_FORM_EVENT = 'DDMForm:render';

		var ProductContent = A.Component.create(
			{
				ATTRS: {
					cpDefinitionId: {
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
						console.log("Inizialized");
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},
					_renderUI: function() {
						var instance = this;
					},
					_bindUI: function() {
						var instance = this;

						var eventHandles = [];

						var cpDefinitionId = instance.get('cpDefinitionId');

						eventHandles.push(
							Liferay.on( cpDefinitionId + STR_DDM_FORM_EVENT, instance._ddmFormRender, instance)
						);

						instance._eventHandles = eventHandles;
					},
					_ddmFormRender: function(event) {
						var instance = this;

						var form = event.form;

						form.after("*:valueChange", instance._ddmFormChange, instance);
					},
					_ddmFormChange: function(valueChangeEvent) {

						var instance = this;

						instance._renderImages();
					},
					_renderImages: function() {
						var instance = this;

						var cpDefinitionId = instance.get('cpDefinitionId');

						var ddmForm = Liferay.component(cpDefinitionId + "DDMForm");
						var ddmFormValues = "";

						if (ddmForm) {
							ddmFormValues = JSON.stringify(ddmForm.toJSON());
						}

						A.io.request(
							instance.get("viewAttachmentURL"),
							{
								data: {
									'_com_liferay_commerce_product_content_web_internal_portlet_CPContentPortlet_ddmFormValues' : ddmFormValues
								},
								on: {
									success: function(event, id, obj) {
										var response = JSON.parse(obj.response);

										console.log(response);
									}
								}
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
		requires: ['aui-base', 'liferay-portlet-base', 'aui-io-request' , 'aui-parse-content']
	}
);