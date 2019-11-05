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

import React, {useContext, useMemo, useState} from 'react';

import {StoreContext} from '../../../app/store/index';
import Collapse from '../../../common/components/Collapse';
import SearchForm from '../../../common/components/SearchForm';
import SidebarPanelContent from '../../../common/components/SidebarPanelContent';
import SidebarPanelHeader from '../../../common/components/SidebarPanelHeader';
import FragmentCard from './FragmentCard';
import Layouts from './Layouts';

export default function SectionBuilderSidebar() {
	const {fragments} = useContext(StoreContext);
	const [searchValue, setSearchValue] = useState('');

	const filtererdFragments = useMemo(() => {
		const searchValueLowerCase = searchValue.toLowerCase();

		return fragments
			.map(fragmentCollection => {
				return {
					...fragmentCollection,
					fragmentEntries: fragmentCollection.fragmentEntries.filter(
						fragmentEntry =>
							fragmentEntry.name
								.toLowerCase()
								.indexOf(searchValueLowerCase) !== -1
					)
				};
			})
			.filter(fragmentCollection => {
				return fragmentCollection.fragmentEntries.length > 0;
			});
	}, [searchValue, fragments]);

	return (
		<>
			<SidebarPanelHeader>
				{Liferay.Language.get('section-builder')}
			</SidebarPanelHeader>

			<SidebarPanelContent>
				<SearchForm onChange={setSearchValue} value={searchValue} />

				{!searchValue.length && <Layouts />}

				{filtererdFragments.map(fragmentCollection => (
					<div key={fragmentCollection.fragmentCollectionId}>
						<Collapse
							label={fragmentCollection.name}
							open={searchValue.length > 0}
						>
							<div className="align-items-start d-flex flex-wrap justify-content-between">
								{fragmentCollection.fragmentEntries.map(
									fragmentEntry => (
										<FragmentCard
											imagePreviewURL={
												fragmentEntry.imagePreviewURL
											}
											key={fragmentEntry.fragmentEntryKey}
											name={fragmentEntry.name}
										/>
									)
								)}
							</div>
						</Collapse>
					</div>
				))}
			</SidebarPanelContent>
		</>
	);
}
