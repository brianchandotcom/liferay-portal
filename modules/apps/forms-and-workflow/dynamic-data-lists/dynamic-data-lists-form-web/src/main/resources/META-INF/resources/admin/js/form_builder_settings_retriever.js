AUI.add(
	'liferay-ddl-form-builder-settings-retriever',
	function(A) {
		var CACHE = {};

		var Settings = Liferay.DDL.Settings;

		var FormBuilderSettingsRetriever = A.Component.create(
			{
				EXTENDS: A.Base,

				NAME: 'liferay-ddl-form-builder-settings-retriever',

				prototype: {
					getSettingsContext: function(field, callback) {
						var instance = this;

						return new A.Promise(
							function(resolve, reject) {
								var resolveJSON = function(json) {
									var parsed = JSON.parse(json);

									resolve(parsed);

									return parsed;
								};

								var type = field.get('type');

								var settingsContext = field.get('context.settingsContext');

								var cachedContextJSON = CACHE[type];

								if (settingsContext) {
									resolve(settingsContext);
								}
								else if (cachedContextJSON) {
									settingsContext = resolveJSON(cachedContextJSON);

									field.set('context.settingsContext', settingsContext);
								}
								else {
									var payload = {
										type: type
									};

									A.io.request(
										Settings.getFieldTypeSettingFormContextURL,
										{
											data: Liferay.Util.ns(Settings.portletNamespace, payload),
											dataType: 'JSON',
											method: 'GET',
											on: {
												failure: function(error) {
													reject(error);
												},
												success: function(event, status, xhr) {
													var contextJSON = xhr.responseText;

													CACHE[type] = contextJSON;

													settingsContext = resolveJSON(contextJSON);

													field.set('context.settingsContext', settingsContext);
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