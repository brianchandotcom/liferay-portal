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
import './ModalAddColumns.scss';
declare function ModalAddColumns<T extends ModalItem>({
	disableRequired,
	getName,
	items,
	observer,
	onClose,
	onSave,
	selected,
}: IProps<T>): JSX.Element;
export default ModalAddColumns;
interface ModalItem {
	id?: unknown;
	label: LocalizedValue<string>;
	required?: boolean;
}
interface IProps<T extends ModalItem>
	extends React.HTMLAttributes<HTMLElement> {
	disableRequired?: boolean;
	getName: (label: T) => string;
	items: T[];
	observer: any;
	onClose: () => void;
	onSave: (selected: T[]) => void;
	selected?: T[];
}
