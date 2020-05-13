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

import classNames from 'classnames';
import React from 'react';

import {useSelectItem} from '../../../app/components/Controls';
import {LAYOUT_DATA_ITEM_TYPE_LABELS} from '../../../app/config/constants/layoutDataItemTypeLabels';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../app/config/constants/layoutDataItemTypes';
import {useDispatch, useSelector} from '../../../app/store/index';
import addItem from '../../../app/thunks/addItem';
import {useDragSymbol} from '../../../app/utils/useDragAndDrop';
import Collapse from '../../../common/components/Collapse';

const layoutElements = [
	{
		Preview: () => (
			<div className="px-0 row">
				<div className="border-left-0 border-right-0 col col-12 mx-0"></div>
			</div>
		),
		label: LAYOUT_DATA_ITEM_TYPE_LABELS.container,
		type: LAYOUT_DATA_ITEM_TYPES.container,
	},
	{
		Preview: () => (
			<div className="row">
				<div className="col col-6"></div>
				<div className="col col-6"></div>
			</div>
		),
		label: LAYOUT_DATA_ITEM_TYPE_LABELS.row,
		type: LAYOUT_DATA_ITEM_TYPES.row,
	},
];

const LayoutElementCard = ({Preview, label, type}) => {
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

	return (
		<button
			aria-label={label}
			className={classNames(
				'page-editor__fragments__layout-element-card-preview',
				'card',
				'card-interactive',
				'card-interactive-secondary',
				'selector-button'
			)}
			ref={sourceRef}
			type="button"
		>
			<div className="card-body px-2 py-3" role="image">
				<div className="container p-0">
					<Preview />
				</div>
				<div className="card-title pt-3 text-truncate">{label}</div>
			</div>
		</button>
	);
};

export default function LayoutElements() {
	return (
		<>
			<Collapse
				label={Liferay.Language.get('layout-elements')}
				open={false}
			>
				<div className="d-flex flex-wrap justify-content-between">
					{layoutElements.map((layoutElement) => {
						return (
							<LayoutElementCard
								key={layoutElement.label}
								label={layoutElement.label}
								Preview={layoutElement.Preview}
								type={layoutElement.type}
							/>
						);
					})}
				</div>
			</Collapse>
		</>
	);
}
