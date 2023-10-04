const inputElement = document.getElementById(
	`${fragmentNamespace}-rich-text-input`
);

if (input.attributes?.readOnly) {
	if (inputElement) {
		inputElement.innerHTML = input.value;
	}
}
else if (layoutMode === 'edit') {
	if (inputElement) {
		inputElement.setAttribute('disabled', true);
	}
}
