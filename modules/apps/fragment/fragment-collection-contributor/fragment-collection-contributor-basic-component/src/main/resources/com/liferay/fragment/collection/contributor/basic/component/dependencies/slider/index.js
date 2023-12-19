const INTERVAL = 5000;
const MOVE_LEFT = 'move-left';
const MOVE_RIGHT = 'move-right';

const editMode = layoutMode === 'edit';
const indicators = [].slice.call(
	fragmentElement.querySelectorAll('.carousel-item-button')
);
const items = [].slice.call(fragmentElement.querySelectorAll('.carousel-item'));
const next = fragmentElement.querySelector('.carousel-control-next');
const nextItemIndexKey = `${fragmentEntryLinkNamespace}-next-item-index`;
const prev = fragmentElement.querySelector('.carousel-control-prev');
const toggleButton = fragmentElement.querySelector('.carousel-toggle-button');
const toggleButtonIconStart = fragmentElement.querySelector(
	'.carousel-toggle-icon-start'
);
const toggleButtonIconStop = fragmentElement.querySelector(
	'.carousel-toggle-icon-stop'
);
const toggleButtonText = fragmentElement.querySelector('.carousel-toggle-text');

let intervalId = null;
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

function getActiveIndicator() {
	return fragmentElement.querySelector('.carousel-item-button.active');
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

	setTimeout(function () {
		activateItem(activeItem, nextItem, movement);

		moving = false;
	}, 600);
}

function startCarousel() {
	if (intervalId) {
		clearInterval(intervalId);
	}

	intervalId = setInterval(function () {
		if (document.contains(items[0])) {
			move(MOVE_RIGHT);
		}
		else {
			stopCarousel();
		}
	}, INTERVAL);

	toggleButton.classList.add('playing');
	toggleButton.classList.remove('stopped');
	toggleButtonIconStart.classList.add('d-none');
	toggleButtonIconStop.classList.remove('d-none');

	toggleButtonText.textContent = 'Stop slide rotation';
}

function stopCarousel() {
	if (intervalId) {
		clearInterval(intervalId);

		intervalId = null;

		toggleButton.classList.remove('stopped');
		toggleButton.classList.remove('playing');
		toggleButton.classList.add('stopped');
		toggleButtonIconStart.classList.remove('d-none');
		toggleButtonIconStop.classList.add('d-none');

		toggleButtonText.textContent = 'Start slide rotation';
	}
}

function setNextItemIndex(index) {
	window[nextItemIndexKey] = index;
}

(function () {
	if (!editMode) {
		startCarousel();
	}

	if (getNextItemIndex() < items.length) {
		const activeItem = fragmentElement.querySelector(
			'.carousel-item.active'
		);
		const nextItem = items[getNextItemIndex()];

		activateIndicator(activeItem, nextItem);
		activateItem(activeItem, nextItem);
	}

	prev.addEventListener('click', function () {
		move(MOVE_LEFT);
	});

	next.addEventListener('click', () => {
		move(MOVE_RIGHT);
	});

	toggleButton.addEventListener('click', () => {
		if (toggleButton.classList.contains('playing')) {
			stopCarousel();
		}
		else {
			startCarousel();
		}
	});

	indicators.forEach(function (indicator, index) {
		indicator.addEventListener('click', function () {
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
		});
	});
})();
