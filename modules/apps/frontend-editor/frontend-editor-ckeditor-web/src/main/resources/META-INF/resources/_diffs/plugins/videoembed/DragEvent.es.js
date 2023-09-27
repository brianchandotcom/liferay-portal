/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
