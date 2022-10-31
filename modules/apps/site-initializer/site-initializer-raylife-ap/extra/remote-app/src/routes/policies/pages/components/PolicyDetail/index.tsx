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

import {useState} from 'react';

import NavigationBar from '../../../../../common/components/navigation-bar';
import {getWebDavUrl} from '../../../../../common/utils/webdav';

const PolicyDetail = () => {
	enum navBarLabels {
		Vehicles = 'Vehicles',
		Drives = 'Drives',
	}
	const amountOfPolicyDetail = [0];
	const navbarLabel = [navBarLabels.Vehicles, navBarLabels.Drives];
	const [active, setActive] = useState(navbarLabel[0]);

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

			{amountOfPolicyDetail.map(
				(curentPolicyDetail, indexPolicyDetail) => (
					<div className="bg-neutral-0 p-6" key={indexPolicyDetail}>
						{active === navBarLabels.Vehicles && (
							<>
								<div className="d-flex flex-row flex-wrap justify-content-between mb-3">
									{indexPolicyDetail !== 0 && (
										<div className="align-self-start col-12 layout-line mb-6 mt-1"></div>
									)}

									<div className="align-self-start w-50">
										<h5>2016 Ford Edge</h5>

										<img
											className="policy-detail-img w-75"
											src={`${getWebDavUrl()}/ford-edge_icon.svg`}
										/>
									</div>

									<div className="align-self-start">
										<p className="text-neutral-7">
											Primary Use
										</p>

										<div>Commuting</div>
									</div>

									<div className="align-self-start">
										<p className="text-neutral-7 w-100">
											Est. Annual Mileage
										</p>

										<div>10,328</div>
									</div>

									<div className="align-self-start">
										<p className="text-neutral-7 w-100">
											Ownership Status
										</p>

										<div>Own</div>
									</div>
								</div>
								<div>
									<div className="align-self-start">
										<p className="text-neutral-7 w-100">
											Features
										</p>

										<div>
											Lane-Departure Warning and
											Lane-Keeping Assist Forward-C
											ollision Warning Blind-Spot
											Monitoring
										</div>
									</div>
								</div>
							</>
						)}

						{active === navBarLabels.Drives && (
							<div className="d-flex flex-row flex-wrap justify-content-between">
								{indexPolicyDetail !== 0 && (
									<div className="align-self-start col-12 layout-line mb-6 mt-1"></div>
								)}

								<div className="align-self-start">
									<h5>Lee Harris, 51 </h5>
								</div>

								<div className="align-self-start">
									<p className="mb-1 text-neutral-7 w-100">
										DOB
									</p>

									<div className="mb-3">09/19/1971</div>

									<p className="mb-1 text-neutral-7 w-100">
										Education
									</p>

									<div className="mb-3">High School</div>

									<p className="mb-1 text-neutral-7 w-100">
										Email
									</p>

									<a
										className="mb-3 text-break"
										href="mailto:lee.insured@mailinator.com"
									>
										lee.insured@mailinator.com
									</a>
								</div>

								<div className="align-self-start">
									<p className="mb-1 text-neutral-7 w-100">
										Gender
									</p>

									<div className="mb-3">Male</div>

									<p className="mb-1 text-neutral-7 w-100">
										Occupation
									</p>

									<div className="mb-3">Home Health Aid</div>

									<p className="mb-1 text-neutral-7 w-100">
										Phone
									</p>

									<div className="mb-3">208.802.6545</div>
								</div>

								<div className="align-self-start">
									<p className="mb-1 text-neutral-7 w-100">
										Marital Status
									</p>

									<div className="mb-3">Married</div>

									<p className="mb-1 text-neutral-7 w-100">
										Credit rating
									</p>

									<div className="mb-3">Good</div>
								</div>
							</div>
						)}
					</div>
				)
			)}
		</div>
	);
};

export default PolicyDetail;
