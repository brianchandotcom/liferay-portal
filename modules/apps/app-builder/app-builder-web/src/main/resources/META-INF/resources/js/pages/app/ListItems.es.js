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

import {ClayRadio, ClayRadioGroup} from '@clayui/form';
import ClayTable from '@clayui/table';
import classNames from 'classnames';
import moment from 'moment';
import React, {useContext} from 'react';

import {withLoading} from '../../components/loading/Loading.es';
import {withEmpty} from '../../components/table/EmptyState.es';
import EditAppContext, {
	UPDATE_DATA_LAYOUT_ID,
	UPDATE_DATA_LIST_VIEW_ID
} from './EditAppContext.es';

const {Body, Cell, Head, Row} = ClayTable;

const ListItems = ({itemType, items}) => {
	const {
		dispatch,
		state: {
			app: {dataLayoutId, dataListViewId}
		}
	} = useContext(EditAppContext);

	const itemId = itemType === 'DATA_LAYOUT' ? dataLayoutId : dataListViewId;

	const onItemIdChange = id => {
		const type =
			itemType === 'DATA_LAYOUT'
				? UPDATE_DATA_LAYOUT_ID
				: UPDATE_DATA_LIST_VIEW_ID;

		dispatch({
			id,
			type
		});
	};

	return (
		<table className="table table-responsive table-autofit table-hover table-heading-nowrap table-nowrap">
			<Head>
				<Row>
					<Cell expanded={true} headingCell>
						{Liferay.Language.get('name')}
					</Cell>
					<Cell headingCell>
						{Liferay.Language.get('create-date')}
					</Cell>
					<Cell headingCell>
						{Liferay.Language.get('modified-date')}
					</Cell>
					<Cell headingCell></Cell>
				</Row>
			</Head>
			<Body>
				{items.map(
					(
						{
							dateCreated,
							dateModified,
							id,
							name: {en_US: itemName}
						},
						index
					) => {
						return (
							<Row
								className={classNames('selectable-row', {
									'selectable-active': id === itemId
								})}
								key={index}
								onClick={() => onItemIdChange(id)}
							>
								<Cell align="left">{itemName}</Cell>
								<Cell>{moment(dateCreated).fromNow()}</Cell>
								<Cell>{moment(dateModified).fromNow()}</Cell>
								<Cell align={'right'}>
									<ClayRadioGroup
										inline
										onSelectedValueChange={() =>
											onItemIdChange(id)
										}
										selectedValue={itemId}
									>
										<ClayRadio value={id} />
									</ClayRadioGroup>
								</Cell>
							</Row>
						);
					}
				)}
			</Body>
		</table>
	);
};

export default withLoading(withEmpty(ListItems));
