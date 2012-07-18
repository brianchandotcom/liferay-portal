;(function() {
	var LiferayUtil = Liferay.Util;

	var entities = function() {
		var escapedChars = LiferayUtil.MAP_HTML_CHARS_ESCAPED;

		escapedChars['['] = '&#91;';
		escapedChars[']'] = '&#93;';
		escapedChars['('] = '&#40;';
		escapedChars[')'] = '&#41;';

		return escapedChars;
	}();

	var BBCodeUtil = {
		escape: function(data) {
			return LiferayUtil.escapeHTML(data, true, entities);
		},

		unescape: function(data) {
			return LiferayUtil.unescapeHTML(data, entities);
		}
	};

	Liferay.BBCodeUtil = BBCodeUtil;
}());