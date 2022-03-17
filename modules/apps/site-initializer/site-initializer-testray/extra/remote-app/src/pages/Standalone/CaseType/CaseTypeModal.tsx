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

import ListView from '../../../components/ListView/ListView';
import {getCaseTypes} from '../../../graphql/queries';
import CaseTypeFormModal from './CaseTypeFormModal';
import useCaseTypeActions from './useCaseTypeActions';

const CaseTypeModal = () => {
	const {actions, formModal} = useCaseTypeActions();

	return (
		<>
			<ListView
				forceRefetch={formModal.forceRefetch}
				managementToolbarProps={{addButton: formModal.modal.open}}
				query={getCaseTypes}
				tableProps={{
					actions,
					columns: [
						{
							key: 'name',
							value: 'Name',
						},
					],
				}}
				transformData={(data) => data?.c?.caseTypes}
			/>

			<CaseTypeFormModal modal={formModal.modal} />
		</>
	);
};

export default CaseTypeModal;
