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
import getDateCustomFormat from '../../utils/dateCustomFormat';
import ModalCardSubscription from '../ModalCardSubscription';

const dateFormat = {
	day: '2-digit',
	month: '2-digit',
	year: 'numeric',
};

const CardSubscription = ({
	cardSubscriptionData,
	selectedSubscriptionGroup,
}) => {
	const [visible, setVisible] = useState(false);
	const {observer, onClose} = useModal({
		onClose: () => setVisible(false),
	});

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
				className="card-subscription d-flex mr-4"
				onClick={() => setVisible(true)}
			>
				<div className="align-self-center d-flex flex-column mx-auto pb-4 pt-3 px-4">
					<div
						className="d-flex head-text justify-content-center mb-1 row"
						type="text"
					>
						{cardSubscriptionData?.name || ' - '}
					</div>

					<div
						className="d-flex head-text-2 justify-content-center row"
						type="text"
					>
						{`Instance size: ${
							cardSubscriptionData?.instanceSize || ' - '
						}`}
					</div>

					<div
						className="card-date d-flex justify-content-center mb-4 row"
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

					<div className="badge-card-subscription d-flex justify-content-center">
						Active
					</div>
				</div>
			</div>
		</>
	);
};

export default CardSubscription;
