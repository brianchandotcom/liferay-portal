import 'frontend-taglib/contextual_sidebar/ContextualSidebar.es';
import Component from 'metal-component';
import Soy from 'metal-soy';
import debounce from 'metal-debounce';
import {Config} from 'metal-state';
import {getUid} from 'metal';

import './sidebar/SidebarAddedFragment.es';
import './sidebar/SidebarFragmentCollection.es';
import FragmentEntryLink from './FragmentEntryLink.es';
import templates from './FragmentsEditor.soy';

/**
 * FragmentsEditor
 * @review
 */

class FragmentsEditor extends Component {

	/**
	 * @inheritDoc
	 * @review
	 */

	created() {
		this._updatePageTemplate = this._updatePageTemplate.bind(this);
		this._updatePageTemplate = debounce(this._updatePageTemplate, 300);

		this._dirty = true;
		this._fetchFragmentsContent().then(
			() => {
				this._dirty = false;
			}
		);
	}

	/**
	 * Fetches a FragmentEntryLink content from the fragment ID and
	 * fragmentEntryLink ID, returns a promise that resolves into it's content.
	 * @param {!string} fragmentEntryId
	 * @param {!string} fragmentEntryLinkId
	 * @return {Promise<string>}
	 * @review
	 */

	_fetchFragmentContent(fragmentEntryId, fragmentEntryLinkId) {
		const formData = new FormData();

		formData.append(
			`${this.portletNamespace}fragmentEntryId`,
			fragmentEntryId
		);

		formData.append(
			`${this.portletNamespace}position`,
			fragmentEntryLinkId
		);

		return fetch(
			this.renderFragmentEntryURL,
			{
				body: formData,
				credentials: 'include',
				method: 'POST'
			}
		)
			.then(response => response.json())
			.then(response => response.content);
	}

	/**
	 * Fetchs all missing fragments contents.
	 * It returns a promise that is resolved when every fragment
	 * has been fetched.
	 * @return {Promise<>}
	 * @review
	 * @private
	 */

