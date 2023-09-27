/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {Col, Row} from '@clayui/layout';
import {StatusRenderer} from '@liferay/frontend-data-set-web';
import classnames from 'classnames';
import React from 'react';

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
