'use strict';

define("frontend-js-metal-web@1.0.0/metal/src/html/html", ['exports', 'metal/src/core', 'metal/src/string/string'], function (exports, _core, _string) {
	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _core2 = _interopRequireDefault(_core);

	var _string2 = _interopRequireDefault(_string);

	function _interopRequireDefault(obj) {
		return obj && obj.__esModule ? obj : {
			default: obj
		};
	}

	function _classCallCheck(instance, Constructor) {
		if (!(instance instanceof Constructor)) {
			throw new TypeError("Cannot call a class as a function");
		}
	}

	var html = (function () {
		function html() {
			_classCallCheck(this, html);
		}

		html.compress = function compress(htmlString) {
			var preserved = {};
			htmlString = html.preserveBlocks_(htmlString, preserved);
			htmlString = html.simplifyDoctype_(htmlString);
			htmlString = html.removeComments_(htmlString);
			htmlString = html.removeIntertagSpaces_(htmlString);
			htmlString = html.collapseBreakingSpaces_(htmlString);
			htmlString = html.removeSpacesInsideTags_(htmlString);
			htmlString = html.removeSurroundingSpaces_(htmlString);
			htmlString = html.returnBlocks_(htmlString, preserved);
			return htmlString.trim();
		};

		html.collapseBreakingSpaces_ = function collapseBreakingSpaces_(htmlString) {
			return _string2.default.collapseBreakingSpaces(htmlString);
		};

		html.lookupPossibleTagBoundary_ = function lookupPossibleTagBoundary_(htmlString, openTag) {
			var tagPos = htmlString.indexOf(openTag);

			if (tagPos > -1) {
				tagPos += htmlString.substring(tagPos).indexOf('>') + 1;
			}

			return tagPos;
		};

		html.preserveBlocks_ = function preserveBlocks_(htmlString, preserved) {
			htmlString = html.preserveOuterHtml_(htmlString, '<!--[if', '<![endif]-->', preserved);
			htmlString = html.preserveInnerHtml_(htmlString, '<code', '</code', preserved);
			htmlString = html.preserveInnerHtml_(htmlString, '<pre', '</pre', preserved);
			htmlString = html.preserveInnerHtml_(htmlString, '<script', '</script', preserved);
			htmlString = html.preserveInnerHtml_(htmlString, '<style', '</style', preserved);
			htmlString = html.preserveInnerHtml_(htmlString, '<textarea', '</textarea', preserved);
			return htmlString;
		};

		html.preserveInnerHtml_ = function preserveInnerHtml_(htmlString, openTag, closeTag, preserved) {
			var tagPosEnd = html.lookupPossibleTagBoundary_(htmlString, openTag);

			while (tagPosEnd > -1) {
				var tagEndPos = htmlString.indexOf(closeTag);
				htmlString = html.preserveInterval_(htmlString, tagPosEnd, tagEndPos, preserved);
				htmlString = htmlString.replace(openTag, '%%%~1~%%%');
				htmlString = htmlString.replace(closeTag, '%%%~2~%%%');
				tagPosEnd = html.lookupPossibleTagBoundary_(htmlString, openTag);
			}

			htmlString = htmlString.replace(/%%%~1~%%%/g, openTag);
			htmlString = htmlString.replace(/%%%~2~%%%/g, closeTag);
			return htmlString;
		};

		html.preserveInterval_ = function preserveInterval_(htmlString, start, end, preserved) {
			var blockId = '%%%~BLOCK~' + _core2.default.getUid() + '~%%%';
			preserved[blockId] = htmlString.substring(start, end);
			return _string2.default.replaceInterval(htmlString, start, end, blockId);
		};

		html.preserveOuterHtml_ = function preserveOuterHtml_(htmlString, openTag, closeTag, preserved) {
			var tagPos = htmlString.indexOf(openTag);

			while (tagPos > -1) {
				var tagEndPos = htmlString.indexOf(closeTag) + closeTag.length;
				htmlString = html.preserveInterval_(htmlString, tagPos, tagEndPos, preserved);
				tagPos = htmlString.indexOf(openTag);
			}

			return htmlString;
		};

		html.removeComments_ = function removeComments_(htmlString) {
			var preserved = {};
			htmlString = html.preserveOuterHtml_(htmlString, '<![CDATA[', ']]>', preserved);
			htmlString = html.preserveOuterHtml_(htmlString, '<!--', '-->', preserved);
			htmlString = html.replacePreservedBlocks_(htmlString, preserved, '');
			return htmlString;
		};

		html.removeIntertagSpaces_ = function removeIntertagSpaces_(htmlString) {
			htmlString = htmlString.replace(html.Patterns.INTERTAG_CUSTOM_CUSTOM, '~%%%%%%~');
			htmlString = htmlString.replace(html.Patterns.INTERTAG_CUSTOM_TAG, '~%%%<');
			htmlString = htmlString.replace(html.Patterns.INTERTAG_TAG, '><');
			htmlString = htmlString.replace(html.Patterns.INTERTAG_TAG_CUSTOM, '>%%%~');
			return htmlString;
		};

		html.removeSpacesInsideTags_ = function removeSpacesInsideTags_(htmlString) {
			htmlString = htmlString.replace(html.Patterns.TAG_END_SPACES, '$1$2');
			htmlString = htmlString.replace(html.Patterns.TAG_QUOTE_SPACES, '=$1$2$3');
			return htmlString;
		};

		html.removeSurroundingSpaces_ = function removeSurroundingSpaces_(htmlString) {
			return htmlString.replace(html.Patterns.SURROUNDING_SPACES, '$1');
		};

		html.replacePreservedBlocks_ = function replacePreservedBlocks_(htmlString, preserved, replaceValue) {
			for (var blockId in preserved) {
				htmlString = htmlString.replace(blockId, replaceValue);
			}

			return htmlString;
		};

		html.simplifyDoctype_ = function simplifyDoctype_(htmlString) {
			var preserved = {};
			htmlString = html.preserveOuterHtml_(htmlString, '<!DOCTYPE', '>', preserved);
			htmlString = html.replacePreservedBlocks_(htmlString, preserved, '<!DOCTYPE html>');
			return htmlString;
		};

		html.returnBlocks_ = function returnBlocks_(htmlString, preserved) {
			for (var blockId in preserved) {
				htmlString = htmlString.replace(blockId, preserved[blockId]);
			}

			return htmlString;
		};

		return html;
	})();

	html.Patterns = {
		INTERTAG_CUSTOM_CUSTOM: /~%%%\s+%%%~/g,
		INTERTAG_TAG_CUSTOM: />\s+%%%~/g,
		INTERTAG_CUSTOM_TAG: /~%%%\s+</g,
		INTERTAG_TAG: />\s+</g,
		SURROUNDING_SPACES: /\s*(<[^>]+>)\s*/g,
		TAG_END_SPACES: /(<(?:[^>]+?))(?:\s+?)(\/?>)/g,
		TAG_QUOTE_SPACES: /\s*=\s*(["']?)\s*(.*?)\s*(\1)/g
	};
	exports.default = html;
});
//# sourceMappingURL=html.js.map