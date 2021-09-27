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

export function calculateTooltipStyle(source, containerRef) {
	const {
		height: sourceHeight,
		left: sourceLeft,
		top: sourceTop,
		width: sourceWidth,
	} = source.getBoundingClientRect();

	const sourceRight = window.innerWidth - sourceLeft - sourceWidth;
	const sourceBottom = window.innerHeight - sourceTop - sourceHeight;

	const {
		height: containerHeight,
		left: containerLeft,
		top: containerTop,
		width: containerWidth,
	} = containerRef.current.getBoundingClientRect();

	const containerRight = window.innerWidth - containerLeft - containerWidth;
	const containerBottom = window.innerHeight - containerTop - containerHeight;

	const style = {};

	if (sourceLeft + sourceWidth / 2 < window.innerWidth / 2) {
		style.left = sourceLeft - containerLeft + sourceWidth;
	}
	else {
		style.right = sourceRight - containerRight + sourceWidth;
	}

	if (sourceTop + sourceHeight / 2 < window.innerHeight / 2) {
		style.top = sourceTop - containerTop + sourceHeight;
	}
	else {
		style.bottom = sourceBottom - containerBottom + sourceHeight;
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
