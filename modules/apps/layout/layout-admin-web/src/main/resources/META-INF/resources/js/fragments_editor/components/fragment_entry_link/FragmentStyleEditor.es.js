import State, {Config} from 'metal-state';
import dom from 'metal-dom';
import {Store} from '../../store/store.es';

import FragmentEditableFieldTooltip from './FragmentEditableFieldTooltip.es';
import FragmentStyleEditors from '../fragment_processors/FragmentStyleEditors.es';
import {OPEN_MAPPING_FIELDS_DIALOG} from '../../actions/actions.es';

/**
 * FragmentStyleEditor
 */
class FragmentStyleEditor extends State {

	/**
	 * @inheritDoc
	 * @review
	 */
	created() {
		this._onNodeClickHandler = dom.on(
			this.node,
			'click',
			this._handleNodeClick.bind(this)
		);
	}

	/**
	 * Handle styled node click event
	 * @param {Event} event
	 * @private
	 */
	_handleNodeClick(event) {
		if (event.target === this.node) {
			event.preventDefault();
			event.stopPropagation();

			if (this._tooltip) {
				this._tooltip.dispose();

				this._tooltip = null;
			}
			else {
				const styleEditor = FragmentStyleEditors[this.type];

				if (styleEditor) {
					this.emit('openTooltip');

					this._tooltip = new FragmentEditableFieldTooltip(
						{
							alignElement: this.node,
							buttons: styleEditor.getButtons(this.showMapping)
						}
					);

					this._tooltip.on('buttonClick', this._handleButtonClick.bind(this));
				}
			}
		}
	}

	/**
	 * Handles tooltip button click event.
	 * @param {MouseEvent} event
	 */
	_handleButtonClick(event) {
		const styleEditor = FragmentStyleEditors[this.type];

		if (styleEditor) {
			styleEditor.init(
				event.buttonId,
				this.node,
				this.portletNamespace,
				this.editorsOptions,
				this._handleChangeStyle.bind(this),
				this.disposeStyleTooltip.bind(this)
			);
		}
	}

	/**
	 * Handles a change style event.
	 * @param {Object} event
	 * @param {string} event.eventType
	 */
	_handleChangeStyle(event) {
		if (event.eventType === 'map') {
			this.store
				.dispatchAction(
					OPEN_MAPPING_FIELDS_DIALOG,
					{
						editableId: `${this.selectorText} ${event.name}`,
						editableType: event.type,
						fragmentEntryLinkId: this.fragmentEntryLinkId,
						mappedFieldId: ''
					}
				);

			this.disposeStyleTooltip();
		}
		else {
			this.emit(
				'styleChanged',
				{
					name: `${this.selectorText} ${event.name}`,
					value: event.value
				}
			);
		}
	}

	/**
	 * Disposes style tooltip if any.
	 */
	disposeStyleTooltip() {
		if (this._tooltip) {
			this._tooltip.dispose();

			this._tooltip = null;
		}
	}

}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FragmentStyleEditor.STATE = {

	/**
	 * CSS rules text
	 * @default undefined
	 * @instance
	 * @memberOf FragmentStyleEditor
	 * @review
	 * @type {!string}
	 */
	cssText: Config.string().required(),

	/**
	 * Set of options that are sent to the editors.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentStyleEditor
	 * @review
	 * @type {!object}
	 */
	editorsOptions: Config.object().required(),

	/**
	 * FragmentEntryLink id
	 * @default undefined
	 * @instance
	 * @memberOf FragmentStyleEditor
	 * @review
	 * @type {!string}
	 */
	fragmentEntryLinkId: Config.string().required(),

	/**
	 * Node to instantiate style editor on
	 * @default undefined
	 * @instance
	 * @memberOf FragmentStyleEditor
	 * @review
	 * @type {HTMLElement}
	 */
	node: Config.internal().object(),

	/**
	 * Portlet namespace
	 * @default undefined
	 * @instance
	 * @memberOf FragmentStyleEditor
	 * @review
	 * @type {!string}
	 */
	portletNamespace: Config.string().required(),

	/**
	 * CSS selector text
	 * @default undefined
	 * @instance
	 * @memberOf FragmentStyleEditor
	 * @review
	 * @type {!string}
	 */
	selectorText: Config.string().required(),

	/**
	 * True if mapping is activated
	 * @default undefined
	 * @instance
	 * @memberOf FragmentStyleEditor
	 * @review
	 * @type {!boolean}
	 */
	showMapping: Config.bool().required(),

	/**
	 * Store instance
	 * @default undefined
	 * @instance
	 * @memberOf FragmentStyleEditor
	 * @review
	 * @type {Store}
	 */
	store: Config.instanceOf(Store),

	/**
	 * Style editor type
	 * @default undefined
	 * @instance
	 * @memberOf FragmentStyleEditor
	 * @review
	 * @type {!string}
	 */
	type: Config.string().required()

};

export {FragmentStyleEditor};
export default FragmentStyleEditor;