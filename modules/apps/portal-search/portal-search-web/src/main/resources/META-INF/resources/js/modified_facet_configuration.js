AUI.add(
	'liferay-search-modified-facet-configuration',
	function(A) {
		var ModifiedFacetConfiguration = function(form) {
			var instance = this;

			instance.form = form;

			instance.form.on('submit', A.bind(instance._onSubmit, instance));
		};

		A.mix(
			ModifiedFacetConfiguration.prototype,
			{
				_onSubmit: function(event) {
					var instance = this;

					event.preventDefault();

					var ranges = [];

					var rangeFormRows = A.all('.range-form-row').filter(
						function(item) {
							return !item.get('hidden');
						}
					);

					rangeFormRows.each(
						function(item) {
							var label = item.one('.label-input').val();

							var range = item.one('.range-input').val();

							ranges.push(
								{
									label: label,
									range: range
								}
							);
						}
					);

					instance.form.one('.ranges-input').val(JSON.stringify(ranges));

					submitForm(instance.form);
				}
			}
		);

		Liferay.namespace('Search').ModifiedFacetConfiguration = ModifiedFacetConfiguration;
	},
	'',
	{
		requires: ['aui-node']
	}
);