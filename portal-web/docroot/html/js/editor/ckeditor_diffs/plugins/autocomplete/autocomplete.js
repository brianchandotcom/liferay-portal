var A = AUI();

var KeyMap = A.Event.KeyMap;

var STR_EDITOR = 'editor';

var STR_SPACE = ' ';

var STR_TERM = 'term';

var AutoCompleteCKEditor = A.Component.create(
{
	EXTENDS: A.Base,

	AUGMENTS: [Liferay.AutoCompleteInputBase],

	NAME: 'liferay-autocomplete-ckeditor',

	ATTRS: {
		editor: {
			validator: A.Lang.isObject
		}
	},

	prototype: {
		initializer: function() {
			var instance = this;

			instance._bindUI();
		},

		_bindUI: function() {
			var instance = this;

			instance._processCaret = A.bind('_processCaretPosition', instance);

			var editor = instance.get(STR_EDITOR);

			instance._eventHandles = [
				editor.on('key', A.bind('_onEditorKey', instance)),
				editor.on('selectionChange', instance._processCaret)
			];

			A.Do.before(instance._wrapCKEditorKeyEvent, instance, '_onEditorKey', instance);
		},

		_getACPositionBase: function() {
			var instance = this;

			return [0, 0];
		},

		_getACPositionOffset: function() {
			var instance = this;

			return [0, 0];
		},

		_acUpdateValue: function(text) {
			var instance = this;

			var prevTermPosition = instance._getPrevTermPosition();

			var prevTermContainer = prevTermPosition.container;

			var containerAscendant = prevTermContainer.getAscendant('p', true);

			var updateWalker = instance._getWalker(containerAscendant, prevTermContainer);

			var offset = prevTermPosition.index;

			var term = instance.get(STR_TERM);

			var node;

			var prevNode = node;

			text = term + text;

			while (node = updateWalker.next()) {
				if (node.type === CKEDITOR.NODE_TEXT) {
					var nodeText = node.getText();

					var availableChars = nodeText.length;

					if (offset !== -1) {
						availableChars -= offset;

						var addChars = text.substring(0, availableChars);

						node.setText(nodeText.substring(0, offset) + addChars);

						offset = -1;

						text = text.substring(availableChars);
					}
					else {
						var spaceIndex = nodeText.indexOf(STR_SPACE);

						if (!availableChars || spaceIndex === 0) {
							prevNode.setText(prevNode.getText() + text);

							updateWalker.end();

							text = '';
						}
						else if (spaceIndex !== -1) {
							node.setText(text + nodeText.substring(spaceIndex));

							updateWalker.end();

							text = '';
						}
						else {
							var addChars = text.substring(0, availableChars);

							node.setText(addChars);

							text = text.substring(availableChars);
						}
					}

					prevNode = node;
				}
			}

			if (text.length) {
				prevNode.setText(prevNode.getText() + text + STR_SPACE);
			}

			var caretIndex = prevNode.getText().indexOf(STR_SPACE) + 1;

			instance._setCaretIndex(prevNode, caretIndex);
		},

		_getCaretContainer: function() {
			var instance = this;

			var range = instance._getCaretRange();

			return range.startContainer;
		},

		_getCaretIndex: function(node) {
			var instance = this;

			var range = instance._getCaretRange();

			return {
				start: range.startOffset,
				end: range.endOffset
			};
		},

		_getCaretOffset: function() {
			var instance = this;

			var contentsOffset = instance._getContentsOffset();

			var editor = instance.get(STR_EDITOR);
			var bookmarks = editor.getSelection().createBookmarks();
			var bookmarkNodeEl = bookmarks[0].startNode.$;

			bookmarkNodeEl.style.setProperty('display', 'inline-block');

			var inputCaretOffsetX = bookmarkNodeEl.offsetLeft + contentsOffset[0];
			var inputCaretOffsetY = bookmarkNodeEl.offsetTop + contentsOffset[1];

			bookmarkNodeEl.parentElement.removeChild(bookmarkNodeEl);

			return {
				x: inputCaretOffsetX,
				y: inputCaretOffsetY
			};
		},

		_getCaretRange: function() {
			var instance = this;

			var editor = instance.get(STR_EDITOR);

			return editor.getSelection().getRanges()[0];
		},

		_getContentsOffset: function() {
			var instance = this;

			if (!instance._contentsContainer) {
				var inputElement = instance._getInputElement();

				instance._contentsContainer = inputElement.siblings('.cke').one('.cke_contents') || inputElement;
			}

			return instance._contentsContainer.getXY();
		},

		_getInputElement: function() {
			var instance = this;

			if (!instance._inputElement) {
				var editor = instance.get(STR_EDITOR);

				instance._inputElement = A.one(editor.element.$);
			}

			return instance._inputElement;
		},

		_getPrevTermPosition: function() {
			var instance = this;

			var term = instance.get(STR_TERM);

			var termContainer = instance._getCaretContainer();

			var termIndex = termContainer.getText().lastIndexOf(term);

			if (termIndex === -1) {
				var termWalker = instance._getWalker(termContainer);

				termWalker.guard = function(node) {
					var hasTerm = false;

					if (node.type === CKEDITOR.NODE_TEXT) {
						termIndex = node.getText().indexOf(term);

						hasTerm = (termIndex !== -1);

						if (hasTerm) {
							termContainer = node;
						}
					}

					return !hasTerm;
				};

				termWalker.checkBackward();
			}

			return {
				container: termContainer,
				index: termIndex
			}
		},

		_getQuery: function() {
			var instance = this;

			var result;

			var caretContainer = instance._getCaretContainer();

			var caretContainerId = caretContainer.getUniqueId();

			var caretIndex = instance._getCaretIndex().start;

			var prevTermPosition = instance._getPrevTermPosition();

			var prevTermContainerId = prevTermPosition.container.getUniqueId();

			var query = '';

			if (caretContainerId === prevTermContainerId) {
				query = prevTermPosition.container.getText().substring(prevTermPosition.index, caretIndex);
			}
			else {
				query = prevTermPosition.container.getText().substring(prevTermPosition.index);

				var queryWalker = instance._getWalker(caretContainer, prevTermPosition.container);

				queryWalker.guard = function(node) {
					var nodeId = node.getUniqueId();

					var isCaretContainer = (nodeId === caretContainerId);

					if (!isCaretContainer) {
						if ((node.type === CKEDITOR.NODE_TEXT) && (nodeId !== prevTermContainerId)) {
							query += node.getText();
						}
					}
					else {
						query += node.getText().substring(0, caretIndex);
					}

					return !isCaretContainer;
				};

				queryWalker.checkForward();
			}

			var regExp = instance.get('regExp');

			var res = regExp.exec(query);

			var term = instance.get(STR_TERM);

			if (res && ((res.index + res[1].length + term.length) === query.length)) {
				result = query;
			}

			return result;
		},

		_getWalker: function(endContainer, startContainer) {
			var instance = this;

			endContainer = endContainer || instance._getCaretContainer();

			startContainer = startContainer || endContainer.getAscendant('p', true);

			var range = new CKEDITOR.dom.range(startContainer);

			range.setStart(startContainer, 0);
			range.setEnd(endContainer, endContainer.getText().length);

			var walker = new CKEDITOR.dom.walker(range);

			return walker;
		},

		_isEmptySelection: function() {
			var instance = this;

			var editor = instance.get(STR_EDITOR);

			var selection = editor.getSelection();

			var ranges = selection.getRanges();

			return (
				(selection.getType() == CKEDITOR.SELECTION_NONE) ||
				(ranges.length == 1 && ranges[0].collapsed)
			);
		},

		_onEditorKey: function(event) {
			var instance = this;

			if (instance._isEmptySelection()) {
				var ac = instance._ac;

				var acVisible = ac.get('visible');

				if (acVisible && (KeyMap.isKeyInSet(event.keyCode, 'down', 'enter', 'up'))) {
					var editor = instance.get(STR_EDITOR);

					var inlineEditor = editor.editable().isInline();

					if (KeyMap.isKey(event.keyCode, 'enter') || !inlineEditor) {
						ac._onInputKey(event);
					}
				}
				else {
					A.soon(instance._processCaret);
				}
			}
		},

		_processCaretPosition: function() {
			var instance = this;

			var query = instance._getQuery();

			instance._processKeyUp(query);
		},

		_setCaretIndex: function(node, caretIndex) {
			var instance = this;

			var editor = instance.get(STR_EDITOR);

			var caretRange = editor.createRange();

			caretRange.setStart(node, caretIndex);
			caretRange.setEnd(node, caretIndex);

			editor.getSelection().selectRanges([caretRange]);
			editor.focus();
		},

		_wrapCKEditorKeyEvent: function(event) {
			var instance = this;

			var alterArgs = new A.Do.AlterArgs(
				'Wrapped CKEDITOR.dom.event inside A.DOMEventFacade',
				[
					new A.DOMEventFacade(
						{
							keyCode: event.data.keyCode,
							preventDefault: event.cancel,
							stopPropagation: event.stop,
							type: 'keydown'
						}
					)
				]
			);

			return alterArgs;
		}
	}
});

Liferay.AutoCompleteCKEditor = AutoCompleteCKEditor;