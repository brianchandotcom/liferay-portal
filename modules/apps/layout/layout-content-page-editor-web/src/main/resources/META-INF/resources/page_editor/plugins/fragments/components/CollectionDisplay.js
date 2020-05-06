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

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React from 'react';

import {useSelectItem} from '../../../app/components/Controls';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../app/config/constants/layoutDataItemTypes';
import {useDispatch, useSelector} from '../../../app/store/index';
import addItem from '../../../app/thunks/addItem';
import {useDragSymbol} from '../../../app/utils/useDragAndDrop';
import Collapse from '../../../common/components/Collapse';

export const CollectionDisplayCard = () => {
	const dispatch = useDispatch();
	const store = useSelector((state) => state);
	const selectItem = useSelectItem();

	const {sourceRef} = useDragSymbol(
		{
			label: Liferay.Language.get('collection-display'),
			type: LAYOUT_DATA_ITEM_TYPES.collection,
		},
		(parentId, position) => {
			dispatch(
				addItem({
					itemType: LAYOUT_DATA_ITEM_TYPES.collection,
					parentItemId: parentId,
					position,
					selectItem,
					store,
				})
			);
		}
	);

	return (
		<div
			className={classNames(
				'page-editor__fragments__fragment-card',
				'card',
				'card-interactive',
				'card-interactive-secondary',
				'selector-button',
				'overflow-hidden'
			)}
			key={'collection-display'}
			ref={sourceRef}
		>
			<div className="page-editor__fragments__fragment-card-no-preview">
				<ClayIcon symbol="list" />
			</div>

			<div className="card-body">
				<div className="card-row">
					<div className="autofit-col autofit-col-expand autofit-row-center">
						<div className="card-title text-truncate">
							{Liferay.Language.get('collection-display')}
						</div>
					</div>
				</div>
			</div>
		</div>
	);
};

export default function CollectionDisplay() {
	return (
		<>
			<Collapse
				label={Liferay.Language.get('collection-display')}
				open={false}
			>
				<div className="d-flex flex-wrap justify-content-between">
					<CollectionDisplayCard />
				</div>
			</Collapse>
		</>
	);
}
