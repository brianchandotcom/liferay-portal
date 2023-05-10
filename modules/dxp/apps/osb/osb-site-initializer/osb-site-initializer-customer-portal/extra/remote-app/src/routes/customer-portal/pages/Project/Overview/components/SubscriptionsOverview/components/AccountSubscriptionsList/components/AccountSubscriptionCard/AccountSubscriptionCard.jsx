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

import ClayCard from '@clayui/card';
import classNames from 'classnames';
import {memo} from 'react';
import i18n from '../../../../../../../../../../../common/I18n';
import {
	Skeleton,
	StatusTag,
} from '../../../../../../../../../../../common/components';

import {
	FORMAT_DATE_TYPES,
	SLA_STATUS_TYPES,
} from '../../../../../../../../../../../common/utils/constants';
import getDateCustomFormat from '../../../../../../../../../../../common/utils/getDateCustomFormat';

const AccountSubscriptionCard = ({
	loading,
	logoPath: IconSVG,
	onClick,
	...accountSubscription
}) => {
	const getDatesDisplay = () =>
		`${getDateCustomFormat(
			accountSubscription.startDate,
			FORMAT_DATE_TYPES.day2DMonth2DYearN
		)} - ${getDateCustomFormat(
			accountSubscription.endDate,
			FORMAT_DATE_TYPES.day2DMonth2DYearN
		)}`;

	return (
		<ClayCard
			className={classNames(
				'border border-light mb-0 mr-4 mt-4 shadow-none',
				{
					'card-interactive': !loading,
				}
			)}
			onClick={onClick}
		>
			<ClayCard.Body className="cp-account-subscription-card d-flex flex-column justify-content-between pb-3">
				{loading ? (
					<Skeleton className="mb-3 py-1" height={45} width={48} />
				) : (
					<div className="mb-3 py-1 text-center">
						<IconSVG height={45} width={45} />
					</div>
				)}

				{loading ? (
					<Skeleton
						className="cp-account-subscription-card-name mb-1"
						height={20}
						width={90}
					/>
				) : (
					<h5 className="align-items-center cp-account-subscription-card-name d-flex justify-content-center mb-1 text-center">
						{accountSubscription.name}
					</h5>
				)}

				<div>
					{loading ? (
						<Skeleton className="mb-1" height={13} width={80} />
					) : (
						<p className="mb-1 text-center text-neutral-7 text-paragraph-sm">
							{`${i18n.translate('instance-size')}: `}

							{accountSubscription.instanceSize}
						</p>
					)}

					{loading ? (
						<Skeleton className="mb-3" height={24} width={160} />
					) : (
						accountSubscription.startDate &&
						accountSubscription.endDate && (
							<p className="mb-3 text-center">
								{getDatesDisplay()}
							</p>
						)
					)}

					{loading ? (
						<Skeleton height={20} width={38} />
					) : (
						<div className="d-flex justify-content-center">
							<StatusTag
								currentStatus={
									SLA_STATUS_TYPES[
										accountSubscription?.subscriptionStatus?.toLowerCase()
									]
								}
							/>
						</div>
					)}
				</div>
			</ClayCard.Body>
		</ClayCard>
	);
};

export default memo(AccountSubscriptionCard);
