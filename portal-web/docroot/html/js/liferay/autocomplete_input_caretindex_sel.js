AUI.add(
	'liferay-autocomplete-input-caretindex-sel',
	function(A) {
		var REGEX_NEW_LINE = /\r\n/g;

		var STR_CHARACTER = 'character';

		var STR_END_TO_END = 'EndToEnd';

		var STR_INPUT_NODE = 'inputNode';

		var STR_NEW_LINE = '\n';

		var AutcompleteInputCaretIndex = function(){};

		AutcompleteInputCaretIndex.prototype = {
			_getCaretIndex: function(node) {
				var instance = this;

				var endRange, len, normalizedValue, range, textInputRange;

				node = node || instance.get(STR_INPUT_NODE);

				var inputDOMNode = node.getDOMNode();

				var docNode = A.getDoc().getDOMNode();

				var start = 0;
				var end = 0;

				range = docNode.selection.createRange();

				if (range && range.parentElement() === inputDOMNode) {
					len = inputDOMNode.value.length;

					normalizedValue = inputDOMNode.value.replace(REGEX_NEW_LINE, STR_NEW_LINE);

					textInputRange = inputDOMNode.createTextRange();
					textInputRange.moveToBookmark(range.getBookmark());

					endRange = inputDOMNode.createTextRange();
					endRange.collapse(false);

					if (textInputRange.compareEndPoints('StartToEnd', endRange) > -1) {
						start = end = len;
					}
					else {
						start = -textInputRange.moveStart(STR_CHARACTER, -len);
						start += normalizedValue.slice(0, start).split(STR_NEW_LINE).length - 1;

						if (textInputRange.compareEndPoints(STR_END_TO_END, endRange) > -1) {
							end = len;
						}
						else {
							end = -textInputRange.moveEnd(STR_CHARACTER, -len);
							end += normalizedValue.slice(0, end).split(STR_NEW_LINE).length - 1;
						}
					}
				}

				return {
					start: start,
					end: end
				};
			},

			_setCaretIndex: function(node, cursorIndex) {
				var instance = this;

				node = node || instance.get(STR_INPUT_NODE);

				var input = node.getDOMNode();

				if (input.createTextRange) {
					var val = node.val().substring(0, cursorIndex);

					var count = 0;

					var regExpNewLine = /\r\n/g;

					while (regExpNewLine.exec(val) !== null) {
						count++;
					}

					var range = input.createTextRange();

					range.move(STR_CHARACTER, cursorIndex - count);

					range.select();
				}
			}
		};

		A.Base.mix(Liferay.AutoCompleteInput, [AutcompleteInputCaretIndex]);
	},
	'',
	{
		requires: ['liferay-autocomplete-input']
	}
);