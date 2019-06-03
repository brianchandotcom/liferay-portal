import DefaultEventHandler from 'frontend-js-web/liferay/DefaultEventHandler.es';
import OpenSimpleInputModal from 'frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es';
import {Config} from 'metal-state';

class EmptyResultMessageDefaultEventHandler extends DefaultEventHandler {
	addAssetListEntry(itemData) {
		OpenSimpleInputModal({
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
