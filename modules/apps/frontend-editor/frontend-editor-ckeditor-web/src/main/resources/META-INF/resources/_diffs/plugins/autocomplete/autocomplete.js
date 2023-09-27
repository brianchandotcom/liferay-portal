/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable @liferay/aui/no-one */

(function () {
	const A = AUI();

	// eslint-disable-next-line @liferay/aui/no-array
	const AArray = A.Array;
	const KeyMap = A.Event.KeyMap;
	const Lang = A.Lang;

	const BR_TAG = 'BR';

	const CSS_LFR_AC_CONTENT = 'lfr-ac-content';

	const STR_EDITOR = 'editor';

	const STR_SPACE = ' ';

	const TPL_REPLACE_HTML =
		'<span class="' + CSS_LFR_AC_CONTENT + '">{html}</span>';

	const AutoCompleteCKEditor = function () {};

	AutoCompleteCKEditor.ATTRS = {
		editor: {
			validator: Lang.isObject,
			writeOnce: true,
		},

		inputNode: {
			valueFn: '_getInputElement',
			writeOnce: true,
		},
	};

	AutoCompleteCKEditor.prototype = {
		_bindUIACCKEditor() {
			const instance = this;

			instance._processCaret = A.bind('_processCaretPosition', instance);

			instance._processCaretTask = A.debounce(instance._processCaret, 50);

			const editor = instance.get(STR_EDITOR);

			instance._eventHandles = [
				editor.on('key', A.bind('_onEditorKey', instance)),
			];

			editor.once('instanceReady', (event) => {
				const editorBody = A.one(event.editor.element.$);

				instance._eventHandles.push(
					editorBody.on(
						'mousedown',
						A.bind('soon', A, instance._processCaret)
					)
				);
			});
		},

		_getACPositionBase() {
			const instance = this;

			const inline = this.get(STR_EDITOR).editable().isInline();

			if (!instance._contentsContainer) {
				const inputElement = instance._getInputElement();

				instance._contentsContainer =
					inputElement.siblings('.cke').one('.cke_contents') ||
					inputElement;
			}

			return inline ? [0, 0] : instance._contentsContainer.getXY();
		},

		_getACPositionOffset() {
			const instance = this;

			const caretContainer = instance._getCaretContainer();

			const containerAscendantElement = instance._getContainerAscendant(
				caretContainer
			);

			const containerAscendantNode = A.one(containerAscendantElement.$);

			return [0, Lang.toInt(containerAscendantNode.getStyle('fontSize'))];
		},

		_getCaretContainer() {
			const instance = this;

			return instance._getCaretRange().startContainer;
		},

		_getCaretIndex() {
			const instance = this;

			const range = instance._getCaretRange();

			return {
				end: range.endOffset,
				start: range.startOffset,
			};
		},

		_getCaretOffset() {
			const instance = this;

			const editor = instance.get(STR_EDITOR);

			const bookmarks = editor.getSelection().createBookmarks();

			const bookmarkNode = A.one(bookmarks[0].startNode.$);

			bookmarkNode.setStyle('display', 'inline-block');

			const bookmarkXY = bookmarkNode.getXY();

			bookmarkNode.remove();

			return {
				x: bookmarkXY[0],
				y: bookmarkXY[1],
			};
		},

		_getCaretRange() {
			const instance = this;

			const editor = instance.get(STR_EDITOR);

			return editor.getSelection().getRanges()[0];
		},

		_getContainerAscendant(container, ascendant) {
			if (!ascendant) {
				ascendant = AutoCompleteCKEditor.CONTAINER_ASCENDANT;
			}

			return container.getAscendant(ascendant, true);
		},

		_getInputElement() {
			const instance = this;

			return A.one(instance.get(STR_EDITOR).element.$);
		},

		_getPrevTriggerPosition() {
			const instance = this;

			const caretContainer = instance._getCaretContainer();
			const caretIndex = instance._getCaretIndex();

			let query = caretContainer.getText().substring(0, caretIndex.start);

			let triggerContainer = caretContainer;

			let triggerIndex = -1;

			let trigger = null;

			const triggers = instance._getTriggers();

			AArray.each(triggers, (item) => {
				const triggerPosition = query.lastIndexOf(item);

				if (triggerPosition !== -1 && triggerPosition > triggerIndex) {
					trigger = item;
					triggerIndex = triggerPosition;
				}
			});

			// What follows is an algorithm to properly select the query
			// from the last detected trigger. Because of the HTML
			// structure, it's not as straightforward as a text search.
			//
			// If triggerIndex === -1, the trigger is not in the current
			// HTML node element. Thus, we walk the DOM tree upwards
			// until we find it, constructing the query as we visit the
			// nodes in the tree.
			//
			// If triggerIndex > 0, we found the trigger in a longer
			// text sequence.  We check if the char before the trigger
			// is a space (' ' or nbsp;) by checking that trimming
			// the char returns false or a filler char (\u200b) for
			// WebKit-based engines. If that's the case, we take the
			// substring from triggerPosition forward and discard the
			// rest of the text sequence.
			//
			// If triggerIndex === 0, the trigger is the first char in
			// the sequence which means it's already the right query and
			// has no additional characters.

			if (triggerIndex === -1) {
				const triggerWalker = instance._getWalker(triggerContainer);

				triggerWalker.guard = function (node) {
					let hasTrigger = false;

					if (
						node.type === CKEDITOR.NODE_TEXT &&
						node.$ !== caretContainer.$
					) {
						const nodeText = node.getText();

						AArray.each(triggers, (item) => {
							const triggerPosition = nodeText.lastIndexOf(item);

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
				(!query.charAt(triggerIndex - 1).trim() ||
					query.charAt(triggerIndex - 1) === '\u200b')
			) {
				query = query.substring(triggerIndex);
			}

			return {
				container: triggerContainer,
				index: triggerIndex,
				query,
				value: trigger,
			};
		},

		_getQuery() {
			const instance = this;

			const prevTriggerPosition = instance._getPrevTriggerPosition();

			let query = prevTriggerPosition.query;

			if (
				query &&
				prevTriggerPosition.container.$.lastElementChild &&
				prevTriggerPosition.container.$.lastElementChild.nodeName ===
					BR_TAG
			) {
				query = null;
			}

			const trigger = prevTriggerPosition.value;

			const res = instance._getRegExp().exec(query);

			let result;

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
			const instance = this;

			endContainer = endContainer || instance._getCaretContainer();

			startContainer =
				startContainer || instance._getContainerAscendant(endContainer);

			const range = new CKEDITOR.dom.range(startContainer);

			range.setStart(startContainer, 0);
			range.setEnd(endContainer, endContainer.getText().length);

			const walker = new CKEDITOR.dom.walker(range);

			return walker;
		},

		_isEmptySelection() {
			const instance = this;

			const editor = instance.get(STR_EDITOR);

			const selection = editor.getSelection();

			const ranges = selection.getRanges();

			const collapsedRange = ranges.length === 1 && ranges[0].collapsed;

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
				type: 'keydown',
			});
		},

		_onEditorKey(event) {
			const instance = this;
			const editor = instance.get(STR_EDITOR);

			if (editor.mode !== 'wysiwyg') {
				return;
			}

			if (instance._isEmptySelection()) {
				event = instance._normalizeCKEditorKeyEvent(event);

				const acVisible = instance.get('visible');

				if (
					acVisible &&
					KeyMap.isKeyInSet(event.keyCode, 'down', 'enter', 'up')
				) {
					const inlineEditor = editor.editable().isInline();

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
			const instance = this;

			const query = instance._getQuery();

			instance._processKeyUp(query);
		},

		_replaceHtml(text, prevTriggerPosition) {
			const instance = this;

			let replaceContainer = instance._getContainerAscendant(
				prevTriggerPosition.container,
				'span'
			);

			if (
				!replaceContainer ||
				!replaceContainer.hasClass('lfr-ac-content') ||
				prevTriggerPosition.value
			) {
				replaceContainer = prevTriggerPosition.container.split(
					prevTriggerPosition.index
				);
			}

			const newElement = CKEDITOR.dom.element.createFromHtml(
				Lang.sub(TPL_REPLACE_HTML, {
					html: text,
				})
			);

			newElement.replace(replaceContainer);

			let nextElement = newElement.getNext(function () {
				return (
					this.type !== CKEDITOR.NODE_TEXT || this.getText().trim()
				);
			});

			if (nextElement && nextElement.$.nodeName === BR_TAG) {
				nextElement = null;
			}

			if (nextElement) {
				const containerAscendant = instance._getContainerAscendant(
					prevTriggerPosition.container
				);

				const updateWalker = instance._getWalker(
					containerAscendant,
					nextElement
				);

				let node = updateWalker.next();

				const removeNodes = [];

				while (node) {
					const nodeText = node.getText();

					const spaceIndex = nodeText.indexOf(STR_SPACE);

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
				node: nextElement,
			};
		},

		_setCaretIndex(node, caretIndex) {
			const instance = this;

			const editor = instance.get(STR_EDITOR);

			const caretRange = editor.createRange();

			caretRange.setStart(node, caretIndex);
			caretRange.setEnd(node, caretIndex);

			editor.getSelection().selectRanges([caretRange]);
			editor.focus();
		},

		_updateValue(value) {
			const instance = this;

			const prevTriggerPosition = instance._getPrevTriggerPosition();

			const caretPosition = instance._replaceHtml(
				value,
				prevTriggerPosition
			);

			instance._setCaretIndex(caretPosition.node, caretPosition.index);

			const editor = instance.get('editor');

			editor.fire('saveSnapshot');
		},

		initializer() {
			const instance = this;

			instance._bindUIACCKEditor();
		},
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
		span: 1,
	};

	Liferay.AutoCompleteCKEditor = A.Base.create(
		'liferayautocompleteckeditor',
		A.AutoComplete,
		[Liferay.AutoCompleteInputBase, AutoCompleteCKEditor],
		{},
		{
			CSS_PREFIX: A.ClassNameManager.getClassName('aclist'),
		}
	);
})();
