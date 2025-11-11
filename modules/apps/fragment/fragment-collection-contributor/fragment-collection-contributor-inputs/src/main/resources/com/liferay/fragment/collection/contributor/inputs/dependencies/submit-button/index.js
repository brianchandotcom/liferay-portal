const button = fragmentElement.querySelector('button');

const isMultistepButton = fragmentElement.querySelector(
	`#fragment-${fragmentElementId}-form-button`
);

const form = button.closest('.lfr-layout-structure-item-form');

function appendLoadingIndicator() {
	button.classList.add('c-gap-2', 'disabled', 'd-flex', 'align-items-center');

	const indicator = document.createElement('span');

	indicator.classList.add(
		'loading-animation',
		'loading-animation-light',
		'loading-animation-sm',
		'm-0'
	);

	button.appendChild(indicator);
}

button.addEventListener('click', ({target}) => {
	const isEditable =
		target.hasAttribute('data-lfr-editable-id') ||
		target.hasAttribute('contenteditable');

	if (isEditable && layoutMode === 'edit') {
		return;
	}

	if (isMultistepButton) {
		Liferay.fire('formFragment:changeStep', {
			emitter: fragmentElement,
			step: configuration.type,
		});
	}
	else {
		Liferay.fire('formFragment:submit');
	}
});

if (form) {
	form.addEventListener('submit', () => {
		appendLoadingIndicator();
	});
}
