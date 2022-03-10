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
import {STATUS_TAG_TYPE_NAMES} from '../../../utils/constants';
import StatusTag from '../../StatusTag';

const ActivationStatusLayout = ({
	activationStatus,
	activationStatusDate,
	iconPath,
	project,
	subscriptionGroupActivationStatus,
}) => {
	return (
		<div className="mb-5">
			<h2>Activation Status</h2>

			<p className="font-weight-normal text-neutral-7 text-paragraph">
				{activationStatus.subtitle}
			</p>

			<div>
				<ClayCard className="border border-light cp-activation-status-container m-0 rounded shadow-none">
					<ClayCard.Body className="px-4 py-3">
						<div className="align-items-center d-flex position-relative">
							<img
								className={classNames(
									'ml-2 mr-4 cp-img-activation-status',
									{
										'in-progress':
											subscriptionGroupActivationStatus ===
											STATUS_TAG_TYPE_NAMES.inProgress,
										'not-active':
											subscriptionGroupActivationStatus ===
												STATUS_TAG_TYPE_NAMES.notActivated ||
											!subscriptionGroupActivationStatus,
									}
								)}
								draggable={false}
								height={30.55}
								src={iconPath}
								width={30.55}
							/>

							<ClayCard.Description
								className="h5 ml-3"
								displayType="title"
								tag="h5"
								title={project.name}
							>
								{project.name}

								<p className="font-weight-normal mb-2 text-neutral-7 text-paragraph">
									{activationStatusDate}
								</p>

								{activationStatus.buttonLink}
							</ClayCard.Description>

							<div className="d-flex justify-content-between ml-auto">
								<ClayCard.Description
									className="cp-label-activation-status position-absolute"
									displayType="text"
									tag="div"
									title={null}
									truncate={false}
								>
									<StatusTag
										currentStatus={activationStatus.id}
									/>
								</ClayCard.Description>
							</div>
						</div>
					</ClayCard.Body>
				</ClayCard>
			</div>
		</div>
	);
};

export default ActivationStatusLayout;
