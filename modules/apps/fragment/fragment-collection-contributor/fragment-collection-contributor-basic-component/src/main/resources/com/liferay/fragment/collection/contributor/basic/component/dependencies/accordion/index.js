const button = fragmentElement.querySelector('.panel-header');
const panel = fragmentElement.querySelector('.panel-collapse');

function main() {
	if (layoutMode !== 'edit') {
		panel.style.maxHeight = panel.scrollHeight + 'px';

		button.addEventListener('click', () => {
			panel.classList.add('collapsed');

			button.classList.toggle('collapsed');

			button.setAttribute(
				'aria-expanded',
				!button.classList.contains('collapsed')
			);

			panel.style.maxHeight = panel.style.maxHeight
				? null
				: panel.scrollHeight + 'px';
		});
	}
}

main();
