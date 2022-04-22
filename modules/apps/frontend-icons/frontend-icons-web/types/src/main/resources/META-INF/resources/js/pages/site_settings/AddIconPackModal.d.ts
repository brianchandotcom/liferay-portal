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

import type {IIconPacks} from '../../types';
interface IProps {
	icons: IIconPacks;
	onSelectedPacksChange: (selectedPacks: string[]) => void;
	onVisibleChange: (visible: boolean) => void;
	portletNamespace: string;
	saveSiteSettingsURL: string;
	selectedPacks: string[];
	visible: boolean;
}
export default function AddIconPackModal({
	icons,
	onSelectedPacksChange,
	onVisibleChange,
	portletNamespace,
	saveSiteSettingsURL,
	selectedPacks: initialSelectedPacks,
	visible,
}: IProps): JSX.Element | null;
export {};
