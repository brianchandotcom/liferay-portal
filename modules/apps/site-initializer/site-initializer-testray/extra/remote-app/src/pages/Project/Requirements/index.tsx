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

import ClayIcon from '@clayui/icon';

import Container from '../../../components/Layout/Container';
import ListView from '../../../components/ListView/ListView';
import {getRequirements} from '../../../graphql/queries';
import i18n from '../../../i18n';
import RequirementsModal from './RequirementModal';
import useRequirementActions from './useRequirementActions';

const Requirements = () => {
	const {actions, formModal} = useRequirementActions();

	return (
		<>
			<Container title={i18n.translate('requirements')}>
				<ListView
					forceRefetch={formModal.forceRefetch}
					managementToolbarProps={{
						addButton: formModal.modal.open,
						visible: true,
					}}
					query={getRequirements}
					tableProps={{
						actions,
						columns: [
							{
								clickable: true,
								key: 'key',
								value: 'Key',
							},
							{
								key: 'linkTitle',
								render: (
									linkTitle: string,
									{linkURL}: {linkURL: string}
								) => (
									<a
										href={linkURL}
										rel="noopener noreferrer"
										target="_blank"
									>
										{linkTitle}

										<ClayIcon
											className="ml-2"
											symbol="shortcut"
										/>
									</a>
								),
								value: 'Link',
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
							{
								key: 'components',
								value: i18n.translate('jira-components'),
							},
							{
								key: 'summary',
								size: 'md',
								value: i18n.translate('summary'),
							},
						],
						navigateTo: ({id}) => id?.toString(),
					}}
					transformData={(data) => data?.requirements}
				/>
			</Container>
			<RequirementsModal modal={formModal.modal} />
		</>
	);
};

export default Requirements;
