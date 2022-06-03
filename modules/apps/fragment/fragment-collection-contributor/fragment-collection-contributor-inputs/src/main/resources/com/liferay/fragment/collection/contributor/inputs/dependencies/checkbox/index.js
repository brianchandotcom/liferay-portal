if (layoutMode === 'edit') {
	const input = document.getElementById(`${fragmentNamespace}-checkbox`);

	if (input) {
		input.setAttribute('disabled', true);
	}
}
