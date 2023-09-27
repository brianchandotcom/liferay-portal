/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			{
				displayType: 'ghost',
				size: 'sm',
				style: 'primary',
			},
			{
				displayType: 'ghost',
				style: 'primary',
			},
			{
				displayType: 'ghost',
				size: 'lg',
				style: 'primary',
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
				monospaced: true,
				size: 'sm',
			},
			{
				displayType: 'primary',
				icon: 'before',
				monospaced: true,
			},
			{
				displayType: 'primary',
				icon: 'before',
				monospaced: true,
				size: 'lg',
			},
			{
				displayType: 'ghost',
				icon: 'before',
				monospaced: true,
				size: 'sm',
				style: 'primary',
			},
			{
				displayType: 'ghost',
				icon: 'before',
				monospaced: true,
				style: 'primary',
			},
			{
				displayType: 'ghost',
				icon: 'before',
				monospaced: true,
				size: 'lg',
				style: 'primary',
			},
		],
		categoryTitle: Liferay.Language.get('button-monospaced'),
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
				style: 'primary',
			},
			{
				displayType: 'solid',
				style: 'secondary',
			},
			{
				displayType: 'solid',
				style: 'neutral',
			},
		],
		categoryTitle: Liferay.Language.get('button-solid'),
		inverted: false,
	},
	{
		buttons: [
			{
				displayType: 'solid',
				style: 'primary',
			},
			{
				displayType: 'solid',
				style: 'secondary',
			},
			{
				displayType: 'solid',
				style: 'neutral',
			},
		],
		categoryTitle: Liferay.Language.get('button-solid-inverted'),
		inverted: true,
	},
	{
		buttons: [
			{
				displayType: 'ghost',
				style: 'primary',
			},
			{
				displayType: 'ghost',
				style: 'secondary',
			},
			{
				displayType: 'ghost',
				style: 'neutral',
			},
		],
		categoryTitle: Liferay.Language.get('button-ghost'),
		inverted: false,
	},
	{
		buttons: [
			{
				displayType: 'ghost',
				style: 'primary',
			},
			{
				displayType: 'ghost',
				style: 'secondary',
			},
			{
				displayType: 'ghost',
				style: 'neutral',
			},
		],
		categoryTitle: Liferay.Language.get('button-ghost-inverted'),
		inverted: true,
	},
	{
		buttons: [
			{
				displayType: 'borderless',
				style: 'primary',
			},
			{
				displayType: 'borderless',
				style: 'secondary',
			},
			{
				displayType: 'borderless',
				style: 'neutral',
			},
		],
		categoryTitle: Liferay.Language.get('button-borderless'),
		inverted: false,
	},
	{
		buttons: [
			{
				displayType: 'borderless',
				style: 'primary',
			},
			{
				displayType: 'borderless',
				style: 'secondary',
			},
			{
				displayType: 'borderless',
				style: 'neutral',
			},
		],
		categoryTitle: Liferay.Language.get('button-borderless-inverted'),
		inverted: true,
	},
	{
		buttons: [
			{
				displayType: 'rounded',
				style: 'primary',
			},
			{
				displayType: 'rounded',
				style: 'secondary',
			},
			{
				displayType: 'rounded',
				style: 'neutral',
			},
		],
		categoryTitle: Liferay.Language.get('button-rounded'),
		inverted: false,
	},
	{
		buttons: [
			{
				displayType: 'rounded',
				style: 'primary',
			},
			{
				displayType: 'rounded',
				style: 'secondary',
			},
			{
				displayType: 'rounded',
				style: 'neutral',
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
		[button.monospaced]: button.monospaced,
		[button.style]: button.style,
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
									[`btn-style-${button.style}`]: button.style,
								})}
								displayType={button.displayType}
								monospaced={button.monospaced}
							>
								{button.icon === 'before' && (
									<span
										className={classNames('inline-item', {
											['inline-item-before']: !button.monospaced,
										})}
									>
										<ClayIcon symbol="angle-left" />
									</span>
								)}

								{!button.monospaced && 'Button'}

								{button.icon === 'after' && (
									<span
										className={classNames('inline-item', {
											['inline-item-after']: !button.monospaced,
										})}
									>
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
