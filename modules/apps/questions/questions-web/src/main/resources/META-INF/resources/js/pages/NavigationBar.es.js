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

import ClayLink from '@clayui/link';
import ClayNavigationBar from '@clayui/navigation-bar';
import React, {useContext} from 'react';
import {Link, withRouter} from 'react-router-dom';

import {AppContext} from '../AppContext.es';

export default withRouter(
	({
		history,
		location,
		match: {
			params: {sectionTitle},
		},
	}) => {
		const isActive = value => location.pathname.includes(value);

		const context = useContext(AppContext);

		return (
			<>
				{location.pathname !== '/' && (
					<div className="align-items-center d-flex justify-content-between questions-navigation">
						<ClayNavigationBar triggerLabel="Questions">
							<ClayNavigationBar.Item
								active={!isActive('tags')}
								onClick={() => {
									if (!sectionTitle) {
										return history.push('/questions');
									}

									return history.push(
										`/questions/${sectionTitle}`
									);
								}}
							>
								<ClayLink
									className="nav-link"
									displayType="unstyled"
								>
									{Liferay.Language.get('questions')}
								</ClayLink>
							</ClayNavigationBar.Item>

							<ClayNavigationBar.Item
								active={isActive('tags')}
								onClick={() => {
									if (!sectionTitle) {
										return history.push('/questions');
									}

									return history.push(
										`/questions/${sectionTitle}/tags`
									);
								}}
							>
								<ClayLink
									className="nav-link"
									displayType="unstyled"
								>
									{Liferay.Language.get('tags')}
								</ClayLink>
							</ClayNavigationBar.Item>
						</ClayNavigationBar>

						<div className="autofit-col">
							<Link
								className="nav-link"
								displayType="unstyled"
								to={`/activity/${context.userId}`}
							>
								{Liferay.Language.get('my-activity')}
							</Link>
						</div>
					</div>
				)}
			</>
		);
	}
);
