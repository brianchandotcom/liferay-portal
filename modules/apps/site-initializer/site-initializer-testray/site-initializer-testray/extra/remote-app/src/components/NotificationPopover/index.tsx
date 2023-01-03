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
import ClayTable from '@clayui/table';
import classNames from 'classnames';
import {useState} from 'react';

import i18n from '../../i18n';
import {APIResponse, TestrayDispatchTrigger} from '../../services/rest';
import {getTimeFromNow} from '../../util/date';
import {DispatchTriggerStatuses} from '../../util/statuses';
import Avatar from '../Avatar';
import EmptyState from '../EmptyState';
import Form from '../Form';
import StatusBadge from '../StatusBadge';

type NotificationPopoverProps = {
	testrayDispatchTriggers: APIResponse<TestrayDispatchTrigger>;
};

const NotificationPopover: React.FC<NotificationPopoverProps> = ({
	testrayDispatchTriggers,
}) => {
	const [visible, setVisible] = useState<boolean>(false);

	const notifications = testrayDispatchTriggers?.items ?? [];

	const jobsInProgress =
		testrayDispatchTriggers?.facets[0].facetValues
			.filter((facetValue) =>
				([
					DispatchTriggerStatuses.INPROGRESS,
					DispatchTriggerStatuses.SCHEDULED,
				] as string[]).includes(facetValue.term)
			)
			.map((facetValue) => facetValue.numberOfOccurrences)
			.reduce(
				(previousValue, currentValue) => previousValue + currentValue,
				0
			) ?? 0;

	return (
		<>
			<div
				className="align-items-center cursor-pointer d-flex mx-2"
				onClick={() => setVisible((visible) => !visible)}
				title={i18n.sub(
					'x-jobs-in-progress',
					jobsInProgress.toString()
				)}
			>
				<ClayIcon fontSize={22} symbol="bell-on" />

				{jobsInProgress > 0 && (
					<span className="header-notification show">
						{jobsInProgress}
					</span>
				)}
			</div>

			<div
				className={classNames('notification-popover box-visible', {
					'box-hidden': !visible,
					'box-visible': visible,
				})}
			>
				<div className="align-items d-flex flex-column justify-content-between m-3">
					<div className="align-items-center d-flex justify-content-between">
						<label className="mb-0">
							{i18n.translate('notifications')}
						</label>

						<span
							className="cursor-pointer"
							onClick={() => setVisible(false)}
						>
							<ClayIcon symbol="times" />
						</span>
					</div>

					<Form.Divider />

					{notifications.length ? (
						<ClayTable borderless>
							<ClayTable.Head>
								<ClayTable.Row>
									<ClayTable.Cell headingCell>
										{i18n.translate('type')}
									</ClayTable.Cell>

									<ClayTable.Cell headingCell>
										{i18n.translate('status')}
									</ClayTable.Cell>

									<ClayTable.Cell headingCell>
										{i18n.translate('created')}
									</ClayTable.Cell>

									<ClayTable.Cell headingCell>
										{i18n.translate('creator')}
									</ClayTable.Cell>
								</ClayTable.Row>
							</ClayTable.Head>

							<ClayTable.Body>
								{notifications.map((notification, index) => (
									<ClayTable.Row key={index}>
										<ClayTable.Cell headingTitle>
											{notification.type}
										</ClayTable.Cell>

										<ClayTable.Cell>
											<StatusBadge
												type={
													notification.dueStatus.key.toLowerCase() as any
												}
											>
												{notification.dueStatus.name}
											</StatusBadge>
										</ClayTable.Cell>

										<ClayTable.Cell headingCell>
											{getTimeFromNow(
												notification.dateCreated
											)}
										</ClayTable.Cell>

										<ClayTable.Cell>
											<Avatar
												displayName={true}
												name={notification.creator.name}
												url={notification.creator.image}
											/>
										</ClayTable.Cell>
									</ClayTable.Row>
								))}
							</ClayTable.Body>
						</ClayTable>
					) : (
						<EmptyState />
					)}
				</div>
			</div>
		</>
	);
};

export default NotificationPopover;
