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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React from 'react';

import TokenGroup from '../components/TokenGroup';
import TokenItem from '../components/TokenItem';

const BUTTON_VARIANTS = [
	{
		buttons: [
			{
				displayType: 'primary',
			},
			{
				displayType: 'secondary',
			},
			{
				displayType: 'outline-primary',
			},
			{
				displayType: 'outline-secondary',
			},
			{
				displayType: 'link',
			},
		],
		categoryTitle: Liferay.Language.get('buttons'),
		inverted: false,
	},
	{
		buttons: [
			{
				displayType: 'primary',
				size: 'sm',
			},
			{
				displayType: 'primary',
			},
			{
				displayType: 'primary',
				size: 'lg',
			},
		],
		categoryTitle: Liferay.Language.get('button-sizes'),
		inverted: false,
	},
	{
		buttons: [
			{
				displayType: 'primary',
				icon: 'before',
				size: 'sm',
			},
			{
				displayType: 'primary',
				icon: 'before',
			},
			{
				displayType: 'primary',
				icon: 'before',
				size: 'lg',
			},
			{
				displayType: 'primary',
				icon: 'after',
				size: 'sm',
			},
			{
				displayType: 'primary',
				icon: 'after',
			},
			{
				displayType: 'primary',
				icon: 'after',
				size: 'lg',
			},
		],
		categoryTitle: Liferay.Language.get('button-with-icons'),
		inverted: false,
	},
	{
		buttons: [
			{
				displayType: 'solid',
				variant: 'primary',
			},
			{
				displayType: 'solid',
				variant: 'secondary',
			},
			{
				displayType: 'solid',
				variant: 'neutral',
			},
		],
		categoryTitle: Liferay.Language.get('button-solid'),
		inverted: false,
	},
	{
		buttons: [
			{
				displayType: 'solid',
				variant: 'primary',
			},
			{
				displayType: 'solid',
				variant: 'secondary',
			},
			{
				displayType: 'solid',
				variant: 'neutral',
			},
		],
		categoryTitle: Liferay.Language.get('button-solid-inverted'),
		inverted: true,
	},
	{
		buttons: [
			{
				displayType: 'ghost',
				variant: 'primary',
			},
			{
				displayType: 'ghost',
				variant: 'secondary',
			},
			{
				displayType: 'ghost',
				variant: 'neutral',
			},
		],
		categoryTitle: Liferay.Language.get('button-ghost'),
		inverted: false,
	},
	{
		buttons: [
			{
				displayType: 'ghost',
				variant: 'primary',
			},
			{
				displayType: 'ghost',
				variant: 'secondary',
			},
			{
				displayType: 'ghost',
				variant: 'neutral',
			},
		],
		categoryTitle: Liferay.Language.get('button-ghost-inverted'),
		inverted: true,
	},
	{
		buttons: [
			{
				displayType: 'borderless',
				variant: 'primary',
			},
			{
				displayType: 'borderless',
				variant: 'secondary',
			},
			{
				displayType: 'borderless',
				variant: 'neutral',
			},
		],
		categoryTitle: Liferay.Language.get('button-borderless'),
		inverted: false,
	},
	{
		buttons: [
			{
				displayType: 'borderless',
				variant: 'primary',
			},
			{
				displayType: 'borderless',
				variant: 'secondary',
			},
			{
				displayType: 'borderless',
				variant: 'neutral',
			},
		],
		categoryTitle: Liferay.Language.get('button-borderless-inverted'),
		inverted: true,
	},
	{
		buttons: [
			{
				displayType: 'rounded',
				variant: 'primary',
			},
			{
				displayType: 'rounded',
				variant: 'secondary',
			},
			{
				displayType: 'rounded',
				variant: 'neutral',
			},
		],
		categoryTitle: Liferay.Language.get('button-rounded'),
		inverted: false,
	},
	{
		buttons: [
			{
				displayType: 'rounded',
				variant: 'primary',
			},
			{
				displayType: 'rounded',
				variant: 'secondary',
			},
			{
				displayType: 'rounded',
				variant: 'neutral',
			},
		],
		categoryTitle: Liferay.Language.get('button-rounded-inverted'),
		inverted: true,
	},
];

function getLabel(button, inverted) {
	return classNames({
		[button.displayType]: button.displayType,
		inverted,
		[button.size]: button.size,
		[button.variant]: button.variant,
		[`icon ${button.icon}`]: button.icon,
	});
}

const ButtonGuide = () => {
	return (
		<>
			{BUTTON_VARIANTS.map((item, key) => (
				<TokenGroup
					className={classNames({
						['bg-neutral-3 px-2']: item.inverted,
					})}
					group="buttons"
					key={key}
					title={item.categoryTitle}
				>
					{item.buttons.map((button) => (
						<TokenItem
							key={getLabel(button, item.inverted)}
							label={getLabel(button, item.inverted)}
							size="medium"
						>
							<ClayButton
								className={classNames({
									'btn-inverted': item.inverted,
									[`btn-${button.size}`]: button.size,
									[`btn-variant-${button.variant}`]: button.variant,
								})}
								displayType={button.displayType}
							>
								{button.icon === 'before' && (
									<span className="inline-item inline-item-before">
										<ClayIcon symbol="angle-left" />
									</span>
								)}
								Button
								{button.icon === 'after' && (
									<span className="inline-item inline-item-after">
										<ClayIcon symbol="angle-right" />
									</span>
								)}
							</ClayButton>
						</TokenItem>
					))}
				</TokenGroup>
			))}
		</>
	);
};

export default ButtonGuide;
