;(function() {
	var A = AUI();

	var LString = A.Lang.String;

	var entities = A.merge(
		Liferay.Util.MAP_HTML_CHARS_ESCAPED,
		{
			'[': '&#91;',
			']': '&#93;',
			'(': '&#40;',
			')': '&#41;'
		}
	);

	var BBCodeUtil = Liferay.namespace('BBCodeUtil');

	BBCodeUtil.escape = A.rbind('escapeHTML', LString, true, entities);
	BBCodeUtil.unescape = A.rbind('unescapeHTML', LString, entities);
}());