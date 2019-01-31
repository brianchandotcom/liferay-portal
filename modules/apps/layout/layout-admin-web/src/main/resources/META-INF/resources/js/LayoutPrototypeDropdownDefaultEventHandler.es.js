import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import {Config} from 'metal-state';

class LayoutPrototypeDropdownDefaultEventHandler extends PortletBase {
	handleItemClicked(event) {
		const itemData = event.data.item.data;

		if (itemData && itemData.action && this[itemData.action]) {
			this[itemData.action](itemData);
		}
	}

	deleteLayoutPrototype(itemData) {
		if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this'))) {
			this._send(itemData.deleteLayoutPrototypeURL);
		}
	}

	exportLayoutPrototype(itemData) {
		this._openWindow('export', itemData.exportLayoutPrototypeURL);
	}

	importLayoutPrototype(itemData) {
		this._openWindow('import', itemData.importLayoutPrototypeURL);
	}

	permissionsLayoutPrototype(itemData) {
		this._openWindow('permissions', itemData.permissionsLayoutPrototypeURL);
	}

	_openWindow(label, url) {
		Liferay.Util.openWindow(
			{
				dialog: {
					destroyOnHide: true,
					modal: true
				},
				dialogIframe: {
					bodyCssClass: 'dialog-with-footer'
				},
				title: Liferay.Language.get(label),
				uri: url
			}
		);
	}

	_send(url) {
		submitForm(document.hrefFm, url);
	}
}

LayoutPrototypeDropdownDefaultEventHandler.STATE = {
	namespace: Config.string()
};

export default LayoutPrototypeDropdownDefaultEventHandler;