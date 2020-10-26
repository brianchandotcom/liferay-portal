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
import React from 'react';

import {withLoading} from '../../../components/loading/Loading.es';
import {withEmpty} from '../../../components/table/EmptyState.es';
import {getLocalizedValue} from '../../../utils/lang.es';
import {fromNow} from '../../../utils/time.es';

const {Body, Cell, Head, Row} = ClayTable;

const ListItems = ({defaultLanguageId, itemId, items, onChange}) => {
	return (
		<table className="table table-autofit table-heading-nowrap table-hover table-nowrap table-responsive">
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
				{items.map(({dateCreated, dateModified, id, name}, index) => (
					<Row
						className={classNames('selectable-row', {
							'selectable-active': id === itemId,
						})}
						key={index}
						onClick={() => onChange(items[index])}
					>
						<Cell align="left">
							{getLocalizedValue(defaultLanguageId, name)}
						</Cell>
						<Cell>{dateCreated && fromNow(dateCreated)}</Cell>
						<Cell>{dateModified && fromNow(dateModified)}</Cell>
						<Cell align={'right'}>
							<ClayRadioGroup
								inline
								onSelectedValueChange={() =>
									onChange(items[index])
								}
								selectedValue={itemId}
							>
								<ClayRadio value={id} />
							</ClayRadioGroup>
						</Cell>
					</Row>
				))}
			</Body>
		</table>
	);
};

export default withLoading(withEmpty(ListItems));
