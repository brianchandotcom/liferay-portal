import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import Soy, {Config} from 'metal-soy';

import './FloatingToolbarMappingPanelDelegateTemplate.soy';
import {COMPATIBLE_TYPES} from '../../../utils/constants';
import {decodeId, encodeAssetId} from '../../../utils/FragmentsEditorIdUtils.es';
import getConnectedComponent from '../../../store/ConnectedComponent.es';
import {setIn} from '../../../utils/FragmentsEditorUpdateUtils.es';
import templates from './FloatingToolbarMappingPanel.soy';
import {UPDATE_EDITABLE_VALUE} from '../../../actions/actions.es';

const SOURCE_TYPE_IDS = {
	content: 'specific_content',
	structure: 'structure'
};

/**
 * FloatingToolbarMappingPanel
 */
class FloatingToolbarMappingPanel extends PortletBase {

	/**
	 * @param {!string} subtypeLabel
	 * @return {{id: string, label: string}[]} Source types
	 * @private
	 * @static
	 * @review
	 */
	static _getSourceTypes(subtypeLabel) {
		return [
			{
				id: SOURCE_TYPE_IDS.structure,
				label: Liferay.Util.sub(
					Liferay.Language.get('x-default'),
					subtypeLabel
				)
			},
			{
				id: SOURCE_TYPE_IDS.content,
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
		let nextState = state;

		nextState = setIn(
			nextState,
			['_assetEntries'],
			nextState._assetEntries.map(encodeAssetId)
		);

		nextState = setIn(
			nextState,
			['_sourceTypeIds'],
			SOURCE_TYPE_IDS
		);

		if (nextState.mappingFieldsURL) {
			nextState = setIn(
				nextState,
				['_sourceTypes'],
				FloatingToolbarMappingPanel._getSourceTypes(
					nextState.selectedMappingTypes.subtype.label
				)
			);
		}

		if (
			nextState.item.editableValues.assetEntryClassNameId &&
			nextState.item.editableValues.assetEntryClassPK
		) {
			nextState = setIn(
				nextState,
				['item', 'editableValues', 'assetEntryEncodedId'],
				encodeAssetId(nextState.item.editableValues).encodedId
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
	 * Clears editable values
	 * @private
	 * @review
	 */
	_clearEditableValues() {
		this._updateEditableValues('assetEntryClassNameId', '');
		this._updateEditableValues('assetEntryClassPK', '');
		this._updateEditableValues('assetEntryFieldId', '');
		this._updateEditableValues('mappedField', '');
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

		if (this._selectedSourceTypeId === SOURCE_TYPE_IDS.content) {
			this._updateEditableValues(
				'assetEntryFieldId',
				this._mappingField
			);
		}
		else if (this._selectedSourceTypeId === SOURCE_TYPE_IDS.structure) {
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
	_handleSourceTypeChange(event) {
		this._selectedSourceTypeId = event.delegateTarget.value;

		this._loadFields();
	}

	/**
	 * Handle unmap button click
	 * @private
	 * @review
	 */
	_handleUnmapButtonClick() {
		this._selectedSourceTypeId = this.mappingFieldsURL ?
			SOURCE_TYPE_IDS.structure :
			SOURCE_TYPE_IDS.content;

		this._clearEditableValues();
	}

	/**
	 * Load the list of fields
	 * @private
	 * @review
	 */
	_loadFields() {
		this._fields = [];

		let promise;

		if (this._selectedSourceTypeId === SOURCE_TYPE_IDS.structure) {
			promise = this.fetch(
				this.mappingFieldsURL,
				{
					classNameId: this.selectedMappingTypes.type.id,
					classTypeId: this.selectedMappingTypes.subtype.id
				}
			);
		}
		else if (
			this._selectedSourceTypeId === SOURCE_TYPE_IDS.content &&
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
	 * @default []
	 * @memberOf FloatingToolbarMappingPanel
	 * @private
	 * @review
	 * @type {object[]}
	 */
	_assetEntries: Config
		.array()
		.internal()
		.value([]),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarMappingPanel
	 * @review
	 * @type {!string}
	 */
	_selectedSourceTypeId: Config
		.oneOf(Object.values(SOURCE_TYPE_IDS))
		.value(SOURCE_TYPE_IDS.structure)
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