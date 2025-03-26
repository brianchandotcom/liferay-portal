/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CKEditor} from '@ckeditor/ckeditor5-react';
import {
	Alignment,
	BlockQuote,
	Bold,
	ClassicEditor as BaseClassicEditor,
	Editor,
	Essentials,
	EventInfo,
	Font,
	GeneralHtmlSupport,
	Heading,
	HorizontalLine,
	Image,
	ImageBlock,
	ImageCaption,
	ImageInline,
	ImageResize,
	ImageStyle,
	ImageToolbar,
	Indent,
	Italic,
	Link,
	List,
	MediaEmbed,
	Paragraph,
	RemoveFormat,
	SourceEditing,
	Strikethrough,
	Style,
	Table,
	TableCaption,
	TableProperties,
	TableToolbar,
	Underline,
} from 'ckeditor5';
import React from 'react';

import '../../css/ckeditor5/editor.scss';
import ItemSelector from './plugins/ItemSelector';
import advancedClassicEditorConfig from './presets/advancedClassicEditorConfig';
import basicClassicEditorConfig from './presets/basicClassicEditorConfig';
import {ClassicEditorConfig, EClassicEditorConfigPreset} from './utils/types';

const ClassicEditor = ({
	className,
	config,
	data,
	onChange,
	onReady,
}: {
	className?: string;
	config?: ClassicEditorConfig;
	data?: string;
	id?: string;
	onChange?: (event: EventInfo, editor: Editor) => void;
	onReady?: (editor: Editor) => void;
}) => {
	const defaultConfig: ClassicEditorConfig = {
		plugins: [
			Alignment,
			BlockQuote,
			Bold,
			Essentials,
			Font,
			GeneralHtmlSupport,
			Heading,
			HorizontalLine,
			ItemSelector,
			Image,
			ImageBlock,
			ImageCaption,
			ImageInline,
			ImageResize,
			ImageStyle,
			ImageToolbar,
			Indent,
			Italic,
			Link,
			List,
			Paragraph,
			RemoveFormat,
			MediaEmbed,
			SourceEditing,
			Strikethrough,
			Style,
			Table,
			TableCaption,
			TableProperties,
			TableToolbar,
			Underline,
		],
		ui: {
			viewportOffset: {
				top: 56,
			},
		},
	};

	if (!Liferay.FeatureFlags['LPD-11235']) {
		return <></>;
	}

	return (
		<div className={`lfr-ck ${className ? className : ''}`}>
			<CKEditor
				config={{
					...defaultConfig,
					...(config?.preset === EClassicEditorConfigPreset.ADVANCED
						? advancedClassicEditorConfig
						: basicClassicEditorConfig),
					...config,
				}}
				data={data}
				editor={BaseClassicEditor}
				onChange={onChange}
				onReady={(editor: BaseClassicEditor) => {
					editor.ui.view.toolbar.items.map((item: any) => {
						if (item.buttonView) {
							item.buttonView.tooltipPosition = 'n';
						}

						item.tooltipPosition = 'n';
					});

					const hasControlMenu = document.querySelector(
						'.control-menu-container'
					);
					if (!hasControlMenu) {
						editor.ui.viewportOffset = {
							top: 0,
						};
					}

					onReady && onReady(editor);
				}}
			/>
		</div>
	);
};

export default ClassicEditor;
