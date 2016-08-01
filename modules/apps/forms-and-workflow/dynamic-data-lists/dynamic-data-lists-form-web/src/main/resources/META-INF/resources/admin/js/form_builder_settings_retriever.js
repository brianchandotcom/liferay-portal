AUI.add(
	'liferay-ddl-form-builder-settings-retriever',
	function(A) {
		var CACHE = {};

		var Lang = A.Lang;

		var FormBuilderSettingsRetriever = A.Component.create(
			{
				ATTRS: {
					getFieldTypeSettingFormContextURL: {
						validator: Lang.isString,
						value: ''
					},

					portletNamespace: {
						validator: Lang.isString,
						value: ''
					}
				},

				EXTENDS: A.Base,

				NAME: 'liferay-ddl-form-builder-settings-retriever',

				prototype: {
					getSettingsContext: function(type, callback) {
						var instance = this;

						return new A.Promise(
							function(resolve, reject) {
								if (CACHE[type]) {
									resolve(A.merge(CACHE[type], {}));
								}
								else {
									var payload = {
										type: type
									};

									var portletNamespace = instance.get('portletNamespace');

									A.io.request(
										instance.get('getFieldTypeSettingFormContextURL'),
										{
											data: Liferay.Util.ns(portletNamespace, payload),
											dataType: 'JSON',
											method: 'GET',
											on: {
												failure: function(error) {
													reject(error);
												},
												success: function(event, status, xhr) {
													var context = JSON.parse(xhr.responseText);

													CACHE[type] = context;

													resolve(context);
												}
											}
										}
									);
								}
							}
						);
					}
				}
			}
		);

		Liferay.namespace('DDL').FormBuilderSettingsRetriever = FormBuilderSettingsRetriever;
	},
	'',
	{
		requires: ['aui-promise', 'aui-request']
	}
);