const checkbox = fragmentElement.querySelector('#togBtn');
const sliderBefore = fragmentElement.querySelector(
	'.public-site-navbar .switch .slider'
);
const sliderOn = fragmentElement.querySelector(
	'.public-site-navbar .switch .slider .on'
);
const sliderOff = fragmentElement.querySelector(
	'.public-site-navbar .switch .slider .off'
);

function changeBeforeText(checked) {
	if (checked) {
		sliderBefore.setAttribute(
			'data-content',
			sliderOn.firstChild.nodeValue
		);
	}
	else {
		sliderBefore.setAttribute(
			'data-content',
			sliderOff.firstChild.nodeValue
		);
	}
}

changeBeforeText(checkbox.checked);

checkbox.addEventListener('click', function (event) {
	changeBeforeText(event.target.checked);
});

const menuButton = fragmentElement.querySelector('.raylife-navbar-button');
const myDropdown = fragmentElement.querySelector('#myDropdown');
const menuGrid = fragmentElement.querySelector('.menu-grid');
const menuClose = fragmentElement.querySelector('.menu-close');

menuButton.addEventListener('click', () => {
	myDropdown.classList.toggle('menu-options');

	if (myDropdown.classList.contains('menu-options')) {
		fragmentElement.querySelector(
			'.raylife-navbar-button div span'
		).innerText = 'CLOSE';
		menuGrid.style.display = 'none';
		menuClose.style.display = 'block';
	}
	else {
		fragmentElement.querySelector(
			'.raylife-navbar-button div span'
		).innerText = 'MENU';
		menuGrid.style.display = 'block';
		menuClose.style.display = 'none';
	}
});

menuButton.addEventListener('blur', () => {
	myDropdown.classList.remove('menu-options');
	fragmentElement.querySelector('.raylife-navbar-button div span').innerText =
		'MENU';
	menuGrid.style.display = 'block';
	menuClose.style.display = 'none';
});
