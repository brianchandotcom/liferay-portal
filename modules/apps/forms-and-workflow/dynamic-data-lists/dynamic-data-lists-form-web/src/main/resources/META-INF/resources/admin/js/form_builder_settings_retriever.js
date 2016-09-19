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
								var resolveJSON = function(json) {
									resolve(JSON.parse(json));
								};

								var cachedContextJSON = CACHE[type];

								if (cachedContextJSON) {
									resolveJSON(cachedContextJSON);
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
													var contextJSON = xhr.responseText;

													CACHE[type] = contextJSON;

													resolveJSON(contextJSON);
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