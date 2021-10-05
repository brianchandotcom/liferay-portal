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
import React, {useState} from 'react';

import TokenGroup from '../components/TokenGroup';
import TokenItem from '../components/TokenItem';

const BORDERS = [
	'border-radius',
	'border-radius-sm',
	'border-radius-lg',
	'border-radius-rounded',
	'border-radius-circle',
	'border-radius-pill',
];

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

const GeneralGuide = () => {
	const [fade, setFade] = useState(false);
	const [collapse, setCollapse] = useState(false);

	return (
		<>
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
						sample={item.replace('border-radius', 'rounded')}
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
					<span className="token-label">transition-collapse</span>
					<input
						onChange={() => setCollapse(!collapse)}
						type="checkbox"
						value={collapse}
					/>
				</label>
			</TokenGroup>
		</>
	);
};

export default GeneralGuide;
