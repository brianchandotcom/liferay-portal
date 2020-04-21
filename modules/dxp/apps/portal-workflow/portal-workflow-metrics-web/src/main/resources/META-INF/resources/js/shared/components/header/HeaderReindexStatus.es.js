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

import {ClayTooltipProvider} from '@clayui/tooltip';
import React from 'react';

import {useReindexActions} from '../../../components/settings/indexes-page/hooks/useReindexActions.es';
import Portal from '../portal/Portal.es';

const HeaderReindexStatus = ({container}) => {
	const {reindexStatuses} = useReindexActions();

	return (
		<>
			{reindexStatuses.length > 0 && (
				<Portal
					className="control-menu-nav-item"
					container={container}
					elementId="headerReindexStatus"
					position="after"
				>
					<div className="control-menu-icon px-2">
						<ClayTooltipProvider>
							<span
								aria-hidden="true"
								className="loading-animation loading-animation-light loading-animation-sm m-0"
								data-testid="statusLoading"
								data-tooltip-align="bottom"
								title={Liferay.Language.get(
									'the-workflow-metrics-data-is-currently-reindexing'
								)}
							></span>
						</ClayTooltipProvider>
					</div>
				</Portal>
			)}
		</>
	);
};

export default HeaderReindexStatus;
