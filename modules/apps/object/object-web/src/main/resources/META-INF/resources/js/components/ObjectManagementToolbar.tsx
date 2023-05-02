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
	onSubmit: (draft: boolean) => void;
	portletNamespace: string;
	screenNavigationCategoryKey: string;
	setValues: (values: Partial<ObjectDefinition>) => void;
	system: boolean;
}

export default function ObjectManagementToolbar({
	backURL,
	externalReferenceCode,
	hasPublishObjectPermission,
	hasUpdateObjectDefinitionPermission,
	isApproved,
	label,
	objectDefinitionId,
	onSubmit,
	portletNamespace,
	screenNavigationCategoryKey,
	setValues,
	system,
}: ObjectManagementToolbarProps) {
	return (
		<ManagementToolbar
			backURL={backURL}
			badgeClassName={system ? 'label-info' : 'label-warning'}
			badgeLabel={
				system
					? Liferay.Language.get('system')
					: Liferay.Language.get('custom')
			}
			className="border-bottom"
			enableBoxShadow={false}
			entityId={objectDefinitionId}
			externalReferenceCode={externalReferenceCode}
			externalReferenceCodeSaveURL={`/o/object-admin/v1.0/object-definitions/${objectDefinitionId}`}
			hasPublishPermission={hasPublishObjectPermission}
			hasUpdatePermission={hasUpdateObjectDefinitionPermission}
			helpMessage={Liferay.Language.get(
				'unique-key-for-referencing-the-object-definition'
			)}
			isApproved={isApproved}
			label={label}
			onExternalReferenceCodeChange={(externalReferenceCode: string) => {
				setValues({
					externalReferenceCode,
				});
			}}
			onGetEntity={() => API.getObjectDefinitionById(objectDefinitionId)}
			onSubmit={onSubmit}
			portletNamespace={portletNamespace}
			screenNavigationCategoryKey={screenNavigationCategoryKey}
		/>
	);
}
