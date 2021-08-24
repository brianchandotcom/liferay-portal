const focusDirection = configuration.focusDirection;
const showArrows = configuration.showArrows;
const slideType = configuration.slideType;

new Splide('.splide', {
	arrows: showArrows,
	autoWidth: true,
	focus: focusDirection,
	type: slideType,
}).mount();
