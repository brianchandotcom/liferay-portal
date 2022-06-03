/* eslint-disable no-undef */
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

const MOVE_LEFT = 'move-left';
const MOVE_RIGHT = 'move-right';
const INTERVAL = 5000;

const editMode = layoutMode === 'edit';
const indicators = [].slice.call(
	fragmentElement.querySelectorAll('.carousel-navigation button')
);
const items = [].slice.call(fragmentElement.querySelectorAll('.carousel-item'));

const next = fragmentElement.querySelector('.carousel-control-next');
const prev = fragmentElement.querySelector('.carousel-control-prev');

let moving = false;

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
	const activeIndicator = getActiveIndicator();

	let nextItemIndex =
		indexActiveItem < 1 ? items.length - 1 : indexActiveItem - 1;

	if (index !== null) {
		nextItemIndex = index;
	}
	else if (movement === MOVE_RIGHT) {
		nextItemIndex = indexActiveItem >= 2 ? 0 : indexActiveItem + 1;
	}

	const nextItem = items[nextItemIndex];

	activeItem.classList.add(movement);
	nextItem.classList.add(movement);
	activeIndicator.classList.remove('active');
	indicators[nextItemIndex].classList.add('active');

	setTimeout(() => {
		activeItem.classList.remove('active', movement);
		nextItem.classList.add('active');
		nextItem.classList.remove(movement);

		moving = false;
	}, 600);
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

(function main() {
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
			const indexActiveIndicator = indicators.indexOf(
				getActiveIndicator()
			);

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
