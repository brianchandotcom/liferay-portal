/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import classNames from 'classnames';
import {useEffect} from 'react';
import i18n from '../../../../common/I18n';
import useIntersectionObserver from '../../../../common/hooks/useIntersectionObserver';
import {Liferay} from '../../../../common/services/liferay';
import routerPath from '../../../../common/utils/routerPath';
import ProjectCard from './components/ProjectCard';

const RenderResults = ({compressed, koroneikiAccounts, loading}) => {
	const pageRoutes = routerPath();

	if (!koroneikiAccounts) {
		return (
			<p className="mx-auto">
				{i18n.translate('sorry-there-are-no-results-found')}
			</p>
		);
	}

	if (koroneikiAccounts.totalCount) {
		return (
			<>
				{koroneikiAccounts?.items.map((koroneikiAccount, index) => (
					<ProjectCard
						compressed={compressed}
						key={`${koroneikiAccount.accountKey}-${index}`}
						onClick={() =>
							Liferay.Util.navigate(
								pageRoutes.project(koroneikiAccount.accountKey)
							)
						}
						{...koroneikiAccount}
					/>
				))}

				{loading && (
					<div className="mx-auto">
						<ClayLoadingIndicator small />
					</div>
				)}
			</>
		);
	}

	return (
		<p className="mx-auto">
			{i18n.translate('no-projects-match-these-criteria')}
		</p>
	);
};

const ProjectList = ({
	compressed,
	fetching,
	koroneikiAccounts,
	loading,
	maxCardsLoading = 4,
	onIntersect,
}) => {
	const [ref, isIntersecting] = useIntersectionObserver();

	const isLastPage = koroneikiAccounts?.page === koroneikiAccounts?.lastPage;
	const allowFetching = !isLastPage && !fetching;

	useEffect(() => {
		if (isIntersecting && allowFetching) {
			onIntersect(koroneikiAccounts?.page);
		}
	}, [isIntersecting, koroneikiAccounts?.page, onIntersect, allowFetching]);

	return (
		<div
			className={classNames('d-flex', {
				'flex-column': compressed,
				'flex-wrap pl-3': !compressed,
			})}
		>
			{loading ? (
				<>
					{[...new Array(maxCardsLoading)].map((_, index) => (
						<ProjectCard
							compressed={compressed}
							key={index}
							loading
						/>
					))}
				</>
			) : (
				<RenderResults
					compressed={compressed}
					koroneikiAccounts={koroneikiAccounts}
					loading={loading}
				/>
			)}

			<div ref={ref}></div>
		</div>
	);
};

export default ProjectList;
