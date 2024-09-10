const button = fragmentElement.querySelector(
	`#fragment-${fragmentNamespace}-form-button`
);

if (button) {
	button.addEventListener('click', ({target}) => {
		const isEditable =
			target.hasAttribute('data-lfr-editable-id') ||
			target.hasAttribute('contenteditable');

		if (isEditable && layoutMode === 'edit') {
			return;
		}

		Liferay.fire('formFragment:changeStep', {
			emitter: fragmentElement,
			step: configuration.type,
		});
	});
}
