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

import calculatedAge from '../../../../../../common/utils/calculatedAge';

import '../index.scss';
import {PolicyDetailsType, dataJSONType} from '../types';

type ApplicationPolicyDetailsType = {
	dataJSON: dataJSONType;
	email: string;
	phone: string;
};

const DriverInfo = ({dataJSON, email, phone}: ApplicationPolicyDetailsType) => {
	const applicationData = dataJSON;

	return (
		<div>
			{applicationData?.driverInfo?.form.map(
				(curentDriver: PolicyDetailsType, indexDriver: number) => (
					<div
						className="bg-neutral-0 pl-6 policy-detail-border pr-6 pt-6"
						key={indexDriver}
					>
						<div className="d-flex flex-row flex-wrap justify-content-between">
							{indexDriver !== 0 && (
								<div className="align-self-start col-12 layout-line mb-6 mt-1"></div>
							)}

							<div className="align-self-start">
								<h5>
									{`${
										curentDriver?.firstName ?? 'No data'
									}, ${
										applicationData?.contactInfo
											?.dateOfBirth
											? calculatedAge(
													applicationData?.contactInfo
														?.dateOfBirth
											  )
											: 'No data'
									}`}
								</h5>
							</div>

							<div className="align-self-start">
								<p className="mb-1 text-neutral-7 w-100">DOB</p>

								<div className="mb-3">
									{applicationData?.contactInfo?.dateOfBirth
										? applicationData?.contactInfo
												?.dateOfBirth
										: 'No data'}
								</div>

								<p className="mb-1 text-neutral-7 w-100">
									Education
								</p>

								<div className="mb-3">
									{curentDriver?.highestEducation
										? curentDriver?.highestEducation
										: 'No data'}
								</div>

								<p className="mb-1 text-neutral-7 w-100">
									Email
								</p>

								<a className="mb-3 text-break" href={email}>
									{email ? email : 'No data'}
								</a>
							</div>

							<div className="align-self-start">
								<p className="mb-1 text-neutral-7 w-100">
									Gender
								</p>

								<div className="mb-3">
									{curentDriver?.gender
										? curentDriver?.gender
										: 'No data'}
								</div>

								<p className="mb-1 text-neutral-7 w-100">
									Occupation
								</p>

								<div className="mb-3">
									{curentDriver?.occupation
										? curentDriver?.occupation
										: 'No data'}
								</div>

								<p className="mb-1 text-neutral-7 w-100">
									Phone
								</p>

								<div className="mb-3">
									{phone ? phone : 'No data'}
								</div>
							</div>

							<div className="align-self-start">
								<p className="mb-1 text-neutral-7 w-100">
									Marital Status
								</p>

								<div className="mb-3">
									{curentDriver?.maritalStatus
										? curentDriver?.maritalStatus
										: 'No data'}
								</div>

								<p className="mb-1 text-neutral-7 w-100">
									Credit rating
								</p>

								<div className="mb-3">
									{curentDriver?.creditRating
										? curentDriver?.creditRating
										: 'No data'}
								</div>
							</div>
						</div>
					</div>
				)
			)}
		</div>
	);
};

export default DriverInfo;
