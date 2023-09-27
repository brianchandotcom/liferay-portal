/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

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

	it('preserves original formatting inside CDATA blocks', () => {
		const options = {newLine: '\n', tagIndent: '  '};

		const input =
			` <?xml xlmns:a="http://www.w3.org/TR/html4/" version="1.0" encoding="UTF-8"?>
			<!DOCTYPE note>
			
			<a:note>  					<a:to>Foo</a:to>
				<a:from>Bar</a:from><a:heading>FooBar</a:heading>
								<a:body>FooBarBaz!</a:body>
			</a:note>\n` +
			'<script><![CDATA[<message> FooBarBaz </message> ]]></script>\n' +
			'<script><![CDATA[\n' +
			'    <#-- FooBarBaz -->\n' +
			'    <#if foo && bar>\n' +
			'      <#assign foo = "bar">\n' +
			'    </#if>\n' +
			'    <#if bar>\n' +
			'            <#if baz><#assign bar = "baz">\n' +
			'        </#if></#if>\n' +
			'      ]]>\n' +
			'        </script>';

		const expectedOutput =
			'<?xml xlmns:a="http://www.w3.org/TR/html4/" version="1.0" encoding="UTF-8"?>\n' +
			'<!DOCTYPE note>\n' +
			'<a:note>\n' +
			'  <a:to>Foo</a:to>\n' +
			'  <a:from>Bar</a:from>\n' +
			'  <a:heading>FooBar</a:heading>\n' +
			'  <a:body>FooBarBaz!</a:body>\n' +
			'</a:note>\n' +
			'<script>\n' +
			'  <![CDATA[<message> FooBarBaz </message> ]]>\n' +
			'</script>\n' +
			'<script>\n' +
			'  <![CDATA[\n' +
			'    <#-- FooBarBaz -->\n' +
			'    <#if foo && bar>\n' +
			'      <#assign foo = "bar">\n' +
			'    </#if>\n' +
			'    <#if bar>\n' +
			'            <#if baz><#assign bar = "baz">\n' +
			'        </#if></#if>\n' +
			'      ]]>\n' +
			'</script>';

		expect(formatXML(input, options)).toEqual(expectedOutput);
	});
});
