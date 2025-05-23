/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {BalloonEditor as BaseBalloonEditor, EventInfo} from 'ckeditor5';
import React from 'react';

import BaseEditor, {TBaseEditor} from './BaseEditor';
import getDefaultEditorConfig from './utils/getDefaultEditorConfig';
import {
	EEditorConfigPreset,
	EEditorType,
	LiferayEditorConfig,
} from './utils/types';

const BalloonEditor = ({
	className,
	config,
	data,
	onChange,
	onReady,
}: {
	className?: string;
	config?: LiferayEditorConfig;
	data?: string;
	onChange?: (event: EventInfo, editor: TBaseEditor) => void;
	onReady?: (editor: TBaseEditor) => void;
}) => {
	return (
		<BaseEditor
			className={className}
			config={{
				...getDefaultEditorConfig({
					editorType: EEditorType.BALLOON,
					preset: config?.preset || EEditorConfigPreset.ADVANCED,
				}),
				...config,
			}}
			data={data}
			editor={BaseBalloonEditor}
			onChange={onChange}
			onReady={onReady}
		/>
	);
};

export default BalloonEditor;
