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

import classNames from 'classnames';
import React from 'react';

import TokenGroup from '../components/TokenGroup';
import TokenItem from '../components/TokenItem';

const BORDERS = [
	'rounded-xs',
	'rounded-sm',
	'rounded',
	'rounded-lg',
	'rounded-xl',
	'rounded-xxl',
	'rounded-circle',
	'rounded-pill',
];

const RATIOS = [
	'aspect-ratio',
	'aspect-ratio-16-to-9',
	'aspect-ratio-8-to-3',
	'aspect-ratio-4-to-3',
];

const SHADOWS = ['shadow-sm', 'shadow', 'shadow-lg'];

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

const GeneralGuide = () => {
	return (
		<>
			<TokenGroup group="spacers" title={Liferay.Language.get('spacers')}>
				{SPACERS.map((item) => (
					<TokenItem
						className={item.replace('spacer', 'pr')}
						key={item}
						label={item}
						size="large"
					/>
				))}
			</TokenGroup>

			<TokenGroup group="borders" title={Liferay.Language.get('borders')}>
				{BORDERS.map((item) => (
					<TokenItem className={item} key={item} label={item} />
				))}
			</TokenGroup>

			<TokenGroup
				group="shadows"
				title={Liferay.Language.get('box-shadow')}
			>
				{SHADOWS.map((item) => (
					<TokenItem className={item} key={item} label={item} />
				))}
			</TokenGroup>

			<TokenGroup
				group="ratios"
				title={Liferay.Language.get('aspect-ratios')}
			>
				{RATIOS.map((item) => (
					<TokenItem key={item} label={item} size="medium">
						<span
							className={classNames('aspect-ratio', item)}
						></span>
					</TokenItem>
				))}
			</TokenGroup>
		</>
	);
};

export default GeneralGuide;
