/* eslint-disable no-undef */
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

$('#thumbnail li').click(function () {
	$(this).addClass('active').siblings().removeClass('active');
	var slide = $('#slide li');
	var slideTop = 0;
	var slideBlock = $('#slide ul');
	var thum = $('#thumbnail .thumbnail-list li');
	var thumTop =
		$('#thumbnail .thumbnail-list .active').position().top -
		$('#thumbnail .thumbnail-list').position().top +
		'px';

	for (var i = 0; i < thum.length; i++) {
		if ($(thum[i]).hasClass('active')) {
			$($(slide)[i]).addClass('active').siblings().removeClass('active');
		}
	}

	for (var y = 0; y < slide.length; y++) {
		$($('#slide li .blur-img')[y]).attr(
			'style',
			$($('#slide li .img')[y]).attr('style')
		);
		if ($($(slide)[y]).hasClass('active')) {
			slideTop += -(400 * y);
			$(slideBlock).css('transform', 'translateY(' + slideTop + 'px)');
		}
	}

	$('#thumbnail .marker').css('top', thumTop);
});
$('#thumbnail li:first-child').addClass('active');
