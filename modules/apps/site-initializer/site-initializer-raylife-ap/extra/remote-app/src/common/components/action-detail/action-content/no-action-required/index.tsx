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

const NoActionRequired = () => (
	<div className="d-flex flex-column">
		<div className="action-detail-title pt-3 px-5">
			<h5 className="m-0">No Action Required</h5>
		</div>

		<hr />

		<div className="action-detail-content px-5">
			<p>
				There is currently no action required of you. You will be
				notified when your attention is needed.
			</p>
		</div>
	</div>
);

export default NoActionRequired;
