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

import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import React from 'react';

import {FETCH_STATUS} from '../../constants';

const AutoTranslate = ({
	fetchAutoTranslateFields,
	fetchAutoTranslateStatus,
}) => {
	const {message, status} = fetchAutoTranslateStatus;
	const isLoading = status === FETCH_STATUS.LOADING;

	return (
		<ClayLayout.ContentRow noGutters="y" verticalAlign="center">
			<ClayLayout.ContentCol>
				<ClayButton
					disabled={isLoading}
					displayType="secondary"
					onClick={fetchAutoTranslateFields}
					small
					type="button"
				>
					{Liferay.Language.get('auto-translate')}
				</ClayButton>
			</ClayLayout.ContentCol>

			<ClayLayout.ContentCol className="autotranslate-feedback">
				{isLoading && (
					<div>
						<span className="inline-item inline-item-before">
							<ClayLoadingIndicator small />
						</span>

						<span className="inline-item">
							{Liferay.Language.get('requesting-translation')}
						</span>
					</div>
				)}

				{status === FETCH_STATUS.SUCCESS && (
					<div className="has-success">
						<ClayForm.FeedbackItem className="mt-0">
							<ClayForm.FeedbackIndicator symbol="check-circle-full" />

							{message}
						</ClayForm.FeedbackItem>
					</div>
				)}

				{status === FETCH_STATUS.ERROR && (
					<div className="has-error">
						<ClayForm.FeedbackItem className="mt-0">
							<ClayForm.FeedbackIndicator symbol="exclamation-full" />

							{message}
						</ClayForm.FeedbackItem>
					</div>
				)}
			</ClayLayout.ContentCol>
		</ClayLayout.ContentRow>
	);
};

export default AutoTranslate;
