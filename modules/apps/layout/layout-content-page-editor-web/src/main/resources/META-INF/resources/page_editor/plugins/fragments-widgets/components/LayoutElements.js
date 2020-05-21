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

import React from 'react';

import {useSelectItem} from '../../../app/components/Controls';
import {useDispatch, useSelector} from '../../../app/store/index';
import addItem from '../../../app/thunks/addItem';
import {useDragSymbol} from '../../../app/utils/useDragAndDrop';
import Collapse from '../../../common/components/Collapse';
import FragmentCard from './FragmentCard';

const layoutElements = [
	{
		icon: 'table',
		label: Liferay.Language.get('section'),
		type: 'container',
	},
	{
		icon: 'table',
		label: Liferay.Language.get('row'),
		type: 'row',
	},
];

const LayoutElementCard = ({icon, label, type}) => {
	const dispatch = useDispatch();
	const store = useSelector((state) => state);
	const selectItem = useSelectItem();

	const {sourceRef} = useDragSymbol(
		{
			label,
			type,
		},
		(parentId, position) => {
			dispatch(
				addItem({
					itemType: type,
					parentItemId: parentId,
					position,
					selectItem,
					store,
				})
			);
		}
	);

	return <FragmentCard icon={icon} name={label} sourceRef={sourceRef} />;
};

export default function LayoutElements() {
	return (
		<li>
			<Collapse
				label={Liferay.Language.get('layout-elements')}
				open={false}
			>
				<ul className="list-unstyled">
					{layoutElements.map((layoutElement) => {
						return (
							<LayoutElementCard
								icon={layoutElement.icon}
								key={layoutElement.type}
								label={layoutElement.label}
								type={layoutElement.type}
							/>
						);
					})}
				</ul>
			</Collapse>
		</li>
	);
}
