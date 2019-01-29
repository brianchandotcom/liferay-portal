import Component from 'metal-component';
import Soy from 'metal-soy';

import {getConnectedComponent} from '../../../store/ConnectedComponent.es';
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

const ConnectedFragmentsEditorSidebarCard = getConnectedComponent(
	FragmentsEditorSidebarCard,
	['spritemap']
);

Soy.register(ConnectedFragmentsEditorSidebarCard, templates);

export {ConnectedFragmentsEditorSidebarCard, FragmentsEditorSidebarCard};
export default ConnectedFragmentsEditorSidebarCard;