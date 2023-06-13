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

import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {openModal} from 'frontend-js-web';
import React from 'react';

import {CreateAPIApplicationModalContent} from '../modals/CreateAPIApplicationModalContent';
import {getAPIApplicationsFDSProps} from './fdsUtils/fdsProps';

interface APIApplicationsTableProps {
	apiURL: string;
	portletId: string;
	readOnly: boolean;
}

export default function APIApplicationsTable({
	apiURL,
	portletId,
	readOnly,
}: APIApplicationsTableProps) {
	const createAPIApplication = {
		onClick: ({loadData}: {loadData: voidReturn}) => {
			openModal({
				center: true,
				contentComponent: ({closeModal}: {closeModal: voidReturn}) =>
					CreateAPIApplicationModalContent({
						apiURL,
						closeModal,
						loadData,
					}),
				id: 'createAPIApplicationModal',
				size: 'md',
			});
		},
	};

	return (
		<FrontendDataSet
			{...getAPIApplicationsFDSProps(apiURL, portletId)}
			creationMenu={{
				primaryItems: readOnly ? [] : ([createAPIApplication] as any),
			}}
		/>
	);
}
