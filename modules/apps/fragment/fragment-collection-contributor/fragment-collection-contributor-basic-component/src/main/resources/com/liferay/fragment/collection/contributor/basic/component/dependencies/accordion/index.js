const button = fragmentElement.querySelector('.panel-header');
const body = fragmentElement.querySelector('.panel-collapse');

function main() {
	if (layoutMode !== 'edit') {
		button.addEventListener('click', () => {
			const isOpen = body.classList.contains('show');

			body.style.height = body.scrollHeight + 'px';
			body.classList.remove('collapse', 'show');
			body.classList.add('collapsing');

			requestAnimationFrame(() => {
				body.style.height = isOpen ? '0px' : body.scrollHeight + 'px';
			});

			body.addEventListener('transitionend', function handler() {
				body.classList.remove('collapsing');
				body.classList.add('collapse');
				if (!isOpen) {
					body.classList.add('show');
				}
				body.style.height = '';
				body.removeEventListener('transitionend', handler);
			});

			button.setAttribute('aria-expanded', String(!isOpen));
		});
	}
}

main();
