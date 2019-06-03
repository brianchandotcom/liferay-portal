import State, {Config} from 'metal-state';
import {Store} from '../../store/store.es';

import FragmentEditableFieldTooltip from './FragmentEditableFieldTooltip.es';
import FragmentStyleEditors from '../fragment_processors/FragmentStyleEditors.es';
import {OPEN_MAPPING_FIELDS_DIALOG} from '../../actions/actions.es';

/**
 * Creates a Fragment Style Editor component.
 */
class FragmentStyleEditor extends State {
	/**
	 * @inheritDoc
	 */
	constructor(...args) {
		super(...args);

		this.disposeStyleTooltip = this.disposeStyleTooltip.bind(this);
		this._handleButtonClick = this._handleButtonClick.bind(this);
		this._handleChangeStyle = this._handleChangeStyle.bind(this);
		this._handleNodeClick = this._handleNodeClick.bind(this);

		this.syncType();

		this._onNodeClickHandler = document.addEventListener(
			'click',
			this._handleNodeClick
		);
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	dispose() {
		document.removeEventListener('click', this._handleNodeClick);

		super.dispose();
	}

	/**
	 * Callback executed when a type property changes. This updates the fragment
	 * style editor being used.
	 */
	syncType() {
		this._styleEditor = FragmentStyleEditors[this.type];
	}

	/**
	 * Handles click events for styled nodes.
	 * @param {Event} event The node click.
	 * @private
	 */
	_handleNodeClick(event) {
		if (
			this.node &&
			(event.target === this.node || this.node.contains(event.target))
		) {
			event.preventDefault();
			event.stopPropagation();

			if (this._tooltip) {
				this.disposeStyleTooltip();
			} else if (this._styleEditor) {
				this.emit('openTooltip');

				this._tooltip = new FragmentEditableFieldTooltip({
					alignElement: this.node,
					buttons: this._styleEditor.getButtons(this.showMapping),
					store: this.store
				});

				this._tooltip.on('buttonClick', this._handleButtonClick);
			}
		} else if (this._tooltip) {
			this.disposeStyleTooltip();
		}
	}

	/**
	 * Handles click events for tooltip buttons.
	 * @param {{buttonId: string}} event The tooltip button click.
	 */
	_handleButtonClick(event) {
		if (this._styleEditor) {
			this._styleEditor.init(
				event.buttonId,
				this.node,
				this.portletNamespace,
				this.editorsOptions,
				this._handleChangeStyle,
				this.disposeStyleTooltip
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
			this.store.dispatch({
				editableId: `${this.selectorText} ${event.name}`,
				editableType: event.type,
				fragmentEntryLinkId: this.fragmentEntryLinkId,
				mappedFieldId: '',
				type: OPEN_MAPPING_FIELDS_DIALOG
			});

			this.disposeStyleTooltip();
		} else {
			this.emit('styleChanged', {
				name: `${this.selectorText} ${event.name}`,
				value: event.value
			});
		}
	}

	/**
	 * Disposes of the style tooltip if it exists.
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
 * @static
 * @type {!Object}
 */
FragmentStyleEditor.STATE = {
	/**
	 * CSS rules text.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentStyleEditor
	 * @type {!string}
	 */
	cssText: Config.string().required(),

	/**
	 * Options that are sent to the editors.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentStyleEditor
	 * @type {!object}
	 */
	editorsOptions: Config.object().required(),

	/**
	 * Fragment entry link ID.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentStyleEditor
	 * @type {!string}
	 */
	fragmentEntryLinkId: Config.string().required(),

	/**
	 * Node to instantiate the style editor on.
	 * @instance
	 * @memberOf FragmentStyleEditor
	 * @type {HTMLElement}
	 */
	node: Config.internal().object(),

	/**
	 * Portlet namespace.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentStyleEditor
	 * @type {!string}
	 */
	portletNamespace: Config.string().required(),

	/**
	 * CSS selector text.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentStyleEditor
	 * @type {!string}
	 */
	selectorText: Config.string().required(),

	/**
	 * If <code>true</code>, the mapping is activated.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentStyleEditor
	 * @type {!boolean}
	 */
	showMapping: Config.bool().required(),

	/**
	 * Store instance.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentStyleEditor
	 * @type {Store}
	 */
	store: Config.instanceOf(Store),

	/**
	 * Style editor type.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentStyleEditor
	 * @type {!string}
	 */
	type: Config.string().required(),

	/**
	 * Style editor instance being used.
	 * @default null
	 * @instance
	 * @memberOf FragmentStyleEditor
	 * @type {FragmentStyleEditors}
	 */
	_styleEditor: Config.internal()
		.object()
		.value(null)
};

export {FragmentStyleEditor};
export default FragmentStyleEditor;
