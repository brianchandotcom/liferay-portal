/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import {API} from '@liferay/object-js-components-web';
import {ManagementToolbar} from 'frontend-js-components-web';
import {openToast} from 'frontend-js-web';
import React, {useContext} from 'react';

import {StructureSettingsContext} from '../contexts/StructureSettingsContext';
import StructureService from '../services/StructureService';

export default function ManagementBar() {
	const {name, setError} = useContext(StructureSettingsContext);

	const onSave = async () => {
		try {
			await StructureService.saveStructure({name});

			openToast({
				message: Liferay.Util.sub(
					Liferay.Language.get('x-was-created-successfully'),
					name
				),
				type: 'success',
			});

			setError(null);
		}
		catch (error) {
			const {message} = error as API.ErrorDetails;

			setError(message);
		}
	};

	return (
		<ManagementToolbar.Container className="border">
			<ManagementToolbar.ItemList className="c-gap-3" expand>
				<ManagementToolbar.Item>
					<ClayLink
						aria-label={Liferay.Language.get('back')}
						className="btn btn-monospaced btn-outline-borderless btn-outline-secondary btn-sm"
						href="structures"
					>
						<ClayIcon symbol="angle-left" />
					</ClayLink>
				</ManagementToolbar.Item>

				<ManagementToolbar.Item className="nav-item-expand">
					<h2 className="font-weight-semi-bold m-0 text-5">
						{Liferay.Language.get('new-structure')}
					</h2>
				</ManagementToolbar.Item>

				<ManagementToolbar.Item>
					<ClayLink
						className="btn btn-outline-borderless btn-outline-secondary btn-sm"
						href="structures"
					>
						{Liferay.Language.get('cancel')}
					</ClayLink>
				</ManagementToolbar.Item>

				<ManagementToolbar.Item>
					<ClayButton
						displayType="secondary"
						onClick={onSave}
						size="sm"
					>
						{Liferay.Language.get('save')}
					</ClayButton>
				</ManagementToolbar.Item>

				<ManagementToolbar.Item>
					<ClayButton displayType="primary" size="sm">
						{Liferay.Language.get('publish')}
					</ClayButton>
				</ManagementToolbar.Item>
			</ManagementToolbar.ItemList>
		</ManagementToolbar.Container>
	);
}
