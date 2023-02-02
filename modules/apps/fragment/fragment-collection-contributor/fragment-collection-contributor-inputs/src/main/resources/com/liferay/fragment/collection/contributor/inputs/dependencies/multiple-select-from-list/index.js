const options = input.attributes.options || [];
const numberOfOptions = configuration.numberOfOptions;

const fieldSet = fragmentElement.querySelector('.multi-select-list-fieldset');

const allInputs = Array.from(
	fragmentElement.querySelectorAll('.custom-control-input')
);

if (layoutMode === 'edit') {
	allInputs.forEach((input) => {
		input.setAttribute('disabled', true);
	});
}

fieldSet.addEventListener('change', () => {
	const someInputIsChecked = allInputs.some((input) => input.checked);

	if (someInputIsChecked) {
		allInputs.forEach((input) => input.removeAttribute('required'));
	}
	else {
		allInputs.forEach((input) => input.setAttribute('required', true));
	}
});

if (numberOfOptions < options.length) {
	const button = fragmentElement.querySelector('.multi-select-list-button');

	const missionOptions = options.slice(numberOfOptions);

	const template = fragmentElement.querySelector(
		'.multi-select-list-option-template'
	);

	button.addEventListener('click', () => {
		missionOptions.forEach((option) => {
			const node = template.content.cloneNode(true);

			const input = node.querySelector('input');
			input.value = option.value;

			if (layoutMode === 'edit') {
				input.setAttribute('disabled', true);
			}

			const text = node.querySelector('.custom-control-label-text');
			text.textContent = option.label;

			fieldSet.appendChild(node);
			allInputs.push(node);
		});

		fieldSet.removeChild(button);
	});
}
