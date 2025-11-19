/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EventInfo} from '@ckeditor/ckeditor5-utils/dist/index.js';
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
	name,
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
	name?: string;
	onBlur?: (event: EventInfo, editor: TEditor) => void;
	onChange?: (event: EventInfo, editor: TEditor) => void;
	onFocus?: (event: EventInfo, editor: TEditor) => void;
	onReady?: (editor: TEditor) => void;
}) => {
	const [loading, setLoading] = useState(true);
	const [value, setValue] = useState(() => {
		const initialValue = data ?? config?.initialData;

		return typeof initialValue === 'string' ? initialValue : '';
	});

	const firstRenderRef = useRef(true);

	const [editorConfig, setEditorConfig] = useState(config);

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

	useEffect(() => {
		setValue(data ?? '');
	}, [data]);

	return loading ? (
		<ClayLoadingIndicator />
	) : (
		<div className={`lfr-ck ${className ? className : ''}`}>
			<CKEditor
				config={editorConfig}
				data={value}
				disabled={disabled}
				editor={editor}
				onBlur={onBlur}
				onChange={(event, editor) => {
					setValue(editor.getData());

					if (onChange) {
						onChange(event, editor);
					}
				}}
				onFocus={onFocus}
				onReady={onReady}
			/>

			{name && <input name={name} type="hidden" value={value} />}
		</div>
	);
};

export default BaseEditor;
