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

const BUTTONS = [
	'primary',
	'secondary',
	'outline-primary',
	'outline-secondary',
	'link',
];

const BUTTON_SIZES = [
	'btn btn-primary btn-sm',
	'btn btn-primary',
	'btn btn-primary btn-lg',
];

const BUTTON_SOLID = [
	'btn btn-solid btn-variant-primary',
	'btn btn-solid btn-variant-secondary',
	'btn btn-solid btn-variant-neutral',
];

const BUTTON_SOLID_INVERTED = [
	'btn btn-solid btn-inverted btn-variant-primary',
	'btn btn-solid btn-inverted btn-variant-secondary',
	'btn btn-solid btn-inverted btn-variant-neutral',
];

const BUTTON_GHOST = [
	'btn btn-ghost btn-variant-primary',
	'btn btn-ghost btn-variant-secondary',
	'btn btn-ghost btn-variant-neutral',
];

const BUTTON_GHOST_INVERTED = [
	'btn btn-ghost btn-inverted btn-variant-primary',
	'btn btn-ghost btn-inverted btn-variant-secondary',
	'btn btn-ghost btn-inverted btn-variant-neutral',
];

const BUTTON_BORDERLESS = [
	'btn btn-borderless btn-variant-primary',
	'btn btn-borderless btn-variant-secondary',
	'btn btn-borderless btn-variant-neutral',
];

const BUTTON_BORDERLESS_INVERTED = [
	'btn btn-borderless btn-inverted btn-variant-primary',
	'btn btn-borderless btn-inverted btn-variant-secondary',
	'btn btn-borderless btn-inverted btn-variant-neutral',
];

const BUTTON_ROUNDED = [
	'btn btn-rounded btn-variant-primary',
	'btn btn-rounded btn-variant-secondary',
	'btn btn-rounded btn-variant-neutral',
];

const BUTTON_ROUNDED_INVERTED = [
	'btn btn-rounded btn-inverted btn-variant-primary',
	'btn btn-rounded btn-inverted btn-variant-secondary',
	'btn btn-rounded btn-inverted btn-variant-neutral',
];

const ButtonGuide = () => {
	return (
		<>
			<TokenGroup group="buttons" title={Liferay.Language.get('buttons')}>
				{BUTTONS.map((item) => (
					<TokenItem key={item} label={`btn-${item}`} size="medium">
						<button className={'btn btn-' + item}>Button</button>
					</TokenItem>
				))}
			</TokenGroup>

			<TokenGroup
				group="buttons"
				title={Liferay.Language.get('button-sizes')}
			>
				{BUTTON_SIZES.map((item) => (
					<TokenItem key={item} label={item} size="medium">
						<button className={item}>Button</button>
					</TokenItem>
				))}
			</TokenGroup>

			<TokenGroup
				group="buttons"
				title={Liferay.Language.get('button-solid')}
			>
				{BUTTON_SOLID.map((item) => (
					<TokenItem key={item} label={item} size="medium">
						<button className={item}>Button</button>
					</TokenItem>
				))}
			</TokenGroup>

			<TokenGroup
				className="bg-neutral-7 px-2"
				group="buttons"
				title={Liferay.Language.get('button-solid-inverted')}
			>
				{BUTTON_SOLID_INVERTED.map((item) => (
					<TokenItem key={item} label={item} size="medium">
						<button className={item}>Button</button>
					</TokenItem>
				))}
			</TokenGroup>

			<TokenGroup
				group="buttons"
				title={Liferay.Language.get('button-ghost')}
			>
				{BUTTON_GHOST.map((item) => (
					<TokenItem key={item} label={item} size="medium">
						<button className={item}>Button</button>
					</TokenItem>
				))}
			</TokenGroup>

			<TokenGroup
				className="bg-neutral-7 px-2"
				group="buttons"
				title={Liferay.Language.get('button-ghost-inverted')}
			>
				{BUTTON_GHOST_INVERTED.map((item) => (
					<TokenItem key={item} label={item} size="medium">
						<button className={item}>Button</button>
					</TokenItem>
				))}
			</TokenGroup>

			<TokenGroup
				group="buttons"
				title={Liferay.Language.get('button-borderless')}
			>
				{BUTTON_BORDERLESS.map((item) => (
					<TokenItem key={item} label={item} size="medium">
						<button className={item}>Button</button>
					</TokenItem>
				))}
			</TokenGroup>

			<TokenGroup
				className="bg-neutral-7 px-2"
				group="buttons"
				title={Liferay.Language.get('button-borderless-inverted')}
			>
				{BUTTON_BORDERLESS_INVERTED.map((item) => (
					<TokenItem key={item} label={item} size="medium">
						<button className={item}>Button</button>
					</TokenItem>
				))}
			</TokenGroup>

			<TokenGroup
				group="buttons"
				title={Liferay.Language.get('button-rounded')}
			>
				{BUTTON_ROUNDED.map((item) => (
					<TokenItem key={item} label={item} size="medium">
						<button className={item}>Button</button>
					</TokenItem>
				))}
			</TokenGroup>

			<TokenGroup
				className="bg-neutral-7 px-2"
				group="buttons"
				title={Liferay.Language.get('button-rounded-inverted')}
			>
				{BUTTON_ROUNDED_INVERTED.map((item) => (
					<TokenItem key={item} label={item} size="medium">
						<button className={item}>Button</button>
					</TokenItem>
				))}
			</TokenGroup>
		</>
	);
};

export default ButtonGuide;
