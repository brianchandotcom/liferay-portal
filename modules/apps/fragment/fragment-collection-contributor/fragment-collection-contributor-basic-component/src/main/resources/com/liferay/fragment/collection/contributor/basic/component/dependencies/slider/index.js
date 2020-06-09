const MOVE_LEFT = 'move-left';
const MOVE_RIGHT = 'move-right';
const INTERVAL = 5000;

const editMode = document.body.classList.contains('has-edit-mode-menu');
const indicators = [].slice.call(
	document.querySelectorAll('.carousel-navigation button')
);
const items = [].slice.call(document.querySelectorAll('.carousel-item'));

const next = document.querySelector('.carousel-control-next');
const prev = document.querySelector('.carousel-control-prev');

const getActiveElements = function () {
	const activeIndicator = document.querySelector(
		'.carousel-navigation .active'
	);
	const activeItem = document.querySelector('.carousel-item.active');
	const indexActiveItem = items.indexOf(activeItem);

	return [activeItem, indexActiveItem, activeIndicator];
};

const move = function (movement, index = null) {
	const [activeItem, indexActiveItem, activeIndicator] = getActiveElements();
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

	setTimeout(function () {
		activeItem.classList.remove('active', movement);
		nextItem.classList.add('active');
		nextItem.classList.remove(movement);
	}, 600);
};

const resetInterval = function (interval, clear = true) {
	if (clear) {
		clearInterval(interval);
	}
	return setInterval(function () {
		if (document.contains(next)) {
			move(MOVE_RIGHT);
		}
		else {
			clearInterval(interval);
		}
	}, INTERVAL);
};

function main() {
	let interval = null;

	if (!editMode) {
		interval = resetInterval(interval, false);
	}

	prev.addEventListener('click', function () {
		if (!editMode) {
			interval = resetInterval(interval);
		}
		move(MOVE_LEFT);
	});

	next.addEventListener('click', function () {
		if (!editMode) {
			interval = resetInterval(interval);
		}
		move(MOVE_RIGHT);
	});

	indicators.forEach(function (indicator, index) {
		indicator.addEventListener('click', function () {
			const [, , activeIndicator] = getActiveElements();
			const indexActiveIndicator = indicators.indexOf(activeIndicator);

			if (index !== indexActiveIndicator) {
				if (index < indexActiveIndicator) {
					move(MOVE_LEFT, index);
				}
				else {
					move(MOVE_RIGHT, index);
				}
			}

			if (!editMode) {
				interval = resetInterval(interval);
			}
		});
	});
}

main();
