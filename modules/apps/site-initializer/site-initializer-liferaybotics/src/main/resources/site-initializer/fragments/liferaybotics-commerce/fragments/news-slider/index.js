/* eslint-disable no-undef */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

$('#thumbnail li').click(function () {
	$(this).addClass('active').siblings().removeClass('active');
	const slide = $('#slide li');
	let slideTop = 0;
	const slideBlock = $('#slide ul');
	const thum = $('#thumbnail .thumbnail-list li');
	const thumTop =
		$('#thumbnail .thumbnail-list .active').position().top -
		$('#thumbnail .thumbnail-list').position().top +
		'px';

	for (let i = 0; i < thum.length; i++) {
		if ($(thum[i]).hasClass('active')) {
			$($(slide)[i]).addClass('active').siblings().removeClass('active');
		}
	}

	for (let y = 0; y < slide.length; y++) {
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
