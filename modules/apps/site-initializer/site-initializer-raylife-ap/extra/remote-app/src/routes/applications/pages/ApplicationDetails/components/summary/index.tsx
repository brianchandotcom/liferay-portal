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
		<div className="bg-neutral-0 rounded summary-container">
			<div className="pt-3 px-5 summary-title">
				<h5 className="m-0">Summary</h5>
			</div>

			<hr />

			<div className="d-flex flex-column pb-5 px-5 summary-content">
				<div className="d-flex flex-column mb-3">
					<label>Submitted on</label>

					<span className="font-weight-bold">
						{data?.applicationCreateDate}
					</span>

					{!data?.applicationCreateDate && <i>No data</i>}
				</div>

				<div className="d-flex flex-column mb-3">
					<label>Address</label>

					<span className="font-weight-bold">
						{data?.address && data.address}
					</span>

					{!data?.address && <i>No data</i>}
				</div>

				<div className="d-flex flex-column mb-3">
					<label>Name</label>

					<span className="font-weight-bold">
						{data?.firstName &&
							`${data?.firstName} ${data?.lastName}`}
					</span>

					{!data?.firstName && <i>No data</i>}
				</div>

				<div className="d-flex flex-column mb-3">
					<label>Email</label>

					<span className="font-weight-bold">
						{data?.email && data.email}
					</span>

					{!data?.email && <i>No data</i>}
				</div>

				<div className="d-flex flex-column">
					<label>Phone</label>

					<span className="font-weight-bold">
						{data?.phone && data.phone}
					</span>

					{!data?.phone && <i>No data</i>}
				</div>
			</div>
		</div>
	);
};

export default Summary;
