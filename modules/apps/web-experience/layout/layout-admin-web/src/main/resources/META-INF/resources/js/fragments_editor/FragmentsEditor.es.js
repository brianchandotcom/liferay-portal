import Component from 'metal-component';
import debounce from 'metal-debounce';
import {Config} from 'metal-state';
import {getUid} from 'metal';
import Soy from 'metal-soy';

import '../contextual_sidebar/ContextualSidebar.es';
import './FragmentEntryLink.es';
import './sidebar/SidebarAddedFragment.es';
import './sidebar/SidebarFragmentCollection.es';
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
		this._updatePageTemplate = debounce(this._updatePageTemplate, 100);

		this._initializeEditables();

		this._dirty = true;
		this._fetchFragmentsContent().then(() => {
			this._dirty = false;
		});
	}

	/**
	 * If there are changes on any fragment, it sets the _dirty property
	 * to true and queues an update.
	 * @inheritDoc
	 * @param {!Object} changes
	 * @review
	 */
	shouldUpdate(changes) {
		if (changes.fragmentEntryLinks || changes._editables) {
			this._dirty = true;
			this._updatePageTemplate();
		}

		return true;
	}

	/**
	 * Fetches a fragment entry from the given ID and position,
	 * returns a promise that resolves into it's content
	 * @param {!string} fragmentEntryId
	 * @param {!number} position
	 * @return {Promise<string>}
	 * @review
	 */
	_fetchFragmentContent(fragmentEntryId, position) {
		const formData = new FormData();

		formData.append(
			`${this.portletNamespace}fragmentEntryId`,
			fragmentEntryId
		);
		formData.append(`${this.portletNamespace}position`, position);

		return fetch(this.renderFragmentEntryURL, {
			body: formData,
			credentials: 'include',
			method: 'POST',
		})
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
		const promises = [];

		this.fragmentEntryLinks.forEach((fragment, index) => {
			if (fragment.fragmentEntryId && !fragment.content) {
				promises.push(this
					._fetchFragmentContent(fragment.fragmentEntryId, index)
					.then((content) => {
						this.fragmentEntryLinks[index].content = content;
					})
				)
			}
		});

		return Promise.all(promises);
	}

	/**
	 * Callback executed everytime an editable field has been changed
	 * @param {{
	 *   editableId: string,
	 *   fragmentEntryLinkId: number,
	 *   value: string
	 * }} data
	 * @private
	 * @review
	 */
	_handleEditableChanged(data) {
		const index = this._editables.findIndex(
			editable =>
				editable.editableId === data.editableId &&
				editable.fragmentEntryLinkId === data.fragmentEntryLinkId
		);

		const editable = {
			editableId: data.editableId,
			fragmentEntryLinkId: data.fragmentEntryLinkId,
			value: data.value,
		};

		if (index === -1) {
			this._editables = [...this._editables, editable];
		} else {
			this._editables = [
				...this._editables.slice(0, index),
				...this._editables.slice(index + 1),
				editable,
			];
		}
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
				fragmentEntryId: event.fragmentEntryId,
				fragmentEntryLinkId: getUid().toString(),
				name: event.fragmentName,
				config: {},
				content: '',
				editableValues: {},
			},
		];
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
	_handleFragmentRemoveButtonClick(data) {
		const fragmentEntryLinkId = data.fragmentEntryLinkId;
		const index = this.fragmentEntryLinks.findIndex(
			fragmentEntryLink =>
				fragmentEntryLink.fragmentEntryLinkId === fragmentEntryLinkId
		);

		if (index !== -1) {
			this._editables = this._editables.filter(
				editable => editable.fragmentEntryLinkId !== fragmentEntryLinkId
			);

			this.fragmentEntryLinks = [
				...this.fragmentEntryLinks.slice(0, index),
				...this.fragmentEntryLinks.slice(index + 1),
			];
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
	 * Initialize _editables property with the existing values received inside
	 * fragmentEntryLinks.
	 * @private
	 */
	_initializeEditables() {
		const editables = [];

		this.fragmentEntryLinks.forEach(fragmentEntryLink => {
			Object.keys(fragmentEntryLink.editableValues || {}).forEach(
				editableId => {
					editables.push({
						editableId,
						fragmentEntryLinkId:
							fragmentEntryLink.fragmentEntryLinkId,
						value: fragmentEntryLink.editableValues[editableId],
					});
				}
			)
		});

		this._editables = editables;
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
				`${this.portletNamespace}classPK`,
				this.classPK
			);

			const editableValues = {};

			this._editables.forEach(editable => {
				const editableId = editable.editableId;
				const fragmentEntryLinkId = editable.fragmentEntryLinkId;
				const value = editable.value;

				editableValues[fragmentEntryLinkId] =
					editableValues[fragmentEntryLinkId] || {};

				editableValues[fragmentEntryLinkId][editableId] = value;
			});

			formData.append(
				`${this.portletNamespace}editableValues`,
				JSON.stringify(editableValues)
			);

			this.fragmentEntryLinks.forEach(fragment => {
				formData.append(
					`${this.portletNamespace}fragmentIds`,
					fragment.fragmentEntryId
				);
			});

			fetch(this.updateURL, {
				body: formData,
				credentials: 'include',
				method: 'POST',
			}).then(() => {
				this._lastSaveDate = new Date().toLocaleTimeString();

				this._fetchFragmentsContent().then(() => {
					this._dirty = false;
				});
			});
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
		visible: true,
	},
	{
		id: 'added',
		name: Liferay.Language.get('added'),
		visible: true,
	},
];

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FragmentsEditor.STATE = {
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
		Config.shapeOf({
			fragmentCollectionId: Config.string().required(),
			name: Config.string().required(),
			entries: Config.arrayOf(
				Config.shapeOf({
					fragmentEntryId: Config.string().required(),
					name: Config.string().required(),
				})
			).required(),
		})
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
		Config.shapeOf({
			fragmentEntryId: Config.string().required(),
			fragmentEntryLinkId: Config.string().required(),
			name: Config.string().required(),
			config: Config.object().value({}),
			content: Config.string().value(''),
			editableValues: Config.object().value({}),
		})
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
	 * List of editable fields that have been modified by the user
	 * @default []
	 * @instance
	 * @memberOf FragmentsEditor
	 * @private
	 * @type {{
	 *   editableId: string,
	 *   fragmentEntryLinkId: string,
	 *   value: string
	 * }}
	 */
	_editables: Config
		.arrayOf(Config.shapeOf({
			editableId: Config.string(),
			fragmentEntryLinkId: Config.string(),
			value: Config.string(),
		}))
		.internal()
		.value([]),

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
		Config.shapeOf({
			id: Config.string(),
			name: Config.string(),
			visible: Config.bool(),
		})
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
		.value(SIDEBAR_TABS[0].id),
};

Soy.register(FragmentsEditor, templates);

export {FragmentsEditor};
export default FragmentsEditor;
