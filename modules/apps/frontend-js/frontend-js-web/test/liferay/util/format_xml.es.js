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

'use strict';

import formatXML from '../../../src/main/resources/META-INF/resources/liferay/util/format_xml.es';

describe('Liferay.Util.formatXML', () => {
	it('throws error if content parameter is not a string', () => {
		expect(() => formatXML({})).toThrow('must be a string');
	});

	it('returns an XML string if content parameter is an XML string and options parameter is not provided', () => {
		const input = `<?xml xlmns:a="http://www.w3.org/TR/html4/" version="1.0" encoding="UTF-8"?>
			<!DOCTYPE note>

			<a:note>  					<a:to>Foo</a:to>
				<a:from>Bar</a:from><a:heading>FooBar</a:heading>
								<a:body>FooBarBaz!</a:body>
			</a:note>`;

		const expectedOutput =
			'<?xml xlmns:a="http://www.w3.org/TR/html4/" version="1.0" encoding="UTF-8"?>\r\n' +
			'<!DOCTYPE note>\r\n' +
			'<a:note>\r\n' +
			'\t<a:to>Foo</a:to>\r\n' +
			'\t<a:from>Bar</a:from>\r\n' +
			'\t<a:heading>FooBar</a:heading>\r\n' +
			'\t<a:body>FooBarBaz!</a:body>\r\n' +
			'</a:note>';

		expect(formatXML(input)).toEqual(expectedOutput);
	});

	it('returns an XML string if content parameter is an XML string and options parameter is provided with custom values', () => {
		const options = {newLine: '\n', tagIndent: '  '};

		const input = ` <?xml xlmns:a="http://www.w3.org/TR/html4/" version="1.0" encoding="UTF-8"?>
			<!DOCTYPE note>

			<a:note>  					<a:to>Foo</a:to>
				<a:from>Bar</a:from><a:heading>FooBar</a:heading>
								<a:body>FooBarBaz!</a:body>
			</a:note>
			`;

		const expectedOutput =
			'<?xml xlmns:a="http://www.w3.org/TR/html4/" version="1.0" encoding="UTF-8"?>\n' +
			'<!DOCTYPE note>\n' +
			'<a:note>\n' +
			'  <a:to>Foo</a:to>\n' +
			'  <a:from>Bar</a:from>\n' +
			'  <a:heading>FooBar</a:heading>\n' +
			'  <a:body>FooBarBaz!</a:body>\n' +
			'</a:note>';

		expect(formatXML(input, options)).toEqual(expectedOutput);
	});
});
