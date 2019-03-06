import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import './FloatingToolbarMappingPanelDelegateTemplate.soy';
import {encodeAssetId} from '../../../utils/FragmentsEditorIdUtils.es';
import getConnectedComponent from '../../../store/ConnectedComponent.es';
import {setIn} from '../../../utils/FragmentsEditorUpdateUtils.es';
import templates from './FloatingToolbarMappingPanel.soy';

const MAPPING_SOURCE_SPECIFIC_CONTENT_KEY = 'specific_content';

const MAPPING_SOURCE_SUBTYPE_KEY = 'subtype';

/**
 * FloatingToolbarMappingPanel
 */
class FloatingToolbarMappingPanel extends Component {

	/**
	 * Get mapping sources
	 * @param {!string} subtypeLabel
	 * @private
	 * @static
	 * @review
	 */
	static _getMappingSources(subtypeLabel) {
		return [
			{
				id: MAPPING_SOURCE_SUBTYPE_KEY,
				label: Liferay.Util.sub(
					Liferay.Language.get('x-default'),
					subtypeLabel
				)
			},
			{
				id: MAPPING_SOURCE_SPECIFIC_CONTENT_KEY,
				label: Liferay.Language.get('specific-content')
			}
		];
	}

	/**
	 * @inheritdoc
	 * @param {object} state
	 * @return {object}
	 * @review
	 */
	prepareStateForRender(state) {
		const encodedMappedAssetEntries = this._mappedAssetEntries.map(
			encodeAssetId
		);

		let nextState = setIn(
			state,
			['_encodedMappedAssetEntries'],
			encodedMappedAssetEntries
		);

		nextState = setIn(
			nextState,
			['_specificContentKey'],
			MAPPING_SOURCE_SPECIFIC_CONTENT_KEY
		);

		if (this.selectedMappingTypes.subtype) {
			nextState = setIn(
				nextState,
				['_mappingSources'],
				FloatingToolbarMappingPanel._getMappingSources(
					this.selectedMappingTypes.subtype.label
				)
			);
		}

		return nextState;
	}

	/**
	 * Handle source option change
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleAssetOptionChange(event) {
		this._mappedAssetEntryEncodedId = event.delegateTarget.value;
	}

	/**
	 * Handle browse all button click
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleBrowseAllAssetsButtonClick(event) {}

	/**
	 * Handle field option change
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleFieldOptionChange(event) {
		this._mappingField = event.delegateTarget.value;
	}

	/**
	 * Handle source option change
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleSourceOptionChange(event) {
		this._selectedSourceId = event.delegateTarget.value;
	}

	/**
	 * Handle unmap button click
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleUnmapButtonClick(event) {}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FloatingToolbarMappingPanel.STATE = {

	/**
	 * @default undefined
	 * @memberof FloatingToolbarMappingPanel
	 * @review
	 * @type {object}
	 */
	store: Config
		.object()
		.value(null),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarMappingPanel
	 * @review
	 * @type {!object}
	 */
	item: Config
		.required(),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarMappingPanel
	 * @review
	 * @type {!string}
	 */
	itemId: Config
		.string()
		.required(),

	/**
	 * @default undefined
	 * @memberOf FloatingToolbarMappingPanel
	 * @private
	 * @review
	 * @type {object[]}
	 */
	_fields: Config
		.array()
		.internal()
		.value([]),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarMappingPanel
	 * @review
	 * @type {!string}
	 */
	_mappedAssetEntryEncodedId: Config
		.string(),

	/**
	 * @default undefined
	 * @memberOf FloatingToolbarMappingPanel
	 * @private
	 * @review
	 * @type {object[]}
	 */
	_mappedAssetEntries: Config
		.array()
		.internal()

	/**
	 * @default undefined
	 * @memberof FloatingToolbarMappingPanel
	 * @review
	 * @type {!string}
	 */
	_mappedFieldId: Config
		.string(),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarMappingPanel
	 * @review
	 * @type {!string}
	 */
	_selectedSourceId: Config
		.oneOf([MAPPING_SOURCE_SPECIFIC_CONTENT_KEY, MAPPING_SOURCE_SUBTYPE_KEY])
		.value(MAPPING_SOURCE_SUBTYPE_KEY)
};

const ConnectedFloatingToolbarMappingPanel = getConnectedComponent(
	FloatingToolbarMappingPanel,
	[
		'mappingFieldsURL',
		'selectedMappingTypes'
	]
);

Soy.register(ConnectedFloatingToolbarMappingPanel, templates);

export {ConnectedFloatingToolbarMappingPanel, FloatingToolbarMappingPanel};
export default ConnectedFloatingToolbarMappingPanel;