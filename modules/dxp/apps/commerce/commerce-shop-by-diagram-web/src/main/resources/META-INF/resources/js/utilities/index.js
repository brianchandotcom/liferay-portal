/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {DIAGRAM_LABELS_MAX_LENGTH, DRAG_AND_DROP_THRESHOLD} from './constants';

export function calculateTooltipStyleFromTarget(target) {
	const {
		height: targetHeight,
		left: targetLeft,
		top: targetTop,
		width: targetWidth,
	} = target.getBoundingClientRect();

	const distanceFromTop = window.pageYOffset + targetTop;
	const targetRight = window.innerWidth - targetLeft - targetWidth;
	const style = {};

	if (targetTop + targetHeight / 2 < window.innerHeight / 2) {
		style.top = distanceFromTop + targetHeight;
	}
	else {
		style.top = distanceFromTop + targetHeight - 150;
	}

	if (targetLeft + targetWidth / 2 < window.innerWidth / 2) {
		style.left = targetLeft + targetWidth + 10;
	}
	else {
		style.right = targetRight + targetWidth + 10;
	}

	return style;
}

export function formatMappedProduct(type, quantity, sequence, selectedProduct) {
	const definition = {
		sequence,
		type,
	};

	switch (type) {
		case 'sku':
			return {
				...definition,
				productId: selectedProduct.productId,
				quantity,
				sku: selectedProduct.sku,
				skuId: selectedProduct.id,
			};
		case 'external':
			return {
				...definition,
				quantity,
				sku: selectedProduct.sku,
			};
		case 'diagram':
			return {
				...definition,
				productId: selectedProduct.productId,
			};
		default:
			throw new Error(`Type ${type} not supported`);
	}
}

export function getAbsolutePositions(x, y, image, scale) {
	const {height, width} = image.getBoundingClientRect();

	return [(x / 100) * (width / scale), (y / 100) * (height / scale)];
}

export function getPercentagePositions(x, y, image) {
	const {
		height: imageHeight,
		width: imageWidth,
		x: imageX,
		y: imageY,
	} = image.getBoundingClientRect();

	const percentagePositions = [
		((x - imageX) / imageWidth) * 100,
		((y - imageY) / imageHeight) * 100,
	];

	return percentagePositions;
}

export function isPinMoving(startX, startY, currentX, currentY) {
	if (
		Math.abs(startX - currentX) > DRAG_AND_DROP_THRESHOLD ||
		Math.abs(startY - currentY) > DRAG_AND_DROP_THRESHOLD
	) {
		return true;
	}

	return false;
}

export function formatLabel(label) {
	if (label.length > DIAGRAM_LABELS_MAX_LENGTH) {
		return label.slice(0, 6) + '…';
	}

	return label;
}
