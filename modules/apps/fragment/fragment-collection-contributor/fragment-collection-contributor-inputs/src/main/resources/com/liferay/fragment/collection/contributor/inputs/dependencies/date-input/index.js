const inputElement = document.getElementById(`${fragmentNamespace}-date-input`);

if (inputElement) {
	if (input.attributes?.readOnly) {
		inputElement.addEventListener('keydown', (event) => {
			if (event.code === 'Space') {
				event.preventDefault();
			}
		});
	}
	else if (layoutMode === 'edit') {
		inputElement.setAttribute('disabled', true);
	}
}
