AUI.add(
	'liferay-autocomplete-input',
	function(A) {
		var AArray = A.Array;
		var KeyMap = A.Event.KeyMap;
		var Lang = A.Lang;

		var KEY_DOWN = KeyMap.DOWN;

		var KEY_LIST = [KEY_DOWN, KeyMap.LEFT, KeyMap.RIGHT, KeyMap.UP].join();

		var STR_INPUT_NODE = 'inputNode';

		var STR_PHRASE_MATCH = 'phraseMatch';

		var STR_REG_EXP = 'regExp';

		var STR_SOURCE = 'source';

		var STR_SPACE = ' ';

		var STR_TERM = 'term';

		var STR_TPL_RESULTS = 'tplResults';

		var STR_VISIBLE = 'visible';

		var AutoCompleteInput = A.Component.create(
			{
				ATTRS: {
					acConfig: {
						validator: Lang.isObject,
						value: {
							activateFirstItem: true,
							resultFilters: STR_PHRASE_MATCH,
							resultHighlighter: STR_PHRASE_MATCH
						}
					},

					caretAtTerm: {
						validator: Lang.isBoolean,
						value: true
					},

					inputNode: {
						setter: A.one,
						writeOnce: true
					},

					offset: {
						validator: '_validateOffset',
						value: 10
					},

					regExp: {
						setter: '_setRegExp',
						value: '(?:\\sterm|^term)([^\\s]+)'
					},

					source: {
					},

					term: {
						validator: Lang.isString,
						value: '@'
					},

					tplResults: {
						validator: Lang.isString
					}
				},

				EXTENDS: A.Base,

				NAME: 'liferay-autocomplete-input',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._ac = new A.AutoComplete(instance._getACConfig()).render();

						instance._bindUI();
					},

					destructor: function() {
						var instance = this;

						instance._ac.destroy();

						if (instance._inputMirror) {
							instance._inputMirror.remove();
						}

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_acResultFormatter: function(query, results) {
						var instance = this;

						var tplResults = instance.get(STR_TPL_RESULTS);

						return AArray.map(
							results,
							function(result) {
								var userData = result.raw;

								return Lang.sub(tplResults, userData);
							}
						);
					},

					_acUpdateValue: function(text) {
						var instance = this;

						var caretIndex = instance._getCaretIndex();

						if (caretIndex) {
							var inputNode = instance.get(STR_INPUT_NODE);

							var val = inputNode.val();

							if (val) {
								var lastTermIndex = instance._getPrevTermIndex(val, caretIndex.start);

								if (lastTermIndex >= 0) {
									var prefix = val.substring(0, lastTermIndex);

									val = val.substring(lastTermIndex);

									var regExp = instance.get(STR_REG_EXP);

									var res = regExp.exec(val);

									if (res) {
										var restText = val.substring(res[1].length + 1);

										if (restText.length === 0 || restText.charAt(0) !== STR_SPACE) {
											text += ' ';

											var spaceAdded = true;
										}

										var resultText = prefix + instance.get(STR_TERM) + text;

										var resultEndPos = resultText.length + (spaceAdded ? 0 : 1);

										inputNode.val(resultText + restText);

										instance._setCaretIndex(inputNode, resultEndPos);
									}
								}
							}
						}
					},

					_adjustACPosition: function() {
						var instance = this;

						var inputNode = instance.get(STR_INPUT_NODE);

						var xy = inputNode.getXY();

						var caretXY = instance._getCaretOffset();

						var offset = instance.get('offset');

						var offsetX = 0;
						var offsetY = 0;

						if (Lang.isArray(offset)) {
							offsetX = offset[0];
							offsetY = offset[1];
						}
						else if (Lang.isNumber(offset)) {
							offsetY = offset;
						}

						xy[0] += caretXY.x + offsetX;
						xy[1] += caretXY.y + Lang.toInt(inputNode.getStyle('fontSize')) + offsetY;

						var acBoundingBox = instance._ac.get('boundingBox');

						acBoundingBox.setStyle('left', xy[0]);
						acBoundingBox.setStyle('top', xy[1]);
					},

					_afterACVisibleChange: function(event) {
						var instance = this;

						if (event.newVal) {
							instance._adjustACPosition();
						}
					},

					_bindUI: function() {
						var instance = this;

						var ac = instance._ac;

						ac.on('query', instance._onACQuery, instance);

						ac.after('visibleChange', instance._afterACVisibleChange, instance);

						ac._keys[KEY_DOWN] = A.bind(instance._onACKeyDown, instance);

						A.Do.before(instance._syncACPosition, ac, '_syncUIPosAlign', instance);

						ac._updateValue = A.bind(instance._acUpdateValue, instance);

						var inputNode = instance.get(STR_INPUT_NODE);

						instance._eventHandles = [
							inputNode.on('key', A.bind(instance._onKeyUp, instance), 'up:' + KEY_LIST)
						];
					},

					_getACConfig: function() {
						var instance = this;

						var acConfig = instance.get('acConfig');

						var tplResults = instance.get(STR_TPL_RESULTS);

						if (tplResults) {
							acConfig.resultFormatter = A.bind(instance._acResultFormatter, instance);
						}

						var input = instance.get(STR_INPUT_NODE);

						if (input) {
							acConfig.inputNode = input;
						}

						var source = instance.get(STR_SOURCE);

						if (source) {
							acConfig.source = source;
						}

						return acConfig;
					},

					_getPrevTermIndex: function(content, position) {
						var instance = this;

						var result = -1;

						var term = instance.get(STR_TERM);

						for (var i = position; i >= 0; --i) {
							if (content.charAt(i) === term) {
								result = i;

								break;
							}
						}

						return result;
					},

					_getQuery: function(val) {
						var instance = this;

						var result = null;

						var caretIndex = instance._getCaretIndex();

						if (caretIndex) {
							val = val.substring(0, caretIndex.start);

							var term = instance.get(STR_TERM);

							var lastTermIndex = val.lastIndexOf(term);

							if (lastTermIndex >= 0) {
								val = val.substring(lastTermIndex);

								var regExp = instance.get(STR_REG_EXP);

								var res = regExp.exec(val);

								if (res && (res.index + res[1].length + term.length === val.length)) {
									result = val;
								}
							}
						}

						return result;
					},

					_onACKeyDown: function() {
						var instance = this;

						if (instance._ac.get(STR_VISIBLE)) {
							instance._ac._activateNextItem();
						}
						else {
							return false;
						}
					},

					_onACQuery: function(event) {
						var instance = this;

						var input = instance._getQuery(event.query);

						if (input) {
							event.query = input.substring(1);
						}
						else {
							event.preventDefault();

							if (instance._ac.get(STR_VISIBLE)) {
								instance._ac.hide();
							}
						}
					},

					_onKeyUp: function(event) {
						var instance = this;

						var acVisible = instance._ac.get(STR_VISIBLE);

						if (!acVisible || event.isKeyInSet('left', 'right')) {
							instance._processKeyUp(event);
						}
					},

					_processKeyUp: function(event) {
						var instance = this;

						var inputNode = instance.get(STR_INPUT_NODE);

						var input = instance._getQuery(inputNode.val());

						if (input) {
							input = input.substring(1);

							instance._ac.sendRequest(input);
						}
						else if (instance._ac.get(STR_VISIBLE)) {
							instance._ac.hide();
						}
					},

					_setRegExp: function(value) {
						var instance = this;

						var term = instance.get(STR_TERM);

						return new RegExp(value.replace(/term/g, term));
					},

					_syncACPosition: function() {
						return new A.Do.Halt(null, -1);
					},

					_validateOffset: function(value) {
						return (Lang.isArray(value) || Lang.isNumber(value));
					}
				}
			}
		);

		Liferay.AutoCompleteInput = AutoCompleteInput;
	},
	'',
	{
		requires: ['aui-base', 'autocomplete', 'autocomplete-filters', 'autocomplete-highlighters']
	}
);
