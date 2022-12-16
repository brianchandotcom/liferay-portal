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

import {Observer} from '@clayui/modal/lib/types';
import {Entity} from './index';
interface ModalEditExternalReferenceCodeProps {
	externalReferenceCode: string;
	helpMessage: string;
	observer: Observer;
	onClose: () => void;
	onExternalReferenceCodeChange?: (value: string) => void;
	onGetEntity: () => Promise<Entity>;
	saveURL: string;
	setExternalReferenceCode: (value: string) => void;
}
export declare function ModalEditExternalReferenceCode({
	externalReferenceCode,
	helpMessage,
	observer,
	onClose,
	onExternalReferenceCodeChange,
	onGetEntity,
	saveURL,
	setExternalReferenceCode,
}: ModalEditExternalReferenceCodeProps): JSX.Element;
export {};
