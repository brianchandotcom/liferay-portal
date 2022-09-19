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
		({
			title,
			type,
			value,
		}: {
			title: string;
			type: SpecificItemTypes;
			value: string;
		}): JSX.Element | string =>
			title &&
			value &&
			type && (
				<div className="c-mb-4 sidebar-section" key={title}>
					<h5 className="c-mb-1 font-weight-semi-bold">{title}</h5>

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
