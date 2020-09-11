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
import ClayLayout from '@clayui/layout';
import ClayModal from '@clayui/modal';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useMemo} from 'react';

import ContentView from '../../../../shared/components/content-view/ContentView.es';
import RetryButton from '../../../../shared/components/list/RetryButton.es';
import moment from '../../../../shared/util/moment.es';

const Body = ({
	assetTitle,
	assetType,
	assignees = [{name: Liferay.Language.get('unassigned')}],
	completed,
	creator,
	dateCompletion,
	dateCreated,
	setRetry,
	id,
	slaResults = [],
	taskNames = [],
}) => {
	const SLAs = {open: [], resolved: []};

	slaResults.forEach((result) => {
		SLAs[result.status === 'Stopped' ? 'resolved' : 'open'].push(result);
	});

	const statesProps = useMemo(
		() => ({
			errorProps: {
				actionButton: (
					<RetryButton
						onClick={() => setRetry((retry) => retry + 1)}
					/>
				),
				className: 'py-8',
				hideAnimation: true,
				message: Liferay.Language.get('unable-to-retrieve-data'),
				messageClassName: 'small',
			},
			loadingProps: {className: 'py-8'},
		}),
		[setRetry]
	);

	return (
		<ClayModal.Body>
			<ContentView {...statesProps}>
				<Body.SectionTitle>
					{Liferay.Language.get('due-date-by-sla')}
				</Body.SectionTitle>

				{slaResults.length === 0 && (
					<p>
						<span className="font-weight-medium text-muted">
							{Liferay.Language.get(
								'no-sla-records-for-this-item'
							)}
						</span>
					</p>
				)}

				{SLAs.open.length > 0 && (
					<Body.SectionSubTitle>
						{`${Liferay.Language.get('open').toUpperCase()} (${
							SLAs.open.length
						})`}
					</Body.SectionSubTitle>
				)}

				{SLAs.open.map((item) => (
					<Body.SLAResultItem key={item.id} {...item} />
				))}

				{SLAs.resolved.length > 0 && (
					<Body.SectionSubTitle>
						{`${Liferay.Language.get('resolved').toUpperCase()} (${
							SLAs.resolved.length
						})`}
					</Body.SectionSubTitle>
				)}

				{SLAs.resolved.map((item) => (
					<Body.SLAResultItem key={item.id} {...item} />
				))}

				<Body.SectionTitle className="mt-5">
					{Liferay.Language.get('process-details')}
				</Body.SectionTitle>

				<Body.SectionAttribute
					description={Liferay.Language.get('process-status')}
					detail={
						completed
							? Liferay.Language.get('completed')
							: Liferay.Language.get('pending')
					}
				/>

				<Body.SectionAttribute
					description={Liferay.Language.get('created-by')}
					detail={creator ? creator.name : ''}
				/>

				{dateCreated && (
					<Body.SectionAttribute
						description={Liferay.Language.get('creation-date')}
						detail={moment
							.utc(dateCreated)
							.format(Liferay.Language.get('mmm-dd-yyyy-lt'))}
					/>
				)}

				<Body.SectionAttribute
					description={Liferay.Language.get('asset-type')}
					detail={assetType}
				/>

				<Body.SectionAttribute
					description={Liferay.Language.get('asset-title')}
					detail={assetTitle}
				/>

				{!completed && (
					<Body.SectionAttribute
						description={Liferay.Language.get('current-step')}
						detail={taskNames.join(', ')}
					/>
				)}

				{completed && dateCompletion && (
					<Body.SectionAttribute
						description={Liferay.Language.get('end-date')}
						detail={moment
							.utc(dateCompletion)
							.format(Liferay.Language.get('mmm-dd-yyyy-lt'))}
					/>
				)}

				{!completed && (
					<Body.SectionAttribute
						description={Liferay.Language.get('current-assignee')}
						detail={assignees.map((user) => user.name).join(', ')}
					/>
				)}

				<ClayTooltipProvider>
					<a
						className="btn btn-secondary btn-sm font-weight-medium mb-1 mt-3"
						data-testid="submissionPageButton"
						data-tooltip-align="bottom"
						data-tooltip-delay="0"
						href={`/group/control_panel/manage/-/workflow_instance/view/${id}`}
						target="_blank"
						title={Liferay.Language.get('open-page-in-a-new-tab')}
					>
						{Liferay.Language.get('go-to-submission-page')}

						<span className="inline-item inline-item-after">
							<ClayIcon symbol="shortcut" />
						</span>
					</a>
				</ClayTooltipProvider>
			</ContentView>
		</ClayModal.Body>
	);
};

const SectionTitle = ({children, className = ''}) => {
	const classNames = `${className} font-weight-medium mb-4`;

	return <h4 className={classNames}>{children}</h4>;
};

const SectionSubTitle = ({children}) => {
	return (
		<h5
			className="font-weight-medium mb-4 mt-4 text-secondary"
			data-testid="instanceSectionSubTitle"
		>
			{children}
		</h5>
	);
};

const SectionAttribute = ({description, detail}) => {
	return (
		<ClayLayout.Row containerElement="p">
			<ClayLayout.Col
				className="font-weight-medium small text-secondary"
				containerElement="span"
				size="2"
			>
				{`${description}`}
			</ClayLayout.Col>

			<ClayLayout.Col
				className="small"
				containerElement="span"
				data-testid="instanceDetailSpan"
			>
				{detail}
			</ClayLayout.Col>
		</ClayLayout.Row>
	);
};

const SLAResultItem = ({dateOverdue, name, onTime, remainingTime, status}) => {
	const bgColor = onTime ? 'success' : 'danger';
	const iconName = onTime ? 'check-circle' : 'exclamation-circle';

	const getStatusText = (status) => {
		switch (status) {
			case 'Paused': {
				return `(${Liferay.Language.get('sla-paused')})`;
			}
			case 'Running': {
				const remainingTimePositive = onTime
					? remainingTime
					: remainingTime * -1;

				const remainingTimeUTC = moment.utc(remainingTimePositive);

				const durationText =
					remainingTimeUTC.format('D') -
					1 +
					remainingTimeUTC.format('[d] HH[h] mm[min]');

				const onTimeText = onTime
					? Liferay.Language.get('left')
					: Liferay.Language.get('overdue');

				return `${moment
					.utc(dateOverdue)
					.format(
						Liferay.Language.get('mmm-dd-yyyy-lt')
					)} (${durationText} ${onTimeText})`;
			}
			default: {
				if (status === 'Stopped' && onTime) {
					return `(${Liferay.Language.get('resolved-on-time')})`;
				}

				return `(${Liferay.Language.get('resolved-overdue')})`;
			}
		}
	};

	return (
		<div className="sla-result">
			<span className={`bg-${bgColor}-light inline-item sticker`}>
				<ClayIcon
					className={`text-${bgColor}`}
					data-testid="resultIcon"
					symbol={iconName}
				/>
			</span>

			<span className="font-weight-medium small text-secondary">
				{`${name}`}{' '}
			</span>

			<span className="small" data-testid="resultStatus">
				{getStatusText(status)}
			</span>
		</div>
	);
};

Body.SLAResultItem = SLAResultItem;
Body.SectionTitle = SectionTitle;
Body.SectionSubTitle = SectionSubTitle;
Body.SectionAttribute = SectionAttribute;

export {Body};
