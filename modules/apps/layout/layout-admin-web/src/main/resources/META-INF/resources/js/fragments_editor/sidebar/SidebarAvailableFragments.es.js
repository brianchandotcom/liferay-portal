import Component from 'metal-component';
import {Config} from 'metal-state';
import {Drag, DragDrop} from 'metal-drag-drop';
import Soy from 'metal-soy';

import './FragmentsEditorSidebarCard.es';
import templates from './SidebarAvailableFragments.soy';

/**
 * SidebarAvailableFragments
 */

class SidebarAvailableFragments extends Component {

	/**
	 * @inheritDoc
	 * @private
	 * @review
	 */

	attached() {
		this._dragDrop = new DragDrop(
			{
				dragPlaceholder: Drag.Placeholder.CLONE,
				handles: '.drag-handler',
				sources: '.drag-card',
				targets: `.${this.dropTargetClass}`
			}
		);

		this._dragDrop.on(
			DragDrop.Events.END,
			this._handleDrop.bind(this)
		);
	}

	/**
	 * @inheritDoc
	 * @private
	 * @review
	 */

	dispose() {
		this._dragDrop.dispose();
	}

	/**
	 * Callback that is executed when an item is dropped.
	 * It propagates an itemDrop event with the item id.
	 * @param {!MouseEvent} event
	 * @private
	 * @review
	 */

	_handleDrop(data, event) {
		event.preventDefault();

		if (data.target) {
			const itemId = data.source.dataset.itemId;
			const itemName = data.source.dataset.itemName;

			this.emit(
				'fragmentEntryClick',
				{
					fragmentEntryId: itemId,
					fragmentName: itemName
				}
			);
		}
	}

	/**
	 * Callback that is executed when a fragment entry is clicked.
	 * It propagates a fragmentEntryClick event with the fragment information.
	 * @param {{
	 *   itemId: !string,
	 *   itemName: !string
	 * }} event
	 * @private
	 */

	_handleEntryClick(event) {
		this.emit(
			'fragmentEntryClick',
			{
				fragmentEntryId: event.itemId,
				fragmentName: event.itemName
			}
		);
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */

SidebarAvailableFragments.STATE = {

	/**
	 * CSS class for the fragments drop target.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentsEditor
	 * @review
	 * @type {!string}
	 */

	dropTargetClass: Config.string(),

	/**
	 * Available entries that can be dragged inside the existing Page Template,
	 * organized by fragment categories.
	 * @default undefined
	 * @instance
	 * @memberOf SidebarAvailableFragments
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
	 * @memberOf SidebarAvailableFragments
	 * @type {!string}
	 */

	spritemap: Config.string().required()
};

Soy.register(SidebarAvailableFragments, templates);

export {SidebarAvailableFragments};
export default SidebarAvailableFragments;