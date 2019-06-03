import Component from 'metal-component';
import Soy from 'metal-soy';

import './SelectMappingTypeForm.es';
import 'frontend-js-web/liferay/compat/modal/Modal.es';
import getConnectedComponent from '../../store/ConnectedComponent.es';
import {HIDE_MAPPING_TYPE_DIALOG} from '../../actions/actions.es';
import templates from './SelectMappingTypeDialog.soy';

/**
 * SelectMappingTypeDialog
 */
class SelectMappingTypeDialog extends Component {
	/**
	 * Change asset type selection dialog visibility.
	 * @private
	 * @review
	 */
	_handleVisibleChanged() {
		this.store.dispatch({
			type: HIDE_MAPPING_TYPE_DIALOG
		});
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
SelectMappingTypeDialog.STATE = {};

const ConnectedSelectMappingTypeDialog = getConnectedComponent(
	SelectMappingTypeDialog,
	['selectMappingTypeDialogVisible', 'spritemap']
);

Soy.register(ConnectedSelectMappingTypeDialog, templates);

export {ConnectedSelectMappingTypeDialog, SelectMappingTypeDialog};
export default ConnectedSelectMappingTypeDialog;
