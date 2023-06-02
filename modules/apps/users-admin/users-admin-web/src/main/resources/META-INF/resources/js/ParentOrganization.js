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
			onSelect(event) {
				setValues({
					label: event.entityname,
					parentOrganizationId: event.entityid,
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
