if (layoutMode === 'edit') {
	const input = document.getElementById(`${fragmentNamespace}-date-input`);

	if (input) {
		input.setAttribute('disabled', true);
	}
}
