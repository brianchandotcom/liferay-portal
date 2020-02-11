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
import React, {useContext, useEffect} from 'react';
import {useDrag} from 'react-dnd';
import {getEmptyImage} from 'react-dnd-html5-backend';

import {useSelectItem} from '../../../app/components/Controls';
import {LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS} from '../../../app/config/constants/layoutDataItemDefaultConfigurations';
import {ConfigContext} from '../../../app/config/index';
import {useDispatch, useSelector} from '../../../app/store/index';
import addItem from '../../../app/thunks/addItem';
import Collapse from '../../../common/components/Collapse';

const layoutElements = [
	{
		columns: ['12'],
		label: Liferay.Language.get('section'),
		type: 'container'
	},
	{
		columns: ['4', '4', '4'],
		label: Liferay.Language.get('row'),
		type: 'row'
	}
];

const LayoutElementCard = ({label, layoutColumns, type}) => {
	const config = useContext(ConfigContext);
	const dispatch = useDispatch();
	const store = useSelector(state => state);
	const selectItem = useSelectItem();

	const [, drag, preview] = useDrag({
		end(item, monitor) {
			const itemConfig = LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS[type];

			const result = monitor.getDropResult();

			if (!result) {
				return;
			}

			const {parentId, position} = result;

			dispatch(
				addItem({
					config,
					itemConfig,
					itemType: item.type,
					parentItemId: parentId,
					position,
					selectItem,
					store
				})
			);
		},
		item: {
			name: label,
			type
		}
	});

	useEffect(() => {
		preview(getEmptyImage(), {captureDraggingState: true});
	}, [preview]);

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
			ref={drag}
			type="button"
		>
			<div className="card-body px-2 py-3" role="image">
				<div className="container p-0">
					<div className="row">
						{layoutColumns.map((column, index) => (
							<div
								className={`col col-${column}`}
								key={`${index}-${column}`}
							></div>
						))}
					</div>
				</div>
				<div className="card-title pt-3 text-truncate" title={label}>
					{label}
				</div>
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
					{layoutElements.map(layoutElement => {
						return (
							<LayoutElementCard
								key={layoutElement.columns.join()}
								label={layoutElement.label}
								layoutColumns={layoutElement.columns}
								type={layoutElement.type}
							/>
						);
					})}
				</div>
			</Collapse>
		</>
	);
}
