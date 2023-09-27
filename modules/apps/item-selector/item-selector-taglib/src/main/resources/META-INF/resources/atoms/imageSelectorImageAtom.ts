/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {State} from '@liferay/frontend-js-state-web';

export const STR_NULL_IMAGE_FILE_ENTRY_ID = '0';

const imageSelectorImageAtom = State.atom('imageSelectorImage', {
	fileEntryId: STR_NULL_IMAGE_FILE_ENTRY_ID,
	paramName: '',
	src: '',
});

export default imageSelectorImageAtom;
