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
import React, {useEffect, useState} from 'react';
import Button from '../../components/button/Button.es';
import {getItem} from '../../utils/client.es';

const {Body, Cell, Head, Row} = ClayTable;

export default ({endpoint, itemId, onItemIdChange, title}) => {
	const [items, setItems] = useState([]);

	useEffect(() => {
		getItem(endpoint)
			.then(response => response.items || [])
			.then(setItems);
	}, [endpoint]);

	return (
		<>
			<div className="autofit-row pl-4 pr-4 mb-4">
				<div className="autofit-col-expand">
					<h2>{title}</h2>
				</div>
			</div>
			<div className="autofit-row pl-4 pr-4 mb-4">
				<div className="autofit-col-expand">
					<div className="input-group">
						<div className="input-group-item">
							<input
								aria-label={Liferay.Language.get('search')}
								className="form-control input-group-inset input-group-inset-after"
								placeholder={`${Liferay.Language.get(
									'search'
								)}...`}
								type="text"
							/>
							<div className="input-group-inset-item input-group-inset-item-after">
								<Button
									disabled={true}
									displayType="unstyled"
									symbol="search"
								/>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div className="autofit-row pl-4 pr-4 scrollable-container">
				<div className="autofit-col-expand">
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
											className={classNames(
												'selectable-row',
												{
													'selectable-active':
														id === itemId
												}
											)}
											key={index}
											onClick={() => onItemIdChange(id)}
										>
											<Cell align="left">{itemName}</Cell>
											<Cell>
												{moment(dateCreated).fromNow()}
											</Cell>
											<Cell>
												{moment(dateModified).fromNow()}
											</Cell>
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
				</div>
			</div>
		</>
	);
};
