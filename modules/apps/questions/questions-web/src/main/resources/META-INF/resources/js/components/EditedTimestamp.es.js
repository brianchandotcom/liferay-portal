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

import {fromNow} from '../utils/time.es';
import {dateToInternationalHuman} from '../utils/utils.es';

const BCP47LanguageId = Liferay.ThemeDisplay.getBCP47LanguageId();

const getTextDelimeted = (text, date) => {
	const delimeter = ' - ';

	return `${text} ${delimeter} ${date}`;
};

const EditedTimestamp = ({
	creator,
	dateCreated,
	dateModified,
	operationText,
	showSignature = false,
	styledTimeStamp = false,
}) => {
	if (!dateCreated || !dateModified) {
		return null;
	}

	const selectedText = getTextDelimeted(
		operationText,
		dateToInternationalHuman(dateCreated, BCP47LanguageId)
	);

	const elapsedTime = fromNow(dateCreated);

	return (
		<div className="mr-2 pl-2 row text-weight-bolder">
			{styledTimeStamp && (
				<div className="d-flex flex-row mb-0 ml-1">
					<span className="text-3 text-weight-bolder">{creator}</span>

					<span className="align-items-center d-flex ml-2 text-3 text-weight-lighter">
						{elapsedTime}
					</span>
				</div>
			)}

			{!styledTimeStamp && !showSignature && (
				<div>
					<ClayTooltipProvider>
						<span className="c-ml-2 small">{selectedText}</span>
					</ClayTooltipProvider>
				</div>
			)}
		</div>
	);
};
export default EditedTimestamp;
