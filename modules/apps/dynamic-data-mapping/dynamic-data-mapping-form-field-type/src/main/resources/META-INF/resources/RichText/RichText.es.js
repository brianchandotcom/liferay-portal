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

import {ClassicEditor} from 'frontend-editor-ckeditor-web';
import React, {useEffect, useMemo, useRef} from 'react';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';

const RichText = ({
	editable,
	editingLanguageId,
	editorConfig,
	id,
	name,
	onBlur,
	onChange,
	onFocus,
	predefinedValue = '',
	readOnly,
	value,
	visible,
	...otherProps
}) => {
	const editorRef = useRef();

	const contents = useMemo(
		() => (editable ? predefinedValue : value ?? predefinedValue),
		[editable, predefinedValue, value]
	);

	useEffect(() => {
		const editor = editorRef.current?.editor;

		if (editor) {
			editor.config.contentsLangDirection =
				Liferay.Language.direction[editingLanguageId];

			editor.config.contentsLanguage = editingLanguageId;

			editor.setData(editor.getData());
		}
	}, [editingLanguageId]);

	return (
		<FieldBase
			{...otherProps}
			id={id}
			name={name}
			readOnly={readOnly}
			style={readOnly ? {pointerEvents: 'none'} : null}
			visible={visible}
		>
			<ClassicEditor
				contents={contents}
				editorConfig={editorConfig}
				name={name}
				onBlur={onBlur}
				onChange={(content) => {
					if (contents !== content) {
						onChange({target: {value: content}});
					}
				}}
				onFocus={onFocus}
				onSetData={({data: {dataValue: value}, editor: {mode}}) => {
					if (mode === 'source') {
						onChange({target: {value}});
					}
				}}
				readOnly={readOnly}
				ref={editorRef}
			/>

			<input name={name} type="hidden" value={contents} />
		</FieldBase>
	);
};

export default RichText;
