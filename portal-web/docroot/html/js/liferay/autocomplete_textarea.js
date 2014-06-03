AUI.add(
	'liferay-autocomplete-textarea',
	function(A) {
		var AArray = A.Array;
		var KeyMap = A.Event.KeyMap;
		var Lang = A.Lang;

		var KEY_DOWN = KeyMap.DOWN;

		var KEY_LIST = [KEY_DOWN, KeyMap.LEFT, KeyMap.RIGHT, KeyMap.UP].join();

		var STR_INPUT_NODE = 'inputNode';

		var STR_REG_EXP = 'regExp';

		var STR_SPACE = ' ';

		var STR_TERM = 'term';

		var STR_VISIBLE = 'visible';

		var AutoCompleteTextarea = function() {};

		AutoCompleteTextarea.prototype = {
			initializer: function() {
				var instance = this;

				instance._bindUI();
			},

			destructor: function() {
				var instance = this;

				if (instance._inputMirror) {
					instance._inputMirror.remove();
				}
			},

			_bindUI: function() {
				var instance = this;

				var inputNode = instance.get(STR_INPUT_NODE);

				instance._eventHandles = [
					inputNode.on('key', A.bind('_onKeyUp', instance), 'up:' + KEY_LIST)
				];
			},

			_getACPositionBase: function() {
				var instance = this;

				return instance.get(STR_INPUT_NODE).getXY();
			},

			_getACPositionOffset: function() {
				var instance = this;

				var inputNode = instance.get(STR_INPUT_NODE);

				return [0, Lang.toInt(inputNode.getStyle('fontSize'))];
			},

			_getACVal: function() {
				var instance = this;

				return instance.get(STR_INPUT_NODE).val();
			},

			_getPrevTerm: function(content, position) {
				var instance = this;

				var result = -1;

				var term = null;

				var terms = instance._getTerms();

				for (var i = position; i >= 0; --i) {
					var termIndex = terms.indexOf(content.charAt(i));

					if (termIndex >=0 ) {
						result = i;
						term = terms[termIndex];

						break;
					}
				}

				return {
					index: result,
					term: term
				}
			},

			_getQuery: function(val) {
				var instance = this;

				var result = null;

				var caretIndex = instance._getCaretIndex();

				if (caretIndex) {
					val = val.substring(0, caretIndex.start);

					AArray.each(
						instance._getTerms(),
						function(item, index, collection) {
							var term = item;

							var lastTermIndex = val.lastIndexOf(term);

							if (lastTermIndex >= 0) {
								val = val.substring(lastTermIndex);

								var regExp = instance.get(STR_REG_EXP);

								var res = regExp.exec(val);

								if (res && ((res.index + res[1].length + term.length) === val.length) && (!result || val.length < result.length)) {
									result = val;
								}
							}
						}
					);
				}

				return result;
			},

			_onKeyUp: function(event) {
				var instance = this;

				var acVisible = instance.get(STR_VISIBLE);

				if (!acVisible || event.isKeyInSet('left', 'right')) {
					var inputNode = instance.get(STR_INPUT_NODE);

					var query = instance._getQuery(inputNode.val());

					instance._processKeyUp(query);
				}
			},

			_setACVal: function(text) {
				var instance = this;

				var inputNode = instance.get(STR_INPUT_NODE);

				inputNode.val(text);
			},

			_updateValue: function(text) {
				var instance = this;

				var caretIndex = instance._getCaretIndex();

				if (caretIndex) {
					var val = instance._getACVal();

					if (val) {
						var lastTerm = instance._getPrevTerm(val, caretIndex.start);

						var lastTermIndex = lastTerm.index;

						if (lastTermIndex >= 0) {
							var prefix = val.substring(0, lastTermIndex);

							val = val.substring(lastTermIndex);

							var regExp = instance.get(STR_REG_EXP);

							var res = regExp.exec(val);

							if (res) {
								var restText = val.substring(res[1].length + 1);

								var spaceAdded = 1;

								if (restText.length === 0 || restText.charAt(0) !== STR_SPACE) {
									text += STR_SPACE;

									spaceAdded = 0;
								}

								var resultText = prefix + lastTerm.term + text;

								var resultEndPos = resultText.length + spaceAdded;

								instance._setACVal(resultText + restText);

								instance._setCaretIndex(instance.get(STR_INPUT_NODE), resultEndPos);
							}
						}
					}
				}
			}
		};

		Liferay.AutoCompleteTextarea = A.Base.mix(
			A.AutoComplete,
			[
				Liferay.AutoCompleteInputBase,
				AutoCompleteTextarea
			]
		);
	},
	'',
	{
		requires: ['liferay-autocomplete-input']
	}
);