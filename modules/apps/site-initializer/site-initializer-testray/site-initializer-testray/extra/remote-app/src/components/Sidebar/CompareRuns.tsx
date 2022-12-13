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

import ClayButton from '@clayui/button';
import ClayLayout from '@clayui/layout';
import {useNavigate} from 'react-router-dom';

const CompareRun = () => {
	const navigate = useNavigate();

	return (
		<div>
			<ClayLayout.Row>
				<ClayLayout.Col>
					<label htmlFor="runA">Run A</label>

					<ClayButton block disabled displayType="secondary">
						Add Run A
					</ClayButton>
				</ClayLayout.Col>

				<ClayLayout.Col>
					<label htmlFor="runB">Run B</label>

					<ClayButton block disabled displayType="secondary">
						Add Run B
					</ClayButton>
				</ClayLayout.Col>
			</ClayLayout.Row>

			<ClayLayout.Row className="mb-3 mt-4">
				<ClayLayout.Col className="d-flex justify-content-between">
					<ClayButton
						displayType="secondary"
						onClick={() => navigate('compare-runs')}
					>
						Compare Runs
					</ClayButton>

					<ClayButton disabled displayType="secondary">
						Auto Fill Runs
					</ClayButton>

					<ClayButton disabled displayType="secondary">
						Auto Fill Builds
					</ClayButton>
				</ClayLayout.Col>
			</ClayLayout.Row>

			<div className="d-flex justify-content-end">
				<ClayButton displayType="secondary">Clear</ClayButton>
			</div>
		</div>
	);
};

export default CompareRun;
