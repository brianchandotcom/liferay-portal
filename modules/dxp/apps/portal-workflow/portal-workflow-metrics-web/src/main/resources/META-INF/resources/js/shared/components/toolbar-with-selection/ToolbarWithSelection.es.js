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
import {ManagementToolbar} from 'frontend-js-components-web';
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
		<ManagementToolbar.Container
			active={active}
			className="mb-0 show-quick-actions-on-hover"
		>
			<ManagementToolbar.ItemList expand>
				<ManagementToolbar.Item className="ml-2">
					<ClayCheckbox
						checked={checked}
						indeterminate={indeterminate}
						onChange={handleCheck}
					/>
				</ManagementToolbar.Item>

				{active && (
					<>
						<ManagementToolbar.Item>
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
						</ManagementToolbar.Item>

						<ManagementToolbar.Item>
							<ClayButton
								className="font-weight-bold nav-link"
								displayType="unstyled"
								onClick={handleClear}
								small
							>
								{Liferay.Language.get('clear')}
							</ClayButton>
						</ManagementToolbar.Item>

						{!selectAll && checked && (
							<ManagementToolbar.Item>
								<ClayButton
									className="font-weight-bold nav-link"
									displayType="unstyled"
									onClick={handleSelectAll}
									small
								>
									{Liferay.Language.get('select-all')}
								</ClayButton>
							</ManagementToolbar.Item>
						)}
					</>
				)}

				{children}
			</ManagementToolbar.ItemList>
		</ManagementToolbar.Container>
	);
};

export default ToolbarWithSelection;
