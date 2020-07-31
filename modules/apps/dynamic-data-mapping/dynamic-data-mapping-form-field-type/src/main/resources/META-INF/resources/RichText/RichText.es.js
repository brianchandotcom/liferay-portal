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
import React, {useMemo} from 'react';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';
import {useSyncValue} from '../hooks/useSyncValue.es';

const CKEDITOR_CONFIG = {
	autoGrow_bottomSpace: 50,
	autoGrow_maxHeight: 600,
	autoGrow_minHeight: 215,
	extraPlugins: ',videoembed,autogrow,stylescombo',
	resize_enabled: true,
	toolbar_ddm: [
		['Undo', 'Redo'],
		['Styles'],
		['Bold', 'Italic', 'Underline'],
		['RemoveFormat'],
		['NumberedList', 'BulletedList'],
		['Blockquote'],
		['Link', 'Unlink'],
		['Table', 'Image', 'VideoEmbed'],
		['Source'],
	],
};

const RichText = ({
	editorConfig,
	id,
	name,
	onChange,
	predefinedValue,
	readOnly,
	value,
	visible,
	...otherProps
}) => {
	const [currentValue, setCurrentValue] = useSyncValue(
		value ? value : predefinedValue
	);

	const normalizedEditorConfig = useMemo(() => {
		const config = editorConfig.JSONObject;

		return {
			...config,
			...CKEDITOR_CONFIG,
			extraPlugins: config.extraPlugins + CKEDITOR_CONFIG.extraPlugins,
		};
	}, [editorConfig]);

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
				contents={currentValue}
				data={currentValue}
				editorConfig={normalizedEditorConfig}
				initialToolbarSet="ddm"
				name={name}
				onChange={(data) => {
					if (currentValue !== data) {
						setCurrentValue(data);

						onChange({}, data);
					}
				}}
				readOnly={readOnly}
			/>

			<input
				defaultValue={currentValue}
				id={id || name}
				name={name}
				type="hidden"
			/>
		</FieldBase>
	);
};

export default RichText;
