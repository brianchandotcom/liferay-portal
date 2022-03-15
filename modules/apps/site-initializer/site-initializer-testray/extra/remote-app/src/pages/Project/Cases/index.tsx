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
import {getCases} from '../../../graphql/queries';
import i18n from '../../../i18n';
import CaseModal from './CaseModal';
import useCaseActions from './useCaseActions';

const Cases = () => {
	const {actions, formModal} = useCaseActions();

	return (
		<>
			<Container title={i18n.translate('cases')}>
				<ListView
					forceRefetch={formModal.forceRefetch}
					initialContext={{
						filters: {
							columns: {
								caseType: false,
								dateCreated: false,
								dateModified: false,
								issues: false,
								team: false,
							},
						},
					}}
					managementToolbarProps={{
						addButton: formModal.modal.open,
						visible: true,
					}}
					query={getCases}
					tableProps={{
						actions,
						columns: [
							{
								key: 'dateCreated',
								value: i18n.translate('create-date'),
							},
							{
								key: 'dateModified',
								value: i18n.translate('modified-date'),
							},
							{
								key: 'priority',
								sorteable: true,
								value: i18n.translate('priority'),
							},
							{
								key: 'caseType',
								render: (caseType) => caseType?.name,
								value: i18n.translate('case-type'),
							},
							{
								clickable: true,
								key: 'name',
								size: 'md',
								sorteable: true,
								value: i18n.translate('case-name'),
							},
							{
								key: 'team',
								render: (_, {component}) =>
									component?.team?.name,
								value: i18n.translate('team'),
							},
							{
								key: 'component',
								render: (component) => component?.name,
								value: i18n.translate('component'),
							},
							{key: 'issues', value: i18n.translate('issues')},
						],
						navigateTo: ({id}) => id?.toString(),
					}}
					transformData={(data) => data?.cases}
				/>
			</Container>

			<CaseModal modal={formModal.modal} />
		</>
	);
};

export default Cases;
