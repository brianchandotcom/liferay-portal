/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import {ManagementToolbar} from 'frontend-js-components-web';
import React from 'react';

type Props = {
	backURL?: string;
	children: React.ReactNode;
	title: string;
};

export default function ManagementBar({backURL, children, title}: Props) {
	return (
		<ManagementToolbar.Container className="border">
			<ManagementToolbar.ItemList className="c-gap-3" expand>
				{backURL ? (
					<ManagementToolbar.Item>
						<ClayLink
							aria-label={Liferay.Language.get('back')}
							className="btn btn-monospaced btn-outline-borderless btn-outline-secondary btn-sm"
							href={backURL}
						>
							<ClayIcon symbol="angle-left" />
						</ClayLink>
					</ManagementToolbar.Item>
				) : null}

				<ManagementToolbar.Item className="nav-item-expand">
					<h2 className="font-weight-semi-bold m-0 text-5">
						{title}
					</h2>
				</ManagementToolbar.Item>

				<ManagementToolbar.Item>
					<ClayLink
						className="btn btn-outline-borderless btn-outline-secondary btn-sm"
						href="structures"
					>
						{Liferay.Language.get('cancel')}
					</ClayLink>
				</ManagementToolbar.Item>

				{children}
			</ManagementToolbar.ItemList>
		</ManagementToolbar.Container>
	);
}
