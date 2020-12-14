/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import * as Utils from '../../../../src/main/resources/META-INF/resources/components/quantity_selector/utils';

describe('QuantitySelector Util -> generateQuantityOptions', () => {
	it('returns allowedQuantities, if non-empty, as formatted options', () => {
		const allowedQuantities = [
			2,
			4,
			42,
			65,
			33,
			913,
			267,
			323,
			122,
			90,
			113,
		];

		expect(Utils.generateQuantityOptions({allowedQuantities})).toEqual(
			allowedQuantities.map((value) => ({
				label: value.toString(),
				value,
			}))
		);
	});

	it('returns formatted options range from minQuantity to maxQuantity if allowedQuantities is empty', () => {
		expect(
			Utils.generateQuantityOptions({
				allowedQuantities: [],
				maxQuantity: 5,
				minQuantity: 2,
			})
		).toEqual(
			[2, 3, 4, 5].map((value) => ({
				label: value.toString(),
				value,
			}))
		);
	});

	it('ignores minQuantity and maxQuantity and returns allowedQuantities as formatted options if it is not empty', () => {
		const allowedQuantities = [3, 5, 7, 9];

		expect(
			Utils.generateQuantityOptions({
				allowedQuantities,
				maxQuantity: 9,
				minQuantity: 1,
				multipleQuantity: 2,
			})
		).toEqual(
			allowedQuantities.map((value) => ({
				label: value.toString(),
				value,
			}))
		);
	});

	it('returns as formatted options computed quantities as multiples if multipleQuantity is greater than 1', () => {
		expect(
			Utils.generateQuantityOptions({
				allowedQuantities: [],
				maxQuantity: 4,
				minQuantity: 1,
				multipleQuantity: 2,
			})
		).toEqual(
			[2, 4, 6, 8].map((value) => ({
				label: value.toString(),
				value,
			}))
		);
	});

	it('returns only one option if allowedQuantities, maxQuantity, minQuantity and multipleQuantity are not passed in', () => {
		expect(Utils.generateQuantityOptions({})).toEqual(
			[1].map((value) => ({
				label: value.toString(),
				value,
			}))
		);
	});
});
