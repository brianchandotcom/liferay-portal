/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dispatch, SetStateAction} from 'react';
interface EditAPIPropertyModalContentProps extends Partial<TreeViewItemData> {
	closeModal: voidReturn;
	setSchemaUIData: Dispatch<SetStateAction<APISchemaUIData>>;
}
export default function EditAPIPropertyModalContent({
	businessType,
	closeModal,
	description,
	name,
	objectFieldId,
	objectFieldName,
	setSchemaUIData,
}: EditAPIPropertyModalContentProps): JSX.Element;
export {};
