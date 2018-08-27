import {Config} from 'metal-state';

import {DRAG_POSITIONS} from './reducers/dragDrop.es';

/**
 * Initial state
 * @review
 * @type {object}
 */

const INITIAL_STATE = {

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
		.value(null)
};

export {INITIAL_STATE};