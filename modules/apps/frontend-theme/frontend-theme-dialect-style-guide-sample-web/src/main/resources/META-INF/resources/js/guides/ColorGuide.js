/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import TokenGroup from '../components/TokenGroup';
import TokenItem from '../components/TokenItem';

const BRAND_PRIMARY_COLORS = [
	'brand-primary-darken-5',
	'brand-primary-darken-4',
	'brand-primary-darken-3',
	'brand-primary-darken-2',
	'brand-primary-darken-1',
	'brand-primary',
	'brand-primary-lighten-1',
	'brand-primary-lighten-2',
	'brand-primary-lighten-3',
	'brand-primary-lighten-4',
	'brand-primary-lighten-5',
	'brand-primary-lighten-6',
];

const BRAND_SECONDARY_COLORS = [
	'brand-secondary-darken-5',
	'brand-secondary-darken-4',
	'brand-secondary-darken-3',
	'brand-secondary-darken-2',
	'brand-secondary-darken-1',
	'brand-secondary',
	'brand-secondary-lighten-1',
	'brand-secondary-lighten-2',
	'brand-secondary-lighten-3',
	'brand-secondary-lighten-4',
	'brand-secondary-lighten-5',
	'brand-secondary-lighten-6',
];

const NEUTRAL_COLORS = [
	'neutral-0',
	'neutral-1',
	'neutral-2',
	'neutral-3',
	'neutral-4',
	'neutral-5',
	'neutral-6',
	'neutral-7',
	'neutral-8',
	'neutral-9',
	'neutral-10',
];

const STATE_COLORS_SUCCESS = [
	'success-darken-2',
	'success-darken-1',
	'success',
	'success-lighten-1',
	'success-lighten-2',
];

const STATE_COLORS_INFO = [
	'info-darken-2',
	'info-darken-1',
	'info',
	'info-lighten-1',
	'info-lighten-2',
];

const STATE_COLORS_WARNING = [
	'warning-darken-2',
	'warning-darken-1',
	'warning',
	'warning-lighten-1',
	'warning-lighten-2',
];

const STATE_COLORS_DANGER = [
	'danger-darken-2',
	'danger-darken-1',
	'danger',
	'danger-lighten-1',
	'danger-lighten-2',
];

const ACCENT_COLORS = [
	'accent-1',
	'accent-1-lighten',
	'accent-2',
	'accent-2-lighten',
	'accent-3',
	'accent-3-lighten',
	'accent-4',
	'accent-4-lighten',
	'accent-5',
	'accent-5-lighten',
	'accent-6',
	'accent-6-lighten',
];

const SAMPLE_TEXT = 'Sample';

const BACKGROUND_COLORS = [
	{
		colors: BRAND_PRIMARY_COLORS,
		title: Liferay.Language.get('background-primary'),
	},
	{
		colors: BRAND_SECONDARY_COLORS,
		title: Liferay.Language.get('background-secondary'),
	},
	{
		colors: NEUTRAL_COLORS,
		title: Liferay.Language.get('background-neutral'),
	},
	{
		colors: STATE_COLORS_SUCCESS,
		title: Liferay.Language.get('background-state-success'),
	},
	{
		colors: STATE_COLORS_INFO,
		title: Liferay.Language.get('background-state-info'),
	},
	{
		colors: STATE_COLORS_WARNING,
		title: Liferay.Language.get('background-state-warning'),
	},
	{
		colors: STATE_COLORS_DANGER,
		title: Liferay.Language.get('background-state-danger'),
	},
	{
		colors: ACCENT_COLORS,
		title: Liferay.Language.get('background-accent'),
	},
];

const BORDER_COLORS = [
	{
		colors: BRAND_PRIMARY_COLORS,
		title: Liferay.Language.get('border-primary'),
	},
	{
		colors: BRAND_SECONDARY_COLORS,
		title: Liferay.Language.get('border-secondary'),
	},
	{
		colors: NEUTRAL_COLORS,
		title: Liferay.Language.get('border-neutral'),
	},
	{
		colors: STATE_COLORS_SUCCESS,
		title: Liferay.Language.get('border-state-success'),
	},
	{
		colors: STATE_COLORS_INFO,
		title: Liferay.Language.get('border-state-info'),
	},
	{
		colors: STATE_COLORS_WARNING,
		title: Liferay.Language.get('border-state-warning'),
	},
	{
		colors: STATE_COLORS_DANGER,
		title: Liferay.Language.get('border-state-danger'),
	},
	{
		colors: ACCENT_COLORS,
		title: Liferay.Language.get('border-accent'),
	},
];

const TEXT_COLORS = [
	{
		colors: BRAND_PRIMARY_COLORS,
		title: Liferay.Language.get('text-primary'),
	},
	{
		colors: BRAND_SECONDARY_COLORS,
		title: Liferay.Language.get('text-secondary'),
	},
	{
		colors: NEUTRAL_COLORS,
		title: Liferay.Language.get('text-neutral'),
	},
	{
		colors: STATE_COLORS_SUCCESS,
		title: Liferay.Language.get('text-state-success'),
	},
	{
		colors: STATE_COLORS_INFO,
		title: Liferay.Language.get('text-state-info'),
	},
	{
		colors: STATE_COLORS_WARNING,
		title: Liferay.Language.get('text-state-warning'),
	},
	{
		colors: STATE_COLORS_DANGER,
		title: Liferay.Language.get('text-state-danger'),
	},
	{
		colors: ACCENT_COLORS,
		title: Liferay.Language.get('text-accent'),
	},
];

const ColorGuide = () => {
	return (
		<>
			{BACKGROUND_COLORS.map((item, index) => (
				<TokenGroup group="colors" key={index} title={item.title}>
					{item.colors.map((color) => (
						<TokenItem
							border={true}
							className={`bg-${color}`}
							key={color}
							label={`bg-${color}`}
						/>
					))}
				</TokenGroup>
			))}

			{BORDER_COLORS.map((item, index) => (
				<TokenGroup group="colors" key={index} title={item.title}>
					{item.colors.map((color) => (
						<TokenItem
							border={true}
							className={`border-${color}`}
							key={color}
							label={`border-${color}`}
						/>
					))}
				</TokenGroup>
			))}

			{TEXT_COLORS.map((item, index) => (
				<TokenGroup group="colors" key={index} title={item.title}>
					{item.colors.map((color) => (
						<TokenItem
							border={true}
							className={`text-${color}`}
							key={color}
							label={`text-${color}`}
						>
							{SAMPLE_TEXT}
						</TokenItem>
					))}
				</TokenGroup>
			))}
		</>
	);
};

export default ColorGuide;
