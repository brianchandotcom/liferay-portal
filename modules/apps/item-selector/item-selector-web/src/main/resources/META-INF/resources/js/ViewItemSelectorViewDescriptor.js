/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {delegate, getOpener} from 'frontend-js-web';

export default function ({
	itemSelectorReturnType,
	itemSelectorSelectedEvent,
	namespace,
}) {
	const selectItem = (event) => {
		const activeCards = document.querySelectorAll(
			'.form-check-card.active'
		);

		if (activeCards.length) {
			activeCards.forEach((card) => {
				card.classList.remove('active');
			});
		}

		const target = event.delegateTarget;

		const newSelectedCard = target.closest('.form-check-card');

		if (newSelectedCard) {
			newSelectedCard.classList.add('active');
		}

		let domElement = target.closest('li');

		if (domElement === null) {
			domElement = target.closest('tr');
		}

		if (domElement === null) {
			domElement = target.closest('dd');
		}

		let itemValue = '';

		if (domElement !== null) {
			itemValue = domElement.dataset.value;
		}

		const openerWindow = getOpener();

		openerWindow.Liferay.fire(itemSelectorSelectedEvent, {
			data: {
				returnType: itemSelectorReturnType,
				value: itemValue,
			},
		});
	};

	const onClickHandler = delegate(
		document.getElementById(`${namespace}entriesContainer`),
		'click',
		'.entry',
		(event) => {
			selectItem(event);
		}
	);

	const onKeydownHandler = delegate(
		document.getElementById(`${namespace}entriesContainer`),
		'keydown',
		'.entry',
		(event) => {
			if (event.code === 'Enter') {
				selectItem(event);
			}
		}
	);

	return {
		dispose() {
			onClickHandler.dispose();
			onKeydownHandler.dispose();
		},
	};
}
