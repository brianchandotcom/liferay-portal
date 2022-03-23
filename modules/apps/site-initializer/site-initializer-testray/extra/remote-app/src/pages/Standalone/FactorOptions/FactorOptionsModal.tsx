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
import {getFactorOptions} from '../../../graphql/queries';
import i18n from '../../../i18n';
import FactorOptionsFormModal from './FactorOptionsFormModal';
import useFactorOptionsActions from './useFactorOptionsActions';

const FactorOptionsModal = () => {
	const {actions, formModal} = useFactorOptionsActions();

	return (
		<>
			<ListView
				forceRefetch={formModal.forceRefetch}
				managementToolbarProps={{addButton: formModal.modal.open}}
				query={getFactorOptions}
				tableProps={{
					actions,
					columns: [
						{
							key: 'name',
							value: i18n.translate('name'),
						},
						{
							key: 'factorCategory',
							render: (factorCategory) => factorCategory?.name,
							value: i18n.translate('category'),
						},
					],
				}}
				transformData={(data) => data?.factorOptions}
			/>

			<FactorOptionsFormModal modal={formModal.modal} />
		</>
	);
};

export default FactorOptionsModal;
