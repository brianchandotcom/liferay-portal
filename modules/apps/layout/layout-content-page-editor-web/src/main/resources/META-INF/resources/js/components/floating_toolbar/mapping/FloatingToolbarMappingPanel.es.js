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
			['mappedAssetEntries'],
			nextState.mappedAssetEntries.map(encodeAssetId)
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
			nextState.item.editableValues.classNameId &&
			nextState.item.editableValues.classPK
		) {
			nextState = setIn(
				nextState,
				['item', 'editableValues', 'encodedId'],
				encodeAssetId(nextState.item.editableValues).encodedId
			);
		}

		return nextState;
	}

	/**
	 * @inheritdoc
	 * @param {boolean} firstRender
	 * @review
	 */
	rendered(firstRender) {
		if (firstRender) {
			this._selectedSourceTypeId = SOURCE_TYPE_IDS.content;

			if (
				this.item &&
				this.mappingFieldsURL &&
				!this.item.editableValues.classNameId
			) {
				this._selectedSourceTypeId = SOURCE_TYPE_IDS.structure;
			}

			this._loadFields();
		}
	}

	/**
	 * Clears editable values
	 * @private
	 * @review
	 */
	_clearEditableValues() {
		this._updateEditableValues('classNameId', '');
		this._updateEditableValues('classPK', '');
		this._updateEditableValues('fieldId', '');
		this._updateEditableValues('mappedField', '');
	}

	/**
	 * Clears fields
	 * @private
	 * @review
	 */
	_clearFields() {
		this._fields = [];
	}

	/**
	 * Handle source option change
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleAssetOptionChange(event) {
		if (event.delegateTarget.value) {
			const {
				classNameId,
				classPK
			} = decodeId(event.delegateTarget.value);

			this._updateEditableValues('classNameId', classNameId);
			this._updateEditableValues('classPK', classPK);
			this._updateEditableValues('fieldId', '');
		}
		else {
			this._clearEditableValues();
		}

		this.store.done(
			() => {
				this._loadFields();
			}
		);
	}

	/**
	 * Handle browse all button click
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleBrowseAllAssetsButtonClick(event) {
		this._loadFields();
	}

	/**
	 * Handle field option change
	 * @param {Event} event
	 * @private
	 * @review
	 */
	_handleFieldOptionChange(event) {
		const fieldId = event.delegateTarget.value;

		if (this._selectedSourceTypeId === SOURCE_TYPE_IDS.content) {
			this._updateEditableValues('fieldId', fieldId);
		}
		else if (this._selectedSourceTypeId === SOURCE_TYPE_IDS.structure) {
			this._updateEditableValues('mappedField', fieldId);
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

		this._clearEditableValues();
		this._loadFields();
	}

	/**
	 * Handle unmap button click
	 * @private
	 * @review
	 */
	_handleUnmapButtonClick() {
		this._clearEditableValues();

		this.store.done(
			() => {
				this._loadFields();
			}
		);
	}

	/**
	 * Load the list of fields
	 * @private
	 * @review
	 */
	_loadFields() {
		let promise;

		this._clearFields();

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
			this.item.editableValues.classNameId &&
			this.item.editableValues.classPK
		) {
			promise = this.fetch(
				this.getAssetMappingFieldsURL,
				{
					classNameId: this.item.editableValues.classNameId,
					classPK: this.item.editableValues.classPK
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
		else if (this._fields.length) {
			this._clearFields();
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
	 * @default []
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
	_selectedSourceTypeId: Config
		.oneOf(Object.values(SOURCE_TYPE_IDS))
		.internal()
};

const ConnectedFloatingToolbarMappingPanel = getConnectedComponent(
	FloatingToolbarMappingPanel,
	[
		'getAssetMappingFieldsURL',
		'mappedAssetEntries',
		'mappingFieldsURL',
		'portletNamespace',
		'selectedMappingTypes'
	]
);

Soy.register(ConnectedFloatingToolbarMappingPanel, templates);

export {ConnectedFloatingToolbarMappingPanel, FloatingToolbarMappingPanel};
export default ConnectedFloatingToolbarMappingPanel;