/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

(function() {
	var A = AUI();

	var AArray = A.Array;
	var KeyMap = A.Event.KeyMap;
	var Lang = A.Lang;

	var CSS_LFR_AC_CONTENT = 'lfr-ac-content';

	var STR_EDITOR = 'editor';

	var STR_SPACE = ' ';

	var TPL_REPLACE_HTML =
		'<span class="' + CSS_LFR_AC_CONTENT + '">{html}</span>';

	var AutoCompleteCKEditor = function() {};

	AutoCompleteCKEditor.ATTRS = {
		editor: {
			validator: Lang.isObject,
			writeOnce: true
		},

		inputNode: {
			valueFn: '_getInputElement',
			writeOnce: true
		}
	};

	AutoCompleteCKEditor.prototype = {
		_bindUIACCKEditor() {
			var instance = this;

			instance._processCaret = A.bind('_processCaretPosition', instance);

			instance._processCaretTask = A.debounce(instance._processCaret, 50);

			var editor = instance.get(STR_EDITOR);

			instance._eventHandles = [
				editor.on('key', A.bind('_onEditorKey', instance))
			];

			editor.once('instanceReady', event => {
				var editorBody = A.one(event.editor.element.$);

				instance._eventHandles.push(
					editorBody.on(
						'mousedown',
						A.bind('soon', A, instance._processCaret)
					)
				);
			});
		},

		_getACPositionBase() {
			var instance = this;

			var inline = this.get(STR_EDITOR)
				.editable()
				.isInline();

			if (!instance._contentsContainer) {
				var inputElement = instance._getInputElement();

				instance._contentsContainer =
					inputElement.siblings('.cke').one('.cke_contents') ||
					inputElement;
			}

			return inline ? [0, 0] : instance._contentsContainer.getXY();
		},

		_getACPositionOffset() {
			var instance = this;

			var caretContainer = instance._getCaretContainer();

			var containerAscendantElement = instance._getContainerAscendant(
				caretContainer
			);

			var containerAscendantNode = A.one(containerAscendantElement.$);

			return [0, Lang.toInt(containerAscendantNode.getStyle('fontSize'))];
		},

		_getCaretContainer() {
			var instance = this;

			return instance._getCaretRange().startContainer;
		},

		_getCaretIndex() {
			var instance = this;

			var range = instance._getCaretRange();

			return {
				end: range.endOffset,
				start: range.startOffset
			};
		},

		_getCaretOffset() {
			var instance = this;

			var editor = instance.get(STR_EDITOR);

			var bookmarks = editor.getSelection().createBookmarks();

			var bookmarkNode = A.one(bookmarks[0].startNode.$);

			bookmarkNode.setStyle('display', 'inline-block');

			var bookmarkXY = bookmarkNode.getXY();

			bookmarkNode.remove();

			return {
				x: bookmarkXY[0],
				y: bookmarkXY[1]
			};
		},

		_getCaretRange() {
			var instance = this;

			var editor = instance.get(STR_EDITOR);

			return editor.getSelection().getRanges()[0];
		},

		_getContainerAscendant(container, ascendant) {
			if (!ascendant) {
				ascendant = AutoCompleteCKEditor.CONTAINER_ASCENDANT;
			}

			return container.getAscendant(ascendant, true);
		},

		_getInputElement() {
			var instance = this;

			return A.one(instance.get(STR_EDITOR).element.$);
		},

		_getPrevTriggerPosition() {
			var instance = this;

			var caretContainer = instance._getCaretContainer();
			var caretIndex = instance._getCaretIndex();

			var query = caretContainer.getText().substring(0, caretIndex.start);

			var triggerContainer = caretContainer;

			var triggerIndex = -1;

			var trigger = null;

			var triggers = instance._getTriggers();

			AArray.each(triggers, item => {
				var triggerPosition = query.lastIndexOf(item);

				if (triggerPosition !== -1 && triggerPosition > triggerIndex) {
					trigger = item;
					triggerIndex = triggerPosition;
				}
			});

			if (triggerIndex === -1) {
				var triggerWalker = instance._getWalker(triggerContainer);

				triggerWalker.guard = function(node) {
					var hasTrigger = false;

					if (
						node.type === CKEDITOR.NODE_TEXT &&
						node.$ !== caretContainer.$
					) {
						var nodeText = node.getText();

						AArray.each(triggers, item => {
							var triggerPosition = nodeText.lastIndexOf(item);

							if (
								triggerPosition !== -1 &&
								triggerPosition > triggerIndex
							) {
								trigger = item;
								triggerIndex = triggerPosition;
							}
						});

						hasTrigger = triggerIndex !== -1;

						if (hasTrigger) {
							query = nodeText.substring(triggerIndex) + query;

							triggerContainer = node;
						}
						else {
							query = node.getText() + query;
						}
					}

					return !(
						hasTrigger ||
						(node.type === CKEDITOR.NODE_ELEMENT &&
							node.$.className === CSS_LFR_AC_CONTENT)
					);
				};

				triggerWalker.checkBackward();
			}
			else if (
				triggerIndex > 0 &&
				query.charAt(triggerIndex - 1) === STR_SPACE
			) {
				query = query.substring(triggerIndex);
			}

			return {
				container: triggerContainer,
				index: triggerIndex,
				query,
				value: trigger
			};
		},

		_getQuery() {
			var instance = this;

			var prevTriggerPosition = instance._getPrevTriggerPosition();

			var query = prevTriggerPosition.query;
			var trigger = prevTriggerPosition.value;

			var res = instance._getRegExp().exec(query);

			var result;

			if (res) {
				if (
					res.index + res[1].length + trigger.length ===
					query.length
				) {
					result = query;
				}
			}

			return result;
		},

		_getWalker(endContainer, startContainer) {
			var instance = this;

			endContainer = endContainer || instance._getCaretContainer();

			startContainer =
				startContainer || instance._getContainerAscendant(endContainer);

			var range = new CKEDITOR.dom.range(startContainer);

			range.setStart(startContainer, 0);
			range.setEnd(endContainer, endContainer.getText().length);

			var walker = new CKEDITOR.dom.walker(range);

			return walker;
		},

		_isEmptySelection() {
			var instance = this;

			var editor = instance.get(STR_EDITOR);

			var selection = editor.getSelection();

			var ranges = selection.getRanges();

			var collapsedRange = ranges.length === 1 && ranges[0].collapsed;

			return (
				selection.getType() === CKEDITOR.SELECTION_NONE ||
				collapsedRange
			);
		},

		_normalizeCKEditorKeyEvent(event) {
			return new A.DOMEventFacade({
				keyCode: event.data.keyCode,
				preventDefault: event.cancel,
				stopPropagation: event.stop,
				type: 'keydown'
			});
		},

		_onEditorKey(event) {
			var instance = this;

			if (instance._isEmptySelection()) {
				event = instance._normalizeCKEditorKeyEvent(event);

				var acVisible = instance.get('visible');

				if (
					acVisible &&
					KeyMap.isKeyInSet(event.keyCode, 'down', 'enter', 'up')
				) {
					var editor = instance.get(STR_EDITOR);

					var inlineEditor = editor.editable().isInline();

					if (KeyMap.isKey(event.keyCode, 'enter') || !inlineEditor) {
						instance._onInputKey(event);
					}
				}
				else if (event.keyCode === KeyMap.ESC) {
					instance.hide();
				}
				else {
					instance._processCaretTask();
				}
			}
		},

		_processCaretPosition() {
			var instance = this;

			var query = instance._getQuery();

			instance._processKeyUp(query);
		},

		_replaceHtml(text, prevTriggerPosition) {
			var instance = this;

			var replaceContainer = instance._getContainerAscendant(
				prevTriggerPosition.container,
				'span'
			);

			if (
				!replaceContainer ||
				!replaceContainer.hasClass('lfr-ac-content')
			) {
				replaceContainer = prevTriggerPosition.container.split(
					prevTriggerPosition.index
				);
			}

			var newElement = CKEDITOR.dom.element.createFromHtml(
				Lang.sub(TPL_REPLACE_HTML, {
					html: text
				})
			);

			newElement.replace(replaceContainer);

			var nextElement = newElement.getNext();

			if (nextElement) {
				var containerAscendant = instance._getContainerAscendant(
					prevTriggerPosition.container
				);

				var updateWalker = instance._getWalker(
					containerAscendant,
					nextElement
				);

				var node = updateWalker.next();

				var removeNodes = [];

				while (node) {
					var nodeText = node.getText();

					var spaceIndex = nodeText.indexOf(STR_SPACE);

					if (spaceIndex !== -1) {
						node.setText(nodeText.substring(spaceIndex));

						updateWalker.end();
					}
					else {
						removeNodes.push(node);
					}

					node = updateWalker.next();
				}

				AArray.invoke(removeNodes, 'remove');

				nextElement = newElement.getNext();
			}

			if (!nextElement) {
				nextElement = new CKEDITOR.dom.element('span');

				nextElement.appendText(STR_SPACE);
				nextElement.insertAfter(newElement);
			}

			return {
				index: 1,
				node: nextElement
			};
		},

		_setCaretIndex(node, caretIndex) {
			var instance = this;

			var editor = instance.get(STR_EDITOR);

			var caretRange = editor.createRange();

			caretRange.setStart(node, caretIndex);
			caretRange.setEnd(node, caretIndex);

			editor.getSelection().selectRanges([caretRange]);
			editor.focus();
		},

		_updateValue(value) {
			var instance = this;

			var prevTriggerPosition = instance._getPrevTriggerPosition();

			var caretPosition = instance._replaceHtml(
				value,
				prevTriggerPosition
			);

			instance._setCaretIndex(caretPosition.node, caretPosition.index);

			var editor = instance.get('editor');

			editor.fire('saveSnapshot');
		},

		initializer() {
			var instance = this;

			instance._bindUIACCKEditor();
		}
	};

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

	Liferay.AutoCompleteCKEditor = A.Base.create(
		'liferayautocompleteckeditor',
		A.AutoComplete,
		[Liferay.AutoCompleteInputBase, AutoCompleteCKEditor],
		{},
		{
			CSS_PREFIX: A.ClassNameManager.getClassName('aclist')
		}
	);
})();
