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

import {API, ManagementToolbar} from '@liferay/object-js-components-web';
import React from 'react';

interface ObjectManagementToolbarProps {
	backURL: string;
	externalReferenceCode: string;
	hasPublishObjectPermission: boolean;
	hasUpdateObjectDefinitionPermission: boolean;
	isApproved: boolean;
	label: string;
	objectDefinitionId: number;
	portletNamespace: string;
	screenNavigationCategoryKey: string;
	system: boolean;
}

export default function ObjectManagementToolbar(
	props: ObjectManagementToolbarProps
) {
	const {
		externalReferenceCode,
		hasPublishObjectPermission,
		hasUpdateObjectDefinitionPermission,
		objectDefinitionId,
		portletNamespace,
		system,
	} = props;

	const submitObjectDefinition = (draft: boolean) => {
		const form = document.getElementById(`${portletNamespace}fm`);

		if (!draft) {
			form?.querySelector(`#${portletNamespace}cmd`)?.setAttribute(
				'value',
				'publish'
			);
		}

		form?.querySelector(
			`#${portletNamespace}externalReferenceCode`
		)?.setAttribute('value', externalReferenceCode);

		(form as HTMLFormElement)?.submit();
	};

	return (
		<ManagementToolbar
			badgeClassName={system ? 'label-info' : 'label-warning'}
			badgeLabel={
				system
					? Liferay.Language.get('system')
					: Liferay.Language.get('custom')
			}
			className="border-bottom"
			entityId={objectDefinitionId}
			externalReferenceCodeSaveURL={`/o/object-admin/v1.0/object-definitions/${objectDefinitionId}`}
			hasPublishPermission={hasPublishObjectPermission}
			hasUpdatePermission={hasUpdateObjectDefinitionPermission}
			helpMessage={Liferay.Language.get(
				'internal-key-to-reference-the-object-definition'
			)}
			onGetEntity={() => API.getObjectDefinitionById(objectDefinitionId)}
			onSubmit={submitObjectDefinition}
			{...props}
		/>
	);
}
