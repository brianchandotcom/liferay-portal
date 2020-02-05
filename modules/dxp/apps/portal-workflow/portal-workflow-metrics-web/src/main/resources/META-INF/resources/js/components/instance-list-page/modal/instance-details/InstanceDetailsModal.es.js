/* eslint-disable react-hooks/exhaustive-deps */
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import React, {useContext, useEffect, useState} from 'react';

import moment from '../../../../shared/util/moment.es';
import {AppContext} from '../../../AppContext.es';
import {InstanceListContext} from '../../store/InstanceListPageStore.es';
import {ModalContext} from '../ModalContext.es';

const InstanceDetailsModal = () => {
	const [instance, setInstance] = useState({});

	const {client} = useContext(AppContext);
	const {instanceId} = useContext(InstanceListContext);
	const {instanceDetailsModal, setInstanceDetailsModal} = useContext(
		ModalContext
	);

	const {processId, visible} = instanceDetailsModal;

	useEffect(() => {
		if (instanceId) {
			fetchData(instanceId, processId).then(data => setInstance(data));
		}
	}, [instanceId, processId]);

	const fetchData = (instanceId, processId) => {
		return client
			.get(`/processes/${processId}/instances/${instanceId}`)
			.then(({data}) => data);
	};

	const {
		assetTitle,
		assetType,
		assigneeUsers,
		creatorUser,
		dateCompletion,
		dateCreated,
		slaResults = [],
		slaStatus,
		status,
		taskNames = []
	} = instance;

	const completed = status === 'Completed';
	const empty = slaResults.length === 0;
	const overdue = slaStatus === 'Overdue';
	const slaOpen = slaResults.filter(({status}) =>
		['Running', 'Paused'].includes(status)
	);
	const slaResolved = slaResults.filter(({status}) => status === 'Stopped');

	let styleName = 'text-danger';

	if (empty) {
		styleName = 'text-info';
	}
	else if (status === 'Completed') {
		styleName = 'text-secondary';
	}
	else if (status === 'Pending' && slaStatus === 'OnTime') {
		styleName = 'text-success';
	}

	let iconTitleName = 'check-circle';

	if (empty) {
		iconTitleName = 'hr';
	}
	else if (overdue) {
		iconTitleName = 'exclamation-circle';
	}

	const {observer} = useModal({
		onClose: () => {
			setInstanceDetailsModal({
				...instanceDetailsModal,
				visible: false
			});
		}
	});

	return (
		<>
			{visible && (
				<ClayModal
					className="instance-details-modal"
					observer={observer}
					size="lg"
				>
					<ClayModal.Header>
						<div className="font-weight-medium">
							<span
								className={`modal-title-indicator ${styleName}`}
							>
								<ClayIcon symbol={iconTitleName} />
							</span>

							{`${Liferay.Language.get('item')} #${instanceId}`}
						</div>
					</ClayModal.Header>
					<ClayModal.Body>
						<InstanceDetailsModal.SectionTitle>
							{Liferay.Language.get('due-date-by-sla')}
						</InstanceDetailsModal.SectionTitle>

						{slaResults.length === 0 && (
							<p>
								<span className="font-weight-medium text-muted">
									{Liferay.Language.get(
										'no-sla-records-for-this-item'
									)}
								</span>
							</p>
						)}

						{!!slaOpen.length && (
							<InstanceDetailsModal.SectionSubTitle>
								{`${Liferay.Language.get(
									'open'
								).toUpperCase()} (${slaOpen.length})`}
							</InstanceDetailsModal.SectionSubTitle>
						)}

						{slaOpen.map(item => (
							<InstanceDetailsModal.Item
								key={item.id}
								{...item}
							/>
						))}

						{!!slaResolved.length && (
							<InstanceDetailsModal.SectionSubTitle>
								{`${Liferay.Language.get(
									'resolved'
								).toUpperCase()} (${slaResolved.length})`}
							</InstanceDetailsModal.SectionSubTitle>
						)}

						{slaResolved.map(item => (
							<InstanceDetailsModal.Item
								key={item.id}
								{...item}
							/>
						))}

						<InstanceDetailsModal.SectionTitle className="mt-5">
							{Liferay.Language.get('process-details')}
						</InstanceDetailsModal.SectionTitle>

						<InstanceDetailsModal.SectionAttribute
							description={Liferay.Language.get('process-status')}
							detail={status}
						/>

						<InstanceDetailsModal.SectionAttribute
							description={Liferay.Language.get('created-by')}
							detail={creatorUser ? creatorUser.name : ''}
						/>

						{!!dateCreated && (
							<InstanceDetailsModal.SectionAttribute
								description={Liferay.Language.get(
									'creation-date'
								)}
								detail={moment
									.utc(dateCreated)
									.format(
										Liferay.Language.get('mmm-dd-yyyy-lt')
									)}
							/>
						)}

						<InstanceDetailsModal.SectionAttribute
							description={Liferay.Language.get('asset-type')}
							detail={assetType}
						/>

						<InstanceDetailsModal.SectionAttribute
							description={Liferay.Language.get('asset-title')}
							detail={assetTitle}
						/>

						{!completed && (
							<InstanceDetailsModal.SectionAttribute
								description={Liferay.Language.get(
									'current-step'
								)}
								detail={taskNames.join(', ')}
							/>
						)}

						{completed && !!dateCompletion && (
							<InstanceDetailsModal.SectionAttribute
								description={Liferay.Language.get('end-date')}
								detail={moment
									.utc(dateCompletion)
									.format(
										Liferay.Language.get('mmm-dd-yyyy-lt')
									)}
							/>
						)}

						{!completed && (
							<InstanceDetailsModal.SectionAttribute
								description={Liferay.Language.get(
									'current-assignee'
								)}
								detail={
									assigneeUsers && assigneeUsers.length
										? assigneeUsers
												.map(user => user.name)
												.join(', ')
										: Liferay.Language.get('unassigned')
								}
							/>
						)}

						<a
							className="btn btn-secondary btn-sm font-weight-medium mb-1 mt-3"
							href={`/group/control_panel/manage/-/workflow_instance/view/${instanceId}`}
							target="_blank"
						>
							{Liferay.Language.get('go-to-submission-page')}

							<span className="inline-item inline-item-after">
								<ClayIcon symbol="shortcut" />
							</span>
						</a>
					</ClayModal.Body>
				</ClayModal>
			)}
		</>
	);
};

