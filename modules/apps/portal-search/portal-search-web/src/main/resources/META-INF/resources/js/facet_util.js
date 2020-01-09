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
	'liferay-search-facet-util',
	A => {
		const FACET_TERM_CLASS = 'facet-term';

		const FACET_TERM_SELECTED_CLASS = 'facet-term-selected';

		/**
		 * Gets the ID by checking the `data-term-id` attribute and then `id` if
		 * `data-term-id` is not defined.
		 *
		 * The default layout continues to use `data-term-id` in case the
		 * original ID format `${namespace}_term_${index}` is expected, but
		 * newer layouts (ADT) sometimes only use `id`.
		 */
		function _getTermId(term) {
			return term.getAttribute('data-term-id') || term.id;
		}

		/**
		 * Converts a NodeList to an array of nodes. This allows array
		 * methods to be performed.
		 * @param {NodeList} nodeList
		 */
		function _transformNodeListToArray(nodeList) {
			const nodeArray = [];

			nodeList.forEach(node => nodeArray.push(node));

			return nodeArray;
		}

		const FacetUtil = {
			addURLParameter(key, value, parameterArray) {
				key = encodeURIComponent(key);
				value = encodeURIComponent(value);

				parameterArray[parameterArray.length] = [key, value].join('=');

				return parameterArray;
			},

			changeSelection(event) {
				event.preventDefault();

				const form = event.currentTarget.form;

				if (!form) {
					return;
				}

				const currentSelectedTermId = _getTermId(event.currentTarget);

				const facetTerms = document.querySelectorAll(
					`#${form.id} .${FACET_TERM_CLASS}`
				);

				const selectedTerms = _transformNodeListToArray(facetTerms)
					.filter(term => {
						if (term.type === 'checkbox') {
							return term.checked;
						}

						const isCurrentTarget =
							_getTermId(term) === currentSelectedTermId;

						const isSelected = Array.prototype.includes.call(
							term.classList,
							FACET_TERM_SELECTED_CLASS
						);

						return isCurrentTarget ? !isSelected : isSelected;
					})
					.map(term => _getTermId(term));

				FacetUtil.selectTerms(form, selectedTerms);
			},

			clearSelections(event) {
				var form = A.one(event.target).ancestor('form');

				if (!form) {
					return;
				}

				var selections = [];

				FacetUtil.selectTerms(form._node, selections);
			},

			enableInputs(inputs) {
				inputs.forEach(term => {
					Liferay.Util.toggleDisabled(term, false);
				});
			},

			removeURLParameters(key, parameterArray) {
				key = encodeURIComponent(key);

				var newParameters = parameterArray.filter(item => {
					var itemSplit = item.split('=');

					if (itemSplit && itemSplit[0] === key) {
						return false;
					}

					return true;
				});

				return newParameters;
			},

			selectTerms(form, selections) {
				var formParameterName = document.querySelector(
					'#' + form.id + ' input.facet-parameter-name'
				);

				document.location.search = FacetUtil.updateQueryString(
					formParameterName.value,
					selections,
					document.location.search
				);
			},

			setURLParameter(url, name, value) {
				var parts = url.split('?');

				var address = parts[0];

				var queryString = parts[1];

				if (!queryString) {
					queryString = '';
				}

				queryString = Liferay.Search.FacetUtil.updateQueryString(
					name,
					[value],
					queryString
				);

				return address + '?' + queryString;
			},

			setURLParameters(key, values, parameterArray) {
				var newParameters = FacetUtil.removeURLParameters(
					key,
					parameterArray
				);

				values.forEach(item => {
					newParameters = FacetUtil.addURLParameter(
						key,
						item,
						newParameters
					);
				});

				return newParameters;
			},

			updateQueryString(key, selections, queryString) {
				var search = queryString;

				var hasQuestionMark = false;

				if (search[0] === '?') {
					hasQuestionMark = true;
				}

				if (hasQuestionMark) {
					search = search.substr(1);
				}

				var parameterArray = search.split('&').filter(item => {
					return item.trim() !== '';
				});

				var newParameters = FacetUtil.setURLParameters(
					key,
					selections,
					parameterArray
				);

				search = newParameters.join('&');

				if (hasQuestionMark) {
					search = '?' + search;
				}

				return search;
			}
		};

		Liferay.namespace('Search').FacetUtil = FacetUtil;
	},
	'',
	{
		requires: []
	}
);
