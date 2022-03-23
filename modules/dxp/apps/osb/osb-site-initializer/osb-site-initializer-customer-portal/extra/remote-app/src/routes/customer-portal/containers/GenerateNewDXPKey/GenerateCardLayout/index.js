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
const GenerateCardLayout = ({infoSelectedKey}) => {
	return (
		<ClayCard className="mr-5 position-absolute rounded-xl shadow-none">
			<ClayCard.Body className="bg-brand-primary-lighten-6 cp-info-new-key-card p-4 rounded-xl">
				<ClayCard.Description displayType="title" tag="div">
					<div className="p-2">
						<div className="d-flex">
							<p className="m-0">Product</p>

							<p className="font-weight-normal">
								{infoSelectedKey?.productType}
							</p>
						</div>

						<p className="m-0">Version</p>

						<p className="font-weight-normal">
							{infoSelectedKey?.selectedVersion}
						</p>

						<p className="m-0">License Type</p>

						<p className="font-weight-normal">
							{infoSelectedKey?.selectedLicenseType}{' '}
						</p>

						<p className="m-0">Subscription</p>

						<p className="font-weight-normal">
							{
								infoSelectedKey.getSelectedSubscription
									?.currentDate
							}
						</p>

						<p className="m-0">Key Activations Available</p>

						<p className="font-weight-normal">
							{
								infoSelectedKey.getSelectedSubscription
									?.keyActivationAvailable
							}
						</p>

						<p className="m-0">Instance Size</p>

						<p className="font-weight-normal m-0">
							{
								infoSelectedKey.getSelectedSubscription
									?.instanceSize
							}
						</p>
					</div>
				</ClayCard.Description>
			</ClayCard.Body>
		</ClayCard>
	);
};
export default GenerateCardLayout;
