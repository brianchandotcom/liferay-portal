/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		<div className="mr-1 pl-2 row text-weight-bolder">
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
