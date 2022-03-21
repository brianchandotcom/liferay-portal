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

const ColorGuide = () => {
	return (
		<>
			<TokenGroup
				group="colors"
				title={Liferay.Language.get('background-primary')}
			>
				{BRAND_PRIMARY_COLORS.map((item) => (
					<TokenItem
						border={true}
						className={`bg-${item}`}
						key={item}
						label={`bg-${item}`}
					/>
				))}
			</TokenGroup>

			<TokenGroup
				group="colors"
				title={Liferay.Language.get('background-secondary')}
			>
				{BRAND_SECONDARY_COLORS.map((item) => (
					<TokenItem
						border={true}
						className={`bg-${item}`}
						key={item}
						label={`bg-${item}`}
					/>
				))}
			</TokenGroup>

			<TokenGroup
				group="colors"
				title={Liferay.Language.get('background-neutral')}
			>
				{NEUTRAL_COLORS.map((item) => (
					<TokenItem
						border={true}
						className={`bg-${item}`}
						key={item}
						label={`bg-${item}`}
					/>
				))}
			</TokenGroup>

			<TokenGroup
				group="colors"
				title={Liferay.Language.get('background-accent')}
			>
				{ACCENT_COLORS.map((item) => (
					<TokenItem
						border={true}
						className={`bg-${item}`}
						key={item}
						label={`bg-${item}`}
					/>
				))}
			</TokenGroup>

			<TokenGroup
				group="colors"
				title={Liferay.Language.get('background-state-success')}
			>
				{STATE_COLORS_SUCCESS.map((item) => (
					<TokenItem
						border={true}
						className={`bg-${item}`}
						key={item}
						label={`bg-${item}`}
					/>
				))}
			</TokenGroup>

			<TokenGroup
				group="colors"
				title={Liferay.Language.get('background-state-info')}
			>
				{STATE_COLORS_INFO.map((item) => (
					<TokenItem
						border={true}
						className={`bg-${item}`}
						key={item}
						label={`bg-${item}`}
					/>
				))}
			</TokenGroup>

			<TokenGroup
				group="colors"
				title={Liferay.Language.get('background-state-warning')}
			>
				{STATE_COLORS_WARNING.map((item) => (
					<TokenItem
						border={true}
						className={`bg-${item}`}
						key={item}
						label={`bg-${item}`}
					/>
				))}
			</TokenGroup>

			<TokenGroup
				group="colors"
				title={Liferay.Language.get('background-state-danger')}
			>
				{STATE_COLORS_DANGER.map((item) => (
					<TokenItem
						border={true}
						className={`bg-${item}`}
						key={item}
						label={`bg-${item}`}
					/>
				))}
			</TokenGroup>

			<TokenGroup
				group="texts"
				title={Liferay.Language.get('text-primary')}
			>
				{BRAND_PRIMARY_COLORS.map((item) => (
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

			<TokenGroup
				group="texts"
				title={Liferay.Language.get('text-secondary')}
			>
				{BRAND_SECONDARY_COLORS.map((item) => (
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

			<TokenGroup
				group="texts"
				title={Liferay.Language.get('text-neutral')}
			>
				{NEUTRAL_COLORS.map((item) => (
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

			<TokenGroup
				group="texts"
				title={Liferay.Language.get('text-accent')}
			>
				{ACCENT_COLORS.map((item) => (
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

			<TokenGroup
				group="texts"
				title={Liferay.Language.get('text-state-success')}
			>
				{STATE_COLORS_SUCCESS.map((item) => (
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

			<TokenGroup
				group="texts"
				title={Liferay.Language.get('text-state-info')}
			>
				{STATE_COLORS_INFO.map((item) => (
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

			<TokenGroup
				group="texts"
				title={Liferay.Language.get('text-state-warning')}
			>
				{STATE_COLORS_WARNING.map((item) => (
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

			<TokenGroup
				group="texts"
				title={Liferay.Language.get('text-state-danger')}
			>
				{STATE_COLORS_DANGER.map((item) => (
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
		</>
	);
};

export default ColorGuide;
