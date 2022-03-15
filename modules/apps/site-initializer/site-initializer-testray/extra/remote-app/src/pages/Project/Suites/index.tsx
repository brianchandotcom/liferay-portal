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
import {getSuites} from '../../../graphql/queries';
import i18n from '../../../i18n';
import SuiteModal from './SuiteModal';
import useSuiteActions from './useSuiteActions';

const Suites = () => {
	const {actions, formModal} = useSuiteActions();

	return (
		<>
			<Container title={i18n.translate('suites')}>
				<ListView
					forceRefetch={formModal.forceRefetch}
					managementToolbarProps={{addButton: formModal.modal.open}}
					query={getSuites}
					tableProps={{
						actions,
						columns: [
							{
								clickable: true,
								key: 'name',
								value: i18n.translate('suite-name'),
							},
							{
								key: 'description',
								value: i18n.translate('description'),
							},
							{
								key: 'caseParameters',
								render: (caseParameters) =>
									i18n.translate(
										caseParameters ? 'smart' : 'static'
									),
								value: i18n.translate('type'),
							},
						],
						navigateTo: ({id}) => id?.toString(),
					}}
					transformData={(data) => data?.c?.suites}
				/>
			</Container>

			<SuiteModal modal={formModal.modal} />
		</>
	);
};

export default Suites;
