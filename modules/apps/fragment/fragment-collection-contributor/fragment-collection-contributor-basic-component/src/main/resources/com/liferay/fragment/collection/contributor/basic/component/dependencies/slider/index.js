const MOVE_LEFT = 'move-left';
const MOVE_RIGHT = 'move-right';
const INTERVAL = 5000;

const editMode = document.body.classList.contains('has-edit-mode-menu');
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

function activateIndicator(activeItem, nextItem, movement) {
	if (movement) {
		activeItem.classList.add(movement);
		nextItem.classList.add(movement);
	}

	getActiveIndicator().classList.remove('active');
	indicators[this.nextItemIndex].classList.add('active');
}

function activateItem(activeItem, nextItem, movement) {
	activeItem.classList.remove('active');
	nextItem.classList.add('active');

	if (movement) {
		activeItem.classList.remove(movement);
		nextItem.classList.remove(movement);
	}
}

function move(movement, index = null) {
	if (moving) {
		return;
	}

	moving = true;

	const activeItem = fragmentElement.querySelector('.carousel-item.active');
	const indexActiveItem = items.indexOf(activeItem);

	this.nextItemIndex =
		indexActiveItem < 1 ? items.length - 1 : indexActiveItem - 1;

	if (index !== null) {
		this.nextItemIndex = index;
	}
	else if (movement === MOVE_RIGHT) {
		this.nextItemIndex =
			indexActiveItem >= items.length - 1 ? 0 : indexActiveItem + 1;
	}

	const nextItem = items[this.nextItemIndex];

	activateIndicator(activeItem, nextItem, movement);

	setTimeout(function () {
		activateItem(activeItem, nextItem, movement);

		moving = false;
	}, 600);
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

(function main() {
	let intervalId = createInterval();

	if (this.nextItemIndex && this.nextItemIndex < items.length) {
		const activeItem = fragmentElement.querySelector(
			'.carousel-item.active'
		);
		const nextItem = items[this.nextItemIndex];

		activateIndicator(activeItem, nextItem);
		activateItem(activeItem, nextItem);
	}

	prev.addEventListener('click', function () {
		clearInterval(intervalId);
		intervalId = createInterval();
		move(MOVE_LEFT);
	});

	next.addEventListener('click', function () {
		clearInterval(intervalId);
		intervalId = createInterval();
		move(MOVE_RIGHT);
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

			clearInterval(intervalId);
			intervalId = createInterval();
		});
	});
})();
