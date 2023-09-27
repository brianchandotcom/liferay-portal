/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ListView from '../../../components/ListView';
import SearchBuilder from '../../../core/SearchBuilder';
import i18n from '../../../i18n';
import {testrayTeamImpl} from '../../../services/rest';
import TeamFormModal from './TeamsFormModal';
import useTeamActions from './useTeamActions';

type TeamsModalProps = {
	projectId: number;
};

const TeamsModal: React.FC<TeamsModalProps> = ({projectId}) => {
	const {actions, formModal} = useTeamActions();

	return (
		<>
			<ListView
				forceRefetch={formModal.forceRefetch}
				managementToolbarProps={{
					addButton: () => formModal.modal.open(),
					display: {columns: false},
					filterSchema: 'teams',
				}}
				resource={testrayTeamImpl.resource}
				tableProps={{
					actions,
					columns: [
						{
							key: 'name',
							value: i18n.translate('name'),
						},
					],
				}}
				transformData={(response) =>
					testrayTeamImpl.transformDataFromList(response)
				}
				variables={{filter: SearchBuilder.eq('projectId', projectId)}}
			/>

			<TeamFormModal modal={formModal.modal} projectId={projectId} />
		</>
	);
};

export default TeamsModal;
