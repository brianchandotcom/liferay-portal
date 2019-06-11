import {DefaultEventHandler, openSimpleInputModal} from 'frontend-js-web';
import {Config} from 'metal-state';

class EmptyResultMessageDefaultEventHandler extends DefaultEventHandler {
	addAssetListEntry(itemData) {
		openSimpleInputModal({
			dialogTitle: itemData.title,
			formSubmitURL: itemData.addAssetListEntryURL,
			mainFieldLabel: Liferay.Language.get('title'),
			mainFieldName: 'title',
			mainFieldPlaceholder: Liferay.Language.get('title'),
			namespace: this.namespace,
			spritemap: this.spritemap
		});
	}
}

EmptyResultMessageDefaultEventHandler.STATE = {
	spritemap: Config.string()
};

export default EmptyResultMessageDefaultEventHandler;
