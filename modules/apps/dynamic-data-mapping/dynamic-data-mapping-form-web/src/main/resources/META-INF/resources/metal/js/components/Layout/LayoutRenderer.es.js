import {Config} from 'metal-state';
import {DragDrop, Drag} from 'metal-drag-drop';
import ClayButton from 'clay-button';
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
         * @default []
         * @instance
         * @memberof LayoutRenderer
         * @type {?array<object>}
         */
        pages: Config.arrayOf(
            Config.shapeOf({
                title: Config.string(),
                description: Config.string(),
                rows: Config.arrayOf(Config.shapeOf({
                    columns: Config.arrayOf(Config.shapeOf({
                        fields: Config.array(),
                        size: Config.number(),
                    }))
                }))
            })
        ).value([]),

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
        disabledDragAndDrop: Config.bool().value(false),

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
         * @default undefined
         * @instance
         * @memberof LayoutRenderer
         * @type {!string}
         */
        spritemap: Config.string().required(),
    }

    /**
     * @private
     */
    _startDrag() {
        this._dragAndDrop = new DragDrop({
            sources: '.ddm-drag',
            targets: '.ddm-target',
        });

        this._dragAndDrop.on(DragDrop.Events.END, this._handleDragAndDropEnd.bind(this));
    }

    /**
	 * @inheritDoc
	 */
    attached() {
        if (this.editable && !this.disabledDragAndDrop) {
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
            !this.disabledDragAndDrop
        ) {
            this._dragAndDrop.disposeInternal();
            this._startDrag();
        }
    }

    /**
     * @param {!Object} data
     * @private
     */
    _handleFieldChange(data) {
        this.emit('fieldEdit', data);
    }
    
    /**
     * @param {!Event} event
     * @private
     */
    _handleFocusSelectField(event) {
        this._emitFieldClicked(event.delegateTarget.parentElement.parentElement, 'edit');
    }

    /**
     * @param {!Event} event
     * @private
     */
    _handleOnClickResize(event) {
        // TODO:
        // Logic to resize field...
        // const handle = event.target;
        // const colNode = dom.closest(event, '.col-ddm');
        // 
        // dom.on(colNode, 'mousemove', this._handleMouseMove.bind(this, colNode));
        // dom.on(handle, 'mousedown', this._handleMouseMove.bind(this, handle));
    }

    /**
     * @param {!Event} event
     * @private
     */
    _handleDeleteButtonClicked(event) {
        const index = LayoutSupport.getIndexes(dom.closest(event.target, '.col-ddm'));

        this.emit('deleteButtonClicked', {
            ...index
        });
    }

    /**
     * @param {!Event} event
     * @param {!Object} data
     * @private
     */
    _handleDragAndDropEnd(data, event) {
        if (!data.target) {
            return;
        }

        const indexTarget = LayoutSupport.getIndexes(data.target.parentElement);
        const indexSource = LayoutSupport.getIndexes(data.source.parentElement.parentElement);
        
        data.source.innerHTML = '';

        this._handleFieldMove({
            data,
            target: indexTarget,
            source: indexSource,
        });
    }

    /**
     * @param {!Object}
     * @private
     */
    _handleFieldMove({data, target, source}) {
        this.emit('fieldMove', {
            target,
            source,
            data,
        });
    }

    /**
     * @param {!Event} event
     * @param {!String} mode
     * @private
     */
    _emitFieldClicked(event, mode) {
        const index = LayoutSupport.getIndexes(event);

        this.emit('fieldClicked', {
            ...index,
            mode,
        });
    }
}

Soy.register(LayoutRenderer, templates);

export default LayoutRenderer;