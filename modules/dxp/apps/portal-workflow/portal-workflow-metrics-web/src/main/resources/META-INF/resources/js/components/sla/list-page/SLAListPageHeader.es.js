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

import ClayIcon from '@clayui/icon';
import ClayManagementToolbar from '@clayui/management-toolbar';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React from 'react';

import ChildLink from '../../../shared/components/router/ChildLink.es';

const Header = ({processId}) => {
	return (
		<ClayManagementToolbar>
			<ClayManagementToolbar.ItemList expand>
				<ClayManagementToolbar.Item className="autofit-col-expand autofit-float-end">
					<ClayTooltipProvider>
						<span>
							<span
								className="workflow-tooltip"
								data-tooltip-align={'bottom'}
								title={Liferay.Language.get('new-sla')}
							>
								<ChildLink
									className="btn btn-primary nav-btn nav-btn-monospaced"
									to={`/sla/${processId}/new`}
								>
									<ClayIcon symbol="plus" />
								</ChildLink>
							</span>
						</span>
					</ClayTooltipProvider>
				</ClayManagementToolbar.Item>
			</ClayManagementToolbar.ItemList>
		</ClayManagementToolbar>
	);
};

export {Header};
