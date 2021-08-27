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
