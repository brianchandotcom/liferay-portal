/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
