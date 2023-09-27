/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ListView from '../../../components/ListView';
import i18n from '../../../i18n';
import {testrayFactorOptionsImpl} from '../../../services/rest';
import FactorOptionsFormModal from './FactorOptionsFormModal';
import useFactorOptionsActions from './useFactorOptionsActions';

const FactorOptionsModal = () => {
	const {actions, formModal} = useFactorOptionsActions();

	return (
		<>
			<ListView
				forceRefetch={formModal.forceRefetch}
				managementToolbarProps={{
					addButton: () => formModal.modal.open(),
				}}
				resource={testrayFactorOptionsImpl.resource}
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
				transformData={(response) =>
					testrayFactorOptionsImpl.transformDataFromList(response)
				}
			/>

			<FactorOptionsFormModal modal={formModal.modal} />
		</>
	);
};

export default FactorOptionsModal;
