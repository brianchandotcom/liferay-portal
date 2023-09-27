/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import ImagePreviewer from '../src/main/resources/META-INF/resources/preview/js/ImagePreviewer.es';

describe('document-library-preview-image', () => {
	afterEach(cleanup);

	it('renders an image previewer', () => {
		const {asFragment} = render(
			<ImagePreviewer
				alt="alt text"
				imageURL="image.jpg"
				spritemap="icons.svg"
			/>
		);

		expect(asFragment()).toMatchSnapshot();
	});
});
