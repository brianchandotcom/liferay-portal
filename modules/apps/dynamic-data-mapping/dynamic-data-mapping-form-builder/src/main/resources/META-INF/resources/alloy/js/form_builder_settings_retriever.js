AUI.add(
	'liferay-ddm-form-builder-settings-retriever',
	function(A) {
		var CACHE = {};

		var FormBuilderSettingsRetriever = A.Component.create(
			{
				EXTENDS: A.Base,

				NAME: 'liferay-ddm-form-builder-settings-retriever',

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
									return resolve(settingsContext);
								}
								else if (cachedContextJSON) {
									settingsContext = resolveJSON(cachedContextJSON);

									field.set('context.settingsContext', settingsContext);

									return settingsContext;
								}

								var payload = {
									languageId: themeDisplay.getLanguageId(),
									portletNamespace: Liferay.DDM.Settings.portletNamespace,
									scopeGroupId: themeDisplay.getScopeGroupId(),
									type: type
								};

								A.io.request(
									Liferay.DDM.Settings.getFieldTypeSettingFormContextURL,
									{
										data: payload,
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

												return settingsContext;
											}
										}
									}
								);
							}
						);
					}
				}
			}
		);

		Liferay.namespace('DDM').FormBuilderSettingsRetriever = FormBuilderSettingsRetriever;
	},
	'',
	{
		requires: ['aui-promise', 'aui-request']
	}
);