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

import React, {useEffect, useState} from 'react';

const currentPath = Liferay.currentURL.split('/');
const mdfRequestId = +currentPath.at(-1);
const SITE_URL = Liferay.ThemeDisplay.getLayoutRelativeURL()
	.split('/')
	.slice(0, 3)
	.join('/');

export default function () {
	const [claims, setClaims] = useState();
	const [loading, setLoading] = useState();

	useEffect(() => {
		const getClaimFromMDFRequest = async () => {
			// eslint-disable-next-line @liferay/portal/no-global-fetch
			const response = await fetch(
				`/o/c/mdfclaims/?filter=(r_mdfRequestToMdfClaims_c_mdfRequestId eq '${mdfRequestId}')`,
				{
					headers: {
						'accept': 'application/json',
						'x-csrf-token': Liferay.authToken,
					},
				}
			);

			if (response.ok) {
				setClaims(await response.json());

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
	}, []);

	if (loading) {
		return <>Loading...</>;
	}

	return (
		<div>
			<div className="align-items-stretch d-flex justify-content-between">
				<div>
					<h6 className="font-weight-normal text-neutral-9">
						Get Reimbursed
					</h6>

					{claims?.items.length < 2 ? (
						<h6 className="font-weight-normal text-neutral-8">
							You can submit up to {2 - claims.items.length}{' '}
							claim(s).
						</h6>
					) : (
						<h6 className="font-weight-normal text-neutral-8">
							You already submitted 2 claims.
						</h6>
					)}
				</div>

				{claims?.items.length < 2 && (
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
	);
}
