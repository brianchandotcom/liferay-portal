if (layoutMode === 'edit') {
	const input = document.getElementById(`${fragmentNamespace}-text-input`);

	if (input) {
		input.setAttribute('disabled', true);
	}
}
