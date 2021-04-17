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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {Col, Row} from '@clayui/layout';
import classnames from 'classnames';
import React from 'react';

import StatusRenderer from '../data_renderers/StatusRenderer';
import Sticker from './Sticker';

const Trigger = React.forwardRef(
	({active, currentAccount, currentOrder, ...props}, ref) => (
		<ClayButton
			{...props}
			className={classnames(
				'btn-account-selector',
				currentAccount && 'account-selected'
			)}
			displayType="unstyled"
			ref={ref}
		>
			{currentAccount ? (
				<>
					<Sticker size="xl" {...currentAccount} />
					<div className="d-flex flex-column">
						<div className="account-name">
							<span className="text-truncate-inline">
								<span className="text-truncate">
									{currentAccount.name}
								</span>
							</span>
						</div>
						<div className="d-flex">
							{currentOrder?.id ? (
								<>
									<span className="order-id">
										{currentOrder.id}
									</span>
									<span className="col order-label">
										<StatusRenderer
											value={
												currentOrder?.workflowStatusInfo
											}
										/>
									</span>
								</>
							) : (
								<Row>
									<Col>
										{Liferay.Language.get(
											'there-is-no-order-selected'
										)}
									</Col>
								</Row>
							)}
						</div>
					</div>
				</>
			) : (
				<div className="no-account-selected-placeholder">
					<span className="text-truncate-inline">
						<span className="text-truncate">
							{Liferay.Language.get('select-account-and-order')}
						</span>
					</span>
				</div>
			)}

			<ClayIcon symbol={active ? 'angle-up' : 'angle-down'} />
		</ClayButton>
	)
);

export default Trigger;
