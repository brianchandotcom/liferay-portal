AUI.add(
	'liferay-autocomplete-input-caretoffset',
	function(A) {
		var AArray = A.Array;

		var ANode = A.Node;

		var MIRROR_STYLES = [
			'boxSizing',
			'fontFamily',
			'fontSize',
			'fontStyle',
			'fontVariant',
			'fontWeight',
			'height',
			'letterSpacing',
			'lineHeight',
			'maxHeight',
			'minHeight',
			'padding',
			'textDecoration',
			'textIndent',
			'textTransform',
			'width',
			'wordSpacing'
		];

		var STR_INPUT_NODE = 'inputNode';

		var AutcompleteInputCaretOffset = function(){};

		AutcompleteInputCaretOffset.prototype = {
			_applyMirrorContent: function() {
				var instance = this;

				var input = instance.get(STR_INPUT_NODE);

				var val = input.val();

				var caretIndex = instance._getCaretIndex().start;

				if (caretIndex === val.length) {
					val = val + instance.TPL_CARET;
				}
				else {
					if (instance.get('caretAtTerm')) {
						caretIndex = instance._getPrevTermIndex(val, caretIndex) + 1;
					}

					val = val.substring(0, caretIndex) + instance.TPL_CARET + val.substring(caretIndex + 1);
				}

				instance._inputMirror.html(val);

				return val;
			},

			_applyMirrorStyles: function() {
				var instance = this;

				var inputMirror = instance._inputMirror;
				var inputNode = instance.get(STR_INPUT_NODE);

				var styles = {};

				AArray.each(
					MIRROR_STYLES,
					function(item, index, collection) {
						styles[item] = inputNode.getStyle(item);
					}
				);

				inputMirror.setStyles(styles);
			},

			_createInputMirror: function() {
				var instance = this;

				if (!instance._inputMirror) {
					var inputMirror = ANode.create(instance.TPL_INPUT_MIRROR);

					A.getBody().appendChild(inputMirror);

					instance._inputMirror = inputMirror;
				}
			},

			_getCaretOffset: function(node) {
				var instance = this;

				instance._createInputMirror();

				instance._applyMirrorStyles();

				instance._applyMirrorContent();

				node = node || instance.get(STR_INPUT_NODE);

				var inputDOMNode = node.getDOMNode();

				var scrollLeft = inputDOMNode.scrollLeft;
				var scrollTop = inputDOMNode.scrollTop;

				var inputCaretNode = instance._inputMirror.one('.input-caret');

				var inputCaretDOMNode = inputCaretNode.getDOMNode();

				return {
					x: inputCaretDOMNode.offsetLeft + scrollLeft,
					y: inputCaretDOMNode.offsetTop - scrollTop
				};
			},

			TPL_CARET: '<span class="input-caret">&nbsp</span>',

			TPL_INPUT_MIRROR: '<div class="input-mirror"></div>'
		};

		A.Base.mix(Liferay.AutoCompleteInput, [AutcompleteInputCaretOffset]);
	},
	'',
	{
		requires: ['liferay-autocomplete-input']
	}
);