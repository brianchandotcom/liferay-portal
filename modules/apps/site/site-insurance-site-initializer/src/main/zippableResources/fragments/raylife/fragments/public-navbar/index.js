const dropdownButton = fragmentElement.querySelector('.dropdown-toggle');
const dropdownMenu = fragmentElement.querySelector('.dropdown-menu');

dropdownButton.addEventListener('click', function () {
	dropdownMenu.classList.toggle('show');

	dropdownButton.setAttribute(
		'aria-expanded',
		dropdownMenu.classList.contains('show').toString()
	);
});
