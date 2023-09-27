/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	LinkedToCatalogProductFormGroup,
	LinkedToDiagramFormGroup,
	LinkedToExternalProductFormGroup,
} from '../components/Form';

export const DIAGRAM_EVENTS = {
	DIAGRAM_UPDATED: 'diagram-updated',
};

export const DIAGRAM_TABLE_EVENTS = {
	HIGHLIGHT_PIN: 'diagram-highlight-pin',
	REMOVE_PIN_HIGHLIGHT: 'diagram-remove-pin-highlight',
	SELECT_PIN: 'diagram-select-pin',
	TABLE_UPDATED: 'diagram-table-updated',
};

export const RADIUS_SIZES = [
	{
		label: Liferay.Language.get('small'),
		size: 10,
		value: 'small',
	},
	{
		label: Liferay.Language.get('medium'),
		size: 20,
		value: 'medium',
	},
	{
		label: Liferay.Language.get('large'),
		size: 30,
		value: 'large',
	},
	{
		label: Liferay.Language.get('custom'),
		value: 'custom',
	},
];

export const DRAG_AND_DROP_THRESHOLD = 20;

export const ZOOM_STEP = 0.25;

export const ZOOM_VALUES = [0.5, 0.75, 1, 1.25, 1.5, 1.75, 2, 3, 5];

export const HEADERS = new Headers({
	'Accept': 'application/json',
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
	'Content-Type': 'application/json',
});

export const PINS_RADIUS = {
	DEFAULT: 1,
	MAX: 3,
	MIN: 0.5,
	OPTIONS: {
		large: {
			label: Liferay.Language.get('large'),
			value: 2,
		},
		medium: {
			label: Liferay.Language.get('medium'),
			value: 1,
		},
		small: {
			label: Liferay.Language.get('small'),
			value: 0.5,
		},
	},
	STEP: 0.25,
};

export const PINS_CIRCLE_RADIUS = 15;

export const DEFAULT_LINK_OPTION = 'sku';

export const LINKING_OPTIONS = {
	diagram: {
		component: LinkedToDiagramFormGroup,
		label: Liferay.Language.get('linked-to-a-diagram'),
		value: 'diagram',
	},
	external: {
		component: LinkedToExternalProductFormGroup,
		label: Liferay.Language.get('not-linked-to-a-catalog'),
		value: 'external',
	},
	sku: {
		component: LinkedToCatalogProductFormGroup,
		label: Liferay.Language.get('linked-to-a-sku'),
		value: 'sku',
	},
};

export const DIAGRAM_LABELS_MAX_LENGTH = 6;

export const ZOOM_DISABLED = process.env.NODE_ENV === 'test';

export const TRANSITIONS_DISABLED = ZOOM_DISABLED;
