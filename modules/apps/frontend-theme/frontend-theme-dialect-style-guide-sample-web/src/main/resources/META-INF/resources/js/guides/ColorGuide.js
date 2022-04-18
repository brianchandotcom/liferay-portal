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
	'accent-7-lighten',
];

const SAMPLE_TEXT = 'Sample';

const BACKGROUND_COLORS = [
	{
		color: BRAND_PRIMARY_COLORS,
		title: Liferay.Language.get('background-primary'),
	},
	{
		color: BRAND_SECONDARY_COLORS,
		title: Liferay.Language.get('background-secondary'),
	},
	{
		color: NEUTRAL_COLORS,
		title: Liferay.Language.get('background-neutral'),
	},
	{
		color: STATE_COLORS_SUCCESS,
		title: Liferay.Language.get('background-state-success'),
	},
	{
		color: STATE_COLORS_INFO,
		title: Liferay.Language.get('background-state-info'),
	},
	{
		color: STATE_COLORS_WARNING,
		title: Liferay.Language.get('background-state-warning'),
	},
	{
		color: STATE_COLORS_DANGER,
		title: Liferay.Language.get('background-state-danger'),
	},
	{
		color: ACCENT_COLORS,
		title: Liferay.Language.get('background-accent'),
	},
];
const BORDER_COLORS = [
	{
		color: BRAND_PRIMARY_COLORS,
		title: Liferay.Language.get('border-primary'),
	},
	{
		color: BRAND_SECONDARY_COLORS,
		title: Liferay.Language.get('border-secondary'),
	},
	{
		color: NEUTRAL_COLORS,
		title: Liferay.Language.get('border-neutral'),
	},
	{
		color: STATE_COLORS_SUCCESS,
		title: Liferay.Language.get('border-state-success'),
	},
	{
		color: STATE_COLORS_INFO,
		title: Liferay.Language.get('border-state-info'),
	},
	{
		color: STATE_COLORS_WARNING,
		title: Liferay.Language.get('border-state-warning'),
	},
	{
		color: STATE_COLORS_DANGER,
		title: Liferay.Language.get('border-state-danger'),
	},
	{
		color: ACCENT_COLORS,
		title: Liferay.Language.get('border-accent'),
	},
];
const TEXT_COLORS = [
	{
		color: BRAND_PRIMARY_COLORS,
		title: Liferay.Language.get('text-primary'),
	},
	{
		color: BRAND_SECONDARY_COLORS,
		title: Liferay.Language.get('text-secondary'),
	},
	{
		color: NEUTRAL_COLORS,
		title: Liferay.Language.get('text-neutral'),
	},
	{
		color: STATE_COLORS_SUCCESS,
		title: Liferay.Language.get('text-state-success'),
	},
	{
		color: STATE_COLORS_INFO,
		title: Liferay.Language.get('text-state-info'),
	},
	{
		color: STATE_COLORS_WARNING,
		title: Liferay.Language.get('text-state-warning'),
	},
	{
		color: STATE_COLORS_DANGER,
		title: Liferay.Language.get('text-state-danger'),
	},
	{
		color: ACCENT_COLORS,
		title: Liferay.Language.get('text-accent'),
	},
];

const ColorGroup = ({backgroundcolor, title}) => {
	return (
		<TokenGroup group="colors" title={title}>
			{backgroundcolor.map((item) => (
				<TokenItem
					border={true}
					className={`bg-${item}`}
					key={item}
					label={`bg-${item}`}
				/>
			))}
		</TokenGroup>
	);
};

const BorderGroup = ({borderColor, title}) => {
	return (
		<TokenGroup group="borders" title={title}>
			{borderColor.map((item) => (
				<TokenItem
					border={true}
					className={`rounded-sm border-${item}`}
					key={item}
					label={`border-${item}`}
				/>
			))}
		</TokenGroup>
	);
};

const TextGroup = ({textColor, title}) => {
	return (
		<TokenGroup group="texts" title={title}>
			{textColor.map((item) => (
				<TokenItem
					border={true}
					className={`text-${item}`}
					key={item}
					label={`text-${item}`}
				>
					{SAMPLE_TEXT}
				</TokenItem>
			))}
		</TokenGroup>
	);
};

const ColorGuide = () => {
	return (
		<>
			{BACKGROUND_COLORS.map((item, index) => {
				return (
					<ColorGroup
						backgroundcolor={item.color}
						key={index}
						title={item.title}
					/>
				);
			})}

			{BORDER_COLORS.map((item, index) => {
				return (
					<BorderGroup
						borderColor={item.color}
						key={index}
						title={item.title}
					/>
				);
			})}

			{TEXT_COLORS.map((item, index) => {
				return (
					<TextGroup
						key={index}
						textColor={item.color}
						title={item.title}
					/>
				);
			})}
		</>
	);
};

export default ColorGuide;
