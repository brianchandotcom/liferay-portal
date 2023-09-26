/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClientExtension} from 'frontend-js-components-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

const getSelectedItemsLabel = ({
	cxFilterImplementation,
	cxFilterURL,
	selectedData,
}) => {
	if (!cxFilterImplementation) {
		return '...';
	}

	if (!cxFilterImplementation.getSelectedItemsLabel) {
		console.warn(
			'The filter client extension',
			cxFilterURL,
			'is not exporting a getSelectedItemsLabel() function,',
			'thus the filter description will not show meaningful information.',
			'The client extension should be reworked.'
		);

		return '...';
	}

	try {
		return cxFilterImplementation.getSelectedItemsLabel(selectedData);
	}
	catch (error) {
		console.error(
			'The filter client extension',
			cxFilterURL,
			'caused an error when trying to render the filter description in',
			'the getSelectedItemsLabel() function.',
			'The client extension needs to be fixed.',
			error
		);

		return '...';
	}
};

const getOdataString = ({
	cxFilterImplementation,
	cxFilterURL,
	selectedData,
}) => {
	if (!cxFilterImplementation) {
		return '';
	}

	if (!cxFilterImplementation.getOdataString) {
		console.error(
			'The filter client extension',
			cxFilterURL,
			'is not exporting a getOdataString() function,',
			'thus the filter will NOT work.',
			'The client extension needs to be fixed.'
		);

		return '';
	}

	try {
		return cxFilterImplementation.getOdataString(selectedData);
	}
	catch (error) {
		console.error(
			'The filter client extension',
			cxFilterURL,
			'caused an error when trying to compute the filter query in the',
			'getOdataString() function.',
			'The client extension needs to be fixed.',
			error
		);

		return '';
	}
};

function spinnerHTMLElementBuilder() {
	const span = document.createElement('span');

	span.ariaHidden = true;
	span.className =
		'loading-animation loading-animation-secondary loading-animation-sm';

	const div = document.createElement('div');

	div.appendChild(span);

	return div;
}

function ClientExtensionFilter({
	cxFilterImplementation,
	cxFilterURL,
	selectedData,
	setFilter,
}) {
	const [htmlElementBuilder, setHTMLElementBuilder] = useState(
		() => spinnerHTMLElementBuilder
	);

	useEffect(() => {
		if (!cxFilterImplementation) {
			setHTMLElementBuilder(() => spinnerHTMLElementBuilder);

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

		setHTMLElementBuilder(() => cxFilterImplementation.htmlElementBuilder);
	}, [cxFilterImplementation, cxFilterURL]);

	return (
		<ClientExtension
			args={{
				filter: {
					selectedData,
				},
				setFilter: ({odataFilterString, selectedData}) =>
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
	cxFilterImplementation: PropTypes.func,
	cxFilterURL: PropTypes.string,
	id: PropTypes.string.isRequired,
	selectedData: PropTypes.any,
	setFilter: PropTypes.func.isRequired,
};

export default {
	Component: ClientExtensionFilter,
	getOdataString,
	getSelectedItemsLabel,
};
