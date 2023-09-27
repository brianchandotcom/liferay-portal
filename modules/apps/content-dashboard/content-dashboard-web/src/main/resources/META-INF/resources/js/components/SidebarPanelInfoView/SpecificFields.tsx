/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React from 'react';

const {default: FileUrlCopyButton} = require('./FileUrlCopyButton');
const {default: formatDate} = require('./utils/formatDate');

const SpecificItem = ({
	languageTag,
	type,
	value,
}: {
	languageTag: string;
	type: SpecificItemTypes;
	value: string;
}): JSX.Element => {
	const components: {Date: Function; String: Function; URL: Function} = {
		Date: (): JSX.Element => <time>{formatDate(value, languageTag)}</time>,
		String: (): JSX.Element => <p>{value}</p>,
		URL: (): any => <FileUrlCopyButton url={value} />,
	};

	return components[type] ? components[type]() : value;
};

const SpecificFields = ({fields, languageTag}: IProps) => {
	if (!fields.length) {
		return null;
	}

	return fields.map(
		({help, title, type, value}: SpecificField): JSX.Element | string =>
			title &&
			value &&
			type && (
				<div className="c-mb-4 sidebar-section" key={title}>
					<h5 className="c-mb-1 font-weight-semi-bold">
						{title}

						{help && (
							<ClayTooltipProvider>
								<span
									className="ml-1 text-secondary"
									data-tooltip-align="top"
									title={help}
								>
									<ClayIcon symbol="question-circle-full" />
								</span>
							</ClayTooltipProvider>
						)}
					</h5>

					<SpecificItem
						languageTag={languageTag}
						type={type}
						value={value}
					/>
				</div>
			)
	);
};

type SpecificItemTypes = 'Date' | 'String' | 'URL';

interface SpecificField {
	help?: string;
	title: string;
	type: SpecificItemTypes;
	value: string;
}

interface IProps {
	children?: React.ReactNode;
	fields: SpecificField[];
	languageTag: string;
}

export default SpecificFields;
