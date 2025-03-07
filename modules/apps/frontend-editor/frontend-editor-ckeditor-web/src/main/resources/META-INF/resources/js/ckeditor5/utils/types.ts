/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EditorConfig} from 'ckeditor5';

export enum EClassicEditorConfigPreset {
	BASIC = 'basic',
	ADVANCED = 'advanced',
}
export interface ClassicEditorConfig extends EditorConfig {
	filebrowserImageBrowseUrl?: string;
	filebrowserVideoBrowseUrl?: string;
	itemSelectorEventName?: string;
	preset?: EClassicEditorConfigPreset;
}
