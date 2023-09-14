/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClientExtension} from 'frontend-js-components-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

const getSelectedItemsLabel = ({odataFilterString}) => {
	return odataFilterString;
};

const getOdataString = ({odataFilterString}) => {
	return odataFilterString;
};

function ClientExtensionFilter({cxFilterURL, selectedData, setFilter}) {
	const [htmlElementBuilder, setHTMLElementBuilder] = useState(() => () =>
		document.createElement('div')
	);

	useEffect(() => {
		const getCXFilter = async () => {
			const cxFilter = await import(
				/* webpackIgnore: true */ cxFilterURL
			);

			setHTMLElementBuilder(() => cxFilter['default']);
		};

		getCXFilter();
	}, [cxFilterURL]);

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
