/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClientExtension, IHTMLElementBuilder} from 'frontend-js-components-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import type {
	FDSFilter,
	FDSFilterHTMLElementBuilderArgs,
} from '@liferay/js-api/data-set';

import type {
	FilterImplementation,
	FilterImplementationArgs,
	SetFilterArgs,
} from '../Filter';

const LOADING_LABEL = '...';

type ClientExtensionFilterHTMLElementBuilder = IHTMLElementBuilder<
	FDSFilterHTMLElementBuilderArgs<unknown>
>;

export interface ClientExtensionFilterImplementationArgs
	extends FilterImplementationArgs<unknown> {
	clientExtensionFilterImplementation?: FDSFilter<unknown>;
	clientExtensionFilterURL: string;
}

function getSelectedItemsLabel({
	clientExtensionFilterImplementation,
	clientExtensionFilterURL,
	selectedData,
}: ClientExtensionFilterImplementationArgs) {
	if (!clientExtensionFilterImplementation) {
		return LOADING_LABEL;
	}

	if (!clientExtensionFilterImplementation.descriptionBuilder) {
		console.warn(
			'The filter client extension',
			clientExtensionFilterURL,
			'is not exporting a descriptionBuilder() function,',
			'thus the filter description will not show meaningful information.',
			'The client extension should be reworked.'
		);

		return '...';
	}

	try {
		return clientExtensionFilterImplementation.descriptionBuilder(
			selectedData
		);
	}
	catch (error) {
		console.error(
			'The filter client extension',
			clientExtensionFilterURL,
			'caused an error when trying to render the filter description in',
			'the descriptionBuilder<() function.',
			'The client extension needs to be fixed.',
			error
		);

		return '...';
	}
}

function getOdataString({
	clientExtensionFilterImplementation,
	clientExtensionFilterURL,
	selectedData,
}: ClientExtensionFilterImplementationArgs) {
	if (!clientExtensionFilterImplementation) {
		return '';
	}

	if (!clientExtensionFilterImplementation.oDataQueryBuilder) {
		console.error(
			'The filter client extension',
			clientExtensionFilterURL,
			'is not exporting a oDataQueryBuilder() function,',
			'thus the filter will NOT work.',
			'The client extension needs to be fixed.'
		);

		return '';
	}

	try {
		return clientExtensionFilterImplementation.oDataQueryBuilder(
			selectedData
		);
	}
	catch (error) {
		console.error(
			'The filter client extension',
			clientExtensionFilterURL,
			'caused an error when trying to compute the filter query in the',
			'oDataQueryBuilder() function.',
			'The client extension needs to be fixed.',
			error
		);

		return '';
	}
}

function ClientExtensionFilter({
	clientExtensionFilterImplementation,
	clientExtensionFilterURL,
	id,
	selectedData,
	setFilter,
}: ClientExtensionFilterImplementationArgs) {
	const [htmlElementBuilder, setHTMLElementBuilder] = useState<
		ClientExtensionFilterHTMLElementBuilder | undefined
	>(undefined);

	useEffect(() => {
		if (!clientExtensionFilterImplementation) {
			setHTMLElementBuilder(undefined);

			return;
		}

		if (!clientExtensionFilterImplementation.htmlElementBuilder) {
			console.error(
				'The filter client extension',
				clientExtensionFilterURL,
				'is not exporting an htmlElementBuilder() function,',
				'thus the filter configurator will NOT be shown in the UI.',
				'The client extension needs to be fixed.'
			);

			setHTMLElementBuilder(() => () => document.createElement('div'));

			return;
		}

		setHTMLElementBuilder(
			() =>
				clientExtensionFilterImplementation.htmlElementBuilder as ClientExtensionFilterHTMLElementBuilder
		);
	}, [clientExtensionFilterImplementation, clientExtensionFilterURL]);

	return (
		<ClientExtension
			args={{
				fieldName: id,
				filter: {
					selectedData,
				},
				setFilter: ({odataFilterString, selectedData}: SetFilterArgs) =>
					setFilter({
						active: true,
						...{
							odataFilterString,
							selectedData,
						},
					}),
			}}
			htmlElementBuilder={htmlElementBuilder}
		/>
	);
}

ClientExtensionFilter.propTypes = {
	clientExtensionFilterImplementation: PropTypes.shape({
		descriptionBuilder: PropTypes.func,
		htmlElementBuilder: PropTypes.func,
		oDataQueryBuilder: PropTypes.func,
	}),
	clientExtensionFilterURL: PropTypes.string,
	id: PropTypes.string.isRequired,
	selectedData: PropTypes.any,
	setFilter: PropTypes.func.isRequired,
};

const filterImplementation: FilterImplementation<ClientExtensionFilterImplementationArgs> =
	{
		Component: ClientExtensionFilter,
		getOdataString,
		getSelectedItemsLabel,
	};

export default filterImplementation;
