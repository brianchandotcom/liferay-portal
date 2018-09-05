import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './FragmentsEditorSidebarCard.soy';

/**
 * FragmentsEditorSidebarCard
 * @review
 */

class FragmentsEditorSidebarCard extends Component {

	/**
	 * Callback that is executed when a item entry is clicked.
	 * It propagates a itemClick event with the item information.
	 * @param {!MouseEvent} event
	 * @private
	 * @review
	 */

	_handleClick(event) {
		const {itemId, itemName} = event.delegateTarget.dataset;

		this.emit(
			'itemClick',
			{
				itemId,
				itemName
			}
		);
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */

FragmentsEditorSidebarCard.STATE = {};

Soy.register(FragmentsEditorSidebarCard, templates);

export {FragmentsEditorSidebarCard};
export default FragmentsEditorSidebarCard;