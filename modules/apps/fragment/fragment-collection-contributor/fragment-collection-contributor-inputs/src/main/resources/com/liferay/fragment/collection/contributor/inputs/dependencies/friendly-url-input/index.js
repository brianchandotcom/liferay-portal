const inputElement = document.getElementById(
	`${fragmentNamespace}-friendly-url-input`
);

function main() {
	if (layoutMode === 'edit' && inputElement) {
		inputElement.setAttribute('disabled', true);
	}
}

main();
