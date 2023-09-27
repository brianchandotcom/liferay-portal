/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './index.scss';

const Summary = ({application}: any) => {
	const {data} = application;

	return (
		<div className="bg-neutral-0 rounded summary-container w-100">
			<div className="pt-3 px-5 summary-title">
				<h5 className="m-0">Summary</h5>
			</div>

			<hr />

			<div className="d-flex flex-column pb-5 px-5 summary-content">
				<div className="d-flex flex-column mb-3">
					<div className="mb-2 text-neutral-7">Submitted on</div>

					<div>{data?.applicationCreateDate}</div>

					{!data?.applicationCreateDate && <i>No data</i>}
				</div>

				<div className="d-flex flex-column mb-3">
					<div className="mb-2 text-neutral-7">Address</div>

					<div>{data?.address && data.address}</div>

					{!data?.address && <i>No data</i>}
				</div>

				<div className="d-flex flex-column mb-3">
					<div className="mb-2 text-neutral-7">Name</div>

					<div>
						{data?.firstName &&
							`${data?.firstName} ${data?.lastName}`}
					</div>

					{!data?.firstName && <i>No data</i>}
				</div>

				<div className="d-flex flex-column mb-3">
					<div className="mb-2 text-neutral-7">Email</div>

					<div>{data?.email && data.email}</div>

					{!data?.email && <i>No data</i>}
				</div>

				<div className="d-flex flex-column">
					<div className="mb-2 text-neutral-7">Phone</div>

					<div>{data?.phone && data.phone}</div>

					{!data?.phone && <i>No data</i>}
				</div>
			</div>
		</div>
	);
};

export default Summary;
