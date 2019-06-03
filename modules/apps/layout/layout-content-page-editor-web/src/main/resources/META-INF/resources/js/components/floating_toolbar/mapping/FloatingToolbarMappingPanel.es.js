import 'clay-dropdown';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import Soy, {Config} from 'metal-soy';

import './FloatingToolbarMappingPanelDelegateTemplate.soy';
import {ADD_MAPPED_ASSET_ENTRY} from '../../../actions/actions.es';
import {COMPATIBLE_TYPES} from '../../../utils/constants';
import {encodeAssetId} from '../../../utils/FragmentsEditorIdUtils.es';
import {openAssetBrowser} from '../../../utils/FragmentsEditorDialogUtils';
import {setIn} from '../../../utils/FragmentsEditorUpdateUtils.es';
import {updateEditableValuesAction} from '../../../actions/updateEditableValue.es';
import getConnectedComponent from '../../../store/ConnectedComponent.es';
import templates from './FloatingToolbarMappingPanel.soy';

const SOURCE_TYPE_IDS = {
	content: 'specific_content',
	structure: 'structure'
};

/**
 * FloatingToolbarMappingPanel
 */
class FloatingToolbarMappingPanel extends PortletBase {
	/**
	 * @param {string} subtypeLabel
	 * @return {Array<{id: string, label: string}>} Source types
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

		nextState = setIn(nextState, ['_sourceTypeIds'], SOURCE_TYPE_IDS);

		if (
			nextState.mappingFieldsURL &&
			nextState.selectedMappingTypes &&
			nextState.selectedMappingTypes.type
		) {
			nextState = setIn(
				nextState,
				['_sourceTypes'],
				FloatingToolbarMappingPanel._getSourceTypes(
					nextState.selectedMappingTypes.subtype
						? nextState.selectedMappingTypes.subtype.label
						: nextState.selectedMappingTypes.type.label
				)
			);
		}

		if (
			nextState.mappedAssetEntries &&
			nextState.item.editableValues.classNameId &&
			nextState.item.editableValues.classPK
		) {
			const mappedAssetEntry = nextState.mappedAssetEntries.find(
				assetEntry =>
					nextState.item.editableValues.classNameId ===
						assetEntry.classNameId &&
					nextState.item.editableValues.classPK === assetEntry.classPK
			);

			if (mappedAssetEntry) {
				nextState = setIn(
					nextState,
					['item', 'editableValues', 'title'],
					mappedAssetEntry.title
				);

				nextState = setIn(
					nextState,
					['item', 'editableValues', 'encodedId'],
					mappedAssetEntry
				);
			}
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
		}
	}

	/**
	 * @param {{editableValues: object}} newItem
	 * @param {{editableValues: object}} [oldItem]
	 * @inheritdoc
	 * @review
	 */
	syncItem(newItem, oldItem) {
		if (!oldItem || newItem.editableValues !== oldItem.editableValues) {
			this._loadFields();
		}
	}

