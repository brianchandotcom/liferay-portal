import DefaultEventHandler from 'frontend-js-web/liferay/DefaultEventHandler.es';

class DDMTemplateElementsDefaultEventHandler extends DefaultEventHandler {
	deleteDDMTemplate(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(document.hrefFm, itemData.deleteDDMTemplateURL);
		}
	}

	permissionsDDMTemplate(itemData) {
		Liferay.Util.openWindow({
			dialog: {
				destroyOnHide: true,
				modal: true
			},
			dialogIframe: {
				bodyCssClass: 'dialog-with-footer'
			},
			title: Liferay.Language.get('permissions'),
			uri: itemData.permissionsDDMTemplateURL
		});
	}
}

export default DDMTemplateElementsDefaultEventHandler;
