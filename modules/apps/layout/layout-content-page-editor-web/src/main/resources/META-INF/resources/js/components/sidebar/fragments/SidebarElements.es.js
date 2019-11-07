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

import React, {useEffect, useMemo, useState} from 'react';

import useDispatch from '../../../store/hooks/useDispatch.es';
import useSelector from '../../../store/hooks/useSelector.es';
import SearchForm from '../../common/SearchForm.es';
import SidebarHeader from '../SidebarHeader.es';
import Collapse from './../../common/Collapse.es';
import SidebarCard from './../SidebarCard.es';
import SidebarElementsDragDrop from './SidebarElementsDragDrop.es';
import SidebarLayouts from './SidebarLayouts.es';

const SidebarElements = () => {
	const dispatch = useDispatch();
	const elements = useSelector(state => state.elements);
	const [searchValue, setSearchValue] = useState('');

	const filteredElements = useMemo(() => {
		const searchValueLowerCase = searchValue.toLowerCase();

		return elements
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
	}, [searchValue, elements]);

	useEffect(() => {
		const dragDrop = new SidebarElementsDragDrop({dispatch});

		return () => {
			dragDrop.dispose();
		};
	}, [dispatch]);

	return (
		<>
			<SidebarHeader>{Liferay.Language.get('fragments')}</SidebarHeader>

			<div className="fragments-editor-sidebar-section__elements-panel fragments-editor-sidebar-section__panel p-3">
				<SearchForm onChange={setSearchValue} value={searchValue} />

				{!searchValue.length && <SidebarLayouts />}

				{filteredElements.map(fragmentCollection => (
					<div key={fragmentCollection.fragmentCollectionId}>
						<Collapse
							label={fragmentCollection.name}
							open={searchValue.length > 0}
						>
							<div className="fragments-editor-sidebar-section__elements-panel-grid">
								{fragmentCollection.fragmentEntries.map(
									fragmentEntry => (
										<SidebarCard
											icon="plus"
											item={fragmentEntry}
											key={fragmentEntry.fragmentEntryKey}
										/>
									)
								)}
							</div>
						</Collapse>
					</div>
				))}
			</div>
		</>
	);
};

export default SidebarElements;
