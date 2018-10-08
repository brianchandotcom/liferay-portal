import dom from 'metal-dom';
import Uri from 'metal-uri';

function openEditContactInformationWindow(title, primaryKey, baseRenderURL, height) {
	const renderURL = new Uri(baseRenderURL);

	const portletNamespace = Liferay.Util.getPortletNamespace(renderURL.getParameterValue('p_p_id'));

	renderURL.setParameterValue(Liferay.Util.ns(portletNamespace, 'primaryKey'), primaryKey);

	const modalId = Liferay.Util.ns(portletNamespace, 'editContactInformationModal');

	Liferay.Util.openWindow(
		{
			dialog: {
				destroyOnHide: true,
				height: height,
				modal: true,
				resizable: false,
				'toolbars.footer': [
					{
						cssClass: 'btn-link close-modal',
						id: 'cancelButton',
						label: Liferay.Language.get('cancel'),
						on: {
							click: () => Liferay.Util.getWindow(modalId).hide()
						}
					},
					{
						cssClass: 'btn-primary',
						id: 'saveButton',
						label: Liferay.Language.get('save'),
						on: {
							click: function(event) {
								const {contentWindow} = document.getElementById(modalId + '_iframe_');

								const {form: modalForm, formValidator: modalFormValidator} = contentWindow.Liferay.Form.get(Liferay.Util.ns(portletNamespace, 'fm'));

								modalFormValidator.validate();

								if (!modalFormValidator.hasErrors()) {
									const mainForm = document.getElementById(Liferay.Util.ns(portletNamespace, 'fm'));

									const editURL = new Uri(mainForm.action);

									const formData = new FormData(modalForm);

									formData.forEach((value, key) => editURL.setParameterValue(key, value));

									submitForm(mainForm, editURL.toString());

									Liferay.Util.getWindow(modalId).hide();
								}
							}
						}
					}
				],
				width: '600'
			},
			dialogIframe: {
				bodyCssClass: 'contact-information-edit-form'
			},
			id: modalId,
			title: title,
			uri: renderURL.toString()
		}
	);
}

function registerContactInformationListener(selector, renderURL, height) {
	var editClickHandler = dom.delegate(
		document.body,
		'click',
		selector,
		event => {
			event.preventDefault();

			const {primaryKey, title} = event.delegateTarget.dataset;

			openEditContactInformationWindow(
				title,
				primaryKey ? primaryKey : 0,
				renderURL,
				height
			);
		}
	);

	Liferay.once(
		'destroyPortlet',
		editClickHandler.removeListener.bind(editClickHandler)
	);
}

export {registerContactInformationListener};
export default {registerContactInformationListener};