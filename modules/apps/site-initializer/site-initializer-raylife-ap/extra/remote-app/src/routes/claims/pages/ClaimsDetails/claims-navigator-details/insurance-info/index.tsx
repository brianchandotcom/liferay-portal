/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import addImageFallback from '../../../../../../common/utils/addImageFallback';
import {getWebDavUrl} from '../../../../../../common/utils/webdav';
import {ClaimComponentsType} from '../../Types';

import './index.scss';

type ClaimTypeJSON = {[keys: string]: string};

function addSecurityMask(input: string): string {
	const lastDigits = input.substring(input.length - 4);
	const hiddenDigits = '*'.repeat(input.length - 4);

	return hiddenDigits + lastDigits;
}

const InsuranceInfo = (claimData: ClaimComponentsType) => {
	const [claimDetails, setClaimDetails] = useState<ClaimTypeJSON>();

	useEffect(() => {
		try {
			const dataClaimsJSON = JSON.parse(
				JSON.stringify(claimData.claimData)
			);
			const detailsJSON = JSON.parse(dataClaimsJSON.dataJSON);

			setClaimDetails(detailsJSON.Detail.InsuranceInfo);
		}
		catch (error) {
			console.warn(error);
		}
	}, [claimData]);

	const getMiddleWords = (str: string) => {
		return str.replace(/^\w+\s\w+\s/, '').replace(/ /g, '');
	};

	return (
		<div className="insurance-info-nav-bar-claims-details">
			{claimDetails && (
				<div className="align-items-center bg-neutral-0 claims-detail-insurance-info-content d-flex justify-content-center mx-1 px-1 py-7 rounded row">
					<div className="claims-detail-insurance-info col-6">
						<div className="list-title pb-2">
							Involved Insurance Holder
						</div>

						<div className="font-weight-bold list-text list-text-info pb-2">
							{claimDetails.InsuredName}
						</div>

						<div className="list-subtext list-subtext-info pb-2">
							{claimDetails.InsuredSSN}
						</div>
					</div>

					<div className="claims-detail-insured-vehicle-section col-6 row">
						<div className="align-items-start col-6 d-flex flex-column justify-content-center">
							<div className="list-title pb-2">
								Insured Vehicle
							</div>

							<div className="list-text pb-2">
								{claimDetails.InsuredVehicle}
							</div>

							<div className="list-subtext pb-2">
								VIN#:
								{addSecurityMask(claimDetails.InsuredVIN)}
							</div>
						</div>

						<div className="col-6">
							<img
								className="w-100"
								onError={addImageFallback}
								src={`${getWebDavUrl()}/${getMiddleWords(
									claimDetails.InsuredVehicle
								)}.svg`}
							/>
						</div>
					</div>
				</div>
			)}
		</div>
	);
};

export default InsuranceInfo;
