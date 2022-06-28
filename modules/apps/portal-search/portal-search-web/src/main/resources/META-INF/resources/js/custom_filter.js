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
	'liferay-search-custom-filter',
	(A) => {
		const FacetUtil = Liferay.Search.FacetUtil;

		const CustomFilter = function (form) {
			if (!form) {
				return;
			}

			const instance = this;

			instance.form = form;

			instance.form.on('submit', A.bind(instance._onSubmit, instance));

			instance.filterValueInput = instance.form.one(
				'.custom-filter-value-input'
			);

			const applyButton = instance.form.one(
				'.custom-filter-apply-button'
			);

			if (applyButton) {
				applyButton.on('click', A.bind(instance._onClick, instance));
			}
		};

		A.mix(CustomFilter.prototype, {
			_onClick() {
				const instance = this;

				instance.search();
			},

			_onSubmit(event) {
				const instance = this;

				event.stopPropagation();

				instance.search();
			},

			getFilterValue() {
				const instance = this;

				const filterValue = instance.filterValueInput.val();

				return filterValue;
			},

			search() {
				const instance = this;

				const searchURL = instance.form.get('action');

				const queryString = instance.updateQueryString(
					document.location.search
				);

				document.location.href = searchURL + queryString;
			},

			updateQueryString(queryString) {
				const instance = this;

				let hasQuestionMark = false;

				if (queryString[0] === '?') {
					hasQuestionMark = true;
				}

				queryString = FacetUtil.updateQueryString(
					instance.filterValueInput.get('name'),
					[instance.getFilterValue()],
					queryString
				);

				if (!hasQuestionMark) {
					queryString = '?' + queryString;
				}

				return queryString;
			},
		});

		Liferay.namespace('Search').CustomFilter = CustomFilter;
	},
	'',
	{
		requires: ['liferay-search-facet-util'],
	}
);
