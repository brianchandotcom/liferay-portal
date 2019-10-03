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

import {cleanup, render, waitForElement} from '@testing-library/react';
import VideoPreviewer from '../src/main/resources/META-INF/resources/preview/js/VideoPreviewer.es';
import React from 'react';

describe('VideoPreviewer', () => {
	afterEach(cleanup);

	it('renders', async () => {
		const {container} = render(
			<VideoPreviewer
				componentId="video-previewer"
				videoPosterURL="some-url"
				videoSources={[
					{type: 'video/mp4', url: 'big_buck_bunny.mp4'},
					{type: 'video/ogv', url: 'big_buck_bunny.ogv'}
				]}
			/>
		);

		await waitForElement(() => container.querySelector('#video-previewer'));
		await waitForElement(() =>
			container.querySelector('video.preview-file-video')
		);
		await waitForElement(() => container.querySelectorAll('source'));
	});
});
