/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {useReindexActions} from '../../../components/settings/indexes-page/hooks/useReindexActions.es';
import Portal from '../portal/Portal.es';

const HeaderReindexStatus = ({container}) => {
	const {reindexStatuses} = useReindexActions();

	return (
		<>
			{!!reindexStatuses.length && (
				<Portal
					className="control-menu-nav-item"
					container={container}
					elementId="headerReindexStatus"
					position="after"
				>
					<span
						aria-hidden="true"
						className="c-m-0 c-pr-2 loading-animation loading-animation-sm"
						data-tooltip-align="bottom"
						title={Liferay.Language.get(
							'the-workflow-metrics-data-is-currently-reindexing'
						)}
					></span>
				</Portal>
			)}
		</>
	);
};

export default HeaderReindexStatus;
