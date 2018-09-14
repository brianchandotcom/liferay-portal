'use strict';

import unescape from '../../../src/main/resources/META-INF/resources/liferay/util/unescape.es';

describe(
	'Liferay.Util.unescape',
	() => {

		it(
			'should convert the character "&amp" to "&" ',
			()	=>	{
				expect(unescape('fred, barney, &amp; pebbles')).toEqual('fred, barney, & pebbles');
			}
		);

		it(
			'should convert the character "<" to "&lt"',
			()	=>	{
				expect(unescape('9 &lt; 20')).toEqual('9 < 20');
			}
		);

		it(
			'should convert the character ">" to "&gt"',
			()	=>	{
				expect(unescape('20 &gt; 9')).toEqual('20 > 9');
			}
		);

		it(
			'should convert the character \'"\' to "&quot"',
			()	=>	{
				expect(unescape('&quot;I go to seek a Great Perhaps.&quot; by François Rabelais.')).toEqual('"I go to seek a Great Perhaps." by François Rabelais.');
			}
		);

		it(
			'should convert the character "\\" to "&#39"',
			()	=>	{
				expect(unescape('&#39;hi fred!&#39; he said')).toEqual('\'hi fred!\' he said');
			}
		);

		it(
			'should return the same string because does not have any HTML Escapes ',
			()	=>	{
				expect(unescape('Este string no tiene elementos HTML Escapes')).toEqual('Este string no tiene elementos HTML Escapes');
			}
		);

		it(
			'should convert multiples characters',
			()	=>	{
				expect(unescape('HTML Escapes: &amp;, &lt;, &gt;, &quot;, &#39;.')).toEqual('HTML Escapes: &, <, >, \", \'.');
			}
		);
	}
);