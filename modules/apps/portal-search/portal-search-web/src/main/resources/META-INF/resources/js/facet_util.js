/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-search-facet-util',
	(A) => {
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
			return term.dataset.termId || term.id;
		}

		/**
		 * Converts a NodeList to an array of nodes. This allows array
		 * methods to be performed.
		 * @param {NodeList} nodeList
		 */
		function _transformNodeListToArray(nodeList) {
			const nodeArray = [];

			nodeList.forEach((node) => nodeArray.push(node));

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

				// Disable checkboxes across all facets to avoid multiple
				// selections. Only the most recent selection will be added
				// since the page needs to be reloaded before another selection
				// can be made.

				const allFacetTerms = document.querySelectorAll(
					`.${FACET_TERM_CLASS}`
				);

				allFacetTerms.forEach((term) => {
					Liferay.Util.toggleDisabled(term, true);
				});

				const currentSelectedTermId = _getTermId(event.currentTarget);

				const facetTerms = document.querySelectorAll(
					`#${form.id} .${FACET_TERM_CLASS}`
				);

				const selectedTerms = _transformNodeListToArray(facetTerms)
					.filter((term) => {
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
					.map((term) => _getTermId(term));

				FacetUtil.selectTerms(form, selectedTerms);
			},

			clearSelections(event) {
				const form = A.one(event.target).ancestor('form');

				if (!form) {
					return;
				}

				const selections = [];

				FacetUtil.selectTerms(form._node, selections);
			},

			enableInputs(inputs) {
				inputs.forEach((term) => {
					Liferay.Util.toggleDisabled(term, false);
				});
			},

			removeStartParameter(startParameterName, queryString) {
				let search = queryString;

				const hasQuestionMark = search[0] === '?';

				if (hasQuestionMark) {
					search = search.substr(1);
				}

				const parameterArray = search.split('&').filter((item) => {
					return item.trim() !== '';
				});

				const newParameters = FacetUtil.removeURLParameters(
					startParameterName,
					parameterArray
				);

				search = newParameters.join('&');

				if (hasQuestionMark) {
					search = '?' + search;
				}

				return search;
			},

			removeURLParameters(key, parameterArray) {
				key = encodeURIComponent(key);

				return parameterArray.filter((item) => {
					const itemSplit = item.split('=');

					return !(itemSplit && itemSplit[0] === key);
				});
			},

			selectTerms(form, selections) {
				const formParameterNameElement = document.querySelector(
					'#' + form.id + ' input.facet-parameter-name'
				);

				const startParameterNameElement = document.querySelector(
					'#' + form.id + ' input.start-parameter-name'
				);

				let search = document.location.search;

				if (startParameterNameElement) {
					search = FacetUtil.removeStartParameter(
						startParameterNameElement.value,
						search
					);
				}

				search = FacetUtil.updateQueryString(
					formParameterNameElement.value,
					selections,
					search
				);

				document.location.search = search;
			},

			setURLParameter(url, name, value) {
				const parts = url.split('?');

				const address = parts[0];

				let queryString = parts[1];

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

			setURLParameters(key, selections, parameterArray) {
				let newParameters = FacetUtil.removeURLParameters(
					key,
					parameterArray
				);

				newParameters = FacetUtil.removeURLParameters(
					key + 'From',
					newParameters
				);

				newParameters = FacetUtil.removeURLParameters(
					key + 'To',
					newParameters
				);

				selections.forEach((item) => {
					newParameters = FacetUtil.addURLParameter(
						key,
						item,
						newParameters
					);
				});

				return newParameters;
			},

			updateQueryString(key, selections, queryString) {
				let search = queryString;

				const hasQuestionMark = search[0] === '?';

				if (hasQuestionMark) {
					search = search.substr(1);
				}

				const parameterArray = search.split('&').filter((item) => {
					return item.trim() !== '';
				});

				const newParameters = FacetUtil.setURLParameters(
					key,
					selections,
					parameterArray
				);

				search = newParameters.join('&');

				if (hasQuestionMark) {
					search = '?' + search;
				}

				return search;
			},
		};

		Liferay.namespace('Search').FacetUtil = FacetUtil;
	},
	'',
	{
		requires: [],
	}
);
