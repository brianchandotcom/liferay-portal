import DragEvent from './DragEvent.es';

const IMAGE_HANDLES = {
	scale: ['tl', 'tr', 'bl', 'br']
};

const POSITION_ELEMENT_FN = {
	bl(handle, left, top, box) {
		positionElement(handle, -3 + left, box.height - 4 + top);
	},
	br(handle, left, top, box) {
		positionElement(handle, box.width - 4 + left, box.height - 4 + top);
	},
	rm(handle, left, top, box) {
		positionElement(handle, box.width - 4 + left, Math.round(box.height / 2) - 3 + top);
	},
	tl(handle, left, top, box) {
		positionElement(handle, left - 3, top - 3);
	},
	tr(handle, left, top, box) {
		positionElement(handle, box.width - 4 + left, -3 + top);
	}
};

const positionElement = (el, left, top) => {
	el.style.left = `${left}px`;
	el.style.top = `${top}px`;
};

const getBoundingBox = (window, el) => {
	const rect = el.getBoundingClientRect();

	return {
		height: rect.height,
		left: rect.left + window.pageXOffset,
		top: rect.top + window.pageYOffset,
		width: rect.width
	};
};

class Resizer {
	constructor(editor, cfg = {}) {
		this.editor = editor;
		this.window = editor.window ? editor.window.$ : window;
		this.document = editor.document ? editor.document.$ : document;
		this.cfg = cfg;

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

		IMAGE_HANDLES[this.cfg.imageScaleResize].forEach(
			(handleName, index) => {
				this.handles[handleName] = this.createHandle(handleName);
			}
		);

		const keys = Object.keys(this.handles);
		for (let key of keys) {
			this.container.appendChild(this.handles[key]);
		}
	}

	createHandle(name) {
		const el = this.document.createElement('i');
		el.classList.add(name);
		return el;
	}

	isHandle(el) {
		const keys = Object.keys(this.handles);
		let result = false;
		for (let key of keys) {
			if (this.handles[key] === el) {
				result = true;
			}
		}
		return result;
	}

	show(el) {
		this.el = el;

		this.box = getBoundingBox(this.window, this.el);

		positionElement(this.container, this.box.left, this.box.top);

		this.document.body.appendChild(this.container);
		this.el.classList.add('ckimgrsz');
		this.showHandles();
	}

	hide() {
		const elements = this.document.getElementsByClassName('ckimgrsz');

		for (let element of elements) {
			element.classList.remove('ckimgrsz');
		}

		this.hideHandles();

		if (this.container.parentNode) {
			this.container.parentNode.removeChild(this.container);
		}
	}

	initDrag(event) {
		if (event.button !== 0) {

			// right-click or middle-click

			return;
		}

		const drag = new DragEvent(this.window, this.document);

		drag.onStart = () => {
			this.showPreview();
			this.isDragging = true;
			this.editor.getSelection().lock();
		};

		drag.onDrag = () => {
			this.calculateSize(drag);
			this.updatePreview();

			const box = this.previewBox;

			this.updateHandles(box, box.left, box.top);
		};

		drag.onRelease = () => {
			this.isDragging = false;
			this.hidePreview();
			this.hide();
			this.editor.getSelection().unlock();

			// Save an undo snapshot before the image is permanently changed

			this.editor.fire('saveSnapshot');
		};

		drag.onComplete = () => {
			this.resizeComplete();

			// Save another snapshot after the image is changed

			this.editor.fire('saveSnapshot');
		};

		drag.start(event);
	}

	updateHandles(box, left = 0, top = 0) {
		const keys = Object.keys(this.handles);

		for (let key of keys) {
			POSITION_ELEMENT_FN[key](this.handles[key], left, top, box);
		}
	}

	showHandles() {
		this.updateHandles(this.box);

		const keys = Object.keys(this.handles);

		for (let key of keys) {
			this.handles[key].style.display = 'block';
		}
	}

	hideHandles() {
		const keys = Object.keys(this.handles);

		for (let key of keys) {
			this.handles[key].style.display = 'none';
		}
	}

	showPreview() {
		this.calculateSize();
		this.updatePreview();
		this.preview.style.display = 'block';
	}

	updatePreview() {
		positionElement(this.preview, this.previewBox.left, this.previewBox.top);

		this.preview.style.width = `${this.previewBox.width}px`;
		this.preview.style.height = `${this.previewBox.height}px`;
	}

	hidePreview() {
		const box = getBoundingBox(this.window, this.preview);

		this.result = {
			height: box.height,
			width: box.width
		};

		this.preview.style.display = 'none';
	}

	calculateSize(data) {
		this.previewBox = {
			height: this.box.height,
			left: 0,
			top: 0,
			width: this.box.width
		};

		if (!data) {
			return;
		}

		const className = data.target.className;

		if (className.indexOf('r') >= 0) {
			this.previewBox.width = Math.max(32, this.box.width + data.delta.x);
		}
		if (className.indexOf('b') >= 0) {
			this.previewBox.height = Math.max(32, this.box.height + data.delta.y);
		}
		if (className.indexOf('l') >= 0) {
			this.previewBox.width = Math.max(32, this.box.width - data.delta.x);
		}
		if (className.indexOf('t') >= 0) {
			this.previewBox.height = Math.max(32, this.box.height - data.delta.y);
		}

		// If dragging corner, enforce aspect ratio (unless shift key is being held)

		if (className.indexOf('m') < 0 && !data.keys.shift) {
			const ratio = this.box.width / this.box.height;

			if (this.previewBox.width / this.previewBox.height > ratio) {
				this.previewBox.height = Math.round(this.previewBox.width / ratio);
			}
			else {
				this.previewBox.width = Math.round(this.previewBox.height * ratio);
			}
		}

		// Recalculate left or top position

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

export {Resizer};
export default Resizer;