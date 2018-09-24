import {Config} from 'metal-state';
import {setValue} from '../../util/i18n.es';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './SucessPage.soy.js';

class SucessPage extends Component {
	static STATE = {

		/**
		 * @instance
		 * @memberof SucessPage
		 * @type {?string}
		 */
		contentLabel: Config.string().value(Liferay.Language.get('content')),

		/**
		 * @instance
		 * @memberof SucessPage
		 * @type {?object}
		 */
		successPageSettings: Config.object(),

		/**
		 * @instance
		 * @memberof SucessPage
		 * @type {?string}
		 */
		titleLabel: Config.string().value(Liferay.Language.get('title'))
	}

	_handleSuccessPageSettingsChanged(event) {
		const {delegateTarget} = event;
		const {dataset: {setting}, value} = delegateTarget;
		const {successPageSettings} = this;
		const language = themeDisplay.getLanguageId();

		this.emit('successPageChanged', setValue(successPageSettings, language, setting, value));
	}
}

Soy.register(SucessPage, templates);

export default SucessPage;