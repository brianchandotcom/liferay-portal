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

const CardSubscription = ({
	cardSubscriptionData,
	selectedSubscriptionGroup,
}) => {
	const [{assetsPath}] = useCustomerPortal();
	const [visible, setVisible] = useState(false);
	const {observer, onClose} = useModal({
		onClose: () => setVisible(false),
	});

	// eslint-disable-next-line no-console
	const subscriptionStatus = cardSubscriptionData.subscriptionStatus.toLowerCase();

	const parseAccountSubscriptionTerms = (subscriptionName) =>
		subscriptionName.toLowerCase().replace(' ', '-');

	const accountSubscriptionERC = `${
		cardSubscriptionData.accountSubscriptionGroupERC
	}_${parseAccountSubscriptionTerms(cardSubscriptionData.name)}`;

	const subscriptionImage = {
		'Analytics': 'analytics_icon.svg',
		'Commerce': 'commerce.icon.svg',
		'DXP': 'dxp_icon.svg',
		'DXP Cloud': 'dxp_icon.svg',
		'Enterprise Search': 'enterprise_icon.svg',
		'Portal': 'portal_icon.svg',
	};

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
				className="align-items-center card-subscription d-flex flex-column justify-content-center mr-4"
				onClick={() => setVisible(true)}
			>
				<img
					className="mb-4 mt-5 w-25"
					src={`${assetsPath}/assets/navigation-menu/${
						subscriptionImage[selectedSubscriptionGroup] ||
						'portal_icon.svg'
					}`}
				/>

				<div className="align-self-center d-flex flex-column mx-auto pb-4 pt-3 px-4">
					<div
						className="d-flex head-text justify-content-center mb-4 mw-25 row text-center"
						type="text"
					>
						{cardSubscriptionData?.name || ' - '}
					</div>

					<div
						className="d-flex head-text-2 justify-content-center mb-1 row"
						type="text"
					>
						{`Instance size: ${
							cardSubscriptionData?.instanceSize || ' - '
						}`}
					</div>

					<div
						className="card-date d-flex justify-content-center mb-3 row"
						type="text"
					>
						{`${getDateCustomFormat(
							cardSubscriptionData?.startDate,
							dateFormat
						)} - ${getDateCustomFormat(
							cardSubscriptionData?.endDate,
							dateFormat
						)}`}
					</div>

					<div className="d-flex justify-content-center mb-3">
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
