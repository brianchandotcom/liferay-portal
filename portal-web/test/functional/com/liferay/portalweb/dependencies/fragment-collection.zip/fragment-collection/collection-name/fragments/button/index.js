{
	const form = document.querySelector('.fragment-button:not(.parsed)');

	const MESSAGE_FORM_SUBMITTED = 'Form submitted';

	form.classList.add('parsed');

	form.addEventListener(
		'submit',
		(event) => {
			event.preventDefault();

			if (Liferay.__FF__.enableCustomDialogs) {
				Liferay.Util.openAlertModal({message: MESSAGE_FORM_SUBMITTED});
			} else {
				alert(MESSAGE_FORM_SUBMITTED);
			}
		});
}