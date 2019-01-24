import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import './components/dialogs/SelectMappingDialog.es';
import './components/dialogs/SelectMappingTypeDialog.es';
import './components/fragment_entry_link/FragmentEntryLinkList.es';
import './components/sidebar/FragmentsEditorSidebar.es';
import './components/toolbar/FragmentsEditorToolbar.es';
import {INITIAL_STATE} from './store/state.es';
import {Store} from './store/store.es';
import templates from './FragmentsEditor.soy';

/**
 * FragmentsEditor
 * @review
 */
class FragmentsEditor extends Component {

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
			{
				mappedField: event.key
			}
		);
	}

	/**
	 * Swap the positions of two fragmentEntryLinks
	 * @param {Array} list
	 * @param {number} indexA
	 * @param {number} indexB
	 * @return {Array}
	 * @private
	 */
	_swapListElements(list, indexA, indexB) {
		[list[indexA], list[indexB]] = [
			list[indexB],
			list[indexA]
		];

		return list;
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
		 * Available elements that can be used, organized by collections.
		 * @default undefined
		 * @instance
		 * @memberOf FragmentsEditor
		 * @review
		 * @type {!Array<object>}
		 */

		elements: Config.arrayOf(
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
		 * URL for getting the list of mapping fields
		 * @default null
		 * @instance
		 * @memberOf FragmentsEditor
		 * @review
		 * @type {string}
		 */

		mappingFieldsURL: Config.string().value(null),

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
		 * Available sections that can be used, organized by collections.
		 * @default undefined
		 * @instance
		 * @memberOf FragmentsEditor
		 * @review
		 * @type {!Array<object>}
		 */

		sections: Config.arrayOf(
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
		 * Store instance
		 * @default undefined
		 * @instance
		 * @memberOf FragmentsEditor
		 * @review
		 * @type {Store}
		 */

		store: Config.instanceOf(Store),

		/**
		 * URL for updating the asset type associated to a template.
		 * @default undefined
		 * @instance
		 * @memberOf FragmentsEditor
		 * @review
		 * @type {!string}
		 */

		updateLayoutPageTemplateEntryAssetTypeURL: Config.string()
	},

	INITIAL_STATE
);

Soy.register(FragmentsEditor, templates);

export {FragmentsEditor};
export default FragmentsEditor;