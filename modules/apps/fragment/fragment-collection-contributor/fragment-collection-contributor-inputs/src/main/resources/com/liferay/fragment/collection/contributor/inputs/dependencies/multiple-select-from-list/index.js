const numberOfOptions = configuration.numberOfOptions;
const options = input.attributes.options || [];
const values = input.value.split(',');

const button = fragmentElement.querySelector('.multiselect-list-button');
const fieldSet = fragmentElement.querySelector('.multiselect-list-fieldset');

const allInputs = Array.from(
	fragmentElement.querySelectorAll('.custom-control-input')
);

const updateInputStatus = () => {
	if (!input.required) {
		return;
	}

	const someInputIsChecked = allInputs.some((input) => input.checked);

	if (someInputIsChecked) {
		allInputs.forEach((input) => input.removeAttribute('required'));
	}
	else {
		allInputs.forEach((input) => input.setAttribute('required', true));
	}
};

if (input.attributes?.readOnly) {
	allInputs.forEach((input) => {
		input.addEventListener('click', (event) => event.preventDefault());
	});
}
else if (layoutMode === 'edit') {
	allInputs.forEach((input) => {
		input.setAttribute('disabled', true);
	});

	button.setAttribute('disabled', true);
}
else {
	if (Liferay.FeatureFlags['LPD-37927']) {
		import('@liferay/fragment-impl').then(
			({registerLocalizedMultiSelect}) => {
				if (input.localizable) {
					const defaultLanguageId =
						themeDisplay.getDefaultLanguageId();

					const {onChange} = registerLocalizedMultiSelect({
						defaultLanguageId,
						initialValues: input.valueI18n,
						inputElements: allInputs,
						namespace: fragmentNamespace,
					});

					fieldSet.addEventListener('change', (event) => {
						onChange(event);
					});
				}
			}
		);
	}
	else {
		fieldSet.addEventListener('change', updateInputStatus);
	}
}

updateInputStatus();

if (numberOfOptions < options.length) {
	const missionOptions = options.slice(numberOfOptions);

	const template = fragmentElement.querySelector(
		'.multiselect-list-option-template'
	);

	button.addEventListener('click', () => {
		missionOptions.forEach((option) => {
			const node = template.content.cloneNode(true);

			const input = node.querySelector('input');
			input.value = option.value;

			// eslint-disable-next-line no-undef
			input.id = `${fragmentEntryLinkNamespace}-checkbox-${option.value}`;

			if (values.includes(option.value)) {
				input.checked = true;
			}

			if (layoutMode === 'edit') {
				input.setAttribute('disabled', true);
			}

			const label = node.querySelector('label');

			label.setAttribute(
				'for',

				// eslint-disable-next-line no-undef
				`${fragmentEntryLinkNamespace}-checkbox-${option.value}`
			);

			const text = node.querySelector('.custom-control-label-text');
			text.textContent = option.label;

			fieldSet.appendChild(node);
			allInputs.push(input);
		});

		fieldSet.removeChild(button);

		updateInputStatus();
	});
}
