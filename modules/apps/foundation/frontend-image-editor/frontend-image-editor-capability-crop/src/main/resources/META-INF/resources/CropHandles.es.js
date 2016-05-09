import Component from 'metal-component/src/Component';
import Soy from 'metal-soy/src/Soy';

import async from 'metal/src/async/async';
import core from 'metal/src/core';
import dom from 'metal-dom/src/dom';
import Drag from 'metal-drag-drop/src/Drag';

import handlesTemplates from './CropHandles.soy';

class CropHandles extends Component {
	attached() {
		this.parentNode_ = this.element.parentNode;

		async.nextTick(() => {
			let canvas = this.getEditorCanvas();

			this.element.style.width = canvas.offsetWidth + 12 + 'px';
			this.element.style.height = canvas.offsetHeight + 12 + 'px';
			this.element.style.top = canvas.offsetTop - 6 + 'px';
			this.element.style.left = canvas.offsetLeft - 6 + 'px';

			this.selectionDrag_ = new Drag({
				constrain: canvas,
				handles: this.element,
				sources: this.element
			});

			var resizer = this.element.querySelector('.resize-handle');

			this.sizeDrag_ = new Drag({
				constrain: canvas,
				handles: resizer,
				sources: resizer
			});

			resizer.addEventListener('mousedown', (event) => event.stopPropagation());

			this.sizeDrag_.on(Drag.Events.DRAG, (data, event) => {
				this.element.style.width = data.relativeX + 12 + 'px';
				this.element.style.height = data.relativeY + 12 + 'px';
			});

			dom.removeClasses(this.element, 'hide');
			dom.append(canvas.parentElement, this.element);
		});
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */
CropHandles.STATE = {
	/**
	 * Injected helper to get the editor canvas element
	 * @type {Function}
	 */
	getEditorCanvas: {
		validator: core.isFunction
	}
};

// Register component
Soy.register(CropHandles, handlesTemplates);

export default CropHandles;