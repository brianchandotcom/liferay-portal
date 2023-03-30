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

import ActivitiesComponent from '../../../../../common/components/activities-table-list';
import {currencyFormatter} from '../../../../../common/utils/currencyFormatter';
import {
	dateFormatter,
	dateFormatterLocalString,
} from '../../../../../common/utils/dateFormatter';
import {ClaimActivitiesDataType, ClaimComponentsType} from '../Types';

const ClaimDetailsActivities = ({claimData}: ClaimComponentsType) => {
	const claimDataJSON = claimData?.dataJSON
		? JSON.parse(claimData?.dataJSON)
		: {};

	const claimActivityData: ClaimActivitiesDataType[] = claimData?.dataJSON
		? [
				{
					activity: claimDataJSON.Detail.Activity.Desc4,
					body: true,
					by: claimDataJSON.Detail.Activity.By4,
					date: `${dateFormatterLocalString(
						claimDataJSON.Detail.Activity.Date4
					)}`,
					message: 'Insured contacted mechanic and completed repair.',
				},
				{
					activity: claimDataJSON.Detail.Activity.Desc3,
					by: claimDataJSON.Detail.Activity.By3,
					date: `${dateFormatterLocalString(
						claimDataJSON.Detail.Activity.Date3
					)}`,
					message:
						'After reviewing all accident details, estimation has been completed and submitted to insured. Insured is responsible for continuing with repairs.',
				},
				{
					activity: claimDataJSON.Detail.Activity.Desc2,
					by: claimDataJSON.Detail.Activity.By2,
					date: `${dateFormatterLocalString(
						claimDataJSON.Detail.Activity.Date2
					)}`,
					message: `I went to insured's residence this morning to assess damage. I also requested police report of accident. Estimation to follow.`,
				},
				{
					activity: claimDataJSON.Detail.Activity.Desc1,
					by: claimDataJSON.Detail.Activity.By1,
					date: `${dateFormatterLocalString(
						claimDataJSON.Detail.Activity.Date1
					)}`,
					message:
						'The insured called me this morning at 10am after getting into an accident. I submitted a claim on behalf of the insured, and the claim is currently waiting for investigation process to begin.',
				},
		  ]
		: [];

	const BodyElement = () => (
		<div className="ml-3">
			<div className="font-weight-bold"> Detail below:</div>

			<div className="claim-activities-body-element">
				<div className="d-flex justify-content-between">
					<div>
						<div className="mt-3 text-neutral-6">Mechanic</div>

						<div>
							{claimDataJSON.Detail.VehicleRepair.MechanicName}
						</div>

						<div>
							{claimDataJSON.Detail.VehicleRepair.MechanicPhone}
						</div>
					</div>

					<div>
						<div className="mt-3 text-neutral-6">Order #</div>

						<div className="text-uppercase">
							{
								claimDataJSON.Detail.VehicleRepair
									.MechanicOrderNum
							}
						</div>
					</div>

					<div className="mr-10">
						<div className="mt-3 text-neutral-6"> Completed on</div>

						<div>
							{dateFormatter(
								claimDataJSON.Detail.VehicleRepair
									.MechanicCompleteDate
							)}
						</div>
					</div>
				</div>

				<div>
					<div className="mt-5 text-neutral-6">Cost</div>

					<div>
						{currencyFormatter(
							claimDataJSON.Detail.VehicleRepair.MechanicCost
						)}
					</div>
				</div>
			</div>
		</div>
	);

	return (
		<ActivitiesComponent
			BodyElement={BodyElement}
			activitiesData={claimActivityData}
		/>
	);
};

export default ClaimDetailsActivities;
