const inputElement = document.getElementById(`${fragmentNamespace}-checkbox`);

if (inputElement) {
	if (input.attributes?.readOnly) {
		inputElement.addEventListener('click', (event) =>
			event.preventDefault()
		);
	}
	else if (layoutMode === 'edit') {
		inputElement.setAttribute('disabled', true);
	}
}
