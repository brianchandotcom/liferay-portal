/* eslint-disable no-undef */

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const INTERVAL = 5000;
const MOVE_LEFT = 'move-left';
const MOVE_RIGHT = 'move-right';

const defaultColor = configuration.textColor;
const editMode = layoutMode === 'edit';
const indicators = [].slice.call(
	fragmentElement.querySelectorAll('.carousel-navigation button')
);
const items = [].slice.call(fragmentElement.querySelectorAll('.carousel-item'));
const nextItemIndexKey = `${fragmentEntryLinkNamespace}-next-item-index`;
const stepsNumber = fragmentElement.querySelectorAll('h1');
const stepsTitle = fragmentElement.querySelectorAll('.h5');

let moving = false;

function activateIndicator(activeItem, nextItem, movement) {
	if (movement) {
		activeItem.classList.add(movement);
		nextItem.classList.add(movement);
	}

	getActiveIndicator().classList.remove('active');
	indicators[getNextItemIndex()].classList.add('active');
}

function activateItem(activeItem, nextItem, movement) {
	activeItem.classList.remove('active');
	nextItem.classList.add('active');

	if (movement) {
		activeItem.classList.remove(movement);
		nextItem.classList.remove(movement);
	}
}

function createInterval() {
	let intervalId = null;

	if (!editMode) {
		intervalId = setInterval(() => {
			if (document.contains(items[0])) {
				move(MOVE_RIGHT);
			}
			else {
				clearInterval(intervalId);
			}
		}, INTERVAL);
	}

	return intervalId;
}

function getNextItemIndex() {
	return window[nextItemIndexKey] || 0;
}

function getActiveIndicator() {
	return fragmentElement.querySelector('.carousel-navigation .active');
}

function move(movement, index = null) {
	if (moving) {
		return;
	}

	moving = true;

	const activeItem = fragmentElement.querySelector('.carousel-item.active');
	const indexActiveItem = items.indexOf(activeItem);

	setNextItemIndex(
		indexActiveItem < 1 ? items.length - 1 : indexActiveItem - 1
	);

	if (index !== null) {
		setNextItemIndex(index);
	}
	else if (movement === MOVE_RIGHT) {
		setNextItemIndex(
			indexActiveItem >= items.length - 1 ? 0 : indexActiveItem + 1
		);
	}

	const nextItem = items[getNextItemIndex()];

	activateIndicator(activeItem, nextItem, movement);

	setTimeout(() => {
		activateItem(activeItem, nextItem, movement);

		moving = false;
	}, 600);
}

function setNextItemIndex(index) {
	window[nextItemIndexKey] = index;
}

(function () {
	stepsNumber.forEach((stepNumber) => {
		stepNumber.style.color = defaultColor;
	});

	stepsTitle.forEach((stepTitle) => {
		stepTitle.style.color = defaultColor;
	});

	let intervalId = createInterval();

	if (getNextItemIndex() < items.length) {
		const activeItem = fragmentElement.querySelector(
			'.carousel-item.active'
		);
		const nextItem = items[getNextItemIndex()];

		activateIndicator(activeItem, nextItem);
		activateItem(activeItem, nextItem);
	}

	indicators.forEach((indicator, index) => {
		indicator.addEventListener('click', () => {
			const indexActiveIndicator =
				indicators.indexOf(getActiveIndicator());

			if (index !== indexActiveIndicator) {
				if (index < indexActiveIndicator) {
					move(MOVE_LEFT, index);
				}
				else {
					move(MOVE_RIGHT, index);
				}
			}

			clearInterval(intervalId);
			intervalId = createInterval();
		});
	});
})();
