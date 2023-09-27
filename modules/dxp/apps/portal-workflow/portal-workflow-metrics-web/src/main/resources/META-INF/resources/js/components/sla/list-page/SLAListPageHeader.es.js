/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {ManagementToolbar} from 'frontend-js-components-web';
import React from 'react';

import ChildLink from '../../../shared/components/router/ChildLink.es';

export default function Header({processId}) {
	return (
		<ManagementToolbar.Container>
			<ManagementToolbar.ItemList expand>
				<ManagementToolbar.Item className="autofit-col-expand autofit-float-end">
					<span
						data-tooltip-align="bottom"
						title={Liferay.Language.get('new-sla')}
					>
						<ChildLink
							className="btn btn-primary nav-btn nav-btn-monospaced"
							to={`/sla/${processId}/new`}
						>
							<ClayIcon symbol="plus" />
						</ChildLink>
					</span>
				</ManagementToolbar.Item>
			</ManagementToolbar.ItemList>
		</ManagementToolbar.Container>
	);
}
