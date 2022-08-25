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

import {ClayTooltipProvider} from '@clayui/tooltip';
import React from 'react';

import {dateToInternationalHuman} from '../utils/utils.es';

const BCP47LanguageId = Liferay.ThemeDisplay.getBCP47LanguageId();

const getTextDelimeted = (text, date) => {
	const delimeter = ' - ';

	return `${text} ${delimeter} ${date}`;
};

const EditedTimestamp = ({dateCreated, dateModified, operationText}) => {
	const selectedText = getTextDelimeted(
		operationText,
		dateToInternationalHuman(dateCreated, BCP47LanguageId)
	);

	const editedText = getTextDelimeted(
		Liferay.Language.get('edited'),
		dateToInternationalHuman(dateModified, BCP47LanguageId)
	);

	return (
		<ClayTooltipProvider>
			<span className="c-ml-2 small">
				{dateModified === dateCreated ? (
					selectedText
				) : (
					<span data-tooltip-align="top-right" title={editedText}>
						{`${selectedText} - `}

						<i>({`${Liferay.Language.get('edited')}`})</i>
					</span>
				)}
			</span>
		</ClayTooltipProvider>
	);
};
export default EditedTimestamp;
