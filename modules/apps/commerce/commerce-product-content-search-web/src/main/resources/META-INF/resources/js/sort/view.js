/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createPortletURL, navigate} from 'frontend-js-web';

function changeOrderBy(currentURL, portletDisplayId, label) {
	const newUrl = createPortletURL(currentURL, {
		orderByCol: label,
		p_p_id: portletDisplayId,
	});

	navigate(newUrl.toString());
}

export default function ({currentURL, namespace, portletDisplayId}) {
	const optionLinks = document.querySelectorAll(
		`.sortWidgetOptions[id^=${namespace}]`
	);

	const commerceSortByButton = document.getElementById('commerce-order-by');

	const dropdownSortByButton = document.getElementById(
		`${namespace}commerce-dropdown-order-by`
	);

	function handleOptionLinks(event) {
		event.preventDefault();

		changeOrderBy(
			currentURL,
			portletDisplayId,
			event.target.getAttribute('data-label')
		);
	}

	optionLinks.forEach((link) => {
		link.addEventListener('click', handleOptionLinks);
	});

	const closeDropDownOnClickOutside = (event) => {
		if (
			event.target !== commerceSortByButton &&
			dropdownSortByButton.classList.contains('show') &&
			event.target.parentNode !== dropdownSortByButton
		) {
			dropdownSortByButton.classList.remove('show');
		}
	};

	window.addEventListener('mouseup', closeDropDownOnClickOutside);

	const clickOnSortButton = () => {
		if (dropdownSortByButton) {
			dropdownSortByButton.classList.toggle('show');
		}
	};

	commerceSortByButton.addEventListener('click', clickOnSortButton);

	return {
		dispose() {
			window.removeEventListener('mouseup', closeDropDownOnClickOutside);
			commerceSortByButton.removeEventListener(
				'click',
				clickOnSortButton
			);
			optionLinks?.forEach((link) => {
				link.removeEventListener('click', handleOptionLinks);
			});
		},
	};
}
