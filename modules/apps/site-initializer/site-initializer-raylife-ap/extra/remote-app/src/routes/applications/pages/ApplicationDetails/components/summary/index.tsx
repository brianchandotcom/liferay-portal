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
