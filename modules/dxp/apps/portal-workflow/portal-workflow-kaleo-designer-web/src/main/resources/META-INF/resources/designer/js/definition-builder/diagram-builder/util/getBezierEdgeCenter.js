/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// Ported from https://github.com/wbkd/react-flow/blob/b0bec1639ae85691492972084e6f2ae9956ae721/src/components/Edges/BezierEdge.tsx
// This file should be removed once we upgrade to the latest React Flow library version.

function calculateControlOffset(distance, curvature) {
	if (distance >= 0) {
		return 0.5 * distance;
	}
	else {
		return curvature * 25 * Math.sqrt(-distance);
	}
}

function getControlWithCurvature({c, pos, x1, x2, y1, y2}) {
	let ctX;
	let ctY;

	// eslint-disable-next-line default-case
	switch (pos) {
		case 'left':
			{
				ctX = x1 - calculateControlOffset(x1 - x2, c);
				ctY = y1;
			}
			break;
		case 'right':
			{
				ctX = x1 + calculateControlOffset(x2 - x1, c);
				ctY = y1;
			}
			break;
		case 'top':
			{
				ctX = x1;
				ctY = y1 - calculateControlOffset(y1 - y2, c);
			}
			break;
		case 'bottom':
			{
				ctX = x1;
				ctY = y1 + calculateControlOffset(y2 - y1, c);
			}
			break;
	}

	return [ctX, ctY];
}

export default function getBezierEdgeCenter({
	curvature,
	sourcePosition,
	sourceX,
	sourceY,
	targetPosition,
	targetX,
	targetY,
}) {
	const [sourceControlX, sourceControlY] = getControlWithCurvature({
		c: curvature,
		pos: sourcePosition,
		x1: sourceX,
		x2: targetX,
		y1: sourceY,
		y2: targetY,
	});
	const [targetControlX, targetControlY] = getControlWithCurvature({
		c: curvature,
		pos: targetPosition,
		x1: targetX,
		x2: sourceX,
		y1: targetY,
		y2: sourceY,
	});

	// cubic bezier t=0.5 mid point, not the actual mid point, but easy to calculate
	// https://stackoverflow.com/questions/67516101/how-to-find-distance-mid-point-of-bezier-curve

	const centerX =
		sourceX * 0.125 +
		sourceControlX * 0.375 +
		targetControlX * 0.375 +
		targetX * 0.125;
	const centerY =
		sourceY * 0.125 +
		sourceControlY * 0.375 +
		targetControlY * 0.375 +
		targetY * 0.125;
	const xOffset = Math.abs(centerX - sourceX);
	const yOffset = Math.abs(centerY - sourceY);

	return [centerX, centerY, xOffset, yOffset];
}
