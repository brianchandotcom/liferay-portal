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
import SidebarWidgetsDragDrop from './SidebarWidgetsDragDrop.es';
import Widget from './Widget.es';

const SidebarWidgets = () => {
	const dispatch = useDispatch();
	const widgets = useSelector(state => state.widgets);
	const [searchValue, setSearchValue] = useState('');

	useEffect(() => {
		const dragDrop = new SidebarWidgetsDragDrop({dispatch});

		return () => {
			dragDrop.dispose();
		};
	}, [dispatch]);

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
			<SidebarHeader>{Liferay.Language.get('widgets')}</SidebarHeader>

			<div className="fragments-editor-sidebar-section__panel p-3">
				<SearchForm onChange={setSearchValue} value={searchValue} />

				{filteredWidgets.map(category => (
					<div key={category.title}>
						<Collapse
							label={category.title}
							open={searchValue.length > 0}
						>
							{category.portlets.map(widget => (
								<Widget
									key={widget.portletId}
									widget={widget}
								/>
							))}
						</Collapse>
					</div>
				))}
			</div>
		</>
	);
};

export default SidebarWidgets;
