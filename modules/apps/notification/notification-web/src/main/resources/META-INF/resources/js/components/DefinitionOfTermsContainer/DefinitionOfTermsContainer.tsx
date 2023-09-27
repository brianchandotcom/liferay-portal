/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text} from '@clayui/core';
import {Card} from '@liferay/object-js-components-web';
import React from 'react';

import {DefinitionOfTerms} from './DefinitionOfTerms';
import {GeneralTerms} from './GeneralTerms';

interface DefinitionOfTermsContainerProps {
	baseResourceURL: string;
	objectDefinitions: ObjectDefinition[];
}
export interface Item {
	termLabel: string;
	termName: string;
}
export default function DefinitionOfTermsContainer({
	baseResourceURL,
	objectDefinitions,
}: DefinitionOfTermsContainerProps) {
	return (
		<Card title={Liferay.Language.get('definition-of-terms')}>
			<Text as="span" color="secondary">
				{Liferay.Language.get(
					'use-terms-to-populate-fields-dynamically-with-the-exception-of-the-freemarker-template-editor'
				)}
			</Text>

			<GeneralTerms baseResourceURL={baseResourceURL} />

			<DefinitionOfTerms
				baseResourceURL={baseResourceURL}
				objectDefinitions={objectDefinitions}
			/>
		</Card>
	);
}
