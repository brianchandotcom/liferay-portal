/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Plugin} from '@ckeditor/ckeditor5-core/dist/index.js';
import {Widget, toWidget} from '@ckeditor/ckeditor5-widget/dist/index.js';

class VideoEmbed extends Plugin {
	static get requires() {
		return [Widget];
	}

	init() {
		const editor = this.editor;

		editor.model.schema.register('liferayVideo', {
			allowAttributes: ['src'],
			allowWhere: '$block',
			isObject: true,
		});

		editor.conversion.for('upcast').elementToElement({
			converterPriority: 'high',
			model: (viewElement, {writer}) =>
				writer.createElement('liferayVideo', {
					src: viewElement.getAttribute('src'),
				}),
			view: 'video',
		});

		editor.conversion.for('dataDowncast').elementToElement({
			model: 'liferayVideo',
			view: (modelElement, {writer}) => {
				const figure = writer.createContainerElement('figure', {
					class: 'media',
				});

				const video = writer.createEmptyElement('video', {
					controls: 'controls',
					src: modelElement.getAttribute('src') as string,
				});

				writer.insert(writer.createPositionAt(figure, 0), video);

				return figure;
			},
		});

		editor.conversion.for('editingDowncast').elementToElement({
			model: 'liferayVideo',
			view: (modelElement, {writer}) => {
				const figure = writer.createContainerElement('figure', {
					class: 'media',
				});

				const wrapper = writer.createContainerElement('div', {
					class: 'ck-media__wrapper',
				});

				const video = writer.createEmptyElement('video', {
					controls: 'controls',
					src: modelElement.getAttribute('src') as string,
					style: 'width: 100%; height: auto;',
				});

				writer.insert(writer.createPositionAt(wrapper, 0), video);
				writer.insert(writer.createPositionAt(figure, 0), wrapper);

				return toWidget(figure, writer, {
					label: 'video',
				});
			},
		});
	}
}

export default VideoEmbed;
