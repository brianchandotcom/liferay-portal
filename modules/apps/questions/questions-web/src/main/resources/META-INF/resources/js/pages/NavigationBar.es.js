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
import {ClayInput} from '@clayui/form';
import ClayLink from '@clayui/link';
import ClayNavigationBar from '@clayui/navigation-bar';
import React, {useContext} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../AppContext.es';
import {useDebounceCallback} from '../utils/utils.es';

export default withRouter(({history, location, searchChange}) => {
	const context = useContext(AppContext);

	const [debounceCallback] = useDebounceCallback(value => {
		searchChange(value);
	}, 500);

	const navigate = href => {
		history.push(href);
	};

	const isActive = value => location.pathname.includes('/' + value);

	return (
		<div className="autofit-padded-no-gutters autofit-row autofit-row-center">
			<div className="autofit-col autofit-col-expand">
				<ClayNavigationBar triggerLabel="Questions">
					<ClayNavigationBar.Item
						active={isActive('questions')}
						onClick={() => navigate('/questions')}
					>
						<ClayLink className="nav-link" displayType="unstyled">
							{Liferay.Language.get('questions')}
						</ClayLink>
					</ClayNavigationBar.Item>

					<ClayNavigationBar.Item
						active={isActive('tags')}
						onClick={() => navigate('/tags')}
					>
						<ClayLink className="nav-link" displayType="unstyled">
							{Liferay.Language.get('tags')}
						</ClayLink>
					</ClayNavigationBar.Item>
				</ClayNavigationBar>
			</div>

			<div className="autofit-col">
				<ClayInput.Group>
					<ClayInput.GroupItem>
						<ClayInput
							className="form-control input-group-inset input-group-inset-after"
							onChange={event =>
								debounceCallback(event.target.value)
							}
							placeholder={Liferay.Language.get('search')}
							type="text"
						/>
						<ClayInput.GroupInsetItem after tag="span">
							<ClayButtonWithIcon
								displayType="unstyled"
								symbol="search"
								type="submit"
							/>
						</ClayInput.GroupInsetItem>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</div>
			<div className="autofit-col">
				{context.canCreateThread && (
					<ClayButton
						displayType="primary"
						onClick={() => navigate('/questions/new')}
					>
						{Liferay.Language.get('ask-question')}
					</ClayButton>
				)}
			</div>
		</div>
	);
});