	/**
	 * Clears editable values
	 * @private
	 * @review
	 */
	_clearEditableValues() {
		this.store.dispatch(
			updateEditableValuesAction(
				this.item.fragmentEntryLinkId,
				this.item.editableId,
				[
					{
						content: '',
						editableValueId: 'classNameId'
					},
					{
						content: '',
						editableValueId: 'classPK'
					},
					{
						content: '',
						editableValueId: 'fieldId'
					},
					{
						content: '',
						editableValueId: 'mappedField'
					}
				]
			)
		);
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
	 * @param {MouseEvent} event
	 * @private
	 * @review
	 */
	_handleAssetBrowserLinkClick(event) {
		const {
			assetBrowserUrl,
			assetBrowserWindowTitle
		} = event.delegateTarget.dataset;

		openAssetBrowser({
			assetBrowserURL: assetBrowserUrl,
			callback: selectedAssetEntry => {
				this._selectAssetEntry(selectedAssetEntry);

				this.store.dispatch(
					Object.assign({}, selectedAssetEntry, {
						type: ADD_MAPPED_ASSET_ENTRY
					})
				);

				requestAnimationFrame(() => {
					this.refs.panel.focus();
				});
			},
			modalTitle: assetBrowserWindowTitle,
			portletNamespace: this.portletNamespace
		});
	}

	/**
	 * @param {MouseEvent} event
	 * @private
	 * @review
	 */
	_handleAssetEntryLinkClick(event) {
		const data = event.delegateTarget.dataset;

		this._selectAssetEntry({
			classNameId: data.classNameId,
			classPK: data.classPk
		});

		requestAnimationFrame(() => {
			this.refs.panel.focus();
		});
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
			this.store.dispatch(
				updateEditableValuesAction(
					this.item.fragmentEntryLinkId,
					this.item.editableId,
					[
						{
							content: fieldId,
							editableValueId: 'fieldId'
						}
					]
				)
			);
		} else if (this._selectedSourceTypeId === SOURCE_TYPE_IDS.structure) {
			this.store.dispatch(
				updateEditableValuesAction(
					this.item.fragmentEntryLinkId,
					this.item.editableId,
					[
						{
							content: fieldId,
							editableValueId: 'mappedField'
						}
					]
				)
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

		this._clearEditableValues();
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
			const data = {
				classNameId: this.selectedMappingTypes.type.id
			};

			if (this.selectedMappingTypes.subtype) {
				data.classTypeId = this.selectedMappingTypes.subtype.id;
			}

			promise = this.fetch(this.mappingFieldsURL, data);
		} else if (
			this._selectedSourceTypeId === SOURCE_TYPE_IDS.content &&
			this.item.editableValues.classNameId &&
			this.item.editableValues.classPK
		) {
			promise = this.fetch(this.getAssetMappingFieldsURL, {
				classNameId: this.item.editableValues.classNameId,
				classPK: this.item.editableValues.classPK
			});
		}

		if (promise) {
			promise
				.then(response => response.json())
				.then(response => {
					this._fields = response.filter(
						field =>
							COMPATIBLE_TYPES[this.item.type].indexOf(
								field.type
							) !== -1
					);
				});
		} else if (this._fields.length) {
			this._clearFields();
		}
	}

	/**
	 * @param {object} assetEntry
	 * @param {string} assetEntry.classNameId
	 * @param {string} assetEntry.classPK
	 * @private
	 * @review
	 */
	_selectAssetEntry(assetEntry) {
		this.store.dispatch(
			updateEditableValuesAction(
				this.item.fragmentEntryLinkId,
				this.item.editableId,
				[
					{
						content: assetEntry.classNameId,
						editableValueId: 'classNameId'
					},
					{
						content: assetEntry.classPK,
						editableValueId: 'classPK'
					},
					{
						content: '',
						editableValueId: 'fieldId'
					}
				]
			)
		);
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {object}
 */
FloatingToolbarMappingPanel.STATE = {
	/**
	 * @default undefined
	 * @memberof FloatingToolbarMappingPanel
	 * @review
	 * @type {object}
	 */
	item: Config.required(),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarMappingPanel
	 * @review
	 * @type {string}
	 */
	itemId: Config.string().required(),

	/**
	 * @default []
	 * @memberOf FloatingToolbarMappingPanel
	 * @private
	 * @review
	 * @type {object[]}
	 */
	_fields: Config.array()
		.internal()
		.value([]),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarMappingPanel
	 * @review
	 * @type {string}
	 */
	_selectedSourceTypeId: Config.oneOf(
		Object.values(SOURCE_TYPE_IDS)
	).internal()
};

const ConnectedFloatingToolbarMappingPanel = getConnectedComponent(
	FloatingToolbarMappingPanel,
	[
		'assetBrowserLinks',
		'getAssetMappingFieldsURL',
		'mappedAssetEntries',
		'mappingFieldsURL',
		'portletNamespace',
		'selectedMappingTypes',
		'spritemap'
	]
);

Soy.register(ConnectedFloatingToolbarMappingPanel, templates);

export {ConnectedFloatingToolbarMappingPanel, FloatingToolbarMappingPanel};
export default ConnectedFloatingToolbarMappingPanel;
