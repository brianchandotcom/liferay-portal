/* eslint-disable @liferay/empty-line-between-elements */
/* eslint-disable @liferay/portal/no-global-fetch */
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

import ClayAlert from '@clayui/alert';
import React, {useEffect, useState} from 'react';

function getIntlNumberFormat() {
	return new Intl.NumberFormat(Liferay.ThemeDisplay.getBCP47LanguageId(), {
		currency: 'USD',
		style: 'currency',
	});
}

function getSiteVariables() {
	const currentPath = Liferay.currentURL.split('/');
	const mdfRequestId = +currentPath.at(-1);
	const SITE_URL = Liferay.ThemeDisplay.getLayoutRelativeURL()
		.split('/')
		.slice(0, 3)
		.join('/');

	return [mdfRequestId, SITE_URL];
}

const ClaimStatus = {
	APPROVED: 'Approved',
	CANCELED: 'Canceled',
	CLAIM_PAID: 'Claim Paid',
	DRAFT: 'Draft',
	EXPIRED: 'Expired',
	IN_FINANCE_REVIEW: 'In Finance Review',
	MARKETING_DIRECTOR_REVIEW: 'Marketing Director Review',
	PENDING: 'Pending Marketing Review',
	REJECT: 'Reject',
	REQUEST_MORE_INFO: 'Request More Info',
};

const statusClassName = {
	[ClaimStatus.DRAFT]: 'label label-tonal-dark ml-2',
	[ClaimStatus.PENDING]: 'label label-tonal-warning ml-2',
	[ClaimStatus.APPROVED]: 'label label-tonal-success ml-2',
	[ClaimStatus.REQUEST_MORE_INFO]: 'label label-tonal-warning ml-2',
	[ClaimStatus.REJECT]: 'label label-tonal-danger ml-2',
	[ClaimStatus.EXPIRED]: 'label label-tonal-danger ml-2',
	[ClaimStatus.MARKETING_DIRECTOR_REVIEW]: 'label label-tonal-light ml-2',
	[ClaimStatus.CANCELED]: 'label label-borderless-dark ml-2',
	[ClaimStatus.CLAIM_PAID]: 'label label-tonal-info ml-2',
	[ClaimStatus.IN_FINANCE_REVIEW]: 'label label-tonal-light ml-2',
};

const Panel = ({mdfClaims}) => {
	const [, SITE_URL] = getSiteVariables();

	return (
		<div>
			<div className="text-neutral-7 text-paragraph-xs">
				Type: {mdfClaims.partial ? 'Partial' : 'Full'}
			</div>

			<div className="mb-1 mt-1 text-neutral-9 text-paragraph-sm">
				Claim ({mdfClaims.id})
			</div>

			<div className="align-items-baseline d-flex justify-content-between">
				<div className="align-items-baseline d-flex">
					<p className="font-weight-bold text-neutral-9 text-paragraph-sm">
						Claimed USD
						{getIntlNumberFormat().format(mdfClaims.amountClaimed)}
					</p>

					<div className={statusClassName[mdfClaims.claimStatus]}>
						{mdfClaims.claimStatus}
					</div>
				</div>

				<button
					className="btn btn-secondary btn-sm"
					onClick={() =>
						Liferay.Util.navigate(`${SITE_URL}/l/${mdfClaims.id}`)
					}
				>
					View
				</button>
			</div>
		</div>
	);
};

export default function () {
	const [claims, setClaims] = useState();
	const [request, setRequest] = useState();
	const [loading, setLoading] = useState();

	const [mdfRequestId, SITE_URL] = getSiteVariables();

	useEffect(() => {
		const getClaimFromMDFRequest = async () => {
			const response = await fetch(
				`/o/c/mdfclaims?nestedFields=mdfClaimToMdfClaimActivities,mdfClaimActivityToMdfClaimBudgets&nestedFieldsDepth=2&filter=(r_mdfRequestToMdfClaims_c_mdfRequestId eq '${mdfRequestId}')`,
				{
					headers: {
						'accept': 'application/json',
						'x-csrf-token': Liferay.authToken,
					},
				}
			);

			const mdfrequest = await fetch(`/o/c/mdfrequests/${mdfRequestId}`, {
				headers: {
					'accept': 'application/json',
					'x-csrf-token': Liferay.authToken,
				},
			});

			if (response.ok && mdfrequest.ok) {
				setClaims(await response.json());
				setRequest(await mdfrequest.json());

				setLoading(false);

				return;
			}

			Liferay.Util.openToast({
				message: 'An unexpected error occured.',
				type: 'danger',
			});
		};

		if (mdfRequestId) {
			getClaimFromMDFRequest();
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	if (loading) {
		return <>Loading...</>;
	}

	const claimsNotDraft = claims?.items.filter((claim) => {
		return !claim.claimStatus.includes(ClaimStatus.DRAFT);
	});

	return (
		<>
			{request?.requestStatus === 'Approved' ? (
				<div>
					{!!claims?.items.length && (
						<div>
							{claims?.items.map((mdfClaims, index) => (
								<div key={index}>
									<Panel mdfClaims={mdfClaims} />

									<hr></hr>
								</div>
							))}
						</div>
					)}

					<div className="align-items-start d-flex justify-content-between">
						<div>
							<h6 className="font-weight-normal text-neutral-9">
								Get Reimbursed
							</h6>

							{claimsNotDraft.length < 2 ? (
								<h6 className="font-weight-normal text-neutral-8">
									You can submit up to{' '}
									{2 - claimsNotDraft.length} claim(s).
								</h6>
							) : (
								<h6 className="font-weight-normal text-neutral-8">
									You already submitted 2 claims.
								</h6>
							)}
						</div>

						{claimsNotDraft.length < 2 && (
							<button
								className="btn btn-primary"
								onClick={() =>
									Liferay.Util.navigate(
										`${SITE_URL}/marketing/mdf-claim/new/#/mdfrequest/${mdfRequestId}`
									)
								}
								type="button"
							>
								New Claim
							</button>
						)}
					</div>
				</div>
			) : (
				<ClayAlert displayType="info" title="Info:">
					Waiting for Manager approval
				</ClayAlert>
			)}
		</>
	);
}
