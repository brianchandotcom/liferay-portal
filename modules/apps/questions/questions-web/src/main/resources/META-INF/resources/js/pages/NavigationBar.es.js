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
import React, {useContext, useEffect} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../AppContext.es';
import {getSections} from '../utils/client.es';
import {historyPushWithSlug} from '../utils/utils.es';

export default withRouter(
	({
		history,
		location,
		match: {
			params: {sectionTitle},
		},
	}) => {
		const isActive = (value) => location.pathname.includes(value);

		const context = useContext(AppContext);

		useEffect(() => {
			if (sectionTitle) {
				context.setSection(sectionTitle);
			}
			else if (Object.keys(context.section).length === 0) {
				getSections(context.siteKey).then((sections) =>
					context.setSection(sections.items[0].title)
				);
			}
		}, [context, sectionTitle]);

		const historyPushParser = historyPushWithSlug(history.push);

		return (
			<section className="border-bottom questions-section questions-section-nav">
				<div className="questions-container">
					<div className="row">
						{location.pathname !== '/' && (
							<div className="align-items-center col d-flex justify-content-between">
								<ClayNavigationBar
									className="navigation-bar"
									triggerLabel="Questions"
								>
									<ClayNavigationBar.Item
										active={
											!isActive('activity') &&
											!isActive('tags')
										}
										onClick={() =>
											historyPushParser(
												`/questions/${context.section}`
											)
										}
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
										onClick={() =>
											historyPushParser(
												`/questions/${context.section}/tags`
											)
										}
									>
										<ClayLink
											className="nav-link"
											displayType="unstyled"
										>
											{Liferay.Language.get('tags')}
										</ClayLink>
									</ClayNavigationBar.Item>

									<ClayNavigationBar.Item
										active={isActive('activity')}
										className={
											Liferay.ThemeDisplay.isSignedIn()
												? 'ml-md-auto'
												: 'd-none'
										}
										onClick={() =>
											historyPushParser(
												`/activity/${context.userId}`
											)
										}
									>
										<ClayLink
											className="nav-link"
											displayType="unstyled"
										>
											{Liferay.Language.get(
												'my-activity'
											)}
										</ClayLink>
									</ClayNavigationBar.Item>
								</ClayNavigationBar>
							</div>
						)}
					</div>
				</div>
			</section>
		);
	}
);
