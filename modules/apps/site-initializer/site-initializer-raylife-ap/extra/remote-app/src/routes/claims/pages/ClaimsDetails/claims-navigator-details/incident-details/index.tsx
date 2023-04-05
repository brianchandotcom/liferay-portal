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

import {useEffect, useState} from 'react';

import {dateFormatter} from '../../../../../../common/utils/dateFormatter';
import {ClaimComponentsType} from '../../Types';
import GoogleMaps from './googleMaps';

import './index.scss';

type ClaimTypeJSON = {[keys: string]: string};

const IncidentDetail = (claimData: ClaimComponentsType) => {
	const [claimIncidentDetails, setClaimDetails] = useState<ClaimTypeJSON>();

	const [claimVehicleRepair, setClaimVehicleRepair] = useState<
		ClaimTypeJSON
	>();

	const incidentDate = claimData?.claimData.incidentDate;

	const address = claimIncidentDetails?.LocationCity;

	useEffect(() => {
		try {
			const dataClaimsJSON = JSON.parse(
				JSON.stringify(claimData.claimData)
			);
			const detailsJSON = JSON.parse(dataClaimsJSON.dataJSON);

			setClaimDetails(detailsJSON.Detail.IncidentDetail);
			setClaimVehicleRepair(detailsJSON.Detail.VehicleRepair);
		} catch (error) {
			console.warn(error);
		}
	}, [claimData]);

	return (
		<div className="incident-info-nav-bar-claims-details px-5">
			{claimIncidentDetails && (
				<div className="align-items-start bg-neutral-0 claims-detail-incident-info-content d-flex justify-content-center py-6 rounded row">
					<div className="col-lg-4 col-md-4 col-sm-12 pr-5">
						<div className="google-maps-container">
							<GoogleMaps address={address as string} />
						</div>
					</div>

					<div className="claims-detail-incident-info col-lg-4 col-md-4 col-sm-12 ml-lg-0 ml-md-0 ml-sm-2 py-2">
						<div className="d-flex flex-column row">
							<div className="font-weight-bold mt-lg-0 mt-md-0 mt-sm-4 pb-2 text-neutral-7">
								Date
							</div>

							<div className="pb-2">
								{dateFormatter(incidentDate)}
							</div>
						</div>

						<div className="claim-details-incident-body d-flex flex-column mt-lg-9 mt-md-9 mt-sm-4 row">
							<div className="font-weight-bold pb-2 text-neutral-7">
								Location Description
							</div>

							<div className="pb-2">
								{claimVehicleRepair?.Description}
							</div>
						</div>
					</div>

					<div className="col-lg-4 col-md-4 col-sm-12 flex-column ml-2 p-0 py-2 row">
						<div className="font-weight-bold pb-2 text-neutral-7">
							Location
						</div>

						<div className="pb-2">
							{claimIncidentDetails?.Location}
						</div>

						<div className="pb-2">
							{claimIncidentDetails?.LocationCity}

							{', '}

							{claimIncidentDetails?.LocationState}

							{'  '}

							{claimIncidentDetails?.LocationZIP}
						</div>
					</div>
				</div>
			)}

			<hr />

			<div className="align-items-start d-flex py-4 row">
				<div className="col-lg-4 col-md-4 col-sm-12">
					<div>
						<div className="pb-2 text-neutral-7">
							Type Of Incident
						</div>

						<div className="pb-2">{claimIncidentDetails?.Type}</div>
					</div>

					<div className="py-4">
						<div className="pb-2 text-neutral-7">
							Incident Detail
						</div>

						<div>{claimIncidentDetails?.Detail}</div>
					</div>
				</div>

				<div className="col-lg-4 col-md-4 col-sm-12">
					<div className="pb-2 text-neutral-7">
						Other Driver&apos;s Vehicle
					</div>

					<div className="pb-2">
						{claimIncidentDetails?.OtherVehicleMake}

						{'  '}

						{claimIncidentDetails?.OtherVehicleModel}
					</div>

					<div className="pb-2">
						{claimIncidentDetails?.OtherVehicleVIN}
					</div>
				</div>

				<div className="claim-details-incident-body col-lg-4 col-md-4 col-sm-12 mt-lg-0 mt-md-0 mt-sm-4">
					<div className="pb-2 text-neutral-7">
						Who was in the other Vehicle?
					</div>

					<div className="pb-2">
						{claimIncidentDetails?.OtherVehicleOccupant}
					</div>

					<div className="pb-2">
						{claimIncidentDetails?.OtherVehicleOccupant2
							? claimIncidentDetails?.OtherVehicleOccupant2
							: ''}
					</div>

					<div className="pb-2">
						{claimIncidentDetails?.OtherVehicleOccupant3
							? claimIncidentDetails?.OtherVehicleOccupant3
							: ''}
					</div>
				</div>
			</div>
		</div>
	);
};

export default IncidentDetail;
