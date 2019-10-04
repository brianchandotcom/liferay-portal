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

import AudioPreviewer from '../../src/main/resources/META-INF/resources/preview/js/soy/AudioPreviewer.es';

let component;

describe('document-library-preview-audio', () => {
	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('renders an audio player', () => {
		component = new AudioPreviewer({
			audioMaxWidth: 520,
			audioSources: [
				{
					type: 'audio/ogg',
					url: '//audio.ogg'
				},
				{
					type: 'audio/mp3',
					url: '//audio.mp3'
				}
			]
		});

		expect(component).toMatchSnapshot();
	});
});
