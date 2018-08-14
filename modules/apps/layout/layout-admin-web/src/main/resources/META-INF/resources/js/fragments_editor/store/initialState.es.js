import {Config} from 'metal-state';

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

	hoveredFragmentEntryLinkBorder: Config.string().value(null),
	hoveredFragmentEntryLinkId: Config.string().value(null)
};

export {INITIAL_STATE};