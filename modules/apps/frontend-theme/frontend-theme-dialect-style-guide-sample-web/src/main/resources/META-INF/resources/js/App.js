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

import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import React, {useState} from 'react';

import '../css/main.scss';

import '../css/tokens.scss';

const BORDERS = [
	'border-radius',
	'border-radius-sm',
	'border-radius-lg',
	'border-radius-rounded',
	'border-radius-circle',
	'border-radius-pill',
];

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

const GRAYS = [
	'white',
	'gray-100',
	'gray-200',
	'gray-300',
	'gray-400',
	'gray-500',
	'gray-600',
	'gray-700',
	'gray-800',
	'gray-900',
	'black',
	'transparent',
];

const HEADINGS = ['h1', 'h2', 'h3', 'h4', 'h5', 'h6'];

const SAMPLE_TEXT = 'The quick brown fox jumps over the lazy dog';

const SHADOWS = ['shadow', 'shadow-sm', 'shadow-lg'];

const SPACERS = [
	'spacer-1',
	'spacer-2',
	'spacer-3',
	'spacer-4',
	'spacer-5',
	'spacer-6',
	'spacer-7',
	'spacer-8',
	'spacer-9',
	'spacer-10',
];

const RATIOS = [
	'aspect-ratio',
	'aspect-ratio-16-to-9',
	'aspect-ratio-8-to-3',
	'aspect-ratio-4-to-3',
];

const THEME_COLORS = [
	'primary',
	'secondary',
	'success',
	'info',
	'warning',
	'danger',
	'light',
	'lighter',
	'gray',
	'dark',
];

const BUTTONS = [
	'primary',
	'secondary',
	'outline-primary',
	'outline-secondary',
	'link',
];

const TokenGroup = ({children, group, medium, title}) => {
	return (
		<ClayLayout.Col
			className={'token-group token-group-' + group}
			md={medium}
			size={!medium && '12'}
		>
			{title && <h2>{title}</h2>}
			{children && <div className="token-items">{children}</div>}
		</ClayLayout.Col>
	);
};

const TokenItem = ({children, label, sample}) => {
	return (
		<div className="token-item">
			<span className={classNames('token-sample', sample)}>
				{children}
			</span>

			{(label || sample) && (
				<span className="token-label">{label || sample}</span>
			)}
		</div>
	);
};

export default function App() {
	const [fade, setFade] = useState(false);
	const [collapse, setCollapse] = useState(false);

	return (
		<div className="ccp">
			<ClayLayout.ContainerFluid>
				<ClayLayout.Row>
					<ClayLayout.Col>
						<h1>{Liferay.Language.get('style-guide-sample')}</h1>
					</ClayLayout.Col>

					<TokenGroup
						group="colors"
						title={Liferay.Language.get('grays')}
					>
						{GRAYS.map((item) => (
							<TokenItem
								key={item}
								label={item}
								sample={`bg-${item}`}
							/>
						))}
					</TokenGroup>

					<TokenGroup
						group="colors"
						title={Liferay.Language.get('theme-colors')}
					>
						{THEME_COLORS.map((item) => (
							<TokenItem
								key={item}
								label={item}
								sample={`bg-${item}`}
							/>
						))}
					</TokenGroup>

					<TokenGroup
						group="spacers"
						md="4"
						title={Liferay.Language.get('spacers')}
					>
						{SPACERS.map((item) => (
							<TokenItem
								key={item}
								label={item}
								sample={item.replace('spacer', 'pr')}
							/>
						))}
					</TokenGroup>

					<TokenGroup
						group="borders"
						md="4"
						title={Liferay.Language.get('borders')}
					>
						{BORDERS.map((item) => (
							<TokenItem
								key={item}
								label={item}
								sample={item.replace(
									'border-radius',
									'rounded'
								)}
							/>
						))}
					</TokenGroup>

					<TokenGroup
						group="shadows"
						md="4"
						title={Liferay.Language.get('box-shadow')}
					>
						{SHADOWS.map((item) => (
							<TokenItem key={item} sample={item} />
						))}
					</TokenGroup>

					<TokenGroup
						group="ratios"
						md="4"
						title={Liferay.Language.get('aspect-ratios')}
					>
						{RATIOS.map((item) => (
							<TokenItem key={item} label={item}>
								<span
									className={classNames('aspect-ratio', item)}
								></span>
							</TokenItem>
						))}
					</TokenGroup>

					<TokenGroup
						group="transitions"
						md="4"
						title={Liferay.Language.get('transitions')}
					>
						<label className="token-item">
							<span className="token-sample">
								<span
									className={classNames('fade', {
										show: !fade,
									})}
								></span>
							</span>
							<span className="token-label">transition-fade</span>
							<input
								onChange={() => setFade(!fade)}
								type="checkbox"
								value={fade}
							/>
						</label>
						<label className="token-item">
							<span className="token-sample">
								<span
									className={classNames('collapsing', {
										show: !collapse,
									})}
								></span>
							</span>
							<span className="token-label">
								transition-collapse
							</span>
							<input
								onChange={() => setCollapse(!collapse)}
								type="checkbox"
								value={collapse}
							/>
						</label>
					</TokenGroup>

					<TokenGroup
						group="texts"
						md="4"
						title={Liferay.Language.get('font-families')}
					>
						{FONT_FAMILIES.map((item) => (
							<TokenItem key={item} sample={item}>
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
							<TokenItem key={item} sample={item}>
								{SAMPLE_TEXT}
							</TokenItem>
						))}
					</TokenGroup>

					<TokenGroup
						group="texts"
						md="6"
						title={Liferay.Language.get('headings')}
					>
						{HEADINGS.map((item) => (
							<TokenItem key={item} sample={item}>
								{SAMPLE_TEXT}
							</TokenItem>
						))}
					</TokenGroup>

					<TokenGroup
						group="texts"
						title={Liferay.Language.get('displays')}
					>
						{DISPLAYS.map((item) => (
							<TokenItem key={item} sample={item}>
								{SAMPLE_TEXT}
							</TokenItem>
						))}
					</TokenGroup>

					<TokenGroup
						group="texts"
						title={Liferay.Language.get('others')}
					>
						<TokenItem sample="lead">{SAMPLE_TEXT}</TokenItem>
						<TokenItem sample="text-muted">{SAMPLE_TEXT}</TokenItem>
						<TokenItem label="blockquote">
							<span className="blockquote">{SAMPLE_TEXT}</span>
							<span className="blockquote-footer">Liferay</span>
						</TokenItem>
						<TokenItem label="separator">
							<hr />
						</TokenItem>
					</TokenGroup>

					<TokenGroup
						group="buttons"
						title={Liferay.Language.get('buttons')}
					>
						{BUTTONS.map((item) => (
							<TokenItem key={item} sample={item}>
								<button className={'btn btn-' + item}>
									Button
								</button>
							</TokenItem>
						))}
					</TokenGroup>
				</ClayLayout.Row>
			</ClayLayout.ContainerFluid>
		</div>
	);
}
