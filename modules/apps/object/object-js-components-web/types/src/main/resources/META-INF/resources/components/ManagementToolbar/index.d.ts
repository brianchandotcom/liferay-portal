/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	enableBoxShadow?: boolean;
	entityId: number;
	externalReferenceCode: string;
	externalReferenceCodeSaveURL: string;
	hasPublishPermission: boolean;
	hasUpdatePermission: boolean;
	helpMessage: string;
	isApproved?: boolean;
	isRootDescendantNode?: boolean;
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
	enableBoxShadow,
	entityId,
	externalReferenceCode: initialExternalReferenceCode,
	externalReferenceCodeSaveURL,
	hasPublishPermission,
	hasUpdatePermission,
	helpMessage,
	isApproved,
	isRootDescendantNode,
	label,
	onExternalReferenceCodeChange,
	onGetEntity,
	onSubmit,
	portletNamespace,
	screenNavigationCategoryKey,
	showEntityDetails,
}: ManagementToolbarProps): JSX.Element;
export {};
