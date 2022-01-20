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

import {useModal} from '@clayui/modal';
import {useState} from 'react';
import {useCustomerPortal} from '../../context';
import {status as statusCard} from '../../utils/constants';
import getDateCustomFormat from '../../utils/dateCustomFormat';
import ModalCardSubscription from '../ModalCardSubscription';
import StatusTag from '../StatusTag';

const dateFormat = {
	day: '2-digit',
	month: '2-digit',
	year: 'numeric',
};

const SUBSCRIPTION_IMAGE_FILE = {
	'Analytics': 'analytics_icon.svg',
	'Commerce': 'commerce.icon.svg',
	'DXP': 'dxp_icon.svg',
	'DXP Cloud': 'dxp_icon.svg',
	'Enterprise Search': 'enterprise_icon.svg',
	'Portal': 'portal_icon.svg',
};

const CardSubscription = ({
	cardSubscriptionData,
	selectedSubscriptionGroup,
}) => {
	const [{assetsPath}] = useCustomerPortal();
	const [visible, setVisible] = useState(false);
	const {observer, onClose} = useModal({
		onClose: () => setVisible(false),
	});

	const subscriptionStatus = cardSubscriptionData.subscriptionStatus.toLowerCase();

	const parseAccountSubscriptionTerms = (subscriptionName) =>
		subscriptionName.toLowerCase().replace(' ', '-');

	const accountSubscriptionERC = `${
		cardSubscriptionData.accountSubscriptionGroupERC
	}_${parseAccountSubscriptionTerms(cardSubscriptionData.name)}`;

	return (
		<>
			{visible && (
				<ModalCardSubscription
					accountSubscriptionERC={accountSubscriptionERC}
					observer={observer}
					onClose={onClose}
					subscriptionGroup={selectedSubscriptionGroup}
					subscriptionName={cardSubscriptionData.name}
				/>
			)}
			<div
				className="border border-light card-subscription p-4 rounded"
				onClick={() => setVisible(true)}
			>
				<div className="text-center">
					<img
						className="w-25"
						src={`${assetsPath}/assets/navigation-menu/${
							SUBSCRIPTION_IMAGE_FILE[
								selectedSubscriptionGroup
							] || 'portal_icon.svg'
						}`}
					/>
				</div>

				<div className="mt-4">
					<h5 className="mb-1 text-center title">
						{cardSubscriptionData?.name || ' - '}
					</h5>

					<p className="mb-1 text-center text-neutral-7 text-paragraph-sm">
						{`Instance size: ${
							cardSubscriptionData?.instanceSize || ' - '
						}`}
					</p>

					<p className="mb-3 text-center">
						{`${getDateCustomFormat(
							cardSubscriptionData?.startDate,
							dateFormat
						)} - ${getDateCustomFormat(
							cardSubscriptionData?.endDate,
							dateFormat
						)}`}
					</p>

					<div className="d-flex justify-content-center">
						<StatusTag
							currentStatus={statusCard[subscriptionStatus]}
						/>
					</div>
				</div>
			</div>
		</>
	);
};

export default CardSubscription;
