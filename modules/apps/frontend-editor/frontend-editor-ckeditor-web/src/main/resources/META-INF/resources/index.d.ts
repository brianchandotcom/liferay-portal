/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import 'ckeditor4';

export function BalloonEditor({
	config,
	contents,
	name,
}: IBalloonEditorProps): JSX.Element;

export function ClassicEditor({
	contents,
	editorConfig,
	onChange,
	ref,
}: IClassicEditorProps): JSX.Element;

export interface IEditor {
	editor: CKEDITOR.editor;
}

interface IBalloonEditorProps {
	config?: CKEDITOR.config;
	contents: string;
	name: string;
}

interface IClassicEditorProps {
	contents: string;
	editorConfig: CKEDITOR.config;
	initialToolbarSet?: string;
	name: string;
	onChange: (content: string) => void;
	ref: React.RefObject<IEditor>;
	title?: string;
}
