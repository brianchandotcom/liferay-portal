/* eslint-disable @liferay/empty-line-between-elements */
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

import './index.scss';

import {useEffect, useState} from 'react';

import NavigationBar from '../../../../../common/components/navigation-bar';
import {getWebDavUrl} from '../../../../../common/utils/webdav';

type ApplicationPolicyDetailsType = {
	dataJSON: string;
	email: string;
	phone: string;
};

type PolicyDetailsType = {
	annualMileage: number;
	creditRating: string;
	features: string;
	firstName: string;
	gender: string;
	highestEducation: string;
	make: string;
	maritalStatus: string;
	model: string;
	occupation: string;
	ownership: string;
	primaryUsage: string;
	year: string;
};

enum NavBarLabel {
	Drivers = 'Drivers',
	Vehicles = 'Vehicles',
}

const PolicyDetail = ({
	dataJSON,
	email,
	phone,
}: ApplicationPolicyDetailsType) => {
	const navbarLabel = [NavBarLabel.Vehicles, NavBarLabel.Drivers];
	const [active, setActive] = useState(navbarLabel[0]);
	const [applicationData, setApplicationData] = useState<any>();

	function calculatedAge(dateOfBirth: string) {
		return Math.floor(
			Math.ceil(
				Math.abs(Date.parse(dateOfBirth) - Date.now()) /
					(1000 * 3600 * 24)
			) / 365.25
		);
	}

	useEffect(() => {
		try {
			const newDataJSON = JSON.parse(dataJSON);
			setApplicationData(newDataJSON);
		}
		catch (error) {
			console.warn(error);
		}
	}, [dataJSON]);

	return (
		<div className="policy-detail-container">
			<div className="bg-neutral-0 policy-detail-title pt-3 px-5 rounded-top">
				<h5 className="m-0">Policy Detail</h5>
			</div>

			<div className="d-flex flex-row">
				<NavigationBar
					active={active}
					navbarLabel={navbarLabel}
					setActive={setActive}
				/>
			</div>

			{active === NavBarLabel.Vehicles &&
				applicationData?.vehicleInfo?.form.map(
					(
						curentVehicle: PolicyDetailsType,
						indexVehicle: number
					) => (
						<div
							className="bg-neutral-0 pl-6 policy-detail-border pr-6 pt-6"
							key={indexVehicle}
						>
							<div className="d-flex flex-row flex-wrap justify-content-between">
								{indexVehicle !== 0 && (
									<div className="align-self-start col-12 layout-line mb-6 mt-1"></div>
								)}

								<div className="align-self-start w-25">
									<h5>
										{curentVehicle?.year}{' '}
										{curentVehicle?.make}{' '}
										{curentVehicle?.model
											? curentVehicle?.model
											: 'No data'}
									</h5>

									<img
										className="w-75"
										src={`${getWebDavUrl()}/${curentVehicle?.model
											.replace(/ /g, '')
											.toLocaleLowerCase()}.svg`}
									/>
								</div>

								<div className="align-self-start">
									<p className="mb-1 text-neutral-7">
										Primary Use
									</p>

									<div>
										{curentVehicle?.primaryUsage
											? curentVehicle?.primaryUsage
											: 'No data'}
									</div>
								</div>

								<div className="align-self-start">
									<p className="mb-1 text-neutral-7 w-100">
										Est. Annual Mileage
									</p>

									<div>
										{curentVehicle?.annualMileage
											? curentVehicle?.annualMileage
											: 'No data'}
									</div>
								</div>

								<div className="align-self-start">
									<p className="mb-1 text-neutral-7 w-100">
										Ownership Status
									</p>

									<div>
										{curentVehicle?.ownership
											? curentVehicle?.ownership
											: 'No data'}
									</div>
								</div>
							</div>

							<div>
								<div className="align-self-start mt-3">
									<p className="mb-1 text-neutral-7 w-100">
										Features
									</p>

									<div>
										{curentVehicle?.features
											? curentVehicle?.features
											: 'No data'}
									</div>
								</div>
							</div>
						</div>
					)
				)}

			{active === NavBarLabel.Drivers &&
				applicationData?.driverInfo?.form.map(
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
										{curentDriver?.firstName
											? curentDriver?.firstName
											: 'No data'}
										,{' '}
										{calculatedAge(
											applicationData?.contactInfo
												?.dateOfBirth
										)
											? calculatedAge(
													applicationData?.contactInfo
														?.dateOfBirth
											  )
											: 'No data'}
									</h5>
								</div>

								<div className="align-self-start">
									<p className="mb-1 text-neutral-7 w-100">
										DOB
									</p>

									<div className="mb-3">
										{applicationData?.contactInfo
											?.dateOfBirth
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

export default PolicyDetail;
