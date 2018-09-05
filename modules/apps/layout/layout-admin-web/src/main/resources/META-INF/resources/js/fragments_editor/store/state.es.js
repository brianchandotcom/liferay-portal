import {Config} from 'metal-state';

import {DRAG_POSITIONS} from '../reducers/placeholders.es';

/**
 * Initial state
 * @review
 * @type {object}
 */

const INITIAL_STATE = {

	/**
	 * URL for associating fragment entries to the underlying model.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentsEditor
	 * @review
	 * @type {!string}
	 */

	addFragmentEntryLinkURL: Config.string().required(),

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
	 * URL for removing fragment entries of the underlying model.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentsEditor
	 * @review
	 * @type {!string}
	 */

	deleteFragmentEntryLinkURL: Config.string().required(),

	/**
	 * List of fragment instances being used, the order
	 * of the elements in this array defines their position.
	 * @default []
	 * @instance
	 * @memberOf FragmentsEditor
	 * @review
	 * @type {Array<{
	 *   config: Object,
	 *   content: string,
	 *   editableValues: Object,
	 *   fragmentEntryId: !string,
	 *   fragmentEntryLinkId: !string,
	 *   name: !string
	 * }>}
	 */

	fragmentEntryLinks: Config.arrayOf(
		Config.shapeOf(
			{
				config: Config.object().value({}),
				content: Config.any().value(''),
				editableValues: Config.object().value({}),
				fragmentEntryId: Config.string().required(),
				fragmentEntryLinkId: Config.string().required(),
				name: Config.string().required()
			}
		)
	).value([]),

	/**
	 * Position where a fragment is being dragged to
	 * @default null
	 * @instance
	 * @review
	 * @type {string}
	 */

	hoveredFragmentEntryLinkBorder: Config
		.oneOf(Object.values(DRAG_POSITIONS))
		.value(null),

	/**
	 * FragmentEntryLinkId where a fragment is being dragged over
	 * @default null
	 * @instance
	 * @review
	 * @type {string}
	 */

	hoveredFragmentEntryLinkId: Config
		.string()
		.value(null),

	/**
	 * Last date when the autosave has been executed.
	 * @default ''
	 * @instance
	 * @review
	 * @type {string}
	 */

	lastSaveDate: Config.string()
		.internal()
		.value(''),

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
	 * When true, it indicates that are changes pending to save.
	 * @default false
	 * @instance
	 * @review
	 * @type {boolean}
	 */

	savingChanges: Config.bool()
		.internal()
		.value(false)
};

export {INITIAL_STATE};
export default INITIAL_STATE;