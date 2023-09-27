/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {repeatSymbol, uncamelize} from '../util/utils';
import {
	BUFFER_ATTR,
	BUFFER_CLOSE_NODE,
	BUFFER_OPEN_NODE,
	STR_BLANK,
	STR_CDATA_CLOSE,
	STR_CDATA_OPEN,
	STR_CHAR_CRLF,
	STR_CHAR_CR_LF_CRLF,
	STR_CHAR_TAB,
	STR_DASH,
	STR_METADATA,
	STR_SPACE,
} from './constants';

const XMLUtil = {
	REGEX_TOKEN_1: /.+<\/\w[^>]*>$/,
	REGEX_TOKEN_2: /^<\/\w/,
	REGEX_TOKEN_3: /^<\w[^>]*[^/]>.*$/,

	create(name, content, attrs) {
		const instance = this;

		const node = instance.createObj(name, attrs);

		return node.open + (content ? content : STR_BLANK) + node.close;
	},

	createObj(name, attrs) {
		let attrBuffer = [STR_SPACE];
		const normalizedName = uncamelize(name, STR_DASH);

		if (attrs !== undefined && attrs.xmlns) {
			attrBuffer = [STR_CHAR_CRLF];
		}

		if (attrs) {
			Object.entries(attrs).map(([key, value]) => {
				if (key) {
					BUFFER_ATTR[0] = key;
					BUFFER_ATTR[2] = value;

					if (attrs.xmlns) {
						attrBuffer.push(STR_CHAR_TAB);
					}

					attrBuffer.push(BUFFER_ATTR.join(STR_BLANK).trim());

					if (attrs.xmlns) {
						attrBuffer.push(STR_CHAR_CRLF);
					}
				}
			});
		}

		const attributes = attrBuffer.join(STR_BLANK).trimRight();

		BUFFER_CLOSE_NODE[1] = normalizedName;

		BUFFER_OPEN_NODE[1] = normalizedName;
		BUFFER_OPEN_NODE[2] = attributes;

		if (attrs !== undefined && attrs.xmlns) {
			BUFFER_OPEN_NODE[3] = STR_CHAR_CRLF + '>';
		}
		else {
			BUFFER_OPEN_NODE[3] = '>';
		}

		return {
			close: BUFFER_CLOSE_NODE.join(STR_BLANK),
			open: BUFFER_OPEN_NODE.join(STR_BLANK),
		};
	},

	format(lines) {
		const instance = this;

		let formatted = STR_BLANK;
		let inCDATA = false;
		let pad = 0;

		lines.forEach((item) => {
			let indent = 0;

			if (!inCDATA) {
				if (item.match(instance.REGEX_TOKEN_1)) {
					indent = 0;
				}
				else if (item.match(instance.REGEX_TOKEN_2)) {
					if (pad !== 0) {
						pad -= 1;
					}
				}
				else if (item.match(instance.REGEX_TOKEN_3)) {
					indent = 1;
				}

				formatted += repeatSymbol(STR_CHAR_TAB, pad);
			}

			if (item.indexOf(STR_METADATA) > -1) {
				const metadata = item.split(STR_CHAR_CR_LF_CRLF);
				item = '';
				for (let i = 0; i < metadata.length; i++) {
					if (i === 0 || i === 2) {
						pad += 1;
					}

					if (
						i === metadata.length - 2 ||
						i === metadata.length - 1
					) {
						pad -= 1;
					}

					item +=
						repeatSymbol(STR_CHAR_TAB, pad) +
						metadata[i] +
						STR_CHAR_CRLF;
				}
			}
			else if (item.indexOf(STR_CDATA_OPEN) > -1) {
				let cdata = item.split(STR_CDATA_OPEN);

				item = repeatSymbol(STR_CHAR_TAB, pad) + cdata[0];

				cdata = cdata[1].split(STR_CDATA_CLOSE);

				item +=
					repeatSymbol(STR_CHAR_TAB, pad + 1) +
					STR_CDATA_OPEN +
					cdata[0] +
					STR_CDATA_CLOSE +
					STR_CHAR_CRLF +
					repeatSymbol(STR_CHAR_TAB, pad) +
					cdata[1].trim();
			}

			formatted += item.trim() + STR_CHAR_CRLF;

			if (item.indexOf(STR_CDATA_CLOSE) > -1) {
				inCDATA = false;
			}
			else if (item.indexOf(STR_CDATA_OPEN) > -1) {
				inCDATA = true;
			}

			pad += indent;
		});

		return formatted.trim();
	},

	validateDefinition(definition) {
		const parser = new DOMParser();

		const xmlDoc = parser.parseFromString(definition, 'text/xml');

		let valid = true;

		if (
			xmlDoc === null ||
			xmlDoc.documentElement === null ||
			xmlDoc.querySelector('parsererror')
		) {
			valid = false;
		}

		return valid;
	},
};

export default XMLUtil;
