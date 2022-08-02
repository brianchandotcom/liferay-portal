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

import ListView from '../../../components/ListView';
import i18n from '../../../i18n';
import {
	TestrayTeam,
	componentsResource,
	getComponentsTransformData,
} from '../../../services/rest';
import {searchUtil} from '../../../util/search';
import TeamFormModal from './ComponentsFormModal';
import useComponentActions from './useComponentActions';

type ComponentsModalProps = {
	projectId: number;
};

const ComponentsModal: React.FC<ComponentsModalProps> = ({projectId}) => {
	const {actions, formModal} = useComponentActions();

	return (
		<>
			<ListView
				forceRefetch={formModal.forceRefetch}
				managementToolbarProps={{
					addButton: () => formModal.modal.open(),
				}}
				resource={componentsResource}
				tableProps={{
					actions,
					columns: [
						{
							key: 'team',
							render: (testrayTeam: TestrayTeam) =>
								testrayTeam?.name,
							value: i18n.translate('team'),
						},
						{
							key: 'name',
							value: i18n.translate('name'),
						},
					],
				}}
				transformData={getComponentsTransformData}
				variables={{filter: searchUtil.eq('projectId', projectId)}}
			/>

			<TeamFormModal modal={formModal.modal} projectId={projectId} />
		</>
	);
};

export default ComponentsModal;
