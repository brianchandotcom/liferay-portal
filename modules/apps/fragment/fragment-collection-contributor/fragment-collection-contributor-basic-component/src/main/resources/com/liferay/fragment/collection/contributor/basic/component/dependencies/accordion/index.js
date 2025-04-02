const button = fragmentElement.querySelector('.panel-header');
const body = fragmentElement.querySelector('.panel-collapse');

function main() {
	if (layoutMode !== 'edit') {
		button.addEventListener('click', () => {
			button.classList.toggle('collapsed');
			body.classList.toggle('collapse');
		});

		button.setAttribute(
			'aria-expanded',
			!button.classList.contains('collapsed')
		);
	}
}

main();
