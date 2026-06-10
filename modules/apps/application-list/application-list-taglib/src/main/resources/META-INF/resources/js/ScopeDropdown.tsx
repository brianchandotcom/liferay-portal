/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBadge from '@clayui/badge';
import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import React from 'react';

interface ScopeDropdownItem {
	deprecated?: boolean;
	href?: string;
	label: string;
}

interface ScopeDropdownProps {
	items: ScopeDropdownItem[];
}

export default function ScopeDropdown({
	items,
}: ScopeDropdownProps): JSX.Element {
	return (
		<ClayDropDown
			closeOnClick
			items={items}
			trigger={
				<ClayButtonWithIcon
					aria-label={Liferay.Util.sub(
						Liferay.Language.get('choose-x'),
						Liferay.Language.get('scope')
					)}
					borderless={true}
					className="text-light"
					displayType="secondary"
					monospaced={true}
					symbol="cog"
					title={Liferay.Util.sub(
						Liferay.Language.get('choose-x'),
						Liferay.Language.get('scope')
					)}
				/>
			}
		>
			{(item: ScopeDropdownItem) => (
				<ClayDropDown.Item href={item.href}>
					{item.label}

					{item.deprecated && (
						<ClayBadge
							className="c-ml-2 text-uppercase"
							displayType="warning"
							label={Liferay.Language.get('deprecated')}
							translucent
						/>
					)}
				</ClayDropDown.Item>
			)}
		</ClayDropDown>
	);
}
