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

import ClayTable from '@clayui/table';
import PropTypes from 'prop-types';
import React from 'react';

import {formatActionUrl} from '../../utilities/index';
import DateRenderer from '../data_renderers/DateRenderer';
import StatusRenderer from '../data_renderers/StatusRenderer';

function OrdersTable(props) {
	return (
		<ClayTable borderless className="orders-list">
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell headingCell>
						{Liferay.Language.get('order-number')}
					</ClayTable.Cell>
					<ClayTable.Cell headingCell>
						{Liferay.Language.get('status')}
					</ClayTable.Cell>
					<ClayTable.Cell headingCell>
						{Liferay.Language.get('last-modified')}
					</ClayTable.Cell>
				</ClayTable.Row>
			</ClayTable.Head>
			<ClayTable.Body>
				{props.orders.map((order) => (
					<ClayTable.Row key={order.id}>
						<ClayTable.Cell headingTitle>
							<a
								href={formatActionUrl(
									props.selectOrderURL,
									order
								)}
							>
								{order.id}
							</a>
						</ClayTable.Cell>
						<ClayTable.Cell>
							<StatusRenderer value={order.orderStatusInfo} />
						</ClayTable.Cell>
						<ClayTable.Cell>
							<DateRenderer value={order.modifiedDate} />
						</ClayTable.Cell>
					</ClayTable.Row>
				))}
			</ClayTable.Body>
		</ClayTable>
	);
}

OrdersTable.propTypes = {
	orders: PropTypes.arrayOf(
		PropTypes.shape({
			id: PropTypes.number.isRequired,
			modifiedDate: PropTypes.string.isRequired,
			orderStatusInfo: PropTypes.object.isRequired,
		})
	),
	selectOrderURL: PropTypes.string.isRequired,
};

export default OrdersTable;
