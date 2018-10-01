/** Used to map characters to HTML entities. */

const htmlEscapes = {
	'"': '&quot',
	'&': '&amp',
	'<': '&lt',
	'>': '&gt',
	'\'': '&#39'
};

/** Used to match HTML entities and HTML characters. */

const reUnescapedHtml = /[&<>"']/g;

const reHasUnescapedHtml = RegExp(reUnescapedHtml.source);

/**
 * Converts the characters "&", "<", ">", '"', and "'" in `string` to their
 * corresponding HTML entities.
 * @param {string} [string=''] The string to escape.
 * @returns {string} Returns the escaped string.
 *
 * @example
 *
 * escape('fred, barney, & pebbles')
 * // => 'fred, barney, &amp pebbles'
 */

export default function escape(string) {
	return (string && reHasUnescapedHtml.test(string)) ?
		string.replace(reUnescapedHtml, (chr) => htmlEscapes[chr]) :
		string;
}