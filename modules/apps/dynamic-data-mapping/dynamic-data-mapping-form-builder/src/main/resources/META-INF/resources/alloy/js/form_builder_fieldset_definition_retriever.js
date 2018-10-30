AUI.add(
	'liferay-ddm-form-builder-fieldset-definition-retriever',
	function(A) {
		var FormBuilderFieldSetDefinitionRetriever = A.Component.create(
			{
				EXTENDS: A.Base,

				NAME: 'liferay-ddm-form-builder-fieldset-definition-retriever',

				prototype: {
					getDefinition: function(fieldSet) {
						var instance = this;

						return new A.Promise(
							function(resolve, reject) {
								var resolveJSON = function(json) {
									var parsed = JSON.parse(json);

									resolve(parsed);

									return parsed;
								};

								var payload = {
									ddmStructureId: fieldSet.get('id'),
									languageId: themeDisplay.getLanguageId(),
									portletNamespace: Liferay.DDM.Settings.portletNamespace,
									scopeGroupId: themeDisplay.getScopeGroupId()
								};

								A.io.request(
									Liferay.DDM.Settings.fieldSetDefinitionURL,
									{
										data: payload,
										dataType: 'JSON',
										method: 'GET',
										on: {
											failure: function(error) {
												reject(error);
											},
											success: function(event, status, xhr) {
												var definitionJSON = xhr.responseText;

												resolveJSON(definitionJSON);
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

		Liferay.namespace('DDM').FormBuilderFieldSetDefinitionRetriever = FormBuilderFieldSetDefinitionRetriever;
	},
	'',
	{
		requires: ['aui-promise', 'aui-request']
	}
);