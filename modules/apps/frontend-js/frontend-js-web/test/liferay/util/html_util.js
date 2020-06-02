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

import {
	MAP_HTML_CHARS_ESCAPED,
	escapeHTML,
	unescapeHTML,
} from '../../../src/main/resources/META-INF/resources/liferay/util/html_util';

describe('Liferay.Util.escapeHTML and Liferay.Util.unescapeHTML', () => {
	describe('Liferay.Util.escapeHTML', () => {
		it('escapes HTML entities', () => {
			const escapedEntities = Object.keys(MAP_HTML_CHARS_ESCAPED);

			escapedEntities.forEach((entity) => {
				expect(escapeHTML(entity)).toEqual(
					MAP_HTML_CHARS_ESCAPED[entity]
				);
			});
		});
	});

	describe('Liferay.Util.unescapeHTML', () => {
		it('unescapes HTML entities', () => {
			const entities = Object.values(MAP_HTML_CHARS_ESCAPED);

			const escapedEntities = Object.keys(MAP_HTML_CHARS_ESCAPED);

			entities.forEach((entity, index) => {
				expect(unescapeHTML(entity)).toEqual(escapedEntities[index]);
			});
		});
	});

	describe('escapeHTML and unescapeHTML used together', () => {
		it('unescaping an escaped string', () => {
			const string = "'hello' & <p>goodbye</p>";
			const escaped = escapeHTML(string);
			const unescaped = unescapeHTML(escaped);

			expect(unescaped).toEqual(string);
		});
	});

	describe('when using entities non entities', () => {
		it("doesn't throw errors", () => {
			expect(() =>
				unescapeHTML('my test !"§$%&/()=?`@€<>|,.-;:_+*#~')
			).not.toThrow();
			expect(() =>
				unescapeHTML('my test !"§$%&/()=?`@€<sth here>|,.-;:_+*#~')
			).not.toThrow();
		});
	});
});
