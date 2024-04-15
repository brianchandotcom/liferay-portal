/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown from '@clayui/drop-down';
import {openConfirmModal, sub} from 'frontend-js-web';
import React, {useContext} from 'react';

import ChartContext from '../ChartContext';
import {deleteAccount, updateAccount} from '../data/accounts';
import {
	ACTION_KEYS,
	INFO_PANEL_MODE_MAP,
	INFO_PANEL_OPEN_EVENT,
	MODEL_TYPE_MAP,
} from '../utils/constants';
import {hasPermission} from '../utils/index';

export default function AccountMenuContent({
	closeMenu,
	data,
	namespace,
	parentData,
}) {
	const {chartInstanceRef} = useContext(ChartContext);

	function handleDelete() {
		openConfirmModal({
			message: sub(Liferay.Language.get('x-will-be-deleted'), data.name),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					deleteAccount(data.id).then(() => {
						chartInstanceRef.current.deleteNodes([data], true);

						closeMenu();
					});
				}
			},
		});
	}

	function handleEdit() {
		Liferay.fire(`${namespace}${INFO_PANEL_OPEN_EVENT}`, {
			data,
			mode: INFO_PANEL_MODE_MAP.edit,
			type: MODEL_TYPE_MAP.account,
		});

		closeMenu();
	}

	function handleRemove() {
		openConfirmModal({
			message: sub(
				Liferay.Language.get('x-will-be-removed-from-x'),
				data.name,
				parentData.name
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					updateAccount(data.id, {
						organizationIds: data.organizationIds.filter(
							(id) => Number(id) !== Number(parentData.id)
						),
					}).then(() => {
						chartInstanceRef.current.deleteNodes([data], false);

						closeMenu();
					});
				}
			},
		});
	}

	function handleView() {
		Liferay.fire(`${namespace}${INFO_PANEL_OPEN_EVENT}`, {
			data,
			mode: INFO_PANEL_MODE_MAP.view,
			type: MODEL_TYPE_MAP.account,
		});

		closeMenu();
	}

	const actions = [];

	actions.push(
		<ClayDropDown.Item key="view" onClick={handleView}>
			{Liferay.Language.get('view')}
		</ClayDropDown.Item>
	);

	if (hasPermission(data, ACTION_KEYS.account.REMOVE)) {
		actions.push(
			<ClayDropDown.Item key="remove" onClick={handleRemove}>
				{Liferay.Language.get('remove')}
			</ClayDropDown.Item>
		);
	}

	if (hasPermission(data, ACTION_KEYS.account.DELETE)) {
		actions.push(
			<ClayDropDown.Item key="delete" onClick={handleDelete}>
				{Liferay.Language.get('delete')}
			</ClayDropDown.Item>
		);
	}

	if (hasPermission(data, ACTION_KEYS.account.UPDATE)) {
		actions.push(
			<ClayDropDown.Item key="edit" onClick={handleEdit}>
				{Liferay.Language.get('edit')}
			</ClayDropDown.Item>
		);
	}

	return actions;
}
