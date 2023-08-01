/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import {useAppPropertiesContext} from '~/common/contexts/AppPropertiesContext';
import ProjectList from './components/ProjectsList';
import SearchHeader from './components/SearchHeader';

import './app.scss';

import {useState} from 'react';
import useKoroneikiAccounts from '~/common/hooks/useKoroneikiAccounts';

import ProjectsNavbar from './components/ProjectsNavbar/ProjectsNavbar';
import useProjectCategoryItems from './hooks/useProjectCategoryItems';

const PROJECT_MIN_THRESHOLD_COUNT = 5;
const THRESHOLD_COUNT = 4;

const Home = () => {
	const [
		selectedProjectCategoryIndex,
		setSelectedProjectCategoryIndex,
	] = useState(3);

	const projectCategoryItems = useProjectCategoryItems();

	const {
		data,
		fetchMore,
		fetching,
		firstKoroneikiAccountsTotal,
		loading,
		onSearch,
		search,
		searching,
	} = useKoroneikiAccounts({
		selectedFilterCategory:
			projectCategoryItems[selectedProjectCategoryIndex],
	});

	const handleOnSelect = (currentIndex) => {
		setSelectedProjectCategoryIndex(currentIndex);
		onSearch('');
	};

	const {featureFlags} = useAppPropertiesContext();

	const koroneikiAccounts = data?.c?.koroneikiAccounts;
	const koroneikiAccountTotal = koroneikiAccounts?.totalCount;

	const hasManyProjects = koroneikiAccountTotal > THRESHOLD_COUNT;

	return (
		<>
			{firstKoroneikiAccountsTotal >= PROJECT_MIN_THRESHOLD_COUNT &&
				featureFlags.includes('LPS-191380') && (
					<ProjectsNavbar
						loading={loading}
						onSelect={handleOnSelect}
						projectCategory={projectCategoryItems}
						selectedProjectCategory={selectedProjectCategoryIndex}
					/>
				)}

			<ClayLayout.ContainerFluid
				className="cp-home-wrapper"
				size={hasManyProjects && !loading ? 'md' : 'xl'}
			>
				<ClayLayout.Row>
					<ClayLayout.Col>
						{hasManyProjects && !loading && (
							<SearchHeader
								count={koroneikiAccountTotal}
								loading={searching}
								onSearchSubmit={onSearch}
								search={search}
							/>
						)}

						<ProjectList
							compressed={hasManyProjects && !loading}
							fetching={fetching}
							koroneikiAccounts={koroneikiAccounts}
							loading={loading || searching}
							maxCardsLoading={THRESHOLD_COUNT}
							onIntersect={(currentPage) =>
								fetchMore({
									variables: {
										page: currentPage + 1,
									},
								})
							}
							selectedProjectCategory={
								selectedProjectCategoryIndex
							}
						/>
					</ClayLayout.Col>
				</ClayLayout.Row>
			</ClayLayout.ContainerFluid>
		</>
	);
};

export default Home;
