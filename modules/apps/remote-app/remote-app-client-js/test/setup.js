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

/**
 * Linear Congruential Generator to make pseudorandom nunmbers.
 *
 * @see https://en.wikipedia.org/wiki/Linear_congruential_generator
 */

const MODULUS = 2 ** 32;

const MULTIPLIER = 1664525;

const INCREMENT = 1013904223;

let seed = 1;

function getRandom() {
	seed = (MULTIPLIER * seed + INCREMENT) % MODULUS;

	return seed;
}

window.crypto = {
	set __SEED__(newSeed) {
		seed = newSeed;
	},

	getRandomValues(array) {
		if (array instanceof Uint8Array) {
			for (let i = 0; i < array.length; i++) {
				array[i] = getRandom() & 0xff;
			}

			return array;
		}
		else {
			throw new Error(
				'crypto.getRandomValues() mock only supports Uint8Array'
			);
		}
	},
};
