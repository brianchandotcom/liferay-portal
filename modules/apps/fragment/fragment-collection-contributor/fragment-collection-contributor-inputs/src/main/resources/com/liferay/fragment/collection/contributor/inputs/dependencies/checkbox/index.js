if (document.body.classList.contains('has-edit-mode-menu')) {
	const input = document.getElementById(`${fragmentNamespace}-checkbox`);

	if (input) {
		input.setAttribute('disabled', true);
	}
}
