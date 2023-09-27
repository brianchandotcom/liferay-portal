/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useLocation, useNavigate, useParams} from 'react-router-dom';

import Container from '../../../components/Layout/Container';
import ListView, {ListViewProps} from '../../../components/ListView';
import {TableProps} from '../../../components/Table';
import {ListViewContextProviderProps} from '../../../context/ListViewContext';
import SearchBuilder from '../../../core/SearchBuilder';
import {FormModal} from '../../../hooks/useFormModal';
import i18n from '../../../i18n';
import {testrayCaseImpl} from '../../../services/rest';
import {Action} from '../../../types';
import dayjs from '../../../util/date';
import useCaseActions from './useCaseActions';

type CaseListViewProps = {
	actions?: Action[];
	formModal?: FormModal;
	projectId?: number | string;
	variables?: any;
} & {
	listViewProps?: Partial<ListViewProps> & {
		initialContext?: Partial<ListViewContextProviderProps>;
	};
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
				filterSchema: 'cases',
				title: i18n.translate('cases'),
			}}
			resource={testrayCaseImpl.resource}
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
					{
						key: 'description',
						render: (description) => description,
						value: i18n.translate('description'),
					},
					{key: 'issues', value: i18n.translate('issues')},
				],
				navigateTo: ({id}) => id?.toString(),
				...tableProps,
			}}
			transformData={(response) =>
				testrayCaseImpl.transformDataFromList(response)
			}
			variables={variables}
			{...listViewProps}
		/>
	);
};

const Cases = () => {
	const {projectId} = useParams();

	const {actions} = useCaseActions();

	return (
		<Container>
			<CaseListView
				actions={actions}
				listViewProps={{
					initialContext: {
						columns: {
							caseType: false,
							dateCreated: false,
							dateModified: false,
							description: false,
							issues: false,
							team: false,
						},
					},
				}}
				variables={{
					filter: SearchBuilder.eq('projectId', projectId as string),
				}}
			/>
		</Container>
	);
};

export {CaseListView};

export default Cases;
