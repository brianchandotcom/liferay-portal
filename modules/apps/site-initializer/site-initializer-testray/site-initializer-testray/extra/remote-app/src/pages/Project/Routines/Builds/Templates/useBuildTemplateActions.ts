/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useRef} from 'react';
import {BuildStatuses} from '~/util/statuses';

import useFormActions from '../../../../../hooks/useFormActions';
import useFormModal from '../../../../../hooks/useFormModal';
import useMutate from '../../../../../hooks/useMutate';
import i18n from '../../../../../i18n';
import {TestrayBuild, testrayBuildImpl} from '../../../../../services/rest';
import {Action} from '../../../../../types';

const useBuildTemplateActions = () => {
	const formModal = useFormModal();
	const {form} = useFormActions();
	const {removeItemFromList, updateItemFromList} = useMutate();

	const actionsRef = useRef([
		{
			action: (build, mutate) => {
				testrayBuildImpl
					.update(build.id, {
						dueStatus:
							build.dueStatus.key === BuildStatuses.ACTIVATED
								? BuildStatuses.DEACTIVATED
								: BuildStatuses.ACTIVATED,
					})
					.then((templateResponse) =>
						updateItemFromList(mutate, build.id, {
							dueStatus: templateResponse.dueStatus,
						})
					)
					.then(form.onSuccess)
					.catch(form.onError);
			},
			icon: 'logout',
			name: ({dueStatus}) =>
				i18n.translate(
					dueStatus.key === BuildStatuses.ACTIVATED
						? 'deactivate'
						: 'activate'
				),
			permission: 'UPDATE',
		},
		{
			action: (build, mutate) => {
				testrayBuildImpl
					.removeResource(build.id)
					?.then(() => removeItemFromList(mutate, build.id))
					.then(form.onSuccess)
					.catch(form.onError);
			},
			hidden: (build) => build.dueStatus.key === BuildStatuses.ACTIVATED,
			icon: 'trash',
			name: i18n.translate('delete'),
			permission: 'DELETE',
		},
	] as Action<TestrayBuild>[]);

	return {
		actions: actionsRef.current,
		formModal,
	};
};

export default useBuildTemplateActions;
