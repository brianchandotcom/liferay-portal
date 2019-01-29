import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import {Config} from 'metal-state';

class EntriesDefaultEventHandler extends PortletBase {
	handleItemClicked(event) {
		var itemData = event.data.item.data;

		if (itemData && itemData.action && this[itemData.action]) {
			this[itemData.action](itemData);
		}
	}

	restoreEntry(itemData) {
		const instance = this;

		if (itemData.move && itemData.move !== 'false') {
			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						destroyOnHide: true,
						modal: true,
						width: 1024
					},
					eventName: this.ns('selectContainer'),
					id: this.ns('selectContainer'),
					title: Liferay.Language.get('warning'),
					uri: itemData.restoreURL
				},
				function(event) {
					const selectContainerForm = document.getElementById(`${instance.namespace}selectContainerForm`);

					if (selectContainerForm) {
						const className = selectContainerForm.querySelector(`#${instance.namespace}className`);

						if (className) {
							className.setAttribute('value', event.classname);
						}

						const classPK = selectContainerForm.querySelector(`#${instance.namespace}classPK`);

						if (classPK) {
							classPK.setAttribute('value', event.classpk);
						}

						const containerModelId = selectContainerForm.querySelector(`#${instance.namespace}containerModelId`);

						if (containerModelId) {
							containerModelId.setAttribute('value', event.containermodelid);
						}

						const redirect = selectContainerForm.querySelector(`#${instance.namespace}redirect`);

						if (redirect) {
							redirect.setAttribute('value', event.redirect);
						}

						submitForm(selectContainerForm);
					}
				}
			);
		}
		else {
			submitForm(document.hrefFm, itemData.restoreURL);
		}
	}

	deleteEntry(itemData) {
		submitForm(document.hrefFm, itemData.deleteURL);
	}

}

EntriesDefaultEventHandler.STATE = {
	namespace: Config.string()
};

export default EntriesDefaultEventHandler;