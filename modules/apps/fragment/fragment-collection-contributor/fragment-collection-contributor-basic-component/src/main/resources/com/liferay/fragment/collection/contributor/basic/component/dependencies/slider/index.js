/*eslint-disable*/
const MOVE_LEFT = 'move-left';
const MOVE_RIGHT = 'move-right';

const indicators = [].slice.call(
	document.querySelectorAll('.carousel-indicators li')
);
const items = [].slice.call(document.querySelectorAll('.carousel-item'));

const next = document.querySelector('.carousel-control-next');
const prev = document.querySelector('.carousel-control-prev');

const getActiveElements = function () {
	const activeIndicator = document.querySelector(
		'.carousel-indicators .active'
	);
	const activeItem = document.querySelector('.carousel-item.active');
	const indexActiveItem = items.indexOf(activeItem);

	return [activeItem, indexActiveItem, activeIndicator];
};

const move = function (movement) {
	const [activeItem, indexActiveItem, activeIndicator] = getActiveElements();
	let nextItemIndex =
		indexActiveItem < 1 ? items.length - 1 : indexActiveItem - 1;

	if (movement === MOVE_RIGHT) {
		nextItemIndex = indexActiveItem >= 2 ? 0 : indexActiveItem + 1;
	}
	const nextItem = items[nextItemIndex];

	startMove({
		activeIndicator,
		activeItem,
		movement,
		nextItem,
		nextItemIndex,
	});
	finishMove({activeItem, movement, nextItem});
};

const startMove = function ({
	activeIndicator,
	activeItem,
	movement,
	nextItem,
	nextItemIndex,
}) {
	activeItem.classList.add(movement);
	nextItem.classList.add(movement);
	activeIndicator.classList.remove('active');
	indicators[nextItemIndex].classList.add('active');
};

const finishMove = function ({activeItem, nextItem, movement}) {
	setTimeout(function () {
		activeItem.classList.remove('active', movement);
		nextItem.classList.add('active');
		nextItem.classList.remove(movement);
	}, 600);
};

function main() {
	prev.addEventListener('click', function () {
		move(MOVE_LEFT);
	});

	next.addEventListener('click', function () {
		move(MOVE_RIGHT);
	});
}

main();
