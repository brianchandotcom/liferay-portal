/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EventInfo} from '@ckeditor/ckeditor5-utils/dist/index.js';
import {useControlledState} from '@clayui/shared';
import {loadEditorClientExtensions} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';

import '../../css/ckeditor5/editor.scss';

import {CKEditor} from '@ckeditor/ckeditor5-react';
import ClayLoadingIndicator from '@clayui/loading-indicator';

import {LiferayEditorConfig, TEditor} from './utils/types';

const BaseEditor = ({
	className,
	config,
	data,
	disabled,
	editor,
	formInputEnabled = false,
	formInputName,
	onBlur,
	onChange,
	onFocus,
	onReady,
}: {
	className?: string;
	config?: LiferayEditorConfig;
	data?: string;
	disabled?: boolean;
	editor: any;
	formInputEnabled?: boolean;
	formInputName?: string;
	onBlur?: (event: EventInfo, editor: TEditor) => void;
	onChange?: (event: EventInfo, editor: TEditor) => void;
	onFocus?: (event: EventInfo, editor: TEditor) => void;
	onReady?: (editor: TEditor) => void;
}) => {
	const [editorConfig, setEditorConfig] = useState(config);
	const [formInputValue, setFormInputValue] = useControlledState({
		defaultName: 'data',
		defaultValue: config?.initialData ?? '',
		handleName: '',
		name: 'data',
		value: data,
	});
	const [loading, setLoading] = useState(true);

	const firstRenderRef = useRef(true);

	useEffect(() => {
		if (firstRenderRef.current) {
			firstRenderRef.current = false;
		}
		else {
			return;
		}

		if (!editorConfig || !editorConfig.editorTransformerURLs) {
			setLoading(false);

			return;
		}

		const {extraPlugins, licenseKey, plugins} = editorConfig;

		loadEditorClientExtensions({
			config: editorConfig,
			onLoad: ({transformedConfig}: any) => {
				const cxExtraPlugins = transformedConfig.extraPlugins ?? [];

				setEditorConfig(() => ({
					...transformedConfig,
					extraPlugins: [...(extraPlugins ?? []), ...cxExtraPlugins],
					licenseKey,
					plugins,
				}));

				setLoading(false);
			},
		});
	}, [editorConfig]);

	return loading ? (
		<ClayLoadingIndicator />
	) : (
		<div
			className={`lfr-ck ${className ? className : ''} ${disabled ? 'lfr-ck-disabled' : ''}`}
		>
			<CKEditor
				config={editorConfig}
				data={data}
				disabled={disabled}
				editor={editor}
				onBlur={onBlur}
				onChange={(event, editor) => {
					setFormInputValue(editor.getData());

					if (onChange) {
						onChange(event, editor);
					}
				}}
				onFocus={onFocus}
				onReady={onReady}
			/>

			{formInputEnabled && formInputName && (
				<input
					name={formInputName}
					type="hidden"
					value={formInputValue ? String(formInputValue) : ''}
				/>
			)}
		</div>
	);
};

export default BaseEditor;
