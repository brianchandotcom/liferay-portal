/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-search-date-facet-configuration',
	(A) => {
		const DateFacetConfiguration = function (form) {
			const instance = this;

			instance.form = form;

			instance.form.on('submit', A.bind(instance._onSubmit, instance));
		};

		A.mix(DateFacetConfiguration.prototype, {
			_onSubmit(event) {
				const instance = this;

				event.preventDefault();

				const ranges = [];

				const rangeFormRows = A.all('.range-form-row').filter(
					(item) => {
						return !item.get('hidden');
					}
				);

				rangeFormRows.each((item) => {
					const label = item.one('.label-input').val();

					const range = item.one('.range-input').val();

					ranges.push({
						label,
						range,
					});
				});

				instance.form.one('.ranges-input').val(JSON.stringify(ranges));

				submitForm(instance.form);
			},
		});

		Liferay.namespace(
			'Search'
		).DateFacetConfiguration = DateFacetConfiguration;
	},
	'',
	{
		requires: ['aui-node'],
	}
);
