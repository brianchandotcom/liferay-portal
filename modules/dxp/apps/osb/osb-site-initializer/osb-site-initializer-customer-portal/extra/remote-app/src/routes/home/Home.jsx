/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import {useAppPropertiesContext} from '~/common/contexts/AppPropertiesContext';
import ProjectList from './components/ProjectsList';
import SearchHeader from './components/SearchHeader';

import './app.scss';

import {useMemo, useState} from 'react';
import useKoroneikiAccounts from '~/common/hooks/useKoroneikiAccounts';
import ProjectCategoryDropdown from './components/ProjectCategoryDropdown';
import useProjectCategoryItems from './hooks/useProjectCategoryItems';

const THRESHOLD_COUNT = 4;

const Home = () => {
	const [
		selectedProjectCategoryKey,
		setSelectedProjectCategoryKey,
	] = useState('all-projects');

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
		selectedFilterCategory: projectCategoryItems.find(
			({key}) => key === selectedProjectCategoryKey
		),
	});

	const handleOnSelect = (key) => {
		setSelectedProjectCategoryKey(key);
		onSearch('');
	};

	const {featureFlags} = useAppPropertiesContext();

	const koroneikiAccounts = data?.c?.koroneikiAccounts;
	const koroneikiAccountTotal = koroneikiAccounts?.totalCount;

	const koroneikiCount =
		firstKoroneikiAccountsTotal[selectedProjectCategoryKey];

	const hasManyProjects = koroneikiCount > THRESHOLD_COUNT;

	const hasAvailableCategoriesToDisplay = useMemo(
		() =>
			projectCategoryItems
				.filter((projectCategoryItem) =>
					['liferay-contact', 'fls-partner'].includes(
						projectCategoryItem.key
					)
				)
				.some(({disabled}) => !disabled),
		[projectCategoryItems]
	);

	return (
		<>
			{featureFlags.includes('LPS-191380') &&
				hasAvailableCategoriesToDisplay && (
					<ProjectCategoryDropdown
						loading={loading || koroneikiCount === null}
						onSelect={handleOnSelect}
						projectCategoryItems={projectCategoryItems}
						selectedProjectCategoryKey={selectedProjectCategoryKey}
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
							loading={
								loading || searching || koroneikiCount === null
							}
							maxCardsLoading={THRESHOLD_COUNT}
							onIntersect={(currentPage) =>
								fetchMore({
									variables: {
										page: currentPage + 1,
									},
								})
							}
						/>
					</ClayLayout.Col>
				</ClayLayout.Row>
			</ClayLayout.ContainerFluid>
		</>
	);
};

export default Home;
