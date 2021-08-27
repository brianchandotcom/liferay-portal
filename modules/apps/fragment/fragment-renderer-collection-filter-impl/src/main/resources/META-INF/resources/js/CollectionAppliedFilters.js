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

import {delegate} from 'frontend-js-web';

export default function CollectionAppliedFilters({
	filterPrefix,
	fragmentEntryLinkNamespace,
}) {
	const filterList = document.getElementById(
		`${fragmentEntryLinkNamespace}_filterList`
	);
	const removeAllFiltersButton = document.getElementById(
		`${fragmentEntryLinkNamespace}_removeAllFilters`
	);
	const toggleExpandFiltersButton = document.getElementById(
		`${fragmentEntryLinkNamespace}_toggleExpand`
	);
	const toggleExpandFiltersButtonIconCollapse = toggleExpandFiltersButton.querySelector(
		'.lexicon-icon-angle-up-small'
	);
	const toggleExpandFiltersButtonIconExpand = toggleExpandFiltersButton.querySelector(
		'.lexicon-icon-angle-down-small'
	);
	const toggleExpandFiltersButtonLabel =
		toggleExpandFiltersButton.firstElementChild;

	const handleRemoveAllFiltersClick = () => {
		const url = new URL(window.location.href);

		Array.from(url.searchParams).forEach(([key]) => {
			if (key.startsWith(filterPrefix)) {
				url.searchParams.delete(key);
			}
		});

		window.location.href = url.toString();
	};

	const handleToggleExpandFiltersButtonClick = () => {
		if (filterList.style.maxHeight) {
			filterList.style.maxHeight = '';

			toggleExpandFiltersButtonLabel.textContent =
				toggleExpandFiltersButton.dataset.showLessLabel;

			toggleExpandFiltersButtonIconCollapse.classList.remove('d-none');

			toggleExpandFiltersButtonIconExpand.classList.add('d-none');
		}
		else {
			filterList.style.maxHeight = '4em';

			toggleExpandFiltersButtonLabel.textContent =
				toggleExpandFiltersButton.dataset.showMoreLabel;

			toggleExpandFiltersButtonIconCollapse.classList.add('d-none');

			toggleExpandFiltersButtonIconExpand.classList.remove('d-none');
		}
	};

	const handleWindowResize = () => {
		const currentListHeight = filterList.getBoundingClientRect().height;

		handleToggleExpandFiltersButtonClick();

		requestAnimationFrame(() => {
			const nextListHeight = filterList.getBoundingClientRect().height;

			toggleExpandFiltersButton.classList.toggle(
				'd-none',
				currentListHeight === nextListHeight
			);

			handleToggleExpandFiltersButtonClick();
		});
	};

	const removeSingleFilterClickHandler = delegate(
		document.body,
		'click',
		`#${fragmentEntryLinkNamespace} .remove-filter-button`,
		(event) => {
			const {
				filterFragmentEntryLinkId,
				filterType,
				filterValue,
			} = event.delegateTarget.dataset;

			const paramName = `${filterPrefix}${filterType}_${filterFragmentEntryLinkId}`;
			const searchParams = [];
			const url = new URL(window.location.href);

			// Here we need to loop all parameters twice if we want to preserve
			// them sorted. First we eliminate unwanted parameters, then we
			// remove all of them and last we re-add preserved parameters
			// following same order.

			url.searchParams.forEach((value, key) => {
				if (value !== filterValue || key !== paramName) {
					searchParams.push([key, value]);
				}
			});

			url.search = '';

			searchParams.forEach(([key, value]) => {
				url.searchParams.append(key, value);
			});

			window.location.href = url.toString();
		}
	);

	toggleExpandFiltersButton.addEventListener(
		'click',
		handleToggleExpandFiltersButtonClick
	);
	removeAllFiltersButton?.addEventListener(
		'click',
		handleRemoveAllFiltersClick
	);
	window.addEventListener('resize', handleWindowResize);

	handleWindowResize();

	return {
		dispose() {
			removeAllFiltersButton?.removeEventListener(
				'click',
				handleRemoveAllFiltersClick
			);
			toggleExpandFiltersButton.removeEventListener(
				'click',
				handleToggleExpandFiltersButtonClick
			);
			window.removeEventListener('resize', handleWindowResize);

			removeSingleFilterClickHandler.dispose();
		},
	};
}
