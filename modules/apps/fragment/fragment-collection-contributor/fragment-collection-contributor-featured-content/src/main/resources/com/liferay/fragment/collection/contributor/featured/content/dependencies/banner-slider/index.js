const INTERVAL = 5000;
const MOVE_LEFT = 'move-left';
const MOVE_RIGHT = 'move-right';

const editMode = layoutMode === 'edit';
const indicators = [].slice.call(
	fragmentElement.querySelectorAll('.carousel-navigation button')
);
const items = [].slice.call(fragmentElement.querySelectorAll('.carousel-item'));
const nextItemIndexKey = `${fragmentEntryLinkNamespace}-next-item-index`;

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
		intervalId = setInterval(function () {
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

	setNextItemIndex(index);

	if (index === null) {
		setNextItemIndex(
			indexActiveItem >= items.length - 1 ? 0 : indexActiveItem + 1
		);
	}

	const nextItem = items[getNextItemIndex()];

	activateIndicator(activeItem, nextItem, movement);

	setTimeout(function () {
		activateItem(activeItem, nextItem, movement);

		moving = false;
	}, 600);
}

function setNextItemIndex(index) {
	window[nextItemIndexKey] = index;
}

(function () {
	let intervalId = createInterval();

	if (getNextItemIndex() < items.length) {
		const activeItem = fragmentElement.querySelector(
			'.carousel-item.active'
		);
		const nextItem = items[getNextItemIndex()];

		activateIndicator(activeItem, nextItem);
		activateItem(activeItem, nextItem);
	}

	indicators.forEach(function (indicator, index) {
		indicator.addEventListener('click', function () {
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
