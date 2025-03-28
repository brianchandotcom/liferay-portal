/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IInternalRenderer} from '@liferay/frontend-data-set-web';

import VocabularyRenderer from './cell_renderers/VocabularyRenderer';

export default function VocabularyFDSPropsTransformer({
	...otherProps
}: {
	otherProps: any;
}) {
	return {
		...otherProps,
		customRenderers: {
			tableCell: [
				{
					component: VocabularyRenderer,
					name: 'customVocabularyRenderer',
					type: 'internal',
				} as IInternalRenderer,
			],
		},
	};
}
