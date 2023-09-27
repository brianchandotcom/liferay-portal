/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
