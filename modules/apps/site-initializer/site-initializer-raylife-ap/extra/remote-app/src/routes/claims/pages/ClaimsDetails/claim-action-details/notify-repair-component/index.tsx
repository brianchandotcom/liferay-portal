/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ActionButtons from '../../../../../../common/components/action-detail/action-content/buttons';
import {CONSTANTS} from '../../../../../../common/utils/constants';

const NotifyRepairComponent = () => (
	<div className="d-flex flex-column">
		<div className="action-detail-title pt-3 px-5">
			<h5 className="m-0">Notify Repair Options</h5>
		</div>

		<hr />

		<div className="action-detail-content px-5">
			<p className="mb-5">
				The underwriter has approved the claim, and the customer&apos;s
				vehicle is ready for repair.
			</p>

			<p>
				In-network repair facilities available near customer&apos;s zip
				code of 90005:
			</p>

			<div className="ml-5">
				{CONSTANTS.REPAIR_ACTIONS_OPTIONS.map((notifyOption, index) => (
					<div
						className="d-flex justify-content-between mb-5 mt-5 row"
						key={index}
					>
						<div className="col col-lg-6 col-md-6 col-sm-12 mb-1">
							<div className="d-inline">{`${index + 1}) `}</div>

							<div className="cursor-pointer d-inline text-primary">
								{notifyOption.name}
							</div>

							<div className="mb-1 ml-4">
								{notifyOption.phone}
							</div>
						</div>

						<div className="col col-lg-6 col-md-6 col-sm-12">
							<div className="mb-1 ml-4">
								{notifyOption.street}
							</div>

							<div className="mb-1 ml-4">{notifyOption.city}</div>
						</div>
					</div>
				))}
			</div>

			<b>Please notify the customer of available repair options.</b>
		</div>

		<ActionButtons buttonText="Contact Customer" linkText="+ Add Notes" />
	</div>
);

export default NotifyRepairComponent;
