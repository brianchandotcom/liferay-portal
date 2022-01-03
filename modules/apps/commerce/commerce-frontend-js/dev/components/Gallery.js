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

import launcher from '../../src/main/resources/META-INF/resources/components/gallery/entry';

import '../../src/main/resources/META-INF/resources/styles/main.scss';

function getImgUrl(image, width) {
	return `https://images.unsplash.com/${image}?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=${width}&q=80`;
}

const props = {
	images: [
		'flagged/photo-1556667885-a6e05b14f2eb',
		'photo-1503328427499-d92d1ac3d174',
		'photo-1505740420928-5e560c06d30e',
		'photo-1526434426615-1abe81efcb0b',
		'photo-1518131672697-613becd4fab5',
	].map((image) => ({
		thumbnailURL: getImgUrl(image, 100),
		title: image,
		URL: getImgUrl(image, 800),
	})),
};

launcher('galleryId', 'gallery-root-id', props);
