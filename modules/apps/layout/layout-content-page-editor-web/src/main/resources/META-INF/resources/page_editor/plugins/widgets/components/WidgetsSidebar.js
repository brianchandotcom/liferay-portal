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

import React, {useMemo, useState} from 'react';

import {useSelector} from '../../../app/store/index';
import Collapse from '../../../common/components/Collapse';
import SearchForm from '../../../common/components/SearchForm';
import SidebarPanelContent from '../../../common/components/SidebarPanelContent';
import SidebarPanelHeader from '../../../common/components/SidebarPanelHeader';
import Widget from './Widget';

export default function WidgetsSidebar() {
	const widgets = useSelector(state => state.widgets);
	const [searchValue, setSearchValue] = useState('');

	const filteredWidgets = useMemo(() => {
		const searchValueLowerCase = searchValue.toLowerCase();

		return widgets
			.map(category => {
				return {
					...category,
					portlets: category.portlets.filter(
						widget =>
							widget.title
								.toLowerCase()
								.indexOf(searchValueLowerCase) !== -1
					)
				};
			})
			.filter(category => {
				return category.portlets.length > 0;
			});
	}, [searchValue, widgets]);

	return (
		<>
			<SidebarPanelHeader>
				{Liferay.Language.get('widgets')}
			</SidebarPanelHeader>

			<SidebarPanelContent>
				<SearchForm onChange={setSearchValue} value={searchValue} />

				{filteredWidgets.map(category => (
					<div key={category.title}>
						<Collapse
							label={category.title}
							open={searchValue.length > 0}
						>
							{category.portlets.map(widget => (
								<Widget key={widget.portletId} {...widget} />
							))}
						</Collapse>
					</div>
				))}
			</SidebarPanelContent>
		</>
	);
}
