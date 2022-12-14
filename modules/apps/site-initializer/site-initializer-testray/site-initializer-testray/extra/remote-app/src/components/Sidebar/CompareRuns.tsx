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

import i18n from '../../i18n';

const CompareRun = () => {
	const navigate = useNavigate();

	return (
		<div>
			<ClayLayout.Row>
				<ClayLayout.Col>
					<label>{i18n.translate('run-a')}</label>

					<ClayButton block disabled displayType="secondary">
						{i18n.translate('add-run-a')}
					</ClayButton>
				</ClayLayout.Col>

				<ClayLayout.Col>
					<label>{i18n.translate('run-b')}</label>

					<ClayButton block disabled displayType="secondary">
						{i18n.translate('add-run-b')}
					</ClayButton>
				</ClayLayout.Col>
			</ClayLayout.Row>

			<ClayLayout.Row className="mb-3 mt-4">
				<ClayLayout.Col className="d-flex justify-content-between">
					<ClayButton
						displayType="secondary"
						onClick={() => navigate('compare-runs')}
					>
						{i18n.translate('compare-runs')}
					</ClayButton>

					<ClayButton disabled displayType="secondary">
						{i18n.sub('auto-fill-x', 'runs')}
					</ClayButton>

					<ClayButton disabled displayType="secondary">
						{i18n.sub('auto-fill-x', 'builds')}
					</ClayButton>
				</ClayLayout.Col>
			</ClayLayout.Row>

			<div className="d-flex justify-content-end">
				<ClayButton displayType="secondary">
					{i18n.translate('clear')}
				</ClayButton>
			</div>
		</div>
	);
};

export default CompareRun;
