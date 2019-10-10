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

import Collapse from './../../common/Collapse.es';
import React, {useEffect} from 'react';
import SidebarLayoutsDragDrop from './SidebarLayoutsDragDrop.es';
import useDispatch from '../../../store/hooks/useDispatch.es';

const layouts = [
	{
		columns: ['12']
	},
	{
		columns: ['6', '6']
	},
	{
		columns: ['8', '4']
	},
	{
		columns: ['4', '8']
	},
	{
		columns: ['4', '4', '4']
	},
	{
		columns: ['3', '3', '3', '3']
	}
];

const SidebarLayoutCard = props => (
	<div
		className="card card-interactive card-interactive-secondary selector-button fragments-editor-sidebar-section__card fragments-editor__drag-source fragments-editor__drag-source--sidebar-layout"
		data-drag-source-label={Liferay.Language.get('layout')}
		data-layout-index={props.layoutIndex}
		label={Liferay.Util.sub(
			Liferay.Language.get('layout-of-x'),
			props.layoutColumns.join('-')
		)}
	>
		<div className="fragments-editor__drag-handler"></div>

		<div className="card-body">
			<div className="card-row">
				<div className="autofit-col autofit-col-expand autofit-row-center">
					<div className="container sidebar-layouts-section__layout-preview">
						<div
							className="row sidebar-layouts-section__layout-preview__row"
							role="image"
						>
							{props.layoutColumns.map((column, index) => (
								<div
									className={`col-${column} sidebar-layouts-section__layout-preview__column`}
									key={`${index}-${column}`}
								></div>
							))}
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
);

const SidebarLayouts = () => {
	const dispatch = useDispatch();

	useEffect(() => {
		const dragDrop = new SidebarLayoutsDragDrop({dispatch, layouts});

		return () => {
			dragDrop.dispose();
		};
	}, [dispatch]);

	return (
		<>
			<Collapse label={Liferay.Language.get('layouts')} open={false}>
				<div className="sidebar-layouts-section sidebar-section">
					{layouts.map(layout => {
						return (
							<SidebarLayoutCard
								key={`layout-of-${layout.columns.join('-')}`}
								layoutColumns={layout.columns}
								layoutIndex={layouts.indexOf(layout)}
							/>
						);
					})}
				</div>
			</Collapse>
		</>
	);
};

export default SidebarLayouts;
