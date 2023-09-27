/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import calculatedAge from '../../../../../../common/utils/calculatedAge';

import '../index.scss';
import {PolicyDetailsType, dataJSONType} from '../types';

export type ApplicationPolicyDetailsType = {
	dataJSON: dataJSONType;
	email: string;
	phone: string;
};

interface generateDriverInfoType {
	label: string;
	value?: string;
}

const GenerateDriverInfo = ({label, value}: generateDriverInfoType) => {
	return (
		<div className="item-driver-info text-break">
			<p className="mb-1 text-neutral-7 w-100">{label}</p>

			{label === 'Email' ? (
				<a className="mb-3" href={value}>
					{value ?? 'No data'}
				</a>
			) : (
				<div className="mb-3">{value ?? 'No data'}</div>
			)}
		</div>
	);
};

const driverInfoValues = (
	currentDriver: PolicyDetailsType,
	email: string,
	phone: string
) => {
	return [
		{
			label: 'DOB',
			value: currentDriver?.dateOfBirth,
		},
		{
			label: 'Education',
			value: currentDriver?.highestEducation,
		},
		{
			label: 'Email',
			value: email,
		},
		{
			label: 'Gender',
			value: currentDriver?.gender,
		},
		{
			label: 'Occupation',
			value: currentDriver?.occupation,
		},
		{
			label: 'Phone',
			value: phone,
		},
		{
			label: 'Marital Status',
			value: currentDriver?.maritalStatus,
		},
		{
			label: 'Credit rating',
			value: currentDriver?.creditRating,
		},
	];
};

const DriverInfo = ({
	dataJSON: applicationData,
	email,
	phone,
}: ApplicationPolicyDetailsType) => {
	return (
		<div>
			{applicationData?.driverInfo?.form.map(
				(currentDriver: PolicyDetailsType, index) => (
					<div
						className="bg-neutral-0 pl-6 policy-detail-border pr-6 pt-6"
						key={index}
					>
						<div className="d-flex flex-row flex-wrap justify-content-between">
							{index !== 0 && (
								<div className="align-self-start col-12 layout-line mb-6 mt-1"></div>
							)}

							<div className="align-self-start pr-9">
								<h5>
									{`${
										currentDriver?.firstName ?? 'No data'
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

							<div className="list-driver-info">
								{driverInfoValues(
									currentDriver,
									email,
									phone
								).map((info, index) => (
									<GenerateDriverInfo
										key={index}
										label={info.label}
										value={info.value}
									/>
								))}
							</div>
						</div>
					</div>
				)
			)}
		</div>
	);
};

export default DriverInfo;
