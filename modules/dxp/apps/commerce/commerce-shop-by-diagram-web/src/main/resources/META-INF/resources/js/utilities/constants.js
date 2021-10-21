/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
	PINS_UPDATED: 'diagram-table-updated',
	REMOVE_PIN_HIGHLIGHT: 'diagram-remove-pin-highlight',
	SELECT_PIN: 'diagram-select-pin',
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
