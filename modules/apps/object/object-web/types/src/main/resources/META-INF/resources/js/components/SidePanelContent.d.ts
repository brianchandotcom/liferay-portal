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

import React from 'react';
import './SidePanelContent.scss';
export declare function closeSidePanel(): void;
export declare function openToast(options: {
	message: string;
	type?: 'danger' | 'success';
}): void;
export default function SidePanelContent({
	children,
	className,
	onSave,
	readOnly,
	title,
}: IProps): JSX.Element;
export declare function SidePanelForm({
	children,
	onSubmit,
	readOnly,
	title,
}: ISidePanelFormProps): JSX.Element;
interface IContainerProps {
	children: React.ReactNode;
	className?: string;
}
interface CommonProps extends IContainerProps {
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