	_fetchFragmentsContent() {
		return Promise.all(
			this.fragmentEntryLinks
				.filter(
					fragmentEntryLink =>
						fragmentEntryLink.fragmentEntryId &&
						fragmentEntryLink.fragmentEntryLinkId &&
						!fragmentEntryLink.content
				)
				.map(
					fragmentEntryLink => {
						return this._fetchFragmentContent(
							fragmentEntryLink.fragmentEntryId,
							fragmentEntryLink.fragmentEntryLinkId
						).then(
							content => {
								const index = this.fragmentEntryLinks.findIndex(
									_fragmentEntryLink =>
										_fragmentEntryLink.fragmentEntryLinkId ===
										fragmentEntryLink.fragmentEntryLinkId
								);

								if (index !== -1) {
									this.fragmentEntryLinks[index].content = content;
								}
							}
						);
					}
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
		const fragmentEntryLink = this.fragmentEntryLinks.find(
			fragmentEntryLink =>
				fragmentEntryLink.fragmentEntryLinkId ===
				data.fragmentEntryLinkId
		);

		if (fragmentEntryLink) {
			fragmentEntryLink.editableValues[data.editableId] = data.value;
		}

		this._updatePageTemplate();
	}

	/**
	 * Callback executed when a fragment entry of a collection is clicked.
	 * It receives fragmentEntryId and fragmentName as event data.
	 * @param {!Event} event
	 * @private
	 * @review
	 */

	_handleFragmentCollectionEntryClick(event) {
		this.fragmentEntryLinks = [
			...this.fragmentEntryLinks,
			{
				config: {},
				content: '',
				editableValues: {},
				fragmentEntryId: event.fragmentEntryId,
				fragmentEntryLinkId: getUid().toString(),
				name: event.fragmentName
			}
		];

		this._updatePageTemplate();
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
		const index = this.fragmentEntryLinks.findIndex(
			fragmentEntryLink =>
				fragmentEntryLink.fragmentEntryLinkId ===
				data.fragmentEntryLinkId
		);

		if (
			(direction === FragmentEntryLink.MOVE_DIRECTIONS.DOWN && index < this.fragmentEntryLinks.length - 1) ||
			(direction === FragmentEntryLink.MOVE_DIRECTIONS.UP && index > 0)
		) {
			this.fragmentEntryLinks = this._swapListElements(
				[].slice(this.fragmentEntryLinks),
				index,
				index + direction
			);

			this._updatePageTemplate();
		}
	}

	/**
	 * Removes a fragment from the fragment list. The fragment to
	 * be removed should be specified inside the event as fragmentEntryLinkId
	 * @param {!{
	 *   fragmentEntryLinkId: !string
	 * }} data
	 * @private
	 * @review
	 */

	_handleFragmentRemove(data) {
		const index = this.fragmentEntryLinks.findIndex(
			fragmentEntryLink =>
				fragmentEntryLink.fragmentEntryLinkId ===
				data.fragmentEntryLinkId
		);

		if (index !== -1) {
			this.fragmentEntryLinks = [
				...this.fragmentEntryLinks.slice(0, index),
				...this.fragmentEntryLinks.slice(index + 1)
			];

			this._updatePageTemplate();
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
	 * Updates _sidebarSelectedTab according to the clicked element
	 * @param {!Event} event
	 * @private
	 * @review
	 */

	_handleSidebarTabClick(event) {
		this._sidebarSelectedTab = event.delegateTarget.dataset.tabName;
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
	 * Sends all the accumulated changes to the server and, if
	 * success, sets the _dirty property to false.
	 * @private
	 * @review
	 */

	_updatePageTemplate() {
		if (!this._dirty) {
			this._dirty = true;

			const formData = new FormData();

			formData.append(
				`${this.portletNamespace}classNameId`,
				this.classNameId
			);

			formData.append(`${this.portletNamespace}classPK`, this.classPK);

			const editableValues = {};

			this.fragmentEntryLinks.forEach(
				(fragmentEntryLink, index) => {
					Object.keys(fragmentEntryLink.editableValues).forEach(
						editableId => {
							editableValues[index] = editableValues[index] || {};

							editableValues[index][editableId] = fragmentEntryLink.editableValues[editableId];
						}
					);
				}
			);

			formData.append(
				`${this.portletNamespace}editableValues`,
				JSON.stringify(editableValues)
			);

			this.fragmentEntryLinks.forEach(
				fragment => {
					formData.append(
						`${this.portletNamespace}fragmentIds`,
						fragment.fragmentEntryId
					);
				}
			);

			fetch(
				this.updateURL,
				{
					body: formData,
					credentials: 'include',
					method: 'POST'
				}
			).then(
				() => {
					this._lastSaveDate = new Date().toLocaleTimeString();

					this._fetchFragmentsContent().then(
						() => {
							this._dirty = false;
						}
					);
				}
			);
		}
	}
}

/**
 * Tabs that can appear inside the sidebar
 * @review
 * @see FragmentsEditor._sidebarTabs
 */

const SIDEBAR_TABS = [
	{
		id: 'fragments',
		name: Liferay.Language.get('fragments'),
		visible: true
	},
	{
		id: 'added',
		name: Liferay.Language.get('added'),
		visible: true
	}
];

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */

FragmentsEditor.STATE = {

	/**
	 * Class name id used for storing changes.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentsEditor
	 * @review
	 * @type {!string}
	 */

	classNameId: Config.string().required(),

	/**
	 * Class primary key used for storing changes.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentsEditor
	 * @review
	 * @type {!string}
	 */

	classPK: Config.string().required(),

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
	 * Optional ID provided by the template system.
	 * @default ''
	 * @instance
	 * @memberOf FragmentsEditor
	 * @review
	 * @type {string}
	 */

	id: Config.string().value(''),

	/**
	 * List of fragment instances being used, the order
	 * of the elements in this array defines their position.
	 * @default []
	 * @instance
	 * @memberOf FragmentsEditor
	 * @review
	 * @type {Array<string>}
	 */

	fragmentEntryLinks: Config.arrayOf(
		Config.shapeOf(
			{
				config: Config.object().value({}),
				content: Config.string().value(''),
				editableValues: Config.object().value({}),
				fragmentEntryId: Config.string().required(),
				fragmentEntryLinkId: Config.string().required(),
				name: Config.string().required()
			}
		)
	).value([]),

	/**
	 * Portlet namespace needed for prefixing form inputs
	 * @default undefined
	 * @instance
	 * @memberOf FragmentsEditor
	 * @review
	 * @type {!string}
	 */

	portletNamespace: Config.string().required(),

	/**
	 * URL for getting a fragment content.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentsEditor
	 * @review
	 * @type {!string}
	 */

	renderFragmentEntryURL: Config.string().required(),

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
	 * URL for updating accumulated changes.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentsEditor
	 * @review
	 * @type {!string}
	 */

	updateURL: Config.string().required(),

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
	 * When true, it indicates that are changes pending to save.
	 * @default false
	 * @instance
	 * @memberOf FragmentsEditor
	 * @private
	 * @review
	 * @type {boolean}
	 */

	_dirty: Config.bool()
		.internal()
		.value(false),

	/**
	 * Last data when the autosave has been executed.
	 * @default ''
	 * @instance
	 * @memberOf FragmentsEditor
	 * @private
	 * @type {string}
	 */

	_lastSaveDate: Config.string()
		.internal()
		.value(''),

	/**
	 * Tabs being shown in sidebar
	 * @default SIDEBAR_TABS
	 * @instance
	 * @memberOf FragmentsEditor
	 * @private
	 * @review
	 * @type {Array<{
	 * 	 id:string,
	 * 	 name:string,
	 * 	 visible:boolean
	 * }>}
	 */

	_sidebarTabs: Config.arrayOf(
		Config.shapeOf(
			{
				id: Config.string(),
				name: Config.string(),
				visible: Config.bool()
			}
		)
	)
		.internal()
		.value(SIDEBAR_TABS),

	/**
	 * Tab selected inside sidebar
	 * @default SIDEBAR_TABS[0].id
	 * @instance
	 * @memberOf FragmentsEditor
	 * @private
	 * @review
	 * @type {string}
	 */

	_sidebarSelectedTab: Config.oneOf(SIDEBAR_TABS.map(tab => tab.id))
		.internal()
		.value(SIDEBAR_TABS[0].id)
};

Soy.register(FragmentsEditor, templates);

export {FragmentsEditor};
export default FragmentsEditor;