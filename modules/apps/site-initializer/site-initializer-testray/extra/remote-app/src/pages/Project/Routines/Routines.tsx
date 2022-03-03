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

import Container from '../../../components/Layout/Container';
import ListView from '../../../components/ListView/ListView';
import ProgressBar from '../../../components/ProgressBar';
import {getTestrayRoutines} from '../../../graphql/queries';
import i18n from '../../../i18n';
import {progress} from '../../../util/mock';

const Routines = () => {
	return (
		<Container title={i18n.translate('routines')}>
			<ListView
				query={getTestrayRoutines}
				tableProps={{
					columns: [
						{
							clickable: true,
							key: 'name',
							value: i18n.translate('routine'),
						},
						{
							key: 'execution_date',
							value: i18n.translate('execution-date'),
						},
						{key: 'failed', value: i18n.translate('failed')},
						{key: 'blocked', value: i18n.translate('blocked')},
						{key: 'test_fix', value: i18n.translate('test-fix')},
						{
							key: 'metrics',
							render: () => <ProgressBar items={progress[0]} />,
							value: i18n.translate('metrics'),
						},
					],
					navigateTo: ({testrayRoutineId}) =>
						testrayRoutineId?.toString(),
				}}
				transformData={(data) => data?.c?.testrayRoutines}
			/>
		</Container>
	);
};

export default Routines;
