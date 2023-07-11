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

import {IFDSTableProps} from '../../utils/fds';
interface ViewObjectDefinitionsProps extends IFDSTableProps {
	baseResourceURL: string;
	storages: LabelTypeObject[];
}
export declare type ViewObjectDefinitionsModals = {
	addObjectDefinition: boolean;
	deleteObjectDefinition: boolean;
	importObject: boolean;
};
export interface DeletedObjectDefinition extends ObjectDefinition {
	hasObjectRelationship: boolean;
	objectEntriesCount: number;
}
export default function ViewObjectDefinitions({
	apiURL,
	baseResourceURL,
	creationMenu,
	id,
	items,
	sorting,
	storages,
	url,
}: ViewObjectDefinitionsProps): JSX.Element;
export {};
