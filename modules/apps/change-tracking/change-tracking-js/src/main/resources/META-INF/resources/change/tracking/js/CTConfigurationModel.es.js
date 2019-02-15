import Component from 'metal-component';

import {Config} from 'metal-state';

class CTConfigurationModel extends Component {

	getTranslatedSupportedContentTypes() {
		if (!this.supportedContentTypeLanguageKeys) {
			return [];
		}

		return this.supportedContentTypeLanguageKeys
			.map(
				supportedContentTypeLanguageKey => Liferay.Language.get(supportedContentTypeLanguageKey)
			);
	}

}

CTConfigurationModel.STATE = {

	companyId: Config.number(),

	supportedContentTypeLanguageKeys: Config.arrayOf(
		Config.string()
	),

	supportedContentTypes: Config.arrayOf(
		Config.string()
	),

	isChangeTrackingEnabled: Config.bool()

};

export default CTConfigurationModel;