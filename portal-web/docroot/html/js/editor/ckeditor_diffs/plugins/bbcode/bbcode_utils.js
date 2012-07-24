;(function() {
	var A = AUI();

	var LiferayUtil = Liferay.Util;

	var entities = A.merge(
		LiferayUtil.MAP_HTML_CHARS_ESCAPED,
		{
			'[': '&#91;',
			']': '&#93;',
			'(': '&#40;',
			')': '&#41;'
		}
	);

	var BBCodeUtil = Liferay.namespace('BBCodeUtil');

	BBCodeUtil.escape = A.rbind('escapeHTML', LiferayUtil, true, entities);
	BBCodeUtil.unescape = A.rbind('unescapeHTML', LiferayUtil, entities);
}());