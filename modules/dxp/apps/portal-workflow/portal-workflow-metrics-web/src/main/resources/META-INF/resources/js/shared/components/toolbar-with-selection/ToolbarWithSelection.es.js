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

import ClayButton from '@clayui/button';
import {ClayCheckbox} from '@clayui/form';
import ClayManagementToolbar from '@clayui/management-toolbar';
import React from 'react';

import {sub} from '../../util/lang.es';

const ToolbarWithSelection = ({
	active,
	checked,
	children,
	handleCheck,
	handleClear,
	handleSelectAll,
	indeterminate,
	selectAll,
	selectedCount,
	totalCount,
}) => {
	return (
		<ClayManagementToolbar
			active={active}
			className="mb-0 show-quick-actions-on-hover"
		>
			<ClayManagementToolbar.ItemList expand>
				<ClayManagementToolbar.Item className="ml-2">
					<ClayCheckbox
						checked={checked}
						indeterminate={indeterminate}
						onChange={handleCheck}
					/>
				</ClayManagementToolbar.Item>

				{active && (
					<>
						<ClayManagementToolbar.Item>
							<span className="ml-0 mr-0 navbar-text">
								{selectAll
									? Liferay.Language.get('all-selected')
									: sub(
											Liferay.Language.get(
												'x-of-x-selected'
											),
											[selectedCount, totalCount]
									  )}
							</span>
						</ClayManagementToolbar.Item>

						<ClayManagementToolbar.Item>
							<ClayButton
								className="font-weight-bold nav-link"
								displayType="unstyled"
								onClick={handleClear}
								small
							>
								{Liferay.Language.get('clear')}
							</ClayButton>
						</ClayManagementToolbar.Item>

						{!selectAll && checked && (
							<ClayManagementToolbar.Item>
								<ClayButton
									className="font-weight-bold nav-link"
									displayType="unstyled"
									onClick={handleSelectAll}
									small
								>
									{Liferay.Language.get('select-all')}
								</ClayButton>
							</ClayManagementToolbar.Item>
						)}
					</>
				)}

				{children}
			</ClayManagementToolbar.ItemList>
		</ClayManagementToolbar>
	);
};

export default ToolbarWithSelection;
