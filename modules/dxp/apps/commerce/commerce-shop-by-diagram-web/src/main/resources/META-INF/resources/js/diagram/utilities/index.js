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

export function calculateTooltipStyle(x, y, tooltipRef) {
	const {height, width} = tooltipRef.current.getBoundingClientRect();

	const calcX = x < window.innerWidth / 2 ? x : x - width;
	const calcY = y < window.innerHeight / 2 ? y : y - height;

	return {
		transform: `translate(${calcX}px, ${calcY}px)`,
	};
}

export function getTypeFromSelectedPin(selectedPin) {
	if (!selectedPin) {
		return null;
	}

	if (selectedPin.mappedProduct.diagram) {
		return 'diagram';
	}

	return selectedPin.mappedProduct.id ? 'sku' : 'external';
}

export function formatMappedProduct(type, quantity, sequence, selectedProduct) {
	const definition = {
		quantity,
		sequence,
	};
	switch (type) {
		case 'sku':
			return {
				...definition,
				diagram: false,
				sku: selectedProduct.sku,
				skuId: selectedProduct.id,
			};
		case 'external':
			return {
				...definition,
				diagram: false,
				sku: selectedProduct.sku,
			};
		case 'diagram':
			return {
				...definition,
				diagram: true,
				productId: selectedProduct.productId,
			};
		default:
			throw new Error(`Type ${type} not supported`);
	}
}
