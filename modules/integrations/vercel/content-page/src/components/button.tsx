/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Link from 'next/link';
import {ComponentProps} from 'react';

export function Button(
	props: ComponentProps<typeof Link> & {
		active?: boolean;
		external?: boolean;
		onClick?: () => void;
	}
) {
	const {
		active,
		className = `button ${active ? 'button--active' : ''}`,
		children,
		onClick,
	} = props;

	if ('href' in props) {
		return (
			<Link
				className={className}
				href={props.href}
				rel={props.external ? 'noopener noreferrer' : undefined}
				target={props.external ? '_blank' : undefined}
			>
				{children}
			</Link>
		);
	}

	return (
		<button className={className} onClick={onClick}>
			{children}
		</button>
	);
}
