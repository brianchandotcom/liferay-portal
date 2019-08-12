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

export function selectText(node) {
	if (node.select) {
		node.select();
	}
}

const KeyMap = {
	A: 65,
	ALT: 18,
	B: 66,
	BACKSPACE: 8,
	C: 67,
	CAPS_LOCK: 20,
	CONTEXT_MENU: 93,
	CTRL: 17,
	D: 68,
	DELETE: 46,
	DOWN: 40,
	E: 69,
	EIGHT: 56,
	END: 35,
	ENTER: 13,
	ESC: 27,
	F: 70,
	F1: 112,
	F2: 113,
	F3: 114,
	F4: 115,
	F5: 116,
	F6: 117,
	F7: 118,
	F8: 119,
	F9: 120,
	F10: 121,
	F11: 122,
	F12: 123,
	FIVE: 53,
	FOUR: 52,
	G: 71,
	H: 72,
	HOME: 36,
	I: 73,
	INSERT: 45,
	J: 74,
	K: 75,
	L: 76,
	LEFT: 37,
	M: 77,
	N: 78,
	NINE: 57,
	NUM_CENTER: 12,
	NUM_DIVISION: 111,
	NUM_EIGHT: 104,
	NUM_FIVE: 101,
	NUM_FOUR: 100,
	NUM_LOCK: 144,
	NUM_MINUS: 109,
	NUM_MULTIPLY: 106,
	NUM_NINE: 105,
	NUM_ONE: 97,
	NUM_PERIOD: 110,
	NUM_PLUS: 107,
	NUM_SEVEN: 103,
	NUM_SIX: 102,
	NUM_THREE: 99,
	NUM_TWO: 98,
	NUM_ZERO: 96,
	O: 79,
	ONE: 49,
	P: 80,
	PAGE_DOWN: 34,
	PAGE_UP: 33,
	PAUSE: 19,
	PRINT_SCREEN: 44,
	Q: 81,
	R: 82,
	RETURN: 13,
	RIGHT: 39,
	S: 83,
	SEVEN: 55,
	SHIFT: 16,
	SIX: 54,
	SPACE: 32,
	T: 84,
	TAB: 9,
	THREE: 51,
	TWO: 50,
	U: 85,
	UP: 38,
	V: 86,
	W: 87,
	WIN_IME: 229,
	WIN_KEY: 224,
	X: 88,
	Y: 89,
	Z: 90,
	ZERO: 48
};

const NON_MODIFYING_KEYS = [
	'ALT',
	'CAPS_LOCK',
	'CTRL',
	'DOWN',
	'END',
	'ESC',
	'F1',
	'F10',
	'F11',
	'F12',
	'F2',
	'F3',
	'F4',
	'F5',
	'F6',
	'F7',
	'F8',
	'F9',
	'HOME',
	'LEFT',
	'NUM_LOCK',
	'PAGE_DOWN',
	'PAGE_UP',
	'PAUSE',
	'PRINT_SCREEN',
	'RIGHT',
	'SHIFT',
	'SPACE',
	'UP',
	'WIN_KEY'
];

export function isModifyingKey(keyCode) {
	return !isKeyInSet(keyCode, NON_MODIFYING_KEYS);
}

export function isKeyInSet(keyCode, array) {
	let i = array.length;

	let result = false;

	let keyName;
	let key;

	while (i--) {
		keyName = array[i];
		key =
			keyName &&
			(KeyMap[keyName] || KeyMap[String(keyName).toUpperCase()]);

		if (keyCode === key) {
			result = true;

			break;
		}
	}

	return result;
}
