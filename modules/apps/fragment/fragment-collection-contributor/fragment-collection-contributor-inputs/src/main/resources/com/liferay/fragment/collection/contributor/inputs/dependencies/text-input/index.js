if (document.body.classList.contains('has-edit-mode-menu')) {
	const input = document.getElementById(`${fragmentNamespace}-text-input`);

	if (input) {
		input.setAttribute('disabled', true);
	}
}
