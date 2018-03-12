import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import templates from './SidebarFragmentCollections.soy';

/**
 * SidebarFragmentCollections
 */

class SidebarFragmentCollections extends Component {

	/**
	 * Callback that is executed when a fragment entry is clicked.
	 * It propagates a collectionEntryClick event with the fragment information.
	 * @param {Event} event
	 * @private
	 */

	_handleEntryClick(event) {
		const fragmentEntryId = event.delegateTarget.dataset.fragmentEntryId;
		const fragmentName = event.delegateTarget.dataset.fragmentEntryName;

		this.emit(
			'collectionEntryClick',
			{
				fragmentEntryId,
				fragmentName
			}
		);
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */

SidebarFragmentCollections.STATE = {

	/**
	 * Available entries that can be dragged inside the existing Page Template,
	 * organized by fragment categories.
	 * @default undefined
	 * @instance
	 * @memberOf SidebarFragmentCollections
	 * @type {!Array<{
	 *   fragmentCollectionId: !string,
	 *   fragmentEntries: Array<{
	 *     fragmentEntryId: !string,
	 *     imagePreviewURL: string,
	 *     name: !string
	 *   }>,
	 *   name: !string
	 * }>}
	 */

	fragmentCollections: Config.arrayOf(
		Config.shapeOf(
			{
				fragmentCollectionId: Config.string().required(),
				fragmentEntries: Config.arrayOf(
					Config.shapeOf(
						{
							fragmentEntryId: Config.string().required(),
							imagePreviewURL: Config.string(),
							name: Config.string().required()
						}
					).required()
				).required(),
				name: Config.string().required()
			}
		)
	),

	/**
	 * Path of the available icons.
	 * @default undefined
	 * @instance
	 * @memberOf SidebarFragmentCollections
	 * @type {!string}
	 */

	spritemap: Config.string().required()
};

Soy.register(SidebarFragmentCollections, templates);

export {SidebarFragmentCollections};
export default SidebarFragmentCollections;