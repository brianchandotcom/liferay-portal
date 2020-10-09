const MOVE_LEFT = 'move-left';
const MOVE_RIGHT = 'move-right';
const INTERVAL = 5000;

const editMode = document.body.classList.contains('has-edit-mode-menu');
const indicators = [].slice.call(
	fragmentElement.querySelectorAll('.carousel-navigation button')
);
const items = [].slice.call(fragmentElement.querySelectorAll('.carousel-item'));

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

	let nextItemIndex = index;

	if (index === null) {
		nextItemIndex = indexActiveItem >= 2 ? 0 : indexActiveItem + 1;
	}

	const nextItem = items[nextItemIndex];

	activeItem.classList.add(movement);
	nextItem.classList.add(movement);
	activeIndicator.classList.remove('active');
	indicators[nextItemIndex].classList.add('active');

	setTimeout(function () {
		activeItem.classList.remove('active', movement);
		nextItem.classList.add('active');
		nextItem.classList.remove(movement);

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
