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
	class DragEvent {
		constructor(document) {
			this.document = document;

			this.events = {
				keydown: this.keydown.bind(this),
				mousemove: this.mousemove.bind(this),
				mouseup: this.mouseup.bind(this),
			};
		}

		start(event) {
			event.preventDefault();
			event.stopPropagation();

			this.target = event.target;

			this.className = this.target.className;

			this.startPos = {
				x: event.clientX,
				y: event.clientY,
			};

			this.update(event);

			this.document.addEventListener(
				'keydown',
				this.events.keydown,
				false
			);
			this.document.addEventListener(
				'mousemove',
				this.events.mousemove,
				false
			);
			this.document.addEventListener(
				'mouseup',
				this.events.mouseup,
				false
			);

			this.document.body.classList.add(`dragging-${this.className}`);

			if (typeof this.onStart === 'function') {
				this.onStart();
			}
		}

		update(event) {
			this.currentPos = {
				x: event.clientX,
				y: event.clientY,
			};

			this.delta = {
				x: event.clientX - this.startPos.x,
				y: event.clientY - this.startPos.y,
			};

			this.keys = {
				alt: event.altKey,
				ctrl: event.ctrlKey,
				shift: event.shiftKey,
			};
		}

		mousemove(event) {
			this.update(event);

			if (typeof this.onDrag === 'function') {
				this.onDrag();
			}

			if (event.which === 0) {
				this.mouseup(event);
			}
		}

		keydown(event) {
			if (event.keyCode === 27) {
				this.release();
			}
		}

		mouseup(event) {
			this.update(event);

			this.release();

			if (typeof this.onComplete === 'function') {
				this.onComplete();
			}
		}

		release() {
			this.document.body.classList.remove(`dragging-${this.className}`);

			this.document.removeEventListener(
				'keydown',
				this.events.keydown,
				false
			);
			this.document.removeEventListener(
				'mousemove',
				this.events.mousemove,
				false
			);
			this.document.removeEventListener(
				'mouseup',
				this.events.mouseup,
				false
			);

			if (typeof this.onRelease === 'function') {
				this.onRelease();
			}
		}
	}

	Liferay.DragEventCKEditor = DragEvent;
})();
