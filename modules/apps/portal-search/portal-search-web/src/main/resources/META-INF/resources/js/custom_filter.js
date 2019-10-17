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
	A => {
		var FacetUtil = Liferay.Search.FacetUtil;

		var CustomFilter = function(form) {
			var instance = this;

			instance.form = form;

			instance.form.on('submit', A.bind(instance._onSubmit, instance));

			instance.filterValueInput = instance.form.one(
				'.custom-filter-value-input'
			);

			var applyButton = instance.form.one('.custom-filter-apply-button');

			applyButton.on('click', A.bind(instance._onClick, instance));
		};

		A.mix(CustomFilter.prototype, {
			_onClick() {
				var instance = this;

				instance.search();
			},

			_onSubmit(event) {
				var instance = this;

				event.stopPropagation();

				instance.search();
			},

			getFilterValue() {
				var instance = this;

				var filterValue = instance.filterValueInput.val();

				return filterValue;
			},

			search() {
				var instance = this;

				var searchURL = instance.form.get('action');

				var queryString = instance.updateQueryString(
					document.location.search
				);

				document.location.href = searchURL + queryString;
			},

			updateQueryString(queryString) {
				var instance = this;

				var hasQuestionMark = false;

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
			}
		});

		Liferay.namespace('Search').CustomFilter = CustomFilter;
	},
	'',
	{
		requires: ['liferay-search-facet-util']
	}
);
