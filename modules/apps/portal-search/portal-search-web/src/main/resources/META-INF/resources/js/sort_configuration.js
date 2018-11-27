AUI.add(
	'liferay-search-sort-configuration',
	function(A) {
		var SortConfiguration = function(form) {
			var instance = this;

			instance.form = form;

			instance.form.on('submit', A.bind(instance._onSubmit, instance));
		};

		A.mix(
			SortConfiguration.prototype,
			{
				_onSubmit: function(event) {
					var instance = this;

					event.preventDefault();

					var fields = [];

					var fieldFormRows = A.all('.field-form-row').filter(
						function(item) {
							return !item.get('hidden');
						}
					);

					fieldFormRows.each(
						function(item) {
							var label = item.one('.label-input').val();

							var field = item.one('.sort-field-input').val();

							fields.push(
								{
									label: label,
									field: field
								}
							);
						}
					);

					instance.form.one('.fields-input').val(JSON.stringify(fields));

					submitForm(instance.form);
				}
			}
		);

		Liferay.namespace('Search').SortConfiguration = SortConfiguration;
	},
	'',
	{
		requires: ['aui-node']
	}
);