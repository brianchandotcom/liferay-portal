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

import {DRAG_AND_DROP_THRESHOLD} from './constants';

export function calculateTooltipStyleFromTarget(target, containerRef) {
	const {
		height: targetHeight,
		left: targetLeft,
		top: targetTop,
		width: targetWidth,
	} = target.getBoundingClientRect();

	const targetRight = window.innerWidth - targetLeft - targetWidth;
	const targetBottom = window.innerHeight - targetTop - targetHeight;

	const {
		height: containerHeight,
		left: containerLeft,
		top: containerTop,
		width: containerWidth,
	} = containerRef.current.getBoundingClientRect();

	const containerRight = window.innerWidth - containerLeft - containerWidth;
	const containerBottom = window.innerHeight - containerTop - containerHeight;

	const style = {};

	if (targetLeft + targetWidth / 2 < window.innerWidth / 2) {
		style.left = targetLeft - containerLeft + targetWidth;
	}
	else {
		style.right = targetRight - containerRight + targetWidth;
	}

	if (targetTop + targetHeight / 2 < window.innerHeight / 2) {
		style.top = targetTop - containerTop + targetHeight;
	}
	else {
		style.bottom = targetBottom - containerBottom + targetHeight;
	}

	return style;
}

export function formatMappedProduct(type, quantity, sequence, selectedProduct) {
	const definition = {
		quantity,
		sequence,
		type,
	};

	switch (type) {
		case 'sku':
			return {
				...definition,
				sku: selectedProduct.sku,
				skuId: selectedProduct.id,
			};
		case 'external':
			return {
				...definition,
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

export function getAbsolutePositions(x, y, wrapper) {
	const {height, width} = wrapper.getBoundingClientRect();

	return [(x / 100) * width, (y / 100) * height];
}

export function getPercentagePositions(x, y, wrapper) {
	const {
		height: wrapperHeight,
		width: wrapperWidth,
		x: wrapperX,
		y: wrapperY,
	} = wrapper.getBoundingClientRect();

	const percentagePositions = [
		((x - wrapperX) / wrapperWidth) * 100,
		((y - wrapperY) / wrapperHeight) * 100,
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
