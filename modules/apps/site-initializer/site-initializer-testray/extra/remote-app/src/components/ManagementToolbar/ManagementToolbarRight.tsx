/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayManagementToolbar from '@clayui/management-toolbar';
import {Dispatch} from 'react';

type ManagementToolbarRightProps = {
	addButton?: () => void;
	setShowMobile: Dispatch<boolean>;
	viewTypes: any[];
};

const ManagementToolbarRight: React.FC<ManagementToolbarRightProps> = ({
	addButton,
	setShowMobile,
	viewTypes,
}) => {
	const viewTypeActive = viewTypes.find((type) => type.active);

	return (
		<ClayManagementToolbar.ItemList>
			<ClayManagementToolbar.Item className="navbar-breakpoint-d-none">
				<ClayButton
					className="nav-link nav-link-monospaced"
					displayType="unstyled"
					onClick={() => setShowMobile(true)}
				>
					<ClayIcon symbol="search" />
				</ClayButton>
			</ClayManagementToolbar.Item>

			<ClayManagementToolbar.Item>
				<ClayButton
					className="nav-link nav-link-monospaced"
					displayType="unstyled"
					onClick={() => {}}
				>
					<ClayIcon symbol="info-circle-open" />
				</ClayButton>
			</ClayManagementToolbar.Item>

			<ClayManagementToolbar.Item>
				<ClayDropDownWithItems
					items={viewTypes}
					trigger={
						<ClayButton
							className="nav-link nav-link-monospaced"
							displayType="unstyled"
						>
							<ClayIcon
								symbol={
									viewTypeActive
										? viewTypeActive.symbolLeft
										: ''
								}
							/>
						</ClayButton>
					}
				/>
			</ClayManagementToolbar.Item>

			{addButton && (
				<ClayManagementToolbar.Item onClick={() => addButton()}>
					<ClayButtonWithIcon
						className="nav-btn nav-btn-monospaced"
						symbol="plus"
					/>
				</ClayManagementToolbar.Item>
			)}
		</ClayManagementToolbar.ItemList>
	);
};

export default ManagementToolbarRight;
