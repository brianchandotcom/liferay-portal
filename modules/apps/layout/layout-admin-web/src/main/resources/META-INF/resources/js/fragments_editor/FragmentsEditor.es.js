import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import './components/dialogs/SelectMappingDialog.es';
import './components/dialogs/SelectMappingTypeDialog.es';
import './components/fragment_entry_link/FragmentEntryLinkList.es';
import './components/sidebar/FragmentsEditorSidebar.es';
import './components/toolbar/FragmentsEditorToolbar.es';
import {
	addFragmentEntryLinkReducer,
	removeFragmentEntryLinkReducer
} from './reducers/fragments.es';
import {connect, Store} from './store/store.es';
import FragmentEntryLink from './components/fragment_entry_link/FragmentEntryLink.es';
import {INITIAL_STATE} from './store/state.es';
import {saveChangesReducer} from './reducers/changes.es';
import templates from './FragmentsEditor.soy';
import {translationStatusReducer} from './reducers/translations.es';
import {
	UPDATE_LAST_SAVE_DATE,
	UPDATE_SAVING_CHANGES_STATUS,
	UPDATE_TRANSLATION_STATUS
} from './actions/actions.es';
import {updateDragTargetReducer} from './reducers/placeholders.es';

/**
 * FragmentsEditor
 * @review
 */

class FragmentsEditor extends Component {

	/**
	 * @inheritDoc
	 * @review
	 */

	constructor(attributes) {
		super(attributes);

		this._store = new Store(
			attributes,
			[
				addFragmentEntryLinkReducer,
				removeFragmentEntryLinkReducer,
				saveChangesReducer,
				translationStatusReducer,
				updateDragTargetReducer
			]
		);

		connect(this, this._store);

		this._store.dispatchAction(UPDATE_TRANSLATION_STATUS);
	}

	/**
	 * Focus a fragmentEntryLink for a given ID
	 * @param {string} fragmentEntryLinkId
	 * @review
	 */

	_focusFragmentEntryLink(fragmentEntryLinkId) {
		this.refs.fragmentsEditorRender.focusFragmentEntryLink(
			fragmentEntryLinkId
		);
	}

	/**
	 * Returns a FragmentEntryLink instance for a fragmentEntryLinkId
	 * @param {string} fragmentEntryLinkId
	 * @private
	 * @return {FragmentEntryLink}
	 * @review
	 */

	_getFragmentEntryLinkComponent(fragmentEntryLinkId) {
		return this.refs.fragmentsEditorRender.refs[
			`fragmentEntryLink_${fragmentEntryLinkId}`
		];
	}

	/**
	 * Gets a fragmentEntryLink index for the given fragmentEntryLinkId
	 * @param {!string} fragmentEntryLinkId
	 * @private
	 * @return {number} fragmentEntryLink index or -1 if it's not found
	 */

	_getFragmentEntryLinkIndex(fragmentEntryLinkId) {
		return this.fragmentEntryLinks.indexOf(
			this.fragmentEntryLinks.find(
				fragmentEntryLink => (
					fragmentEntryLink.fragmentEntryLinkId ===
					fragmentEntryLinkId
				)
			)
		);
	}

	/**
	 * Callback executed everytime an editable field has been changed
	 * @param {{
	 *   editableId: !string,
	 *   fragmentEntryLinkId: !string,
	 *   value: !string
	 * }} data
	 * @private
	 * @review
	 */

	_handleEditableChanged(data) {
		this._setFragmentEntryLinkEditableValue(
			data.fragmentEntryLinkId,
			data.editableId,
			{[this.languageId || 'defaultValue']: data.value}
		);
	}

	/**
	 * Moves a fragment one position onto the specified direction.
	 * @param {!{
	 *   direction: !number,
	 *   fragmentEntryLinkId: !string
	 * }} data
	 * @private
	 * @review
	 */

	_handleFragmentMove(data) {
		const direction = data.direction;
		const index = this._getFragmentEntryLinkIndex(
			data.fragmentEntryLinkId
		);

		if (
			(direction === FragmentEntryLink.MOVE_DIRECTIONS.DOWN && index < this.fragmentEntryLinks.length - 1) ||
			(direction === FragmentEntryLink.MOVE_DIRECTIONS.UP && index > 0)
		) {
			const formData = new FormData();

			formData.append(
				`${this.portletNamespace}fragmentEntryLinkId1`,
				this.fragmentEntryLinks[index].fragmentEntryLinkId
			);

			formData.append(
				`${this.portletNamespace}fragmentEntryLinkId2`,
				this.fragmentEntryLinks[index + direction].fragmentEntryLinkId
			);

			fetch(
				this.updateFragmentEntryLinksURL,
				{
					body: formData,
					credentials: 'include',
					method: 'POST'
				}
			).then(
				() => {
					this._store
						.dispatchAction(
							UPDATE_LAST_SAVE_DATE,
							{
								lastSaveDate: new Date()
							}
						)
						.dispatchAction(
							UPDATE_SAVING_CHANGES_STATUS,
							{
								savingChanges: false
							}
						);
				}
			);

			this.fragmentEntryLinks = this._swapListElements(
				Array.prototype.slice.call(this.fragmentEntryLinks),
				index,
				index + direction
			);
		}
	}

