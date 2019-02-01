import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import '../floating_toolbar/background_color/FloatingToolbarBackgroundColorPanel.es';
import '../floating_toolbar/background_image/FloatingToolbarBackgroundImagePanel.es';
import '../floating_toolbar/spacing/FloatingToolbarSpacingPanel.es';
import './FragmentEntryLink.es';
import FloatingToolbar from '../floating_toolbar/FloatingToolbar.es';
import getConnectedComponent from '../../store/ConnectedComponent.es';
import templates from './FragmentEntryLinkListSection.soy';
import {FRAGMENTS_EDITOR_ITEM_TYPES} from '../../utils/constants';
import {shouldUpdatePureComponent} from '../../utils/FragmentsEditorComponentUtils.es';

/**
 * List of available panels
 * @review
 * @type {object[]}
 */
const FLOATING_TOOLBAR_PANELS = [
	{
		icon: 'format',
		panelId: 'background_color',
		title: Liferay.Language.get('background-color')
	},
	{
		icon: 'table',
		panelId: 'spacing',
		title: Liferay.Language.get('spacing')
	},
	{
		icon: 'picture',
		panelId: 'background_image',
		title: Liferay.Language.get('background-image')
	}
];

/**
 * FragmentEntryLinkListSection
 */
class FragmentEntryLinkListSection extends Component {

	/**
	 * @inheritdoc
	 * @review
	 */
	disposed() {
		this._disposeFloatingToolbar();
	}

	/**
	 * @inheritdoc
	 * @review
	 */
	rendered() {
		if (
			(this.rowId === this.activeItemId) &&
			(this.activeItemType === FRAGMENTS_EDITOR_ITEM_TYPES.section)
		) {
			this._createFloatingToolbar();
		}
		else {
			this._disposeFloatingToolbar();
		}
	}

	/**
	 * @inheritdoc
	 * @return {boolean}
	 * @review
	 */
	shouldUpdate(changes) {
		return shouldUpdatePureComponent(changes);
	}

	/**
	 * Creates a new instance of FloatingToolbar
	 * @private
	 * @review
	 */
	_createFloatingToolbar() {
		if (!this._floatingToolbar) {
			this._floatingToolbar = new FloatingToolbar(
				{
					item: this.row,
					itemId: this.rowId,
					panels: FLOATING_TOOLBAR_PANELS,
					portalElement: document.body,
					store: this.store
				}
			);
		}
	}

	/**
	 * Disposes an existing instance of FloatingToolbar
	 * @private
	 * @review
	 */
	_disposeFloatingToolbar() {
		if (this._floatingToolbar) {
			this._floatingToolbar.dispose();

			this._floatingToolbar = null;
		}
	}

}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FragmentEntryLinkListSection.STATE = {

	/**
	 * Internal FloatingToolbar instance.
	 * @default null
	 * @instance
	 * @memberOf FragmentEntryLinkListSection
	 * @review
	 * @type {object|null}
	 */
	_floatingToolbar: Config.internal()
		.value(null),

	/**
	 * Section row
	 * @default undefined
	 * @instance
	 * @memberof FragmentEntryLinkListSection
	 * @review
	 * @type {object}
	 */
	row: Config.object()
		.required(),

	/**
	 * Section row ID
	 * @default undefined
	 * @instance
	 * @memberof FragmentEntryLinkListSection
	 * @review
	 * @type {string}
	 */
	rowId: Config.string()
		.required()
};

const ConnectedFragmentEntryLinkListSection = getConnectedComponent(
	FragmentEntryLinkListSection,
	[
		'activeItemId',
		'activeItemType',
		'dropTargetBorder',
		'dropTargetItemId',
		'dropTargetItemType',
		'hoveredItemId',
		'hoveredItemType',
		'selectedMappingTypes',
		'spritemap'
	]
);

Soy.register(ConnectedFragmentEntryLinkListSection, templates);

export {ConnectedFragmentEntryLinkListSection, FragmentEntryLinkListSection};
export default FragmentEntryLinkListSection;