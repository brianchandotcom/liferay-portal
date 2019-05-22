const slide = fragmentElement.querySelector('.slide');
const indicators = Array.from(
	fragmentElement.querySelectorAll('.carousel-indicators > li')
);
const carouselControls = Array.from(
	fragmentElement.querySelectorAll(
		'.carousel-control-prev, .carousel-control-next'
	)
);
const carouselId = fragmentElement.id + '-carousel';

slide.id = carouselId;
indicators.forEach(function(indicator) {
	indicator.dataset.target = '#' + carouselId;
});
carouselControls.forEach(function(control) {
	control.href = '#' + carouselId;
});

slide.classList.add('carousel');

if (document.querySelector('.has-edit-mode-menu')) {
	$(slide).carousel('pause');
	$('.carousel').off('keydown.bs.carousel');
} else {
	$(slide).carousel({ride: 'carousel'});
}
