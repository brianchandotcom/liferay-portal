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
import {getRoutines} from '../../../graphql/queries';
import i18n from '../../../i18n';
import {getTimeFromNow} from '../../../util/date';
import RoutineModal from './RoutineModal';
import useRoutineActions from './useRoutineActions';

const Routines = () => {
	const {actions, formModal} = useRoutineActions();

	return (
		<Container title={i18n.translate('routines')}>
			<ListView
				forceRefetch={formModal.forceRefetch}
				managementToolbarProps={{addButton: formModal.modal.open}}
				query={getRoutines}
				tableProps={{
					actions,
					columns: [
						{
							clickable: true,
							key: 'name',
							value: i18n.translate('routine'),
						},
						{
							clickable: true,
							key: 'dateCreated',
							render: (dateCreated) =>
								getTimeFromNow(dateCreated),
							value: i18n.translate('execution-date'),
						},
						{
							clickable: true,
							key: 'failed',
							render: () => 0,
							value: i18n.translate('failed'),
						},
						{
							clickable: true,
							key: 'blocked',
							render: () => 0,
							value: i18n.translate('blocked'),
						},
						{
							clickable: true,
							key: 'test_fix',
							render: () => 0,
							value: i18n.translate('test-fix'),
						},
						{
							clickable: true,
							key: 'metrics',
							render: () => (
								<ProgressBar
									items={{
										blocked: 0,
										failed: 1,
										passed: 70,
									}}
								/>
							),
							size: 'sm',
							value: i18n.translate('metrics'),
						},
					],
					navigateTo: ({id}) => id?.toString(),
				}}
				transformData={(data) => data?.c?.routines}
			/>

			<RoutineModal modal={formModal.modal} />
		</Container>
	);
};

export default Routines;
