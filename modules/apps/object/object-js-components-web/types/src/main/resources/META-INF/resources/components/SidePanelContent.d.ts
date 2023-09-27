/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import './SidePanelContent.scss';
export declare function closeSidePanel(): void;
export declare function saveAndReload(): void;
export declare function openToast(options: {
	message: string;
	type?: 'danger' | 'success';
}): void;
export declare function SidePanelContent({
	children,
	className,
	customLabel,
	onSave,
	readOnly,
	title,
}: IProps): JSX.Element;
export declare function SidePanelForm({
	children,
	customLabel,
	onSubmit,
	readOnly,
	title,
}: ISidePanelFormProps): JSX.Element;
interface IContainerProps {
	children: React.ReactNode;
	className?: string;
}
interface CommonProps extends IContainerProps {
	customLabel?: {
		displayType: 'success' | 'info';
		message: string;
	};
	readOnly?: boolean;
	title: string;
}
interface IProps extends CommonProps {
	onSave?: () => void;
}
interface ISidePanelFormProps extends CommonProps {
	onSubmit?: React.FormEventHandler<HTMLFormElement>;
}
export {};
