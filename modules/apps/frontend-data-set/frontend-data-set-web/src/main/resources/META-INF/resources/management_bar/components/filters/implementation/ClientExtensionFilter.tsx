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

type CXFilterHTMLElementBuilder = IHTMLElementBuilder<
	FDSFilterHTMLElementBuilderArgs<unknown>
>;

export interface ClientExtensionFilterImplementationArgs
	extends FilterImplementationArgs<unknown> {
	cxFilterImplementation?: FDSFilter<unknown>;
	cxFilterURL: string;
}

function getSelectedItemsLabel({
	cxFilterImplementation,
	cxFilterURL,
	selectedData,
}: ClientExtensionFilterImplementationArgs) {
	if (!cxFilterImplementation) {
		return LOADING_LABEL;
	}

	if (!cxFilterImplementation.descriptionBuilder) {
		console.warn(
			'The filter client extension',
			cxFilterURL,
			'is not exporting a descriptionBuilder() function,',
			'thus the filter description will not show meaningful information.',
			'The client extension should be reworked.'
		);

		return '...';
	}

	try {
		return cxFilterImplementation.descriptionBuilder(selectedData);
	}
	catch (error) {
		console.error(
			'The filter client extension',
			cxFilterURL,
			'caused an error when trying to render the filter description in',
			'the descriptionBuilder<() function.',
			'The client extension needs to be fixed.',
			error
		);

		return '...';
	}
}

function getOdataString({
	cxFilterImplementation,
	cxFilterURL,
	selectedData,
}: ClientExtensionFilterImplementationArgs) {
	if (!cxFilterImplementation) {
		return '';
	}

	if (!cxFilterImplementation.oDataQueryBuilder) {
		console.error(
			'The filter client extension',
			cxFilterURL,
			'is not exporting a oDataQueryBuilder() function,',
			'thus the filter will NOT work.',
			'The client extension needs to be fixed.'
		);

		return '';
	}

	try {
		return cxFilterImplementation.oDataQueryBuilder(selectedData);
	}
	catch (error) {
		console.error(
			'The filter client extension',
			cxFilterURL,
			'caused an error when trying to compute the filter query in the',
			'oDataQueryBuilder() function.',
			'The client extension needs to be fixed.',
			error
		);

		return '';
	}
}

function ClientExtensionFilter({
	cxFilterImplementation,
	cxFilterURL,
	selectedData,
	setFilter,
}: ClientExtensionFilterImplementationArgs) {
	const [htmlElementBuilder, setHTMLElementBuilder] = useState<
		CXFilterHTMLElementBuilder | undefined
	>(undefined);

	useEffect(() => {
		if (!cxFilterImplementation) {
			setHTMLElementBuilder(undefined);

			return;
		}

		if (!cxFilterImplementation.htmlElementBuilder) {
			console.error(
				'The filter client extension',
				cxFilterURL,
				'is not exporting an htmlElementBuilder() function,',
				'thus the filter configurator will NOT be shown in the UI.',
				'The client extension needs to be fixed.'
			);

			setHTMLElementBuilder(() => () => document.createElement('div'));

			return;
		}

		setHTMLElementBuilder(
			() =>
				cxFilterImplementation.htmlElementBuilder as CXFilterHTMLElementBuilder
		);
	}, [cxFilterImplementation, cxFilterURL]);

	return (
		<ClientExtension
			args={{
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
	cxFilterImplementation: PropTypes.shape({
		descriptionBuilder: PropTypes.func,
		htmlElementBuilder: PropTypes.func,
		oDataQueryBuilder: PropTypes.func,
	}),
	cxFilterURL: PropTypes.string,
	id: PropTypes.string.isRequired,
	selectedData: PropTypes.any,
	setFilter: PropTypes.func.isRequired,
};

const filterImplementation: FilterImplementation<ClientExtensionFilterImplementationArgs> = {
	Component: ClientExtensionFilter,
	getOdataString,
	getSelectedItemsLabel,
};

export default filterImplementation;
