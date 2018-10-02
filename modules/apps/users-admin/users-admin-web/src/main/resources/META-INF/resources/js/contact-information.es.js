import dom from 'metal-dom';

function openEditContactInformationWindow(title, entryId, baseRenderURL, height) {
	const renderURL = new URL(baseRenderURL);

	const portletNamespace = Liferay.Util.getPortletNamespace(renderURL.searchParams.get('p_p_id'));

	renderURL.searchParams.set(Liferay.Util.ns(portletNamespace, 'entryId'), entryId);

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

									const editURL = new URL(mainForm.action);

									for (const field of modalForm.elements) {
										let value = field.value;

										if (field.type === 'checkbox') {
											value = field.checked;
										}

										editURL.searchParams.set(field.name, value);
									}

									submitForm(mainForm, editURL.href);

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
			uri: renderURL.href
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

			const {entryId, title} = event.delegateTarget.dataset;

			openEditContactInformationWindow(
				title,
				entryId ? entryId : 0,
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