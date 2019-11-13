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

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayManagementToolbar from '@clayui/management-toolbar';
import React from 'react';

const spritemap = `${Liferay.ThemeDisplay.getPathThemeImages()}/lexicon/icons.svg`;

const Header = () => {
	const showMobile = true;

	return (
		<ClayManagementToolbar>
			<ClayManagementToolbar.Item>
				<strong className="ml-0 mr-0 navbar-text">
					{Liferay.Language.get('filter-by')}
				</strong>
			</ClayManagementToolbar.Item>

			<ClayManagementToolbar.Search showMobile={showMobile}>
				<ClayInput.Group>
					<ClayInput.GroupItem>
						<ClayInput
							aria-label="Search"
							className="form-control input-group-inset input-group-inset-after"
							placeholder={Liferay.Language.get(
								'search-for-assignee-name'
							)}
							type="text"
						/>

						<ClayInput.GroupInsetItem after tag="span">
							<ClayButtonWithIcon
								className="navbar-breakpoint-d-none"
								displayType="unstyled"
								spritemap={spritemap}
								symbol="times"
							/>

							<ClayButtonWithIcon
								displayType="unstyled"
								spritemap={spritemap}
								symbol="search"
								type="submit"
							/>
						</ClayInput.GroupInsetItem>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayManagementToolbar.Search>
		</ClayManagementToolbar>
	);
};

export {Header};
