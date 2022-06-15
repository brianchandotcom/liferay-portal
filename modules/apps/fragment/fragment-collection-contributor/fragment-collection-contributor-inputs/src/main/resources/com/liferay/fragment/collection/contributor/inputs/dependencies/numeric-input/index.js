if (layoutMode === 'edit') {
	const input = document.getElementById(`${fragmentNamespace}-numeric-input`);

	if (input) {
		input.setAttribute('disabled', true);
	}
}