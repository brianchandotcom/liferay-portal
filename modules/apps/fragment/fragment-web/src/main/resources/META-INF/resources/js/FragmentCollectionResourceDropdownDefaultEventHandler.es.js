import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import {Config} from 'metal-state';

class FragmentCollectionResourceDropdownDefaultEventHandler extends PortletBase {
	handleItemClicked(event) {
		const itemData = event.data.item.data;

		if (itemData && itemData.action && this[itemData.action]) {
			this[itemData.action](itemData);
		}
	}

	deleteFragmentCollectionResource(itemData) {
		if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this'))) {
			submitForm(document.hrefFm, itemData.deleteFragmentCollectionResourceURL);
		}
	}
}

FragmentCollectionResourceDropdownDefaultEventHandler.STATE = {
	namespace: Config.string()
};

export default FragmentCollectionResourceDropdownDefaultEventHandler;