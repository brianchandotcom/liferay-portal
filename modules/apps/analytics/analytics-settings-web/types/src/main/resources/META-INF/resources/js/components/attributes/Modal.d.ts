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
import {TQueries} from '../../utils/request';
import {TColumn, TFormattedItems} from '../table/Table';
declare type TRawItem = {
	example: string;
	name: string;
	required: boolean;
	selected: boolean;
	source: string;
	type: string;
};
export interface ICommonModalProps {
	observer: any;
	onCancel: () => void;
	onSubmit: () => void;
}
interface IModalProps {
	columns: TColumn[];
	observer: any;
	onCancel: () => void;
	onSubmit: (items: TFormattedItems) => void;
	requestFn: (params: TQueries) => Promise<any>;
	title: string;
}
declare const Modal: React.FC<IModalProps>;
export declare function getFields(items: TFormattedItems): TRawItem[];
export default Modal;
