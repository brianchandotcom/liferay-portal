AUI.add(
	'liferay-autocomplete-input-base',
	function(A) {
		var AArray = A.Array;
		var KeyMap = A.Event.KeyMap;
		var Lang = A.Lang;

		var KEY_DOWN = KeyMap.DOWN;

		var REGEX_TERM = /term/g;

		var STR_INPUT_NODE = 'inputNode';

		var STR_PHRASE_MATCH = 'phraseMatch';

		var STR_SOURCE = 'source';

		var STR_TERM = 'term';

		var STR_TPL_RESULTS = 'tplResults';

		var STR_VISIBLE = 'visible';

		var AutoCompleteInputBase = function() {};

		AutoCompleteInputBase.ATTRS = {
			acConfig: {
				validator: Lang.isObject,
				value: {
					activateFirstItem: true,
					resultFilters: STR_PHRASE_MATCH,
					resultHighlighter: STR_PHRASE_MATCH
				}
			},

			inputNode: {
				setter: A.one,
				writeOnce: true
			},

			caretAtTerm: {
				validator: Lang.isBoolean,
				value: true
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
		};

		AutoCompleteInputBase.NAME = 'liferay-autocomplete-input-base';

		AutoCompleteInputBase.prototype = {
			initializer: function() {
				var instance = this;

				var ac = new A.AutoComplete(instance._getACConfig()).render();

				ac.get('boundingBox').addClass('lfr-autocomplete-input-list');

				instance._ac = ac;

				instance._bindUIACIBase();
			},

			destructor: function() {
				var instance = this;

				instance._ac.destroy();

				(new A.EventHandle(instance._eventHandles)).detach();
			},

			_acResultFormatter: function(query, results) {
				var instance = this;

				var tplResults = instance.get(STR_TPL_RESULTS);

				return AArray.map(
					results,
					function(result) {
						return Lang.sub(tplResults, result.raw);
					}
				);
			},

			_adjustACPosition: function() {
				var instance = this;

				var xy = instance._getACPositionBase();

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

				var acOffset = instance._getACPositionOffset();

				xy[0] += caretXY.x + offsetX + acOffset[0];
				xy[1] += caretXY.y + offsetY + acOffset[1];

				instance._ac.get('boundingBox').setXY(xy);
			},

			_afterACVisibleChange: function(event) {
				var instance = this;

				if (event.newVal) {
					instance._adjustACPosition();
				}
			},

			_bindUIACIBase: function() {
				var instance = this;

				var ac = instance._ac;

				ac.on('query', instance._onACQuery, instance);

				ac.after('visibleChange', instance._afterACVisibleChange, instance);

				ac._keys[KEY_DOWN] = A.bind('_onACKeyDown', instance);

				A.Do.before(instance._syncACPosition, ac, '_syncUIPosAlign', instance);

				ac._updateValue = A.bind('_acUpdateValue', instance);
			},

			_getACConfig: function() {
				var instance = this;

				var acConfig = instance.get('acConfig');

				var tplResults = instance.get(STR_TPL_RESULTS);

				if (tplResults) {
					acConfig.resultFormatter = A.bind('_acResultFormatter', instance);
				}

				acConfig.inputNode = instance.get(STR_INPUT_NODE);

				var source = instance.get(STR_SOURCE);

				if (source) {
					acConfig.source = source;
				}

				return acConfig;
			},

			_onACKeyDown: function() {
				var instance = this;

				var ac = instance._ac;

				var acVisible = ac.get(STR_VISIBLE);

				if (acVisible) {
					ac._activateNextItem();
				}

				return acVisible;
			},

			_onACQuery: function(event) {
				var instance = this;

				var input = instance._getQuery(event.query);

				if (input) {
					event.query = input.substring(1);
				}
				else {
					event.preventDefault();

					var ac = instance._ac;

					if (ac.get(STR_VISIBLE)) {
						ac.hide();
					}
				}
			},

			_processKeyUp: function(query) {
				var instance = this;

				var ac = instance._ac;

				if (query) {
					query = query.substring(1);

					ac.sendRequest(query);
				}
				else if (ac.get(STR_VISIBLE)) {
					ac.hide();
				}
			},

			_setRegExp: function(value) {
				var instance = this;

				return new RegExp(value.replace(REGEX_TERM, instance.get(STR_TERM)));
			},

			_syncACPosition: function() {
				return new A.Do.Halt(null, -1);
			},

			_validateOffset: function(value) {
				return (Lang.isArray(value) || Lang.isNumber(value));
			}
		};

		Liferay.AutoCompleteInputBase = AutoCompleteInputBase;
	},
	'',
	{
		requires: ['aui-base', 'autocomplete', 'autocomplete-filters', 'autocomplete-highlighters']
	}
);