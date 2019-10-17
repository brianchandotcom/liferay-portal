/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

AUI.add(
	'liferay-search-modified-facet-configuration',
	A => {
		var ModifiedFacetConfiguration = function(form) {
			var instance = this;

			instance.form = form;

			instance.form.on('submit', A.bind(instance._onSubmit, instance));
		};

		A.mix(ModifiedFacetConfiguration.prototype, {
			_onSubmit(event) {
				var instance = this;

				event.preventDefault();

				var ranges = [];

				var rangeFormRows = A.all('.range-form-row').filter(item => {
					return !item.get('hidden');
				});

				rangeFormRows.each(item => {
					var label = item.one('.label-input').val();

					var range = item.one('.range-input').val();

					ranges.push({
						label,
						range
					});
				});

				instance.form.one('.ranges-input').val(JSON.stringify(ranges));

				submitForm(instance.form);
			}
		});

		Liferay.namespace(
			'Search'
		).ModifiedFacetConfiguration = ModifiedFacetConfiguration;
	},
	'',
	{
		requires: ['aui-node']
	}
);
