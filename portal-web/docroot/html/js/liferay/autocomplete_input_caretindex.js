AUI.add(
	'liferay-autocomplete-input-caretindex',
	function(A) {
		var AutcompleteInputCaretIndex = function(){};

		AutcompleteInputCaretIndex.prototype = {
			_getCaretIndex: function(node) {
				var instance = this;

				node = node || instance.get('inputNode');

				var input = node.getDOM();

				return {
					start: input.selectionStart,
					end: input.selectionStart
				};
			},

			_setCaretIndex: function(node, caretIndex) {
				var instance = this;

				node = node || instance.get(STR_INPUT_NODE);

				var input = node.getDOM();

				Liferay.Util.focusFormField(input);

				input.setSelectionRange(caretIndex, caretIndex);
			}
		};

		A.Base.mix(Liferay.AutoCompleteInput, [AutcompleteInputCaretIndex]);
	},
	'',
	{
		requires: ['liferay-autocomplete-input']
	}
);