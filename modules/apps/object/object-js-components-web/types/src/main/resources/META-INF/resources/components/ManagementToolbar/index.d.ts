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

/// <reference types="react" />

import './index.scss';
import {NotificationTemplate} from '../../utils/api';
export declare type Entity = NotificationTemplate | ObjectDefinition;
interface ManagementToolbarProps {
	backURL: string;
	badgeClassName?: string;
	badgeLabel?: string;
	className?: string;
	entityId: number;
	externalReferenceCode: string;
	externalReferenceCodeSaveURL: string;
	hasPublishPermission: boolean;
	hasUpdatePermission: boolean;
	helpMessage: string;
	isApproved?: boolean;
	label: string;
	onExternalReferenceCodeChange?: (value: string) => void;
	onGetEntity: () => Promise<Entity>;
	onSubmit: (props: boolean) => void;
	portletNamespace: string;
	screenNavigationCategoryKey?: string;
	showEntityDetails?: boolean;
}
export declare function ManagementToolbar({
	backURL,
	badgeClassName,
	badgeLabel,
	className,
	entityId,
	externalReferenceCode: initialExternalReferenceCode,
	externalReferenceCodeSaveURL,
	hasPublishPermission,
	hasUpdatePermission,
	helpMessage,
	isApproved,
	label,
	onExternalReferenceCodeChange,
	onGetEntity,
	onSubmit,
	portletNamespace,
	screenNavigationCategoryKey,
	showEntityDetails,
}: ManagementToolbarProps): JSX.Element;
export {};
