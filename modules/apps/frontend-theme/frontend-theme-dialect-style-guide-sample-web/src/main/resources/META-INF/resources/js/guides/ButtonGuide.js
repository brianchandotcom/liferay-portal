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

const BUTTON_VARIANTS = [
	{
		buttons: [
			'btn btn-primary',
			'btn btn-secondary',
			'btn btn-outline-primary',
			'btn btn-outline-secondary',
			'btn btn-link',
		],
		categoryTitle: Liferay.Language.get('buttons'),
		inverted: false,
	},
	{
		buttons: [
			'btn btn-primary btn-sm',
			'btn btn-primary',
			'btn btn-primary btn-lg',
		],
		categoryTitle: Liferay.Language.get('button-sizes'),
		inverted: false,
	},
	{
		buttons: [
			'btn btn-solid btn-variant-primary',
			'btn btn-solid btn-variant-secondary',
			'btn btn-solid btn-variant-neutral',
		],
		categoryTitle: Liferay.Language.get('button-solid'),
		inverted: false,
	},
	{
		buttons: [
			'btn btn-ghost btn-variant-primary',
			'btn btn-ghost btn-variant-secondary',
			'btn btn-ghost btn-variant-neutral',
		],
		categoryTitle: Liferay.Language.get('button-ghost'),
		inverted: false,
	},
	{
		buttons: [
			'btn btn-ghost btn-inverted btn-variant-primary',
			'btn btn-ghost btn-inverted btn-variant-secondary',
			'btn btn-ghost btn-inverted btn-variant-neutral',
		],
		categoryTitle: Liferay.Language.get('button-ghost-inverted'),
		inverted: true,
	},
	{
		buttons: [
			'btn btn-borderless btn-variant-primary',
			'btn btn-borderless btn-variant-secondary',
			'btn btn-borderless btn-variant-neutral',
		],
		categoryTitle: Liferay.Language.get('button-borderless'),
		inverted: false,
	},
	{
		buttons: [
			'btn btn-borderless btn-inverted btn-variant-primary',
			'btn btn-borderless btn-inverted btn-variant-secondary',
			'btn btn-borderless btn-inverted btn-variant-neutral',
		],
		categoryTitle: Liferay.Language.get('button-borderless-inverted'),
		inverted: true,
	},
	{
		buttons: [
			'btn btn-rounded btn-variant-primary',
			'btn btn-rounded btn-variant-secondary',
			'btn btn-rounded btn-variant-neutral',
		],
		categoryTitle: Liferay.Language.get('button-rounded'),
		inverted: false,
	},
	{
		buttons: [
			'btn btn-rounded btn-inverted btn-variant-primary',
			'btn btn-rounded btn-inverted btn-variant-secondary',
			'btn btn-rounded btn-inverted btn-variant-neutral',
		],
		categoryTitle: Liferay.Language.get('button-rounded-inverted'),
		inverted: true,
	},
];

const ButtonGuide = () => {
	return (
		<>
			{BUTTON_VARIANTS.map((item, key) => (
				<TokenGroup
					className={classNames({
						['bg-neutral-7 px-2']: item.inverted,
					})}
					group="buttons"
					key={key}
					title={item.categoryTitle}
				>
					{item.buttons.map((button) => (
						<TokenItem key={button} label={button} size="medium">
							<button className={button}>Button</button>
						</TokenItem>
					))}
				</TokenGroup>
			))}
		</>
	);
};

export default ButtonGuide;
