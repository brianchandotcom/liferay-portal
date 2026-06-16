/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import React, {useEffect, useState} from 'react';

function SideNavigationColorSchemeButton({
	colorScheme: initialColorScheme,
	colorSchemeSessionKey,
}: {
	colorScheme: 'dark' | 'light';
	colorSchemeSessionKey: string;
}) {
	const [colorScheme, setColorScheme] = useState(initialColorScheme);

	useEffect(() => {
		document.documentElement.dataset.colorScheme = colorScheme;
	}, [colorScheme]);

	const toggle = async () => {
		const nextColorScheme = colorScheme === 'dark' ? 'light' : 'dark';

		setColorScheme(nextColorScheme);

		await Liferay.Util.Session.set(colorSchemeSessionKey, nextColorScheme);
	};

	const label =
		colorScheme === 'dark'
			? Liferay.Language.get('switch-to-light-mode')
			: Liferay.Language.get('switch-to-dark-mode');

	return (
		<ClayButtonWithIcon
			aria-label={label}
			aria-pressed={colorScheme === 'dark'}
			borderless
			className="c-ml-2"
			displayType="secondary"
			monospaced
			onClick={toggle}
			size="sm"
			symbol={colorScheme === 'dark' ? 'sun' : 'moon'}
			title={label}
		/>
	);
}

export default SideNavigationColorSchemeButton;
