export const POSITIONS = {
	BottomCenter: 4,
	BottomLeft: 5,
	BottomRight: 3,
	LeftCenter: 6,
	RightCenter: 2,
	TopCenter: 0,
	TopLeft: 7,
	TopRight: 1
};

export type Position = typeof POSITIONS[keyof typeof POSITIONS];

export function align(element, alignElement, position, autoBestAlign = true) {
	let bestRegion;

	if (autoBestAlign) {
		const suggestion = suggestAlignBestRegion(
			element,
			alignElement,
			position
		);
		position = suggestion.position;
		bestRegion = suggestion.region;
	} else {
		bestRegion = getAlignRegion(element, alignElement, position);
	}

	const computedStyle = window.getComputedStyle(element, null);

	if (computedStyle.getPropertyValue('position') !== 'fixed') {
		bestRegion.top += window.scrollY;
		bestRegion.left += window.scrollX;

		let offsetParent = element;
		while ((offsetParent = offsetParent.offsetParent)) {
			bestRegion.top -= offsetParent.offsetTop;
			bestRegion.left -= offsetParent.offsetLeft;
		}
	}

	element.style.top = `${bestRegion.top}px`;
	element.style.left = `${bestRegion.left}px`;

	return position;
}

function getAlignRegion(element, alignElement, position) {
	const r1 = alignElement.getBoundingClientRect();
	const r2 = element.getBoundingClientRect();
	let top = 0;
	let left = 0;

	switch (position) {
		case POSITIONS.TopCenter:
		default:
			top = r1.top - r2.height;
			left = r1.left + r1.width / 2 - r2.width / 2;
			break;
		case POSITIONS.RightCenter:
			top = r1.top + r1.height / 2 - r2.height / 2;
			left = r1.left + r1.width;
			break;
		case POSITIONS.BottomCenter:
			top = r1.bottom;
			left = r1.left + r1.width / 2 - r2.width / 2;
			break;
		case POSITIONS.LeftCenter:
			top = r1.top + r1.height / 2 - r2.height / 2;
			left = r1.left - r2.width;
			break;
		case POSITIONS.TopRight:
			top = r1.top - r2.height;
			left = r1.right - r2.width;
			break;
		case POSITIONS.BottomRight:
			top = r1.bottom;
			left = r1.right - r2.width;
			break;
		case POSITIONS.BottomLeft:
			top = r1.bottom;
			left = r1.left;
			break;
		case POSITIONS.TopLeft:
			top = r1.top - r2.height;
			left = r1.left;
			break;
	}

	return {
		bottom: top + r2.height,
		height: r2.height,
		left,
		right: left + r2.width,
		top,
		width: r2.width
	};
}

function suggestAlignBestRegion(element, alignElement, position) {
	let bestArea = 0;
	let bestPosition = position;
	let bestRegion = getAlignRegion(element, alignElement, bestPosition);
	let tryPosition = bestPosition;
	let tryRegion = bestRegion;

	const viewportRegion = getViewportRegion();

	for (let i = 0; i < 8; ) {
		if (intersectRegion(viewportRegion, tryRegion)) {
			const visibleRegion = intersection(viewportRegion, tryRegion);

			const area = visibleRegion.width * visibleRegion.height;

			if (area > bestArea) {
				bestArea = area;
				bestRegion = tryRegion;
				bestPosition = tryPosition;
			}

			if (insideRegion(viewportRegion, tryRegion)) {
				break;
			}
		}
		tryPosition = (position + ++i) % 8;
		tryRegion = getAlignRegion(element, alignElement, tryPosition);
	}

	return {
		position: bestPosition,
		region: bestRegion
	};
}

function getViewportRegion() {
	const height = document.documentElement.offsetHeight;
	const width = document.documentElement.offsetWidth;

	return makeRegion(height, height, 0, width, 0, width);
}

function insideRegion(r1, r2) {
	return (
		r2.top >= r1.top &&
		r2.bottom <= r1.bottom &&
		r2.right <= r1.right &&
		r2.left >= r1.left
	);
}

function intersectRect(x0, y0, x1, y1, x2, y2, x3, y3) {
	return !(x2 > x1 || x3 < x0 || y2 > y1 || y3 < y0);
}

function intersectRegion(r1, r2) {
	return intersectRect(
		r1.top,
		r1.left,
		r1.bottom,
		r1.right,
		r2.top,
		r2.left,
		r2.bottom,
		r2.right
	);
}

function intersection(r1, r2) {
	if (!intersectRegion(r1, r2)) {
		return null;
	}
	const bottom = Math.min(r1.bottom, r2.bottom);
	const right = Math.min(r1.right, r2.right);
	const left = Math.max(r1.left, r2.left);
	const top = Math.max(r1.top, r2.top);

	return makeRegion(bottom, bottom - top, left, right, top, right - left);
}

function makeRegion(bottom, height, left, right, top, width) {
	return {
		bottom,
		height,
		left,
		right,
		top,
		width
	};
}
