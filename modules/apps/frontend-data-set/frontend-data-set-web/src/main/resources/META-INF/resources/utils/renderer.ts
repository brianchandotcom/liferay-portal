/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ComponentType} from 'react';

// @ts-ignore

import InputCheckboxRenderer from '../cell_renderers/InputCheckboxRenderer';

// @ts-ignore

import InputDateTimeRenderer from '../cell_renderers/InputDateTimeRenderer';

// @ts-ignore

import InputTextRenderer from '../cell_renderers/InputTextRenderer';

const INPUT_RENDERERS: {[key: string]: ComponentType} = {
	checkbox: InputCheckboxRenderer,
	dateTime: InputDateTimeRenderer,
	text: InputTextRenderer,
};

export function getInputRendererById(id: string): ComponentType {
	const inputRenderer = INPUT_RENDERERS[id];

	if (!inputRenderer) {
		throw new Error(`No input renderer found with id "${id}"`);
	}

	return inputRenderer;
}