	/**
	 * Callback executed when the sidebar should be hidden
	 * @private
	 * @review
	 */

	_handleHideContextualSidebar() {
		this._contextualSidebarVisible = false;
	}

	/**
	 * Callback executed when a mappeable fragment has been clicked
	 * @param {!{ fragmentEntryLinkId: !string, editableId: !string }} event
	 * @private
	 * @review
	 */

	_handleMappeableFieldClicked(event) {
		this._selectMappingDialogEditableId = event.editableId;
		this._selectMappingDialogEditableType = event.editableType;
		this._selectMappingDialogFragmentEntryLinkId = event.fragmentEntryLinkId;
		this._selectMappingDialogMappedFieldId = event.mappedFieldId;

		if (this.selectedMappingTypes && this.selectedMappingTypes.type) {
			this._selectMappingDialogVisible = true;
		}
		else {
			this._handleSelectAssetTypeButtonClick();
		}
	}

	/**
	 * Callback executed when a mappeable field has been selected for the
	 * given editable.
	 * @param {!{
	 *   editableId: !string,
	 *   fragmentEntryLinkId: !string,
	 *   key: !string
	 * }} event
	 * @private
	 */

	_handleMappeableFieldSelected(event) {
		this._setFragmentEntryLinkEditableValue(
			event.fragmentEntryLinkId,
			event.editableId,
			{mappedField: event.key}
		);
	}

	/**
	 * Callback executed when a mapping type has selected
	 * @param {{
	 * 	 mappingTypes: {
	 *     subtype: {
	 *   	  id: !string,
	 *   	  label: !string
	 *     },
	 *     type: {
	 *   	  id: !string,
	 *   	  label: !string
	 *     }
	 * 	 }
     * }} event
	 * @private
	 * @review
	 */

	_handleMappingTypeSelected(event) {
		this.selectedMappingTypes = event.mappingTypes;

		if (this._selectMappingDialogFragmentEntryLinkId &&
			this._selectMappingDialogEditableId) {

			this._selectMappingDialogVisible = true;
		}
	}

	/**
	 * Callback executed when the SelectMappingTypeDialog should be shown
	 * @review
	 */

	_handleSelectAssetTypeButtonClick() {
		this._selectMappingTypeDialogVisible = true;
	}

	/**
	 * Callback executed when the SelectMappingDialog visibility changes
	 * @param {{ newVal: boolean }} change
	 * @private
	 * @review
	 */

	_handleSelectMappingDialogVisibleChanged(change) {
		this._selectMappingDialogVisible = change.newVal;
	}

	/**
	 * Callback executed when the SelectMappingTypeDialog visibility changes
	 * @param {{ newVal: boolean }} change
	 * @private
	 * @review
	 */

	_handleSelectMappingTypeDialogVisibleChanged(change) {
		this._selectMappingTypeDialogVisible = change.newVal;
	}

	/**
	 * Callback executed when the sidebar visible state should be toggled
	 * @private
	 * @review
	 */

	_handleToggleContextualSidebarButtonClick() {
		this._contextualSidebarVisible = !this._contextualSidebarVisible;
	}

	/**
	 * Toggle highlightMapping attribute value
	 * @private
	 * @review
	 */

	_handleToggleHighlightMapping() {
		this._highlightMapping = !this._highlightMapping;
	}

	/**
	 * Callback executed when the translation language has changed
	 * @private
	 * @param {{languageId: string}} event
	 * @review
	 */

	_handleTranslationLanguageChange(event) {
		this.languageId = event.languageId;
	}

	/**
	 * Swap the positions of two fragmentEntryLinks
	 * @param {Array} list
	 * @param {number} indexA
	 * @param {number} indexB
	 * @private
	 */

	_swapListElements(list, indexA, indexB) {
		[list[indexA], list[indexB]] = [list[indexB], list[indexA]];

		return list;
	}

	/**
	 * Updates the given fragmentEntryLinkId editable value without mutating
	 * the fragmentEntryLinks property but creating a new array and
	 * synchronizing changes with server.
	 *
	 * @param {!string} fragmentEntryLinkId
	 * @param {!string} editableValueId
	 * @param {!object} editableValueContent
	 * @private
	 */

