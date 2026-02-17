/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Trims leading and trailing whitespace from the `input` string and
 * reduces the indent level back to column 0.
 */
function dedent(input, tabWidth = 4) {

	// Collapse totally blank lines to empty strings.

	const lines = input.split('\n').map((line) => {
		if (line.match(/^\s+$/)) {
			return '';
		}
		else {
			return line;
		}
	});

	// Find minimum indent (ignoring empty lines).

	const minimum = lines.reduce((acc, line) => {
		const [, indent, rest] = line.match(/^(\s*)(.*)/);

		if (indent) {
			const length = Array.from(indent).reduce((count, char) => {
				return count + (char === '\t' ? tabWidth : 1);
			}, 0);

			return Math.min(acc, length);
		}
		else if (rest) {
			return Math.min(acc, 0);
		}

		return acc;
	}, Infinity);

	// Strip out minimum indent from every line.

	const dedented = isFinite(minimum)
		? lines.map((line) => {
				const [, indent, rest] = line.match(/^(\s*)(.*)/);

				if (minimum && indent.length) {

					// Remove leftmost character until hitting desired length;
					// if you overshoot (due to a tab), pad with spaces.

					const chars = Array.from(indent);

					let currentLength = chars.reduce((count, char) => {
						return count + (char === '\t' ? tabWidth : 1);
					}, 0);

					const desiredLength = currentLength - minimum;

					while (currentLength > desiredLength) {
						const char = chars.shift();

						currentLength -= char === '\t' ? tabWidth : 1;
					}

					if (currentLength < desiredLength) {
						return (
							chars.join('') +
							' '.repeat(desiredLength - currentLength) +
							rest
						);
					}

					return chars.join('') + rest;
				}
				else {
					return line;
				}
			})
		: lines;

	// Trim first and last line if empty.

	if (dedented[0] === '') {
		dedented.shift();
	}

	if (dedented[dedented.length - 1] === '') {
		dedented.pop();
	}

	return dedented.join('\n');
}

export default dedent;
