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

import classNames from 'classnames';

import {useEffect, useState} from 'react';
import ProjectList from './components/ProjectsList';
import SearchHeader from './components/SearchHeader';
import useKoroneikiAccounts from './hooks/useKoroneikiAccounts';

const THRESHOLD_COUNT = 4;

const Home = () => {
	const {data, fetchMore, fetching, loading, search} = useKoroneikiAccounts();
	const [maxTotalCount, setMaxTotalCount] = useState(0);

	const koroneikiAccounts = data?.c?.koroneikiAccounts;
	const hasManyProjects = maxTotalCount > THRESHOLD_COUNT;

	useEffect(() => {
		const totalCount = koroneikiAccounts?.totalCount;

		if (totalCount > maxTotalCount) {
			setMaxTotalCount(totalCount);
		}
	}, [maxTotalCount, koroneikiAccounts?.totalCount]);

	return (
		<div
			className={classNames({
				'cp-project-cards-container': !hasManyProjects,
				'mx-auto cp-project-cards-container-sm': hasManyProjects,
			})}
		>
			<div
				className={classNames({
					'd-flex flex-column w-100': hasManyProjects,
					'ml-3': !hasManyProjects,
				})}
			>
				{hasManyProjects && (
					<SearchHeader
						count={koroneikiAccounts?.items.length}
						loading={loading}
						onSearchSubmit={(term) => search(term)}
					/>
				)}

				<ProjectList
					fetching={fetching}
					hasManyProjects={hasManyProjects}
					koroneikiAccounts={koroneikiAccounts}
					loading={loading}
					onIntersect={(currentPage) =>
						fetchMore({
							variables: {
								page: currentPage + 1,
							},
						})
					}
				/>
			</div>
		</div>
	);
};

export default Home;
