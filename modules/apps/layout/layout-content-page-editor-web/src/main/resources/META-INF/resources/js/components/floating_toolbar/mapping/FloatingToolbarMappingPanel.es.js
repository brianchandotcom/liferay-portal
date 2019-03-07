import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import Soy, {Config} from 'metal-soy';

import './FloatingToolbarMappingPanelDelegateTemplate.soy';
import {COMPATIBLE_TYPES} from '../../../utils/constants';
import {decodeId, encodeAssetId} from '../../../utils/FragmentsEditorIdUtils.es';
import getConnectedComponent from '../../../store/ConnectedComponent.es';
import {setIn} from '../../../utils/FragmentsEditorUpdateUtils.es';
import templates from './FloatingToolbarMappingPanel.soy';
import {UPDATE_EDITABLE_VALUE} from '../../../actions/actions.es';

const MAPPING_SOURCE_SPECIFIC_CONTENT_KEY = 'specific_content';

const MAPPING_SOURCE_SUBTYPE_KEY = 'subtype';

/**
 * FloatingToolbarMappingPanel
 */
class FloatingToolbarMappingPanel extends PortletBase {

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

	rendered(firstRender) {
		if (firstRender) {
			this._loadFields();
		}
	}

	/**
	 * Handle source option change
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleAssetOptionChange(event) {
		this._mappedAssetEntryEncodedId = event.delegateTarget.value;

		this._loadFields();

		const asset = decodeId(this._mappedAssetEntryEncodedId);

		this._updateEditableValues(
			'assetEntryClassNameId',
			asset.assetEntryClassNameId
		);

		this._updateEditableValues(
			'assetEntryClassPK',
			asset.assetEntryClassPK
		);
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

		if (this._selectedSourceId === MAPPING_SOURCE_SPECIFIC_CONTENT_KEY) {
			this._updateEditableValues(
				'assetEntryFieldId',
				this._mappingField
			);
	}
		else if (this._selectedSourceId === MAPPING_SOURCE_SUBTYPE_KEY) {
			this._updateEditableValues(
				'mappedField',
				this._mappingField
			);
		}
	}

	/**
	 * Handle source option change
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleSourceOptionChange(event) {
		this._selectedSourceId = event.delegateTarget.value;

		this._loadFields();
	}

	/**
	 * Handle unmap button click
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleUnmapButtonClick(event) {}

	/**
	 * Load the list of fields
	 * @private
	 * @review
	 */
	_loadFields() {
		this._fields = [];

		let promise;

		if (this._selectedSourceId === MAPPING_SOURCE_SUBTYPE_KEY) {
			promise = this.fetch(
				this.mappingFieldsURL,
				{
					classNameId: this.selectedMappingTypes.type.id,
					classTypeId: this.selectedMappingTypes.subtype.id
				}
			);
		}
		else if (
			this._selectedSourceId === MAPPING_SOURCE_SPECIFIC_CONTENT_KEY &&
			this._mappedAssetEntryEncodedId
		) {
			const asset = decodeId(this._mappedAssetEntryEncodedId);

			promise = this.fetch(
				this.getAssetMappingFieldsURL,
				{
					classNameId: asset.assetEntryClassNameId,
					classPK: asset.assetEntryClassPK
				}
			);
		}

		if (promise) {
			promise
				.then(
					response => response.json()
				)
				.then(
					response => {
						this._fields = response.filter(
							field => COMPATIBLE_TYPES[this.item.type]
								.indexOf(field.type) !== -1
						);
					}
				);
		}
	}

	/**
	 * Dispatches action to update editable value
	 * @param {!string} key
	 * @param {!string} value
	 */
	_updateEditableValues(key, value) {
		this.store
			.dispatchAction(
				UPDATE_EDITABLE_VALUE,
				{
					editableId: this.itemId,
					editableValue: value,
					editableValueId: key,
					fragmentEntryLinkId: this.item.fragmentEntryLinkId
				}
			);
	}
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
		'getAssetMappingFieldsURL',
		'mappingFieldsURL',
		'portletNamespace',
		'selectedMappingTypes'
	]
);

Soy.register(ConnectedFloatingToolbarMappingPanel, templates);

export {ConnectedFloatingToolbarMappingPanel, FloatingToolbarMappingPanel};
export default ConnectedFloatingToolbarMappingPanel;