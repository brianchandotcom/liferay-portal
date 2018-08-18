import 'clay-button';
import {Config} from 'metal-state';
import {DragDrop} from 'metal-drag-drop';
import Component from 'metal-component';
import dom from 'metal-dom';
import LayoutSupport from './LayoutSupport.es';
import Soy from 'metal-soy';
import templates from './LayoutRenderer.soy.js';

/**
 * LayoutRenderer.
 * @extends Component
 */

class LayoutRenderer extends Component {
	static STATE = {

		/**
		 * @default 0
		 * @instance
		 * @memberof LayoutRenderer
		 * @type {?number}
		 */

		activePage: Config.number().value(0),

		/**
		 * @default false
		 * @instance
		 * @memberof LayoutRenderer
		 * @type {?bool}
		 */

		dragAndDropDisabled: Config.bool().value(false),

		/**
		 * @default false
		 * @instance
		 * @memberof LayoutRenderer
		 * @type {?bool}
		 */

		editable: Config.bool().value(false),

		/**
		 * @default grid
		 * @instance
		 * @memberof LayoutRenderer
		 * @type {?bool}
		 */

		modeRenderer: Config.oneOf(['grid', 'list']).value('grid'),

		/**
		 * @default []
		 * @instance
		 * @memberof LayoutRenderer
		 * @type {?array<object>}
		 */

		pages: Config.arrayOf(
			Config.shapeOf(
				{
					description: Config.string(),
					rows: Config.arrayOf(
						Config.shapeOf(
							{
								columns: Config.arrayOf(
									Config.shapeOf(
										{
											fields: Config.array(),
											size: Config.number()
										}
									)
								)
							}
						)
					),
					title: Config.string()
				}
			)
		).value([]),

		/**
		 * @default undefined
		 * @instance
		 * @memberof LayoutRenderer
		 * @type {!string}
		 */

		spritemap: Config.string().required()
	};

	/**
	 * @private
	 */

	_startDrag() {
		this._dragAndDrop = new DragDrop(
			{
				sources: '.ddm-drag',
				targets: '.ddm-target'
			}
		);

		this._dragAndDrop.on(
			DragDrop.Events.END,
			this._handleDragAndDropEnd.bind(this)
		);
	}

	/**
	 * @inheritDoc
	 */

	attached() {
		if (this.editable && !this.dragAndDropDisabled) {
			this._startDrag();
		}
	}

	/**
	 * @inheritDoc
	 */

	willReceiveState(nextState) {
		if (
			typeof nextState.pages !== 'undefined' &&
			nextState.pages.newVal.length &&
			this.editable &&
			!this.dragAndDropDisabled
		) {
			this._dragAndDrop.disposeInternal();
			this._startDrag();
		}
	}

	/**
	 * @param {!Object} data
	 * @private
	 */

	_handleFieldChanged(data) {
		this.emit('fieldEdited', data);
	}

	/**
	 * @param {!Event} event
	 * @private
	 */

	_handleSelectFieldFocused(event) {
		this._emitFieldClicked(
			event.delegateTarget.parentElement.parentElement,
			'edit'
		);
	}

	/**
	 * @param {!Event} event
	 * @private
	 */

	_handleOnClickResize() {}

	/**
	 * @param {!Event} event
	 * @private
	 */

	_handleDeleteButtonClicked(event) {
		const index = LayoutSupport.getIndexes(
			dom.closest(event.target, '.col-ddm')
		);

		this.emit(
			'deleteButtonClicked',
			{...index}
		);
	}

	/**
     * @param {!Event} event
     * @private
     */

	_handleDuplicateButtonClicked(event) {
		const index = LayoutSupport.getIndexes(dom.closest(event.target, '.col-ddm'));

		this.emit(
			'duplicateButtonClicked',
			{
				...index
			}
		);
	}

	/**
	 * @param {!Object} data
	 * @private
	 */

	_handleDragAndDropEnd(data) {
		if (!data.target) {
			return;
		}

		const sourceIndex = LayoutSupport.getIndexes(
			data.source.parentElement.parentElement
		);
		const targetIndex = LayoutSupport.getIndexes(data.target.parentElement);

		data.source.innerHTML = '';

		this._handleFieldMoved(
			{
				data,
				source: sourceIndex,
				target: targetIndex
			}
		);
	}

	/**
	 * @param {!Object} payload
	 * @private
	 */

	_handleFieldMoved({data, target, source}) {
		this.emit(
			'fieldMoved',
			{
				data,
				source,
				target
			}
		);
	}

	/**
	 * @param {!Event} event
	 * @param {!String} mode
	 * @private
	 */

	_emitFieldClicked(event, mode) {
		const index = LayoutSupport.getIndexes(event);

		this.emit(
			'fieldClicked',
			{
				...index,
				mode
			}
		);
	}
}

Soy.register(LayoutRenderer, templates);

export default LayoutRenderer;