/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBreadcrumb from '@clayui/breadcrumb';
import {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import Nav from '@clayui/nav';
import React from 'react';

import SpaceSticker from './SpaceSticker';

interface Props {
	breadcrumbItems: BreadcrumbItem[];
}

export interface BreadcrumbItem {
	active?: boolean;
	href?: string;
	label: string;
	onClick?: () => void;
}

export default function Breadcrumb({breadcrumbItems}: Props) {
	return (
		<Nav
			aria-label="Breadcrumb"
			className="autofit-row autofit-row-center ml-3 mt-3"
		>
			<div className="autofit-col cms-sticker-dropdown mr-1">
				<SpaceSticker
					hideName
					name={breadcrumbItems[0]?.label}
					size="sm"
				/>
			</div>

			<div className="autofit-col cms-breadcrumb">
				<ClayBreadcrumb items={breadcrumbItems} />
			</div>

			<div className="autofit-col">
				<ClayDropDownWithItems
					items={[]}
					trigger={
						<ClayButtonWithIcon
							aria-label={Liferay.Language.get('more-actions')}
							displayType="unstyled"
							size="xs"
							symbol="ellipsis-v"
						/>
					}
				/>
			</div>
		</Nav>
	);
}
