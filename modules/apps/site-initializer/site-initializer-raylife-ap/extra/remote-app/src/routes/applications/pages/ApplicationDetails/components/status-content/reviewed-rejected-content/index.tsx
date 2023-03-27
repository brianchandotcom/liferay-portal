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
import ClayButton from '@clayui/button';
import {useEffect, useState} from 'react';

import {updateRaylifeApplication} from '../../../../../../../common/services';
import {getUserNotification} from '../../../../../../../common/services/UserNotification';
import {
	CONSTANTS,
	ConstantListType,
} from '../../../../../../../common/utils/constants';
import {redirectTo} from '../../../../../../../common/utils/liferay';

import './index.scss';

type UnderwriterApplicationType = {
	externalReferenceCode?: string;
	id?: number;
};

const UnderwritingContent = ({
	externalReferenceCode,
	id,
}: UnderwriterApplicationType) => {
	const [underwriterComment, setUnderwriterComment] = useState<string>();

	const handleUpdateApplicationStatus = () => {
		updateRaylifeApplication(
			externalReferenceCode as string,
			CONSTANTS.APPLICATION_STATUS['bound'].NAME
		);

		redirectTo('Applications');
	};

	useEffect(() => {
		getUserNotification().then((response) => {
			const notifications = response?.data?.items;
			const message = notifications.filter(
				(notification: ConstantListType) => {
					if (notification.message.includes(`${id}`)) {
						return notification.message;
					}
				}
			);

			const comment = message[0]?.message?.split(': ')[1];

			setUnderwriterComment(comment);
		});
	}, [id]);

	return (
		<div className="application-details-underwriter-content d-flex flex-column">
			<div className="action-detail-title pt-3 px-5">
				{externalReferenceCode ? (
					<h5 className="m-0">Policy Ready to Bind</h5>
				) : (
					<h5 className="m-0">Follow up With Applicant</h5>
				)}
			</div>

			<hr />

			<div className="action-detail-content mb-10 px-5">
				{externalReferenceCode ? (
					<p>
						This application has been <b>Approved</b> by
						Underwriting. You may proceed with binding this policy.
					</p>
				) : (
					<>
						<p>
							This application has been <b>Rejected</b> by
							Underwriting. Please follow up with applicant
							accordingly.
						</p>

						{underwriterComment && (
							<>
								<div className="application-reject mb-2 mt-6 text-neutral-7">
									Underwriter Comments
								</div>

								<div>{underwriterComment}</div>
							</>
						)}
					</>
				)}
			</div>

			<div className="d-flex justify-content-between mt-10 p-3">
				<ClayButton displayType="link">+ Add Notes</ClayButton>

				<div className="d-flex justify-content-end px-2">
					<ClayButton
						className="m-2 text-uppercase"
						displayType="primary"
					>
						Contact Applicant
					</ClayButton>

					{externalReferenceCode && (
						<ClayButton
							className="m-2 text-uppercase"
							displayType="primary"
							onClick={() => handleUpdateApplicationStatus()}
						>
							Collect Payment
						</ClayButton>
					)}
				</div>
			</div>
		</div>
	);
};

export default UnderwritingContent;
