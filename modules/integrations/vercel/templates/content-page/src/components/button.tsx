/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Link from 'next/link';
import {PropsWithChildren} from 'react';

export const Button = (
	props: PropsWithChildren<
		{
			active?: boolean;
		} & ({onClick: () => void} | {href: string; external?: boolean})
	>
) => {
	const {active, children} = props;
	const className = `button ${active ? 'button--active' : ''}`;

	if ('href' in props) {
		return (
			<Link
				className={className}
				href={props.href}
				target={props.external ? '_blank' : undefined}
			>
				{children}
			</Link>
		);
	}

	return (
		<button className={className} onClick={props.onClick}>
			{children}
		</button>
	);
};
