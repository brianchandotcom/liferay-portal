const button = fragmentElement.querySelector(
	`#fragment-${fragmentNamespace}-form-button`
);

if (button) {
	button.addEventListener('click', () => {
		Liferay.fire('formFragment:changeStep', {
			emitter: fragmentElement,
			step: configuration.type,
		});
	});
}