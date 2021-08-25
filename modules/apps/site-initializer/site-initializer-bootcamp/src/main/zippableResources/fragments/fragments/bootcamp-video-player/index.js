const btnPlay = fragmentElement.querySelectorAll('.btn-play');
const btnPlayFirst = fragmentElement.querySelector('.btn-play');
const colToHide = fragmentElement.querySelector('.col-to-hide');
const courses = fragmentElement.querySelector('.courses-video');
const fullscreen = fragmentElement.querySelector('.fullscreen');
const play = fragmentElement.querySelector('.play');

btnPlayFirst.classList.add('active');
courses.setAttribute('src', btnPlayFirst.getAttribute('href'));

for (const btn of btnPlay) {
	btn.addEventListener('click', function (e) {
		e.preventDefault();
		for (const rem of btnPlay) {
			rem.classList.remove('active');
		}
		e.target.classList.add('active');
		courses.setAttribute('src', e.target.getAttribute('href'));
		play.classList.remove('hide');
	});
}

play.addEventListener('click', function () {
	if (courses.paused) {
		courses.play();
	}
});

fullscreen.addEventListener('click', function () {
	colToHide.classList.toggle('hide');
	fullscreen.classList.toggle('active');
});

courses.addEventListener('play', (e) => {
	play.classList.add('hide');
});

courses.addEventListener('pause', (e) => {
	play.classList.remove('hide');
});
