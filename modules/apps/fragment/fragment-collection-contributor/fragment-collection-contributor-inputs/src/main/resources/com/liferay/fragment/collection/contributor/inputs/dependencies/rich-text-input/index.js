if (layoutMode === 'edit') {
	const input = document.getElementById(
		`${fragmentNamespace}-rich-text-input`
	);

	if (input) {
		input.setAttribute('disabled', true);
	}
}