	_setFragmentEntryLinkEditableValue(
		fragmentEntryLinkId,
		editableValueId,
		editableValueContent
	) {
		const component = this._getFragmentEntryLinkComponent(
			fragmentEntryLinkId
		);

		const index = this._getFragmentEntryLinkIndex(
			fragmentEntryLinkId
		);

		if (component && index !== -1) {
			const newEditableValues = component.setEditableValue(
				editableValueId,
				editableValueContent
			);

			const newFragmentEntryLink = Object.assign(
				{},
				this.fragmentEntryLinks[index],
				{editableValues: newEditableValues}
			);

			const newFragmentEntryLinks = [...this.fragmentEntryLinks];
			newFragmentEntryLinks[index] = newFragmentEntryLink;

			this.fragmentEntryLinks = newFragmentEntryLinks;

			this._updateFragmentEntryLink(newFragmentEntryLink);
		}
	}

	/**
	 * Sends the change of a single fragment entry link to the server.
	 * @private
	 * @review
	 */

	_updateFragmentEntryLink(fragmentEntryLink) {
		if (!this.savingChanges) {
			this._store.dispatchAction(
				UPDATE_SAVING_CHANGES_STATUS,
				{
					savingChanges: true
				}
			);

			const formData = new FormData();

			formData.append(
				`${this.portletNamespace}fragmentEntryLinkId`,
				fragmentEntryLink.fragmentEntryLinkId
			);

			formData.append(
				`${this.portletNamespace}editableValues`,
				JSON.stringify(fragmentEntryLink.editableValues)
			);

			fetch(
				this.editFragmentEntryLinkURL,
				{
					body: formData,
					credentials: 'include',
					method: 'POST'
				}
			).then(
				() => {
					this._store
						.dispatchAction(
							UPDATE_LAST_SAVE_DATE,
							{
								lastSaveDate: new Date()
							}
						)
						.dispatchAction(
							UPDATE_SAVING_CHANGES_STATUS,
							{
								savingChanges: false
							}
						)
						.dispatchAction(
							UPDATE_TRANSLATION_STATUS
						);
				}
			);
		}
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */

FragmentsEditor.STATE = Object.assign(
	{

		/**
		 * List of available languages for translation.
		 * @default undefined
		 * @instance
		 * @memberOf FragmentsEditor
		 * @review
		 * @type {!object}
		 */

		availableLanguages: Config.object().required(),

		/**
		 * Default configurations for AlloyEditor instances.
		 * @default {}
		 * @instance
		 * @memberOf FragmentsEditor
		 * @review
		 * @type {object}
		 */

		defaultEditorConfigurations: Config.object().value({}),

		/**
		 * Default language id.
		 * @default undefined
		 * @instance
		 * @memberOf FragmentsEditor
		 * @review
		 * @type {!string}
		 */

		defaultLanguageId: Config.string().required(),

		/**
		 * URL for updating a distinct fragment entries of the editor.
		 * @default undefined
		 * @instance
		 * @memberOf FragmentsEditor
		 * @review
		 * @type {!string}
		 */

		editFragmentEntryLinkURL: Config.string().required(),

		/**
		 * Available entries that can be used, organized by collections.
		 * @default undefined
		 * @instance
		 * @memberOf FragmentsEditor
		 * @review
		 * @type {!Array<object>}
		 */

		fragmentCollections: Config.arrayOf(
			Config.shapeOf(
				{
					entries: Config.arrayOf(
						Config.shapeOf(
							{
								fragmentEntryId: Config.string().required(),
								name: Config.string().required()
							}
						)
					).required(),
					fragmentCollectionId: Config.string().required(),
					name: Config.string().required()
				}
			)
		).required(),

		/**
		 * URL for obtaining the class types of an asset
		 * created.
		 * @default undefined
		 * @instance
		 * @memberOf FragmentsEditor
		 * @review
		 * @type {!string}
		 */

		getAssetClassTypesURL: Config.string(),

		/**
		 * URL for obtaining the asset types for which asset display pages can be
		 * created.
		 * @default undefined
		 * @instance
		 * @memberOf FragmentsEditor
		 * @review
		 * @type {!string}
		 */

		getAssetDisplayContributorsURL: Config.string(),

		/**
		 * Image selector url
		 * @default undefined
		 * @instance
		 * @memberOf FragmentsEditor
		 * @review
		 * @type {!string}
		 */

		imageSelectorURL: Config.string().required(),

		/**
		 * URL for getting the list of mapping fields
		 * @default null
		 * @instance
		 * @memberOf FragmentsEditor
		 * @review
		 * @type {string}
		 */

		mappingFieldsURL: Config.string().value(null),

		/**
		 * Currently selected language id.
		 * @default undefined
		 * @instance
		 * @memberOf FragmentsEditor
		 * @review
		 * @type {!string}
		 */

		languageId: Config.string().required(),

		/**
		 *
		 * @default undefined
		 * @instance
		 * @memberOf FragmentsEditor
		 * @review
		 * @type {!string}
		 */

		publishLayoutPageTemplateEntryURL: Config.string(),

		/**
		 * URL for redirect.
		 * @default undefined
		 * @instance
		 * @memberOf FragmentsEditor
		 * @review
		 * @type {!string}
		 */

		redirectURL: Config.string().required(),

		/**
		 * Selected mapping types
		 * @default {}
		 * @instance
		 * @memberOf FragmentsEditor
		 * @review
		 * @type {{
		 *   subtype: {
		 *   	id: !string,
		 *   	label: !string
		 *   },
		 *   type: {
		 *   	id: !string,
		 *   	label: !string
		 *   }
		 * }}
		 */

		selectedMappingTypes: Config
			.shapeOf(
				{
					subtype: Config.shapeOf(
						{
							id: Config.string().required(),
							label: Config.string().required()
						}
					),
					type: Config.shapeOf(
						{
							id: Config.string().required(),
							label: Config.string().required()
						}
					)
				}
			)
			.value({}),

		/**
		 * Path of the available icons.
		 * @default undefined
		 * @instance
		 * @memberOf FragmentsEditor
		 * @review
		 * @type {!string}
		 */

		spritemap: Config.string().required(),

		/**
		 * URL for swapping to fragmentEntryLinks.
		 * @default undefined
		 * @instance
		 * @memberOf FragmentsEditor
		 * @review
		 * @type {!string}
		 */

		updateFragmentEntryLinksURL: Config.string().required(),

		/**
		 * URL for updating the asset type associated to a template.
		 * @default undefined
		 * @instance
		 * @memberOf FragmentsEditor
		 * @review
		 * @type {!string}
		 */

		updateLayoutPageTemplateEntryAssetTypeURL: Config.string(),

		/**
		 * Allow opening/closing contextual sidebar
		 * @default true
		 * @instance
		 * @memberOf FragmentsEditor
		 * @private
		 * @review
		 * @type {boolean}
		 */

		_contextualSidebarVisible: Config.bool()
			.internal()
			.value(true),

		/**
		 * CSS class for the fragments drop target.
		 * @default undefined
		 * @instance
		 * @memberOf FragmentsEditor
		 * @review
		 * @type {!string}
		 */

		_dropTargetClass: Config.string().internal().value('dropTarget'),

		/**
		 * If true, editable values should be highlighted.
		 * @default false
		 * @instance
		 * @memberOf FragmentsEditor
		 * @private
		 * @review
		 * @type {boolean}
		 */

		_highlightMapping: Config.bool()
			.internal()
			.value(false),

		/**
		 * Editable type of the field that is being mapped
		 * @default ''
		 * @instance
		 * @memberOf FragmentsEditor
		 * @private
		 * @review
		 * @type {string}
		 */

		_selectMappingDialogEditableType: Config
			.string()
			.internal()
			.value(''),

		/**
		 * EditableId of the field that is being mapped
		 * @default ''
		 * @instance
		 * @memberOf FragmentsEditor
		 * @private
		 * @review
		 * @type {string}
		 */

		_selectMappingDialogEditableId: Config
			.string()
			.internal()
			.value(''),

		/**
		 * FragmentEntryLinkId of the field that is being mapped
		 * @default ''
		 * @instance
		 * @memberOf FragmentsEditor
		 * @private
		 * @review
		 * @type {string}
		 */

		_selectMappingDialogFragmentEntryLinkId: Config
			.string()
			.internal()
			.value(''),

		/**
		 * Mapped field ID of the field that is being mapped
		 * @default ''
		 * @instance
		 * @memberOf FragmentsEditor
		 * @private
		 * @review
		 * @type {string}
		 */

		_selectMappingDialogMappedFieldId: Config
			.string()
			.internal()
			.value(''),

		/**
		 * Flag indicating if the SelectMappingDialog should be shown
		 * @default false
		 * @instance
		 * @memberOf FragmentsEditor
		 * @private
		 * @review
		 * @type {boolean}
		 */

		_selectMappingDialogVisible: Config
			.bool()
			.internal()
			.value(false),

		/**
		 * Flag indicating if the SelectMappingTypeDialog should be shown
		 * @default false
		 * @instance
		 * @memberOf FragmentsEditor
		 * @private
		 * @review
		 * @type {boolean}
		 */

		_selectMappingTypeDialogVisible: Config
			.bool()
			.internal()
			.value(false),

		/**
		 * Store instance
		 * @default undefined
		 * @instance
		 * @memberOf FragmentsEditor
		 * @private
		 * @review
		 * @type {Store}
		 */

		_store: Config
			.instanceOf(Store)
			.internal()
	},

	INITIAL_STATE
);

Soy.register(FragmentsEditor, templates);

export {FragmentsEditor};
export default FragmentsEditor;