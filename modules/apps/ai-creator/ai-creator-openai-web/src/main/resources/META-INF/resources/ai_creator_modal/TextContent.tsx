/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import React from 'react';

interface Props {
	content: string;
	portletNamespace: string;
}

export function TextContent({content, portletNamespace}: Props) {
	const textContentId = `${portletNamespace}generatedContent`;

	return (
		<ClayForm.Group>
			<label htmlFor={textContentId}>
				{Liferay.Language.get('content')}
			</label>

			<ClayInput
				component="textarea"
				id={textContentId}
				readOnly
				value={content}
			/>
		</ClayForm.Group>
	);
}
