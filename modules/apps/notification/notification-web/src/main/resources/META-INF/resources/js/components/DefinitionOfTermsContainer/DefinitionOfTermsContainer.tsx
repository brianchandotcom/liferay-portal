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
