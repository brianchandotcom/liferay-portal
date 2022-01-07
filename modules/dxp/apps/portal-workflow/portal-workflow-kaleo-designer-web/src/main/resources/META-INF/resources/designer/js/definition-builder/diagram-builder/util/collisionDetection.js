/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 */

import {isNode} from 'react-flow-renderer';

const getCollidingElements = (
	elements,
	elementRectangle,
	newElementPosition
) => {
	const collidingElements = [];

	elements.forEach((element) => {
		if (
			isNode(element) &&
			isOverlapping(
				element.position,
				elementRectangle,
				newElementPosition
			)
		) {
			collidingElements.push(element.id);
		}
	});

	return collidingElements;
};

const isOverlapping = (
	elementPosition,
	elementRectangle,
	newElementPosition
) => {
	if (elementRectangle !== null) {
		const existingElement = {
			bottomBound: elementPosition.y + 84,
			lefBound: elementPosition.x - 10,
			rightBound: elementPosition.x + 264,
			topBound: elementPosition.y - 10,
		};

		const {rectangleHeight, rectangleWidth} = elementRectangle;

		const isInHorizontalBounds =
			(newElementPosition.x > existingElement.lefBound ||
				newElementPosition.x + rectangleWidth >
					existingElement.lefBound) &&
			(newElementPosition.x < existingElement.rightBound ||
				newElementPosition.x + rectangleWidth <
					existingElement.rightBound);

		const isInVerticalBounds =
			(newElementPosition.y > existingElement.topBound ||
				newElementPosition.y + rectangleHeight >
					existingElement.topBound) &&
			(newElementPosition.y < existingElement.bottomBound ||
				newElementPosition.y + rectangleHeight <
					existingElement.bottomBound);

		const isOverlapping = isInHorizontalBounds && isInVerticalBounds;

		return isOverlapping;
	}

	return false;
};

export default getCollidingElements;
