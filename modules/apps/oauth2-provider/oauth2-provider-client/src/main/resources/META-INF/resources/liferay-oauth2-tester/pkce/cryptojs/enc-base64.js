/* eslint-disable */
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

(function () {

	// Shortcuts

	const C = CryptoJS;
	const C_lib = C.lib;
	const WordArray = C_lib.WordArray;
	const C_enc = C.enc;

	/**
	 * Base64 encoding strategy.
	 */
	const Base64 = (C_enc.Base64 = {

		/**
		 * Converts a word array to a Base64 string.
		 *
		 * @param {WordArray} wordArray The word array.
		 *
		 * @return {string} The Base64 string.
		 *
		 * @static
		 *
		 * @example
		 *
		 *     var base64String = CryptoJS.enc.Base64.stringify(wordArray);
		 */
		stringify(wordArray) {

			// Shortcuts

			const words = wordArray.words;
			const sigBytes = wordArray.sigBytes;
			const map = this._map;

			// Clamp excess bits

			wordArray.clamp();

			// Convert

			const base64Chars = [];
			for (let i = 0; i < sigBytes; i += 3) {
				const byte1 = (words[i >>> 2] >>> (24 - (i % 4) * 8)) & 0xff;
				const byte2 =
					(words[(i + 1) >>> 2] >>> (24 - ((i + 1) % 4) * 8)) & 0xff;
				const byte3 =
					(words[(i + 2) >>> 2] >>> (24 - ((i + 2) % 4) * 8)) & 0xff;

				const triplet = (byte1 << 16) | (byte2 << 8) | byte3;

				for (let j = 0; j < 4 && i + j * 0.75 < sigBytes; j++) {
					base64Chars.push(
						map.charAt((triplet >>> (6 * (3 - j))) & 0x3f)
					);
				}
			}

			// Add padding

			const paddingChar = map.charAt(64);
			if (paddingChar) {
				while (base64Chars.length % 4) {
					base64Chars.push(paddingChar);
				}
			}

			return base64Chars.join('');
		},

		/**
		 * Converts a Base64 string to a word array.
		 *
		 * @param {string} base64Str The Base64 string.
		 *
		 * @return {WordArray} The word array.
		 *
		 * @static
		 *
		 * @example
		 *
		 *     var wordArray = CryptoJS.enc.Base64.parse(base64String);
		 */
		parse(base64Str) {

			// Shortcuts

			let base64StrLength = base64Str.length;
			const map = this._map;

			// Ignore padding

			const paddingChar = map.charAt(64);
			if (paddingChar) {
				const paddingIndex = base64Str.indexOf(paddingChar);
				if (paddingIndex != -1) {
					base64StrLength = paddingIndex;
				}
			}

			// Convert

			const words = [];
			let nBytes = 0;
			for (let i = 0; i < base64StrLength; i++) {
				if (i % 4) {
					const bits1 =
						map.indexOf(base64Str.charAt(i - 1)) << ((i % 4) * 2);
					const bits2 =
						map.indexOf(base64Str.charAt(i)) >>> (6 - (i % 4) * 2);
					words[nBytes >>> 2] |=
						(bits1 | bits2) << (24 - (nBytes % 4) * 8);
					nBytes++;
				}
			}

			return WordArray.create(words, nBytes);
		},

		_map:
			'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=',
	});
})();
