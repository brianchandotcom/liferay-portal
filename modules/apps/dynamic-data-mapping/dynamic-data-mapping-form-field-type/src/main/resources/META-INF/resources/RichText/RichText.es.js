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

import './RichTextRegister.soy';

import {Editor} from 'frontend-editor-ckeditor-web';
import React from 'react';

import {FieldBaseProxy} from '../FieldBase/ReactFieldBase.es';
import {useSyncValue} from '../Text/Text.es';
import getConnectedReactComponentAdapter from '../util/ReactComponentAdapter.es';
import {connectStore} from '../util/connectStore.es';
import templates from './RichTextAdapter.soy';

const CKEDITOR_CONFIG = {
	toolbar: [
		{items: ['Undo', 'Redo'], name: 'clipboard'},
		'/',
		{
			items: [
				'Bold',
				'Italic',
				'Underline',
				'Strike',
				'-',
				'CopyFormatting',
				'RemoveFormat',
			],
			name: 'basicstyles',
		},
		{
			items: [
				'NumberedList',
				'BulletedList',
				'-',
				'Outdent',
				'Indent',
				'-',
				'Blockquote',
				'-',
				'JustifyLeft',
				'JustifyCenter',
				'JustifyRight',
				'JustifyBlock',
			],
			name: 'paragraph',
		},
		{items: ['Link', 'Unlink', 'Anchor'], name: 'links'},
		{
			items: ['Image', 'Table', 'HorizontalRule', 'SpecialChar'],
			name: 'insert',
		},
		'/',
		{items: ['Styles', 'Format', 'Font', 'FontSize'], name: 'styles'},
		{items: ['TextColor', 'BGColor'], name: 'colors'},
		{items: ['Maximize'], name: 'tools'},
		{
			items: ['Source'],
			name: 'document',
		},
	],
};

const RichText = ({data, id, name, onChange, readOnly}) => {
	const [currentValue, setCurrentValue] = useSyncValue(data);

	const editorProps = {
		config: CKEDITOR_CONFIG,
		data: currentValue,
	};

	if (readOnly) {
		editorProps.readOnly = true;
		editorProps.style = {pointerEvents: 'none'};
	}
	else {
		editorProps.onChange = event => {
			const newValue = event.editor.getData();

			setCurrentValue(newValue);

			onChange({data: newValue, event});
		};
	}

	const style = {
		border: '1px solid #E7E7ED',
		height: '148px',
	};

	return (
		<>
			{readOnly && currentValue && (
				<div
					dangerouslySetInnerHTML={{
						__html: currentValue,
					}}
					name={`html_text_${name}`}
					style={style}
				></div>
			)}

			{readOnly && !currentValue && (
				<div
					name={`html_text_${name}`}
					style={{...style, color: '#A7A9BC', padding: '16px'}}
				>
					{Liferay.Language.get('click-to-add-a-rich-text')}
				</div>
			)}

			{!readOnly && <Editor {...editorProps} />}

			<input
				defaultValue={currentValue}
				id={id || name}
				name={name}
				type="hidden"
			/>
		</>
	);
};

const Main = ({
	id,
	name,
	onChange,
	predefinedValue,
	readOnly,
	value,
	...otherProps
}) => {
	return (
		<FieldBaseProxy {...otherProps} id={id} name={name} readOnly={readOnly}>
			<RichText
				data={value || predefinedValue}
				id={id}
				name={name}
				onChange={onChange}
				readOnly={readOnly}
			/>
		</FieldBaseProxy>
	);
};

const RichTextProxy = connectStore(({emit, ...otherProps}) => (
	<Main
		{...otherProps}
		onChange={({data, event}) => emit('fieldEdited', event, data)}
	/>
));

const ReactRichTextAdapter = getConnectedReactComponentAdapter(
	RichTextProxy,
	templates
);

export {ReactRichTextAdapter};
export default ReactRichTextAdapter;
