/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import ClayNavigationBar from '@clayui/navigation-bar';
import React, {useContext} from 'react';
import {matchPath, withRouter} from 'react-router-dom';

import {AppContext} from '../AppContext.es';
import useQueryParams from '../hooks/useQueryParams.es';
import {historyPushWithSlug} from '../utils/utils.es';

export default withRouter(({history, location}) => {
	const context = useContext(AppContext);

	const queryParams = useQueryParams(location);

	const match = matchPath(location.pathname, {
		path: '/questions/:sectionTitle/',
		strict: false,
	});

	const sectionTitle =
		(match &&
			match.params &&
			match.params.sectionTitle !== 'activity' &&
			match.params.sectionTitle !== 'subscriptions' &&
			match.params.sectionTitle !== 'tag' &&
			match.params.sectionTitle) ||
		queryParams.get('sectiontitle');

	const isActive = (value) => location.pathname === value;

	const getLabel = () => {
		if (location.pathname.includes('tags')) {
			return Liferay.Language.get('tags');
		}
		else if (location.pathname.includes('activity')) {
			return Liferay.Language.get('activity');
		}
		else if (location.pathname.includes('subscriptions')) {
			return Liferay.Language.get('subscriptions');
		}

		return Liferay.Language.get('questions');
	};

	const historyPushParser = historyPushWithSlug(history.push);

	return (
		<section className="border-bottom pb-0 questions-section questions-section-nav">
			<div className="questions-container row">
				<div className="align-items-center col d-flex justify-content-between">
					<ClayNavigationBar
						className="border-0 navigation-bar"
						triggerLabel={getLabel()}
					>
						<ClayNavigationBar.Item
							active={
								isActive(`/questions`) ||
								isActive(`/questions/${sectionTitle}`) ||
								isActive('/')
							}
							onClick={() =>
								historyPushParser(
									sectionTitle
										? `/questions/${sectionTitle}`
										: '/'
								)
							}
						>
							<ClayLink>
								{Liferay.Language.get('questions')}
							</ClayLink>
						</ClayNavigationBar.Item>

						<ClayNavigationBar.Item
							active={isActive(`/tags`)}
							onClick={() => historyPushParser('/tags')}
						>
							<ClayLink>{Liferay.Language.get('tags')}</ClayLink>
						</ClayNavigationBar.Item>

						<ClayNavigationBar.Item
							active={isActive(
								`/questions/subscriptions/${context.userId}`
							)}
							className={
								Liferay.ThemeDisplay.isSignedIn()
									? 'ml-md-auto'
									: 'd-none'
							}
							onClick={() =>
								historyPushParser(
									`/questions/subscriptions/${
										context.userId
									}${
										sectionTitle
											? '?sectionTitle=' + sectionTitle
											: ''
									}`
								)
							}
						>
							<ClayLink>
								{Liferay.Language.get('subscriptions')}
							</ClayLink>
						</ClayNavigationBar.Item>

						<ClayNavigationBar.Item
							active={isActive(
								`/questions/activity/${context.userId}`
							)}
							className={
								Liferay.ThemeDisplay.isSignedIn()
									? ''
									: 'd-none'
							}
							onClick={() =>
								historyPushParser(
									`/questions/activity/${context.userId}${
										sectionTitle
											? '?sectionTitle=' + sectionTitle
											: ''
									}`
								)
							}
						>
							<ClayLink>
								{Liferay.Language.get('activity')}
							</ClayLink>
						</ClayNavigationBar.Item>
					</ClayNavigationBar>
				</div>
			</div>
		</section>
	);
});
