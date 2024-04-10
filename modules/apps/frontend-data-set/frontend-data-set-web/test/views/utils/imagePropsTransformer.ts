/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import imagePropsTransformer from '../../../src/main/resources/META-INF/resources/views/utils/imagePropsTransformer';

const docsAndMediaImage = {
	id: 1,
	link: {
		href: 'path-to-the-image/name-of-the-image.jpg',
		label: 'name-of-the-image.jpg',
	},
	name: 'name-of-the-image.jpg',
};

describe('imagePropsTransformer utility', () => {
	it('returns undefined when there is no input information', () => {
		expect(imagePropsTransformer(undefined)).toBeUndefined();

		expect(imagePropsTransformer({})).toBeUndefined();
	});

	it('returns an object with valid HTML image attributes when the input information is a string', () => {
		const image = '/path-to-the-image.jpg';

		expect(imagePropsTransformer(image)).toMatchObject({
			alt: '',
			src: image,
		});
	});

	it('returns an object with valid HTML image attributes when the input information is a string within a value object', () => {
		const image = {value: '/path-to-the-image.jpg'};

		expect(imagePropsTransformer(image)).toMatchObject({
			alt: '',
			src: image.value,
		});
	});

	it('returns an object with valid HTML image attributes when the input information is from Documents and Media images', () => {
		expect(imagePropsTransformer(docsAndMediaImage)).toMatchObject({
			alt: docsAndMediaImage.link.label,
			src: docsAndMediaImage.link.href,
		});

		const nestedImageInformation = {
			value: docsAndMediaImage,
		};

		expect(imagePropsTransformer(nestedImageInformation)).toMatchObject({
			alt: docsAndMediaImage.link.label,
			src: docsAndMediaImage.link.href,
		});
	});
});
