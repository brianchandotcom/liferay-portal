import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './SucessPage.soy.js';
import {Config} from 'metal-state';
import {setValue} from '../../util/i18n.es';

class SucessPage extends Component {
	prepareStateForRender(state) {
		const {store} = this.context;
		const {editingLanguageId} = store.props;
		const {successPageSettings} = this;
		const {body, title} = successPageSettings;

		return {
			...state,
			body: body[editingLanguageId] || '',
			title: title[editingLanguageId] || ''
		};
	}

	_handleSuccessPageUpdated(event) {
		const {dispatch, store} = this.context;
		const {editingLanguageId} = store.props;
		const {delegateTarget} = event;
		const {
			dataset: {setting},
			value
		} = delegateTarget;
		const {successPageSettings} = this;

		dispatch(
			'successPageChanged',
			setValue(successPageSettings, editingLanguageId, setting, value)
		);
	}
}

SucessPage.STATE = {
	/**
	 * @instance
	 * @memberof SucessPage
	 * @type {?object}
	 */

	successPageSettings: Config.object().value({})
};

Soy.register(SucessPage, templates);

export default SucessPage;
