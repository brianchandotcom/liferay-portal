import DefaultEventHandler from 'frontend-js-web/liferay/DefaultEventHandler.es';

class EntriesDefaultEventHandler extends DefaultEventHandler {
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
					const selectContainerForm = document.getElementById(
						`${instance.namespace}selectContainerForm`
					);

					if (selectContainerForm) {
						const className = selectContainerForm.querySelector(
							`#${instance.namespace}className`
						);

						if (className) {
							className.setAttribute('value', event.classname);
						}

						const classPK = selectContainerForm.querySelector(
							`#${instance.namespace}classPK`
						);

						if (classPK) {
							classPK.setAttribute('value', event.classpk);
						}

						const containerModelId = selectContainerForm.querySelector(
							`#${instance.namespace}containerModelId`
						);

						if (containerModelId) {
							containerModelId.setAttribute(
								'value',
								event.containermodelid
							);
						}

						const redirect = selectContainerForm.querySelector(
							`#${instance.namespace}redirect`
						);

						if (redirect) {
							redirect.setAttribute('value', event.redirect);
						}

						submitForm(selectContainerForm);
					}
				}
			);
		} else {
			submitForm(document.hrefFm, itemData.restoreURL);
		}
	}

	deleteEntry(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(document.hrefFm, itemData.deleteURL);
		}
	}
}

export default EntriesDefaultEventHandler;
