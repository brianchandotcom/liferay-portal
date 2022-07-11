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

import {useMutation} from '@apollo/client';
import {useNavigate} from 'react-router-dom';

import {DeleteBuild, UpdateBuild} from '../../../../graphql/mutations';
import {TestrayBuild} from '../../../../graphql/queries';
import useFormModal from '../../../../hooks/useFormModal';
import i18n from '../../../../i18n';

const useBuildActions = () => {
	const formModal = useFormModal();
	const [onUpdateBuild] = useMutation(UpdateBuild);
	const [onDeleteBuild] = useMutation(DeleteBuild);
	const navigate = useNavigate();

	const modal = formModal.modal;

	return {
		actions: [
			{
				action: () => alert('Archive'),
				name: i18n.translate('archive'),
			},
			{
				action: (testrayBuild: TestrayBuild) =>
					navigate(`build/${testrayBuild.id}/update`),
				name: i18n.translate('edit'),
			},
			{
				action: (testrayBuild: TestrayBuild) =>
					onUpdateBuild({
						update(cache, {data: {updateBuild}}) {
							cache.modify({
								fields: {
									builds(buildCache) {
										return {
											...buildCache,
											items: buildCache.items.map(
												(build: TestrayBuild) => {
													if (
														build.id ===
														testrayBuild.id
													) {
														return {
															...build,
															promoted:
																updateBuild.promoted,
														};
													}

													return build;
												}
											),
										};
									},
								},
							});
						},
						variables: {
							data: {
								promoted: !testrayBuild.promoted,
							},
							id: testrayBuild.id,
						},
					}).then(() => modal.onSave(null, {forceRefetch: false})),
				name: i18n.translate('promote'),
			},
			{
				action: ({id}: any) =>
					onDeleteBuild({variables: {id}})
						.then(() => modal.onSave())
						.catch(modal.onError),
				name: i18n.translate('delete'),
			},
		],
		formModal,
	};
};

export default useBuildActions;
