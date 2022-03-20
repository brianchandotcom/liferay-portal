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
import {getFactorCategories} from '../../../graphql/queries';
import FactorCategoryFormModal from './FactorCategoryFormModal';
import useFactorCategoryActions from './useFactorCategoryActions';

const FactorCategoryModal = () => {
	const {actions, formModal} = useFactorCategoryActions();

	return (
		<>
			<ListView
				forceRefetch={formModal.forceRefetch}
				managementToolbarProps={{addButton: formModal.modal.open}}
				query={getFactorCategories}
				tableProps={{
					actions,
					columns: [
						{
							key: 'name',
							value: 'Name',
						},
					],
				}}
				transformData={(data) => data?.c?.factorCategories}
			/>

			<FactorCategoryFormModal modal={formModal.modal} />
		</>
	);
};

export default FactorCategoryModal;
