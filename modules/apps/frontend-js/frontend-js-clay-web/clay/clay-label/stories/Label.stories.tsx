/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';

import ClayLabel from '../src';

export default {
	argTypes: {
		displayType: {
			control: {type: 'select'},
			options: [
				'danger',
				'info',
				'secondary',
				'success',
				'warning',
			] as const,
		},
		inverse: {
			control: {type: 'boolean'},
		},
		large: {
			control: {type: 'boolean'},
		},
	},
	component: ClayLabel,
	title: 'Design System/Components/Label',
};

export function Default(args: typeof Default.args) {
	const [visible, setVisible] = useState<boolean>(true);

	return visible ? (
		<ClayLabel
			closeButtonProps={
				args.closeable
					? {
							onClick: () => setVisible((val) => !val),
						}
					: undefined
			}
			displayType={args.displayType as 'secondary'}
			href={args.href}
			inverse={args.inverse}
			large={args.large}
		>
			{args.label}
		</ClayLabel>
	) : null;
}

Default.args = {
	closeable: false,
	displayType: 'secondary',
	href: '',
	inverse: false,
	label: 'Label',

	large: false,
};

export function Truncate(args: typeof Default.args) {
	return (
		<div style={{width: 150}}>
			<ClayLabel
				displayType={args.displayType as 'secondary'}
				inverse={args.inverse}
				large={args.large}
			>
				<span className="text-truncate">
					this is a very long bit of text, can you see the end of it?
				</span>
			</ClayLabel>
		</div>
	);
}

Truncate.args = {
	large: false,
};

export function ContentBefore(args: typeof Default.args) {
	return (
		<ClayLabel
			displayType={args.displayType as 'secondary'}
			inverse={args.inverse}
			large={args.large}
			withClose={false}
		>
			<ClayLabel.ItemBefore>
				<ClayIcon symbol="check" />
			</ClayLabel.ItemBefore>

			<ClayLabel.ItemExpand>Label</ClayLabel.ItemExpand>
		</ClayLabel>
	);
}

ContentBefore.args = {
	large: false,
};
