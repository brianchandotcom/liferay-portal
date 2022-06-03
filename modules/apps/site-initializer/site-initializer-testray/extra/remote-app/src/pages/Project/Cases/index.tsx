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

import {useLocation, useNavigate, useParams} from 'react-router-dom';

import Container from '../../../components/Layout/Container';
import ListView, {ListViewProps} from '../../../components/ListView/ListView';
import {TableProps} from '../../../components/Table';
import {getCases} from '../../../graphql/queries';
import {FormModal} from '../../../hooks/useFormModal';
import i18n from '../../../i18n';
import {filters} from '../../../schema/filter';
import dayjs from '../../../util/date';
import {searchUtil} from '../../../util/search';
import CaseModal from './CaseModal';
import useCaseActions from './useCaseActions';

type CaseListViewProps = {
	actions?: any[];
	formModal?: FormModal;
	projectId?: number | string;
	variables?: any;
} & {
	listViewProps?: Partial<ListViewProps> & {initialContext?: any};
	tableProps?: Partial<TableProps>;
};

const CaseListView: React.FC<CaseListViewProps> = ({
	actions,
	formModal,
	listViewProps,
	tableProps,
	variables,
}) => {
	const {pathname} = useLocation();
	const navigate = useNavigate();

	return (
		<ListView
			forceRefetch={formModal?.forceRefetch}
			managementToolbarProps={{
				addButton: () => navigate('create', {state: {back: pathname}}),
				filterFields: filters.case,
				title: i18n.translate('cases'),
			}}
			query={getCases}
			tableProps={{
				actions,
				columns: [
					{
						key: 'dateCreated',
						render: (dateCreated) =>
							dayjs(dateCreated).format('lll'),
						value: i18n.translate('create-date'),
					},
					{
						key: 'dateModified',
						render: (dateModified) =>
							dayjs(dateModified).format('lll'),
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
						render: (_, {component}) => component?.team?.name,
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
				...tableProps,
			}}
			transformData={(data) => data?.cases}
			variables={variables}
			{...listViewProps}
		/>
	);
};

const Cases = () => {
	const {projectId} = useParams();

	const {actions, formModal} = useCaseActions();

	return (
		<>
			<Container>
				<CaseListView
					actions={actions}
					formModal={formModal}
					listViewProps={{
						initialContext: {
							filters: {
								columns: {
									caseType: false,
									dateCreated: false,
									dateModified: false,
									issues: false,
									team: false,
								},
							},
						},
					}}
					variables={{
						filter: searchUtil.eq('projectId', projectId as string),
					}}
				/>
			</Container>

			<CaseModal modal={formModal.modal} projectId={Number(projectId)} />
		</>
	);
};

export {CaseListView};

export default Cases;
