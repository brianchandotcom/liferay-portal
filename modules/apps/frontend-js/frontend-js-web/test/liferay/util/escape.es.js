'use strict';

import escape from '../../../src/main/resources/META-INF/resources/liferay/util/escape.es';

describe(

	'Liferay.Util.escape',
	()	=>	{

		it(
			'should convert the character "&" to "&amp;"',
			()	=>	{
				expect(escape('fred, barney, & pebbles')).toEqual('fred, barney, &amp pebbles');
			}
		);

		it(
			'should convert the character "<" to "&lt"',
			()	=>	{
				expect(escape('9 < 20')).toEqual('9 &lt 20');
			}
		);

		it(
			'should convert the character ">" to "&gt"',
			()	=>	{
				expect(escape('20 > 9')).toEqual('20 &gt 9');
			}
		);

		it(
			'should convert the character \' " \' to "&quot"',
			()	=>	{
				expect(escape('"I go to seek a Great Perhaps." by François Rabelais.')).toEqual('&quotI go to seek a Great Perhaps.&quot by François Rabelais.');
			}
		);

		it(
			'should convert the character  to "&#39"',
			()	=>	{
				expect(escape(' \'hi fred!\' he said')).toEqual(' &#39hi fred!&#39 he said');
			}
		);

		it(
			'should return the same string because does not have any HTML Escapes ',
			()	=>	{
				expect(escape('Este string no tiene elementos HTML Escapes')).toEqual('Este string no tiene elementos HTML Escapes');
			}
		);

		it(
			'should convert the character multiple element to HTML Escapes',
			()	=>	{
				expect(escape('HTML Escapes: &, <, >, \", \'.')).toEqual('HTML Escapes: &amp, &lt, &gt, &quot, &#39.');
			}
		);
	}
);