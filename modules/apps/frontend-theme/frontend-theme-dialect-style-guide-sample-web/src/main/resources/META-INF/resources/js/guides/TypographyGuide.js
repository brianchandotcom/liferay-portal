/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

const SAMPLE_TEXT = 'The quick brown fox jumps over the lazy dog.';

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
						border={true}
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
						border={true}
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
						border={true}
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
						border={true}
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
						border={true}
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
						border={true}
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
						border={true}
						className={item}
						key={item}
						label={item}
						size="large"
					>
						{SAMPLE_TEXT}
					</TokenItem>
				))}
			</TokenGroup>

			<TokenGroup group="texts" title={Liferay.Language.get('other')}>
				<TokenItem border={true} label="mark, .mark" size="large">
					The <mark>quick brown fox</mark> jumps over the lazy dog.
				</TokenItem>
			</TokenGroup>
		</>
	);
};

export default TypographyGuide;