const Item = ({dateOverdue, name, onTime, remainingTime, status}) => {
	let bgColor = 'danger';
	let iconClassName = 'text-danger';
	let iconName = 'exclamation-circle';
	let statusText = `(${Liferay.Language.get('resolved-overdue')})`;

	if (onTime) {
		bgColor = 'success';
		iconClassName = 'text-success';
		iconName = 'check-circle';
	}

	if (status === 'Paused') {
		statusText = `(${Liferay.Language.get('sla-paused')})`;
	}
	else if (status === 'Running') {
		const remainingTimePositive = onTime
			? remainingTime
			: remainingTime * -1;

		const remainingTimeUTC = moment.utc(remainingTimePositive);

		const durationText =
			remainingTimeUTC.format('D') -
			1 +
			remainingTimeUTC.format('[d] HH[h] mm[min]');

		let onTimeText = Liferay.Language.get('overdue');

		if (onTime) {
			onTimeText = Liferay.Language.get('left');
		}

		statusText = `${moment
			.utc(dateOverdue)
			.format(
				Liferay.Language.get('mmm-dd-yyyy-lt')
			)} (${durationText} ${onTimeText})`;
	}
	else if (status === 'Stopped' && onTime) {
		statusText = `(${Liferay.Language.get('resolved-on-time')})`;
	}

	return (
		<p>
			<span
				className={`bg-${bgColor}-light sticker`}
				style={{height: '1.5rem', width: '1.5rem'}}
			>
				<span className="inline-item" style={{fontSize: '10px'}}>
					<ClayIcon className={iconClassName} symbol={iconName} />
				</span>
			</span>

			<span className="font-weight-medium small text-secondary">{` ${name} `}</span>

			<span className="small">{statusText}</span>
		</p>
	);
};

const SectionTitle = ({children, className = ''}) => {
	const classNames = `${className} font-weight-medium mb-4`;

	return <h4 className={classNames}>{children}</h4>;
};

const SectionSubTitle = ({children}) => {
	return (
		<h5 className="font-weight-medium mb-4 mt-4 text-secondary">
			{children}
		</h5>
	);
};

const SectionAttribute = ({description, detail}) => {
	return (
		<p className="row">
			<span className="col-2 font-weight-medium small text-secondary">
				{`${description} `}
			</span>

			<span className="col small" data-testid="instanceDetailSpan">
				{detail}
			</span>
		</p>
	);
};

InstanceDetailsModal.Item = Item;
InstanceDetailsModal.SectionTitle = SectionTitle;
InstanceDetailsModal.SectionSubTitle = SectionSubTitle;
InstanceDetailsModal.SectionAttribute = SectionAttribute;

export {InstanceDetailsModal};
