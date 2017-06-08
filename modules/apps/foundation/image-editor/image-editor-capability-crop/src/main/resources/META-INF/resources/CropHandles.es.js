import Component from 'metal-component/src/Component';
import Soy from 'metal-soy/src/Soy';

import async from 'metal/src/async/async';
import core from 'metal/src/core';
import Drag from 'metal-drag-drop/src/Drag';

import handlesTemplates from './CropHandles.soy';

class CropHandles extends Component {
	attached() {
		this.parentNode_ = this.element.parentNode;

		async.nextTick(() => {
			let imageContainer = this.getEditorImageContainer();

			this.element.classList.remove('hide');
			imageContainer.appendChild(this.element);

			this.selectionDrag_ = new Drag({
				constrain: '#' + this.imageContainerId,
				handles: this.element,
				sources: this.element
			});

			var resizer = this.element.querySelector('.resize-handle');

			this.sizeDrag_ = new Drag({
				constrain: '#' + this.imageContainerId,
				handles: resizer,
				sources: resizer
			});

			resizer.addEventListener('mousedown', (event) => event.stopPropagation());

			this.sizeDrag_.on(Drag.Events.DRAG, (data, event) => {
				this.element.style.width = data.relativeX + 'px';
				this.element.style.height = data.relativeY + 'px';
			});
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
	 * Injected helper to get the editor image container
	 * @type {Function}
	 */
	getEditorImageContainer: {
		validator: core.isFunction
	}
};

// Register component
Soy.register(CropHandles, handlesTemplates);

export default CropHandles;