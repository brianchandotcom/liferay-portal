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

import VideoPreviewer from '../../src/main/resources/META-INF/resources/preview/js/soy/VideoPreviewer.es';

let component;

describe('document-library-preview-video', () => {
	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('renders a video player', () => {
		component = new VideoPreviewer({
			videoPosterURL: 'poster.jpg',
			videoSources: [
				{
					type: 'video/mp4',
					url: '//video.mp4'
				},
				{
					type: 'video/ogv',
					url: '//video.ogv'
				}
			]
		});

		expect(component).toMatchSnapshot();
	});
});
