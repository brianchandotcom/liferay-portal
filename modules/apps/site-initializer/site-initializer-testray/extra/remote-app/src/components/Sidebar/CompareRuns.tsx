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
		<div style={{width: '380px'}}>
			<ClayLayout.Row>
				<ClayLayout.Col>
					<label htmlFor="runA">Run A</label>

					<ClayButton block displayType="secondary">
						Add Run
					</ClayButton>
				</ClayLayout.Col>

				<ClayLayout.Col>
					<label htmlFor="runB">Run B</label>

					<ClayButton block displayType="secondary">
						Add Run
					</ClayButton>
				</ClayLayout.Col>
			</ClayLayout.Row>

			<div className="mb-3 mt-4">
				<ClayButton
					className="mr-2"
					displayType="secondary"
					onClick={() => navigate('compare-runs')}
				>
					Compare Runs
				</ClayButton>

				<ClayButton disabled displayType="secondary">
					Auto Fill
				</ClayButton>
			</div>

			<div>
				<ClayButton className="mr-2" disabled displayType="secondary">
					Auto Fill Builds
				</ClayButton>

				<ClayButton displayType="secondary">Clear</ClayButton>
			</div>
		</div>
	);
};

export default CompareRun;
