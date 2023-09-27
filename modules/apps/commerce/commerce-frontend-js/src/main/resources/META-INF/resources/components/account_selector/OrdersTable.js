/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';
import {DateTimeRenderer, StatusRenderer} from '@liferay/frontend-data-set-web';
import PropTypes from 'prop-types';
import React from 'react';

import {formatActionUrl} from '../../utilities/index';

function OrdersTable({orders, selectOrderURL}) {
	return (
		<ClayTable borderless className="orders-table">
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
				{orders.map((order) => (
					<ClayTable.Row key={order.id}>
						<ClayTable.Cell headingTitle>
							<a href={formatActionUrl(selectOrderURL, order)}>
								{order.id}
							</a>
						</ClayTable.Cell>

						<ClayTable.Cell>
							<StatusRenderer value={order.orderStatusInfo} />
						</ClayTable.Cell>

						<ClayTable.Cell>
							<DateTimeRenderer value={order.modifiedDate} />
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
