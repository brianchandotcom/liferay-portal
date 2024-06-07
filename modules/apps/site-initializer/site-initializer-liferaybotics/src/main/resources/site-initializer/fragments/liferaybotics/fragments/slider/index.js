/* eslint-disable no-undef */

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const INTERVAL = 5000;
const MOVE_LEFT = 'move-left';
const MOVE_RIGHT = 'move-right';

const editMode = layoutMode === 'edit';
const indicators = [].slice.call(
	fragmentElement.querySelectorAll('.carousel-navigation button')
);
const items = [].slice.call(fragmentElement.querySelectorAll('.carousel-item'));
const next = fragmentElement.querySelector('.carousel-control-next');
const nextItemIndexKey = `${fragmentEntryLinkNamespace}-next-item-index`;
const prev = fragmentElement.querySelector('.carousel-control-prev');

let moving = false;

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

function getActiveIndicator() {
	return fragmentElement.querySelector('.carousel-navigation .active');
}

function getNextItemIndex() {
	return window[nextItemIndexKey] || 0;
}

function move(movement, index = null) {
	if (moving) {
		return;
	}

	moving = true;

	const activeItem = fragmentElement.querySelector('.carousel-item.active');
	const indexActiveItem = items.indexOf(activeItem);
	const activeIndicator = getActiveIndicator();

	setNextItemIndex(
		indexActiveItem < 1 ? items.length - 1 : indexActiveItem - 1
	);

	if (index !== null) {
		setNextItemIndex(index);
	}
	else if (movement === MOVE_RIGHT) {
		setNextItemIndex(indexActiveItem >= 2 ? 0 : indexActiveItem + 1);
	}

	const nextItem = items[getNextItemIndex()];

	activeItem.classList.add(movement);
	nextItem.classList.add(movement);
	activeIndicator.classList.remove('active');
	indicators[getNextItemIndex()].classList.add('active');

	setTimeout(() => {
		activeItem.classList.remove('active', movement);
		nextItem.classList.add('active');
		nextItem.classList.remove(movement);

		moving = false;
	}, 600);
}

function setNextItemIndex(index) {
	window[nextItemIndexKey] = index;
}

(function () {
	let intervalId = createInterval();

	prev.addEventListener('click', () => {
		clearInterval(intervalId);
		intervalId = createInterval();
		move(MOVE_LEFT);
	});

	next.addEventListener('click', () => {
		clearInterval(intervalId);
		intervalId = createInterval();
		move(MOVE_RIGHT);
	});

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
