const slide = fragmentElement.querySelector('.slide');
const indicators = Array.from(fragmentElement.querySelectorAll('.carousel-indicators > li'));
const carouselControls = Array.from(fragmentElement.querySelectorAll('.carousel-control-prev, .carousel-control-next'));
const carouselId = fragmentElement.id + '-carousel';

slide.id = carouselId;
indicators.forEach(indicator => indicator.dataset.target = '#' + carouselId);
carouselControls.forEach(control => control.href = '#' + carouselId);

slide.classList.add('carousel');
$(slide).carousel({ ride: 'carousel' });