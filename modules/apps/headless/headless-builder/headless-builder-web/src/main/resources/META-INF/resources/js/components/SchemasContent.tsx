/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {Dispatch, SetStateAction, useState} from 'react';

import EditAPISchema from '../components/EditAPISchema';
import APISchemasTable from '../components/FDS/APISchemasTable';

interface SchemasContentProps {
	apiURLPaths: APIURLPaths;
	currentAPIApplicationId: string;
	portletId: string;
	setManagementButtonsProps: Dispatch<SetStateAction<ManagementButtonsProps>>;
	setStatus: Dispatch<SetStateAction<ApplicationStatusKeys>>;
	setTitle: Dispatch<SetStateAction<string>>;
}

export default function SchemasContent({
	apiURLPaths,
	currentAPIApplicationId,
	portletId,
	setManagementButtonsProps,
	setStatus,
	setTitle,
}: SchemasContentProps) {
	const [mainSchemaNav, setMainSchemaNav] = useState<MainNav>('list');

	return (
		<>
			{mainSchemaNav === 'list' ? (
				<APISchemasTable
					apiURLPaths={apiURLPaths}
					currentAPIApplicationId={currentAPIApplicationId}
					portletId={portletId}
					setMainSchemaNav={setMainSchemaNav}
				/>
			) : (
				mainSchemaNav.edit && (
					<EditAPISchema
						apiURLPaths={apiURLPaths}
						currentAPIApplicationId={currentAPIApplicationId}
						schemaId={mainSchemaNav.edit}
						setMainSchemaNav={setMainSchemaNav}
						setManagementButtonsProps={setManagementButtonsProps}
						setStatus={setStatus}
						setTitle={setTitle}
					/>
				)
			)}
		</>
	);
}
