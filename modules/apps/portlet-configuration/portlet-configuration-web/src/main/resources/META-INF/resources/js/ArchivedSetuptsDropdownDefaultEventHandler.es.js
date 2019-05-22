import DefaultEventHandler from 'frontend-js-web/liferay/DefaultEventHandler.es';

class ArchivedSetuptsDropdownDefaultEventHandler extends DefaultEventHandler {
	deleteArchivedSetups(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			this._send(itemData.deleteArchivedSetupsURL);
		}
	}

	restoreArchivedSetup(itemData) {
		this._send(itemData.restoreArchivedSetupURL);
	}

	_send(url) {
		submitForm(document.hrefFm, url);
	}
}

export default ArchivedSetuptsDropdownDefaultEventHandler;
