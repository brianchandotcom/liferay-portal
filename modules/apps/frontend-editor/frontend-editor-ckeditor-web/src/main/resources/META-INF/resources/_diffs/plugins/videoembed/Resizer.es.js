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

(function () {
	const IMAGE_HANDLES = ['tl', 'tr', 'bl', 'br'];

	const positionElement = (element, left, top) => {
		element.style.left = `${left}px`;
		element.style.top = `${top}px`;
	};

	const POSITION_ELEMENT_FN = {
		bl(handle, left, top, box) {
			positionElement(handle, -3 + left, box.height - 4 + top);
		},
		br(handle, left, top, box) {
			positionElement(handle, box.width - 4 + left, box.height - 4 + top);
		},
		rm(handle, left, top, box) {
			positionElement(
				handle,
				box.width - 4 + left,
				Math.round(box.height / 2) - 3 + top
			);
		},
		tl(handle, left, top, _box) {
			positionElement(handle, left - 3, top - 3);
		},
		tr(handle, left, top, box) {
			positionElement(handle, box.width - 4 + left, -3 + top);
		},
	};

	const getBoundingBox = (window, element) => {
		const rect = element.getBoundingClientRect();

		return {
			height: rect.height,
			left: rect.left + window.pageXOffset,
			top: rect.top + window.pageYOffset,
			width: rect.width,
		};
	};

	class Resizer {
		constructor(editor, cfg = {}) {
			this.cfg = cfg;
			this.editor = editor;

			this.document = editor.document ? editor.document.$ : document;
			this.window = editor.window ? editor.window.$ : window;

			this.box = null;
			this.container = null;
			this.handles = {};
			this.preview = null;
			this.previewBox = null;
			this.result = null;

			this.init();
		}

		init() {
			this.container = this.document.createElement('div');
			this.container.id = 'ckimgrsz';

			this.preview = this.document.createElement('span');

			this.container.appendChild(this.preview);

			this.handles = {};

			IMAGE_HANDLES.forEach((handleName) => {
				this.handles[handleName] = this.createHandle(handleName);
			});

			const keys = Object.keys(this.handles);

			for (let i = 0; i < keys.length; i++) {
				this.container.appendChild(this.handles[keys[i]]);
			}
		}

		createHandle(name) {
			const element = this.document.createElement('i');

			element.classList.add(name);

			return element;
		}

		isHandle(element) {
			const keys = Object.keys(this.handles);

			let result = false;

			for (let i = 0; i < keys.length; i++) {
				if (this.handles[keys[i]] === element) {
					result = true;
				}
			}

			return result;
		}

		show(element) {
			const uiNode = this.editor.config.uiNode || this.document.body;

			this.el = element;

			this.box = getBoundingBox(this.window, this.el);

			positionElement(
				this.container,
				this.box.left,
				this.box.top + uiNode.scrollTop
			);

			uiNode.appendChild(this.container);

			this.el.classList.add('ckimgrsz');

			this.showHandles();
		}

		hide() {
			const elements = this.document.getElementsByClassName('ckimgrsz');

			for (let i = 0; i < elements.length; i++) {
				elements[i].classList.remove('ckimgrsz');
			}

			this.hideHandles();

			if (this.container.parentNode) {
				this.container.parentNode.removeChild(this.container);
			}
		}

		initDrag(event) {
			if (event.button !== 0) {
				return;
			}

			const drag = new Liferay.DragEventCKEditor(this.document);

			drag.onStart = () => {
				this.showPreview();

				this.isDragging = true;

				this.editor.getSelection().lock();
			};

			drag.onDrag = () => {
				this.calculateSize(drag);

				const body = this.editor.document.getBody();
				const editorBounds = body.$.getBoundingClientRect();

				if (this.previewBox.width >= editorBounds.width) {
					return;
				}

				this.updatePreview();

				const box = this.previewBox;

				this.updateHandles(box, box.left, box.top);
			};

			drag.onRelease = () => {
				this.hidePreview();

				this.isDragging = false;

				this.hide();

				this.editor.getSelection().unlock();
			};

			drag.onComplete = () => {
				this.resizeComplete();

				this.editor.fire('saveSnapshot');
			};

			drag.start(event);
		}

		updateHandles(box, left = 0, top = 0) {
			const keys = Object.keys(this.handles);

			for (let i = 0; i < keys.length; i++) {
				POSITION_ELEMENT_FN[keys[i]](
					this.handles[keys[i]],
					left,
					top,
					box
				);
			}
		}

		showHandles() {
			this.updateHandles(this.box);

			const keys = Object.keys(this.handles);

			for (let i = 0; i < keys.length; i++) {
				this.handles[keys[i]].style.display = 'block';
			}
		}

		hideHandles() {
			const keys = Object.keys(this.handles);

			for (let i = 0; i < keys.length; i++) {
				this.handles[keys[i]].style.display = 'none';
			}
		}

		showPreview() {
			this.calculateSize();

			this.updatePreview();

			this.preview.style.display = 'block';
		}

		updatePreview() {
			positionElement(
				this.preview,
				this.previewBox.left,
				this.previewBox.top
			);

			this.preview.style.height = `${this.previewBox.height}px`;
			this.preview.style.width = `${this.previewBox.width}px`;
		}

		hidePreview() {
			const box = getBoundingBox(this.window, this.preview);

			this.result = {
				height: Math.round(box.height),
				width: Math.round(box.width),
			};

			this.preview.style.display = 'none';
		}

		calculateSize(data) {
			this.previewBox = {
				height: this.box.height,
				left: 0,
				top: 0,
				width: this.box.width,
			};

			if (!data) {
				return;
			}

			const className = data.target.className;

			if (className.indexOf('r') >= 0) {
				this.previewBox.width = Math.max(
					32,
					this.box.width + data.delta.x
				);
			}

			if (className.indexOf('b') >= 0) {
				this.previewBox.height = Math.max(
					32,
					this.box.height + data.delta.y
				);
			}

			if (className.indexOf('l') >= 0) {
				this.previewBox.width = Math.max(
					32,
					this.box.width - data.delta.x
				);
			}

			if (className.indexOf('t') >= 0) {
				this.previewBox.height = Math.max(
					32,
					this.box.height - data.delta.y
				);
			}

			if (className.indexOf('m') < 0 && !data.keys.shift) {
				const ratio = this.box.width / this.box.height;

				if (this.previewBox.width / this.previewBox.height > ratio) {
					this.previewBox.height = Math.round(
						this.previewBox.width / ratio
					);
				}
				else {
					this.previewBox.width = Math.round(
						this.previewBox.height * ratio
					);
				}
			}

			if (className.indexOf('l') >= 0) {
				this.previewBox.left = this.box.width - this.previewBox.width;
			}

			if (className.indexOf('t') >= 0) {
				this.previewBox.top = this.box.height - this.previewBox.height;
			}
		}

		resizeComplete() {
			this.cfg.onComplete(this.el, this.result.width, this.result.height);
		}
	}

	Liferay.ResizerCKEditor = Resizer;
})();
