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

const DISPLAYS = ['display-1', 'display-2', 'display-3', 'display-4'];

const FONT_FAMILIES = [
	'font-family-sans-serif',
	'font-family-monospace',
	'font-family-base',
];

const FONT_WEIGHTS = [
	'font-weight-lighter',
	'font-weight-light',
	'font-weight-normal',
	'font-weight-semi-bold',
	'font-weight-bold',
	'font-weight-bolder',
];

const HEADINGS = ['h1', 'h2', 'h3', 'h4', 'h5', 'h6'];

const LINKS = ['text-link-lg', 'text-link-md', 'text-link-sm'];

const PARAGRAPHS = [
	'text-paragraph-lg',
	'text-paragraph',
	'text-paragraph-sm',
	'text-paragraph-xs',
	'text-paragraph-xxs',
];

const SAMPLE_TEXT = 'The quick brown fox jumps over the lazy dog';

const SMALL_CAPS = ['text-small-caps'];

const TypographyGuide = () => {
	return (
		<>
			<TokenGroup
				group="texts"
				title={Liferay.Language.get('font-families')}
			>
				{FONT_FAMILIES.map((item) => (
					<TokenItem
						className={item}
						key={item}
						label={item}
						size="large"
					>
						{SAMPLE_TEXT}
					</TokenItem>
				))}
			</TokenGroup>

			<TokenGroup
				group="texts"
				md="6"
				title={Liferay.Language.get('font-weights')}
			>
				{FONT_WEIGHTS.map((item) => (
					<TokenItem
						className={item}
						key={item}
						label={item}
						size="large"
					>
						{SAMPLE_TEXT}
					</TokenItem>
				))}
			</TokenGroup>

			<TokenGroup group="texts" title={Liferay.Language.get('displays')}>
				{DISPLAYS.map((item) => (
					<TokenItem
						className={item}
						key={item}
						label={item}
						size="large"
					>
						{SAMPLE_TEXT}
					</TokenItem>
				))}
			</TokenGroup>

			<TokenGroup group="texts" title={Liferay.Language.get('headings')}>
				{HEADINGS.map((item) => (
					<TokenItem
						className={item}
						key={item}
						label={item}
						size="large"
					>
						{SAMPLE_TEXT}
					</TokenItem>
				))}
			</TokenGroup>

			<TokenGroup
				group="texts"
				title={Liferay.Language.get('paragraphs')}
			>
				{PARAGRAPHS.map((item) => (
					<TokenItem
						className={item}
						key={item}
						label={item}
						size="large"
					>
						{SAMPLE_TEXT}
					</TokenItem>
				))}
			</TokenGroup>

			<TokenGroup
				group="texts"
				title={Liferay.Language.get('small-caps')}
			>
				{SMALL_CAPS.map((item) => (
					<TokenItem
						className={item}
						key={item}
						label={item}
						size="large"
					>
						{SAMPLE_TEXT}
					</TokenItem>
				))}
			</TokenGroup>

			<TokenGroup group="texts" title={Liferay.Language.get('links')}>
				{LINKS.map((item) => (
					<TokenItem
						className={item}
						key={item}
						label={item}
						size="large"
					>
						{SAMPLE_TEXT}
					</TokenItem>
				))}
			</TokenGroup>
		</>
	);
};

export default TypographyGuide;
