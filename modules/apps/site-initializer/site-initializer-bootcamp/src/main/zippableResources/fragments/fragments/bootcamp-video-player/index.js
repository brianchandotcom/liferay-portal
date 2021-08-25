/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

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
