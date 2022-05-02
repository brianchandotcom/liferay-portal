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
import getCurrentEndDate from '../../../../../common/utils/getCurrentEndDate';
const GenerateCardLayout = ({infoSelectedKey}) => {
	const currentDate = `${getCurrentEndDate(
		infoSelectedKey?.selectedSubscription?.startDate
	)} - ${getCurrentEndDate(infoSelectedKey?.selectedSubscription?.endDate)}`;

	return (
		<ClayCard className="mr-5 position-absolute rounded-xl shadow-none">
			<ClayCard.Body className="bg-brand-primary-lighten-6 cp-info-new-key-card p-4 rounded-xl">
				<ClayCard.Description displayType="title" tag="div">
					<div className="p-2">
						<p className="m-0">Product</p>

						<p className="font-weight-normal">
							{infoSelectedKey?.productType}
						</p>

						<p className="m-0">Version</p>

						<p className="font-weight-normal">
							{infoSelectedKey?.productVersion}
						</p>

						<p className="m-0">License Type</p>

						<p className="font-weight-normal">
							{infoSelectedKey?.licenseEntryType}{' '}
						</p>

						<p className="m-0">Subscription</p>

						<p className="font-weight-normal">{currentDate}</p>

						<p className="m-0">Key Activations Available</p>

						<p className="font-weight-normal">
							{infoSelectedKey?.selectedSubscription?.quantity -
								infoSelectedKey?.selectedSubscription
									?.provisionedCount}

							{' of '}

							{infoSelectedKey?.selectedSubscription?.quantity}
						</p>

						<p className="m-0">Instance Size</p>

						<p className="font-weight-normal m-0">
							{infoSelectedKey?.selectedSubscription
								?.instanceSize === 0
								? infoSelectedKey?.selectedSubscription
										?.instanceSize + 1
								: infoSelectedKey?.selectedSubscription
										?.instanceSize}
						</p>
					</div>
				</ClayCard.Description>
			</ClayCard.Body>
		</ClayCard>
	);
};
export default GenerateCardLayout;
