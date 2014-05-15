;(function() {
	var A = AUI();

	var KeyMap = A.Event.KeyMap;

	var Lang = A.Lang;

	var STR_EDITOR = 'editor';

	var STR_EMPTY = '';

	var STR_SPACE = ' ';

	var STR_TERM = 'term';

	var AutoCompleteCKEditor = A.Component.create(
		{
			EXTENDS: A.Base,

			AUGMENTS: [Liferay.AutoCompleteInputBase],

			NAME: 'liferay-autocomplete-ckeditor',

			ATTRS: {
				editor: {
					validator: Lang.isObject,
					writeOnce: true
				},

				inputNode: {
					valueFn: '_getInputElement',
					writeOnce: true
				}
			},

			prototype: {
				initializer: function() {
					var instance = this;

					instance._bindUI();
				},

				_acUpdateValue: function(text) {
					var instance = this;

					var prevTermPosition = instance._getPrevTermPosition();

					var prevTermContainer = prevTermPosition.container;

					var containerAscendant = instance._getContainerAscendant(prevTermContainer);

					var updateWalker = instance._getWalker(containerAscendant, prevTermContainer);

					var offset = prevTermPosition.index;

					var term = instance.get(STR_TERM);

					var addChars;

					var node = updateWalker.next();

					var prevNode = node;

					var remainingChars = term + text;

					while (node) {
						if (node.type === CKEDITOR.NODE_TEXT) {
							var nodeText = node.getText();

							var availableChars = nodeText.length;

							if (offset !== -1) {
								availableChars -= offset;

								addChars = remainingChars.substring(0, availableChars);

								node.setText(nodeText.substring(0, offset) + addChars);

								offset = -1;

								remainingChars = remainingChars.substring(availableChars);
							}
							else {
								var spaceIndex = nodeText.indexOf(STR_SPACE);

								if (!availableChars || spaceIndex === 0) {
									prevNode.setText(prevNode.getText() + remainingChars);

									updateWalker.end();

									remainingChars = STR_EMPTY;
								}
								else if (spaceIndex !== -1) {
									node.setText(remainingChars + nodeText.substring(spaceIndex));

									updateWalker.end();

									remainingChars = STR_EMPTY;
								}
								else {
									addChars = remainingChars.substring(0, availableChars);

									node.setText(addChars);

									remainingChars = remainingChars.substring(availableChars);
								}
							}

							prevNode = node;
						}

						node = updateWalker.next();
					}

					if (remainingChars.length) {
						prevNode.setText(prevNode.getText() + remainingChars + STR_SPACE);
					}

					var caretIndex = prevNode.getText().indexOf(STR_SPACE) + 1;

					if (prevNode.$ === prevTermPosition.container.$ && caretIndex <= prevTermPosition.index) {
						caretIndex = prevNode.getText().length;
					}

					instance._setCaretIndex(prevNode, caretIndex);

					var editor = instance.get('editor');

					editor.fire('saveSnapshot');
				},

				_bindUI: function() {
					var instance = this;

					instance._processCaret = A.bind('_processCaretPosition', instance);

					var editor = instance.get(STR_EDITOR);

					instance._eventHandles = [
						editor.on('key', A.bind('_onEditorKey', instance))
					];

					editor.once(
						'instanceReady',
						function(event) {
							var editorBody = A.one(event.editor.document.$.body);

							instance._eventHandles.push(
								editorBody.on('mousedown', A.bind('soon', A, instance._processCaret))
							);
						}
					);
				},

				_getACPositionBase: function() {
					var instance = this;

					if (!instance._contentsContainer) {
						var inputElement = instance._getInputElement();

						instance._contentsContainer = inputElement.siblings('.cke').one('.cke_contents') || inputElement;
					}

					return instance._contentsContainer.getXY();
				},

				_getACPositionOffset: function() {
					var instance = this;

					var caretContainer = instance._getCaretContainer();

					var containerAscendantElement = instance._getContainerAscendant(caretContainer);

					var containerAscendantNode = A.one(containerAscendantElement.$);

					return [0, Lang.toInt(containerAscendantNode.getStyle('fontSize'))];
				},

				_getCaretContainer: function() {
					var instance = this;

					return instance._getCaretRange().startContainer;
				},

				_getCaretIndex: function(node) {
					var instance = this;

					var range = instance._getCaretRange();

					return {
						end: range.endOffset,
						start: range.startOffset
					};
				},

				_getCaretOffset: function() {
					var instance = this;

					var editor = instance.get(STR_EDITOR);

					var bookmarks = editor.getSelection().createBookmarks();

					var bookmarkNodeEl = bookmarks[0].startNode.$;

					A.one(bookmarkNodeEl).setStyle('display', 'inline-block');

					var inputCaretOffsetX = bookmarkNodeEl.offsetLeft;
					var inputCaretOffsetY = bookmarkNodeEl.offsetTop;

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

				_getContainerAscendant: function(container, ascendant) {
					if (!ascendant) {
						ascendant = AutoCompleteCKEditor.CONTAINER_ASCENDANT;
					}

					return container.getAscendant(ascendant, true);
				},

				_getInputElement: function(value) {
					var instance = this;

					return A.one(instance.get(STR_EDITOR).element.$);
				},

				_getPrevTermPosition: function() {
					var instance = this;

					var term = instance.get(STR_TERM);

					var caretContainer = instance._getCaretContainer();
					var caretIndex = instance._getCaretIndex();

					var query = caretContainer.getText().substring(0, caretIndex.start);

					var termContainer = caretContainer;

					var termIndex = query.lastIndexOf(term);

					if (termIndex === -1) {
						var termWalker = instance._getWalker(termContainer);

						termWalker.guard = function(node) {
							var hasTerm = false;

							if (node.type === CKEDITOR.NODE_TEXT && node.$ !== caretContainer.$) {
								var nodeText = node.getText();

								termIndex = nodeText.lastIndexOf(term);

								hasTerm = (termIndex !== -1);

								if (hasTerm) {
									query = nodeText.substring(termIndex) + query;

									termContainer = node;
								}
								else {
									query = node.getText() + query;
								}
							}

							return !hasTerm;
						};

						termWalker.checkBackward();
					}
					else {
						query = query.substring(termIndex);
					}

					return {
						container: termContainer,
						index: termIndex,
						query: query
					};
				},

				_getQuery: function() {
					var instance = this;

					var query = instance._getPrevTermPosition().query;

					var regExp = instance.get('regExp');

					var res = regExp.exec(query);

					var term = instance.get(STR_TERM);

					var result;

					if (res && ((res.index + res[1].length + term.length) === query.length)) {
						result = query;
					}

					return result;
				},

				_getWalker: function(endContainer, startContainer) {
					var instance = this;

					endContainer = endContainer || instance._getCaretContainer();

					startContainer = startContainer || instance._getContainerAscendant(endContainer);

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

					return (selection.getType() === CKEDITOR.SELECTION_NONE || (ranges.length === 1 && ranges[0].collapsed));
				},

				_normalizeCKEditorKeyEvent: function(event) {
					return new A.DOMEventFacade(
						{
							keyCode: event.data.keyCode,
							preventDefault: event.cancel,
							stopPropagation: event.stop,
							type: 'keydown'
						}
					);
				},

				_onEditorKey: function(event) {
					var instance = this;

					if (instance._isEmptySelection()) {
						event = instance._normalizeCKEditorKeyEvent(event);

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
				}
			}
		}
	);

	AutoCompleteCKEditor.CONTAINER_ASCENDANT = {
		body: 1,
		div: 1,
		h1: 1,
		h2: 1,
		h3: 1,
		h4: 1,
		p: 1,
		pre: 1,
		span: 1
	};

	Liferay.AutoCompleteCKEditor = AutoCompleteCKEditor;
})();