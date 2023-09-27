/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import {openSelectionModal, sub} from 'frontend-js-web';
import React, {useState} from 'react';

export default function ParentOrganization({
	label: initialLabel,
	parentOrganizationId: initialParentOrganizationId,
	portletNamespace,
	selectOrganizationRenderURL,
}) {
	const [{label, parentOrganizationId}, setValues] = useState({
		label: initialLabel,
		parentOrganizationId: initialParentOrganizationId,
	});

	const onChangeParentOrganization = () => {
		openSelectionModal({
			onSelect(selectedItem) {
				const itemValue = JSON.parse(selectedItem.value);

				setValues({
					label: itemValue.name,
					parentOrganizationId: itemValue.organizationId,
				});
			},
			selectEventName: `${portletNamespace}selectOrganization`,
			title: sub(
				Liferay.Language.get('select-x'),
				Liferay.Language.get('parent-organization')
			),
			url: selectOrganizationRenderURL,
		});
	};

	const onClearParentOrganization = () => {
		setValues({
			label: '',
			parentOrganizationId: '',
		});
	};

	return (
		<>
			<input
				name={`${portletNamespace}parentOrganizationId`}
				type="hidden"
				value={parentOrganizationId}
			/>

			<ClayForm.Group>
				<label htmlFor={`${portletNamespace}parentOrganization`}>
					{Liferay.Language.get('parent-organization')}
				</label>

				<div className="d-flex">
					<ClayInput
						className="mr-2"
						id={`${portletNamespace}parentOrganization`}
						readOnly
						value={label}
					/>

					<ClayButtonWithIcon
						aria-label={sub(
							Liferay.Language.get('change-x'),
							Liferay.Language.get('parent-organization')
						)}
						className="flex-shrink-0 mr-2"
						displayType="secondary"
						onClick={onChangeParentOrganization}
						symbol="change"
						title={sub(
							Liferay.Language.get('change-x'),
							Liferay.Language.get('parent-organization')
						)}
					/>

					<ClayButtonWithIcon
						aria-label={sub(
							Liferay.Language.get('clear-x'),
							Liferay.Language.get('parent-organization')
						)}
						className="flex-shrink-0"
						disabled={label === ''}
						displayType="secondary"
						onClick={onClearParentOrganization}
						symbol="times-circle"
						title={sub(
							Liferay.Language.get('clear-x'),
							Liferay.Language.get('parent-organization')
						)}
					/>
				</div>
			</ClayForm.Group>
		</>
	);
}
