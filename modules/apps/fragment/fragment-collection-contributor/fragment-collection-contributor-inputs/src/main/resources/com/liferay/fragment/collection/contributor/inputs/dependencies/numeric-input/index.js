const input = fragmentElement.querySelector(`#${fragmentNamespace}-numeric-input`);
const isInteger = input.getAttribute('data-type') === 'integer';

function handleOnKeydown(event) {
	if (isInteger && (event.key === ',' || event.key === '.')) {
		event.preventDefault();
	}
}

if (input) {
	if (layoutMode === 'edit') {
		input.setAttribute('disabled', true);
	}
	else {
		input.addEventListener('keydown', handleOnKeydown);
	}
}
